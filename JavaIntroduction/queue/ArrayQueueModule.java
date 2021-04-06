package queue;

import java.util.Arrays;

public class ArrayQueueModule {
    private static Object[] queue = new Object[5];
    private static int head = 0;
    private static int tail = 0;
//INV:
    // (queue is empty && head == tail) OR
    // head - number of first in element
    // tail - number of last in element + 1

    // x != null
    public static void enqueue(Object x) {
        ensureCapacity(size() + 1);
        queue[tail] = x;
        tail = (tail + 1) % queue.length;
    }
    // last in == x

    // x != null
    public static void push(Object x) {
        assert x != null;

        ensureCapacity(size() + 1);
        head = (head - 1 + queue.length) % queue.length;
        queue[head] = x;
    }
    // nearest to the exit = x && the rest of the queue is not changed

    // queue isn't empty
    public static Object peek() {
        assert size() > 0;

        return queue[(tail - 1 + queue.length) % queue.length];
    }
    // R = the last in element

    // queue isn't empty
    public static Object remove() {
        assert size() > 0;
        tail = (tail - 1 + queue.length) % queue.length;
        Object x = queue[tail];
        queue[tail] = null;
        return x;
    }
    // R = the last in element
    // last in element removed from queue


    private static void ensureCapacity(int capacity) {
        if (capacity >= queue.length) {
            queue = Arrays.copyOf(toArray(), queue.length * 2);
            tail = queue.length / 2 - 1;
            head = 0;
        }
    }
    // queue.length > number of elements && INV

    public static String toStr() {
        return Arrays.toString(toArray());
    }
    // R = String in format [head, ... , tail]

    public static Object[] toArray() {
        Object[] n = new Object[size()];

        if (head > tail) {
            System.arraycopy(queue, head, n, 0, queue.length - head);
            System.arraycopy(queue, 0, n, queue.length - head, tail);
        } else {
            System.arraycopy(queue, head, n, 0, size());
        }
        return n;
    }
    // R = array {head, .. , tail}

    // queue isn't empty
    public static Object element() {
        assert size() > 0;

	    return queue[head];
    }
    // R = queue[head] (first element to out)

    // queue isn't empty
    public static Object dequeue() {
	    assert size() > 0;

	    Object x = queue[head];
	    queue[head] = null;
        head = (head + 1) % queue.length;
        return x;
    }
    // R = queue[head] (first element to out)
    // The nearest to the exit removed from queue


    public static int size() {
        return (tail - head + queue.length) % queue.length;
    }
    // R = number of elements in queue

    public static boolean isEmpty() {
        return head == tail;
    }
    // R = false if there are >= 1 elements in queue
    // R = true if there are no elements in queue


    public static void clear() {
        queue = new Object[5];
        head = 0;
        tail = 0;
    }
    //queue is empty, size == 0, head == 0, tail == 0, length == 5;
}