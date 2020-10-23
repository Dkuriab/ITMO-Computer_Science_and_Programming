import java.util.*;
import java.io.*;

public class B {
	public static int[][] a;
	public static int[][] dp;
	public static char[][] p;


	public static void main(String[] args) {
		try {
	        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt"), "UTF-8"));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("output.txt"), "UTF-8"));
	        
	        Scanner scan = new Scanner (in);
	        int n = scan.nextInt();// row
	        int m = scan.nextInt();// column


	        a = new int[n + 1][m + 1];
	        dp = new int[n + 1][m + 1];
	        p = new char[n + 1][m + 1];
	       	
	       	for (int i = 1; i <= n; i++) {
	       		for (int j = 1; j <= m; j++) {
       				a[i][j] = scan.nextInt();
	       		}
	       	} 

	       	for (int i = 1; i <= n; i++) {
	       		p[i][1] = 'D';
	       		dp[i][1] = dp[i-1][1] + a[i][1];
	       	}
	       	for (int j = 1; j <= m; j++) {
	       		p[1][j] = 'R';
	       		dp[1][j] = dp[1][j-1] + a[1][j];
	       	}

			for (int i = 2; i <= n; i++) {
	       		for (int j = 2; j <= m; j++) {
       				if (dp[i][j-1] >= dp[i-1][j]) {
       					p[i][j] = 'R';
       					dp[i][j] = dp[i][j-1] + a[i][j];
       				} else {
       					p[i][j] = 'D';
       					dp[i][j] = dp[i-1][j] + a[i][j];
       				}
	       		}
	       	} 
	       	StringBuilder rd = new StringBuilder();
	       	out.write(dp[n][m] + "\n");
	       	
	       	int i = n;
	       	int j = m;
	       	while (!(i == 1 && j == 1)) {
	       		if (p[i][j] == 'R') {
	       			rd.append("R");
	       			j--;
	       		} else if (p[i][j] == 'D'){
	       			rd.append("D");
	       			i--;
	       		} else {
	       			break;
	       		}
	       		//System.out.println ("i: " + i + " j: " + j);
	       	} 

	       	out.write(rd.reverse().toString());
	        out.flush();

	    }catch (IOException e) {
				System.out.println(e);
		}     
	}
}

/*Черепаха хочет переползти из левого верхнего угла поля размером N на M клеток (2 ≤ N, M ≤ 1000) в правый нижний. 
За один шаг она может переместиться на соседнюю клетку вправо или на соседнюю клетку вниз. 
Кроме того, проходя через каждую клетку, Черепаха получает (или теряет) несколько золотых монет (это число известно для каждой клетки).

Определите, какое максимальное количество монет может собрать Черепаха по пути и как ей нужно идти для этого.

Входные данные
В первой строке вводятся два натуральных числа: N и M (2 ≤ N, M ≤ 1000), разделённые пробелом. 
В каждой из следующих N строк записаны через пробел по M чисел aij(|aij| ≤ 10), которые обозначают количество монет, 
получаемых Черепашкой при проходе через каждую клетку. Если это число отрицательное, Черепашка теряет монеты.

Выходные данные
В первой строке программа должна вывести наибольшее количество монет, которое может собрать Черепаха. 
Во второй строке без пробелов выводятся команды, которые нужно выполнить Черепахе: буква 'R' 
(от слова right) обозначает шаг вправо, а буква 'D' (от слова down) – шаг вниз.*/