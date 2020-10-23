import java.io.*;
import java.util.*;
  
public class K {

    public static class Pair {
        long w;
        long v;

        public Pair(long w, long v) {
            this.w = w;
            this.v = v;
        }
    }

    public static void main(String[] args) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("dowry.in")));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("dowry.out")));
    
            String[] data = in.readLine().split(" ");
            long n = Long.parseLong(data[0]);
            long l = Long.parseLong(data[1]);
            long r = Long.parseLong(data[2]);

            Pair[] jew = new Pair[(int)n];

            for (int i = 0; i < n; i++) {
                String[] info = in.readLine().split(" ");
                jew[i] = new Pair(Long.parseLong(info[0]), Long.parseLong(info[1]));
            }

            long max = (1L << n) - 1;

            System.out.println("max : " + max);
            long maxV = 0;
            long ans = 0;


            for (long i = max; i >= 0; i--) {
                if (i % 1000000000 == 0) {
                    System.out.println(i);
                }
                boolean isGood = true;
                long weight = 0;
                long goodness = 0;

                for (int j = 0; j < n; j++) {
                    if ((i & (1L << j)) > 0) {
                        weight += jew[j].w;
                        goodness += jew[j].v;
                        // long curW = jew[j].w;
                        // if (r - weight >= curW) {
                        //     weight += curW;
                        // } else {
                        //     isGood = false;
                        // }
                    }    
                }

                // System.out.println("weight: " + weight + " goodness: " + goodness);

                if (weight >= l && weight <= r && goodness >= maxV) {
                    ans = i;
                    maxV = goodness;
                    // System.out.println("best goodness: " + maxV);
                }
            }

            String answer = "";
            int count = 0;
            for (int i = 0; i < n; i++) {
                if ((ans & (1L << i)) > 0) {
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