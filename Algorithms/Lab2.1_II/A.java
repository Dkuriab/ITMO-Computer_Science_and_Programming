import java.util.*;
import java.io.*;

public class A {

	public static void main(String[] args) {
		try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

			int n = Integer.parseInt(in.readLine());
			int[][] graph = new int[n][n];

			for (int i = 0 ; i < n; i++) {
				String[] data = in.readLine().split(" ");
				for (int j = 0; j < n; j++) {
					graph[i][j] = Integer.parseInt(data[j]);
				}
			}

			for (int phase = 0; phase < n; phase++) {
				for (int i = 0; i < n; i++) {
					for (int j = 0; j < n; j++) {
						int s = graph[i][phase] + graph[phase][j];
						if (graph[i][j] > s) {
							graph[i][j] = s;
						}
					}
				}
			}

			for (int i = 0 ; i < n; i++) {
				for (int j = 0; j < n; j++) {
					out.write(graph[i][j] + " ");
				}
				out.newLine();
			}

            out.close();
            in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}