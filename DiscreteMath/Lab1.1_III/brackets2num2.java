import java.util.*;
import java.io.*;

public class brackets2num2 {
    public static BufferedReader in;
    public static BufferedWriter out;
    public static Set<Integer> used;
    public static int n;
    public static long k;
    public static long m;


    public static void main(String[] args) {
        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream("brackets2num2.in"), "UTF-8"));
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("brackets2num2.out"), "UTF-8"));
            
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
            int balance1 = 0; // balance of "("
            long num = 0;
            Stack<Integer> prevs = new Stack<>();

            for (int i = 0; i < length; i++) {
                if (brackets.charAt(i) == '(') {
                    balance++; 
                    balance1++;
                    prevs.push(0);
                } else  if (brackets.charAt(i) == ')') {
                    balance++;
                    num += dp[length - i - 1][balance] * (1 << ((length - i - 1 - balance) / 2)); // if (
                    balance--;

                    balance--;
                    balance1--;
                    prevs.pop();

                } else  if (brackets.charAt(i) == '[') {
                    balance++;
                    num += dp[length - i - 1][balance] * (1 << ((length - i - 1 - balance) / 2)); // if (
                    balance--;

                    if (balance1 > 0 && prevs.peek() != 1) {
                        balance--;
                        num += dp[length - i - 1][balance] * (1 << ((length - i - 1 - balance) / 2)); // if )
                        balance++;
                    }
                    balance++;
                    prevs.push(1);

                } else  if (brackets.charAt(i) == ']') {
                    balance++;
                    num += 2 * dp[length - i - 1][balance] * (1 << ((length - i - 1 - balance) / 2)); // if ( or [
                    balance--;

                    balance--;
                    prevs.pop();
                }
            }


            out.write(num + "");
            out.flush();
        }catch (IOException e) {
                System.out.println(e);
        }     
    }
}