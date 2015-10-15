/** @author Prasad Jayanti
* October 12, 2015
* SA9.java
*
* Private methods written by Ezekiel Elin, October 12, 2015
*
* Write the bodies of the three private methods to practice the use of iterators.
*/

import java.util.Random;


public class SA9 {
    private static final int SIZE = 20; // length of the list we will construct

    public static  void main(String[] args) {
        CS10IteratedList<Integer> lst = new SentinelDLLIterator<Integer>(); // the list we will construct


        // add random integers to the list
        Random r = new Random();
        for (int i=0; i < SIZE; i++) {
            lst.addFirst(r.nextInt(20)); // random integer between 0 and 19
        }

        print(lst);  // print all numbers in the list in order
        removeOdd(lst); // remove all odd numbers in the list
        System.out.println("---");
        print(lst); // print all elements that are in the list now
        System.out.println("---");
        printReverse(lst); // print all elements, but in the reverse order
    }

    // print all numbers in the list l in order
    private static void print(CS10IteratedList<Integer> l) {
        CS10ListIterator<Integer> iterator = l.listIterator();
        while (iterator.hasNext()) System.out.println(iterator.next()); //Loop through the list
    }

    // remove all odd numbers in the list l
    private static void removeOdd(CS10IteratedList<Integer> l) {
        CS10ListIterator<Integer> iterator = l.listIterator();
        while (iterator.hasNext()) if (iterator.next() % 2 != 0) iterator.remove(); //Loop through the list, and remove odd numbers
    }

    // print all numbers in the list l in reverse order
    private static void printReverse(CS10IteratedList<Integer> l) {
        CS10ListIterator<Integer> iterator = l.listIterator();
        while (iterator.hasNext()) iterator.next(); //Iterate to the end of the list
        while (iterator.hasPrevious()) System.out.println(iterator.previous()); //Loop through, backwards
    }

}
