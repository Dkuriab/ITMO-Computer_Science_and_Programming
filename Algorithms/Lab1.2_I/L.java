import java.util.*;
import java.io.*;
import java.lang.*;

public class L {

	public static long[][][] fenivikTheRockStar;
	public static int n;

	public static int fen(int w) {
		return (w & (w + 1)) - 1;
	}

	public static int unfen(int w) {
		return w | (w + 1);
	}

	public static int sum(int x, int y, int z) {
		int result = 0;
		for (int i = x; i >= 0; i = fen(i)) {
			for (int j = y; j >= 0; j = fen(j)) {
				for(int k = z; k >= 0; k = fen(k)) {
					result += fenivikTheRockStar[i][j][k];
				}
			}
		}
		return result;
	}

	public static void add(int x, int y, int z, int adding) {
		for (int i = x; i < n; i = unfen(i)) {
			for (int j = y; j < n; j = unfen(j)) {
				for (int k = z; k < n; k = unfen(k)) {
					fenivikTheRockStar[i][j][k] += adding;
				}
			}
		}
	}

	public static long get(int x1, int x2, int y1, int y2, int z1, int z2) {
		return (sum(x2, y2, z2) - sum(x1, y2, z2) - sum(x2, y1, z2) - sum(x2, y2, z1) + 
				sum(x1, y1, z2) + sum(x2, y1, z1) + sum(x1, y2, z1) - sum(x1, y1, z1));
	}

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

		n = Integer.parseInt(in.readLine()) + 1;

		fenivikTheRockStar = new long[n][n][n];

		while(true) {
			String[] values = in.readLine().split(" ");
			int command = Integer.parseInt(values[0]);

			if (command == 3) {
				break;
			} else if (command == 2) {
				int x1 = Integer.parseInt(values[1]);
				int y1 = Integer.parseInt(values[2]);
				int z1 = Integer.parseInt(values[3]);
				int x2 = Integer.parseInt(values[4]);
				int y2 = Integer.parseInt(values[5]);
				int z2 = Integer.parseInt(values[6]);

				out.write(get(x1 - 1, x2, y1 - 1, y2, z1 - 1, z2) + "");
				out.newLine();

			} else if (command == 1) {
				int x = Integer.parseInt(values[1]);
				int y = Integer.parseInt(values[2]);
				int z = Integer.parseInt(values[3]);
				int k = Integer.parseInt(values[4]); 

				add(x, y, z, k);
			}
		}
		out.flush();
	}
}

// Вася любит наблюдать за звездами. Но следить за всем небом сразу ему тяжело. 
// Поэтому он наблюдает только за частью пространства, ограниченной кубом размером n × n × n. 
// Этот куб поделен на маленькие кубики размером 1 × 1 × 1. Во время его наблюдений могут происходить следующие события:

// В каком-то кубике появляются или исчезают несколько звезд.
// К нему может заглянуть его друг Петя и поинтересоваться, сколько видно звезд в части пространства, состоящей из нескольких кубиков.
// Входные данные
// Первая строка входного файла содержит натуральное число 1 ≤ n ≤ 128. 
// Координаты кубиков — целые числа от 0 до n - 1. Далее следуют записи о происходивших событиях по одной в строке. 
// В начале строки записано число m. Если m равно:

// 1, то за ним следуют 4 числа — x, y, z (0 ≤ x, y, z < N) и k ( - 20000 ≤ k ≤ 20000) — координаты кубика и величина, 
// на которую в нем изменилось количество видимых звезд;
// 2, то за ним следуют 6 чисел — x1, y1, z1, x2, y2, z2 (0 ≤ x1 ≤ x2 < N, 0 ≤ y1 ≤ y2 < N, 0 ≤ z1 ≤ z2 < N), 
// которые означают, что Петя попросил подсчитать количество звезд в кубиках (x, y, z) из области: x1 ≤ x ≤ x2, y1 ≤ y ≤ y2, z1 ≤ z ≤ z2;
// 3, то это означает, что Васе надоело наблюдать за звездами и отвечать на вопросы Пети. 
// Эта запись встречается во входном файле только один раз и будет последней.
// Количество записей во входном файле не больше 100 002.
// Выходные данные
// Для каждого Петиного вопроса выведите искомое количество звезд.

// Пример
// входные данные
// 2
// 2 1 1 1 1 1 1
// 1 0 0 0 1
// 1 0 1 0 3
// 2 0 0 0 0 0 0
// 2 0 0 0 0 1 0
// 1 0 1 0 -2
// 2 0 0 0 1 1 1
// 3
// выходные данные
// 0
// 1
// 4
// 2