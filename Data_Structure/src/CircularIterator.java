public class CircularIterator {
    private Node header, prev, cur;

    public CircularIterator(Node h) {
        header = h;
        prev = h;
        cur = h.next;
    }

    public boolean atEnd() {
        return (cur == header);
    }

    public int getData() {
        if (atEnd()) {
            // error
            return -1;
        }
        return cur.data;
    }

    public void next() {
        prev = cur;
        cur = cur.next;
    }

    public int deleteCurrent() {
        int ret;

        if (cur == header) {
            ret = -1;
        } else {
            ret = cur.data;
            prev.next = cur.next;
            cur = cur.next;
        }

        return ret;
    }

    public void insertAfter(int x) {
        Node newnode = new Node();

        newnode.data = x;
        newnode.next = cur.next;
        cur.next = newnode;
    }
}