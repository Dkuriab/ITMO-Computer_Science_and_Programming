import java.util.*;

public class J {

	public static boolean check(double m1, double m2, double a, int v1, int v2) {
		return (Math.sqrt((1 - a) * (1 - a) + m1 * m1) / v1 + Math.sqrt(a * a + (1 - m1) * (1 - m1)) / v2  < 
				Math.sqrt((1 - a) * (1 - a) + m2 * m2) / v1 + Math.sqrt(a * a + (1 - m2) * (1 - m2)) / v2);
	}
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int v1 = scan.nextInt();
		int v2 = scan.nextInt();
		double a = scan.nextDouble();
		double l = 0;
		double r = 1.1;

		for (int i = 0; i < 1000; i++) {
			double m1 = (2 * l + r) / 3;
			double m2 = (2 * r + l) / 3;

			if (check(m1, m2, a, v1, v2)) {
				r = m2;
			}
			else {
				l = m1;
			}
		}
		System.out.print((r + l) / 2);
	}
}