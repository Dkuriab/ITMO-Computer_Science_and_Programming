import java.util.*;
import java.io.*;

public class H {
	public static int[][] graph;
	public static boolean[] used;
	public static int n;
	public static int count = 0;

	public static void dfs(int node, int fuel) {
		used[node] = true;
		count++;
		for (int i = 0; i < n; i++) {
			if (!used[i] && graph[node][i] <= fuel) {
				dfs(i, fuel);
			}
		}
	}

	public static void dfsBack(int node, int fuel) {
		used[node] = true;
		count++;
		for (int i = 0; i < n; i++) {
			if (!used[i] && graph[i][node] <= fuel) {
				dfsBack(i, fuel);
			}
		}
	}

	public static boolean check(int fuel) {
		used = new boolean[n];
		count = 0;
		dfs(0, fuel);
		int a = count;

		used = new boolean[n];
		count = 0;
		dfsBack(0, fuel);

		// System.out.println(fuel + " : " + a  + " :: " + count);
		return (count == n) && (a == n);
	}

	public static void main(String[] args) {
		try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("avia.in")));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("avia.out")));

            n = Integer.parseInt(in.readLine());
            graph = new int[n][n];

            for (int i = 0; i < n; i++) {
            	String[] data = in.readLine().split(" ");
            	for (int j = 0; j < n; j++) {
            		graph[i][j] = Integer.parseInt(data[j]);
            	}
            } 

            int l = 0;
            int r = Integer.MAX_VALUE;

            if (n == 1) {
            	out.write(0 + "");
            	out.close();
            	in.close();
            	return;
            }
            for (int i = 0; i < 50; i++) {
            	int m = (l + r) / 2;
            	// System.out.println(m);
            	if (check(m)) {
            		r = m;
            	} else {
            		l = m;
            	}
            }

            out.write(r + "");

            out.close();
            in.close();
        } catch (IOException e) {
        	e.printStackTrace();
        }
	}
}