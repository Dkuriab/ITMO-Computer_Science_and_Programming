import java.util.*;
import java.io.*;

public class D {

	public static ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
	public static boolean[] used;
    public static ArrayList<String> answer = new ArrayList<>();
    public static int[] lowestUp;
    public static int[] inTime;
    public static int timer = 0;
    public static int colorCur = 0;
	public static int[] color;
	public static HashSet<String> multyRoads = new HashSet<>();

	public static LinkedList<Integer> stack = new LinkedList<>();

	public static void drow(int node) {
		colorCur++;
		int cur = -1;
		while(cur != node && !stack.isEmpty()) {
			cur = stack.poll();
			color[cur] = colorCur;
		}
	}
	
	public static void dfs(int node, int parent) {
        stack.push(node);
        used[node] = true;
        inTime[node] = timer;
        lowestUp[node] = timer;
        timer++;
        for (int u : graph.get(node)) {
            if (used[u] && u != parent) {
                lowestUp[node] = Math.min(lowestUp[node], inTime[u]);
            }
            if (!used[u]) {
                dfs(u, node);
                lowestUp[node] = Math.min(lowestUp[node], lowestUp[u]);
                if (lowestUp[u] > inTime[node] && !multyRoads.contains(u + " " + node)) {
                    drow(u);
                    //node - u is a bridge
                }
            }
        }
    }

	public static void main(String[] args) {
		try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

            String[] data = in.readLine().split(" ");

			int n = Integer.parseInt(data[0]);
			int m = Integer.parseInt(data[1]);

			for (int i = 0 ; i < n; i++) {
				graph.add(new ArrayList<>());
			}

			used = new boolean[n];
			inTime = new int[n];
			lowestUp = new int[n];
			color = new int[n];

			HashSet<String> roads = new HashSet<>();

			for (int i = 0; i < m; i++) {
				String road = in.readLine();
				String[] edges = road.split(" ");
				int from = Integer.parseInt(edges[0]) - 1;
				int to = Integer.parseInt(edges[1]) - 1;

				String ft = from + " " + to;
				String tf = to + " " + from;

				if (!roads.add(ft)) {
					multyRoads.add(ft);
				}

				if (!roads.add(tf)) {
					multyRoads.add(tf);
				}

				graph.get(to).add(from);
				graph.get(from).add(to);
			}

			// System.out.println("multyRoads: " + multyRoads);

            for (int i = 0; i < n; i++) {
                if (!used[i]) {
                    dfs(i, -1);
                    drow(i);
                }
            }

            out.write(colorCur + "");
            out.newLine();

            for (int i = 0; i < n; i++) {
            	out.write(color[i] + " ");
            }
            out.close();
            in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}