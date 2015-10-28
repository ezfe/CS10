/**
 * Huffman Encoder file to encode files using Huffman encoding.
 * @author Ezekiel Elin, October 27, 2015
 */

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JFileChooser;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicReference;
import javax.swing.SwingUtilities;

public class HuffmanEncoder {

	private static BinaryTree<CharacterFrequencyStore> freqTree; 

	public static void main(String[] args) throws Exception {
		String in = "/Users/ezekielelin/Library/Mobile Documents/com~apple~CloudDocs/Developer/CS10/LAB3/src/MobyDick.ezip";
	
		//Let's now generate a path for the compressed output, as well as the decompressed output
		String out = null;
		
		if (in.lastIndexOf(".txt") == in.length() - 4) {
			out = in.substring(0, in.lastIndexOf(".txt")) + ".ezip";
			try {
				//Using generateCharacterFrequencyTree() to get a frequency tree
				generateCharacterFrequencyTree(in);

				//Let's compress the file using compressFile method!
				compressFile(in, out);
			} catch (Exception e) {
				//Uh oh, let's tell the user what happened
				//			System.err.println(e);
				throw e;
			}
		} else if (in.lastIndexOf(".ezip") == in.length() - 5) {
			out = in.substring(0, in.lastIndexOf(".ezip")) + "_decompressed.txt";			
			try {
				//Let's now decompress the file we just compressed using decompressFile()
				decompressFile(in, out);
			} catch (Exception e) {
				//Uh oh, let's tell the user what happened
				//			System.err.println(e);
				throw e;
			}
		}
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

		List<CharacterFrequencyStore> frequencyList = new LinkedList<CharacterFrequencyStore>();
		freqTree.populateStorageList(frequencyList);

		writeCharacter('%', outFile); //mark start
		writeCharacter('%', outFile);

		//Loop through preList
		for (CharacterFrequencyStore cfstore : frequencyList) {
			if (cfstore.character != null) {
				writeCharacter(cfstore.character, outFile);
			}

			writeCharacter('!', outFile); //mark end of character
			writeCharacter('!', outFile);

			String freqString = Integer.toString(cfstore.frequency);
			for (char c : freqString.toCharArray()) {
				writeCharacter(c, outFile);
			}
			writeCharacter('%', outFile); //Mark end of entry
			writeCharacter('%', outFile); //aka the start of the next
		}

		writeCharacter('-', outFile); //Mark the end of the list
		writeCharacter('-', outFile); //Really mark it!

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

		generateCharacterFrequencyTree(pq);
	}

	private static void generateCharacterFrequencyTree(PriorityQueue<BinaryTree<CharacterFrequencyStore>> pq) {

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
		//Make the readers and writer
		BufferedBitReader compressedFileBit = new BufferedBitReader(pathIn);
		BufferedReader compressedFile =  new BufferedReader(new FileReader(pathIn));
		BufferedWriter decompressedFile = new BufferedWriter(new FileWriter(pathOut));

		List<CharacterFrequencyStore> extractedList = new LinkedList<CharacterFrequencyStore>();

		int place = 0;

		Character previousCharacter = null;
		while (true) {
			int cint = compressedFile.read();
			place++;
			if (cint == -1) {
				//There is nothing left, let's move on!
				break;
			} else {
				//Get the character
				char charA = (char)cint;

				if (charA == '%') {
					if (!(previousCharacter != null && previousCharacter == '%')) {
						compressedFile.read();
						place++;
					}

					char charC = (char)compressedFile.read();
					char charD = (char)compressedFile.read();
					char charE = (char)compressedFile.read();
					place += 3;


					char firstNumberCharacter = charE;

					Character foundCharacter = null;

					if (charD == '!' && charE == '!') {
						//We know charC is the character
						foundCharacter = charC;
						//AKA charF
						firstNumberCharacter = (char)compressedFile.read();
						place++;
					} else if (charC == '-' && charD == '-') {
						break;
					} else {//Else, charE is the first.
					}

					String workingNumber;
					if (Character.isDigit(firstNumberCharacter)) {
						workingNumber = "" + firstNumberCharacter;
					} else {
						workingNumber = "";
						System.out.println("Uh oh");
					}

					while (true) {
						char x = (char)compressedFile.read();
						place++;
						//TODO check if X is a number instead of what X isn't
						if (Character.isDigit(x)) workingNumber += (char)x;
						else break;
					}

					//					System.out.println(foundCharacter);
					//					System.out.println(workingNumber);

					if (foundCharacter == null) {
						extractedList.add(new CharacterFrequencyStore(Integer.parseInt(workingNumber)));
					} else {
						extractedList.add(new CharacterFrequencyStore(Integer.parseInt(workingNumber), foundCharacter));
					}
				}
				previousCharacter = charA;
			}
		}

		//Make a new comparator
		TreeComparator comparator = new TreeComparator();
		//And make a priority queue, using that comparator
		PriorityQueue<BinaryTree<CharacterFrequencyStore>> pq = new PriorityQueue<BinaryTree<CharacterFrequencyStore>>(extractedList.size(), comparator);

		//Go through the frequency map and create trees from them
		//Add each tree to the priority queue
		Iterator<CharacterFrequencyStore> iterator = extractedList.iterator();
		while (iterator.hasNext()) {
			CharacterFrequencyStore cfstore = iterator.next(); //Character from the keySet (a key)

			//Make a new binary tree, with this new instance
			BinaryTree<CharacterFrequencyStore> singletonTree = new BinaryTree<CharacterFrequencyStore>(cfstore);
			//Add it to the priority queue
			pq.add(singletonTree);
		}

		generateCharacterFrequencyTree(pq);

		//Have to adjust place by one, because it is overstepped one
		for (int i = 0; i < (place - 1) * 8; i++) {
			compressedFileBit.readBit();
		}

		//Keep track of where in the tree we are
		BinaryTree<CharacterFrequencyStore> current = freqTree;

		//More looping forever!
		while (true) {
			int bit = compressedFileBit.readBit(); //Read the bit
			//			System.out.println("Bit: " + bit);
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
		compressedFileBit.close();
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
	private static void writeCharacter(char c, BufferedBitWriter w) throws IOException {
		String binary = Integer.toBinaryString((int) c);

		//Mark the end of our file
		//Fix the binary string, so that it's all 8 digits
		while (binary.length() < 8) binary = '0' + binary;

		//Write out the binary string
		for (char x : binary.toCharArray()) w.writeBit(x == '1' ? 1 : 0);
	}
}