import java.util.*;
import java.io.*;

public class A {

	public static void main(String[] args) {
		try {
	        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("allvectors.in"), "UTF-8"));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("allvectors.out"), "UTF-8"));
	        
	        Scanner scan = new Scanner (in.readLine());
	        int k = scan.nextInt();

	        int s = 1 << k;
	        
	        for (int i = 0; i < s; i++) {
	        	for (int t = k - 1; t >= 0; t--) {
	        		out.write("" + ((i >> t) % 2));
	        	}
				out.write("\n");
	        }
	        out.flush();

	    }catch (IOException e) {
				System.out.println(e);
		}     
	}
}