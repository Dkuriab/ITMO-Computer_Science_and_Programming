import java.util.*;
import java.io.*;

public class B {

	public static void main(String[] args) {
		try {
	        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("gray.in"), "UTF-8"));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("gray.out"), "UTF-8"));
	        
	        Scanner scan = new Scanner (in.readLine());
	        int k = scan.nextInt();

	        int s = 1 << k;
	        
	        for (int i = 0; i < s; i++) {
	        	int g = i ^ (i/2);
	        	for (int t = k - 1; t >= 0; t--) {
	        		out.write("" + ((g >> t) % 2));
	        	}
				out.write("\n");
	        }
	        out.flush();

	    }catch (IOException e) {
				System.out.println(e);
		}     
	}
}