public class LinkedList {
    // Node 타입의 필드
    // 리스트 전체에 대한 정보이기 때문에 LinkedList class가 보유
    public Node first, last;

    // constructor
    public LinkedList() {
        first = last = null;
    }

    // methods
    public boolean isEmpty() {
        return (first == null);
    }

    public void insertAtEnd(int x) {
        Node newnode;

        newnode = new Node();
        newnode.data = x;
        newnode.next = null;
        if (last == null) {
            first = last = newnode;
        } else {
            last.next = newnode;
            last = newnode;
        }
    }

    public void insertAtFront(int x) {
        Node newnode;

        newnode = new Node();
        newnode.data = x;
        newnode.next = first;
        first = newnode;

        if (last == null) last = newnode;
    }

    public int deleteFirst() {
        int ret;

        if (first == null) return -1;
        ret = first.data;
        first = first.next;

        if (first == null) last = null;

        return ret;
    }

    public void insertAfter(Node n, int x) {
        Node newnode;

        if (n == null) return;
        newnode = new Node();
        newnode.data = x;
        newnode.next = n.next;
        n.next = newnode;

        if (newnode.next == null) last = newnode;
    }

    // Iterator 타입의 LinkedList class 메서드
    public Iterator getIterator() {
        return new Iterator(this, first);
    }
}
