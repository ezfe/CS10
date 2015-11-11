
public class MyBSTMap implements MyMapADT {

	private Node root;
	private Node sentinel; //TODO: Sentinel?
	
	public MyBSTMap() {
		this.sentinel = new Node(null, null);
		this.root = sentinel;
	}
	
	@Override
	public boolean insert(int k, int v) {
		Node inserted = new Node(k, v);
		Node node = root;
		Node nodeParent = root.parent;
		
		while (node != sentinel) {
			if (k < node.key) {
				node = node.left;
			} else {
				node = node.right;
			}
		}
		
//		System.out.println(node);
		
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
		
		private Integer key;
		private Integer value;
		
		protected Node left, right;
		protected Node parent;
		
		public Node(Integer key, Integer value) {
			this.key = key;
			this.value = value;
			
			parent = sentinel;
			left = sentinel;
			right = sentinel;
		}
		
		public Integer getKey() {
			return this.key;
		}
		
		public Integer getValue() {
			return this.value;
		}
		
		public void setValue(Integer value) {
			this.value = value;
		}
	}
	
	/**
	 * This method is not implemeneted
	 * per the project requirements
	 */
	@Override
	public RetVal delete(int k) { return null; }
}
