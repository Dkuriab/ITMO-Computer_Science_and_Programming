import java.util.*;
import java.io.*;

public class D {
	public static class Node {
		Node parent = null;
		Node left = null;
		Node right = null;
		long value;
		long min;
		long max;
		long sum;
		public Node(Node parent, long value) {
			this.parent = parent;
			this.value = value;
			this.min = value;
			this.max = value;
			this.sum = value;
		}
	}
	public static void setParent(Node child, Node parent) {
	  	if (child != null) {
	    	child.parent = parent;
		}
	}

	public static void acceptParent(Node v) { 
	  	setParent(v.left, v);
	  	setParent(v.right, v);
	}

	public static void checkSum(Node v) { 
	  	if (v == null) {
	  		return;
	  	}
	  	v.sum = v.value;
	  	v.min = v.value;
	  	v.max = v.value;
	  	if (v.left != null) {
	  		v.sum += v.left.sum;
	  		v.min = v.left.min;
	  	}
	  	if (v.right != null) {
	  		v.sum += v.right.sum;
	  		v.max = v.right.max;
	  	}
	}

	public static class SplayTree {
		private Node root;

		public void rotate(Node parent, Node child) {
			if (parent == null || child == null) {
				return;
			}

			Node grandparent = parent.parent;

			if (grandparent != null) {
				if (grandparent.left == parent) {
				  grandparent.left = child;
				}
				else {
				  grandparent.right = child;
				}
			}

			if (parent.left == child) {
				parent.left = child.right;
				child.right = parent;
			}
			else {
				parent.right = child.left;
				child.left = parent;
			}

			setParent(child, grandparent);
			acceptParent(parent);
			acceptParent(child);
			checkSum(parent);
			checkSum(child);
		}

		public void splay(Node x) {
				if (x.parent == null) {
					root = x;
					return;
				}

				Node parent = x.parent;
				Node grandparent = parent.parent;

				if (grandparent == null) {
				// zig 
					rotate(parent, x);
				} else if ((grandparent.left == parent && parent.left == x) || (grandparent.right == parent && parent.right == x)) {
				// zigzig
					rotate(grandparent, parent);
					rotate(parent, x);
				} else {
				// zigzag
					rotate(parent, x);
					rotate(grandparent, x);
				}
				splay(x);
		}

		public Node find(Node cur, long x) {
			if (cur == null) {
				return null;
			}
			if (cur.value == x) {
				splay(cur);
				return cur;
			}
			if (x < cur.value) {
				return find(cur.left, x);
			} else {
				return find(cur.right, x);
			}
		}

		public void insert(Node node, long x) {
			if (node == null) {
				root = new Node(null, x);
				return;
			}

			if (node.value == x) {
				return;
			}
			Node s;

			if (x < node.value) {
				if (node.left == null) {
					node.left = new Node(node, x);
					checkSum(node);
					s = node.left;
					splay(node.left);
					checkSum(s);
				} else {
					insert(node.left, x);
				}
			} else {
				if (node.right == null) {
					node.right = new Node(node, x);
					checkSum(node);
					s = node.right;
					splay(node.right);
					checkSum(s);
				} else {
					insert(node.right, x);
				}
			}

		}

		public long sum(Node c, int l, int r) {
			if (c == null) {
				return 0;
			}

			if (c.min > r || c.max < l) {
				return 0;
			}
			if (c.min >= l && c.max <= r) {
				return c.sum;
			}
			long t = 0;
			if (c.value >= l && c.value <= r) {
				t = c.value;
			}
			return sum(c.left, l, r) + sum(c.right, l, r) + t;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

		SplayTree tree = new SplayTree();

		int d = Integer.parseInt(in.readLine());
		
		boolean isQ = false;
		long last = 0;
		for (int i = 0; i < d; i++) {
			String[] cur = in.readLine().split(" ");
			String command = cur[0];

			switch (command) {
				case "+" :
					if (isQ) {
						tree.insert(tree.root, (Long.parseLong(cur[1]) + last) % 1000_000_000);
						isQ = false;	
					} else {
						tree.insert(tree.root, Long.parseLong(cur[1]));
					}
					break;
				case "?" :
					isQ = true;
					last = tree.sum(tree.root, Integer.parseInt(cur[1]), Integer.parseInt(cur[2]));
					out.write(last + "");
					out.newLine();
					break;
			}
			out.flush();
		}
	}
}