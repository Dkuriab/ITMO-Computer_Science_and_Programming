import java.util.*;
import java.io.*;

public class num2perm {
    public static BufferedReader in;
    public static BufferedWriter out;


    public static void main(String[] args) {
        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream("num2perm.in"), "UTF-8"));
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("num2perm.out"), "UTF-8"));
            
            Scanner scan = new Scanner (in.readLine());
            int n = scan.nextInt();
            long k = scan.nextLong();
            Set<Integer> waser = new HashSet<>();
            int[] numToPerm = new int[n + 1];
            
            long counter = 0;

            long fac = 1;
            for (int y = 2; y <= (n - 1); y++) {
                fac *= y;
            }


            for (int i = 1; i <= n; i++) {
                counter = k / fac;      
                k %= fac;
                int last = 0;
                for (int j = 1; j <= n; j++) {  
                    if (!waser.contains(j)) {
                        last++;
                        if (last == counter + 1) {
                            out.write(j + " ");
                            waser.add(j);
                        }
                    }
                }

                if (n - i != 0) {
                    fac /= (n - i);
                } else {
                    fac = 1;
                }
            }
            out.flush();
        }catch (IOException e) {
                System.out.println(e);
        }     
    }
}