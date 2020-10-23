import java.util.*;

public class F { 

    public static int lowerbound(int[] a,int length, int x) {
    	int l = -1;
    	int r = length;
    	while ((r - l) > 1) {
    		int m = (l + r) / 2;
    		if (a[m] < x) {
    			l = m;
    		}
    		else {
    			r = m;
    		}
    	}

    	return r;
    }
    public static int min(int a, int b) {
        if (a < b) {
            return a;
        }
        else {
            return b;
        }
    }
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int k = scan.nextInt();
        int[] arr = new int[n];
        int[] zap = new int[k];

        for (int i = 0; i < n; i++) {
            arr[i] = scan.nextInt();
        }
        for (int i = 0; i < k; i++) {
            zap[i] = scan.nextInt();
        }

        for (int trial: zap) {
            int i = (lowerbound(arr, n, trial));

            if (i == 0) {
                System.out.println(arr[i]);
            }else if (i > (n - 1) || ((trial - arr[i - 1]) <= (arr[i] - trial))) {
                System.out.println(arr[i - 1]);
            }
            else {
                System.out.println(arr[i]);

            }
        }
    }
}
