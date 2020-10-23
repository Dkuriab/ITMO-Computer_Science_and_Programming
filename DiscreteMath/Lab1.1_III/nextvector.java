
import java.util.*;
import java.io.*;

public class nextvector {
    public static BufferedReader in;
    public static BufferedWriter out;

	public static void main(String[] args) {
		try {
	        in = new BufferedReader(new InputStreamReader(new FileInputStream("nextvector.in"), "UTF-8"));
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("nextvector.out"), "UTF-8"));
	        
	        Scanner scan = new Scanner (in.readLine());
	        String line = scan.next();

	        int length = line.length();

	        StringBuilder end = new StringBuilder();

	        int i = 0;
	        String trash = "0";
	        while (length - i > 0 && line.charAt(length - 1 - i) == '0') {
	        	i++;
	        	trash += "1";
	        }

	        if (i == length) {
	        	out.write("-" + "\n");
	        } else {
	        	out.write(line.substring(0, length - 1 - i) + trash + "\n");
	        }

	        i = 0;
	        String trash1 = "1";
	        while (length - i > 0 && line.charAt(length - 1 - i) == '1') {
	        	i++;
	        	trash1 += "0";
	        }

	        if (i == length) {
	        	out.write("-" + "\n");
	        } else {
	        	out.write(line.substring(0, length - 1 - i) + trash1);
	        }




			out.flush();

	    }catch (IOException e) {
				System.out.println(e);
		}     
	}
}