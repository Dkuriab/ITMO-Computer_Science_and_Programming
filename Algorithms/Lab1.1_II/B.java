import java.util.*;
 
public class B {
    public static class list {
        private int val;
        private list next;
        private list prev;
 
        public list(int x, list next, list prev) {
            this.val = x;
            this.next = next;
            this.prev = prev;
        }
    }
 
    public static void main(String[] args) {
 
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int result = 0;
 
        list start = new list(-1, null, null);
        list tmp = start;
 
        for (int i = 0; i < n; i++) {
            list tmp1 = new list(scan.nextInt(), null, null);
            tmp.next = tmp1;
            tmp1.prev = tmp;
            tmp = tmp1; 
        }
 
        list finish = new list(-1, null, tmp);
        tmp.next = finish;
        list allstar = start;
        
        for (int i = 0; i < n / 3; i++) {
            int res = result;

            list curent = allstar.next;
            list first = allstar;
            int counter = 1;
 
            while (curent.next != null) {   
                if (curent.val == curent.next.val) {
                    counter++;
                } else {
                    if (counter < 3) {
                        counter = 1;
                        first = curent;
                    } else {
                        result += counter;
                        first.next = curent.next;
                        curent.next.prev = first;

                        list temp = first;
                        int k = 0;
                        while (temp.val != -1 && k < 2) {
                            temp = temp.prev;
                            k++;
                        }
                        allstar = temp;
                        break;
                    }
                }
                curent = curent.next;
            }
            if (res == result) {
                break;
            }
        }
        System.out.println(result);
    }
}