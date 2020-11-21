import java.util.*;
import java.io.*;

public class B {

	public static class Road {
		public int to;
		public int weight;
		public Road(int to, int weight) {
			this.to = to;
			this.weight = weight;
		}
	}

	public static void main(String[] args) {
		try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

            String[] data = in.readLine().split(" ");
			short n = Short.parseShort(data[0]);
			int m = Integer.parseInt(data[1]);

			ArrayList<ArrayList<Road>> graph = new ArrayList<>();

			for (int i = 0; i < n; i++) {
				graph.add(new ArrayList<Road>());
			}

			int[] d = new int[n];
			d[0] = 0;

			for (short i = 1; i < n; i++) {
				d[i] = 300_000_000;
			}

			for (int i = 0 ; i < m; i++) {
				data = in.readLine().split(" ");
				int from = Integer.parseInt(data[0]) - 1;
				int to = Integer.parseInt(data[1]) - 1;
				short weight = Short.parseShort(data[2]);

				graph.get(from).add(new Road(to, weight));
				graph.get(to).add(new Road(from, weight));
			}

			while (true) {
				boolean isChanged = false;
				for (int i = 0; i < n; i++) {
					int before = d[i];
					for (Road road : graph.get(i)) {
						d[i] = Math.min(d[i], d[road.to] + road.weight);		
					}
					if (d[i] != before) {
						isChanged = true;
					}
				}

				if (!isChanged) {
					break;
				}
			}

			for (short i = 0 ; i < n; i++) {
				out.write(d[i] + " ");
			}

            out.close();
            in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}