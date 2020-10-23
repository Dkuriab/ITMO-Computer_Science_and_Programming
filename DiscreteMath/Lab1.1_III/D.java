import java.util.*;
import java.io.*;

public class D {

	public static void main(String[] args) {
		try {
	        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("chaincode.in"), "UTF-8"));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("chaincode.out"), "UTF-8"));
	        
	        Scanner scan = new Scanner (in.readLine());
	        int k = scan.nextInt();
	        Set<String> codes = new HashSet<>();

	        int s = 1 << k;

	        String tmp = "";

	        for (int i = 0; i < k; i++) {
	        	tmp += "0";
	        }
	        codes.add(tmp);
	        out.write(tmp + "\n");

	      	for (int i = 0; i < s - 1; i++) {

	      		tmp = tmp.substring(1, k);
	      		String l = tmp + "1";
	      		if (!codes.contains(l)) {
	      			tmp += "1";
	      			out.write(tmp + "\n");
	      			codes.add(l);
	      		} else {

	      			tmp += "0"; 
	      			out.write(tmp + "\n");
	      			codes.add(tmp);
	      		}
	      	}  

	        out.flush();

	    }catch (IOException e) {
				System.out.println(e);
		}     
	}
}