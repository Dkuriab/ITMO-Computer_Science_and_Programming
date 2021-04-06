package queue;

import java.util.Arrays;

public class ArrayQueueADT {
    private  Object[] queue = new Object[5];
    private int head = 0;
    private int tail = 0;
//INV:
    // (queue is empty && head == tail) OR
    // head - number of first in element
    // tail - number of last in element + 1

    // x != null
    public static void enqueue(ArrayQueueADT queueATD, Object x) {
        assert  x != null;

        ensureCapacity(queueATD, queueATD.size(queueATD) + 1);
        queueATD.queue[queueATD.tail] = x;
        queueATD.tail = (queueATD.tail + 1) % queueATD.queue.length;
    }
    // last in == x

    // x != null
    public static void push(ArrayQueueADT queueATD, Object x) {
        assert x != null;

        ensureCapacity(queueATD,queueATD.size(queueATD) + 1);
        queueATD.head = (queueATD.head - 1 + queueATD.queue.length) % queueATD.queue.length;
        queueATD.queue[queueATD.head] = x;
    }
    // nearest to the exit = x && the rest of the queue is not changed

    // queue isn't empty
    public static Object peek(ArrayQueueADT queueATD) {
        assert queueATD.size(queueATD) > 0;

        return queueATD.queue[(queueATD.tail - 1 + queueATD.queue.length) % queueATD.queue.length];
    }
    // R = the last in element

    // queue isn't empty
    public static Object remove(ArrayQueueADT queueATD) {
        assert queueATD.size(queueATD) > 0;

        queueATD.tail = (queueATD.tail - 1 + queueATD.queue.length) % queueATD.queue.length;
        Object x = queueATD.queue[queueATD.tail];
        queueATD.queue[queueATD.tail] = null;
        return x;
    }
    // R = the last in element
    // last in element removed from queue

    private static void ensureCapacity(ArrayQueueADT queueATD, int capacity) {
        if (capacity >= queueATD.queue.length) {
            queueATD.queue = Arrays.copyOf(queueATD.toArray(queueATD), queueATD.queue.length * 2);
            queueATD.tail = queueATD.queue.length / 2 - 1;
            queueATD.head = 0;
        }
    }
    // queue.length > number of elements

    public static String toStr(ArrayQueueADT queueATD) {
        return Arrays.toString(queueATD.toArray(queueATD));
    }
    // R = String in format [head, ... , tail]


    public Object[] toArray(ArrayQueueADT queueATD) {
        Object[] n = new Object[queueATD.size(queueATD)];

        if (queueATD.head > queueATD.tail) {
            System.arraycopy(queueATD.queue, queueATD.head, n, 0, queueATD.queue.length - queueATD.head);
            System.arraycopy(queueATD.queue, 0, n, queueATD.queue.length - queueATD.head, queueATD.tail);
        } else {
            System.arraycopy(queueATD.queue, queueATD.head, n, 0, queueATD.size(queueATD));
        }
        return n;
    }
    // R = array {head, .. , tail}

    // queue isn't empty
    public static Object element(ArrayQueueADT queueATD) {
        assert queueATD.size(queueATD) > 0;

	    return queueATD.queue[queueATD.head];
    }
    // R = queue[head] (first element to out)

    // queue isn't empty
    public static Object dequeue(ArrayQueueADT queueATD) {
	    assert queueATD.size(queueATD) > 0;

	    Object x = queueATD.queue[queueATD.head];
	    queueATD.queue[queueATD.head] = null;
        queueATD.head = (queueATD.head + 1) % queueATD.queue.length;
        return x;
    }
    // R = queue[head] (first element to out)
    // Number of elements --

    public static int size(ArrayQueueADT queueATD) {
        return (queueATD.tail - queueATD.head + queueATD.queue.length) % queueATD.queue.length;
    }
    // R = number of elements in queue

    public static boolean isEmpty(ArrayQueueADT queueATD) {
        return queueATD.head == queueATD.tail;
    }
    // R = false if it contains minimum 1 element in queue
    // R = true if there are no elements in queue

    public static void clear(ArrayQueueADT queueATD) {
        queueATD.queue = new Object[5];
        queueATD.head = 0;
        queueATD.tail = 0;
    }
    //queue is empty, size == 0, head == 0, tail == 0, length == 5;
}