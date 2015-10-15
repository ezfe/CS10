/**
 * Interface defining methods for a linked list iterator.
 * @author Scot Drysdale
 */

import java.util.Iterator;

public interface CS10ListIterator<T> extends Iterator<T>
{
  /**
   * Inserts a new element with given object reference, after the current position.
   * Makes the new element the current position.
   * @param obj the object to be added
   */
  public void add(T obj);

  /**
   * Returns the previous element in the iteration, updating current position.
   * @return the previous element in the iteration
   */
  public T previous();

  /**
   * Is there a previous element?
   * @return true if there is a previous element to return, false otherwise
   */
  public boolean hasPrevious();

  /**
   * Sets the element last returned by next or previous to obj
   * @param obj the new value for the item. */
  public void set(T obj);
}
