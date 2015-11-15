
public class MyAVLMap extends MyBSTMap implements MyMapADT {

	@Override
	public boolean insert(int k, int v) {
		return p_insert(k, v, true).didCreate();
	}
	
	/**
	 * See MyMapADT.java for details
	 */
	@Override
	protected InsertData p_insert(int k, int v, boolean updateHeights) {
		InsertData insertData = super.p_insert(k, v, false);
		
		Node z = insertData.getInserted().parent;
				
		while (z != sentinel) {
			if (z.hasCorrectHeight()) {
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
		Node y = z.heaviestChild();
		Node x = y.heaviestChild();
		if (y == z.getRight() && x == y.getRight()) {
			leftRotate(z);
		} else if (y == z.getRight() && x == y.getLeft()) {
			rightRotate(y);
			leftRotate(z);
		} else if (y == z.getLeft() && x == y.getLeft()) {
			rightRotate(z);
		} else {
			leftRotate(y);
			rightRotate(z);
		}
	}
	
	
	public void leftRotate(Node z) {
		Node y = z.getRight();
		
		if (z.parent == sentinel) {
			y.parent = sentinel;
			root = y;
		} else {
			if (z.parent.getRight() == z)
				//Parent's right child is z
				z.parent.setRight(y);
			else
				//Parent's left child is z
				z.parent.setLeft(y);
		}
		
		z.setRight(y.getLeft());
		
		y.setLeft(z);
	}
	
	public void rightRotate(Node z) {
		Node y = z.getLeft();
		
		if (z.parent == sentinel) {
			y.parent = sentinel;
			root = y;
		} else {
			if (z.parent.getRight() == z)
				//Parent's right child is z
				z.parent.setRight(y);
			else
				//Parent's left child is z
				z.parent.setLeft(y);
		}
		
		z.setLeft(y.getRight());
		
		y.setRight(z);
	}
}
