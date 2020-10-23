import java.io.*;
import java.util.*;

public class E {

	public static int find(ArrayList<HashSet<Integer>> passed, HashSet<Integer> check) {
		for (int i = 0; i < passed.size(); i++) {
			HashSet<Integer> p = passed.get(i);
			boolean isP = true;

			if (p.size() == check.size()) {
				Object[] am = p.toArray();
				Object[] bm = check.toArray();

				for (int j = 0; j < am.length; j++) {
					if ((int)am[j] != (int)bm[j]) {
						isP = false;
					}
				} 
			} else {
				isP = false;
			}

			if (isP) {
				return i + 1;
			}
		}
		return -1;
	}

	public static void main(String[] args) {
		try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("problem5.in")));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("problem5.out")));

            String[] parametrs = in.readLine().split(" ");
            int n = Integer.parseInt(parametrs[0]); // number of points
            int m = Integer.parseInt(parametrs[1]); // number of roads
            int k = Integer.parseInt(parametrs[2]); // number of ends
            int l = Integer.parseInt(parametrs[3]); // word length
            int mod = 1000000007;

            int[][] graph = new int[110][150];
            boolean[] ends = new boolean[110];

            boolean[][][] graphNKA = new boolean[n + 5][150][n + 5];
            boolean[] endsNKA = new boolean[n + 5];

            String[] endsString = in.readLine().split(" ");
            for (int i = 0; i < k; i++) {
                endsNKA[Integer.parseInt(endsString[i])] = true;
            }

            for (int i = 0; i < m; i++) {
                String[] cur = in.readLine().split(" ");
                graphNKA[Integer.parseInt(cur[0])][cur[2].charAt(0) + 0][Integer.parseInt(cur[1])] = true;
            }

            HashSet<Integer> startPosition = new HashSet<>();
            startPosition.add(1);

            ArrayList<HashSet<Integer>> passedPoints = new ArrayList<>();
            passedPoints.add(startPosition);

            ArrayList<HashSet<Integer>> pointsDKA = new ArrayList<>();
            pointsDKA.add(startPosition);

            ends[1] = endsNKA[1];

            // Tompson algorithm

            while(!pointsDKA.isEmpty()) {
            	HashSet<Integer> current = pointsDKA.get(pointsDKA.size() - 1);
            	int startPos = find(passedPoints, current);
            	
            	pointsDKA.remove(pointsDKA.size() - 1);

            	for (int symbol = 80; symbol < 150; symbol++) {
            		HashSet<Integer> step = new HashSet<>();
            		boolean isTerminal = false;
            		for (int point : current) {
            			for (int target = 1; target < n + 5; target++) {
            				if (graphNKA[point][symbol][target]) {
            					step.add(target);
            					if (endsNKA[target]) {
            						isTerminal = true;
            					}
            				}
            			}
            		}

            		if (!step.isEmpty()) {
            			int pos = find(passedPoints, step);
            			if (pos == -1) {
            				pos = passedPoints.size() + 1;

            				passedPoints.add(step);
            				pointsDKA.add(step);

        					ends[pos] = isTerminal;
            			}
            			graph[startPos][symbol] = pos;
            		}
            	}
            }

            // calculate answer from DKA
            HashMap<Integer, Integer> cur = new HashMap<>();
			cur.put(1, 1);
			for (int i = 0; i < l; i++) {
				HashMap<Integer, Integer> next = new HashMap<>();

				for (Integer g : cur.keySet()) {
					for (int j = 0; j < 150; j++) {
						int key = graph[g][j];
						if (key != 0) {
							next.put(key, (next.getOrDefault(key, 0) + cur.get(g)) % mod);
						}
					}
				}
				cur = next;
			}

			long ans = 0;
			for (Integer g : cur.keySet()) {
				if (ends[g]) {
					ans += cur.get(g);
				}
			}

			out.write((ans % mod) + "");
			out.close();
			in.close();
        } catch (IOException e) {
        	e.printStackTrace();
        }
	}
}

// Задача E. Число слов длины l в языке НКА
// Имя входного файла: problem5.in
// Имя выходного файла: problem5.out
// Ограничение по времени: 2 секунды
// Ограничение по памяти: 256 мегабайт

// Задан недетерминированный конечный автомат и число l. Требуется определить количество
// допускаемых им слов длины l по модулю 10^9 + 7

// Формат входного файла:
// В первой строке содержатся числа n, m, k и l — количество состояний, переходов и допускающих
// состояний в автомате, а также длина слов (1 <= n, m <= 100, 1 <= k <= n, 1 <= l <= 103).
// В следующей строке содержатся k чисел — номера допускающих состояний (состояния пронумерованы от 1 до n).
// В следующих m строках описываются переходы в формате “a b c”, где a — номер исходного
// состояния перехода, b — номер состояния, в которое осуществляется переход и c — символ (строчная
// латинская буква), по которому осуществляется переход.

// Стартовое состояние автомата всегда имеет номер 1. Гарантируется, что алгоритм Томпсона
// найдёт такой детерминированный автомат, распознающий тот же язык, имеющий не более 100 состояний.
// Формат выходного файла

// Требуется выдать количество слов длины l, допускаемых автоматом, по модулю 10^9 + 7