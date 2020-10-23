import java.util.*;
 
public class D {
    public static class list {
        private int val;
        private list next;
        private list prev;
 
        public list(list prev, int x, list next) {
            this.prev = prev;
            this.val = x;
            this.next = next;
        }
    }
 
    public static void main(String[] args) {
 
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();

        list head = new list(null, -1, null);
        list tail = new list(head, -1, null);
        head.next = tail;
        list mid = head;

        boolean flag = true;

        for (int i = 0; i < n; i++) {
            char k = scan.next().charAt(0);
            
            if (k == '+') {
                int t = scan.nextInt();
                list goblin = new list(tail.prev, t, tail);
                tail.prev.next = goblin;
                tail.prev = goblin;
                if (flag) {
                    mid = mid.next;
                    flag = false;
                } else {
                    flag = true;
                }
            }
            if (k == '-') {
                System.out.println(head.next.val);
                
                if (head.next.next.val == -1) {
                    tail.prev = head;
                    head.next = tail;
                    mid = head;
                    flag = true;
                } else {
                    head.next.next.prev = head;
                    head.next = head.next.next;

                    if (flag) {
                        mid = mid.next;
                        flag = false;
                    } else {
                        flag = true;
                    }
                } 
            }
            if (k == '*') {
                int t = scan.nextInt();
                list goblin = new list(mid, t, mid.next);
                
                mid.next.prev = goblin;
                mid.next = goblin;  
                
                if (flag) {
                    mid = mid.next;
                    flag = false;
                } else {
                    flag = true;
                }
            }
        }
    }
}   