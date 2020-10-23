
import java.util.*;
import java.io.*;

public class H {
    public static int k;
    public static int n;
    public static BufferedReader in;
    public static BufferedWriter out;
    public static List<Integer> ok = new LinkedList<>();

    public static void print() {
    	try {
	    	for (Integer h : ok) {
	    		out.write(h + " ");
	    		//System.out.print(h + " ");
	    	}
	    	out.write("\n");
	    	//System.out.println();
	    	out.flush();
	    } catch (IOException e) {

	    }
    }

	public static void rec (int start) {
		if (ok.size() == k) {
			print();
		}
		for (int i = start; i <= n; i++) {
			ok.add(i);
			rec(i + 1);
			ok.remove(ok.size() - 1);
		}

	}	

	public static void main(String[] args) {
		try {
	        in = new BufferedReader(new InputStreamReader(new FileInputStream("choose.in"), "UTF-8"));
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("choose.out"), "UTF-8"));
	        
	        Scanner scan = new Scanner (in.readLine());
	        n = scan.nextInt();
	        k = scan.nextInt();



	        rec(1);

			out.flush();

	    }catch (IOException e) {
				System.out.println(e);
		}     
	}
}