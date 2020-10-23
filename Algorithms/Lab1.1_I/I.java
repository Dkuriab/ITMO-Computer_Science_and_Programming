import java.util.*;

public class I {

	public static boolean check(double m, double c) {
		return (m * m * m * m + m - c < 0);
	}
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		double c = scan.nextDouble();
		double l = 0;
		double r = 1024;
		for (int i = 0; i < 1000; i++) {
			double m = (l + r) / 2;
			if (check(m, c)) {
				l = m;
			}
			else {
				r = m;
			}
		}
		System.out.print(((l + r) / 2) * ((l + r) / 2));
	}
}