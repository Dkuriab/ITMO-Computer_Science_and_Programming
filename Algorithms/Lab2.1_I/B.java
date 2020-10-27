import java.io.*;
import java.util.*;

public class B {

    public static ArrayList<HashSet<Integer>> graph = new ArrayList<>();
    public static boolean[] used;
    public static ArrayList<String> answer = new ArrayList<>();
    public static int[] lowestUp;
    public static int[] inTime;
    public static int timer = 0;

    public static void dfs(int node, int parent) {
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
                if (lowestUp[u] > inTime[node]) {
                    answer.add(node + " " + u);
                }
            }
        }
    }

    public static void main(String[]args){
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

            String[] data = in.readLine().split(" ");

			int n = Integer.parseInt(data[0]);
			int m = Integer.parseInt(data[1]);

			for (int i = 0 ; i < n; i++) {
				graph.add(new HashSet<>());
			}

			used = new boolean[n];
			inTime = new int[n];
			lowestUp = new int[n];

			HashMap<String, Integer> roads = new HashMap<>();

			for (int i = 0; i < m; i++) {
				String road = in.readLine();
				String[] edge = road.split(" ");
				int from = Integer.parseInt(edge[0]) - 1;
				int to = Integer.parseInt(edge[1]) - 1;

				graph.get(to).add(from);
				graph.get(from).add(to);

				roads.put(from + " " + to, i + 1);
				roads.put(to + " " + from, i + 1);
			}

            for (int i = 0; i < n; i++) {
                if (!used[i]) {
                    dfs(i, -1);
                }
            }

            out.write(answer.size() + "");
            out.newLine();

            TreeSet<Integer> answerSorted = new TreeSet<>();
            for (String ans : answer) {
                answerSorted.add(roads.get(ans));
            }

            for (int i : answerSorted) {
            	out.write(i + " ");
            }

            out.close();
            in.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
