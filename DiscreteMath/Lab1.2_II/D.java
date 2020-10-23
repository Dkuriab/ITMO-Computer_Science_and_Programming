import java.io.*;
import java.util.*;

public class D {

	public static boolean isNonTerm(int a) {
		return a >= 65 && a < 91;
	}

	public static void main(String[] args) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("nfc.in")));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("nfc.out")));

			String[] parametrs = in.readLine().split(" ");
			int n = Integer.parseInt(parametrs[0]); // number of productions
			char s = parametrs[1].charAt(0); // start symbol

			LinkedHashMap<Integer, LinkedList<LinkedList<Integer>>> productions = new LinkedHashMap<>();

			for (int i = 0; i < n; i++) {
				String curInfo = in.readLine();
					String[] production = curInfo.split(" -> ");
					int from = (int)production[0].charAt(0);
					LinkedList<Integer> curR = new LinkedList<>();

					for (int j = 0; j < production[1].length(); j++) {
						char c = production[1].charAt(j);
						curR.add((int)c);
					}

					LinkedList<LinkedList<Integer>> variable = productions.getOrDefault(from, new LinkedList<LinkedList<Integer>>());
					variable.add(curR);
					productions.put(from, variable);
			}

			String word = in.readLine();
			long[][][] dp = new long[100][word.length()][word.length()];

			for (int key : productions.keySet()) {
				for (LinkedList<Integer> list : productions.get(key)) {
					if (list.size() == 1) {
						int sym = (int)list.toArray()[0];

						for (int i = 0; i < word.length(); i++) {
							if ((int)word.charAt(i) == sym) {
								dp[key][i][i] = 1;
							}
						}
					}
				}
			}

			for (int i = word.length() - 1; i >= 0; i--) {
				for (int j = i + 1; j < word.length(); j++) {
					for (int key : productions.keySet()) {
						//dp[key][i][j]
						for (LinkedList<Integer> list : productions.get(key)) {
							if (list.size() == 2) {
								Object[] prod = list.toArray();
								int A = (int)prod[0];
								int B = (int)prod[1];

								for (int k = i; k < j; k++) {
									dp[key][i][j] = (dp[key][i][j] + dp[A][i][k] * dp[B][k + 1][j]) % 1000000007;
								}
							}
						}
					}
				}
			}

			out.write(dp[s][0][word.length() - 1] % 1000000007 + "");

			out.flush();
			out.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

// Задача D. НФХ
// Имя входного файла: nfc.in
// Имя выходного файла: nfc.out
// Дана КС-грамматика в нормальной форме Хомского. Дано слов w. Требуется посчитать количество способов вывести w в заданной грамматике. Напомним, что грамматика находится в нормальной форме Хомского, если любая ее продукция имеет вид либо A → BC, либо A → a.
// Формат входного файла
// В первой строке задано число продукций n и стартовый символ. Следующие n строк содержат
// продукции вида либо Ai → BiCi
// , либо Ai → ai
// , где Ai
// , Bi
// , Ci — нетерминалы, а ai — терминал. В
// следующей строке задано слово w (1 6 n 6 100, 1 6 |w| 6 100).
// Каждый нетерминальный символ представлен в виде большой буквы латинского алфавита, а
// терминальный — маленькой. Слово w состоит только из маленьких букв латинского алфавита.
// Формат выходного файла
// Для каждого wi выведите число способов породить слово в заданной грамматике по модулю
// 1000000007.
// Пример
// nfc.in 			nfc.out
// 4 S
// S -> AB
// S -> AA
// A -> a
// B -> a
// aa
// 					2

}