import java.util.*;

public class I {

    public static long[][][] dp;
    public static boolean[][] hole;

    public static void sum(int a, int i, int j, int p) {
        dp[i][j + 1][a] += dp[i][j][p];
    }

    public static boolean bitMask(int i, int p) {
        return (p >> (i)) % 2 == 1;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int m = scan.nextInt();

        dp = new long[n + 1][m + 1][1 << m]; // dp[j][i][profile] -> how many keyses j full column, i next column, profile profile

        dp[0][0][0] = 1; // m - column // n - rows
        int max = 1 << m;
        
        hole = new boolean[n + 1][m + 1]; // is it hole at i j

        for (int i = 0; i < n; i++) {
            String row = scan.next();
            for (int j = 0; j < m; j++) {
                hole[i][j] = (row.charAt(j) == 'X');
            }
        }


        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                for (int profile = 0; profile < max; profile++) {
	                        
	                    if (bitMask(j, profile) || hole[i][j]) {
	                    	sum(profile & (max - 1 - (1 << j)), i, j, profile);
	                	} else {
							if (!hole[i + 1][j]) {
	                			sum(profile + (1 << j), i, j, profile);
							}
	                		if ((j < m - 1) && !bitMask(j + 1, profile) && !hole[i][j + 1]) {
	                			sum(profile + (1 << (j + 1)), i, j, profile);
	                		}	
	                    }  
	                }
	                
	                for (int profile = 0; profile < max; profile++) {
	                    dp[i + 1][0][profile] = dp[i][m][profile];
	                }
	            }
	        }
	    System.out.println(dp[n][0][0]);
	    
    }
}

/*
Вам дана прямоугольная сетка из квадратных ячеек. 
Символ 'X' представляет собой уже покрытую ячейку, 
символ '.' - это ячейка, которую еще нужно покрыть.

Вы хотите покрыть все ячейки '.', непересекающимися доминошками 2×1. 
Найдите количество способов сделать это. Два способа считаются различными, 
если две ячейки покрыты одним и тем же домино в одном замощении и 
двумя разными домино в другом замощении.
*/
