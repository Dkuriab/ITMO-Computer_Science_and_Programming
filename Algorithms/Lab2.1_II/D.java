import java.util.*;
import java.io.*;
 
public class D {
 
	public static class Road {
		public int from;
		public int weight;
		public Road(int from, int weight) {
			this.from = from;
			this.weight = weight;
		}

		public String toString() {
			return "road to " + from + " by " + weight;
		}
	}
 
	public static void main(String[] args) {
		try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
 
            String[] data = in.readLine().split(" ");
			int n = Integer.parseInt(data[0]);
			int m = Integer.parseInt(data[1]);
			int k = Integer.parseInt(data[2]);
			int s = Integer.parseInt(data[3]) - 1;
 
			ArrayList<ArrayList<Road>> graph = new ArrayList<>();
 
			for (int i = 0; i < n; i++) {
				graph.add(new ArrayList<Road>());
			}
 
			int[][] d = new int[n][k + 1];

			for (int[] row : d) {
				Arrays.fill(row, 300_000_000);
			}
 
			d[s][0] = 0;
 
			for (int i = 0 ; i < m; i++) {
				data = in.readLine().split(" ");
				int from = Integer.parseInt(data[0]) - 1;
				int to = Integer.parseInt(data[1]) - 1;
				int weight = Integer.parseInt(data[2]);
 
				graph.get(to).add(new Road(from, weight));
			}
 
			for (int t = 1; t <= k; t++) {
				for (int i = 0; i < n; i++) {
					for (Road road : graph.get(i)) {
						if (d[road.from][t - 1] != 300_000_000) {
							d[i][t] = Math.min(d[i][t], d[road.from][t - 1] + road.weight);		
						}
					}
				}
			}

			for (int i = 0 ; i < n; i++) {
				int answer = d[i][k] == 300_000_000 ? -1 : d[i][k]; 
				out.write(answer + "");
				out.newLine();
			}
 
            out.close();
            in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}