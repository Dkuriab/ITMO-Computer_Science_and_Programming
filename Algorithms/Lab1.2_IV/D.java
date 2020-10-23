import java.util.*;
import java.io.*;

public class D {

	public static class Pair {
		public int key;
		public int value;

		public Pair (int key, int value) {
			this.key = key;
			this.value = value;
		}
	}

    public static void merge(Pair[] a, int l, int m, int r) {
        int i = l;
        int j = m;
        int t = 0;
        Pair[] c = new Pair[r - l + 1];
        while (i < m || j < r) {
            if (i < m && (j == r || a[i].value < a[j].value)) {
                c[t++] = a[i++];
            } else {
                c[t++] = a[j++];
            }
        }
        System.arraycopy(c, 0, a, l, t);
    }

    public static void mergesort(Pair[] a, int l, int r) {
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
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("sequence.in")));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("sequence.out")));

			int n = Integer.parseInt(in.readLine().split(" ")[0]);
			Pair[] seq = new Pair[n];
			int l = 0;
			int r = 0;
			int sum = 0;
			LinkedList<String> a = new LinkedList<>();

			String[] s = in.readLine().split(" ");

			for (int i = 0; i < n; i++) {
				seq[i] = new Pair(i + 1, Integer.parseInt(s[i]));
				sum += seq[i].value;
			}

			mergesort(seq, 0, n);

			for (int i = n - 1; i >= 0; i--) {
				if (l < r) {
					a.add(seq[i].key + "");
					l += seq[i].value;
				} else {
					r += seq[i].value;
				}
			}

			if (l == r) {
				out.write(a.size() + "");
				out.newLine();

				out.write(String.join(" ", a));
			} else {
				out.write(-1 + "");
			}


			out.close();
			in.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}