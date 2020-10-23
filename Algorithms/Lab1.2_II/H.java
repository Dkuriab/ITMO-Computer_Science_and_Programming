import java.util.*;
import java.io.*;

public class H {

	public static class Node {
		Node left;
		Node right;
		int balance;
		int info;
		boolean rotate = false;
		int size = 1;
		public Node(int info, int balance) {
			this.info = info;
			this.balance = balance;
		}
	}
	public static class Pair {
		Node l;
		Node r;
		public Pair(Node l, Node r) {
			this.l = l;
			this.r = r;
		}
	}

	public static class Treap {
		public Node root = null;

		public Node merge(Node l, Node r) {
			push(l);
			push(r);
			if (l == null) {
				return r;
			}
			if (r == null) {
				return l;
			}

			if (l.balance >= r.balance) {
				l.right = merge (l.right, r);
				checkSize(l);
				return l;
			} else {
				r.left = merge (l, r.left);
				checkSize(r);  
				return r;
			}
		}

		public Pair split(Node x, int number, int passedSize) {
			if (x == null) {
				return new Pair(null, null);
			}
			push(x);
			int lSize = size(x.left);

			if (number <= passedSize + lSize) {
				Pair s = split (x.left, number, passedSize);
				push(s.l);
				push(s.r);
				x.left = s.r;

				checkSize(x);
				return new Pair(s.l, x);
			} else {
				Pair s = split (x.right, number, passedSize + lSize + 1);
				push(s.l);
				push(s.r);

				x.right = s.l;

				checkSize(x);
				return new Pair(x, s.r);
			}
		}

		public void insert(Node x, int number) {
			Pair s = split(root, number, 0);

			s.l = merge(s.l, x);
			root = merge(s.l, s.r);
		}


		public void rotate(int l, int r) {
			Pair a = split(root, l - 1, 0);
			Pair b = split(a.r, r - l + 1, 0);

			if (b.l != null && !b.l.rotate) {
				b.l.rotate = true;
			}

			root = merge(merge(a.l, b.l), b.r);
		}

		public void push(Node x) {
			if (x != null) {
				if (x.rotate) {
					Node t = x.right;
					x.right = x.left;
					x.left = t;
					x.rotate = false;
					if (x.left != null) {
						x.left.rotate = x.left.rotate ? false : true;
					}
					if (x.right != null) {
						x.right.rotate = x.right.rotate ? false : true;
					}
				}
			}
		}

		public void delete(int number, int passedSize) {
			Pair a = split(root, number, 0);
			Pair b = split(a.l, number - 1, 0);
			root = merge(b.l, a.r);
		}

		public String output(Node cur) {
			if (cur == null){
				return "";	
			}

			push(cur);

			return output(cur.left) + cur.info + " " + output(cur.right);
		}

		public int size(Node cur) {
			if (cur == null) {
				return 0;
			}
			return cur.size;
		}

		public void checkSize(Node cur) {
			if (cur != null) {
				cur.size = size(cur.left) + size(cur.right) + 1;
			}
		}

	}


	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

		Treap treap = new Treap();

		String[] a = in.readLine().split(" ");
		int n = Integer.parseInt(a[0]);
		int m = Integer.parseInt(a[1]);

		Random rm = new Random();

		Set<Integer> generated = new LinkedHashSet<Integer>();
		while (generated.size() <= n) {
			generated.add(rm.nextInt(2000_000_000));
		}	

		Object[] gen = generated.toArray();

		for (int i = 0; i < n; i++) {
			treap.insert(new Node(i + 1, (int)gen[i]), i + 1);
		}

		for (int i = 0; i < m; i++) {
			String[] q = in.readLine().split(" ");
			treap.rotate(Integer.parseInt(q[0]), Integer.parseInt(q[1]));
		}

		out.write(treap.output(treap.root));
		out.flush();
	}
}