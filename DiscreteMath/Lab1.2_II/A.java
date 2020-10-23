import java.io.*;
import java.util.*;

public class A {
	public static boolean[][][] graph;
	public static int n;
	public static boolean[] ends;
	public static String word;

	public static void main(String[] args) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("automaton.in")));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("automaton.out")));

			String[] parametrs = in.readLine().split(" ");

			n = Integer.parseInt(parametrs[0]); // number of productions
			char s = parametrs[1].charAt(0); // start symbol

			graph = new boolean[123][123][123];

			for (int i = 0; i < n; i++) {
				String[] fromTo = in.readLine().split(" -> ");
				if (fromTo[1].length() == 1) {
					graph[fromTo[0].charAt(0)][fromTo[1].charAt(0)][0] = true;
					// System.out.println((int )fromTo[0].charAt(0) + " by " + (int)fromTo[1].charAt(0) + " to " + 0);
				} else {
					graph[fromTo[0].charAt(0)][fromTo[1].charAt(0)][fromTo[1].charAt(1)] = true;
					// System.out.println((int)fromTo[0].charAt(0) + " by " + (int)fromTo[1].charAt(0) + " to " + (int)fromTo[1].charAt(1));
				}
				// System.out.println(" |" + fromTo[0] + "| --- |" + fromTo[1] + "| ");
			}

			int words = Integer.parseInt(in.readLine());

			for (int t = 0; t < words; t++) {
				String word = in.readLine();


				HashSet<Integer> cur = new HashSet<>();
				cur.add((int)s);

				for (int i = 0; i < word.length(); i++) {
					HashSet<Integer> next = new HashSet<>();

					for (Integer g : cur) {
						for (int j = 0; j < 123; j++) {
							if (graph[(int)g][word.charAt(i)][j]) {
								next.add((int)j);
							}
						}
					}
					cur = next;
				}

				boolean ans = false;
				for (Integer se : cur) {
					if (se == 0) {
						ans = true;
						break;
					}
				}

				if (ans) {
					out.write("yes\n");
				} else {
					out.write("no\n");
				}
			}

			out.flush();
			out.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


// Задача A. Автоматная грамматика
// Имя входного файла: automaton.in
// Имя выходного файла: automaton.out
// Дана автоматная грамматика, задающая язык L. Дан набор слов {wi}
// m
// i=1. Для каждого слова
// wi требуется определить, принадлежит ли оно языку L.
// Формат входного файла
// В первой строке задано n — число продукций и стартовый символ. Следующие n строк содержат
// продукции вида либо Ai → biCi
// , либо Ai → bi
// . В следующей строке задано число слов m, которые
// требуется проверить. Далее следуют m строк, содержащих слова wi (1 6 n 6 100, 1 6 m 6 20,
// 1 6 wi 6 10000).
// Каждый нетерминальный символ представлен в виде большой буквы латинского алфавита, а
// терминальный — маленькой. Все слова состоят только из маленьких букв латинского алфавита.
// Формат выходного файла
// Для каждого wi выведите «yes», если слово принадлежит языку, или «no» в противном случае.
// Пример
// automaton.in automaton.out
// 2 S
// S -> aA
// A -> b
// 2
// ab
// aa
// yes
// no
}