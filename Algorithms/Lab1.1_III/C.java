import java.util.*;
import java.io.*;
import java.lang.*;
 
public class C {

	public static void main(String[] args) {
		try{
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
			Scanner scan = new Scanner(System.in);
			int k = scan.nextInt();

			int[] a = new int[k + 1];
			int[] dp = new int[k + 2];
			int[] dpp = new int[k + 2];
			int[] dp_pos = new int[k + 2];
			int maxj = 0;

			for (int i = 1; i <= k; i++) {
				a[i] = scan.nextInt();
				dp[i] = 1000000000;
			}
			dp[0] = -1000000000;
			dp[k] = 1000000000;
			dp[k + 1] = 1000000000;
			
			for (int i = 1; i <= k; i++) {
				int j = i + 1;
				while (a[i] <= dp[j]) {
					j--;
				}
				dp[j + 1] = a[i];

				dp_pos[j + 1] = i;

				dpp[i] = dp_pos[j];
			}

			int t = k; 
			while (dp_pos[t] == 0) {
				t--;
			}

			int f = dp_pos[t]; 

			int[] done = new int[t + 1];
			int i = t;

			while (f > 0) {
				done[i--] = a[f];
				f = dpp[f];
			}

			out.write(t + "\n");
			for (int g = 1; g <= t; g++) {
				out.write(done[g] + " ");				
			}
			out.flush();

		}catch (IOException e) {
			System.out.println(e);
		}
	}

}

/*Пусть a1, a2, ..., an — числовая последовательность. Длина последовательности — это количество элементов этой последовательности. 
Последовательность ai1, ai2, ..., aik называется подпоследовательностью последовательности a, если 1 ≤ i1 < i2 < ... < ik ≤ n. 
Последовательность a называется возрастающей, если a1 < a2 < ... < an.

Вам дана последовательность, содержащая n целых чисел. Найдите ее самую длинную возрастающую подпоследовательность.

Входные данные
В первой строке задано одно число n (1 ≤ n ≤ 2000) — длина подпоследовательности. В следующей строке задано n целых чисел ai 
( - 109 ≤ ai ≤ 109) — элементы последовательности.

Выходные данные
В первой строке выведите число k — длину наибольшей возрастающей подпоследовательности. 
В следующей строке выведите k чисел — саму подпоследовательность.*/