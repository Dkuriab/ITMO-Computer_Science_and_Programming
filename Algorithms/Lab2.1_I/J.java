import java.util.*;
import java.io.*;

public class J {

	public static class Road implements Comparable<Road> {
		public int a;
		public int b;
		public int w;

		public Road(int a, int b, int w) {
			this.a = a;
			this.b = b;
			this.w = w;
		}

		@Override
	    public int compareTo(Road r) {
	        return w - r.w;
	    }

	    public String toString() {
	    	return w + "";
	    }
	}

	public static int[] unit;

	public static int get(int v) {
		if (v == unit[v]) {
			return v;
		} else {
			unit[v] = get(unit[v]);
			return unit[v];
		}
	}
	
	public static void unite(int a, int b) {
		a = get(a);
		b = get(b);
		if (a != b) {
			if (Math.random() > 0.5) {
				unit[b] = a;
			} else {
				unit[a] = b;
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
            Road[] graph = new Road[m];
            unit = new int[n];

            for (int i = 0; i < m; i++) {
            	data = in.readLine().split(" ");
            	int a = Integer.parseInt(data[0]) - 1;
            	int b = Integer.parseInt(data[1]) - 1;
				int w = Integer.parseInt(data[2]);
            	graph[i] = new Road(a, b, w);
            }

            long answer = 0;

            Arrays.sort(graph);
            int[] pos = new int[n];
			for (int i = 0; i < n; i++) {
				unit[i] = i;
			}
			for (int i = 0; i < m; i++) {
				if (get(graph[i].a) != get(graph[i].b)) {
					answer += graph[i].w;
					unite(graph[i].a, graph[i].b);
				}
			}

			out.write(answer + "");
            out.close();
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }		
	}
}