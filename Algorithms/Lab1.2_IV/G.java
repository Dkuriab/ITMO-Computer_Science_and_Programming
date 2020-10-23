import java.io.*;
import java.util.*;
  
public class G {
    public static int len = 0;
    public static int[] cur;
    public static int count = 0;
    public static int r = 0;
    
    public static boolean beautiful(int x) {
        int ans = 0;
        int counter = 0;
        for (int i = 1; i <= x; i++) {
            if (x % i == 0) {
                counter++;
            }
        }
        return (counter % 3 == 0) || x == 0;
    }


    // public 
    public static void check() {
        int num = 0;
        for (int k = 0; k < cur.length - 1; k++) {
            num += cur[k] * cur[k + 1];
        }
        count = beautiful(num % r) ? count + 1 : count;
    }


    public static void rec(boolean[] a) {
        // int i = 1;
        if (cur[0] == cur.length) {
            return;
        }
        for (int i = 1; i <= cur.length; i++) {
            if (!a[i - 1]) {
                a[i - 1] = true;
                cur[len++] = i;
                if (cur.length == len) {
                    if (cur[0] < cur[cur.length - 1]) {
                        check();
                    }
                }
                rec(a);
                len--;
                a[i - 1] = false;
            }   
        }
    }

    public static void main(String[] args) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("beautiful.in")));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("beautiful.out")));
  
            String[] data = in.readLine().split(" ");
            int n = Integer.parseInt(data[0]);
            r = Integer.parseInt(data[1]);
            cur = new int[n];

            // fac();
            rec(new boolean[n]);
// 1108528
            out.write(count * 2 + "");
            out.close();
            in.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}