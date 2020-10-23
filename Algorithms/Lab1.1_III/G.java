import java.util.*;
import java.io.*;
import java.lang.*;
 
public class G {
	public static int[][] dp;
	public static int[][] answer;
	public static String question;

	public static String rec (int l, int r) {
		if (l >= r) {
			return "";
		}

		if (answer[l][r] == -1) {
			return question.charAt(l) + rec(l+1, r-1) + question.charAt(r);
		} else {
			return rec(l, answer[l][r]) + rec(answer[l][r] + 1, r);
		}


	}

	public static int min(int i, int j) {
		int min = 200;
		answer[i][j] = -1;
		int l = i;
		int r = j;

		if ((question.charAt(i) == '(' && question.charAt(j) == ')') ||
			(question.charAt(i) == '[' && question.charAt(j) == ']') ||
			(question.charAt(i) == '{' && question.charAt(j) == '}')) {

			min = (i < (j - 1) ? Math.min(dp[i+1][j-1], dp[i][j]) : 0);
		}

		for (int k = l; k < r; k++) {
			if (dp[l][k] + dp[k+1][r] < min) {
				min = dp[l][k] + dp[k+1][r];
				answer[l][r] = k;
			}
		}
		return min;
	}


	public static void main(String[] args) {
		try{
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
			Scanner scan = new Scanner(System.in);
			question = scan.next();
			dp = new int[question.length()][question.length()];
			answer = new int[question.length()][question.length()];

			for (int i = 0; i < question.length(); i++) {
				for (int j = 0; j < question.length(); j++) {
					dp[i][j] = 200;
				}
			}

			for (int i = 0; i < question.length(); i++) {
				dp[i][i] = 1;
			}

			for (int j = 1; j < question.length(); j++) {
				for (int i = j-1; i > -1; i--) {
					dp[i][j] = min(i, j);
				}
			}
			out.write(rec(0, question.length() - 1));


			out.flush();
		}catch (IOException e) {
			System.out.println(e);
		}
	}
}

/*Дана строка, составленная из круглых, квадратных и фигурных скобок. Определите, 
какое наименьшее количество символов необходимо удалить из этой строки, чтобы оставшиеся символы образовывали правильную скобочную последовательность.

Входные данные
Во входном файле записана строка из круглых, квадратных и фигурных скобок. Длина строки не превосходит {100} символов.

Выходные данные
Выведите строку максимальной длины, являющейся правильной скобочной последовательностью, 
которую можно получить из исходной строки удалением некоторых символов.*/