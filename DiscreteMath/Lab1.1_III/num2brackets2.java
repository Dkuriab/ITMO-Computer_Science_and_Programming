import java.util.*;
import java.io.*;

public class num2brackets2 {
    public static BufferedReader in;
    public static BufferedWriter out;
    public static Set<Integer> used;
    public static int n;
    public static long k;
    public static long m;


    public static void main(String[] args) {
        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream("num2brackets2.in"), "UTF-8"));
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("num2brackets2.out"), "UTF-8"));
            
            Scanner scan = new Scanner (in.readLine());
            int open = scan.nextInt();
            long num = scan.nextLong();

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
    
            int length = 2 * open;
            int balance = 0;
            int balance1 = 0; // balance of "("

            Stack<Integer> prevs = new Stack<>();

            for (int i = 0; i < length; i++) {

                if (!prevs.empty() && prevs.peek() == 1 && 
                    num >= 2 * dp[length - i - 1][balance + 1] * (1 << ((length - i - 2 - balance) / 2))) {

                    num -= 2 * dp[length - i - 1][balance + 1] * (1 << ((length - i - 2 - balance) / 2));
                    out.write("]");
                    balance--;
                    prevs.pop();
                    continue;

                } else if ((balance1 > 0 && !prevs.empty() && prevs.peek() != 1) 
                    && num >= dp[length - i - 1][balance + 1] * (1 << ((length - i - 2 - balance) / 2)) +
                    dp[length - i - 1][balance - 1] * (1 << ((length - i - balance) / 2))) {

                    num -= dp[length - i - 1][balance + 1] * (1 << ((length - i - 2 - balance) / 2)) +
                    dp[length - i - 1][balance - 1] * (1 << ((length - i - balance) / 2));

                    out.write("[");
                    balance++;
                    prevs.push(1);
                    continue;


                } else  if (!(balance1 > 0 && !prevs.empty() && prevs.peek() != 1) 
                    && num >= dp[length - i - 1][balance + 1] * (1 << ((length - i - 2 - balance) / 2))) {

                    out.write("[");
                    num -= dp[length - i - 1][balance + 1] * (1 << ((length - i - 2 - balance) / 2));


                    balance++;
                    prevs.push(1);
                    continue;

                } else  if (num >= dp[length - i - 1][balance + 1] * (1 << ((length - i - 2 - balance) / 2))) {

                    num -= dp[length - i - 1][balance + 1] * (1 << ((length - i - 2 - balance) / 2));
                    out.write(")");

                    balance--;
                    balance1--;
                    prevs.pop();

                } else {
                    out.write("(");
                    balance++; 
                    balance1++;
                    prevs.push(0);
                }
            }


            //out.write(num + "");
            out.flush();
        }catch (IOException e) {
                System.out.println(e);
        }     
    }
}