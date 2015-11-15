
public class MyBSTMap implements MyMapADT {

	protected Node root;
	protected Node sentinel;

	public MyBSTMap() {
		this.sentinel = new Node();
		this.root = sentinel;
	}

	/**
	 * See MyMapADT.java for details
	 * Citation:
	 * 	This method is derived from / based on the lecture notes' implementation of a Binary Search Tree
	 */
	@Override
	public boolean insert(int k, int v) {
		return p_insert(k, v, true).didCreate();
	}
	
	protected InsertData p_insert(int k, int v, boolean updateHeights) {
		//Keep references to the working node
		Node node = root;
		//And it's parent
		//- We cannot use `node.parent`, because
		//  if node becomes sentinel, the parent reference
		//  will be null, which is useless
		Node nodeParent = sentinel;

		//Continue until node is the sentinel
		//When this condition is true, nodeParent will be
		//The leaf that references the sentinel
		while (node != sentinel) {
			//Set the parent to the current node
			nodeParent = node;
			if (k < node.key) {
				//`k` should go to the left of `node`
				node = node.getLeft();
			} else if (k > node.key) {
				//`k` should go to the right of `node`
				node = node.getRight();
			} else if (k == node.key) {
				//The node already exists, so we simply update its value
				node.value = v;
				//Return false because the node already exists
				return new InsertData(false, node);
			}
		}

		//Create the new node
		Node inserted = new Node(k, v);

		//If the prent is the sentinel, then the tree hasn't been constructed yet
		if (nodeParent == sentinel) {
			//Set the root to inserted (would have been sentinel previously)
			root = inserted;
			//Return true because we have just inserted a new node
			return new InsertData(true, inserted);
		} else {
			//node is sentinel
			//nodeParent is one above that

			if (k < nodeParent.key) {
				//`k` goes left of `nodeParent`

				//Set the left child of nodeParent to inserted
				if (updateHeights)
					nodeParent.setLeft(inserted);
				else
					nodeParent.setLeftWithNoParentHeightModification(inserted);
				//Return true because we have just inserted a new node
				return new InsertData(true, inserted);
			} else {
				//`k` goes right of `nodeParent`

				//Set the right child of nodeParent to inserted
				if (updateHeights)
					nodeParent.setRight(inserted);
				else
					nodeParent.setRightWithNoParentHeightModification(inserted);
				//Return true because we have just inserted a new node
				return new InsertData(true, inserted);
			}
		}
	}

	/**
	 * See MyMapADT.java for details
	 */
	@Override
	public RetVal find(int k) {
		Node node = root;

		//Traverse the tree
		while (node != sentinel && node.key != k) {
			//Going left and right as appropriate
			if (k < node.key) {
				node = node.left;
			} else {
				node = node.right;
			}
		}

		if (sentinel == node) {
			//the node is the sentinel, we reached the end without finding it
			return new RetVal(false, 0);
		} else {
			//We found the node
			return new RetVal(true, node.value);
		}
	}

	/**
	 * See MyMapADT.java for details
	 */
	@Override
	public int size() {
		//Return the size of the tree referenced by root
		return this.root.size();
	}

	/**
	 * Stores an instance of a node in the tree
	 * @author Ezekiel Elin
	 */
	protected class Node {
		/*
		 * Getting sick and tired of java's lousy access
		 * control. I apparently got too used to Swift
		 */

		//Stores the key and relavent value associated with the node
		//Uses Integer instead of int to facilitate creation of sentinel node
		protected Integer key;
		protected Integer value;

		//Keep references to left, right, and parent nodes
		private Node left, right;
		protected Node parent;

		//Height of the node
		protected int height;

		/**
		 * Create a sentinel node
		 */
		public Node() {
			//Set everything to null
			this.key = this.value = null;
			this.parent = null;
			this.left = null;
			this.right = null;

			//Sentinels have a height of -1
			this.height = -1;
		}

		/**
		 * Create a node
		 * @param key for the new node
		 * @param value for the new node
		 */
		public Node(int key, int value) {
			this.key = key;
			this.value = value;

			this.height = 0;

			//Parent, left, and right are all sentinel by default
			//Caller must set these if they wish to change them
			this.parent = sentinel;
			this.left = sentinel;
			this.right = sentinel;
		}

		/**
		 * Get the size of the node
		 * @return the size of the node
		 */
		public int size() {
			if (this == sentinel)
				//Sentinel has a size of zero, because it doesn't count
				return 0;

			//Return the sum of the left and right sides
			return this.getRight().size() + this.getLeft().size() + 1;
		}

