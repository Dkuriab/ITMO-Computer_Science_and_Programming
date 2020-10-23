import java.util.*;
import java.io.*;

public class G {

	public static class Node {
		Node left;
		Node right;
		int balance;
		int info;
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
			if (l == null) {
				return r;
			}
			if (r == null) {
				return l;
			}

			if (l.balance > r.balance) {
				l.right = merge (l.right, r);
				checkSize(l);
				return l;
			} else {
				r.left = merge (l, r.left);
				checkSize(r);  
				return r;
			}
		}

		public Pair split(Node t, int number, int passedSize) {
			if (t == null) {
				return new Pair(null, null);
			}

			int lSize = size(t.left);

			if (number <= passedSize + lSize) {
				Pair s = split (t.left, number, passedSize);
				t.left = s.r;

				checkSize(t);
				return new Pair(s.l, t);
			} else {
				Pair s = split (t.right, number, passedSize + lSize + 1);
				t.right = s.l;

				checkSize(t);
				return new Pair(t, s.r);
			}
		}

		public void insert(Node x, int number) {
			Pair s = split(root, number, 0);
			s.l = merge(s.l, x);
			root = merge(s.l, s.r);
		}

		public void moveToStart(int l, int r) {
			Pair a = split(root, l - 1, 0);
			Pair b = split(a.r, r - l + 1, 0);

			root = merge(merge(b.l, a.l), b.r);
		}



		public void delete(Node cur, int number, int passedSize) {
			Pair a = split(root, number, 0);
			Pair b = split(a.l, number - 1, 0);
			root = merge(b.l, a.r);
		}

		public String output(Node cur) {
			if (cur == null){
				return "";	
			}			
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
		for (int i = 0; i < n; i++) {
			int t = rm.nextInt(2000000);
			treap.insert(new Node(i + 1, t), i + 1);
		}

		for (int i = 0; i < m; i++) {
			String[] q = in.readLine().split(" ");
			treap.moveToStart(Integer.parseInt(q[0]), Integer.parseInt(q[1]));
		}

		out.write(treap.output(treap.root));
		out.flush();
	}
}