/**
* SLL.java
*
* Implementation of singly linked with a sentinel.
* Two instance variables used to maintain a sentinel and a current value.
* The current is tracked via currentPred, which points to the value prior to current
*
* WARNING: This implementation is guaranteed to work only if always given
* immutable objects (or at least ones that do not change).
*
* @author THC
* @author Scot Drysdale converted to Java
* @author Scot Drysdale, THC have made a number of modifications.
* @author Scot Drysdale most recently modified on 1/12/2011
* @author Ezekiel Elin, reworking into a SLL with Sentinel for CS10, Lab 2
*/
public class SmartSentinelSLL<T> implements CS10LinkedList<T> {
    // Instance variables.
    private Element<T> currentPred;    // item in front of the current item
    private Element<T> sentinel;       // head of list

    /**
    * A private class inner representing the elements in the list.
    */
    private static class Element<T> {
        // Because this is a private inner class, these can't be seen from outside SLL.
        private T data;         // reference to data stored in this element
        private Element<T> next;   // reference to next item in list

        /**
        * Constructor for a linked list element, given an object.
        * @param obj the data stored in the element.
        */
        public Element(T obj) {
            next = null;          // no element after this one, yet
            data = obj;           // OK to copy reference, since obj references an immutable object
        }

        /**
        * @return the String representation of a linked list element.
        */
        public String toString() {
            return data.toString();
        }
    }

    /**
    * Constructor to create an empty singly linked list.
    */
    public SmartSentinelSLL() {
        clear();
    }

    /**
    * @see CS10ListADT#clear()
    */
    public void clear() {
        // No elements are in the list, so everything is null.
        sentinel = new Element<T>(null);;
        currentPred = sentinel;
    }

    /**
    * @see CS10ListADT#add()
    */
    public void add(T obj) {
        Element<T> x = new Element<T>(obj);   // allocate a new element

        //Check if there is a current element. Do the magic if there is
        if (hasCurrent()) {
            x.next = currentPred.next.next; //We know currentPred.next exists
            currentPred.next.next = x; //the predecessor of the current is the one we track, so jump two ahead and set X there
            currentPred = currentPred.next; //Set the currentPred to the one after the current currentPred.
        } else { //Otherwise, this should go at the beginning of the list
            x.next = sentinel.next; //can't do currentPred.next.next if currentPred.next is null
            sentinel.next = x; //set X as the element after the sentinel, because the list is empty
            currentPred = sentinel; //make sure currentPred is pointing at the sentinel, which is just prior to our new element
        }
    }

    /**
    *   * @see CS10ListADT#remove()
    */
    public void remove() {

        if (!hasCurrent()) { // check whether current element exists
            System.err.println("No current item");
            return;
        }

        currentPred.next = currentPred.next.next;       // make the successor the current position
        if (currentPred.next == null) currentPred = null; //If there is no current, move currentPred to the sentinel
    }

    /**
    * @return the String representation of this list.
    */
    public String toString() {
        String result = "";

        for (Element<T> x = sentinel.next; x != null; x = x.next)
        result += x.toString() + "\n";

        return result;
    }

    /**
    * @see CS10ListADT#contains()
    */
    public boolean contains(T obj) {
        Element<T> x = sentinel.next; //set x to the first item in the list. MAY be NULL, this is
        Element<T> xPred = sentinel; //set xPred to the sentinel

        while (x != null) {
            if (x.next != null) {
                xPred = x;
                x = x.next;
                if (x.data.equals(obj)) {
                    currentPred = xPred;
                    return true;
                }
            } else break;
        }

        return false;
    }


    /**
    * @see CS10ListADT#isEmpty()
    */
    public boolean isEmpty() {
        return sentinel.next == null;
    }

    /**
    * @see CS10ListADT#hasCurrent()
    */
    public boolean hasCurrent() {
        return !(isEmpty() || currentPred == null || currentPred.next == null);
    }

    /**
    * @see CS10ListADT#hasNext()
    */
    public boolean hasNext() {
        return hasCurrent() && currentPred.next.next != null;
    }

    /**
    * @see CS10ListADT#getFirst()
    */
    public T getFirst() {
        //check that the list isn't empty
        if (isEmpty()) {
            System.err.println("The list is empty");
            return null;
        }
        currentPred = sentinel; //set the "current" to whatever the sentinel precedes (first item)
        return get(); //get it
    }

    /**
    * @see CS10ListADT#getLast()
    */
    public T getLast() {
        if (isEmpty()) {
            System.err.println("The list is empty");
            return null;
        } else {
            while (hasNext()) next(); //Go to the end of the list
            return get(); //get it
        }
    }

    /**
    * @see CS10ListADT#addFirst()
    */
    public void addFirst(T obj) {
        Element<T> x = new Element<T>(obj);   // allocate a new element

        currentPred = null; //set current pred to null, so that
        add(obj); //add adds it to the beginning of the list
    }

    /**
    * @see CS10ListADT#addLast()
    */
    public void addLast(T obj) {
        if(isEmpty())
        addFirst(obj);
        else {
            getLast();
            add(obj);
        }
    }

    /**
    * @see CS10ListADT#get()
    */
    public T get() {
        if (hasCurrent()) {
            return currentPred.next.data;
        }
        else {
            System.err.println("No current item");
            return null;
        }

    }

    /**
    * @see CS10ListADT#set()
    */
    public void set(T obj) {
        if (hasCurrent())
        currentPred.next.data = obj;
        else
        System.err.println("No current item");
    }

    /**
    * @see CS10ListADT#next()
    */
    public T next() {
        if (hasNext()) {
            currentPred = currentPred.next;
            return currentPred.next.data;
        } else {
            System.err.println("No next item");
            return null;
        }
    }
}
