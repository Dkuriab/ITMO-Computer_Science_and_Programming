import java.io.*;
import java.util.*;

public class C {

	public static boolean isNonTerm(int a) {
		return a >= 65 && a < 91;
	}

	public static void main(String[] args) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("useless.in")));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("useless.out")));

			String[] parametrs = in.readLine().split(" ");
			int n = Integer.parseInt(parametrs[0]); // number of productions
			char s = parametrs[1].charAt(0); // start symbol

			LinkedHashMap<Integer, List<Set<Integer>>> productions = new LinkedHashMap<>();
			LinkedHashMap<Integer, List<Set<Integer>>> productionsOnlyGen = new LinkedHashMap<>();
			TreeSet<Integer> generating = new TreeSet<Integer>();

			TreeSet<Integer> allNT = new TreeSet<Integer>();


			for (int i = 0; i < n; i++) {
				String[] production = in.readLine().split(" ->");
				int from = (int)production[0].charAt(0);
				
				allNT.add(from);
				
				if (production.length == 1) {
					generating.add(from);

					List<Set<Integer>> curGen = productionsOnlyGen.getOrDefault(from, new LinkedList<Set<Integer>>());
					curGen.add(Set.of(0));
					productionsOnlyGen.put(from, curGen);
				} else {
					boolean onlyTerm = true;
					HashSet<Integer> newRight = new HashSet<Integer>();

					for (int j = 1; j < production[1].length(); j++) {
						newRight.add((int)production[1].charAt(j));

						if (isNonTerm(production[1].charAt(j))) {
							allNT.add((int)production[1].charAt(j));
							onlyTerm = false;
						}
					}


					if (onlyTerm) {
						generating.add(from);
						List<Set<Integer>> curGen = productionsOnlyGen.getOrDefault(from, new LinkedList<Set<Integer>>());
						curGen.add(newRight);
						productionsOnlyGen.put(from, curGen);
					} else {
						List<Set<Integer>> cur = productions.getOrDefault(from, new LinkedList<Set<Integer>>());
						cur.add(newRight);
						productions.put(from, cur);
					}
				}
			}

// finding generating
			boolean changes = true;
			while (changes) {
				int before = generating.size();

				for (int key : productions.keySet()) {
					for (Set<Integer> cir : productions.get(key)) {

						boolean isGen = true;
						
						for (int f : cir) {
							if (isNonTerm(f) && !generating.contains(f)) {
								isGen = false;
							}
						}

						if (isGen) {
							generating.add(key);

							List<Set<Integer>> curGen = productionsOnlyGen.getOrDefault(key, new LinkedList<Set<Integer>>());
							curGen.add(cir);
							productionsOnlyGen.put(key, curGen);

						}
					}
				}

				if (generating.size() == before) {
					changes = false;
				}
			}
// finding generating
			boolean isS = false;
			for (int key : productionsOnlyGen.keySet()) {
				for (Set<Integer> curSet : productionsOnlyGen.get(key)) {
					if (key == s) {
						isS = true;
					}

					for (int v : curSet) {
						if (v == s) {
							isS = true;
						}
					}
				}
			}

// finding achivable

			changes = true;
			TreeSet<Integer> achivable = new TreeSet<Integer>(Set.of((int)s));

			while (changes) {
				int before = achivable.size();

				for (int key : productionsOnlyGen.keySet()) {
					if (achivable.contains(key)) {
						for (Set<Integer> cir : productionsOnlyGen.get(key)) {
							achivable.addAll(cir);	
						}
					}
				}	

				if (achivable.size() == before) {
					changes = false;
				}
			}
// finding achivable
			// System.out.println("allNT: " + allNT);

			// for (int f : allNT) {
			// 	System.out.println((char)f);
			// }

			allNT.removeAll(achivable);

			if (!isS) {
				allNT.add((int)s);
			}

			for (int o : allNT) {
				out.write((char)o + " ");
			}

			out.flush();
			out.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

// Задача C. Бесполезные символы
// Имя входного файла: useless.in
// Имя выходного файла: useless.out
// Дана КС-грамматика. Найдите все бесполезные нетерминалы.
// Формат входного файла
// В первой строке задано число продукций n и стартовый символ. Следующие n строк содержат
// продукции вида Ai → wi
// , где Ai — нетерминал, а Ni — строка из терминалов и нетерминалов
// (1 6 n 6 100, 0 6 |Ni
// | 6 50).
// Каждый нетерминальный символ представлен в виде большой буквы латинского алфавита, а
// терминальный — маленькой. Строка Ni может быть пустой.
// Формат выходного файла
// Выведите через пробел в лексикографическом порядке множество бесполезных символов.
// Пример
// useless.in useless.out
// 5 S
// S -> AB
// S -> C
// A -> a
// B -> b
// T -> c
// C T

}