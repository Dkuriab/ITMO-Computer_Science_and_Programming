import java.util.*;

public class H {
    public static long min(long a, long b) {
	    if (a < b) {
	    	return a;
	    }
		else {
			return b;
		}
	}
	public static long max(long a, long b) {
		if (a >= b) {
			return a;
		}
		else {
			return b;
		}
	}

	public static boolean check (long k, long n, long w, long h) {
		return (Math.log(n) <= Math.log(k/w) + Math.log(k/h));
	}
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		long w = scan.nextLong();
		long h = scan.nextLong();
		long n = scan.nextLong();
		long l = 0, r = w*n + h*n;
		while(r - l > 1) {
			long m = (l + r) / 2;
			if(check(m, n, w, h))
				r = m;
			else
				l = m;
		}
		System.out.println(r);
	}
}