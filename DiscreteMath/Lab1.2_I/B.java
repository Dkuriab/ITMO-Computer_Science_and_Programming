import java.io.*;
import java.util.*;

public class B {
	public static boolean[][][] graph;
	public static int n;
	public static boolean[] ends;
	public static String word;

	public static void main(String[] args) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("problem2.in")));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("problem2.out")));

			word = in.readLine();
			String[] parametrs = in.readLine().split(" ");
				n = Integer.parseInt(parametrs[0]); // number of points
			int m = Integer.parseInt(parametrs[1]); // number of roads
			int k = Integer.parseInt(parametrs[2]); // number of ends

			graph = new boolean[n + 1][150][n + 1];
			ends = new boolean[n + 1];
			String[] endsString = in.readLine().split(" ");

			for (int i = 0; i < k; i++) {
				ends[Integer.parseInt(endsString[i])] = true;
			}

			for (int i = 0; i < m; i++) {
				String[] cur = in.readLine().split(" ");
				int a = Integer.parseInt(cur[0]);
				int b = Integer.parseInt(cur[1]);
				char c = cur[2].charAt(0);
				graph[a][c + 0][b] = true;
			}

			HashSet<Integer> cur = new HashSet<>();
			HashSet<Integer> next = new HashSet<>();
			cur.add(1);
			next.add(1);

			for (int i = 0; i < word.length(); i++) {
				next.clear();
				for (Integer g : cur) {
					for (int j = 1; j <= n; j++) {
						if (graph[(int)g][word.charAt(i)][j]) {
							next.add(j);
						}
					}
				}
				cur = next;
			}

			boolean ans = false;
			for (Integer s : cur) {
				if (ends[(int)s]) {
					ans = true;
					break;
				}
			}

			if (ans) {
				out.write("Accepts");
			} else {
				out.write("Rejects");
			}
			out.flush();
			out.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

// Задача B. Слово и НКА

// Имя входного файла: problem2.in
// Имя выходного файла: problem2.out
// Ограничение по времени: 2 секунды
// Ограничение по памяти: 256 мегабайт

// Задан недетерминированный конечный автомат и слово. Определить, допускает ли данный НКА
// заданное слово.

// Формат входного файла
// В первой строке входного файла находится слово, состоящее из не более чем 10000 строчных
// латинских букв.

// Во второй строке содержатся числа n, m и k — количество состояний, переходов и допускающих
// состояний в автомате соответственно (1 <= n <= 100, 1 <= m <= 1000, 1 <= k <= n).
// В следующей строке содержатся k чисел — номера допускающих состояний (состояния пронумерованы от 1 до n).
// В следующих m строках описываются переходы в формате “a b c”, где a — номер исходного
// состояния перехода, b — номер состояния, в которое осуществляется переход и c — символ (строчная
// латинская буква), по которому осуществляется переход.
// Стартовое состояние автомата всегда имеет номер 1.
// Формат выходного файла

// Требуется выдать строку “Accepts”, если автомат принимает заданное слово, или “Rejects” в
// противном случае.
