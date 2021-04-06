package queue;

public class LinkedQueue extends AbstractQueue {
    private int size = 0;
    private Node tail = null;
    private Node head = null;

    public void push(Object element) {

    }

    public Object pop() {
        return null;
    }

    public Object peek() {
        return null;
    }

    public void enqueue(Object x) {
        assert x != null;
        if (size() == 0) {
            head = new Node(x, null);
            tail = head;
        } else {
            Node tmp = tail;
            tail = new Node(x, null);
            tmp.next = tail;
        }
        size++;
    }

    public Object element() {
        assert size() > 0;

        return head.value;
    }

    public Object dequeue() {
        assert size() > 0;

        Object x = head.value;
        head = head.next;
        size--;
        return x;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        size = 0;
        head = new Node(0, tail);
        tail = null;
    }

    private class Node {

        private Object value;
        private Node next;

        public Node(Object value, Node next) {
            assert value != null;

            this.value = value;
            this.next = next;
        }
    }
}
