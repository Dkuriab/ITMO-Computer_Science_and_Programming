import java.util.*;
import java.io.*;

public class A {
	public static int[] a;
	public static int[][] dp;


	public static int max(int start, int stop) {
		int max = dp[0][stop - 1];
		int final_max = 0;

		start = (start < 1 ? 1 : start);

		for (int t = start; t < stop; t++) {
			if (dp[0][t] >= max) {
				max = dp[0][t];
				final_max = t;
			} 
		}

		dp[1][stop] = dp[1][final_max] + 1;

		dp[2][stop] = final_max;

		return max;
	}

	public static void main(String[] args) {
		try {
	        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt"), "UTF-8"));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("output.txt"), "UTF-8"));
	        
	        Scanner scan = new Scanner (in);
	        int n = scan.nextInt();// column quantity
	        int k = scan.nextInt();// jump length


	        a = new int[n + 3];
	        dp = new int[3][n + 3];
	       	
	       	for (int i = 2; i < n; i++) {
	       		a[i] = scan.nextInt();
	       	} 

	       	for (int i = 2; i <= n; i++) {
	       		dp[0][i] = a[i] + max(i - k, i);
	       	}
	       	
	       	out.write(dp[0][n] + "\n" + dp[1][n] + "\n" + "1 ");
	       	
	       	int cur = n;
	       	int[] done = new int[dp[1][n]];

	       	for (int i = 0; i < dp[1][n]; i++) {
	       		done[i] = cur;
	       		cur = dp[2][cur];
	       	}
	       	
	       	for (int i = dp[1][n] - 1; i >= 0 ; i--) {
	       		out.write(done[i] + " ");
	        }


	        out.flush();

	    }catch (IOException e) {
				System.out.println(e);
		}     
	}
}

/* Кузнечик прыгает по столбикам, расположенным на одной линии на равных расстояниях друг от друга.
Столбики имеют порядковые номера от 1 до N. В начале Кузнечик сидит на столбике с номером 1 и хочет добраться до столбика с номером N.
Он может прыгнуть вперед на расстояние от 1 до K столбиков, считая от текущего.

На каждом столбике Кузнечик может получить или потерять несколько золотых монет (для каждого столбика это число известно). 
Определите, как нужно прыгать Кузнечику, чтобы собрать наибольшее количество золотых монет. Учитывайте, что Кузнечик не может прыгать назад.

Входные данные
В первой строке вводятся два натуральных числа: N и K (2 ≤ N, K ≤ 10000), разделённые пробелом. 
Во второй строке записаны через пробел N - 2 целых числа – количество монет, которое Кузнечик получает на каждом столбике, от 2-го до N - 1-го. 
Если это число отрицательное, Кузнечик теряет монеты. Гарантируется, что все числа по модулю не превосходят 10 000.

Выходные данные
В первой строке программа должна вывести наибольшее количество монет, которое может собрать Кузнечик. 
Во второй строке выводится число прыжков Кузнечика, а в третьей строке – номера всех столбиков, 
которые посетил Кузнечик (через пробел в порядке возрастания).*/