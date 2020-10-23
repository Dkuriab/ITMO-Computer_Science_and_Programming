import java.io.*;
import java.util.*;

public class C {
	public static boolean[] achievable;
	public static int[][] graph;
	public static int[][] revGraph;
	public static boolean[] ends;
	public static boolean[] used;
	public static int[] type;
	public static LinkedList<Integer> topological = new LinkedList<Integer>();
	public static boolean isInf = false;

	public static void finder(int cur) {
		achievable[cur] = true;
		for (int i = 97; i < 123; i++) {

			if (!achievable[revGraph[cur][i]] && revGraph[cur][i] != 0) {
				finder(revGraph[cur][i]);
			}
		}
	}

	public static void checkCicles(int cur) {
	    type[cur] = 1; 
          
	    for (int i = 97; i < 123; i++) {
	    	int next = graph[cur][i];	
		    if (next != 0) {
		        if (type[next] == 0) {
		            checkCicles(next);
		        } else if (type[next] == 1 && achievable[next]) {
		            isInf = true;
		            break; 
		        }
		    }
		}
		type[cur] = 5;
	}

	public static void dfs(int cur) {
		used[cur] = true;
		for (int i = 97; i < 123; i++) {
			if (!used[graph[cur][i]] && graph[cur][i] != 0) {
				dfs (graph[cur][i]);
			}
		}
		topological.addFirst(cur);
	}
	 
	public static void sort(int n) {
		used = new boolean[n + 1];
		for (int i = 1; i < n + 1; i++) {
			if (!used[i]) {
				dfs(i);
			}
		}
	}

	public static void main(String[] args) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("problem3.in")));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("problem3.out")));

			String[] parametrs = in.readLine().split(" ");
			int n = Integer.parseInt(parametrs[0]); // number of points
			int m = Integer.parseInt(parametrs[1]); // number of roads
			int k = Integer.parseInt(parametrs[2]); // number of ends
			int mod = 1_000_000_007;

			type = new int[n + 1];
			graph = new int[n + 1][150];
			revGraph = new int[n + 1][150];
			ends = new boolean[n + 1];
			achievable = new boolean[n + 1];

			String[] endsString = in.readLine().split(" ");
			for (int i = 0; i < k; i++) {
				ends[Integer.parseInt(endsString[i])] = true;
			}

			for (int i = 0; i < m; i++) {
				String[] cur = in.readLine().split(" ");
				int a = Integer.parseInt(cur[0]);
				int b = Integer.parseInt(cur[1]);
				char c = cur[2].charAt(0);
				graph[a][(int)c] = b;
				revGraph[b][(int)c] = a;
			}

			for (int i = 1; i < n + 1; i++) {
				if (ends[i]) {
					finder(i);
				}
			}

			checkCicles(1);

			if (isInf) {
				out.write("-1");
			} else {
				long ans = 0;

				sort(n);
				Object[] top = topological.toArray();

				long[] val = new long[n + 1];
			    val[1] = 1;

			    for (int i = 0; i < top.length; i++) {
		        	int ver = (int)top[i];
			        for (int j = 97; j < 123; j++) {
			        	if (graph[ver][j] != 0) {
			        		val[graph[ver][j]] = (val[graph[ver][j]] + val[ver]) % mod;
			        	}
			        }
			    }

			    for(int i = 1; i < n + 1; i++) {
			     	if (ends[i]) {
				    	ans = (ans + val[i]) % mod;
			     	}
				}

				ans = ans % mod;	
				out.write(ans + "");
			}

			out.flush();
			out.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}