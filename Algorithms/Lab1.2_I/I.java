import java.lang.*;
import java.io.*;
import java.util.*;

public class I {
	public static int NEUTRAL = Integer.MAX_VALUE;


	public static class Node {
		public int maxHigh = 0;
		public int sum = 0;
		public int l;
		public int r;
		public int upd = NEUTRAL;
		public Node left = null;
		public Node right = null;

		public Node(int l, int r) {
			this.l = l;
			this.r = r;
		}

		public boolean isLeaf() {
			return l == r;
		}
	}

	public static class Tree {
        public Node root;
        public int n;

        public Tree(int n) {
        	this.n = n;

        	int k = 1;
        	while (k < n) {
        		k *= 2;
        	}
        	
            root = new Node(0, k - 1);
        }

        public int max(int left, int right) {
            return left > right ? left : right;
        }

        public void set(Node cur, int l, int r, int x) {
            if (l <= cur.l && r >= cur.r) {
            	cur.maxHigh = (cur.r - cur.l + 1) * x;
            	cur.upd = x;
            	cur.sum = cur.maxHigh;
                return;
            }
            if (l > cur.r || r < cur.l) {
                return;
            }
            push(cur);

            set(cur.left, l, r, x);
            set(cur.right, l, r, x);

            cur.sum = cur.left.sum + cur.right.sum;
            cur.maxHigh = max(cur.left.maxHigh, cur.right.maxHigh + cur.left.sum);

            return;
        }

        public void push(Node cur) {
        	if (!cur.isLeaf() && cur.left == null) {
        		cur.left = new Node(cur.l, (cur.r + cur.l) / 2);
        	}
        	if (!cur.isLeaf() && cur.right == null) {
        		cur.right = new Node((cur.r + cur.l) / 2 + 1, cur.r);
        	}

        	if (!cur.isLeaf() && cur.upd != NEUTRAL) {
        		cur.left.upd = cur.upd;
        		cur.right.upd = cur.upd;

        		int x = cur.left.upd;

        		cur.left.maxHigh = (cur.left.r - cur.left.l + 1) * x;
        		cur.right.maxHigh = (cur.right.r - cur.right.l + 1) * x;

        		cur.left.sum = cur.left.maxHigh;
        		cur.right.sum = cur.right.maxHigh;

        		cur.upd = NEUTRAL;
        	}
        }
    
        public int getRoad(Node cur, int high, int adder) {
        		if (cur.maxHigh + adder < high) {
        			return cur.r + 1 > n ? n : cur.r + 1;
        		} else if (cur.isLeaf()) {
        			return cur.l;
        		} else {
	        		push(cur);
        			int i = getRoad(cur.left, high, adder);

        			return (cur.left.maxHigh + adder >= high) ? i : getRoad(cur.right, high, adder + cur.left.sum);
        		}
        }
    }

	public static void main(String[] args) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

			int n = Integer.parseInt(in.readLine());
			Tree mountains = new Tree(n);
	
			boolean flag = true;
	
			while (flag) {
				String[] question = in.readLine().split(" ");
				switch (question[0].charAt(0)) {
					case 'Q':
						out.write(mountains.getRoad(mountains.root, Integer.parseInt(question[1]) + 1, 0) + "");
						out.newLine();
						out.flush();
						break;
					case 'I':
						int l = Integer.parseInt(question[1]) - 1;
						int r = Integer.parseInt(question[2]) - 1;
						int x = Integer.parseInt(question[3]);
						mountains.set(mountains.root, l, r ,x);
						break;
					case 'E':
						flag = false;
						break;
				}
			}


		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}


// В парке развлечений «Ай-ой-ай» открылся новейший аттракцион: польские горки. Трек состоит из n рельс, присоединенных одна к концу другой. 
// Начало первой рельсы находится на высоте 0. Оператор Петя может конфигурировать аттракцион, 
// изменяя по своему желанию подъём нескольких последовательных рельс. При этом подъём всех остальных рельс не изменяется. 
// При каждом изменении конфигурации рельс положение следующих за изменяемыми подбирается таким образом, чтобы весь трек оставался связным.

// Каждый запуск вагонетки осуществляется с энергией, достаточной для достижения высоты h. 
// Это значит, что вагонетка будет двигаться до тех пор, пока высота не превысит h, либо пока не закончится трек.

// По записям о всех изменениях конфигурации рельс и временах запусков вагонетки для каждого запуска определите, 
// сколько рельс вагонетка проедет до остановки.

// Трек можно представить как последовательность n подъемов di, по одному на рельс. Изначально рельсы горизонтальны, то есть di = 0 для всех i.


// Каждое изменение конфигурации определяется числами a, b и D: все рельсы с a-й по b-ю включительно после этого действия имеют подъем, равный D.


// Каждый запуск вагонетки определяется единственным целым числом h — максимальной высотой, на которую способна подняться вагонетка.

// Входные данные
// В первой строке записано целое число n (1 ≤ n ≤ 109) — число рельс. Следующие строки содержат запросы трех видов:

//  a b D — изменение конфигурации. Рельсы с a-й по b-ю включительно после выполнения запроса имеют подъем, равный D.
//  h — запуск вагонетки. Требуется найти число рельс, которое проедет вагонетка, которая способна подняться на высоту h.
//  — конец ввода. Этот запрос встретится ровно один раз в конце файла.
// В любой момент времени высота любой точки трека лежит в промежутке от 0 до 109. Во вводе не более 100 000 строк.

// Выходные данные
// Для каждого запроса  выведите единственное целое число — количество рельс, которое проедет вагонетка.

// Пример
// входные данные
// 4
// Q 1
// I 1 4 2
// Q 3
// Q 1
// I 2 2 -1
// Q 3
// E
// выходные данные
// 4
// 1
// 0
// 3