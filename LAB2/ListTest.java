/**
 * ListTest.java
 *
 * An interactive driver program to test list functions.
 * This program is good to run with the debugger.
 *
 * Based on earlier demos by THC, Scot Drysdale, and others.
 * Modified by Scot Drysdale on 11/16/04 to make methods closer to those in Java's
 *   LinkedList class.
 *
 * @author Scot Drysdale
 * @author Prasad Jayanti: changed the interface name from CS10LinkedList to CS10ListADT, April 11 2012
 */
import java.util.Scanner;

public class ListTest {
  public static void main(String args[]) {
    // Create a list to play with, initially empty.
    CS10LinkedList<String> theList = new SmartSentinelSLL<String>();

    char command;           // a command
    String name;            // a name
    Scanner input = new Scanner(System.in);

    do {
      System.out.print("Command (q, C, a, f, l, c, r, p, h, t, n, H, g, s, ?): ");
      command = input.nextLine().charAt(0);

      switch (command) {
        case 'q':                   // Quit
          System.out.println("Bye!");
          break;

        case 'C':                   // Clear
          theList.clear();
          break;

        case 'a':                   // Add
          System.out.print("Enter name: ");
          name = input.nextLine();
          theList.add(name);
          break;

        case 'f':                   // Add first
          System.out.print("Enter name: ");
          name = input.nextLine();
          theList.addFirst(name);
          break;

        case 'l':                   // Add last
          System.out.print("Enter name: ");
          name = input.nextLine();
          theList.addLast(name);
          break;

        case 'c':                   // Contains
          System.out.print("Enter name: ");
          name = input.nextLine();
          if (theList.contains(name))
            System.out.println("Found " + name);
          else
            System.out.println("Didn't find " + name);
          break;

        case 'r':                   // Remove
          theList.remove();
          break;

        case 'p':                   // Print
          if (theList.isEmpty())
            System.out.println("List is empty");
          else
            System.out.print(theList);
          break;

        case 'h':                   // Head
          System.out.println(theList.getFirst());
          break;

        case 't':                   // Tail
          System.out.println(theList.getLast());
          break;

        case 'n':                     // Next
          System.out.println(theList.next());
          break;

        case 'H':                     // Has Next?
          if (theList.hasNext())
            System.out.println("The list has a next element");
          else
            System.out.println("The list does not have a next element");
          break;

        case 'g':                     // Get Current
          System.out.println(theList.get());
          break;

        case 's':                   // Set
            System.out.print("Enter name: ");
            name = input.nextLine();
            theList.set(name);
            break;

        case '?':                   // Print all the commands
          System.out.println("Commands are\n  q: quit\n  C: clear\n  a: add\n  " +
              "f: addFirst\n  l: addLast\n  c: contains\n  r: remove\n  p: print\n  " +
              "h: head\n  t: tail\n  n: next\n  H: has next\n  " +
              "g: get current\n  s: set current\n  ?: print this command list\n");
          break;

        default:
          System.out.println("Huh?");
      }
     }
    while (command != 'q');
  }
}
