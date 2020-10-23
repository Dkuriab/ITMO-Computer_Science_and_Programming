import java.lang.*;
import java.io.*;
import java.util.*;

public class H {

    public static long NEUTRAL = Long.MAX_VALUE - 1;
   
    public static class node {
        public long min = NEUTRAL;
        public int leftBorder;
        public int rightBorder;
        public long update = NEUTRAL;

        public node(long min, int leftBorder, int rightBorder) {
            this.min = min;
            this.leftBorder = leftBorder;
            this.rightBorder = rightBorder;
        }

        public boolean isLeaf() {
            return leftBorder == rightBorder;
        }
    }

    public static class Tree {
        public node[] a;
        public int k = 1;

        public Tree(int n) {

            while (k < n) {
                k *= 2;
            }

            a = new node[2 * k - 1];

            for (int i = 0; i < k; i++) {
                a[k - 1 + i] = new node(NEUTRAL, i, i);
            }
            for (int i = k - 2; i >= 0; i--) {
                a[i] = new node(NEUTRAL, a[2*i + 1].leftBorder, a[2*i + 2].rightBorder);
            }
        }

        public long min(long left, long right) {
            return left < right ? left : right;
        }

        public void set(int position, int l, int r, int x) {
            push(position);

            if (l <= a[position].leftBorder && r >= a[position].rightBorder) {
                a[position].min = x;
                a[position].update = x;
                return;
            }
            if (l > a[position].rightBorder || r < a[position].leftBorder) {
                return;
            }

            set(position * 2 + 1, l, r, x);
            set(position * 2 + 2, l, r, x);


            a[position].min = min(a[position * 2 + 1].min, a[position * 2 + 2].min);

            return;
        }

        public long getMin(int position, int l, int r) {
            push(position);

            if (l <= a[position].leftBorder && r >= a[position].rightBorder) {
                return a[position].min;
            }
            if (l > a[position].rightBorder || r < a[position].leftBorder) {
                return NEUTRAL;
            }

            return min(getMin(position * 2 + 1, l, r), getMin(position * 2 + 2, l, r));
        }

        public void push(int position) {
            if (a[position].update != NEUTRAL) {
                a[position].min = a[position].update;

                if (!a[position].isLeaf()) {
                    a[position * 2 + 1].update = a[position].update;
                    a[position * 2 + 2].update = a[position].update;
                }
                a[position].update = NEUTRAL;
            }
        }
    }
	

	public static class request implements Comparable<request> {
		public int left;
		public int right;
		public int value;

		public request(int l, int r, int v) {
			left = l;
			right = r;
			value = v;
		}

		@Override
		public int compareTo(request x) {

            if (x.value != this.value) {
                if (this.value < x.value) {
                    return -1;
                } else {
    			    return 1;
                }
            } else {
                if (x.left != this.left) {
                    return this.left - x.left;
                } else {
                    if (x.right != this.right) {
                        return this.right - x.right;
                    } else {
                        return 0;
                    } 
                }
            }
		}
	} 


	public static void main(String[] args) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("rmq.in")));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("rmq.out")));

			String[] values = in.readLine().split(" ");

			int n = Integer.parseInt(values[0]);
			int m = Integer.parseInt(values[1]);

			Tree RMQ = new Tree(n);
			request[] questions = new request[m];

			for (int i = 0; i < m; i++) {
				values = in.readLine().split(" ");

				questions[i] = new request(Integer.parseInt(values[0]) - 1, Integer.parseInt(values[1]) - 1, Integer.parseInt(values[2]));
			}

			Arrays.sort(questions);

            for (int i = 0; i < m; i++) {
                RMQ.set(0, questions[i].left, questions[i].right, questions[i].value);
            }

            StringBuilder answer = new StringBuilder();
            boolean inconsistent = false;

            for (int i = 0; i < m; i++) {
                if (RMQ.getMin(0, questions[i].left, questions[i].right) != questions[i].value) {
                    inconsistent = true;
                }
            }

            if (inconsistent) {
                out.write("inconsistent");
            } else {
                out.write("consistent");
                out.newLine();

                for (int i = 0; i < n; i++) {
                    long and = RMQ.getMin(0, i, i);
                    and = (and == NEUTRAL ? Long.MAX_VALUE : and);
                    out.write(and + " ");   
                }
            }

            out.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

// H. RMQ наоборот
// ограничение по времени на тест2 секунды
// ограничение по памяти на тест256 мегабайт
// вводrmq.in
// выводrmq.out
// Рассмотрим массив a[1..n]. Пусть Q(i, j) — ответ на запрос о нахождении минимума среди чисел a[i], ..., a[j]. 
// Вам даны несколько запросов и ответы на них. Восстановите исходный массив.

// Входные данные
// Первая строка входного файла содержит число n — размер массива, и m — число запросов (1 ≤ n, m ≤ 100 000). 
// Следующие m строк содержат по три целых числа i, j и q, означающих, что Q(i, j) = q (1 ≤ i ≤ j ≤ n,  - 231 ≤ q ≤ 231 - 1).

// Выходные данные
// Если искомого массива не существует, выведите строку «inconsistent».

// В противном случае в первую строку выходного файла выведите «consistent». 
// Во вторую строку выходного файла выведите элементы массива. 
// Элементами массива должны быть целые числа в интервале от  - 231 до 231 - 1 включительно. Если решений несколько, выведите любое.

// Примеры
// входные данные
// 3 2
// 1 2 1
// 2 3 2
// выходные данные
// consistent
// 1 2 2 
// входные данные
// 3 3
// 1 2 1
// 1 1 2
// 2 3 2
// выходные данные
// inconsistent
