import java.util.*;

public class C {

	public static int[] queue;
	public static int[] id;
	public static int head = 0;
	public static int tail = 0;

	public static void push (int x) {
		queue[tail] = x;
		id[x] = tail;
		tail++;
		
	}
	public static void popback () {
		tail--;
	}
	public static void popfront () {
		head++;
	}	
	public static void ahead (int x) {
		System.out.println(id[x] - head);
	}
	public static void first () {
		System.out.println(queue[head]);
	}		

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		int n = scan.nextInt();

		queue = new int[1000001];
		id = new int[1000001];

		for (int i = 0; i < n; i++) {
			int t = scan.nextInt();
			if (t == 1) {
				push (scan.nextInt());
			}
			if (t == 2) {
				popfront();
			}
			if (t == 3) {
				popback();
			}
			if (t == 4) {
				ahead(scan.nextInt());
			}
			if (t == 5) {
				first();
			}
		}
	}
}
