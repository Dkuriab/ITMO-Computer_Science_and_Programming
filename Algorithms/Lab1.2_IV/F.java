import java.io.*;
import java.util.*;
  
public class F {
    public static long ans = 0;

    public static class Card implements Comparable<Card> {
        int red;
        int blue;

        public Card(int red, int blue) {
            this.red = red;
            this.blue = blue;
        }

        public int compareTo(Card x) {
            if (red == x.red) {
                return blue - x.blue;
            } else {
                return red - x.red;
            }
        }

        public String toString() {
            return red + " <--> " + blue;
        }
    }

    public static void merge(Card[] a, int l, int m, int r) {
        int i = l;
        int j = m;
        int t = 0;
        Card[] c = new Card[r - l + 1];
        while (i < m || j < r) {
            if (i < m && (j == r || a[i].blue <= a[j].blue)) {
                c[t++] = a[i++];
            } else {
                ans += m - i;
                c[t++] = a[j++];
            }
        }
        System.arraycopy(c, 0, a, l, t);
    }

    public static void mergesort(Card[] a, int l, int r) {
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
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("john.in")));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("john.out")));
  
            int n = Integer.parseInt(in.readLine().split(" ")[0]);
            Card[] stack = new Card[n];

            for (int i = 0; i < n; i++) {
                String[] values = in.readLine().split(" ");
                stack[i] = new Card(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
            }

            Arrays.sort(stack);
            // System.out.println(Arrays.toString(stack));

            mergesort(stack, 0, n);

            out.write(ans + "");
            out.close();
            in.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}