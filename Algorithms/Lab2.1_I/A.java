import java.io.*;
import java.util.*;

public class A {

	public static ArrayList<HashSet<Integer>> graph = new ArrayList<>();
	public static ArrayList<Integer> answer = new ArrayList<>();
	public static HashSet<Integer> currentlyPassed = new HashSet<>();
	public static boolean[] used;
	public static boolean shit = false;

	public static void dfs(int node) {
		used[node] = true;
		currentlyPassed.add(node);

		for (int to : graph.get(node)) {
			if (!used[to]) {
				dfs(to);
			} else if (currentlyPassed.contains(to)) {
				shit = true;
				return;
			}
		}
		currentlyPassed.remove(node);
		answer.add(node + 1);
	}

	public static void main(String[] args) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

			String[] data = in.readLine().split(" ");

			int n = Integer.parseInt(data[0]);
			int m = Integer.parseInt(data[1]);
			used = new boolean[n];

			for (int i = 0 ; i < n; i++) {
				graph.add(new HashSet<Integer>());
			}

			HashSet<String> roads = new HashSet<>();

			for (int i = 0; i < m; i++) {
				String road = in.readLine();
				if (roads.add(road)) {

					String adge[] = road.split(" ");
					int from = Integer.parseInt(adge[0]) - 1;
					int to = Integer.parseInt(adge[1]) - 1;

					graph.get(from).add(to);
				}
			}

			for (int i = 0; i < n; i++) {
				if (!used[i]) {
					dfs(i);
				}
			}

			if (shit) {
				out.write(-1 + "");
			} else {
				for (int i = 0; i < answer.size(); i++) {
					out.write(answer.get(answer.size() - i - 1) + " ");
				}
			}
			out.close();
			in.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}