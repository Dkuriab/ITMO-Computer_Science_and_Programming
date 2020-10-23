import java.io.*;

public class A {
	public static void main(String[] args) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("problem1.in")));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("problem1.out")));

			String word = in.readLine();
			String[] parametrs = in.readLine().split(" ");
			int n = Integer.parseInt(parametrs[0]); // number of points
			int m = Integer.parseInt(parametrs[1]); // number of roads
			int k = Integer.parseInt(parametrs[2]); // number of ends

			int[][] graph = new int[n + 1][150];
			boolean[] ends = new boolean[n + 1];
			String[] endsString = in.readLine().split(" ");

			for (int i = 0; i < k; i++) {
				ends[Integer.parseInt(endsString[i])] = true;
			}

			for (int i = 0; i < m; i++) {
				String[] cur = in.readLine().split(" ");
				int a = Integer.parseInt(cur[0]);
				int b = Integer.parseInt(cur[1]);
				char c = cur[2].charAt(0);
				graph[a][c + 0] = b;
			}

			int currentPosition = 1;

			for (int i = 0; i < word.length(); i++) {
				char curChar = word.charAt(i);
				
				currentPosition = graph[currentPosition][curChar + 0];
				if (currentPosition == 0) {
					break;	
				}
			}

			if (ends[currentPosition]) {
				out.write("Accepts");
			} else {
				out.write("Rejects");
			}
			out.flush();
			out.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

// Задача A. Слово и ДКА

// Имя входного файла: problem1.in
// Имя выходного файла: problem1.out
// Ограничение по времени: 2 секунды
// Ограничение по памяти: 256 мегабайт

// Задан детерминированный конечный автомат и слово. Определить, допускает ли данный ДКА
// заданное слово.

// Формат входного файла
// В первой строке входного файла находится слово, состоящее из не более чем 100000 строчных
// латинских букв.

// Во второй строке содержатся числа n, m и k — количество состояний, переходов и допускающих
// состояний в автомате соответственно. (1 <= n, m <= 100000, 1 <= k <= n).
// В следующей строке содержатся k чисел — номера допускающих состояний (состояния пронумерованы от 1 до n).
// В следующих m строках описываются переходы в формате “a b c”, где a — номер исходного
// состояния перехода, b — номер состояния, в которое осуществляется переход и c — символ (строчная
// латинская буква), по которому осуществляется переход.

// Стартовое состояние автомата всегда имеет номер 1. Гарантируется, что из любого состояния не
// более одного перехода по каждому символу.

// Формат выходного файла
// Требуется выдать строку “Accepts”, если автомат принимает заданное слово, или “Rejects” в
// противном случае.
