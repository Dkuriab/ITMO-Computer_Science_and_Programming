import java.util.*;

public class A {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		int x = scan.nextInt();
		int y = scan.nextInt();
		int a0 = scan.nextInt();

		int m = scan.nextInt();
		int z = scan.nextInt();
		int t = scan.nextInt();
		int b0 = scan.nextInt();

		long[] prefix = new long[n];
		prefix[0] = a0;
		long buffer = a0;

		for (int i = 1; i < n; i++) {
			buffer = (x * buffer + y) % (1 << 16);
			prefix[i] = prefix[i - 1] + buffer;
		}

		long res = 0;

		int b1 = (z * b0 + t) % (1 << 30);
		b1 = (b1 < 0 ) ? b1 += (1 << 30) : b1;

		for (int i = 0; i < m; i++) {

			int c0 = b0 % n;
			int c1 = b1 % n;
			int min = c0 < c1 ? c0 : c1;

			int max = c0 + c1 - min;


			if (min == 0) {
				res += prefix[max];
			} else {
				res += prefix[max] - (prefix[min - 1]);
			}

			b0 = (z*b1 + t) % (1 << 30);
			b0 = (b0 < 0 ) ? b0 += (1 << 30) : b0;

			b1 = (z*b0 + t) % (1 << 30);
			b1 = (b1 < 0 ) ? b1 += (1 << 30) : b1;
		}

		
		System.out.println(res);
	}
}	

// A. Сумма простая
// ограничение по времени на тест1 секунда
// ограничение по памяти на тест256 мегабайт
// вводстандартный ввод
// выводстандартный вывод
// Вам нужно научиться отвечать на запрос «сумма чисел на отрезке».

// Массив не меняется. Запросов много. Отвечать на каждый запрос следует за .

// Входные данные
// Размер массива — n и числа x, y, a0, порождающие массив a: 

// Далее следует количество запросов m и числа z, t, b0, порождающие массив b: .

// Массив c строится следующим образом: .

// Запросы: i-й из них — найти сумму на отрезке от min(c2i, c2i + 1) до max(c2i, c2i + 1) в массиве a.

// Ограничения: 1 ≤ n ≤ 107, 0 ≤ m ≤ 107. Все числа целые от 0 до 216. t может быть равно  - 1.

// Выходные данные
// Выведите сумму всех сумм.

// Пример
// входные данные
// 3 1 2 3
// 3 1 -1 4
// выходные данные
// 23
// Примечание
// a = {3, 5, 7}, b = {4, 3, 2, 1, 0, 230 - 1}, c = {1, 0, 2, 1, 0, 0},

// запросы = {[0, 1], [1, 2], [0, 0]}, суммы = {8, 12, 3}.