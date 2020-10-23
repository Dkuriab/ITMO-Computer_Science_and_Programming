import java.util.*;
import java.io.*;

public class E {

	public static void main(String[] args) {
		try {
	        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("telemetry.in"), "UTF-8"));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("telemetry.out"), "UTF-8"));
	        
	        Scanner scan = new Scanner (in.readLine());
	        int n = scan.nextInt();// code length
	        int k = scan.nextInt();// razriadnost
	        
	        Set<String> codes = new HashSet<>();

	        int power = (int)Math.pow(k, n);
	        int[][] done = new int[power][n];
	        
	        int cur = 0;
	        int counter = 1;
	        int max = 1;
	        boolean flag = true;

	        for (int j = 0; j < n; j++) {
	        	for (int i = 0; i < power; i++) {
	        		if (counter == 0) {
	        			cur = (flag ? cur + 1 : cur - 1);
	        			counter = max;
	        		}
					done[i][j] = cur;

        			counter--;
	        		
	        		if (cur == (k - 1) && flag && counter == 0) {
	        			flag = false;
	        			counter = max;
	        		}
	        		if (cur == 0 && !flag && counter == 0) {
	        			flag = true;
	        			counter = max;
	        		}

	        	}
	        	flag = true;
	        	max *= k;
	        	counter = max;
	        	cur = 0;
	        }
	        
	        for (int i = 0; i < power; i++) {
	        	for (int j = 0; j < n; j++) {
	        		out.write(done[i][j] + "");
	        	}
	        	out.newLine();
	        }


	        out.flush();

	    }catch (IOException e) {
				System.out.println(e);
		}     
	}
}