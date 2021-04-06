package queue;

import java.util.Random;

public class ArrayQueueADTTest {
    public static Random random = new Random();

    public static void fill(ArrayQueueADT q) {
        for (int i = 0; i < 100; i++) {
            ArrayQueueADT.enqueue(q, random.nextInt(100000));
        }
    }

    public static void randomCommand(ArrayQueueADT q) {
        int x = random.nextInt();
        switch (random.nextInt(9)) {
            case 0:
                System.out.println("Enqueue: " + x);
                ArrayQueueADT.enqueue(q, x);
                break;
            case 1:
                System.out.println("Try to Dequeue");
                if (!ArrayQueueADT.isEmpty(q)) {
                    ArrayQueueADT.dequeue(q);
                }
                break;
            case 2:
                System.out.print("Try to Element: ");
                if (!ArrayQueueADT.isEmpty(q)) {
                    System.out.print(ArrayQueueADT.element(q));
                }
                break;
            case 3:
                System.out.println("IsEmpty: " + ArrayQueueADT.isEmpty(q));
                break;
            case 4:
                System.out.println("Size: " + ArrayQueueADT.size(q));
                break;
            case 5:
                System.out.println("Clear");
                ArrayQueueADT.clear(q);
                break;
            case 6:
                    System.out.println("Try to Remove");
                if (!ArrayQueueADT.isEmpty(q)) {
                    ArrayQueueADT.remove(q);
                }
                break;
            case 7:
                if (!ArrayQueueADT.isEmpty(q)) {
                    System.out.println("Peek: " + ArrayQueueADT.peek(q));
                }
                break;
            case 8:
                System.out.println("Push: " + x);
                ArrayQueueADT.push(q, x);

        }
        System.out.println("Queue: " + ArrayQueueADT.toStr(q));
    }

    public static void main(String[] args) {
        ArrayQueueADT q = new ArrayQueueADT();
        fill(q);

        for (int i = 0; i < 1000; i++) {
            randomCommand(q);
        }
    }
}

