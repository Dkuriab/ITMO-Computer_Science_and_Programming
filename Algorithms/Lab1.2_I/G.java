import java.util.*;
import java.io.*;

public class G {
    public static class node {
        public int max;
        public int leftBorder;
        public int rightBorder;
        public int update = 0;
        public int point = 0;

        public node(int max, int leftBorder, int rightBorder) {
            this.max = max;
            this.point = leftBorder;
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

        public Tree() {

            k = 524_288;

            a = new node[1_048_576];

            for (int i = 0; i < k; i++) {
                a[k - 1 + i] = new node(0, i, i);
            }
            for (int i = k - 2; i >= 0; i--) {
                a[i] = operation(a[2*i + 1], a[2*i + 2]);
            }
        }


        public node operation(node left, node right) {
            return new node (
                operation(left.max, right.max),
                left.leftBorder,
                right.rightBorder
            );
        }

        public int operation(int left, int right) {
            return left > right ? left : right;
        }

        public void set(int position, int l, int r, int x) {
            push(position);

            if (l <= a[position].leftBorder && r >= a[position].rightBorder) {
                a[position].update += x;
                push(position);
                return;
            }
            if (l > a[position].rightBorder || r < a[position].leftBorder) {
                return;
            }

            set(position * 2 + 1, l, r, x);
            set(position * 2 + 2, l, r, x);


            if (a[position*2 + 1].max > a[position*2 + 2].max) {
           		a[position].max = a[position * 2 + 1].max;
           		a[position].point = a[position * 2 + 1].point;
            } else {
           		a[position].max = a[position * 2 + 2].max;
           		a[position].point = a[position * 2 + 2].point;
           	}

            return;
        }

        public void push(int position) {
            if (a[position].update != 0) {
                a[position].max += a[position].update;

                if (!a[position].isLeaf()) {
                    a[position * 2 + 1].update += a[position].update;
                    a[position * 2 + 2].update += a[position].update;
                }
                a[position].update = 0;
            }
        }
    }

    public static class pair {
    	int y1;
    	int y2;
    	int value;
    	public pair(int y1, int y2, int value) {
    		this.y1 = y1;
    		this.y2 = y2;
    		this.value = value;
    	}
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out, "UTF-8"));

        List<pair>[] xes = new List[400_010];


        int n = Integer.parseInt(in.readLine());
        if (n == 0) {
        	return;
        }
        for (int i = 0; i < n; i++) {
            String[] values = in.readLine().split(" ");
            int x1 = Integer.parseInt(values[0]) + 200_002;
            int y1 = Integer.parseInt(values[1]) + 200_002;
            int x2 = Integer.parseInt(values[2]) + 200_002;
            int y2 = Integer.parseInt(values[3]) + 200_002;

            if (xes[x1] == null) {
            	xes[x1] = new ArrayList<>();
            }
          
          	if (xes[x2 + 1] == null) {
            	xes[x2 + 1] = new ArrayList<>();
          	}
            
            xes[x1].add(new pair(y1, y2, 1));
            xes[x2 + 1].add(new pair(y1, y2, -1));            
        }

        Tree y = new Tree();
        int max = 0;
        int xa = 0;
        int ya = 0; 

        for (int i = 0; i < 400_010; i++) {
        	if (xes[i] != null) {
        		Iterator<pair> runner = xes[i].iterator();
        		while (runner.hasNext()) {
        			pair current = runner.next();
    				y.set(0, current.y1, current.y2, current.value);
        		}

    			if (y.a[0].max > max) {
    				max = y.a[0].max;
    				xa = i;
    				ya = y.a[0].point;
    			}
        	}
        }

        out.write(max + "\n" + (xa - 200_002) + " " + (ya - 200_002));
        out.flush();
    }
}

// G. Окна
// ограничение по времени на тест2 секунды
// ограничение по памяти на тест256 мегабайт
// вводстандартный ввод
// выводстандартный вывод
// На экране расположены прямоугольные окна, каким-то образом перекрывающиеся (со сторонами, параллельными осям координат). 
// Вам необходимо найти точку, которая покрыта наибольшим числом из них.

// Входные данные
// В первой строке входного файла записано число окон n (1 ≤ n ≤ 50000). 
// Следующие n строк содержат координаты окон x(1, i) y(1, i) x(2, i) y(2, i), 
// где (x(1, i), y(1, i)) — координаты левого верхнего угла i-го окна, 
// а (x(2, i), y(2, i)) — правого нижнего (на экране компьютера y растет сверху вниз, а x — слева направо). 
// Все координаты — целые числа, по модулю не превосходящие 2·105.

// Выходные данные
// В первой строке выходного файла выведите максимальное число окон, 
// покрывающих какую-либо из точек в данной конфигурации. 
// Во второй строке выведите два целых числа, разделенные пробелом — координаты точки, 
// покрытой максимальным числом окон. Окна считаются замкнутыми, т.е. покрывающими свои граничные точки.

// Примеры
// входные данные
// 2
// 0 0 3 3
// 1 1 4 4
// выходные данные
// 2
// 1 3
// входные данные
// 1
// 0 0 1 1
// выходные данные
// 1
// 0 1