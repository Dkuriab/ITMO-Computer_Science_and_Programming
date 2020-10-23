import java.io.*;
import java.util.*;
 
public class L {
 
    public static int getHash(int x) {
        x ^= (x << 13);
        x ^= (x >> 17);    
        x ^= (x << 5); 
        return x % 10000 + 10000;
    }
 
    public static void main(String[] args) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("set.in")));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("set.out")));
 
            ArrayList<ArrayList<Integer>> hashSet = new ArrayList<>();
            
            for (int i = 0; i < 20000; i++) {
                hashSet.add(new ArrayList<Integer>());
            }
 
            String line = null;
            while ((line = in.readLine()) != null) {
                String[] data = line.split(" ");
                int value = Integer.parseInt(data[1]);
                int hashValue = getHash(value);
 
                switch(data[0]) {
                    case "insert":
                        boolean isExists = false;
                        for (int k : hashSet.get(hashValue)) {
                            if (k == value) {
                                isExists = true;
                                break;
                            }
                        }
                        if (!isExists) {
                            hashSet.get(hashValue).add(value);
                        }
                        break;
                    case "delete":
                        hashSet.get(hashValue).remove((Integer)value);
                        break;
 
                    case "exists":
                        out.write(hashSet.get(hashValue).contains(value) + "\n");
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