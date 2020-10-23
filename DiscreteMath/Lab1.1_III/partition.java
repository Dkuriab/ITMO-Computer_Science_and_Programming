

import java.util.*;
import java.io.*;

public class partition {
    public static int n;
    public static int sum = 0;
    public static BufferedReader in;
    public static BufferedWriter out;
    public static List<Integer> ok = new LinkedList<>();
    //public static int balance = 0;


	public static void rec (int a) {
		if (sum == n) {
			try {
				StringBuilder done = new StringBuilder();
				for (Integer h : ok) {
	    			done.append(h + "+");
	    		}
	    		out.write(done.deleteCharAt(done.length() - 1) + "\n");
			} catch (IOException e) {
			}
			return;
		}
		if (sum > n) {
			return;
		}
		
		for (int i = a; i <= n; i++) {
			sum += i;
			ok.add(i);
			rec(i);
			ok.remove(ok.size() - 1);
			sum -= i;
		}

	}	

	public static void main(String[] args) {
		try {
	        in = new BufferedReader(new InputStreamReader(new FileInputStream("partition.in"), "UTF-8"));
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("partition.out"), "UTF-8"));
	        
	        Scanner scan = new Scanner (in.readLine());
	        n = scan.nextInt();

	        rec(1);

			out.flush();

	    }catch (IOException e) {
				System.out.println(e);
		}     
	}
}