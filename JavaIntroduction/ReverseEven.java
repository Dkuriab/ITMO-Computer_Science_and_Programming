import java.util.Arrays;

public class ReverseEven {
	
	public static void main(String[] args) {
		int i = 0;
		int[][] ints = new int[1_000_000][];
		int[] intt = new int[1_000_000];
		
		Scanner sc = new Scanner(System.in);
		while (sc.hasNextLine()) {
			int j = 0;
			Scanner dc = new Scanner(sc.nextLine());
				while (dc.hasNextInt()) {
					int s = dc.nextInt();
					if (s % 2 ==0) {
						intt[j++] = s;

					}						
				}
			ints[i] = new int[j];
			System.arraycopy(intt, 0, ints[i], 0, j);					
			i++;
		}
		
		for (int s = i - 1; s >= 0; s--) {
			for (int k = ints[s].length - 1; k >= 0; k--) {
				System.out.print(ints[s][k] + " ");				
			}
    		System.out.println();	
		}	
	}
}