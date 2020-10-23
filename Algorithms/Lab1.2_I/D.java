import java.util.*;
import java.io.*;

public class D {

	public static class node {
		public int leftBorder = 0;
		public int rightBorder = 0;
		public int update = -1;
		
		public int sections = 0;
		public int left = 0;
		public int right = 0;
		public int sumLength = 0;

		public node(int leftBorder, int rightBorder) {
			this.leftBorder = leftBorder;
			this.rightBorder = rightBorder;
		}

		public boolean isLeaf() {
			return rightBorder == leftBorder;
		}

		public boolean containsUpdate() {
			return update != -1;
		}
	}

	public static void build(int[] t) {
		int n = t.length;
		int k = 1;
		while (k < n) {
			k *= 2;
		} 
		a = new node[2*k - 1];

		for (int i = 0; i < n; i++) {
			a[k - 1 + i] = new node(i, i);
		}
		for (int i = n; i < k; i++) {
			a[k - 1 + i] = new node(i, i);
		}
		for (int i = k - 2; i >= 0; i--) {
			a[i] = new node(a[i*2 + 1].leftBorder, a[i*2 + 2].rightBorder);
		}
	}

	public static void set(int position, int l, int r, int x) {
		push(position);

		if (l <= a[position].leftBorder && r >= a[position].rightBorder) {
			a[position].update = x;
			push(position);
			return;
		}
		if (l > a[position].rightBorder || r < a[position].leftBorder) {
			return;
		}

		set(position * 2 + 1, l, r, x);
		set(position * 2 + 2, l, r, x);

		a[position].sections = a[position * 2 + 1].sections + a[position * 2 + 2].sections;
		a[position].left = a[position * 2 + 1].left;
		a[position].right = a[position * 2 + 2].right;
		a[position].sumLength = a[position * 2 + 1].sumLength + a[position * 2 + 2].sumLength;

		if (a[position * 2 + 1].right == 1 && a[position * 2 + 2].left == 1) {
			a[position].sections--;
		}

		return;
	}

	public static void push(int position) {
		if (a[position].containsUpdate()) {
			int x = a[position].update;
			a[position].update = -1;

			a[position].sections = x;
			a[position].left = x;
			a[position].right = x;
			a[position].sumLength = (x == 1 ? a[position].rightBorder - a[position].leftBorder + 1 : 0);
			if (!a[position].isLeaf()) {
				a[position * 2 + 1].update = x;
				a[position * 2 + 2].update = x;
			}
		}
	}

	public static node[] a;

	public static void main(String[] args) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
        	BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out, "UTF-8"));

        	String current = in.readLine();

			int n = Integer.parseInt(current);

			int[] t = new int[1_000_010];

			build(t);
			
			for (int i = 0; i < n; i++) {
				current = in.readLine();
				String[] comand = current.split(" ");
				String letter = comand[0];

				int x = Integer.parseInt(comand[1]) + 500_001;
				int l = Integer.parseInt(comand[2]);

				if (letter.equals("W")) {
					set(0, x - 1, x + l - 2, 0);
				} else {
					set(0, x - 1, x + l - 2, 1);
				}
				push(0);
				out.write(a[0].sections + " " + a[0].sumLength);
				out.newLine();
			}

			out.flush();
		} catch (IOException e) {
			System.out.println("Warning! Testin system is unsafe");
		} 
	}  
}

// D. Художник
// ограничение по времени на тест2 секунды
// ограничение по памяти на тест256 мегабайт
// вводстандартный ввод
// выводстандартный вывод
// Итальянский художник-абстракционист Ф. Мандарино увлекся рисованием одномерных черно-белых картин. Он пытается найти оптимальное местоположение и количество черных участков картины. Для этого он проводит на прямой белые и черные отрезки, и после каждой из таких операций хочет знать количество черных отрезков на получившейся картине и их суммарную длину.

// Изначально прямая — белая. Ваша задача — написать программу, которая после каждой из таких операций выводит в выходной файл интересующие художника данные.

// Входные данные
// В первой строке входного файла содержится общее количество нарисованных отрезков (1 ≤ n ≤ 100 000). В последующих n строках содержится описание операций. Каждая операция описывается строкой вида c x l, где c — цвет отрезка (W для белых отрезков, B для черных), а сам отрезок имеет вид [x;x + l), причем координаты обоих концов — целые числа, не превосходящие по модулю 500 000. Длина задается положительным целым числом.

// Выходные данные
// После выполнения каждой из операций необходимо вывести в выходной файл на отдельной строке количество черных отрезков на картине и их суммарную длину, разделенные одним пробелом.

// Пример
// входные данные
// 7
// W 2 3
// B 2 2
// B 4 2
// B 3 2
// B 7 2
// W 3 1
// W 0 10
// выходные данные
// 0 0
// 1 2
// 1 4
// 1 4
// 2 6
// 3 5
// 0 0