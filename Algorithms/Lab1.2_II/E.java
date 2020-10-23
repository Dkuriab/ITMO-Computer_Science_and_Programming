import java.util.*;
import java.io.*;

public class E {
	public static class Node {
		Node parent = null;
		Node left = null;
		Node right = null;
		int value = 0;
		int mas = 1;
		public Node(Node parent, int value) {
			this.parent = parent;
			this.value = value;
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

	public static void checkMas(Node v) { 
	  	if (v == null) {
	  		return;
	  	}
	  	v.mas = 1;
	  	if (v.left != null) {
	  		v.mas += v.left.mas;
	  	}
	  	if (v.right != null) {
	  		v.mas += v.right.mas;
	  	}
	}

	public static class SplayTree {
		private Node root;

		public String show(Node c) {
			if (c == null) {
				return " null ";
			}
			return show(c.left) + " " + c.value + " " + show(c.right);
		}

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
			checkMas(parent);
			checkMas(child);
		}

		public void splay(Node x) {
				if (x.parent == null) {
					root = x;
					return;
				}

				Node parent = x.parent;
				Node gparent = parent.parent;

				if (gparent == null) {
				// zig 
					rotate(parent, x);
				} else if ((gparent.left == parent && parent.left == x) || (gparent.right == parent && parent.right == x)) {
				// zigzig
					rotate(gparent, parent);
					rotate(parent, x);
				} else {
				// zigzag
					rotate(parent, x);
					rotate(gparent, x);
				}
				splay(x);
				root = x;
		}

		public void merge(Node left, Node right) {
			if (left == null) {
				root = right;
				return;
			}
			if (right == null) {
				root = left;
				return;
			}

			Node l = right;
			while (l.left != null) {
				l = l.left;
			}
			splay(l);
			setParent(l, null);

			if (l.left != null) {
				System.out.println("wr");
			}

			l.left = left;
			setParent(left, l);
		}

		public Node find(Node cur, int x) {
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

		public void insert(Node node, int x) {
			if (node == null) {
				root = new Node(null, x);
				return;
			}

			if (node.value == x) {
				return;
			}
			if (x < node.value) {
				if (node.left == null) {
					node.left = new Node(node, x);
					checkMas(node);
					splay(node.left);
				} else {
					insert(node.left, x);
				}
			} else {
				if (node.right == null) {
					node.right = new Node(node, x);
					checkMas(node);
					splay(node.right);
				} else {
					insert(node.right, x);
				}
			}
		}

		public void delete(int x) {
			Node cur = find(root, x);
			if (cur != null) {
				setParent(cur.left, null);
				setParent(cur.right, null);
				merge(cur.left, cur.right);
			}

		}

		public boolean exists(int x) {
			return find(root, x) != null;
		}

		public int k(Node c, int x) {
			int rm = c.right != null ? c.right.mas : 0;

			if (rm + 1 == x) {
				return c.value;
			}
			if (rm + 1 < x) {
				return k(c.left, x - rm - 1);
			} else {
				return k(c.right, x);
			}
		}

		public Node next(int x) {
			if (root == null) {
				return null;
			}

			Node max = root;
			while (max.right != null) {
				max = max.right;
			}
			if (x >= max.value) {
				return null;
			}

			Node lastMore = root;
			Node cur = root;
			while (cur != null && cur.value != x) {
				if (x < cur.value) {
					lastMore = cur;
					cur = cur.left;
				} else {
					cur = cur.right;
				}
			}

			if (cur == null) {
				return lastMore;
			}

			if (cur.value == x) {
				if (cur.right != null) {
					cur = cur.right;
					while (cur.left != null) {
						cur = cur.left;
					}
					return cur;
				} else {
					return lastMore;
				}
			}
			return null;
		}

		public Node prev(int x) {
			if (root == null) {
				return null;
			}
			
			Node min = root;
			while (min.left != null) {
				min = min.left;
			}
			if (x <= min.value) {
				return null;
			}


			Node lastLess = root;
			Node cur = root;
			while (cur != null && cur.value != x) {
				if (x < cur.value) {
					cur = cur.left;
				} else {
					lastLess = cur;
					cur = cur.right;
				}
			}

			if (cur == null) {
				return lastLess;
			}

			if (cur.value == x) {
				if (cur.left != null) {
					cur = cur.left;
					while (cur.right != null) {
						cur = cur.right;
					}
					return cur;
				} else {
					return lastLess;
				}
			}
			return null;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

		SplayTree tree = new SplayTree();

		int d = Integer.parseInt(in.readLine());

		for (int i = 0; i < d; i++) {
			String[] cur = in.readLine().split(" ");
			int command = Integer.parseInt(cur[0]);

			int x = Integer.parseInt(cur[1]);

			switch (command) {
				case 100 :
					System.out.println(tree.show(tree.root));
				case 1 :
					tree.insert(tree.root, x);
					break;
				case -1 :
					tree.delete(x);
					break;
				case 0 :
					System.out.println(tree.k(tree.root, x));
					break;

			}
			out.flush();

		}
	}
}