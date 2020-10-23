import java.util.*;
import java.io.*;

public class F {

	public static void main(String[] args) {
		try {
	        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("vectors.in"), "UTF-8"));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("vectors.out"), "UTF-8"));
	        
	        Scanner scan = new Scanner (in.readLine());
	        int k = scan.nextInt();

	        int s = 1 << k;

	        int[][] triple = new int[s][k];
	        int cur = 0;
	        int counter = s/2;
	        int max = s/2;

	        for (int j = 0; j < k; j++) {
	        	for (int i = 0; i < s; i++) {
	        		if (counter == 0) {
	        			cur = (cur + 1) % 2;
	        			counter = max;
	        		}
					triple[i][j] = cur;
        			counter--;
	        	}
	        	max /= 2;
	        	counter = max;
	        	cur = 0;
	        }
	        int zero = 0;
	        StringBuilder done = new StringBuilder("\n");

        	for (int i = 0; i < s; i++) {
        		boolean flag = true;
	        	for (int j = 0; j < k - 1; j++) {
	        		if (triple[i][j] + triple[i][j+1] == 2){
	        			flag = false;
	        		}
	        	}
	        	if (flag) {
	        		zero++;
	        		for (int y = 0; y < k; y++) {
	        			done.append(triple[i][y]);
	        		}
	        		done.append("\n");
	        	}
	        }

	        out.write(zero + done.toString());
	        out.flush();

	    }catch (IOException e) {
				System.out.println(e);
		}     
	}
}