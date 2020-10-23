import java.util.*;

public class B {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		int n = scan.nextInt(); // literals
		int k = scan.nextInt(); // dis

		int[][] horn = new int[k][n];
		int[] count = new int[k];
		int lonely = 0;
		boolean flag = false;

		for (int i = 0; i < k; i++) {
			for (int j = 0; j < n; j++) {
				horn[i][j] = scan.nextInt();
				if (horn[i][j] != -1) {
					count[i]++;
				}
			}
			if (count[i] == 1) {
				lonely++;
			}
		}

		while (lonely != 0) {
			
			int pos = 0;
			int link = 0;

			while (count[link] != 1) {
				link++;
			}
			while (horn[link][pos] == -1) {
				pos++;
			}

			count[link] = 0;
			lonely--;

			for (int i = 0; i < k; i++) {
				if (count[i] != 0 && horn[i][pos] != -1) {
					if (count[i] == 1) {
						if (horn[i][pos] == horn[link][pos]) {
							count[i] = 0;
							lonely--;
						}
						else {
							count[i] = 0;
							lonely--;
							flag = true;
						}
					}
					if (count[i] == 2) {
						if (horn[i][pos] != horn[link][pos]) {
							lonely++;
							horn[i][pos] = -1;
							count[i] = 1;
						}
						else {
							count[i] = 0;
						}
					}
					if (count[i] > 2) {
						if (horn[i][pos] == horn[link][pos]) {
							count[i] = 0;
						}
						else {
							horn[i][pos] = -1;
							count[i]--;
						}
					}
				}
			}
		}
		if (flag) {
			System.out.println("YES");
		}
		else {
			System.out.println("NO");
		}
	}
}