import java.util.*;
import java.io.*;
import java.lang.*;
 
public class E { //Задача о редакционном расстоянии

	public static int min(int a, int b, int c) {
		if (a <= b && a <=c) {
			return a;
		} else if (b <= a && b <= c) {
			return b;
		} else {
			return c;
		}
	}

	public static void main(String[] args) {
		try{
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
			Scanner scan = new Scanner(System.in);
			String a = scan.next();
			String b = scan.next();

			int[][] dp = new int[a.length() + 1][b.length() + 1];
			for (int i = 0; i < a.length() + 1; i++) {
				dp [i][0] = i;
			}
			for (int j = 0; j < b.length() + 1; j++) {
				dp [0][j] = j;
			}

			for (int i = 1; i < a.length() + 1; i++) {
				for (int j = 1; j < b.length() + 1; j++) {
					if (a.charAt(i - 1) == b.charAt(j - 1)) {	
						dp[i][j] = dp[i-1][j-1];
					} else {
						dp[i][j] = min(dp[i-1][j-1] + 1, dp[i-1][j] + 1, dp[i][j-1] + 1);
					}
				}
			}

			out.write(dp[a.length()][b.length()] + "");
			out.flush();


		}catch (IOException e) {
			System.out.println(e);
		}
	}
}

/*Дана текстовая строка. С ней можно выполнять следующие операции:

1. Заменить один символ строки на другой символ.

2. Удалить один произвольный символ.

3. Вставить произвольный символ в произвольное место строки.

Например, при помощи первой операции из строки «СОК» можно получить строку «СУК», при помощи второй операции — строку «ОК», 
при помощи третьей операции — строку «СТОК».

Минимальное количество таких операций, при помощи которых можно из одной строки получить другую, называется стоимостью 
редактирования или расстоянием Левенштейна.

Определите расстояние Левенштейна для двух данных строк.

Входные данные
Программа получает на вход две строки, длина каждой из которых не превосходит 1000 символов, строки состоят только из заглавных латинских букв.

Выходные данные
Требуется вывести одно число — расстояние Левенштейна для данных строк.*/