import java.util.*;
import java.io.*;

public class C {

	public static void main(String[] args) {
		try {
	        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("antigray.in"), "UTF-8"));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("antigray.out"), "UTF-8"));
	        
	        Scanner scan = new Scanner (in.readLine());
	        int k = scan.nextInt();

	        int s = (int)Math.pow(3, k - 1);

	        int[][] triple = new int[s][k];
	        int cur = 0;
	        int counter = s;
	        int max = s;

	        for (int j = 0; j < k; j++) {
	        	for (int i = 0; i < s; i++) {
	        		if (counter != 0) {
	        		} else {
	        			cur = (cur + 1) % 3;
	        			counter = max;
	        		}
					triple[i][j] = cur;
        			counter--;
	        	}
	        	max /= 3;
	        	counter = max;
	        	cur = 0;
	        }

        	for (int i = 0; i < s; i++) {
	        	for (int j = 0; j < k; j++) {
	        		out.write(triple[i][j] + "");
	        	}
	        	out.newLine();
	        	for (int j = 0; j < k; j++) {
	        		out.write((triple[i][j] + 1) % 3 + "");
	        	}
	        	out.newLine();
	        	for (int j = 0; j < k; j++) {
	        		out.write((triple[i][j] + 2) % 3 + "");
	        	}
	        	out.newLine();
	        }
	        out.flush();

	    }catch (IOException e) {
				System.out.println(e);
		}     
	}
}