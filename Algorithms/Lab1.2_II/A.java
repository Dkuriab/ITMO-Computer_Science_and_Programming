import java.util.*;
import java.io.*;

public class A {
	public static class Node {
		Node parent;
		Node left;
		Node right;

		int value;
		public Node(Node parent, int value) {
			this.parent = parent;
			this.value = value;
		}

		public void change(Node a, Node b) {
			if (this.left != null && this.left.value == a.value) {
				this.left = b;
			} else {
				this.right = b;
			}
		}
	}

	public static class Tree {
		private Node root;

		public Tree() {
		}

		public Node find(Node cur, int x) {
			if (cur == null) {
				return null;
			}
			if (cur.value == x) {
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
				} else {
					insert(node.left, x);
				}
			} else {
				if (node.right == null) {
					node.right = new Node(node, x);
				} else {
					insert(node.right, x);
				}
			}
		}

		public void delete(int x) {
			Node cur = find(root, x);
			if (cur == null) {
				return;
			}

			if (cur.left == null) {
				if (cur.parent == null) {
					if (cur.right != null) {
						cur.right.parent = null;
					}
					root = cur.right;
					return;
				}
				if (cur.right != null) {
					cur.right.parent = cur.parent;
				}
				cur.parent.change(cur, cur.right);
			} else {
				Node righter = cur.left;
				while (righter.right != null) {
					righter = righter.right;
				}
				if (righter.left != null) {
					righter.left.parent = righter.parent;
				}
				righter.parent.change(righter, righter.left);
				cur.value = righter.value;
			}
		}

		public boolean exists(Node node, int x) {
			if (node == null) {
				return false;
			}
			if (node.value == x) {
				return true;
			}
			if (x < node.value) {
				return exists(node.left, x);
			} else { 
				return exists(node.right, x);
			}
		}

		public Node next(int x) {
			if (root == null) {
				return null;
			}

		//========check if no more than x==========//
			Node max = root;
			while (max.right != null) {
				max = max.right;
			}
			if (x >= max.value) {
				return null;
			}
		//========check if no more than x==========//


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
			
		//========check if no less than x==========//
			Node min = root;
			while (min.left != null) {
				min = min.left;
			}
			if (x <= min.value) {
				return null;
			}
		//========check if no less than x==========//


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

		Tree tree = new Tree();
		for (int i = 0; i < 100; i++) {
			String s = in.readLine();
			if (s == null || s.equals("")) {
				return;
			}
			String[] cur = s.split(" ");

			String command = cur[0];

			try {
				int x = Integer.parseInt(cur[1]);

				switch (command) {
					case "insert" :
						tree.insert(tree.root, x);
						break;
					case "delete" :
						tree.delete(x);
						break;
					case "exists" :
						out.write(tree.exists(tree.root, x) + "");
						out.newLine();
						break;
					case "next"   :
						Node ansn = tree.next(x);
						if (ansn == null) {
							out.write("none");
						} else {
							out.write(ansn.value + "");
						}
						out.newLine();
						break;
					case "prev"   :
						Node ansp = tree.prev(x);
						if (ansp == null) {
							out.write("none");
						} else {
							out.write(ansp.value + "");
						}
						out.newLine();
						break;
				}
				out.flush();
			} catch (NumberFormatException e) {
				continue;
			}
		}
	}
}