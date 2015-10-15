/**
 * CS10IteratedList.java
 * Interface defining methods supported by linked lists with iterators.
 *
 * @author Scot Drysdale
 */

public interface CS10IteratedList<T> {
  /**
   * Is the list empty?
   * @return true if the list is empty, false otherwise
   */
  public boolean isEmpty();

  /**
   * Insert a new element at the head of the list.
   * @param obj the element to insert
   */
  public void addFirst(T obj);

  /**
   * Insert a new element at the tail of the list.
   * @param obj the element to insert
   */
  public void addLast(T obj);

 /**
  * Find an object within a linked list.
  * There is no current to set.
  * @param obj the object searched for
  * @return true if the object is found, false otherwise
  */
  public boolean contains(T obj);

  /**
   * Create an iterator for a forward iteration.
   * @return the iterator, starting an iteration
   */
  public CS10ListIterator<T> listIterator();
}
