import java.util.*;
import java.io.*;
 
public class I {

	public static ArrayList<TreeSet<Integer>> graphBack = new ArrayList<>();
	public static ArrayList<TreeSet<Integer>> graph = new ArrayList<>();
	
	public static void main(String[] args) throws NullPointerException {
		try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
 			
 			try {
				while (true) {            
					String[] data = in.readLine().split(" ");
					int n = Integer.parseInt(data[0]);
					int m = Integer.parseInt(data[1]);
					
					graphBack = new ArrayList<>();
					graph = new ArrayList<>();
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

					boolean[] used = new boolean[n];

					while (!queue.isEmpty()) {
						int at = queue.pollFirst();
						used[at] = true;

						if (answer[at] == -1) {
							for (int i : graphBack.get(at)) {
								if (!used[i]) {
									answer[i] = 1;
									queue.add(i);
								}
							}
						} else {
							for (int i : graphBack.get(at)) {
								count[i]++;

								if (count[i] == graph.get(i).size()) {
									if (!used[i]) {
										answer[i] = -1;
										queue.add(i);
									}
								}
							}
						} 
					}

					for (int s = 0; s < n; s++) {
						String ans = answer[s] == -1 ? "SECOND" : answer[s] == 0 ? "DRAW" : "FIRST";
						out.write(ans + "");
						out.newLine();
					}
					out.newLine();
				}
			} catch (Exception e) {
			}

            out.close();
            in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}