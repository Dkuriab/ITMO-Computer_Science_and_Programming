import java.util.*;
import java.io.*;

public class B {
	public static void main(String[] args) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("request.in")));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("request.out")));

			String[] kn = in.readLine().split(" ");
			int n = Integer.parseInt(kn[0]);
			boolean[] used = new boolean[n];

			int[] start = new int[n];
			int[] end = new int[n];

			for (int i = 0; i < n; i++) {
				String[] time = in.readLine().split(" ");
				start[i] = Integer.parseInt(time[0]);
				end[i] = Integer.parseInt(time[1]);
			}

			int lastEnd = 0;
			int ans = 0;

			for (int i = 0; i < n; i++) {

				int endChoose = Integer.MAX_VALUE;
				int choosen = -1;
				for (int j = 0; j < n; j++) {
					if (start[j] >= lastEnd && end[j] < endChoose) {
						choosen = j;
						endChoose = end[j];
					}
				}
				if (choosen != -1) {
					lastEnd = end[choosen];
					ans++;
				}
			}

			out.write(ans + "");
			out.close();
			in.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}