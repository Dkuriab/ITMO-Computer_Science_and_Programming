import java.util.*;
import java.io.*;

public class subsets {
    public static int n;
    public static BufferedReader in;
    public static BufferedWriter out;
    public static List<Integer> ok = new LinkedList<>();

    public static void print() {
    	try {
			StringBuilder done = new StringBuilder();
			for (Integer h : ok) {
    			done.append(h + " ");
    		}
    		out.write(done.deleteCharAt(done.length() - 1) + "\n");
		} catch (IOException e) {
		}
    }


	public static void rec (int a) {
		if (a > n) {
			return;
		}
		
		for (int i = a + 1; i <= n; i++) {
			ok.add(i);
			print();
			rec(i);
			ok.remove(ok.size() - 1);
		}

	}	

	public static void main(String[] args) {
		try {
	        in = new BufferedReader(new InputStreamReader(new FileInputStream("subsets.in"), "UTF-8"));
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("subsets.out"), "UTF-8"));
	        
	        Scanner scan = new Scanner (in.readLine());
	        n = scan.nextInt();

	        out.write("\n");
	        rec(0);

			out.flush();

	    }catch (IOException e) {
				System.out.println(e);
		}     
	}
}