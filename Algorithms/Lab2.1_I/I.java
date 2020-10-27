import java.io.*;
import java.util.*;

public class I {

	public static class Point {
		public int x;
		public int y;
		public Point (int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	public static int dist(Point a, Point b) {
		return (a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y);
	}

	public static void main(String[] args) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

			int n = Integer.parseInt(in.readLine());
			boolean[] used = new boolean[n];
			Point[] points = new Point[n];
			double[] minFrom = new double[n + 1];
			int[] bestDest = new int[n];
			double answer = 0;
			Arrays.fill(minFrom, Integer.MAX_VALUE);
			Arrays.fill(bestDest, -1);

			for (int i = 0; i < n; i++) {
				String[] point = in.readLine().split(" ");
				int x = Integer.parseInt(point[0]);
				int y = Integer.parseInt(point[1]);
				points[i] = new Point(x, y);
			}

			minFrom[0] = 0;
			for (int i = 0; i < n; i++) {
				int cur = n;

				for (int j = 0; j < n; j++) {
					if (!used[j] && minFrom[j] < minFrom[cur]) {
						cur = j;
					}
				}
				used[cur] = true;
			 
				if (bestDest[cur] != -1) {
					answer += Math.sqrt(dist(points[cur], points[bestDest[cur]]));
				}

				for (int dest = 0; dest < n; dest++) {
					int f = dist(points[cur], points[dest]);
					if (f < minFrom[dest]) {
						bestDest[dest] = cur;
						minFrom[dest] = f;
					}
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