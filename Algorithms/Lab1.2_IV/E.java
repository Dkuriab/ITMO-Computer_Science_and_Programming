import java.util.*;
import java.io.*;

public class E {

	public static class Apple {
		public int difference;
		public int low;
		public int up;
		public int num;

		public Apple (int difference, int low, int num) {
			this.difference = difference;
			this.low = low;
			up = difference + low;
			this.num = num;
		}
	}

	// a better then b
	public static boolean compare(Apple a, Apple b) {
		if (a.difference <= 0 && b.difference >= 0) {
			return false;
		} else if (a.difference >= 0 && b.difference <= 0) {
			return true;
		} else if (a.difference >= 0 && b.difference >= 0) {
			if (a.low == b.low) {
	            return  a.up > b.up;
	        } else {
	            return a.low < b.low;
	        }
		} else if (a.difference <= 0 && b.difference <= 0) {
			if (a.up == b.up) {
	            return a.low < b.low;
	        } else {
	            return a.up > b.up;
	        }
		}
		return false;
	}

    public static void merge(Apple[] a, int l, int m, int r) {
        int i = l;
        int j = m;
        int t = 0;
        Apple[] c = new Apple[r - l + 1];
        while (i < m || j < r) {
            if (i < m && (j == r || compare(a[i], a[j]))) {
                c[t++] = a[i++];
            } else {
                c[t++] = a[j++];
            }
        }
        System.arraycopy(c, 0, a, l, t);
    }

    public static void mergesort(Apple[] a, int l, int r) {
        if (r - l <= 1) {
            return;
        }
        int m = (l + r) / 2;
        mergesort(a, l, m);
        mergesort(a, m, r);
        merge(a, l, m, r);
    }

	public static void main(String[] args) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("apples.in")));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("apples.out")));

			String[] ns = in.readLine().split(" ");

			int n = Integer.parseInt(ns[0]);
			int s = Integer.parseInt(ns[1]);

			Apple[] apples = new Apple[n];

			for (int i = 0; i < n; i++) {
				String[] du = in.readLine().split(" ");
				int down = Integer.parseInt(du[0]);
				int up = Integer.parseInt(du[1]);
				
				apples[i] = new Apple(up - down, down, i + 1);
			}

			mergesort(apples, 0, n);
			String ans = "";
			for (Apple a : apples) {
				s -= a.low;
				if (s <= 0) {
					break;
				}
				s += a.up;
				ans += a.num + " ";
				// System.out.println("difference: " + a.difference + " :: " + "low: " + a.low);

			}

			if (s <= 0) {
				out.write(-1 + "");
			} else {
				out.write(ans);
			}

			out.close();
			in.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}