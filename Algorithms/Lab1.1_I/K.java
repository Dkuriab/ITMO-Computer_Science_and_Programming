import java.util.*;
import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.StringTokenizer;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.util.ArrayList;

public class K {

    public static void merge(double[] a, int[] b, int l, int m, int r) {
        int i = l;
        int j = m;
        int t = 0;
        double[] c = new double [r - l + 1];
        int[] cc = new int [r - l + 1];
        while (i < m || j < r) {
            if (i < m && (j == r || a[i] > a[j])) {
                c[t] = a[i];
                cc[t] = b[i];
                i++;
                t++;
            } else {
                c[t] = a[j];
                cc[t] = b[j];
                t++;
                j++;
            }
        }
        System.arraycopy(cc, 0, b, l, t);
        System.arraycopy(c, 0, a, l, t);
    }

    public static void mergesort(double[] a, int[] b, int l, int r) {
        if (r - l <= 1) {
            return;
        }
        int m = (l + r)/2;
        mergesort(a, b, l, m);
        mergesort(a, b, m, r);
        merge(a, b, l, m, r);
    }

	public static boolean check(double s, int[] v, int[] w, double[] raz, int n, int k) {

		int[] num = new int[n];
		for (int i = 0; i < n; i++) {
			raz[i] = v[i] - s * w[i];
			num[i] = i;
		}

		mergesort(raz, num,  0, n);

		double sum = 0;

		for (int i = 0; i < k; i++) {
			sum += raz[i]; 
		}

		return (sum >= 0);
	}

	public static void result(double s, int[] v, int[] w, int n, int k) {
		double[] raz = new double[n];
		int[] num = new int[n];

		for (int i = 0; i < n; i++) {
			raz[i] = v[i] - s * w[i];
			num[i] = i + 1;
		}
		mergesort(raz, num, 0, n);

		for (int i = 0; i < k; i++) {
			System.out.println(num[i]); 
		}
	}

	public static void main(String[] args) {
		InputReader scan = new InputReader(System.in);
		int n = scan.nextInt();
		int k = scan.nextInt();
		double l = 0;
		double r = 10_000_000;

		double[] raz = new double[n];
		int[] v = new int[n];
		int[] w = new int[n];

		for (int i = 0; i < n; i++) {
			v[i] = scan.nextInt();
			w[i] = scan.nextInt();
		}

		for (int i = 0; i < 100; i++) {
			if(check((r + l) / 2, v, w, raz, n, k)) {
				l = (r + l) / 2;
			}
			else {
				r = (r + l) / 2;
			}
		}

		result(r, v, w, n, k);
	}
	static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;
 
        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }
 
        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }
		 
        public int nextInt() {
            return Integer.parseInt(next());
        }
 
    }
}
