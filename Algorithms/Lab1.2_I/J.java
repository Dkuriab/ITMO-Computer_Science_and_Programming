import java.util.*;
import java.io.*;

public class J {
    public static class node {
        public long min;
        public int leftBorder;
        public int rightBorder;
        public int position = 0;
        public int update = 0;

        public node(long min, int leftBorder, int rightBorder) {
            this.min = min;
            this.position = leftBorder;
            this.leftBorder = leftBorder;
            this.rightBorder = rightBorder;
        }

        public boolean isLeaf() {
            return leftBorder == rightBorder;
        }
    }

    public static class Tree {
        public node[] a;
        public int k = 0;

        public Tree(long[] t) {
            int n = t.length;
            k = 1;
            while (k < n) {
                k *= 2;
            } 
            a = new node[2*k - 1];

            for (int i = 0; i < n; i++) {
                a[k - 1 + i] = new node(0, i, i);
            }
            for (int i = n; i < k; i++) {
                a[k - 1 + i] = new node(Long.MAX_VALUE, i, i);
            }
            for (int i = k - 2; i >= 0; i--) {
                a[i] = operation(a[2*i + 1], a[2*i + 2]);
            }
        }

        public node attack (int position, int l, int r) {
            push(position);

            if (l <= a[position].leftBorder && r >= a[position].rightBorder) {
                return a[position]; 
                
            }
            if (l > a[position].rightBorder || r < a[position].leftBorder) {
                return new node(Long.MAX_VALUE, 0, 0);
            }

	
			node left = attack(position * 2 + 1, l, r);
			node right = attack(position * 2 + 2, l, r);

			return left.min < right.min ? left : right;	
        }

        public node operation(node left, node right) {
            return new node (
                min(left.min, right.min),
                left.leftBorder,
                right.rightBorder
            );
        }

        public long min(long left, long right) {
            return left < right ? left : right;
        }

        public long max(long left, long right) {
            return left > right ? left : right;
        }

        public void set(int position, int l, int r, int x) {
            push(position);

            if (l <= a[position].leftBorder && r >= a[position].rightBorder) {
                a[position].update = (int)max(x, a[position].update);
                a[position].min = max(x, a[position].min);
                return;
            }
            if (l > a[position].rightBorder || r < a[position].leftBorder) {
                return;
            }

            set(position * 2 + 1, l, r, x);
			set(position * 2 + 2, l, r, x);

			if (a[position * 2 + 1].min < a[position * 2 + 2].min) {
				a[position].position = a[position * 2 + 1].position;
				a[position].min = a[position * 2 + 1].min;
			} else {
				a[position].position = a[position * 2 + 2].position;
				a[position].min = a[position * 2 + 2].min;
        	}
        }


        public void push(int position) {
            if (a[position].update != 0) {

                if (!a[position].isLeaf()) {
                    a[position * 2 + 1].update = (int)max(a[position].update, a[position * 2 + 1].update);
                    a[position * 2 + 1].min = max(a[position].update, a[position * 2 + 1].min);

                    a[position * 2 + 2].update = (int)max(a[position].update, a[position * 2 + 2].update);
                    a[position * 2 + 2].min = max(a[position].update, a[position * 2 + 2].min);
                }
                a[position].update = 0;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));

        String current = in.readLine();
        String[] values = current.split(" ");

        int n = Integer.parseInt(values[0]);
        int m = Integer.parseInt(values[1]);

        Tree china = new Tree(new long[n + 1]);

        for (int i = 0; i < m; i++) {
            current = in.readLine();
            values = current.split(" ");

            if (values[0].equals("defend")) {
                china.set(0, Integer.parseInt(values[1]) - 1, Integer.parseInt(values[2]) - 1, Integer.parseInt(values[3]));
            } else {
            	node answer = china.attack(0, Integer.parseInt(values[1]) - 1, Integer.parseInt(values[2]) - 1);
                System.out.println(answer.min + " " + (answer.position + 1));
            }
        }
    }
}

// В этой задаче мы проследим альтернативную историю Великой Китайской Стены.

// Великая Китайская Стена состоит из n метровых участков, пронумерованных по порядку целыми числами от 1 до n. Каждый участок характеризуется своей высотой в метрах — целым неотрицательным числом. До начала нашей истории Стена ещё не построена, поэтому высота каждого участка равна нулю.

// Происходят события двух видов.

// Укрепление Стены (запись: «defend a b c»). Император вызывает к себе вассалов из приграничных провинций и велит им сделать так, чтобы промежуток Стены, охватывающий участки от a до b включительно, имел высоту не менее c метров. Это значит, что все участки меньшей высоты на этом промежутке нужно достроить до высоты c, а остальные оставить нетронутыми. Приказ императора выполняется немедленно, то есть до наступления следующего события.
// Нападение варваров (запись: «attack d e»). Варвары подходят к Стене снаружи и занимают позиции напротив промежутка Стены, охватывающего участки от d до e включительно. После этого они находят такой участок на этом промежутке, у которого высота как можно меньше, и пытаются через него проникнуть на территорию Китая. Нападение также происходит немедленно, до наступления следующего события.
// Для восстановления достоверной альтернативно-исторической картины не хватает одного: для каждого нападения варваров указать минимальную высоту Стены на соответствующем промежутке, а также какой-нибудь участок из этого промежутка с такой высотой. По заданной последовательности событий найдите эти числа.

// Входные данные
// В первой строке заданы через пробел два целых числа n и m — длина Стены в метрах и количество событий соответственно (1 ≤ n ≤ 106, 0 ≤ m ≤ 105). В следующих m строках описаны события в порядке их следования. Если событие описывает укрепление Стены, оно задано в форме «defend a b c» (1 ≤ a ≤ b ≤ n, 1 ≤ c ≤ 107). Если же событие описывает нападение варваров, оно задано в форме «attack d e» (1 ≤ d ≤ e ≤ n).

// Выходные данные
// В ответ на каждое нападение варваров выведите строку, содержащую два числа, разделённые пробелом. Первое из этих чисел — минимальная высота Стены на соответствующем промежутке. Второе — номер любого метрового участка Стены на этом промежутке, имеющего такую высоту.

// Пример
// входные данные
// 5 4
// defend 1 3 10
// attack 1 4
// attack 2 3
// attack 1 2
// выходные данные
// 0 4
// 10 2
// 10 1