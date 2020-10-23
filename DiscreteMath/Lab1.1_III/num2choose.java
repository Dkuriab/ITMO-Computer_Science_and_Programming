import java.util.*;
import java.io.*;

public class num2choose {
    public static BufferedReader in;
    public static BufferedWriter out;
    public static Set<Integer> used;
    public static int n;
    public static int k;
    public static long m;


    public static void main(String[] args) {
        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream("num2choose.in"), "UTF-8"));
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("num2choose.out"), "UTF-8"));
            
            Scanner scan = new Scanner (in.readLine());
            n = scan.nextInt();
            k = scan.nextInt();
            m = scan.nextLong();
            used = new HashSet<>();
            
            long[][] binom = new long[31][31];

            for (int i = 1; i <= n; i++) {
                binom[i][1] = i;
            }

            for (int i = 2; i <= n; i++) {
                for (int k = 2; k <= i; k++) {
                    binom[i][k] = binom[i - 1][k - 1] + binom[i - 1][k];
                }
            }

            int cur = 1;

            for (int i = 0 ; i < k; i++) {

                for (int j = cur; j <= n; j++) {
                    
                    long B = 1;

                    if (k - i - 1 != 0) {
                        B = binom[n - j][k - i - 1];
                    }
             
                    if (m < B) {
                        out.write(j + " ");
                        cur = j + 1;
                        break;
                    } else {
                        m -= B;
                    }

                }
            }

            out.flush();
        }catch (IOException e) {
                System.out.println(e);
        }     
    }
}