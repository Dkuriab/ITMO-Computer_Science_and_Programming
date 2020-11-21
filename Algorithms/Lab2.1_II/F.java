import java.util.*;
import java.io.*;
 
public class F {

	public static boolean[] used;
	public static int n;
	public static int m;
	public static long[] d;
	public static boolean[] reached;
	public static long INF = Long.MAX_VALUE - 5;
	public static ArrayList<ArrayList<Road>> graphBack = new ArrayList<>();
	public static ArrayList<ArrayList<Road>> graph = new ArrayList<>();
 
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

	public static class NodeMinRoad implements Comparable<NodeMinRoad> {
		public int node;
		public long road;

		public NodeMinRoad(int node, long road) {
			this.node = node;
			this.road = road;
		}

		public int compareTo(NodeMinRoad x) {
			if (road < x.road) {
				return -1;
			} else if (road == x.road && node == x.node) {
				return 0;
			}
			return 1;
		}

		public String toString() {
			return node + " by: " + road;
		}
	}

	public static void deikFrom(int s) {
		TreeSet<NodeMinRoad> state = new TreeSet<>();
		d = new long[n];
		Arrays.fill(d, INF);

		d[s] = 0;
		state.add(new NodeMinRoad(s, 0));

		while(!state.isEmpty()) {
			NodeMinRoad x = state.pollFirst();

			for (Road road : graph.get(x.node)) {
				if (d[road.node] > x.road + road.weight) {
					state.remove(new NodeMinRoad(road.node, d[road.node]));
					d[road.node] = x.road + road.weight;
					state.add(new NodeMinRoad(road.node, x.road + road.weight));
				}
			}
		}
	}
 
	public static void main(String[] args) throws NullPointerException {
		try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
			TreeSet<NodeMinRoad> test = new TreeSet<>();
 
            String[] data = in.readLine().split(" ");
			n = Integer.parseInt(data[0]);
			m = Integer.parseInt(data[1]);
			
			for (int i = 0; i < n; i++) {
				graph.add(new ArrayList<Road>());
				graphBack.add(new ArrayList<Road>());
			}
 
 
			for (int i = 0 ; i < m; i++) {
				data = in.readLine().split(" ");
				int from = Integer.parseInt(data[0]) - 1;
				int to = Integer.parseInt(data[1]) - 1;
				long weight = Long.parseLong(data[2]);
 
				graphBack.get(to).add(new Road(from, weight));
				graph.get(from).add(new Road(to, weight));

				graphBack.get(from).add(new Road(to, weight));
				graph.get(to).add(new Road(from, weight));
			}

			data = in.readLine().split(" ");
			int a = Integer.parseInt(data[0]) - 1;
			int b = Integer.parseInt(data[1]) - 1;
			int c = Integer.parseInt(data[2]) - 1;
 	
 			int existCount = 0;
 			long ab = 0;
 			long ac = 0;
 			deikFrom(a);
			if (d[b] != INF) {
				existCount++;
				ab = d[b];
			}

			if (d[c] != INF) {
				existCount++;
				ac = d[c];
			}

			long bc = 0;
 			deikFrom(b);
			if (d[c] != INF) {
				existCount++;
				bc = d[c];
			}

			long answer = existCount > 1 ? Math.min(ab + ac, Math.min(ab + bc, ac + bc)) : -1;

			out.write(answer + "");
 
            out.close();
            in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}