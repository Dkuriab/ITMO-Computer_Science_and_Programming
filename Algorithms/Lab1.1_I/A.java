import java.util.*;

public class A {
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

    public static void main(String[] args) {
        Scanner scan = new Scanner (System.in);
        int n = scan.nextInt();
        int[] a = new int[n];

        for (int i = 0; i < n; i++) {
            a[i] = scan.nextInt();
        }
        mergesort(a, 0, n);
        for (int i = 0; i < n; i++) {
            System.out.print(a[i] + " ");
        }
    }

}