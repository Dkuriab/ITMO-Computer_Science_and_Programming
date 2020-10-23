import java.io.*;
import java.util.*;
  
public class N {
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
        Pair prev = null;
        Pair next = null;
 
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
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("linkedmap.in")));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("linkedmap.out")));
  
            ArrayList<ArrayList<Pair>> hashSet = new ArrayList<>();
             
            for (int i = 0; i < 100000; i++) {
                hashSet.add(new ArrayList<Pair>());
            }
            Pair last = null;

            String line = null;
            while ((line = in.readLine()) != null) {
                String[] data = line.split(" ");
                int hashValue = getHash(data[1]);
  
                switch(data[0]) {
                    case "put":
                        Pair nv = new Pair(data[1], data[2]);
                        boolean isExists = false;

                        for (Pair k : hashSet.get(hashValue)) {
                            if (k.key.equals(data[1])) {
                                // ans = k.value;
                                k.value = data[2];
                                isExists = true;
                                break;
                            }
                        }
 
                        if (!isExists) {
                            if (last != null) {
                                last.next = nv;
                            }
                            nv.prev = last;
                            last = nv;
                            hashSet.get(hashValue).add(nv);
                        }

                        break;
                    case "delete":
                        for (Pair k : hashSet.get(hashValue)) {
                            if (k.key.equals(data[1])) {
                                if (k.prev != null) {
                                    k.prev.next = k.next;
                                }
                                if (k.next != null) {
                                    k.next.prev = k.prev;
                                }
                                if (last.key.equals(data[1])) {
                                    last = k.prev;
                                }
                                // k.value = data[2];
                                // isExists = true;
                                break;
                            }
                        }
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
                    case "next":

                        String ansN = "none";
                        for (Pair k : hashSet.get(hashValue)) {
                            if (k.key.equals(data[1])) {
                                if (k.next != null) {
                                    ansN = k.next.value;
                                }
                                break;
                            }
                        }
                        out.write(ansN + "\n");
                        break;

                    case "prev":
                        String ansP = "none";
                        for (Pair k : hashSet.get(hashValue)) {
                            if (k.key.equals(data[1])) {
                                if (k.prev != null) {
                                    ansP = k.prev.value;
                                }
                                break;
                            }
                        }
                        out.write(ansP + "\n");
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