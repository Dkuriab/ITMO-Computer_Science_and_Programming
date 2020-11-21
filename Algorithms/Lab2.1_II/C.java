import java.util.*;
import java.io.*;

public class C {

	public static void main(String[] args) {
		try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

            String[] data = in.readLine().split(" ");
			int n = Integer.parseInt(data[0]);

			int[][] graph = new int[n][n];

			long[] d = new long[n];
			int[] parent = new int[n];
			Arrays.fill(parent, -1);

			for (int i = 1; i < n; i++) {
				d[i] = Integer.MAX_VALUE;
			}

			for (int i = 0 ; i < n; i++) {
				data = in.readLine().split(" ");
				for (int j = 0; j < n; j++) {
					graph[i][j] = Integer.parseInt(data[j]);
				}
			}

			int last = -1;
			boolean isChanged = false;

			for (int t = 0; t < n; t++) {
				// System.out.println("t: " + t);
				last = -1;
				isChanged = false;

				for (int i = 0; i < n; i++) {
					for (int j = 0; j < n; j++) {
						if (graph[j][i] != 100_000) { // there is edge
							long before = d[i];
							d[i] = Math.min(d[i], d[j] + graph[j][i]);
							if (d[i] != before) {
								isChanged = true;
								parent[i] = j;
								last = i;
							}
						}
					}
				}
			}

			if (!isChanged) {
				out.write("NO");
			} else {
				for (int i = 0; i < n; i++) {
					last = parent[last];
				}

				ArrayList<Integer> answer = new ArrayList<>();

				int at = last;
				answer.add(at);
				at = parent[at];

				while (true) {
					if (at == last) {
						break;
					} 

					answer.add(at);
					at = parent[at];

				}

				Collections.reverse(answer);
		 		out.write("YES");
		 		out.newLine();
		 		out.write(answer.size() + "");
		 		out.newLine();

		 		for (int a : answer) {
		 			out.write((a + 1) + " ");
		 		}
			}


            out.close();
            in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}