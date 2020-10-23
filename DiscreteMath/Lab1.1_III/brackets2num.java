import java.util.*;
import java.io.*;

public class brackets2num {
    public static BufferedReader in;
    public static BufferedWriter out;
    public static Set<Integer> used;
    public static int n;
    public static long k;
    public static long m;


    public static void main(String[] args) {
        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream("brackets2num.in"), "UTF-8"));
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("brackets2num.out"), "UTF-8"));
            
            Scanner scan = new Scanner (in.readLine());
            String brackets = scan.next();

            int length = brackets.length();

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
            long num = 0;

            for (int i = 0; i < length; i++) {
            	if (brackets.charAt(i) == '(') {
            		balance++;
            	} else {
        			num += dp[length - i - 1][balance + 1];
            		balance--;
            	}
            }


            out.write(num + "");
            out.flush();
        }catch (IOException e) {
                System.out.println(e);
        }     
    }
}