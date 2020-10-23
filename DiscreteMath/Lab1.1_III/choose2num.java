import java.util.*;
import java.io.*;

public class choose2num {
    public static BufferedReader in;
    public static BufferedWriter out;
    public static Set<Integer> used;
    public static int n;
    public static int k;
    public static long m;


    public static void main(String[] args) {
        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream("choose2num.in"), "UTF-8"));
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("choose2num.out"), "UTF-8"));
            
            Scanner scan = new Scanner (in.readLine());
            n = scan.nextInt();
            k = scan.nextInt();

            scan = new Scanner (in.readLine());

            int choose[] = new int[31];

            for (int i = 0 ; i < k; i++) {
                choose[i] = scan.nextInt();
            }

            long[][] binom = new long[31][31];

            for (int i = 1; i <= n; i++) {
                binom[i][1] = i;
            }

            for (int i = 1; i <= n; i++) {
                binom[i][0] = 1;
            }

            for (int i = 2; i <= n; i++) {
                for (int k = 2; k <= i; k++) {
                    binom[i][k] = binom[i - 1][k - 1] + binom[i - 1][k];
                }
            }

            long num = 0;
            int cur = 1;

            for (int i = 0 ; i < k; i++) {
                for (int j = cur; j < choose[i]; j++) {
                    num += binom[n - j][k - i - 1];
                }
                cur = choose[i] + 1;
            }


            out.write(num + "");
            out.flush();
        }catch (IOException e) {
                System.out.println(e);
        }     
    }
}