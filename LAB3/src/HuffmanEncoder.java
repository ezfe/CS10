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
	private static List<CharacterFrequencyStore> freqListForCompression = new LinkedList<CharacterFrequencyStore>();

	public static void main(String[] args) {
		String in = getFilePath();
		String out;

		if (in.lastIndexOf(".txt") == in.length() - 4) {
			//Replace .txt with .ezip to represent a zipped file
			out = in.substring(0, in.lastIndexOf(".txt")) + ".ezip";
			try {
				//Let's compress the file using compressFile method!
				compressFile(in, out);
			} catch (CustomException e) {
				System.err.println("Unable to compress");
				System.err.println("Message: " + e.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (in.lastIndexOf(".ezip") == in.length() - 5) {
			//Replace .ezip with _decompressed.txt to represent an uncompressed file
			out = in.substring(0, in.lastIndexOf(".ezip")) + "_decompressed.txt";			
			try {
				//Let's now decompress the file
				decompressFile(in, out);
			} catch (CustomException e) {
				System.err.println("Unable to decompress");
				System.err.println("Message: " + e.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
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
		//Make the freqTre
		generateCharacterFrequencyTree(pathIn);

		//Create a code map
		HashMap<Character, String> charCodeMap = new HashMap<Character, String>();
		//Traverse the tree and fill the code map
		freqTree.traverse(charCodeMap);

		//Create a buffered bit writer
		BufferedBitWriter outFile = new BufferedBitWriter(pathOut);
		//Create a buffered reader (not a buffered bit reader, because we're reading regular stuff)
		BufferedReader inFile =  new BufferedReader(new FileReader(pathIn));

		writeCharacter('~', outFile); //mark start

		//Loop through preList
		for (CharacterFrequencyStore cfstore : freqListForCompression) {
			if (cfstore.character != null) {
				writeCharacter(cfstore.character, outFile);

				String freqString = Integer.toString(cfstore.frequency);
				for (char c : freqString.toCharArray()) {
					writeCharacter(c, outFile);
				}

				writeCharacter(',', outFile); //Mark end of entry
			} else {
				inFile.close();
				throw new CustomException("Character not found! Can't encode");
			}
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
					inFile.close();
					throw new NullPointerException("Character Code Map did not contain " + c);
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
		PriorityQueue<BinaryTree<CharacterFrequencyStore>> pq;
		try {
			pq = new PriorityQueue<BinaryTree<CharacterFrequencyStore>>(charMap.size(), comparator);
		} catch (IllegalArgumentException e) {
			throw new CustomException("Empty files cannot be compressed, silly");
		}

		//Go through the frequency map and create trees from them
		//Add each tree to the priority queue
		Iterator<Character> iterator = charMap.keySet().iterator();
		freqListForCompression.clear();
		while (iterator.hasNext()) {
			char c = iterator.next(); //Character from the keySet (a key)
			//Get the character and it's frequency
			//And store it in a new instancec of CharacterFrequencyStore
			CharacterFrequencyStore cfstore = new CharacterFrequencyStore(charMap.get(c), c);

			freqListForCompression.add(cfstore);

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

		int firstInt = compressedFile.read();
		place++;
		if (firstInt == -1) {
			compressedFile.close();
			decompressedFile.close();
			throw new CustomException("Empty file");
		} else if ((char)firstInt != '~') {
			compressedFile.close();
			decompressedFile.close();
			throw new CustomException("File doesn't start");
		}

		while (true) {
			int cint = compressedFile.read();
			place++;
			if (cint == -1) {
				//Ran out of file, but we haven't even started decompressing
				compressedFile.close();
				decompressedFile.close();
				throw new CustomException("Unexpected EOF");
			} else {
				//cint should be the character, unless we're at the end of the list
				char character = (char)cint;

				//This should be the first number, unless we're at the end of the list
				char firstNumber = (char)compressedFile.read();
				place++;

				//If character is - and firstNumber is -, then we're at the end of the list
				if (character == '-' && firstNumber == '-') break;

				//Start constructing the number
				String workingNumber;
				if (Character.isDigit(firstNumber)) {
					workingNumber = "" + firstNumber;
				} else {
					compressedFile.close();
					decompressedFile.close();
					throw new CustomException("No frequency found for " + character);
				}

				while (true) {
					//Cast the character
					char x = (char)compressedFile.read();
					place++; //Increment place
					//If the character is a digit, add it to our number
					if (Character.isDigit(x)) workingNumber += (char)x;
					//We've hit the end of the number sequence, let's get out of here
					else break;
				}

				try {
					//Add to the list a new CFStore object with workingNumber and character
					extractedList.add(new CharacterFrequencyStore(Integer.parseInt(workingNumber), character));
				} catch (NumberFormatException e) {
					//Uh oh, the number was bad
					compressedFile.close();
					decompressedFile.close();
					throw new CustomException("Unable to parse " + workingNumber + "to int");
				}
			}
		}

		//Make a new comparator
		TreeComparator comparator = new TreeComparator();
		//And make a priority queue, using that comparator
		PriorityQueue<BinaryTree<CharacterFrequencyStore>> pq;
		try {
			//Make a new priority queue
			pq = new PriorityQueue<BinaryTree<CharacterFrequencyStore>>(extractedList.size(), comparator);
		} catch (IllegalArgumentException e) {
			compressedFile.close();
			decompressedFile.close();
			throw new CustomException("Unable to construct priority queue");
		}

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

		//generate a frequency tree from the priority queue
		generateCharacterFrequencyTree(pq);

		//Adance the compressedFileBit reader to the end of the encoding
		//8 bits per byte
		for (int i = 0; i < place * 8; i++) {
			compressedFileBit.readBit();
		}

		//Keep track of where in the tree we are
		BinaryTree<CharacterFrequencyStore> current = freqTree;

		//More looping
		while (true) {
			int bit = compressedFileBit.readBit(); //Read the bit
			if (bit == -1) {
				//Stop! We've reached the end of the file
				break;
			} else if (!freqTree.isLeaf()) {
				/*
				 * If the freqTree is a leaf, then there is just one character, and every bit represents that character
				 * However if it's not a leaf, then we should traverse the tree
				 */
				if (bit == 1) {
					//The bit is 1, move right
					current = current.getRight();
				} else if (bit == 0) {
					//The bit is 0, move left
					current = current.getLeft();
				}
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