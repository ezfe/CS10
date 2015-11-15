
public class MyAVLMap extends MyBSTMap implements MyMapADT {

	/**
	 * See MyMapADT.java for details
	 */
	@Override
	protected InsertData p_insert(int k, int v, boolean updateHeights) {
		InsertData insertData = super.p_insert(k, v, false);

		System.out.println(this.root);
		
		Node z = insertData.getInserted();
		
		while (z != sentinel) {
			if (z.hasCorrectHeight()) {
				System.out.println("Reached " + z);
				break;
			} else {
				z.updateHeight();
				if (z.isBalanced()) {
					z = z.parent;
				} else {
					balance(z);
				}
			}
		}
		
		return insertData;
	}
	
	/**
	 * Balances the tree around Node z
	 * @param z node to balance around
	 */
	private void balance(Node z) {
		
	}
	
	public void leftRotate(Node a) {
		Node b = a.getRight();
	}
}
