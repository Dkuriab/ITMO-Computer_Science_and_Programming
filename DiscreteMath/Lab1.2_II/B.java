import java.io.*;
import java.util.*;

public class B {

	public static boolean isNotTeerm(int a) {
		return a >= 65 && a < 91;
	}

	public static void main(String[] args) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("epsilon.in")));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("epsilon.out")));

			String[] parametrs = in.readLine().split(" ");
			int n = Integer.parseInt(parametrs[0]); // number of productions
			char s = parametrs[1].charAt(0); // start symbol

			LinkedHashMap<Integer, List<Set<Integer>>> productions = new LinkedHashMap<>();
			TreeSet<Integer> eps = new TreeSet<Integer>();


			for (int i = 0; i < n; i++) {
				String[] production = in.readLine().split(" ->");
				if (production.length == 1) {
					eps.add((int)production[0].charAt(0));
				} else {
					boolean onlyNotTerm = true;
					HashSet<Integer> newRight = new HashSet<Integer>();
					for (int j = 1; j < production[1].length(); j++) {
						if (isNotTeerm(production[1].charAt(j))) {
							newRight.add((int)production[1].charAt(j));
						} else {
							onlyNotTerm = false;
						}
					}

					if (onlyNotTerm) {
						List<Set<Integer>> cur = productions.getOrDefault((int)production[0].charAt(0), new LinkedList<Set<Integer>>());
						cur.add(newRight);

						productions.put((int)production[0].charAt(0), cur);
					}
				}
			}	

			boolean changes = true;

			while (changes) {
				int before = eps.size();

				for (int key : productions.keySet()) {
					for (Set<Integer> cir : productions.get(key)) {
						if (eps.containsAll(cir)) {
							eps.add(key);
						}
					}
				}

				if (eps.size() == before) {
					changes = false;
				}
			}

			for (int o : eps) {
				out.write((char)o + " ");
			}

			out.flush();
			out.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

// Задача B. ε-продукции
// Имя входного файла: epsilon.in
// Имя выходного файла: epsilon.out
// Дана КС-грамматика. Найдите все нетерминалы, из которых выводится ε.
// Формат входного файла
// В первой строке задано число продукций n и стартовый символ. Следующие n строк содержат
// продукции вида Ai → Ni
// , где Ai — нетерминал, а Ni — строка из терминалов и нетерминалов
// (1 6 n 6 100, 0 6 |Ni
// | 6 50).
// Каждый нетерминальный символ представлен в виде большой буквы латинского алфавита, а
// терминальный — маленькой. Строка Ni может быть пустой.
// Формат выходного файла
// Выведите через пробел в лексикографическом порядке множество нетерминалов, из которых
// выводится ε.
// Пример
// epsilon.in epsilon.out
// 4 S
// S -> AB
// A -> a
// B ->
// B -> b
// B

}