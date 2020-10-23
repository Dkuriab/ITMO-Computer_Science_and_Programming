import java.util.*;
import java.io.*;

public class A {
	public static void main(String[] args) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("cobbler.in")));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("cobbler.out")));

			String[] kn = in.readLine().split(" ");
			int k = Integer.parseInt(kn[0]);
			int n = Integer.parseInt(kn[1]);

			String[] data = in.readLine().split(" ");
			int[] time = new int[n];

			for (int i = 0; i < n; i++) {
				time[i] = Integer.parseInt(data[i]);
			}

			Arrays.sort(time);

			int ans = 0;
			while (k > 0 && ans < n) {
				k -= time[ans++];
			}

			ans = k >= 0 ? ans : ans - 1;

			out.write(ans + "");
			out.close();
			in.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}