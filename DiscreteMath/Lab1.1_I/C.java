import java.util.*;

public class C {

	public static int power;
	public static int[] funk;

    public static boolean mon() {
         for (int i = 0; i < power; i++) {
            for (int t = i; t < power; t++) {
                if ((i & t) == i && !(funk[i] <= funk[t])) {
                    return false; 
                }
            }
        }
        return true;
    }

    public static boolean sd() {
        boolean sder = true;

        if (power == 1) {
            sder = false;
        }
        else {
            for (int i = 0; i < power / 2; i++) {
                if (funk[i] == funk[power-1 - i]) {                
                    sder = false;
                }
            }
        }
        return sder;
    }

    public static boolean lin() { 
        int[] polynom = new int[power];
        int[] tmp = new int[power];
    

        polynom[0] = funk[0];
        boolean flag = true;

        for (int i = 1; i < power; i++) {
            if (flag) {
                for (int t = 0; t < power - i; t++) {
                    tmp[t] = (funk[t] ^ funk[t + 1]);
                }
                polynom[i] = tmp[0];
                flag = false;
                }
            else {
                for (int t = 0; t < power - i; t++) {
                    funk[t] = (tmp[t] ^ tmp[t + 1]);
                }
                polynom[i] = funk[0];
                flag = true;
            }
        }

        boolean poster = true;

        for (int i = 1; i < power; i++) {
            if (i!=1 && i!=2 && i!=4 && i!=8 && i!=16 && i!=32 && polynom[i] == 1) {
                poster = false;
            }
        }

        return poster;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int[] arg = new int[n];
        int[] post = new int[5];

        for (int i = 0; i < n; i++) {
            arg[i] = scan.nextInt();
            power = (int) Math.pow(2, arg[i]);            
            funk = new int[power];
            String tmp = scan.next();
            Scanner digit = new Scanner(tmp).useDelimiter("");

            for (int s = 0; s < power;  s++) {
                funk[s] = digit.nextInt();
            }

            if (funk[0] == 0) {
                post[0] += 1;
            }
            if (funk[power - 1] == 1) {
                post[1] += 1;
            } 
            if (sd()) {
                post[2] += 1;
            }
            if (mon()) {
                post[3] += 1;
            } 
            if (lin()) {
                post[4] += 1;
            }
        }

        if (post[0]==n || post[1]==n || post[2]==n || post[3]==n || post[4]==n) {
            System.out.println("NO");
        }
        else {
            System.out.println("YES");
        }  
    }
}