import java.util.Arrays;
import java.io.IOException;

public class ReverseTranspose {
    
    public static void main(String[] args) {
        int d = 0; // depth
        int maxlen = 0;
        int[][] ints = new int[1_000_000][];
        int[] intt = new int[1_000_000];
        try {
            Scanner sc = new Scanner(System.in);
            try {
                while (sc.nextLine() != null) {
                    int j = 0;
                    while (sc.hasNextInt()) {
                        intt[j++] = sc.nextInt();                      
                    }

                    ints[d] = new int[j];
                    if (j > maxlen) {
                        maxlen = j;
                    }
                    ints[d++] = Arrays.copyOf(intt, j);
                }
            } finally {
                sc.close();
            }
        } catch (IOException e) {
            System.out.println("Input error: " + e.getMessage());
            return;
        }   

        int[] length = new int[maxlen + 1];
        int[][] trans = new int[maxlen][];
        
        for (int o = 0; o < d; o++) {
            length[ints[o].length]++;
        }
        for (int o = maxlen; o > 0; o--) {
            length[o - 1] += length[o];
        }
        for (int o = 0; o < maxlen; o++) {
            trans[o] = new int[length[o + 1]];
            length[o] = 0;
        }
        for (int j = 0; j < d; j++) {
            for (int i = 0; i < ints[j].length; i++) {
                trans[i][length[i]] = ints[j][i];
                length[i]++;                    
            }   
        }
        
        for (int j = 0; j < maxlen; j++) {
            for (int i = 0; i < trans[j].length; i++) {
                System.out.print(trans[j][i] + " ");
            }
            System.out.println();            
        }
    }
}