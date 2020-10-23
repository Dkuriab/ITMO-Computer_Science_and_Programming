import java.util.*;
import java.io.*;
import java.lang.*;
 
public class H {
	public static gorn gor[];
	public static int extmp = 0;
	public static BufferedWriter go = new BufferedWriter(new OutputStreamWriter(System.out));


	public static class gorn {
		private int exp;
		private int depth;
		private gorn head;

		public gorn(int x) {
			exp = 0;
			depth = 0;
		}
	}
	public static gorn set(gorn x) {
		if (x.head == x) {
			return x;
		}
		return set(x.head);
	}

	public static void join(int a, int b) {
		gorn m = set(gor[a]);
		gorn n = set(gor[b]); 
		if (m != n) {
			if (m.depth == n.depth) {
				m.depth++;
			} else if (m.depth < n.depth) {
				gorn tmp = m;
				m = n;
				n = tmp;
			} 
			n.head = m;
			n.exp -=m.exp;
		}
	}

	public static void expsum(gorn x) {
		extmp += x.exp;
		if (x.head == x) {
			return;
		} 
		expsum(x.head);
	}

	public static void get(int x) {
		extmp = 0;
		expsum(gor[x]);
			try{
				go.write(extmp + "");
				go.newLine();
				go.flush();
			}catch (IOException e) {
				System.out.println(e);
			}
	}

	public static void add(int x, int ex) {
		set(gor[x]).exp += ex;
	}

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		gor = new gorn[n + 1];

		for (int i = 1; i <= n; i++) {
			gorn goblin = new gorn(i);
			goblin.head = goblin;			
			gor[i] = goblin;
		}
		int k = scan.nextInt();
		
		for (int i = 0; i < k; i++) {
			String next = scan.next();

			if (next.compareTo("join") == 0) {
				join(scan.nextInt(), scan.nextInt());
			}
			if (next.compareTo("get") == 0){
				get(scan.nextInt());
			}
			if (next.compareTo("add") == 0){
				add(scan.nextInt(), scan.nextInt());
			}
		}
	}
}