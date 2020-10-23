import java.util.*;
import java.io.*;
 
public class K {
	public static BufferedReader in;
	public static BufferedWriter out;
	public static int[] curentCows;
	public static int[] weight;
	public static int w;
	public static StringBuilder ans = new StringBuilder();
	public static int answer = 0;


	public static void rec(int unused) {
	
			if (unused == 0) {
				return;
			}
			answer++;

			int bestWeight = 0;
			int bestMask = 0;
			int bestCount = 0;

			for (int mask = 1; mask < (1 << unused); mask++) {

				int weightForMask = 0;
				int howManyCowsThere = 0;

				for (int j = 0; j < unused; j++) {
					if ((mask >> j) % 2 == 1) {
						howManyCowsThere++;
						weightForMask += weight[curentCows[j + 1]];
					}
				}

				if (weightForMask <= w && weightForMask >= bestWeight) {

					bestWeight = weightForMask;
					bestMask = mask;
					bestCount = howManyCowsThere;
				}
			}

			ans.append(bestCount + " ");

			for (int i = 0; i < unused; i++) {
				if ((bestMask >> i) % 2 == 1) {
					ans.append(curentCows[i + 1] + " ");
				}
			}
			ans.append("\n");
			
			int h = 1; 			// refill curentCows
			for (int i = 0; i < unused; i++) {
				if ((bestMask >> i) % 2 == 0) {
					curentCows[h] = curentCows[i + 1];
					h++;
				}
			} 

			unused -= bestCount;
			rec(unused);
	}

	public static void main(String[] args) {
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream("skyscraper.in"), "UTF-8"));
	        out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("skyscraper.out"), "UTF-8"));
	            
	        Scanner scan = new Scanner (in.readLine());
	        int n = scan.nextInt();
	        w = scan.nextInt();

	        weight = new int[n + 1];

	        for (int i = 1; i <= n; i++) {
	        	scan = new Scanner (in.readLine());
	        	weight[i] = scan.nextInt();
	        }

	        curentCows = new int[n + 1];

	        for (int j = 1; j <= n; j++) {
	        	curentCows[j] = j;
	        }

	        rec(n);
	        out.write(answer + "\n"+ ans.toString());
	        out.flush();

        }catch (IOException e) {
        	System.out.println(e);
        }
	}
}

/*Коровы любят соревноваться в беге по лестницам небоскребов. А вниз потом едут на лифте. 
Лифт имеет максимальную вместимость w (1 ≤ w ≤ 108) фунтов, а корова номер i весит ci (1 ≤ ci ≤ w) фунтов.

Помогите Бесси определить минимальное количество спусков лифта, чтобы переместить вниз все n (1 ≤ n ≤ 18) коров.

Сумма весов коров в каждом спуске не должна превышать W.

Входные данные
Первая строка содержит два целых числа n и w (1 ≤ n ≤ 18; 1 ≤ w ≤ 108).

Следующие n строк содержат веса коров: i-я строк содержит целое число ci (1 ≤ ci ≤ w).

Выходные данные
Первая строка должна содержать число r — минимально количество спусков лифта.

Каждая из следующих r строк содержит множество коров, которые сейчас спускаются. 
Строка начинается с количество коров на данном спуске, далее содержатся номера коров.*/