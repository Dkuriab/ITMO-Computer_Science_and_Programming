import java.io.*;
import java.util.*;
   
public class O {
    public static int[] primary = new int[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71};
      
    public static int getHash(String x) {
        long ans = 1;
        for (int i = 0; i < x.length(); i++) {
            ans += x.charAt(i) * primary[i % 20];
        } 
        return (int)(ans % 100000 + 100000);
    }
  
    public static class Pair {
        String key;
        // String value;
        ArrayList<String> values = new ArrayList<>();
  
        public Pair(String key) {
            this.key = key;
            // this.value = value;
        }
  
        @Override
        public boolean equals(Object bo) {
            Pair b = (Pair)bo;
            return key.equals(b.key);
        }
 
        public boolean equals(Pair a, Pair b) {
            return a.key.equals(b.key);
        }
    }
   
    public static void main(String[] args) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("multimap.in")));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("multimap.out")));
   
            ArrayList<ArrayList<Pair>> hashSet = new ArrayList<>();
              
            for (int i = 0; i < 200000; i++) {
                hashSet.add(new ArrayList<Pair>());
            }
   
            String line = null;
            while ((line = in.readLine()) != null) {
                String[] data = line.split(" ");
                int hashValue = getHash(data[1]);
   
                switch(data[0]) {
                    case "put":
 
                        boolean isExists = false;
                        for (Pair k : hashSet.get(hashValue)) {
                            if (k.key.equals(data[1])) {
                                if (!k.values.contains(data[2])) {
                                    k.values.add(data[2]);
                                }
                                isExists = true;
                                break;
                            }
                        }
  
                        if (!isExists) {
                            Pair nv = new Pair(data[1]);
                            nv.values.add(data[2]);
                            hashSet.get(hashValue).add(nv);
                        }
 
                        break;
                    case "deleteall":
                        hashSet.get(hashValue).remove(new Pair(data[1]));
                        break;
 
                    case "delete":
 
                        for (Pair k : hashSet.get(hashValue)) {
                            if (k.key.equals(data[1])) {
                                k.values.remove(data[2]);
                                break;
                            }
                        }
                        break;
   
                    case "get":
                        String ans = "0";
                        for (Pair k : hashSet.get(hashValue)) {
                            if (k.key.equals(data[1])) {
                                ans = k.values.size() + " ";
                                for (String f : k.values) {
                                    ans += f + " ";
                                }
                                break;
                            }
                        }
                        out.write(ans.toString() + "\n");
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