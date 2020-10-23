import java.util.*;
import java.io.*;

public class num2brackets {
    public static BufferedReader in;
    public static BufferedWriter out;
    public static Set<Integer> used;
    public static int n;
    public static long k;
    public static long m;


    public static void main(String[] args) {
        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream("num2brackets.in"), "UTF-8"));
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("num2brackets.out"), "UTF-8"));
            
            Scanner scan = new Scanner (in.readLine());
            n = scan.nextInt();
            k = scan.nextLong();

            long[][] dp = new long[42][42]; // i - lenght // j - balance

            dp[0][0] = 1;
            
            for (int i = 1; i < 41; i++) {
                for (int j = 0; j < 41; j++) {
                	if (j == 0) {
                		dp[i][j] = dp[i - 1][j + 1];
                	} else {
                    	dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j + 1];
                	}
                }
            }

            int balance = 0;
            k++;

            for (int i = 2 * n - 1; i >= 0; i--) {
            	if (k > dp[i][Math.abs(balance + 1)]) {
            		out.write(')');
            		k -= dp[i][Math.abs(balance + 1)];
            		balance--;
            	} else {
            		out.write('(');
            		balance++;
            	}
            }

            out.flush();
        }catch (IOException e) {
                System.out.println(e);
        }     
    }
}