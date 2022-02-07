package RBT;

public class RBT<K extends Comparable<K>, V>{
	private Node root;
	private final int BLACK = 0;
	private final int RED = 1;
	
	private class Node{
		private K key;
		private V value;
		private int color;
		private int height;
		private int size;
		private Node left;
		private Node right;
		
		private Node(K key, V value) {
			this.key = key;
			this.value = value;
			color = RED;
			height = 0;
			size = 1;
			left = null;
			right = null;			
		}
	}
	
	public int height() {
		return height(root);
	}
	private int height(Node root) {
		return(root == null ? 0 : root.height);
	}
	
	public int size() {
		return size(root);
	}
	private int size(Node root) {
		return(root == null ? 0 : root.size);
	}
	
	private int color(Node root) {
		return(root == null ? BLACK : root.color);
	}
	
	public boolean isEmpty() {
		return root == null;
	}
	
	public V get(K key) {
		if(key == null)
			throw new IllegalArgumentException("argument to get() is null");
		Node res = get(root, key);
		return(res == null ? null : res.value);
	}
	
	private Node get(Node root, K key) {
		if(root == null)
			return null;
		
		int cmp = key.compareTo(root.key);
		
		if(cmp < 0) 
			return get(root.left, key);
		else if(cmp > 0)
			return get(root.right, key);
		return root;
	}
	
	public void put(K key, V value) {
		if(key == null)
			throw new IllegalArgumentException("argument to get() is null");
		
		if(root != null)
			root.color = RED;
		root = put(root, key, value);
		root.color = BLACK;
	}
	
	private Node put(Node root, K key, V value) {
		if(root == null)
			return new Node(key,value);
		
		int cmp = key.compareTo(root.key);
		
		if(cmp < 0) 
			root.left = put(root.left, key, value);
		else if(cmp > 0)
			root.right = put(root.right, key, value);
		else
			root.value = value;
		
		if(color(root.left) == BLACK && color(root.right) == RED) 
			root = leftRotate(root);
		if(color(root.left) == RED && color(root.left.left) == RED)
			root = rightRotate(root);
		if(color(root.left) == RED && color(root.right) == RED)
			root = flipColor(root);
		
		root.size = 1 + size(root.left) + size(root.right);
		root.height = 1 + Math.max(height(root.left), height(root.right));
		
		return root;
	}
	
	private Node leftRotate(Node root) {
		Node temp = root.right;
		root.right = temp.left;
		temp.left = root;
		
		temp.color = root.color;
		root.color = RED;
		
		temp.size = root.size;
		root.size = 1 + size(root.left) + size(root.right);
		root.height = 1 + Math.max(height(root.left), height(root.right));
		temp.height = 1 + Math.max(height(temp.left), height(temp.right));
		
		return temp;
	}
	
	private Node rightRotate(Node root) {
		Node temp = root.left;
		root.left = temp.right;
		temp.right = root;
		
		temp.color = root.color;
		root.color = RED;
		
		temp.size = root.size;
		root.size = 1 + size(root.left) + size(root.right);
		root.height = 1 + Math.max(height(root.left), height(root.right));
		temp.height = 1 + Math.max(height(temp.left), height(temp.right));
		
		return temp;
	}
	
	private Node flipColor(Node root) {
		root.left.color = root.color;
		root.right.color = root.color;
		root.color = RED;
		
		return root;
	}

	public static void main(String[] args) {
		RBT<Integer, String> tree = new RBT<>();
		System.out.println(tree.isEmpty());
		tree.put(3, "Ayush");
		System.out.println(tree.get(3));
		tree.put(2, "Simran");
		tree.put(1, "Cheeku");
		System.out.println(tree.get(2));
		System.out.println(tree.get(1));
		
		
	}
}
