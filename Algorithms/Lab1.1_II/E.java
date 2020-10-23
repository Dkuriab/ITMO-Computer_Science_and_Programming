import java.util.*;
 
public class E {

    public static int[] stek = new int[100];
    public static int cur = 0;

    public static void push(int x) {
        stek[cur++] = x;
    }

    public static int pop() {
        cur--;
        return stek[cur];

    }
 
    public static void main(String[] args) {
 
        Scanner scan = new Scanner(System.in);

        while (scan.hasNext()) {
            String s = scan.next();
            char k = s.charAt(0);
            if (k == '+') {
                int a = pop();
                int b = pop();
                push(a + b);
            } else if (k == '-') {
                int a = pop();
                int b = pop();
                push(b - a);
            } else if (k == '*') {
                int a = pop();
                int b = pop();
                push(a * b);
            } else {
                push(Integer.parseInt(s));
            }
        }

        System.out.println(pop());
    }
}