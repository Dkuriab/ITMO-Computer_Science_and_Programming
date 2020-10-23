import java.util.*;



public class A {

    public static int[][] stek = new int[1_000_001];
    public static int val = 0;

    public static void push(int x) {
        if (val != 0) {
            stek[val] = min(x, stek[val - 1]);
        } else {
            stek[val] = x;
        }
        val++;    
    }

    public static void pop() {
        val--;
    }

    public static int mins() {
        return stek[val - 1];
    }

    public static int min(int a, int b) {
        if (a < b) {
            return a;
        }
        else {
            return b;
        }
    } 

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int n = scan.nextInt();

        for (int i = 0; i < n; i++) {
            int t = scan.nextInt();
            if (t == 1) {
                push(scan.nextInt());
            }
            if (t == 2) {
                pop();
            }
            if (t == 3) {
                System.out.println(mins());
            }
        }
    
    }   
}