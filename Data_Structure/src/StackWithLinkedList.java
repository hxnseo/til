public class StackWithLinkedList {
    private final LinkedList llist;

    public StackWithLinkedList() {
        llist = new LinkedList();
    }

    public void push(int x) {
        llist.insertAtFront(x);
    }

    public int pop() {
        return llist.deleteFirst();
    }

    public boolean isEmpty() {
        return llist.isEmpty();
    }
}
