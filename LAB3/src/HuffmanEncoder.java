import java.io.*;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JFileChooser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicReference;
import javax.swing.SwingUtilities;

public class HuffmanEncoder {

	public static void main(String[] args) throws Exception {
		String s = getFilePath();
		BufferedReader inputFile =  new BufferedReader(new FileReader(s));
		
		HashMap<Character, Integer> charMap = new HashMap<Character, Integer>();
		HashMap<Character, String> charCodeMap = new HashMap<Character, String>();
		HashMap<Integer, BinaryTree<Character>> singletons = new HashMap<Integer, BinaryTree<Character>>();
		
		while (true) {
			int cint = inputFile.read();
			if (cint == -1) {
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
		
		inputFile.close();
		
		PriorityQueue<BinaryTree<CharacterFrequencyStore>> pq = new PriorityQueue<BinaryTree<CharacterFrequencyStore>>(charMap.size(), new TreeComparator());
		
		Iterator iterator = charMap.keySet().iterator();
		while (iterator.hasNext()) {
			char c = (char)iterator.next();
			CharacterFrequencyStore cfstore = new CharacterFrequencyStore(charMap.get(c), c);
			BinaryTree<CharacterFrequencyStore> singletonTree = new BinaryTree<CharacterFrequencyStore>(cfstore);
			
			pq.add(singletonTree);
		}
		
		while (pq.size() > 1) {
			BinaryTree<CharacterFrequencyStore> a = pq.remove();
			BinaryTree<CharacterFrequencyStore> b = pq.remove();
			
			CharacterFrequencyStore newCFStore = new CharacterFrequencyStore(a.getValue().frequency + b.getValue().frequency);
			
			BinaryTree<CharacterFrequencyStore> n = new BinaryTree<CharacterFrequencyStore>(newCFStore);
			n.setLeft(a);
			n.setRight(b);
			
			pq.add(n);
		}

		BinaryTree<CharacterFrequencyStore> mainTree = pq.peek();
		mainTree.traverse(charCodeMap);

		String out = getFilePath();
		BufferedBitWriter outFile = new BufferedBitWriter(out);
		BufferedReader inFile =  new BufferedReader(new FileReader(s));

		while (true) {
			int cint = inFile.read();
			if (cint == -1) {
				break;
			} else {
				char c = (char)cint;
				String toWrite = charCodeMap.get(c);
				
				for (char bit : toWrite.toCharArray()){
					if (bit == '0') {
						outFile.writeBit(0);
					} else {
						outFile.writeBit(1);
					}
				}
			}
		}
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