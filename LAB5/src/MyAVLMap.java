/**
 * MyAVLMap.java
 * @author Ezekiel Elin
 * Implements MyMapADT as an AVL Tree
 */

public class MyAVLMap extends MyBSTMap implements MyMapADT {

	/**
	 * See MyMapADT.java for details
	 */
	@Override
	protected InsertData insert(int k, int v, boolean updateHeights) {
		//Insert the key/value
		//But DO NOT update the heights, we need to do that here
		InsertData insertData = super.insert(k, v, false);
		
		//Get the inserted node's parent
		Node z = insertData.getInserted().parent;
	
		while (z != sentinel) {
			if (z.hasCorrectHeight()) {
				//Z has the correct height, nothing needs to be changed
				break;
			} else {
				//Z doesn't have the right height
				//So we update the height
				z.updateHeight();
				if (z.isBalanced()) {
					//Z is balanced, so we go on up the tree
					z = z.parent;
				} else {
					//We need to balance Z
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
		//Get the nodes that concern us
		Node y = z.heaviestChild();
		Node x = y.heaviestChild();
		
		//Figure out which methods we need to use
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
		//Get the right child of z
		Node y = z.getRight();
		
		//Update the parent/child of y
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
		
		//Set the right child of z to y's left child
		z.setRight(y.getLeft());
		
		//Set the left child of y to z
		y.setLeft(z);
	}
	
	public void rightRotate(Node z) {
		//Get the left child of z
		Node y = z.getLeft();
		
		//Update the parent/child relationship of y
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
		
		//Set the left child of z to y's right child
		z.setLeft(y.getRight());
		
		//Set the right child of y to z
		y.setRight(z);
	}
}
