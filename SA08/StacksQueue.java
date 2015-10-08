/**
* Implements a queue using two stacks.
* For CS 10 Short Assigment 8
*
* @author Ezekiel Elin, October 8, 2015
*/
public class StacksQueue<T> implements CS10Queue<T> {
    CS10Stack<T> inStack;   // place where enqueued
    CS10Stack<T> outStack;  // place where dequeued

    /**
    * Constructor, makes an empty queue.
    */
    public StacksQueue() {
        inStack = new ArrayListStack<T>();
        outStack  = new ArrayListStack<T>();
    }

    /**
    * Add an element to the rear of the queue.
    * @param obj element to be enqueued
    */
    public void enqueue(T obj) {
        // YOU FILL THIS IN.
        inStack.push(obj);
    }

    /**
    * Remove an element from the front of the queue.
    * @return the element removed from the front of the queue
    */
    public T dequeue() {
        shuffle();
        //Could still be empty, if nothing was in the inStack
        if (outStack.isEmpty()) {
            //Return null, because outStack is empty
            return null;
        } else {
            //Return whatever is at the not-empty outStack
            return outStack.pop();
        }
    }

    /**
    * Return the element at the front of queue, but do not remove it
    * @return the element at the front of the queue
    */
    public T front() {
        shuffle();
        //Could still be empty, if nothing was in the inStack
        if (outStack.isEmpty()) {
            //Return null, because outStack is empty
            return null;
        } else {
            //Return whatever is at the not-empty outStack
            return outStack.peek();
        }
    }

    /**
    * Is the queue empty?
    * @return true if queue is empty, false otherwise
    */
    public boolean isEmpty() {
        return inStack.isEmpty() && outStack.isEmpty();
    }

    /**
    * Shuffle the inStack into the outStack, if needed
    */
    private void shuffle() {
        //Check if we need to shuffle stuff
        if (outStack.isEmpty()) {
            //If it is, grab everything from the inStack, and push it in the outStack
            while (!inStack.isEmpty()) {
                outStack.push(inStack.pop());
            }
        } //else, don't need to do anything right now
    }

    /**
    * A testing program
    */
    public static void main (String [] args)  {
        CS10Queue<String> q = new StacksQueue<String>();
        System.out.println("Is it empty? : " + q.isEmpty());
        q.enqueue("cat");
        q.enqueue("dog");
        q.enqueue("bee");
        System.out.println("Is it empty? : " + q.isEmpty());
        System.out.println("Front is: " + q.dequeue());
        System.out.println("Next front is: " + q.dequeue());
        System.out.println("Is it empty? : " + q.isEmpty());
        q.enqueue("eagle");
        System.out.println("Next front is: " + q.dequeue());
        System.out.println("Next front is: " + q.dequeue());
        System.out.println("Is it empty? : " + q.isEmpty());
        System.out.println("dequeue of empty stack: " + q.dequeue());

        q.enqueue("bear");
        q.enqueue("beaver");
        System.out.println("front is: " + q.dequeue());
        q.enqueue("cat");
        q.enqueue("dog");
        q.enqueue("sheep");
        q.enqueue("cow");
        q.enqueue("eagle");
        q.enqueue("bee");
        q.enqueue("lion");
        q.enqueue("tiger");
        q.enqueue("zebra");
        q.enqueue("ant");

        System.out.println("Bigger example:");
        System.out.println("front is: " + q.dequeue());
        System.out.println("Next front is: " + q.dequeue());
        System.out.println("Next front is: " + q.dequeue());
        System.out.println("Next front is: " + q.dequeue());
        System.out.println("Next front is: " + q.dequeue());
        System.out.println("Next front is: " + q.dequeue());
        System.out.println("Next front is: " + q.dequeue());
        System.out.println("Next front is: " + q.dequeue());
        System.out.println("Next front is: " + q.dequeue());
        System.out.println("Next front is: " + q.dequeue());
        System.out.println("Next front is: " + q.dequeue());
        System.out.println("Is it empty? : " + q.isEmpty());
        System.out.println("Next front is: " + q.dequeue());
    }
}
