/*
 * Prasad Jayanti      November 6, 2015
 * Purpose: To test the Hash, BST, and AVL Tree implementations of the MyMapADT interface
 */

import java.util.Random;

public class MyMapDriver {
	private static final boolean DEBUG = false; // true to print the map after each insert and delete
	private static final boolean PERFORM_DELETIONS = false; // set to false if delete is not implemented
	private static final String WHICH_IMPLEMENTATION = "AVL"; // "ArrayList", "Hash", "BST", "AVL" are the possibilities
	private static final String KEY_SOURCE = "fixed"; // "fixed", "random", "worstcase" are the possibilities
	private static final int[] KEYS_TO_INSERT = { 40, 20, 80, 100, 40, 20, 120, 60, 50, 10, 200}; // these keys are inserted if KEY_SOURCE is set to "fixed"
	private static final int[] KEYS_TO_DELETE = { 200, 100, 1, 40, 20, 80, 100, 10, 200 }; // these keys are deleted if KEY_SOURCE is set to "fixed"
	private static final int NUM_TO_INSERT = 200000; // number of keys that we attempt to insert when KEY_SOURCE is "random" or "worstcase"
	private static final int NUM_TO_DELETE = NUM_TO_INSERT; // number of keys that we attempt to delete when KEY_SOURCE is "random" or "worstcase"
	private static final int R1 = 10 * NUM_TO_INSERT; // [0,1,...,R1-1] is the range from which a random key is picked and inserted
	private static final int R2 = R1; // [0,1,...,R2-1] is the range from which a random key is picked and deleted

	public static void main(String[] args) {
		MyMapADT map = null;
				
		if (WHICH_IMPLEMENTATION == "ArrayList")
			map = new MyArrayListMap();
		else if (WHICH_IMPLEMENTATION == "Hash")
			map = new MyHashMap();
		else if (WHICH_IMPLEMENTATION == "BST")
			map = new MyBSTMap();
		else
			map = new MyAVLMap();

		long tStart = System.nanoTime(); // start time

		int n1, n2; // number of keys that we'll attempt to insert and later
					// delete
		if (KEY_SOURCE == "fixed") {
			n1 = KEYS_TO_INSERT.length;
			n2 = KEYS_TO_DELETE.length;
		} else {
			n1 = NUM_TO_INSERT;
			n2 = NUM_TO_DELETE;
		}

		// Insertion Phase begins here (wherein n1 inserts are attempted)
		int k;
		Random rand = new Random();
		int nInserted = 0; // number of keys successfully inserted
		for (int i = 0; i < n1; i++) {
			if (KEY_SOURCE == "fixed")
				k = KEYS_TO_INSERT[i];
			else if (KEY_SOURCE == "worstcase")
				k = i;
			else
				k = rand.nextInt(R1);
			if (map.insert(k, k))
				nInserted++;
			if (DEBUG) {
				System.out.println("After inserting key " + k + ", tree is:");
				System.out.println(map);
			}
		}
		System.out.println("***********************************");
		System.out.println("STATS at the end of the Insertion Phase:");
		System.out.println("    Attempted " + n1 + " inserts");
		System.out.println("    Successfully inserted " + nInserted + " keys");
		System.out.println("    Tree size at the moment is " + map.size());
		System.out.println("    At the moment map has " + map.size() + " keys");
		if (map.size() < 20) {
			System.out.println("Map after the insertions: ");
			System.out.println(map);
		}
		else System.out.println("Map is too large to print.");
		System.out.println("***********************************");

		if (PERFORM_DELETIONS) {
			// Deletion Phase begins here (wherein n2 deletes are attempted)
			int nDeletions = 0; // number of keys successfully deleted
			for (int i = 0; i < n2; i++) {
				if (KEY_SOURCE == "fixed")
					k = KEYS_TO_DELETE[i];
				else if (KEY_SOURCE == "worstcase")
					k = i;
				else
					k = rand.nextInt(R2);
				if (map.find(k).found) {
					map.delete(k);
					nDeletions++;
					if (DEBUG) {
						System.out.println("After deleting key " + k + ", tree is:");
						System.out.println(map);
					}
				} else if (DEBUG)
					System.out.println(k + " is not a key");
			}

			System.out.println("***********************************");
			System.out.println("STATS at the end of the Deletion Phase:");
			System.out.println("    Attempted " + n2 + " deletes");
			System.out.println("    Successfully deleted " + nDeletions + " keys");
			System.out.println("    At the moment map has " + map.size() + " keys");
			if (map.size() < 20) {
				System.out.println("Map after the deletions: ");
				System.out.println(map);
			}
			else System.out.println("Map is too large to print.");
			System.out.println("***********************************");
		}

		long tFinish = System.nanoTime();
		System.out.println("It took " + ((tFinish - tStart) / ((long) 1000000)) + " milliseconds ");
		System.out.println("to attempt to insert " + n1 + " keys");
		if (PERFORM_DELETIONS) System.out.println("and attempt to delete " + n2 + " keys");
		System.out.println("using " + WHICH_IMPLEMENTATION + " implementation and a " + KEY_SOURCE + " key source");
	}
}
