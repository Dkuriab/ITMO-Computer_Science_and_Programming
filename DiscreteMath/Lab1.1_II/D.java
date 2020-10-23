import java.util.*;
import java.io.*;

public class D {
	public static void main(String[] args) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("lzw.in")));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("lzw.out")));

			String s = in.readLine();
			int n = s.length();
			

			LinkedList<String> alfabet = new LinkedList<>();
			
			for (int i = 0; i < 26; i++) {
				alfabet.addLast((char)('a' + i) + "");
			}
			
			char last = s.charAt(0);
			String buffer = last + "";
			int i = 1;

			while (i != n) {
				last = s.charAt(i);
				i++;
				if (alfabet.contains(buffer + last)) {
					buffer = buffer + last;
				} else {
					out.write(alfabet.indexOf(buffer) + " ");
					alfabet.addLast(buffer + last);
					buffer = last + "";
				}
			}
			out.write(alfabet.indexOf(buffer) + " ");

			out.close();
			in.close();
		} catch (IOException e) {
			System.err.println(e);
		} 
	}
}