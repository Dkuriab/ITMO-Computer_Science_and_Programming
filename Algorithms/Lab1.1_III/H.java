import java.util.*;
import java.io.*;
 
public class H {

	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		int max = (1 << n + 1) - 1;

		int[][] roads = new int[n + 1][n + 1];
		int[][] dp = new int[n + 1][max + 1];

		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				roads[i][j] = scan.nextInt();
			}
		}

		for (int i = 0; i <= n; i++) {
			for (int j = 0; j <= max; j++) {
				dp[i][j] = 1000000000;
			}
		}
		dp[0][0] = 0;

		for (int m = 0; m <= max; m++) {	
			for (int i = 0; i <= n; i++) {
				for (int j = 0; j <= n; j++) {
				    if (((m >> j) & 1) == 1) {
				    	dp[i][m] = Math.min(dp[i][m], dp[j][m ^ (1 << j)] + roads[i][j]);

			    	}
			  	}
			}
		}
				
		System.out.println(dp[0][max]);

	    int i = dp[0][0];
	    for (int p = 0; p <= n; p++) {
	        for (int j = 0; j <= n; j++) {
	            if (((max >> j) & 1) == 1 && dp[i][max] == dp[j][max ^ (1 << j)] + roads[i][j] && j != 0) {
	                System.out.print(j + " ");
	                i = j;
	                max -= (1 << j);
	            }
	        }
	    }
	}
}

/*Продавец аквариумов для кошек хочет объехать n городов, посетив каждый из них ровно один раз. Помогите ему найти кратчайший путь.


Входные данные
Первая строка входного файла содержит натуральное число n (1 ≤ n ≤ 13) — количество городов. 
Следующие n строк содержат по n чисел — длины путей между городами.

В i-й строке j-е число — ai, j — это расстояние между городами i и j (0 ≤ ai, j ≤ 106; ai, j = aj, i; ai, i = 0).

Выходные данные
В первой строке выходного файла выведите длину кратчайшего пути. Во второй строке выведите n чисел — порядок, в котором нужно посетить города.*/