		/**
		 * Update the heights of this node's parent
		 */
		public void updateParentHeights() {
			if (!this.parent.hasCorrectHeight()) {
				//The parent is not a sentinel, and its height needs to be changed (<=)
				this.parent.height = this.height + 1;
				//Now we tell the parent to do the same for its parents (if needed)
				this.parent.updateParentHeights();
			}
		}
		
		/**
		 * Updates the height of this Node
		 */
		public void updateHeight() {
			this.height = Math.max(this.getRight().height, this.getLeft().height) + 1;
		}

		/**
		 * Check if this node's height is correct
		 * @return
		 */
		public boolean hasCorrectHeight() {
			if (this == sentinel)
				return true;
			return Math.max(this.getRight().height, this.getLeft().height) + 1 == this.height;
		}
		
		/**
		 * Checks if THIS node is balanced
		 * Does not check down the tree
		 * @return node is balanced
		 */
		public boolean isBalanced() {
			return Math.abs(this.getRight().height - this.getLeft().height) <= 1;
		}
		
		public Node heaviestChild() {
			if (this.getRight().height > this.getLeft().height) {
				return this.getRight();
			} else if (this.getLeft().height > this.getRight().height) {
				return this.getLeft();
			} else {
				return null;
			}
		}
		
		/**
		 * Sets the node's right child
		 * Does NOT modifiy the parent's height. The caller MUST do that
		 * @param r node to set as the right child
		 */
		public void setRightWithNoParentHeightModification(Node r) {
			this.right = r;
			r.parent = this;
		}

		/**
		 * Sets the node's left child
		 * Does NOT modifiy the parent's height. The caller MUST do that
		 * @param l node to set as the left child
		 */
		public void setLeftWithNoParentHeightModification(Node l) {
			this.left = l;
			l.parent = this;
		}
		
		/**
		 * Sets the node's right child
		 * Will update parent links. Do NOT use if
		 * control over those links is needed
		 * @param r node to set as the right child
		 */
		public void setRight(Node r) {
			if (r == null) return;

			this.setRightWithNoParentHeightModification(r);
			r.parent = this;
			r.updateParentHeights();
		}

		/**
		 * Sets the node's left child
		 * Will update parent links. Do NOT use if
		 * control over those links is needed
		 * @param l node to set as the left child
		 */
		public void setLeft(Node l) {
			if (l == null) return;

			this.setLeftWithNoParentHeightModification(l);
			l.parent = this;
			l.updateParentHeights();
		}

		/**
		 * Get the right child
		 * @return the right child node
		 */
		public Node getRight() {
			return this.right;
		}

		/**
		 * Get the left child
		 * @return the left child node
		 */
		public Node getLeft() {
			return this.left;
		}
		
		/**
		 * Convert this node to a string
		 * Current includes the height as a third value printed
		 * As such:
		 * 	{K, V (H)}
		 */
		public String toString() {
			return "{" + this.key + ", " + this.value + " (" + this.height + ")}";
		}
	}

	/**
	 * This method is not implemeneted
	 * per the project requirements
	 */
	@Override
	public RetVal delete(int k) {
		System.err.println(">>Unimplemented method called<<\nplease remove calls of BST/AVL .delete()"); 
		return null;
	}


	/*
	 * All printing methods (toString, indent, and print) are sourced from the lecture notes'
	 * implementation of a binary search tree.
	 * 
	 * Minor modifications were made to support additional requirements in Lab 5:
	 * 	printing tree size
	 */

	/**
	 * Return a String representation of this BST, indenting each level by two
	 * spaces. Right subtrees appear before subtree roots, which appear before
	 * left subtrees, so that when viewed sideways, we see the BST structure.
	 */
	public String toString() {
		int size = this.size();
		if (root == sentinel)
			return "Size: " + size;
		else
			return "Size: " + size + "\n" + print(root, 0);
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
			return print(x.getRight(), depth + 1) + indent(depth) + x.toString() + "\n"
			+ print(x.left, depth + 1);
	}

	protected class InsertData {
		private boolean created;
		private Node inserted;
		public InsertData(boolean c, Node i) {
			this.created = c;
			this.inserted = i;
		}
		public boolean didCreate() {
			return this.created;
		}
		public Node getInserted() {
			return this.inserted;
		}
	}
}
