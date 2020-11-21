import java.util.*;
import java.io.*;
 
public class E {
 
	public static class Road {
		public int node;
		public long weight;
		public Road(int node, long weight) {
			this.node = node;
			this.weight = weight;
		}

		public String toString() {
			return "road to " + node + " by " + weight;
		}
	}

	public static boolean[] used;
	public static boolean[] reached;
	// public static long INF = 9_000_000_000_000_000_000L;
	public static long INF = Long.MAX_VALUE - 5;
	public static ArrayList<ArrayList<Road>> graphBack = new ArrayList<>();
	public static ArrayList<ArrayList<Road>> graph = new ArrayList<>();

	public static void dfs(int node) {
		used[node] = true;

		for (Road r : graph.get(node)) {
			if (!used[r.node]) {
				dfs(r.node);
			}
		}
	}

	public static void dfsReached(int node) {
		reached[node] = true;

		for (Road r : graph.get(node)) {
			if (!reached[r.node]) {
				dfsReached(r.node);
			}
		}
	}
 
	public static void main(String[] args) throws NullPointerException {
		try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
 
            String[] data = in.readLine().split(" ");
			int n = Integer.parseInt(data[0]);
			int m = Integer.parseInt(data[1]);
			int s = Integer.parseInt(data[2]) - 1;
			used = new boolean[n];
			reached = new boolean[n];

 
			for (int i = 0; i < n; i++) {
				graph.add(new ArrayList<Road>());
				graphBack.add(new ArrayList<Road>());
			}
 
			long[] d = new long[n];
			Arrays.fill(d, INF);
			d[s] = 0;
 
			for (int i = 0 ; i < m; i++) {
				data = in.readLine().split(" ");
				int from = Integer.parseInt(data[0]) - 1;
				int to = Integer.parseInt(data[1]) - 1;
				long weight = Long.parseLong(data[2]);
 
				graphBack.get(to).add(new Road(from, weight));
				graph.get(from).add(new Road(to, weight));
			}
			dfs(s);
 
 			boolean[] changed = new boolean[n];

			for (int t = 0; t < m + 2; t++) {

				Arrays.fill(changed, false);

				for (int i = 0; i < n; i++) {
					for (Road road : graphBack.get(i)) {
						long before = d[i];

						if (d[road.node] != INF && d[i] > d[road.node] + road.weight) {
							d[i] = Math.max(-INF, d[road.node] + road.weight);
						}

						// d[i] = Math.min(d[i], d[road.node] + road.weight);	

						if (d[i] != before) {
							changed[i] = true;
						}
					}
				}
			}

			for (int i = 0; i < n; i++) {
				if (changed[i]) {
					dfsReached(i);
				}
			}


			for (int i = 0 ; i < n; i++) {
				if (!used[i]) {
					out.write("*");
				} else if (reached[i]) {
					out.write("-");
				} else {
					out.write(d[i] + "");
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