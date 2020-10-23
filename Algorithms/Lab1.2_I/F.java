import java.util.*;
import java.io.*;

public class F {

	public static int min(int a, int b) {
		return a < b ? a : b;
	}

	public static int abs(int a) {
		return a < 0 ? -a : a;
	}

	public static int log (int a) {
		int k = 1;
		while (a > 0) {
			a /= 2;
			k += 1;
		}
		return k;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		String cuu = in.readLine();
		String[] values = cuu.split(" ");
		int n = Integer.parseInt(values[0]);
		int m = Integer.parseInt(values[1]);
		int a = Integer.parseInt(values[2]);

		cuu = in.readLine();
		values = cuu.split(" ");
		int u = Integer.parseInt(values[0]);
		int v = Integer.parseInt(values[1]);

		int[][] sparseTable = new int[n + 1][log(n + 1)];

		for (int i = 0; i < n + 1; i++) {
			for (int j = 0; j < log(n + 1); j++) {	
				sparseTable[i][j] = Integer.MAX_VALUE;
			}
		}

		for (int i = 1; i < n + 1; i++) {
			sparseTable[i][0] = a;
			a = (23 * a + 21563) % 16714589;
		}

		for (int j = 1; (1 << j) <= n + 1; j++) {
			for (int i = 0; i + (1 << j) <= n + 1; i++) {
				sparseTable[i][j] = min(sparseTable[i][j - 1], sparseTable[i + (1 << (j - 1))][j - 1]);
			}
		}

		int[] preK = new int[n + 1];
		preK[1] = 1;
		for (int i = 2; i < n + 1; i++) {
			preK[i] = preK[i-1];
			if ((1 << (preK[i] + 1)) <= i) {
				preK[i]++;
			}
		}

		int rLast = 0;

		for (int i = 1; i <= m; i++) {
			int k = preK[abs(v - u)];

			if (u < v) {
				rLast = min(sparseTable[u][k], sparseTable[v - (1 << k) + 1][k]);
			} else {
				rLast = min(sparseTable[v][k], sparseTable[u - (1 << k) + 1][k]);
			} 
				
			if (i == m) {
				System.out.println(u + " " + v + " " + rLast);
				return;
			}

			u = ((17 * u + 751 + rLast + 2 * i) % n) + 1;
			v = ((13 * v + 593 + rLast + 5 * i) % n) + 1;
		}
	}
}

// F. Разреженные таблицы
// ограничение по времени на тест2 секунды
// ограничение по памяти на тест256 мегабайт
// вводстандартный ввод
// выводстандартный вывод
// Дан массив из n чисел. Требуется написать программу, которая будет отвечать на запросы следующего вида: найти минимум на отрезке между u и v включительно.

// Входные данные
// В первой строке зданы три натуральных числа n, m (1⩽n⩽105, 1⩽m⩽107) и a1 (0⩽a1<16714589) — количество элементов в массиве, количество запросов и первый элемент массива соответственно. Вторая строка содержит два натуральных числа u1 и v1 (1⩽u1,v1⩽n) — первый запрос.

// Для того, размер ввода был небольшой, массив и запросы генерируются.

// Элементы a2,a3,…,an задаются следующей формулой:
// ai+1=(23⋅ai+21563)mod16714589.
// Например, при n=10, a1=12345 получается следующий массив: a = (12345, 305498, 7048017, 11694653, 1565158, 2591019, 9471233, 570265, 13137658, 1325095).

// Запросы генерируются следующим образом:

// ui+1=((17⋅ui+751+ri+2i)modn)+1, vi+1=((13⋅vi+593+ri+5i)modn)+1,
// где ri — ответ на запрос номер i.
// Обратите внимание, что ui может быть больше, чем vi.

// Выходные данные
// В выходной файл выведите um, vm и rm (последний запрос и ответ на него).

// Примеры
// входные данныеСкопировать
// 10 8 12345
// 3 9
// выходные данныеСкопировать
// 5 3 1565158
// Примечание
// Можно заметить, что массивы u, v и r можно не сохранять в памяти полностью.

// Запросы и ответы на них выглядят следующим образом:

// i12345678ui3101105235vi912109123ri57026512345123451325095570265123453054981565158
// Эта задача скорее всего не решается стандартными интерпретаторами Python 2 и Python 3. Используйте соответствующие компиляторы PyPy.