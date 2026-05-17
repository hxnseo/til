public class QueueWithLinkedList {
    private final LinkedList llist;

    public QueueWithLinkedList() {
        llist = new LinkedList();
    }

    public void enqueue(int x) {
        llist.insertAtEnd(x);
    }

    public int dequeue(){
        return llist.deleteFirst();
    }

    public boolean isEmpty() {
        return llist.isEmpty();
    }
}
