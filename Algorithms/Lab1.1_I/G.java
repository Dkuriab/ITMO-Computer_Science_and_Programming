import java.util.*;

public class G {
    public static int min(int a, int b) {
	    if (a < b) {
	    	return a;
	    }
	    return b;
    }
	public static int max(int a, int b) {
		if (a >= b) {
			return a;
		}
		else {
			return b;
		}
	}
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		int x = scan.nextInt();
		int y = scan.nextInt();
		int min = n * x;
		int m = min(x, y);
		int r = x + y - m;
		for (int k = 0; k < n; k++) {
			if (max((k * m), ((n - 1 - k) * r)) < min) {
				min = max((k * m), ((n - 1 - k) * r));
			}
		}
		System.out.print(m + min);
	}
}