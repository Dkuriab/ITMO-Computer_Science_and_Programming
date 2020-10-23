import java.util.*;
import java.io.*;

public class H {
	public static BufferedWriter out;
	public static int n;
	public static StringBuilder ans = new StringBuilder();
	public static int counter = 0;

	public static void rec (String done, boolean isEndOne) throws IOException {
		if (done.length() == n) {
			ans.append(done + "\n");
			counter++;
			return;
		}

		rec(done + "0", false);

		if (!isEndOne) {
			rec(done + "1", true);
		}
	}


	public static void main(String[] args) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("vectors2.in")));
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("vectors2.out")));

			String[] ns = in.readLine().split(" ");
			n = Integer.parseInt(ns[0]);

			rec("", false);

			out.write(counter + "\n");
			out.write(ans.toString());
			out.close();
			in.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}