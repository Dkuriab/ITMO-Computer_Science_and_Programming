import java.util.*;

public class B {
	public static int neutral = 0;
	public static int k = 0;

	public static node operation(node first, node second) {
		return new node(first.value + second.value, first.leftBorder, second.rightBoarder);
	}

	public static class node {
		public long value = 0;
		public int leftBorder = 0;
		public int rightBoarder = 0;

		public node(long value, int leftBorder, int rightBoarder) {
			this.value = value;
			this.leftBorder = leftBorder;
			this.rightBoarder = rightBoarder;
		}
	}

	public static void build(long[] t) {
		int n = t.length;
		while (k < n) {
			k *= 2;
		} 
		a = new node[2*k - 1];

		for (int i = 0; i < n; i++) {
			a[k - 1 + i] = new node(t[i], i, i);
		}
		for (int i = n; i < k; i++) {
			a[k - 1 + i] = new node(neutral, i, i);
		}
		for (int i = k - 2; i >= 0; i--) {
			a[i] = operation(a[2*i + 1], a[2*i + 2]);
		}
	}


	public static long sum(int position, int l, int r) {
		node current = a[position];
		long sum = 0;

		if (l <= current.leftBorder && r >= current.rightBoarder) {
			return current.value;
		}
		if (l > current.rightBoarder || r < current.leftBorder) {
			return neutral;
		}
		return sum(position * 2 + 1, l, r) + sum(position * 2 + 2, l, r);

		
	} 

	public static void set(int i, long x) {
		int j = k - 1 + i;
		a[j].value = x;

		while (j > 0) {
			j = (j - 1) / 2;
			a[j] = operation(a[2*j + 1], a[2*j + 2]);
		}
	}

	public static node[] a;

	public static void print() {
		int o = 1;
		int p = 1;
		System.out.println("Tree: ");
		for (int i = 0; i < a.length; i++) {
			if (i == o) {
				p *= 2;
				o += p;
				System.out.println();
			}
			System.out.print(a[i].value + " ");
		}
	}

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		k = 1;
		while (k < n) {
			k *= 2;
		} 
		long[] t = new long[n];

		for (int i = 0; i < n; i++) {
			t[i] = scan.nextInt();
		}
		
		build(t);
		
		while(scan.hasNext()) {

			if (scan.next().equals("sum")) {
				System.out.println(sum(0, scan.nextInt() - 1, scan.nextInt() - 1));
			} else {
				set(scan.nextInt() - 1, scan.nextInt());
			}
		}
	}
}

// B. RSQ
// ограничение по времени на тест2 секунды
// ограничение по памяти на тест256 мегабайт
// вводстандартный ввод
// выводстандартный вывод
// Входные данные
// В первой строке находится число n — размер массива. (1 ≤ n ≤ 500 000) 
// Во второй строке находится n чисел ai — элементы массива. 
// Далее содержится описание операций, их количество не превышает 1 000 000. В каждой строке находится одна из следующих операций:

// set i x — установить a[i] в x.
// sum i j — вывести значение суммы элементов в массиве на отрезке с i по j, гарантируется, что (1 ≤ i ≤ j ≤ n).
// Все числа во входном файле и результаты выполнения всех операций не превышают по модулю 1018.
// Выходные данные
// Выведите последовательно результат выполнения всех операций sum. Следуйте формату выходного файла из примера.

// Пример
// входные данные
// 5
// 1 2 3 4 5
// sum 2 5
// sum 1 5
// sum 1 4
// sum 2 4
// set 1 10
// set 2 3
// set 5 2
// sum 2 5
// sum 1 5
// sum 1 4
// sum 2 4
// выходные данные
// 14
// 15
// 10
// 9
// 12
// 22
// 20
// 10