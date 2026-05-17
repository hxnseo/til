public class CircularQueues {
    private final int[] data;
    private final int size;
    private int front, rear;

    public CircularQueues(int s) {
        s++; // insert for avoiding vital mistake
        data = new int[s];
        size = s;
        front = 0;
        rear = -1 + s;
    }

    public void enqueue(int x) {
        if (!isFull()) {
            rear = (rear + 1) % size;
            data[rear] = x;
        }
    }

    public int dequeue() {
        int ret;

        if (isEmpty()) return -1;
        ret = data[front];
        front = (front + 1) % size;

        return ret;
    }

    public boolean isEmpty() {
        return ((rear+1) % size) == front;
    }

    public boolean isFull() {
        return ((rear+2) % size) == front;
    }
}