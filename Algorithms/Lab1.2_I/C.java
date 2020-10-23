import java.util.*;

public class C {
	public static long neutral = Long.MAX_VALUE;

	public static node operation(node first, node second) {
		return new node(operation(first.value, second.value), first.leftBorder, second.rightBorder);
	}

	public static long operation(long first, long second) {
		return first < second ? first : second;
	}

	public static class node {
		public int leftBorder = 0;
		public int rightBorder = 0;
		public long value = 0;
		public long add = 0;
		public long update = neutral;

		public node(long value, int leftBorder, int rightBorder) {
			this.value = value;
			this.leftBorder = leftBorder;
			this.rightBorder = rightBorder;
		}

		public void add(long x) {
			if (this.isLeaf()) {
				this.value += x;
			} else {
				this.add += x;
			}
		}

		public void update(long x) {
			if (this.isLeaf()) {
				this.value = x;
			} else {
				this.update = x;
			}
			this.add = 0;
		}

		public boolean isLeaf() {
			return rightBorder == leftBorder;
		}

		public boolean containsAdd() {
			return add != 0;
		}

		public boolean containsUpdate() {
			return update != neutral;
		}
	}

	public static void build(long[] t) {
		int n = t.length;
		int k = 1;
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


	public static long min(int position, int l, int r) {
		push(position);

		if (l <= a[position].leftBorder && r >= a[position].rightBorder) {
			return a[position].value;
		}
		if (l > a[position].rightBorder || r < a[position].leftBorder) {
			return neutral;
		}
		return operation(min(position * 2 + 1, l, r), min(position * 2 + 2, l, r));
	} 

	public static void set(int position, int l, int r, long x) {
		push(position);

		if (l <= a[position].leftBorder && r >= a[position].rightBorder) {
			a[position].update(x);
			push(position);
			return;
		}
		if (l > a[position].rightBorder || r < a[position].leftBorder) {
			return;
		}

		set(position * 2 + 1, l, r, x);
		set(position * 2 + 2, l, r, x);

		a[position].value = operation(a[position * 2 + 1].value, a[position * 2 + 2].value);
		return;
	}

	public static void add(int position, int l, int r, long x) {
		push(position);

		if (l <= a[position].leftBorder && r >= a[position].rightBorder) {
			a[position].add(x);
			push(position);
			return;
		}
		if (l > a[position].rightBorder || r < a[position].leftBorder) {
			return;
		}

		add(position * 2 + 1, l, r, x);
		add(position * 2 + 2, l, r, x);

		a[position].value = operation(a[position * 2 + 1].value, a[position * 2 + 2].value);
		return;
	}

	public static void push(int position) {
		if (!a[position].isLeaf()) {
			if (a[position].containsUpdate()) {
				a[position * 2 + 1].update(a[position].update);
				a[position * 2 + 2].update(a[position].update);

				a[position].value = a[position].update;
				a[position].update = neutral;
			}
			if (a[position].containsAdd()) {
				a[position * 2 + 1].add(a[position].add);
				a[position * 2 + 2].add(a[position].add);
				
				a[position].value += a[position].add;
				a[position].add = 0;
			} 
		} else {
			if (a[position].containsUpdate()) {
				a[position].value = a[position].update;
				a[position].update = neutral;
			}
			if (a[position].containsAdd()) {
				a[position].value += a[position].add;
				a[position].add = 0;
			}
		}
	}

	public static node[] a;

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		long[] t = new long[n];

		for (int i = 0; i < n; i++) {
			t[i] = scan.nextInt();
		}

		build(t);
		
		while(scan.hasNext()) {
			String comand = scan.next(); 
			if (comand.equals("min")) {
				System.out.println(min(0, scan.nextInt() - 1, scan.nextInt() - 1));
			} else if (comand.equals("set")) {
				set(0, scan.nextInt() - 1, scan.nextInt() - 1, scan.nextInt());
			} else if (comand.equals("add")) {
				add(0, scan.nextInt() - 1, scan.nextInt() - 1, scan.nextInt());
			}
		}
	}
}

// C. RMQ2
// ограничение по времени на тест2 секунды
// ограничение по памяти на тест256 мегабайт
// вводстандартный ввод
// выводстандартный вывод
// Входные данные
// В первой строке находится число n — размер массива. (1 ≤ n ≤ 105) Во второй строке находится n чисел ai — элементы массива. Далее содержится описание операций, их количество не превышает 2·105. В каждой строке находится одна из следующих операций:

// set i j x — установить все a[k], i ≤ k ≤ j в x.
// add i j x — увеличить все a[k], i ≤ k ≤ j на x.
// min i j — вывести значение минимального элемента в массиве на отрезке с i по j, гарантируется, что (1 ≤ i ≤ j ≤ n).
// Все числа во входном файле и результаты выполнения всех операций не превышают по модулю 1018.
// Выходные данные
// Выведите последовательно результат выполнения всех операций min. Следуйте формату выходного файла из примера.

// Пример
// входные данные
// 5
// 1 2 3 4 5
// min 2 5
// min 1 5
// min 1 4
// min 2 4
// set 1 3 10
// add 2 4 4
// min 2 5
// min 1 5
// min 1 4
// min 2 4
// выходные данные
// 2
// 1
// 1
// 2
// 5
// 5
// 8
// 8