import java.io.*;
import java.util.*;
  
public class J {

    public static void main(String[] args) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("jurassic.in")));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("jurassic.out")));
  
            int n = Integer.parseInt(in.readLine().split(" ")[0]);
            // String[] bonse = new String[n];
            int[] bonse = new int[n];

            for (int i = 0; i < n; i++) {
                String cur = in.readLine();
                for (int j = 0; j < cur.length(); j++) {
                    bonse[i] |= (1 << ((int)cur.charAt(j) - 65));
                }
            }

            int max = (1 << n) - 1;
            int ans = 0;

            for (int i = max; i >= 0; i--) {
                int check = 0;
                for (int j = 0; j < n; j++) {
                    if ((i & (1 << j)) > 0) {
                        check ^= bonse[j];
                    }    
                }
                if (check == 0) {
                    ans = i;
                    break;
                }
            }

            String answer = "";
            int count = 0;
            for (int i = 0; i < n; i++) {
                if ((ans & (1 << i)) > 0) {
                    count++;
                    answer += (i + 1) + " ";
                }
            }

            out.write(count + "\n");
            out.write(answer);

            out.close();
            in.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}