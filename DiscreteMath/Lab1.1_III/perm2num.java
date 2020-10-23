import java.util.*;
import java.io.*;

public class perm2num {
    public static BufferedReader in;
    public static BufferedWriter out;
    public static Set<Integer> used;
    public static int n;

    public static long fac (int a) {
        long done = 1;
        for (int y = 2; y <= a; y++) {
            done *= y;
        }
        return done;
    }
    public static int next (int min) {
        for (int s = min; s <= n; s++) {
            if (!used.contains(s)) {
                return s;
            }
        }
        System.out.println("Sorry");
        return 0;
    }
    


    public static void main(String[] args) {
        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream("perm2num.in"), "UTF-8"));
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("perm2num.out"), "UTF-8"));
            
            Scanner scan = new Scanner (in.readLine());
            n = scan.nextInt();
            int[] perm = new int[n];
            scan = new Scanner (in.readLine());
            used = new HashSet<>();
            
            for (int i = 0; i < n; i++) {
                perm[i] = scan.nextInt();
            }

            int min = 1;
            long num = 0;

            for (int i = 0; i < n - 1; i++) {
                if (perm[i] != min) {
                    used.add(perm[i]);

                    int k = 1; // how much is used before perm[i]//
                    for (int u = 1 ; u < perm[i]; u++) {
                        if (used.contains(u)) {
                            k++;
                        }
                    }

                    num += (perm[i] - k) * fac(n - i - 1);
                } else {
                    min = next(min);
                    used.add(min);
                }
            }
            
            out.write(num + "");            
            out.flush();
        }catch (IOException e) {
                System.out.println(e);
        }     
    }
}