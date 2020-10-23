import java.util.*;
import java.io.*;
import java.lang.*;
 
public class G {
	public static gorn gor[];
	public static BufferedWriter go = new BufferedWriter(new OutputStreamWriter(System.out));


	public static class gorn {
		private int power;
		private int min;
		private int max;
		private int depth;

		private gorn head;

		public gorn(int x) {
			power = 1;
			min = x;
			max = x;
			depth = 0;
		}
	}
	public static gorn set(gorn x) {
		if (x.head == x) {
			return x;
		}
		gorn tmp = set(x.head);
		x.head = tmp;
		return tmp;
	}

	public static void union(int a, int b) {
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

				m.max = max(n.max, m.max);
				m.min = min(n.min, m.min);
				m.power += n.power;
			}
	}
	public static int max(int a, int b) {
		if (a > b) {
			return a;
		} else {
			return b;
		}
	}
	public static int min(int a, int b) {
		if (a > b) {
			return b;
		} else {
			return a;
		}
	}
	public static void get(int x) {
		gorn tmp = set(gor[x]);
			try{
				go.write(tmp.min + " " + tmp.max + " " + tmp.power);
				go.newLine();
				go.flush();
			}catch (IOException e) {
				System.out.println(e);
			}
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
		while (scan.hasNext()) {
			String next = scan.next();

			if (next.compareTo("union") == 0) {
				union(scan.nextInt(), scan.nextInt());
			}
			if (next.compareTo("get") == 0){
				get(scan.nextInt());
			}

		}
	}
}