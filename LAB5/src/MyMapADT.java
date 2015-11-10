/*
 * Prasad Jayanti		November 6, 2015
 * Purpose: Map ADT where key and value are integers (for Lab 5 of CS 10)
 */

public interface MyMapADT {
	// if key k is in the map, change its associated value to v and return false;
	// otherwise insert key k with v as its associated value and return true.
	public boolean insert(int k, int v);
	
	// if key k is the object with an associated value of v, return (true, v);
	// otherwise return (false, 0)
	public RetVal find(int k);
	
	// if key k is not in the object, return (false, 0);
	// otherwise, remove that key (along with its associated value v) and return (true, v)
	public RetVal delete(int k);
	
	// return the number of keys in the object
	public int size();
	
	// return a good string representation of the object that you can print
	// to figure out whether inserts and deletes are working properly
	public String toString();
}
