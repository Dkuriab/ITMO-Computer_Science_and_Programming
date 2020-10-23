
import java.util.*;
import java.io.*;

public class brackets {
    public static int n;
    public static int opened = 0;
    public static BufferedReader in;
    public static BufferedWriter out;
    public static int balance = 0;


	public static void rec (String b) {
		if (balance < 0 || opened == n + 1) {
			return;
		}
		if (b.length() == 2*n) {
			try {
				out.write(b + "\n");
			} catch (IOException e) {
			}
			return;
		}
		opened++;
		balance++;
		rec(b + "(");
		balance--;
		opened--;

		balance--;
		rec(b + ")");
		balance++;
	}	

	public static void main(String[] args) {
		try {
	        in = new BufferedReader(new InputStreamReader(new FileInputStream("brackets.in"), "UTF-8"));
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("brackets.out"), "UTF-8"));
	        
	        Scanner scan = new Scanner (in.readLine());
	        n = scan.nextInt();

	        rec("");

			out.flush();

	    }catch (IOException e) {
				System.out.println(e);
		}     
	}
}