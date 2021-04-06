import java.util.Arrays;
import java.util.NoSuchElementException;
import java.io.*;

public class FastReverse {
	
	public static void main(String[] args) {
		int i = 0;
		int[][] ints = new int[1_000_000][];
		int[] intt = new int[1_000_000];
		try { 
		    Scanner sc = new Scanner(System.in);
				try {
					while (sc.nextLine() != null) {
						int j = 0;
							while (sc.hasNextInt()) {
								int s = sc.nextInt();
								intt[j++] = s;						
							}
						ints[i] = new int[j];
						System.arraycopy(intt, 0, ints[i], 0, j);					
						i++;
					}
				} finally {
					sc.close();
				}
		} catch (IOException e) {
			System.out.println("IOException" + e.getMessage());
		} catch (NullPointerException e) {
            System.out.println("NullPointerException" + e.getMessage());
        } catch (NoSuchElementException  e) {
            System.out.println("NoSuchElementException " + e.getMessage());
        } 	
		
		for (int s = i - 1; s >= 0; s--) {
			for (int k = ints[s].length - 1; k >= 0; k--) {
				System.out.print(ints[s][k] + " ");	
        try {
			BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("outist.txt"), "utf8"));

					wr.write(ints[s][k] + " ");
					wr.newLine();	

		} catch (IOException e) {
			System.out.println("IOException" + e.getMessage());
		}                 
			}
    		System.out.println();	
		}	
	}
}