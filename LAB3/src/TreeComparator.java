import java.util.Comparator;

/**
 * @author Ezekiel Elin
 *
 */

public class TreeComparator implements Comparator {

	public int compare(Object o1, Object o2) {
		if (o1 instanceof BinaryTree && o2 instanceof BinaryTree) {
			BinaryTree<CharacterFrequencyStore> bt1 = (BinaryTree<CharacterFrequencyStore>)o1;
			BinaryTree<CharacterFrequencyStore> bt2 = (BinaryTree<CharacterFrequencyStore>)o2;

			if (bt1.getValue().frequency > bt2.getValue().frequency)		return -1;
			else if (bt1.getValue().frequency < bt2.getValue().frequency)	return 1;
			else 															return 0;
		} else {
			System.out.println("Faulty comparison");
			return 0;
		}
	}
}
