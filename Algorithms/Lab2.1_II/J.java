import java.util.*;
import java.io.*;
 
public class J {

	public static ArrayList<TreeSet<Integer>> graphBack = new ArrayList<>();
	public static ArrayList<TreeSet<Integer>> graph = new ArrayList<>();
	public static ArrayList<Integer> sorted = new ArrayList<>();
	public static boolean[] used;
	public static int n;
	public static int m;
	

	public static void dfs (int v) {
		used[v] = true;
		for (int to : graph.get(v)) {
			if (!used[to])
				dfs(to);
		}
		sorted.add(v);
	}
 
	public static void topSort() {
		used = new boolean[n];

		for (int i = 0; i < n; i++) {
			if (!used[i]) {
				dfs(i);
			}
		}
	}

	public static void main(String[] args) throws NullPointerException {
		try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
 
            String[] data = in.readLine().split(" ");
			n = Integer.parseInt(data[0]);
			m = Integer.parseInt(data[1]);
			used = new boolean[n];
			
			for (int i = 0; i < n; i++) {
				graph.add(new TreeSet<Integer>());
				graphBack.add(new TreeSet<Integer>());
			}
 
 
			for (int i = 0 ; i < m; i++) {
				data = in.readLine().split(" ");

				int from = Integer.parseInt(data[0]) - 1;
				int to = Integer.parseInt(data[1]) - 1;
 
				graph.get(from).add(to);
				graphBack.get(to).add(from);
			}

			TreeSet<Integer> queue = new TreeSet<>();
			int[] Grandi = new int[n];
			int[] answer = new int[n];
			int[] count = new int[n];
			Arrays.fill(Grandi, -1);


			topSort();

			for (int i : sorted) {
				boolean[] was = new boolean[n];
				for (int to : graph.get(i)) {
					was[Grandi[to]] = true;
				}
				for (int j = 0; j < n; j++) {
					if (!was[j]) {
						Grandi[i] = j;
						break;
					}
				}
			}

			
			for (int i = 0; i < n; i++) {
				out.write(Grandi[i] + "");
				out.newLine();
			}

            out.close();
            in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}