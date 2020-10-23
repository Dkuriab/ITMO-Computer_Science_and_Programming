import java.util.*;

public class B {
	public static void main(String[] args) {
        Scanner scan = new Scanner (System.in);
        int[] count = new int[101];
        while (scan.hasNextInt()) {
        	count[scan.nextInt()]++;
        }
        for (int i = 0; i < 101; i++) {
        	for (int k = 0; k < count[i]; k++) {
        		System.out.print(i + " ");
        	}
        }	
	}
}
