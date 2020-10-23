import java.util.*;
import java.io.*;

public class A {

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
			int[] tree = new int[n + 1];
			int logN = log(n) + 5;

			String[] info = in.readLine().split(" ");

			for (int i = 0; i < n; i++) {
				tree[i + 1] = Integer.parseInt(info[i]);
			}

			int[][] jump = new int[n + 1][logN + 1];

			for (int i = 1; i <= n; i++) {
				jump[i][0] = tree[i];
			}

			for (int j = 1; j <= logN; j++) {
				for (int i = 1; i <= n; i++) {
					jump[i][j] = jump[jump[i][j - 1]][j - 1];
				}
			}

			for (int i = 1; i <= n; i++) {
				out.write(i + ": ");

				for (int j = 0; j <= logN; j++) {
					if (jump[i][j] == 0) {
						break;
					}
					
					out.write(jump[i][j] + " ");
				}
				out.newLine();
			}

			out.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
// 	A. Двоичные подъемы
// ограничение по времени на тест2 секунды
// ограничение по памяти на тест256 мегабайт
// вводстандартный ввод
// выводстандартный вывод
// Задано подвешенное дерево. Найдите для каждой вершины двоичные подъемы: предков, которые находятся от нее на расстоянии 2 k для какого-либо целого k.

// Входные данные
// В первой строке входа задано число n (1 ≤ n ≤ 105) — число вершин дерева. Во второй строке заданы n чисел p i. Число p i равно номеру вершины, являющейся предком вершины i (вершины нумеруются с 1) или нулю, если вершина i — корень дерева.

// Выходные данные
// Выведите n строк. В i-й строке выведите номер вершины i и далее после двоеточия список требуемых предков, в порядке увеличения расстояния от i.

// Пример
// входные данныеСкопировать
// 8
// 5 8 5 0 4 5 4 1
// выходные данныеСкопировать
// 1: 5 4 
// 2: 8 1 4 
// 3: 5 4 
// 4: 
// 5: 4 
// 6: 5 4 
// 7: 4 
// 8: 1 5 
}