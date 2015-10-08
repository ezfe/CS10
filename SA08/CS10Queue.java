/**
 * An interface for a Queue ADT
 *
 * The "CS10" is to avoid conflicting with the Queue interface
 * in the Java library.
 * @author Scot Drysdale
 */
public interface CS10Queue<T> {

  /**
   * Add an element to the rear of the queue.
   * @param obj element to be enqueued
   */
  public void enqueue(T obj);

  /**
   * Remove an element from the front of the queue.
   * @return the element removed from the front of the queue
   */
  public T dequeue();

  /**
   * Return the element at the front of queue, but do not remove it
   * @return the element at the front of the queue
   */
  public T front();

  /**
   * Is the queue empty?
   * @return true if queue is empty, false otherwise
   */
  public boolean isEmpty();
}
