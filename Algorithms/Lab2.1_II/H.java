import java.util.*;
import java.io.*;
 
public class H {

	public static ArrayList<TreeSet<Integer>> graphBack = new ArrayList<>();
	public static ArrayList<TreeSet<Integer>> graph = new ArrayList<>();
	
	public static void main(String[] args) throws NullPointerException {
		try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("game.in")));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("game.out")));
 
            String[] data = in.readLine().split(" ");
			int n = Integer.parseInt(data[0]);
			int m = Integer.parseInt(data[1]);
			int s = Integer.parseInt(data[2]) - 1;
			
			for (int i = 0; i < n; i++) {
				graph.add(new TreeSet<Integer>());
				graphBack.add(new TreeSet<Integer>());
			}
 
 
			for (int i = 0 ; i < m; i++) {
				data = in.readLine().split(" ");

				int from = Integer.parseInt(data[0]) - 1;
				int to = Integer.parseInt(data[1]) - 1;
 
				graphBack.get(to).add(from);
				graph.get(from).add(to);
			}

			TreeSet<Integer> queue = new TreeSet<>();
			int[] answer = new int[n];
			int[] count = new int[n];

			for (int i = 0; i < n; i++) {
				if (graph.get(i).size() == 0) {
					answer[i] = -1;
					queue.add(i);
				}
			}

			while (!queue.isEmpty()) {
				int at = queue.pollFirst();

				if (answer[at] == -1) {
					for (int i : graphBack.get(at)) {
						answer[i] = 1;
						queue.add(i);
					}
				} else {
					for (int i : graphBack.get(at)) {
						count[i]++;

						if (count[i] == graph.get(i).size()) {
							answer[i] = -1;
							queue.add(i);
						}
					}
				} 
			}

			String ans = answer[s] == -1 ? "Second player wins" : "First player wins";
			out.write(ans + "");

            out.close();
            in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}