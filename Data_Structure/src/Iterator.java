public class Iterator {
    private Node prev, cur;
    private LinkedList llist;

    public Iterator(LinkedList caller, Node first) {
        llist = caller;
        prev = null;
        cur = first;
    }

    public boolean atEnd() {
        return (cur == null);
    }

    public int getData() {
        if (atEnd()) {
            // error
            return 0;
        }
        return cur.data;
    }

    public void next() {
        if (!atEnd()) {
            prev = cur;
            cur = cur.next;
        }
    }

    // Insertion using iteration
    public void insertAfter(int x) {
        llist.insertAfter(cur, x);
    }

    // Deletion using iteration
    public int deleteCurrent() {
        int ret;

        if (cur == null) {
            return -1;
        }

        ret = cur.data;
        cur = cur.next;

        if (prev == null) {
            llist.first = cur;
        } else {
            prev.next = cur;
        }

        if (cur == null) llist.last = prev;

        return ret;
    }
}