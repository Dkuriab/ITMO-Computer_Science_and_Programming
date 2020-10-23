import java.io.*;
import java.util.*;

public class D {
	public static void main(String[] args) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("problem4.in")));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("problem4.out")));

			String[] parametrs = in.readLine().split(" ");
			int n = Integer.parseInt(parametrs[0]); // number of points
			int m = Integer.parseInt(parametrs[1]); // number of roads
			int k = Integer.parseInt(parametrs[2]); // number of ends
			int l = Integer.parseInt(parametrs[3]); // word length
			int mod = 1_000_000_000 + 7;
			int[][] graph = new int[n + 1][150];
			boolean[] ends = new boolean[n + 1];
			String[] endsString = in.readLine().split(" ");

			for (int i = 0; i < k; i++) {
				ends[Integer.parseInt(endsString[i])] = true;
			}

			for (int i = 0; i < m; i++) {
				String[] cur = in.readLine().split(" ");
				int a = Integer.parseInt(cur[0]);
				int b = Integer.parseInt(cur[1]);
				char c = cur[2].charAt(0);
				graph[a][c + 0] = b;
			}

			HashMap<Integer, Integer> cur = new HashMap<>();
			cur.put(1, 1);

			for (int i = 0; i < l; i++) {
				HashMap<Integer, Integer> next = new HashMap<>();

				for (Integer g : cur.keySet()) {
					for (int j = 0; j < 150; j++) {
						int key = graph[(int)g][j];
						if (key != 0) {

							next.put(key, (next.getOrDefault(key, 0) + cur.get(g)) % mod);
						}
					}
				}
				cur = next;
			}

			long ans = 0;
			for (Integer g : cur.keySet()) {
				if (ends[(int)g]) {
					ans += cur.get(g);

				}
			}
			
			ans = ans % mod;	

			out.write(ans + "");
			out.flush();
			out.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

// Задача D. Число слов длины l в языке ДКА

// Имя входного файла: problem4.in
// Имя выходного файла: problem4.out
// Ограничение по времени: 2 секунды
// Ограничение по памяти: 256 мегабайт

// Задан детерминированный конечный автомат и число l. Требуется определить количество допускаемых им слов длины l по модулю 10^9 + 7

// Формат входного файла
// В первой строке содержатся числа n, m, k и l — количество состояний, переходов и допускающих
// состояний в автомате, а также длина слов (1 <= n, m <= 100, 1 <= k <= n, 1 <= l <= 10^3).

// В следующей строке содержатся k чисел — номера допускающих состояний (состояния пронумерованы от 1 до n).
// В следующих m строках описываются переходы в формате “a b c”, где a — номер исходного
// состояния перехода, b — номер состояния, в которое осуществляется переход и c — символ (строчная
// латинская буква), по которому осуществляется переход.

// Стартовое состояние автомата всегда имеет номер 1. Гарантируется, что из любого состояния не
// более одного перехода по каждому символу.

// Формат выходного файла
// Выведите количество слов длины l, допускаемых автоматом, по модулю 10^9 + 7.