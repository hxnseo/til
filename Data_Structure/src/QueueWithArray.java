public class QueueWithArray {
    private final int[] data;
    private final int size;
    private int front, rear;

    public QueueWithArray(int s) {
        data = new int[s];
        size = s;
        front = 0;
        rear = -1;
    }

    public boolean isEmpty() { return (rear + 1) == front; }
    public boolean isFull() { return rear == (size -1); }

    public void enqueue(int x) { if (!isFull()) data[++rear] = x; }
    public int dequeue() {
        if (isEmpty()) return -1;
        else return data[front++];
    }
}
