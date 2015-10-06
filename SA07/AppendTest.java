/**
 * AppendTest.java
 * Tests the append method of SentinelDLL.
 *
 * @author Tom Cormen
 */

public class AppendTest {
  public static void main(String[] args) {
    SentinelDLL<String> mammals = new SentinelDLL<String>();
    SentinelDLL<String> primates = new SentinelDLL<String>();

    mammals.addLast("antelope");
    mammals.addLast("bear");
    mammals.addLast("cougar");
    primates.addLast("baboon");
    primates.addLast("gorilla");
    primates.addLast("orangutan");

    System.out.print("mammals before: ");
    printListForward(mammals);
    System.out.print("primates before: ");
    printListForward(primates);

    mammals.append(primates);

    System.out.print("mammals after: ");
    printListForward(mammals);
    System.out.print("primates after: ");
    printListForward(primates);
    System.out.print("mammals after (backward): ");
    printListBackward(mammals);
    System.out.print("primates after (backward): ");
    printListBackward(primates);
  }

  private static void printListForward(SentinelDLL<String> list) {
    for (String s = list.getFirst(); s != null; s = list.next())
      System.out.print(s + " ");
    System.out.println();
  }

  private static void printListBackward(SentinelDLL<String> list) {
    for (String s = list.getLast(); s != null; s = list.previous())
      System.out.print(s + " ");
    System.out.println();
  }
}
