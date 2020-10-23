import java.util.*;
import java.io.*;

public class C {
	public static int[] depth;
	public static int[] tree;
	public static boolean[] used;
	public static int[][] jump;
	public static int[][] min;
	public static int logN; 														
	public static int[] mass;
	public static int curMin;

	public static int min(int a, int b) {
		return a < b ? a : b;
	}

	public static void depthFinder(int cur) {
		if (used[cur]) {
			return;
		}
																				 
		used[cur] = true;

		if (tree[cur] == 0) {
			depth[cur] = 0;
		} else {
			depthFinder(tree[cur]);
			depth[cur] = depth[tree[cur]] + 1;
		}

	}

	public static void LCA(int a, int b) {
		if (a == b) {
			return;
		}

		for (int i = logN; i >= 0; i--) {
			if (jump[a][i] != 0) {
				if (jump[a][i] != jump[b][i]) {
					curMin = min(min[a][i], min(min[b][i], curMin));
					LCA(jump[a][i], jump[b][i]);
					return;
				}
			}
		}
		curMin = min(curMin, min(min[a][0], min[b][0]));
		return;
	}

	public static int up(int h, int cur) {
		if (h == 0) {
			return cur;
		}

		int curAns = logN;

		while (h < (1 << curAns)) {
			curAns--;
		}

		curMin = min(curMin, min[cur][curAns]);
		return up(h - (1 << curAns), jump[cur][curAns]);

	}

	public static int doEqual(int a, int b) {
		if (depth[a] == depth[b]) {
			LCA(a, b);
			return curMin;
		}

		if (depth[a] < depth[b]) {
			int c = a;
			a = b;
			b = c;
		}

		a = up(depth[a] - depth[b], a);

		LCA(a, b);
		return curMin;

	}

	public static int log(int n) {
		int cur = 0;
		while (n != 0) {
			cur++;
			n /= 2;
		}
		return cur;
	}

	public static void main(String[] args) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("minopath.in")));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("minopath.out")));
			int n = Integer.parseInt(in.readLine());
			
			logN = log(n) + 5;
			tree = new int[n + 1];
			depth = new int[n + 1];
			mass = new int[n + 1];
			used = new boolean[n + 1];
			jump = new int[n + 1][logN + 1];
			min = new int[n + 1][logN + 1];

			tree[1] = 0;
			for (int i = 1; i < n; i++) {
				String[] j = in.readLine().split(" ");
				tree[i + 1] = Integer.parseInt(j[0]);
				mass[i + 1] = Integer.parseInt(j[1]);
			}

			for (int i = 1; i <= n; i++) {
				depthFinder(i);
			}

			for (int i = 1; i <= n; i++) {
				jump[i][0] = tree[i];
				min[i][0] = mass[i];
			}

			for (int j = 1; j <= logN; j++) {
				for (int i = 1; i <= n; i++) {
					int to = jump[i][j - 1];

					jump[i][j] = jump[to][j - 1];
					min[i][j] = min(min[i][j - 1], min[to][j - 1]);
				}
			}

			int m = Integer.parseInt(in.readLine());
			for (int i = 0; i < m; i++) {
				curMin = Integer.MAX_VALUE;
				String[] q = in.readLine().split(" ");
				out.write(doEqual(Integer.parseInt(q[0]), Integer.parseInt(q[1])) + " ");
				out.newLine();
			}

			out.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

// 	C. Самое дешевое ребро
// ограничение по времени на тест4 секунды
// ограничение по памяти на тест256 мегабайт
// вводminonpath.in
// выводminonpath.out
// Дано подвешенное дерево с корнем в первой вершине. Все ребра имеют веса (стоимости). Вам нужно ответить на M запросов вида "найти у двух вершин минимум среди стоимостей ребер пути между ними".

// Входные данные
// В первой строке задано целое число n — число вершин в дереве (1 ≤ n ≤ 2·105).

// В следующих n - 1 строках записаны два целых числа x и y. Число x на строке i означает, что x — предок вершины i, y задает стоимость ребра ( x < i; |y| ≤ 106).

// Далее заданы m (0 ≤ m ≤ 5·105) запросов вида (x, y) — найти минимум на пути из x в y (1 ≤ x, y ≤ n; x ≠ y).

// Выходные данные
// Выведите ответы на запросы.

// Примеры
// входные данныеСкопировать
// 5
// 1 2
// 1 3
// 2 5
// 3 2
// 2
// 2 3
// 4 5
// выходные данныеСкопировать
// 2
// 2
// входные данныеСкопировать
// 5
// 1 1
// 1 2
// 2 3
// 3 4
// 2
// 1 4
// 3 2
// выходные данныеСкопировать
// 1
// 1
}