import java.io.*;
import java.util.*;
  
public class M {
    public static int[] primary = new int[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71};
     
    public static int getHash(String x) {
        long ans = 1;
        for (int i = 0; i < x.length(); i++) {
            ans += x.charAt(i) * primary[i % 20];
        } 
        return (int)(ans % 50000 + 50000);
    }
 
    public static class Pair {
        String key;
        String value;
 
        public Pair(String key, String value) {
            this.key = key;
            this.value = value;
        }
 
        @Override
        public boolean equals(Object bo) {
            Pair b = (Pair)bo;
            return key.equals(b.key);
        }

        public boolean equals(Pair a, Pair b) {
            return a.key.equals(b.key);
        }
 
        public String toString() {
            return key + " -> " + value;
        }
    }
  
    public static void main(String[] args) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("map.in")));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("map.out")));
  
            ArrayList<ArrayList<Pair>> hashSet = new ArrayList<>();
             
            for (int i = 0; i < 100000; i++) {
                hashSet.add(new ArrayList<Pair>());
            }
  
            String line = null;
            while ((line = in.readLine()) != null) {
                String[] data = line.split(" ");
                int hashValue = getHash(data[1]);
  
                switch(data[0]) {
                    case "put":
                        Pair nv = new Pair(data[1], data[2]);
                        boolean isExists = hashSet.get(hashValue).contains(nv);
 
                        if (isExists) {
                            hashSet.get(hashValue).remove(nv);
                        }
                        hashSet.get(hashValue).add(nv);

                        break;
                    case "delete":
                        hashSet.get(hashValue).remove(new Pair(data[1], ""));
                        break;
  
                    case "get":
                        String ans = "none";
                        for (Pair k : hashSet.get(hashValue)) {
                            if (k.key.equals(data[1])) {
                                ans = k.value;
                                isExists = true;
                                break;
                            }
                        }
                        out.write(ans + "\n");
                        break;
                }
            }

            out.close();
            in.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}