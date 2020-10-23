import java.util.*;

public class E {
 public static void merge(int[] a, int l, int m, int r) {
        int i = l;
        int j = m;
        int t = 0;
        int[] c = new int[r - l + 1];
        while (i < m || j < r) {
            if (i < m && (j == r || a[i] < a[j])) {
                c[t++] = a[i++];
            } else {
                c[t++] = a[j++];
            }
        }
        System.arraycopy(c, 0, a, l, t);
    }

    public static void mergesort(int[] a, int l, int r) {
        if (r - l <= 1) {
            return;
        }
        int m = (l + r)/2;
        mergesort(a, l, m);
        mergesort(a, m, r);
        merge(a, l, m, r);
    }

    public static int lowerbound(int[] a,int length, int x) {
    	int l = -1;
    	int r = length;
    	while ((r - l) > 1) {
    		int m = (l + r) / 2;
    		if (a[m] < x) {
    			l = m;
    		}
    		else {
    			r = m;
    		}
    	}
    	return r;
    }

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		int[] a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = scan.nextInt();	
		}
		int k = scan.nextInt();
		int[] l = new int[k];
		int[] r = new int[k];
		for (int i = 0; i < k; i++) {
			l[i] = scan.nextInt();
			r[i] = scan.nextInt();	
		}
		mergesort(a, 0, n);

		for (int i = 0; i < k; i++) {
			System.out.print((lowerbound(a, n, r[i] + 1) - lowerbound(a, n, l[i])) + " ");
		}


	}
}