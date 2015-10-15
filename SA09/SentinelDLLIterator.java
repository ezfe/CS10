import java.util.NoSuchElementException;

/**
 *
 * Implementation of doubly linked list with a sentinel plus a ListIterator.
 *
 * There is no current element. The "cursor" position can be thought of as
 * halfway between two elements, the one that would be returned by previous and
 * the one that would be returned by next. Note that calling alternating calls
 * to next and previous keep returning the same element.
 *
 * Remove and set are not defined by cursor position. They operate on the last
 * item obtained by a next or previous operation. If there was not next or
 * previous, or if an add or remove has been done since the last call to next or
 * previous, these methods throw an exception.
 *
 *
 * This implementation includes an iterator for the list. WARNING: This
 * implementation is guaranteed to work only if always given immutable objects.
 *
 * @author THC - original linked list program.
 * @author Scot Drydale = modified to include a ListIterator
 */
public class SentinelDLLIterator<T> implements CS10IteratedList<T> {
  private Element<T> sentinel;  // sentinel, serves as head and tail

  /**
   * A private inner class representing the elements in the list.
   */
  private static class Element<T> {
    // Because this is a private inner class, these can be seen from
    // SentinelDLLIterator, but not from outside SentinelDLLIterator.
    private T data;               // reference to data stored in this element
    private Element<T> next;      // reference to next item in list
    private Element<T> previous;  // reference to previous item in list

    /**
     * Constructor for a linked list element, given an object.
     *
     * @param obj the data in the element.
     */
    public Element(T obj) {
      next = previous = null; // no element before or after this one, yet
      data = obj; // OK to copy reference, since obj references an immutable object
    }

    /**
     * @return the String representation of a linked list element.
     */
    public String toString() {
      return data.toString();
    }
  }

  /**
   * Constructor for an empty, circular, doubly linked list with a sentinel.
   */
  public SentinelDLLIterator() {
    // Allocate the sentinel with a null reference.
    sentinel = new Element<T>(null);
    clear();
  }

  /*
   * @see CS10LinkedList#clear()
   */
  public void clear() {
    // Make the list be empty by having the sentinel point to itself
    // in both directions.
    sentinel.next = sentinel.previous = sentinel;
  }

  /**
   * Just for fun, we use a ListIterator supplied by this class to convert the
   * list to a string.
   *
   * @return the String representation of this list.
   */
  public String toString() {
    String result = "";

    CS10ListIterator<T> iter = listIterator();

    while (iter.hasNext())
      result += iter.next().toString() + "\n";

    return result;
  }

  /**
   * @see CS10LinkedList#isEmpty()
   */
  public boolean isEmpty() {
    return sentinel.next == sentinel;
  }

  /**
   * Return a new CS10ListIterator.
   *
   * @return a ListIterator
   */
  public CS10ListIterator<T> listIterator() {
    return new DLLIterator();
  }

  /**
   * @see CS10LinkedList#addFirst()
   */
  public void addFirst(T obj) {
    listIterator().add(obj);
  }

  /**
   * @see CS10LinkedList#addLast()
   */
  public void addLast(T obj) {
    DLLIterator iter = (DLLIterator) listIterator();
    iter.last();
    iter.add(obj);
  }

  /**
   * @see CS10LinkedList#contains()
   */
  public boolean contains(T obj) {
    Element<T> x;

    // Since we have a sentinel that isn't being used for a "real" element,
    // we put the object we are looking for in the sentinel. That way, we
    // know we'll find it some time during the search.
    sentinel.data = obj;

    for (x = sentinel.next; !x.data.equals(obj); x = x.next)
      ;

    // We dropped out of the loop because we found the target object in an
    // element that x references.
    // If we found it in the sentinel, it wasn't really in the list.
    // If we found it before getting back to the sentinel, it was in the list.

    // Put the sentinel back into its null state.
    sentinel.data = null;

    return x != sentinel;
  }

  /**
   * Inner class for an iterator.  This class uses the generic type T from the
   * outer class.
   */
  private class DLLIterator implements CS10ListIterator<T> {
    // The cursor is halfway between current and current.next.
    private Element<T> current;

    // Keep track of the last item returned by a call to next or previous.
    // When null, cannot call remove or set.
    private Element<T> lastReturned;

    /**
     * Initializes a DLLIterator object.
     */
    private DLLIterator() {
      current = sentinel;
      lastReturned = null;
    }

    /**
     * @see CS10LinkedList#hasNext
     */
    public boolean hasNext() {
      return current.next != sentinel;
    }

    /**
     * @see CS10LinkedListIterator#hasPrevious
     */
    public boolean hasPrevious() {
      return current != sentinel;
    }

    /**
     * Advance the cursor by one position and return the next element.
     *
     * @return the next element, or null if no next element
     */
    public T next() {
      if (hasNext()) {
        current = current.next;
        lastReturned = current;
        return lastReturned.data;
      }
      else
        return null;
    }

    /**
     * Move the cursor backward by one position and return the previous element.
     *
     * @return the previous element, or null if no previous element
     */
    public T previous() {
      if (hasPrevious()) {
        lastReturned = current;
        current = current.previous;
        return lastReturned.data;
      }
      else
        return null;
    }

    /**
     * Remove the element most recently returned by a previous or next.  Leaves
     * the cursor unmoved. Error if there was no call to previous or
     * next or if a remove or add has occurred since the most recent call to
     * previous or next.
     */
    public void remove() {
      if (lastReturned != null) {
        // Splice out the most recently returned element.
        lastReturned.previous.next = lastReturned.next;
        lastReturned.next.previous = lastReturned.previous;

        if (current == lastReturned)  // was last call to next?
          current = current.previous; // if so, back up current to keep cursor unmoved
        lastReturned = null;          // updated list, so no longer valid
      }
      else
        System.err.println("No current item");
    }

    /**
     * Inserts a new element with given object reference, before the cursor
     * position.  A call to next is unaffected, and previous returns the new
     * item.
     *
     * @param obj the element to insert
     */
    public void add(T obj) {
      Element<T> x = new Element<T>(obj); // allocate a new element

      // Splice in the new element.
      x.next = current.next;
      x.previous = current;
      current.next.previous = x;
      current.next = x;

      current = x;
      lastReturned = null;  // updated list, so no longer valid
    }

    /**
     * Returns whether two ListIterators are equal. They are if they're both
     * SentinelDLLIterators that are looking at the same Element.
     *
     * @param obj the other ListIterator
     */
    public boolean equals(Object obj) {
      if (obj instanceof SentinelDLLIterator.DLLIterator)
        return (current == ((DLLIterator) obj).current);
      else
        return false;
    }

    /**
     * Set the data in the Element whose data was most recently returned by
     * next or previous to obj.  Error if there was no call to
     * previous or next or if a remove or add has occurred since the most recent
     * call to previous or next.
     *
     * @param obj data to set in the current Element
     */
    public void set(T obj) {
      if (lastReturned != null)
        lastReturned.data = obj;
      else
        System.err.println("No current item");
    }

    /**
     * Make the current element be the last one in the list.
     */
    public void last() {
      current = sentinel.previous;
    }
  }
}
