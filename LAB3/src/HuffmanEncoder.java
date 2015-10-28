import java.io.*;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;
import javax.swing.SwingUtilities;

public class HuffmanEncoder {

	public static void main(String[] args) throws Exception {
//		String in = getFilePath();
		String in = "/Users/ezekielelin/Library/Mobile Documents/com~apple~CloudDocs/Developer/CS10/LAB3/src/MobyDick.txt";
		
		String out = in + "__compressed.txt";
		String out2 = in + "__decompressed.txt";

		BinaryTree<CharacterFrequencyStore> freqTree = generateCharacterFrequencyTree(in);

		List<CharacterFrequencyStore> preList = new LinkedList<CharacterFrequencyStore>();
		freqTree.preorder(preList);
		List<CharacterFrequencyStore> inList = new LinkedList<CharacterFrequencyStore>();
		freqTree.inorder(inList);
		
		System.out.println(preList);

		compressFile(in, out, freqTree);
				
		decompressFile(out, out2, freqTree);
	}

	private static void compressFile(String pathIn, String pathOut, BinaryTree<CharacterFrequencyStore> freqTree) throws Exception {
		//Create a code map
		HashMap<Character, String> charCodeMap = new HashMap<Character, String>();
		//Traverse the tree and generate the code map
		freqTree.traverse(charCodeMap);

		BufferedBitWriter outFile = new BufferedBitWriter(pathOut);
		BufferedReader inFile =  new BufferedReader(new FileReader(pathIn));

		while (true) {
			int cint = inFile.read();
			if (cint == -1) {
				break;
			} else {
				char c = (char)cint;
				String toWrite = charCodeMap.get(c);
				if (toWrite == null) {
					System.err.println("Character Code Map did not contain " + c);
				}
				for (char bit : toWrite.toCharArray()){
					if (bit == 'L') {
						outFile.writeBit(0);
					} else {
						outFile.writeBit(1);
					}
				}
			}
		}

		inFile.close();
		outFile.close();
	}

	private static BinaryTree<CharacterFrequencyStore> generateCharacterFrequencyTree(String s) throws Exception {
		BufferedReader inputFile =  new BufferedReader(new FileReader(s));

		HashMap<Character, Integer> charMap = new HashMap<Character, Integer>();
		//Generate a frequency table
		while (true) {
			int cint = inputFile.read();
			if (cint == -1) {
				//There is nothing left, let's move on!
				break;
			} else {
				char c = (char)cint;
				if (charMap.get(c) == null) {
					charMap.put(c, 1);
				} else {
					charMap.put(c, charMap.get(c)+1);
				}

			}
		}

		//Close the input file
		inputFile.close();

		TreeComparator comparator = new TreeComparator();
		PriorityQueue<BinaryTree<CharacterFrequencyStore>> pq = new PriorityQueue<BinaryTree<CharacterFrequencyStore>>(charMap.size(), comparator);

		//Go through the frequency map and create trees from them
		//Add each tree to the priority queue
		Iterator<Character> iterator = charMap.keySet().iterator();
		while (iterator.hasNext()) {
			char c = iterator.next();
			CharacterFrequencyStore cfstore = new CharacterFrequencyStore(charMap.get(c), c);

			BinaryTree<CharacterFrequencyStore> singletonTree = new BinaryTree<CharacterFrequencyStore>(cfstore);
			pq.add(singletonTree);
		}

		//While the priority queue has more than one element, go through and combine them
		while (pq.size() > 1) {
			BinaryTree<CharacterFrequencyStore> a = pq.remove();
			BinaryTree<CharacterFrequencyStore> b = pq.remove();

			CharacterFrequencyStore newCFStore = new CharacterFrequencyStore(a.getValue().frequency + b.getValue().frequency);

			BinaryTree<CharacterFrequencyStore> n = new BinaryTree<CharacterFrequencyStore>(newCFStore);
			n.setLeft(a);
			n.setRight(b);

			pq.add(n);
		}

		//Return the last element in the priority queue
		return pq.remove();
	}

	private static void decompressFile(String pathIn, String pathOut, BinaryTree<CharacterFrequencyStore> freqTree) throws Exception {
		BufferedBitReader compressedFile = new BufferedBitReader(pathIn);
		BufferedWriter decompressedFile = new BufferedWriter(new FileWriter(pathOut));

		BinaryTree<CharacterFrequencyStore> current = freqTree;

		while (true) {
			int bit = compressedFile.readBit();
			if (bit == -1) {
				break;
			} else if (bit == 1) {
				current = current.getRight();
			} else if (bit == 0) {
				current = current.getLeft();
			}
			Character character = current.getValue().character;
			if (character != null) {
				decompressedFile.write(character);
				current = freqTree;
			}
		}

		decompressedFile.close();
		compressedFile.close();
	}

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