import java.io.*;

public class F {

	public static boolean[] visitedFirst = new boolean[100010];
	public static boolean[] visitedSecond = new boolean[100010];

	public static int[] turnedTo = new int[100010];

	public static int[][] graphFirst;
	public static int[][] graphSecond;
	public static boolean[] endsFirst;
	public static boolean[] endsSecond;

	public static boolean compare(int u, int v) { 
		visitedFirst[u] = true;
		visitedSecond[v] = true;
			   
		if (endsFirst[u] != endsSecond[v])  {
	     	return false; 
	    }  
	   	boolean ans = true;
	   	turnedTo[u] = v;
	   	for (int symbol = 80; symbol < 150; symbol++) {      
	     	int uStep = graphFirst[u][symbol];
	     	int vStep = graphSecond[v][symbol];

	     	if ((uStep == 0 && vStep != 0) || (vStep == 0 && uStep != 0)) {
	       		return false;
	     	} else {
		     	if (visitedFirst[uStep]) { 
		     		if (vStep != turnedTo[uStep]) {
		       			ans = false;
		     		}
		       	} else {
		       		if (!compare(uStep, vStep)) {
		       			ans = false;
		       		}
		       	}
	        }
       	}               
	         
	   	return ans;
	}

	public static void main(String[] args) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("isomorphism.in")));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("isomorphism.out")));

			String[] parametrsFirst = in.readLine().split(" ");
			int nFirst = Integer.parseInt(parametrsFirst[0]); // number of points
			int mFirst = Integer.parseInt(parametrsFirst[1]); // number of roads
			int kFirst = Integer.parseInt(parametrsFirst[2]); // number of ends


// Reading Firrst DKA //
			graphFirst = new int[nFirst + 1][150];
			endsFirst = new boolean[nFirst + 1];

			String[] endsString = in.readLine().split(" ");
			for (int i = 0; i < kFirst; i++) {
				endsFirst[Integer.parseInt(endsString[i])] = true;
			}

			for (int i = 0; i < mFirst; i++) {
				String[] cur = in.readLine().split(" ");
				int a = Integer.parseInt(cur[0]);
				int b = Integer.parseInt(cur[1]);
				char c = cur[2].charAt(0);
				graphFirst[a][c + 0] = b;
			}
// Reading Firrst DKA //			


// Reading Second DKA //

			String[] parametrsSecond = in.readLine().split(" ");
			int nSecond = Integer.parseInt(parametrsSecond[0]); // number of points
			int mSecond = Integer.parseInt(parametrsSecond[1]); // number of roads
			int kSecond = Integer.parseInt(parametrsSecond[2]); // number of ends

			graphSecond = new int[nSecond + 1][150];
			endsSecond = new boolean[nSecond + 1];

			endsString = in.readLine().split(" ");
			for (int i = 0; i < kSecond; i++) {
				endsSecond[Integer.parseInt(endsString[i])] = true;
			}

			for (int i = 0; i < mSecond; i++) {
				String[] cur = in.readLine().split(" ");
				int a = Integer.parseInt(cur[0]);
				int b = Integer.parseInt(cur[1]);
				char c = cur[2].charAt(0);
				graphSecond[a][c + 0] = b;
			}
// Reading Second DKA //


			if (compare(1, 1)) {
				out.write("YES");
			} else {
				out.write("NO");
			}
			out.flush();
			out.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

// Задача F. Изоморфизм ДКА
// Имя входного файла: isomorphism.in
// Имя выходного файла: isomorphism.out
// Ограничение по времени: 2 секунды
// Ограничение по памяти: 256 мегабайта
// Задано два детерминированных конечных автомата. Определить, изоморфны ли они друг другу.
// Гарантируется, что все состояния автоматов достижимы.
// Формат входного файла
// Во входном файле находятся два описания ДКА. Формат описания следующий:
// Во первой строке описания содержатся числа n, m и k — количество состояний, переходов и
// допускающих состояний в автомате соответственно. (1 <= n, m <= 100000, 1 <= k <= n).
// В следующей строке содержатся k чисел — номера допускающих состояний (состояния пронумерованы от 1 до n).
// В следующих m строках описываются переходы в формате “a b c”, где a — номер исходного
// состояния перехода, b — номер состояния, в которое осуществляется переход и c — символ (строчная
// латинская буква), по которому осуществляется переход.
// Стартовое состояние автомата всегда имеет номер 1. Гарантируется, что из любого состояния не
// более одного перехода по каждому символу.
// Формат выходного файла
// Требуется выдать строку “YES”, если автоматы изоморфны, или “NO” в противном случае.
// Пример
// isomorphism.in isomorphism.out
// 3 3 1
// 3
// 1 2 a
// 1 3 c
// 2 3 b
// 3 3 1
// 2
// 1 3 a
// 1 2 c
// 3 2 b
// YES
// Примечание
// Автоматы называются изоморфными, если существует биекция между их вершинами такая, что
// сохраняются все переходы, терминальные состояния соответствуют терминальным а начальные –
// начальным
