import java.util.*;
import java.io.*;
 
public class F {
	public static BufferedWriter go = new BufferedWriter(new OutputStreamWriter(System.out));
	public static int[] a;
	public static int as = 0;
	public static int[] b;
	public static int bs = 0;
	public static String[] answer;
	public static int ans = 0;

	public static void push(int x){
		while (as > 0 && a[as - 1] < x) {
			pop();
		}
		answer[ans++] = "push";
		a[as++] = x;
	}

	public static void pop(){
		answer[ans++] = "pop";
		b[bs++] = a[--as];
	}

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		a = new int[n + 1];
		b = new int[n + 1];
		answer = new String[2*n];

		for(int i = 0; i < n; i++) {
			push(scan.nextInt());
		}

		while (as > 0) {
			pop();
		}

		boolean flag = true;
		for (int i = 0; i < bs - 1; i++) {
			if (b[i] > b[i+1]) {
				flag = false;
			}
		}
		if (flag) {
			for (int i = 0; i < ans; i++) {
				try{
					go.write(answer[i]);
					go.newLine();
					go.flush();
				}catch (IOException e) {
					System.out.println(e);
				}
			}
		} else {
			try {
				go.write("impossible");
				go.newLine();
				go.flush();
			}catch (IOException e) {
				System.out.println(e);
			}
		}
	}


}