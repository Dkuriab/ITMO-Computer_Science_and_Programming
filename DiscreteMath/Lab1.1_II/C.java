import java.util.Arrays;
import java.util.*;
import java.io.*;

public class C {
	public static void main(String[] args) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("mtf.in")));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("mtf.out")));

			String s = in.readLine();

			int n = s.length();

			LinkedList<String> alfabet = new LinkedList<>();
			
			for (int i = 0; i < 26; i++) {
				alfabet.addLast((char)('a' + i) + "");
			}

			for (int i = 0; i < n; i++) {

				out.write((alfabet.indexOf(s.charAt(i) + "") + 1) + " ");
				String tmp = s.charAt(i) + "";
				alfabet.remove(tmp);
				alfabet.addFirst(tmp);
			}
			out.close();
			in.close();
		} catch (IOException e) {
			System.err.println(e);
		} 
	}
}