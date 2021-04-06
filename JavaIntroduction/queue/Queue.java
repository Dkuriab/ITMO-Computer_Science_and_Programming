package queue;

public interface Queue {
    void enqueue(Object x);
    Object element();
    Object dequeue();
    int size();
    boolean isEmpty();
    void clear();
}
