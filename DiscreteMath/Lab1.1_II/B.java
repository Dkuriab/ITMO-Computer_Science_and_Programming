import java.util.Arrays;
import java.util.*;
import java.io.*;

public class B {
	public static void main(String[] args) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("bwt.in")));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("bwt.out")));

			String s = in.readLine();

			int n = s.length();

			StringBuilder[] words = new StringBuilder[n];
			for (int i = 0; i < n; i++) {
				words[i] = new StringBuilder();
			}

			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					words[i].append(s.charAt((j + i) % n));
				}
			}
			Arrays.sort(words);
			for (int i = 0; i < n; i++) {
				out.write(words[i].charAt(n-1));
			}
			out.close();
			in.close();
		} catch (IOException e) {
			System.err.println(e);
		} 
	}
}