/**
 * Huffman Encoder file to encode files using Huffman encoding.
 * @author Ezekiel Elin, October 27, 2015
 */

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JFileChooser;
import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicReference;
import javax.swing.SwingUtilities;

public class HuffmanEncoder {

	private static BinaryTree<CharacterFrequencyStore> freqTree; 
	
	public static void main(String[] args) {
		//First things first, let's get the file path for what we are going to work with!
		String in = getFilePath();

		//Let's now generate a path for the compressed output, as well as the decompressed output
		String out = in + "__compressed.txt";
		String out2 = in + "__decompressed.txt";

		try {
			//Using generateCharacterFrequencyTree() to get a frequency tree
			generateCharacterFrequencyTree(in);
		} catch (Exception e) {
			//Uh oh, let's tell the user what happened
			System.err.println(e.getMessage());
		}

		try {
			//Let's compress the file using compressFile method!
			compressFile(in, out);
		} catch (Exception e) {
			//Uh oh, let's tell the user what happened
			System.err.println(e.getMessage());
		}
		
		try {
			//Let's now decompress the file we just compressed using decompressFile()
			decompressFile(out, out2);
		} catch (Exception e) {
			//Uh oh, let's tell the user what happened
			System.err.println(e.getMessage());
		}
		
		//Should be all set
	}

	/**
	 * Compresses the given file, storing it in the given output
	 * @param pathIn file to compress
	 * @param pathOut where to put it
	 * @throws Exception when bad stuff happens
	 */
	private static void compressFile(String pathIn, String pathOut) throws Exception {
		//Create a code map
		HashMap<Character, String> charCodeMap = new HashMap<Character, String>();
		//Traverse the tree and fill the code map
		freqTree.traverse(charCodeMap);

		//Create a buffered bit writer
		BufferedBitWriter outFile = new BufferedBitWriter(pathOut);
		//Create a buffered reader (not a buffered bit reader, because we're reading regular stuff)
		BufferedReader inFile =  new BufferedReader(new FileReader(pathIn));

		//Loop forever!
		while (true) {
			//Try to read the file
			int cint = inFile.read();
			if (cint == -1) {
				//If it's -1, then break out of the loop
				break;
			} else {
				//Otherwise, make it into a character
				char c = (char) cint;
				//Figure out what this will be represented by in the compressed version
				String toWrite = charCodeMap.get(c);
				//If it is null, then we have a SERIOUS problem
				if (toWrite == null) {
					System.err.println("Character Code Map did not contain " + c);
					throw new NullPointerException("Couldn't find the code for " + c);
				}
				//Loop through the string, writing the bits
				for (char bit : toWrite.toCharArray()){
					if (bit == 'L') {
						//If the path was a Left, write 0
						outFile.writeBit(0);
					} else {
						//Otherwise, write 1
						outFile.writeBit(1);
					}
				}
			}
		}

		//Close the files
		inFile.close();
		outFile.close();
	}

	/**
	 * Generates a frequency tree from the given path
	 * @param s path of file to generate from
	 * @throws Exception when something bad happens
	 */
	private static void generateCharacterFrequencyTree(String s) throws Exception {
		//Make a buffered reader
		BufferedReader inputFile =  new BufferedReader(new FileReader(s));

		//Make a hashmap of the character frequencies
		HashMap<Character, Integer> charMap = new HashMap<Character, Integer>();
		//Generate a frequency table
		while (true) {
			int cint = inputFile.read();
			if (cint == -1) {
				//There is nothing left, let's move on!
				break;
			} else {
				//Get the character
				char c = (char)cint;
				if (charMap.get(c) == null) {
					//If the character hasn't been seen yet, make it's frequency 1
					charMap.put(c, 1);
				} else {
					//Otherwise, it's frequency is whatever it already is, plus 1
					charMap.put(c, charMap.get(c)+1);
				}

			}
		}

		//Close the input file
		inputFile.close();

		//Make a new comparator
		TreeComparator comparator = new TreeComparator();
		//And make a priority queue, using that comparator
		PriorityQueue<BinaryTree<CharacterFrequencyStore>> pq = new PriorityQueue<BinaryTree<CharacterFrequencyStore>>(charMap.size(), comparator);

		//Go through the frequency map and create trees from them
		//Add each tree to the priority queue
		Iterator<Character> iterator = charMap.keySet().iterator();
		while (iterator.hasNext()) {
			char c = iterator.next(); //Character from the keySet (a key)
			//Get the character and it's frequency
			//And store it in a new instancec of CharacterFrequencyStore
			CharacterFrequencyStore cfstore = new CharacterFrequencyStore(charMap.get(c), c);

			//Make a new binary tree, with this new instance
			BinaryTree<CharacterFrequencyStore> singletonTree = new BinaryTree<CharacterFrequencyStore>(cfstore);
			//Add it to the priority queue
			pq.add(singletonTree);
		}

		//While the priority queue has more than one element, go through and combine them
		while (pq.size() > 1) {
			//Get the smallest two trees (a and b)
			BinaryTree<CharacterFrequencyStore> a = pq.remove();
			BinaryTree<CharacterFrequencyStore> b = pq.remove();

			//Make a new frequency object, without a character
			//Frequency is sum of a-frequency and b-frequency
			CharacterFrequencyStore newCFStore = new CharacterFrequencyStore(a.getValue().frequency + b.getValue().frequency);

			//Make a new binary tree, with it's children being a and b
			//and it's data being the new frequency object
			BinaryTree<CharacterFrequencyStore> n = new BinaryTree<CharacterFrequencyStore>(newCFStore);
			n.setLeft(a);
			n.setRight(b);

			//Add it back to the priority queue
			pq.add(n);
		}

		//Return the last element in the priority queue, and make it the freqTree
		freqTree = pq.remove();
	}

	/**
	 * Decompress the given file to the given output path
	 * @param pathIn file to decompress
	 * @param pathOut file to output to
	 * @throws Exception when something bad happens
	 */
	private static void decompressFile(String pathIn, String pathOut) throws Exception {
		//Make the reader and writer
		BufferedBitReader compressedFile = new BufferedBitReader(pathIn);
		BufferedWriter decompressedFile = new BufferedWriter(new FileWriter(pathOut));

		//Keep track of where in the tree we are
		BinaryTree<CharacterFrequencyStore> current = freqTree;

		//More looping forever!
		while (true) {
			int bit = compressedFile.readBit(); //Read the bit
			if (bit == -1) {
				//Stop! We've reached the end of the file
				break;
			} else if (bit == 1) {
				//The bit is 1, move right
				current = current.getRight();
			} else if (bit == 0) {
				//The bit is 0, move left
				current = current.getLeft();
			}
			//Get the character of the current tree
			Character character = current.getValue().character;
			//Was there even a character?
			if (character != null) {
				//Yes there was!
				//Write it to the decompressed file
				decompressedFile.write(character);
				//And reset the current back to the top
				current = freqTree;
			}
		}

		//Close stuff
		decompressedFile.close();
		compressedFile.close();
	}

	/**
	 * Supplied method–gets a user supplied file path
	 * @return Path to file user selects
	 */
	public static String getFilePath() {
		final AtomicReference<String> result = new AtomicReference<>();

		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					JFileChooser fc = new JFileChooser();

					int returnVal = fc.showOpenDialog(null);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						File file = fc.getSelectedFile();
						String pathName = file.getAbsolutePath();
						result.set(pathName);
					}
					else
						result.set("");
				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}

		// Create a file chooser.
		return result.get();
	}
}