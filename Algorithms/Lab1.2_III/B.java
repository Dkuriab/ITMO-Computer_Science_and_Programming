import java.util.*;
import java.io.*;

public class B {
	public static int[] depth;
	public static int[] tree;
	public static boolean[] used;
	public static int[][] jump;
	public static int logN;

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

	public static int LCA(int a, int b) {
		if (a == b) {
			return a;
		}

		for (int i = logN; i >= 0; i--) {
			if (jump[a][i] != 0) {
				if (jump[a][i] != jump[b][i]) {
					return LCA(jump[a][i], jump[b][i]);
				}
			}
		}
		return jump[a][0];
	}

	public static int up(int h, int cur) {
		if (h == 0) {
			return cur;
		}

		int curAns = logN;

		while (h < (1 << curAns)) {
			curAns--;
		}

		return up(h - (1 << curAns), jump[cur][curAns]);

	}

	public static int doEqual(int a, int b) {
		if (depth[a] == depth[b]) {
			return LCA(a, b);
		}

		if (depth[a] < depth[b]) {
			int c = a;
			a = b;
			b = c;
		}

		a = up(depth[a] - depth[b], a);

		return LCA(a, b);

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
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
			int n = Integer.parseInt(in.readLine());
			
			logN = log(n) + 5;
			tree = new int[n + 1];
			depth = new int[n + 1];
			used = new boolean[n + 1];
			jump = new int[n + 1][logN + 1];

			tree[1] = 0;
			for (int i = 1; i < n; i++) {
				tree[i + 1] = Integer.parseInt(in.readLine().split(" ")[0]);
			}

			for (int i = 1; i <= n; i++) {
				depthFinder(i);
			}

			for (int i = 1; i <= n; i++) {
				jump[i][0] = tree[i];
			}

			for (int j = 1; j <= logN; j++) {
				for (int i = 1; i <= n; i++) {
					jump[i][j] = jump[jump[i][j - 1]][j - 1];
				}
			}

			int m = Integer.parseInt(in.readLine());
			for (int i = 0; i < m; i++) {
				String[] q = in.readLine().split(" ");
				out.write(doEqual(Integer.parseInt(q[0]), Integer.parseInt(q[1])) + " ");
				out.newLine();
			}

			out.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
// 	B. LCA
// ограничение по времени на тест5 секунд
// ограничение по памяти на тест256 мегабайт
// вводстандартный ввод
// выводстандартный вывод
// Дано подвешенное дерево с корнем в первой вершине. Вам нужно ответить на m запросов вида "найти LCA двух вершин". LCA вершин u и v в подвешенном дереве — это наиболее удалённая от корня дерева вершина, лежащая на обоих путях от u и v до корня.

// Входные данные
// В первой строке задано целое число n — число вершин в дереве (1≤n≤2⋅105).

// В следующих n−1 строках записано одно целое число x. Число x на строке i означает, что x — предок вершины i(x<i).

// Затем дано число m.

// Далее заданы m (0≤m≤5⋅105) запросов вида (u,v) — найти LCA двух вершин u и v (1≤u,v≤n; u≠v).

// Выходные данные
// Для каждого запроса выведите LCA двух вершин на отдельной строке.

// Примеры
// входные данныеСкопировать
// 5
// 1
// 1
// 2
// 3
// 2
// 2 3
// 4 5
// выходные данныеСкопировать
// 1
// 1
// входные данныеСкопировать
// 5
// 1
// 1
// 2
// 2
// 3
// 4 5
// 4 2
// 3 5
// выходные данныеСкопировать
// 2
// 2
// 1
}