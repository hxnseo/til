public class StackWithArray {
        private final int[] data;
        private final int size;
        private int top;

    public StackWithArray(int s) {
        data = new int[s];
        size = s;
        top = -1;
    }

    public boolean isEmpty() { return top == -1; }
    public boolean isFull() { return top == (size - 1); }

    public void push(int x) {
        if (!isFull()) data[++top] = x;
    }

    public int pop() {
        if (isEmpty()) return -1; else return data[top--];
    }
}