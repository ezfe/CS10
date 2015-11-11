
public class MyBSTMap implements MyMapADT {

	private Node root;
	private Node sentinel; //TODO: Sentinel?

	public MyBSTMap() {
		this.sentinel = new Node();
		this.root = sentinel;
	}

	@Override
	public boolean insert(int k, int v) {
		Node inserted = new Node(k, v);

		Node node = root;
		Node nodeParent = sentinel;

		if (nodeParent == sentinel) {
			root = inserted;
		} else {
			while (node != sentinel) {
				if (k < node.key) {
					node = node.left;
				} else {
					node = node.right;
				}
			}

			//node = sentinel
			//nodeParent is one above that
			if (k < nodeParent.key) {
				nodeParent.left = inserted;
			} else {
				nodeParent.right = inserted;
			}
		}

		return false;
	}

	@Override
	public RetVal find(int k) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	private class Node {
		//TODO: Protected??

		/*
		 * Getting sick and tired of java's lousy access
		 * control. I apparently got too used to Swift
		 */
		protected Integer key;
		protected Integer value;

		protected Node left, right;
		protected Node parent;

		protected int height;

		public Node() {
			this.key = this.value = null;
			this.parent = null;
			this.left = null;
			this.right = null;

			this.height = -1;
		}

		public Node(int key, int value) {
			this.key = key;
			this.value = value;

			this.height = 0;
			this.parent = sentinel;
			this.left = sentinel;
			this.right = sentinel;
		}
	}

	/**
	 * This method is not implemeneted
	 * per the project requirements
	 */
	@Override
	public RetVal delete(int k) { return null; }

	/*
	 * Following methods taken from lecture notes
	 */

	/**
	 * Return a String representation of this BST, indenting each level by two
	 * spaces. Right subtrees appear before subtree roots, which appear before
	 * left subtrees, so that when viewed sideways, we see the BST structure.
	 */
	public String toString() {
		if (root == sentinel)
			return "";
		else
			return print(root, 0);
	}

	/**
	 * Return a string of 2*s spaces, for indenting.
	 */
	private String indent(int s) {
		String result = "";
		for (int i = 0; i < s; i++)
			result += "  ";
		return result;
	}

	/**
	 * Return a String representing the subtree rooted at a node.
	 * 
	 * @param x the root of the subtree
	 * @param depth the depth of x in the BST
	 * @return the String representation of the subtree rooted at x
	 */
	private String print(Node x, int depth) {
		if (x == sentinel)
			return "";
		else
			return print(x.right, depth + 1) + indent(depth) + x.toString() + "\n"
			+ print(x.left, depth + 1);
	}

}
