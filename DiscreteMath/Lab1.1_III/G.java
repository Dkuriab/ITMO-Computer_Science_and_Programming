import java.util.*;
import java.io.*;

public class G {
    public static int k;
    public static BufferedReader in;
    public static BufferedWriter out;

	public static void rec (boolean[] a, StringBuilder b) {

		for (int i = 1; i <= k; i++) {

			if (!a[i]) {
				b.append(i + " ");
				a[i] = true;
				if (b.length() == 2*k) {
					try{
						out.write(b.toString() + "\n");
					} catch (IOException e) {
						System.out.println(e);
					}
				}
				rec(a, b);
				b.delete(b.length() - 2, b.length());
				a[i] = false;
			}	
		}
	}

	public static void main(String[] args) {
		try {
	        in = new BufferedReader(new InputStreamReader(new FileInputStream("permutations.in"), "UTF-8"));
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("permutations.out"), "UTF-8"));
	        
	        Scanner scan = new Scanner (in.readLine());
	        k = scan.nextInt();


	        rec(new boolean[k+1], new StringBuilder());

			out.flush();

	    }catch (IOException e) {
				System.out.println(e);
		}     
	}
}