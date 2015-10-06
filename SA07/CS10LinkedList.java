/**
 * CS10LinkedList.java
 * 
 * Interface defining methods supported by linked lists.
 * 
 * @author Scot Drysdale
 * @author Tom Cormen
 */
public interface CS10LinkedList<T> {
  /**
   * Inserts a new element with given object reference, after the current position. 
   * If there is no current element, inserts at the front of the list.
   * Makes the new element the current position.
   * 
   * @param obj the object to be inserted.
   */
  public void add(T obj);

  /**
   * Removes the current element.  Makes its successor the current position.
   * No current item if the last element is removed.
   * Error if no current item.
   */
  public void remove();

  /**
   * Finds if an object is within a linked list.
   * If found, sets current to be the element containing the object.
   * If not found, leaves current alone.
   * 
   * @param obj the object to search for
   * @return true it was found, false otherwise.
   */
  public boolean contains(T obj);

  /**
   * @return true iff the list is empty.
   */
  public boolean isEmpty();
  
  /**
   * @return true if there is a valid current
   */
  public boolean hasCurrent();

  /** 
   * @return true if there is a next item (current item is not the last item).
   */
  public boolean hasNext();
  
  /**
   * Makes the current element be the head of the list.
   * @return the object in the head of the list, null if list is empty
   */
  public T getFirst();

  /**
   * Makes the current element be the tail of the list.
   * @return the object in the tail of the list, null if list is empty.
   */
  public T getLast();

  /**
   * Inserts a new element at the head of a linked list.
   * Makes it the current item.
   * @param obj the element to insert
   */
  public void addFirst(T obj);

  /**
   * Inserts a new element at the tail of a linked list.
   * Makes it the current item.
   * @param obj the element to insert
   */
  public void addLast(T obj);
  
  /**
   * Removes all elements from this list.
   */
  public void clear();
  
  /**
   * @return the current item, null if no current item.
   */
  public T get();
  
  /**
   * Sets the current item's data to obj.  
   * Error if there is no current item.
   * 
   * @param obj the new value for current item.
   */
  public void set(T obj);
  
  /**
   * Moves current to the next position.
   * @return the data in the new current, null if no next item.
   */
  public T next();
}