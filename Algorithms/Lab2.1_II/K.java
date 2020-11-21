import java.util.*;
import java.io.*;
 
public class K {

	public static ArrayList<ArrayList<Road>> graph = new ArrayList<>();
	public static int[] grandi;
	public static int n;
	public static int r;
	
	public static class Road {
		public int to;
		public int number;
		public Road (int to, int number) {
			this.to = to;
			this.number = number;
		}
	}

	public static int calcGrandi(int node, int parent) {
		if (grandi[node] == -1) {
			grandi[node] = 0;
			for (Road road : graph.get(node)) {
				int to = road.to;
				if (to != parent) {
					grandi[node] = (grandi[node] ^ (calcGrandi(to, node) + 1));
				}
			}
		}
		return grandi[node];
	}

	public static int solve(int node, int parent, int needs) {

		for (Road road : graph.get(node)) {
			int to = road.to;
			if (to != parent) {
				if ((grandi[node] ^ (grandi[to] + 1)) == needs) {
					return road.number;
				}
			}
		}

		for (Road road : graph.get(node)) {
			int to = road.to;
			if (to != parent) {
				int ans = solve(to, node, (grandi[node] ^ (grandi[to] + 1) ^ needs) - 1);
				if (ans != -1) {
					return ans;
				}
			}
		}

		return -1;
	}

	public static void main(String[] args) throws NullPointerException {
		try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
 		
            String[] data = in.readLine().split(" ");
			n = Integer.parseInt(data[0]);
			r = Integer.parseInt(data[1]) - 1;
			grandi = new int[n];
			
			for (int i = 0; i < n; i++) {
				graph.add(new ArrayList<Road>());
			}
 
			for (int i = 0 ; i < n - 1; i++) {
				data = in.readLine().split(" ");

				int from = Integer.parseInt(data[0]) - 1;
				int to = Integer.parseInt(data[1]) - 1;
 
				graph.get(from).add(new Road(to, i));
				graph.get(to).add(new Road(from, i));
			}

			Arrays.fill(grandi, -1);
			calcGrandi(r, -1);
			int ans = solve(r, -1, 0);

			if (ans == -1) {
				out.write(2 + "");
			} else {
				out.write(1 + "");
				out.newLine();
				out.write((ans + 1) + "");
			}

            out.close();
            in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}