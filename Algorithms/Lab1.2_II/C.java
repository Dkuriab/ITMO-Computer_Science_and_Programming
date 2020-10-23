import java.util.*;
import java.io.*;

public class C {

	public static class Node {
		Node left;
		Node right;
		int balance;
		int info;
		int size = 1;
		int sizeNull;
		public Node(int info, int balance) {
			this.info = info;
			this.balance = balance;
			if (info == 0) {
				this.sizeNull = 1;
			} else {
				this.sizeNull = 0;
			}
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
			int lSize = size(x.left);

			if (number <= passedSize + lSize) {
				Pair s = split (x.left, number, passedSize);
				x.left = s.r;
				checkSize(x);
				return new Pair(s.l, x);
			} else {
				Pair s = split (x.right, number, passedSize + lSize + 1);
				x.right = s.l;
				checkSize(x);
				return new Pair(x, s.r);
			}
		}

		public void insert(Node x, int number) {
			
			Pair a = split(root, number, 0);
			Pair b = split(a.l, number - 1, 0);
			try {


				if (b.r.info == 0) {
					root = merge(merge(b.l, x), a.r);
					checkSize(root);
				} else {
					a.r = deleteFirstNull(a.r);

					b.l = merge(b.l, x);
					checkSize(b.l);
					a.l = merge(b.l, b.r);
					checkSize(a.l); 
					root = merge(a.l, a.r);
					checkSize(root);
				}


			} catch (NullPointerException e) {
				System.out.println("null: " + x.info + " : " + number);
				e.printStackTrace();
			}
		}

		public void justInsert(Node x, int number) {
			Pair s = split(root, number, 0);

			s.l = merge(s.l, x);
			root = merge(s.l, s.r);
		}

		public Node deleteFirstNull(Node cur) {
            if (cur == null) {
                return cur;
            }

            if (cur.info == 0 && (cur.left == null || cur.left.sizeNull == 0)) {
            	Node ans = merge(cur.left, cur.right);
            	checkSize(ans);
            	return ans;

            } else if (cur.left != null && cur.left.sizeNull != 0) {
            	cur.left = deleteFirstNull(cur.left);
            } else if (cur.right != null && cur.right.sizeNull != 0){
            	cur.right = deleteFirstNull(cur.right);
            }
            checkSize(cur);
            return cur;
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

		public int sizeNull(Node cur) {
			if (cur == null) {
				return 0;
			}
			return cur.sizeNull;
		}

		public void checkSize(Node cur) {
			if (cur != null) {
				cur.size = size(cur.left) + size(cur.right) + 1;
				int s = cur.info == 0 ? 1 : 0;
				cur.sizeNull = sizeNull(cur.left) + sizeNull(cur.right) + s;
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
		while (generated.size() <= (1 << 19)) {
			generated.add(rm.nextInt(Integer.MAX_VALUE));
		}	

		Object[] gen = generated.toArray();

		for (int i = 0; i < m; i++) {
			treap.justInsert(new Node(0, (int)gen[i]), i + 1);
		}

		String[] l = in.readLine().split(" ");

		for (int i = 0; i < n; i++) {
			treap.insert(new Node(i + 1, (int)gen[i + (1 << 18)]), Integer.parseInt(l[i]));
		}

		String ans = treap.output(treap.root);

		Scanner scan = new Scanner(ans);
		int cur = 0;
		int lastNotNull = 0;
		int t = 0;
		while (scan.hasNextInt()) {
			t++;
			cur = scan.nextInt();
			if (cur != 0) {
				lastNotNull = t;
			} 
		}

		Scanner outs = new Scanner(ans);

		out.write(lastNotNull + "");
		out.newLine();

		for (int i = 0; i < lastNotNull; i++) {
			out.write(outs.nextInt() + " ");
		}

		out.flush();
	}
}