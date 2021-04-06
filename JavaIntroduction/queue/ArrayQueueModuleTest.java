package queue;

import java.util.Random;

public class ArrayQueueModuleTest {
    public static ArrayQueueModule q;
    public static ArrayQueueModule q2;
    public static Random random = new Random();

    public static void fill(ArrayQueueModule q) {
        for (int i = 0; i < 100; i++) {
            q.enqueue(random.nextInt(100000));
        }
    }

    public static void randomCommand() {
        int x = random.nextInt();
        switch (random.nextInt(9)) {
            case 0:
                System.out.println("Enqueue: " + x);
                q.enqueue(x);
                break;
            case 1:
                System.out.println("Try to Dequeue");
                if (!q.isEmpty()) {
                    q.dequeue();
                }
                break;
            case 2:
                System.out.print("Try to Element: ");
                if (!q.isEmpty()) {
                    System.out.print(q.element());
                }
                break;
            case 3:
                System.out.println("IsEmpty: " + q.isEmpty());
                break;
            case 4:
                System.out.println("Size: " + q.size());
                break;
            case 5:
                System.out.println("Clear");
                q.clear();
                break;
            case 6:
                    System.out.println("Try to Remove");
                if (!q.isEmpty()) {
                    q.remove();
                }
                break;
            case 7:
                if (!q.isEmpty()) {
                    System.out.println("Peek: " + q.peek());
                }
                break;
            case 8:
                System.out.println("Push: " + x);
                q.push(x);

        }
        System.out.println("Queue: " + q.toStr());
    }

    public static void main(String[] args) {
        q = new ArrayQueueModule();
        q2 = new ArrayQueueModule();
        fill(q);
        fill(q2);

        for (int i = 0; i < 1000; i++) {
            randomCommand();
        }
    }
}
