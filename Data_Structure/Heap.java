/* =====================================================================
 *  Heap.java   (CSI2103 Data Structures - final prep)
 *  ---------------------------------------------------------------
 *  MAX-heap on an array, following the lecture conventions exactly:
 *    - structural property : complete binary tree (filled left->right)
 *    - order property      : parent key >= children keys   (MAX heap)
 *    - array representation : valid because a heap is ALWAYS complete,
 *                             so there are no gaps to waste.
 *    - 1-indexed            : we keep A[0] unused and store A[1..n],
 *                             so  parent(i)=i/2, left(i)=2i, right(i)=2i+1.
 *                             (Java arrays are 0-based; the professor uses
 *                              1-based "for convenience" -- we mirror that.)
 *  NO java.util is used anywhere (course constraint).
 * ===================================================================== */
public class Heap {

    private int[] A;   // A[1..n] holds the heap; A[0] is a dummy slot
    private int   n;   // current number of nodes in the heap

    // ---- create an empty heap : O(1) -------------------------------
    public Heap(int capacity) {
        A = new int[capacity + 1];   // +1 because index 0 is unused
        n = 0;
    }

    public int  size()    { return n; }
    public boolean isEmpty() { return n == 0; }

    // index helpers (1-indexed)
    private static int parent(int i) { return i / 2;     }
    private static int left  (int i) { return 2 * i;     }
    private static int right (int i) { return 2 * i + 1; }

    private void swap(int i, int j) { int t = A[i]; A[i] = A[j]; A[j] = t; }

    // ---- findMax : the max is always the root : O(1) ---------------
    public int findMax() {
        if (n == 0) throw new RuntimeException("heap is empty");
        return A[1];
    }

    // =================================================================
    //  Heapify(i)  ("down-heap" / sift-down)
    //  Precondition: the LEFT and RIGHT subtrees of i are already valid
    //  max-heaps. We push A[i] down until the whole subtree rooted at i
    //  is a valid heap. Each step we swap with the LARGER child so the
    //  promoted child does not violate the heap property above it.
    //  Runtime: O(height of subtree) = O(log n) in the worst case,
    //  because a heap is a complete tree (height ~ log n), unlike a
    //  skewed BST.
    // =================================================================
    private void heapify(int i) {
        while (left(i) <= n) {            // while i has at least a left child
            int larger = left(i);
            int r = right(i);
            if (r <= n && A[r] > A[larger])   // pick the larger of the 2 kids
                larger = r;
            if (A[i] >= A[larger]) break;     // heap property already holds
            swap(i, larger);
            i = larger;                       // problem moves down one level
        }
    }

    // ---- insert : add at the end, then "up-heap" : O(log n) --------
    public void insert(int key) {
        if (n + 1 >= A.length) grow();
        A[++n] = key;                    // only legal spot that keeps it complete
        int j = n;
        while (j > 1 && A[j] > A[parent(j)]) {   // bubble up past smaller parents
            swap(j, parent(j));
            j = parent(j);
        }
    }

    // ---- deleteMax : swap root with last, shrink, heapify(root) -----
    //  O(log n). We never physically erase the array cell; we just
    //  decrement n, so the old max sits safely just past the heap end
    //  (this is exactly what makes heap sort in-place).
    public int deleteMax() {
        if (n == 0) throw new RuntimeException("heap is empty");
        int max = A[1];
        A[1] = A[n];        // last node moves to the root
        n--;                // logically delete the last slot
        heapify(1);         // restore order from the root down
        return max;
    }

    private void grow() {
        int[] B = new int[A.length * 2];
        for (int k = 0; k < A.length; k++) B[k] = A[k];
        A = B;
    }

    // =================================================================
    //  buildHeap : bottom-up heapify of a raw array  ->  O(n)
    //  Treat the given array as a complete binary tree as-is, then call
    //  heapify on every INTERNAL node, going from the last internal node
    //  (index n/2) up to the root (index 1). Leaves (indices > n/2) are
    //  trivially valid heaps, so we skip them.
    //  This is O(n), NOT O(n log n): nodes with large height are few, and
    //  the level-by-level sum of subtree heights telescopes to O(n).
    // =================================================================
    public void buildHeap(int[] data) {
        n = data.length;
        if (n + 1 > A.length) A = new int[n + 1];
        for (int k = 0; k < n; k++) A[k + 1] = data[k];  // copy into 1..n
        for (int i = n / 2; i >= 1; i--) heapify(i);
    }

    // =================================================================
    //  heapSort (static, in-place, ASCENDING result)
    //  Phase 1: build a max-heap bottom-up               -> O(n)
    //  Phase 2: repeatedly move the current max to the end
    //           (swap root with last live slot, shrink, sift down) -> O(n log n)
    //  Each extracted max lands at the back, so the array ends sorted
    //  in ASCENDING order. In-place and UNSTABLE.  Total: O(n log n).
    //
    //  Implemented on a private 1-indexed copy so the index math matches
    //  the lecture; we copy the sorted result back into `data`.
    // =================================================================
    public static void heapSort(int[] data) {
        int m = data.length;
        if (m <= 1) return;
        int[] a = new int[m + 1];                 // 1-indexed working array
        for (int k = 0; k < m; k++) a[k + 1] = data[k];

        // Phase 1: bottom-up build  O(n)
        for (int i = m / 2; i >= 1; i--) siftDown(a, i, m);

        // Phase 2: repeated deleteMax  O(n log n)
        for (int end = m; end >= 2; end--) {
            int t = a[1]; a[1] = a[end]; a[end] = t; // move max to back
            siftDown(a, 1, end - 1);                 // restore heap on a[1..end-1]
        }
        for (int k = 0; k < m; k++) data[k] = a[k + 1];
    }

    // sift-down on a 1-indexed array a[1..size]  (same logic as heapify)
    private static void siftDown(int[] a, int i, int size) {
        while (2 * i <= size) {
            int larger = 2 * i;
            if (2 * i + 1 <= size && a[2 * i + 1] > a[larger]) larger = 2 * i + 1;
            if (a[i] >= a[larger]) break;
            int t = a[i]; a[i] = a[larger]; a[larger] = t;
            i = larger;
        }
    }

    // =================================================================
    //  Self-test
    // =================================================================
    private static boolean isAscending(int[] x) {
        for (int k = 1; k < x.length; k++) if (x[k - 1] > x[k]) return false;
        return true;
    }

    public static void main(String[] args) {
        System.out.println("=== TEST 1: buildHeap (bottom-up) + repeated deleteMax ===");
        int[] data = {4, 31, 61, 9, 37, 1, 15, 8, 25};
        Heap h = new Heap(data.length);
        h.buildHeap(data);
        System.out.print("max should be 61 -> findMax() = " + h.findMax());
        System.out.println(h.findMax() == 61 ? "   [OK]" : "   [FAIL]");

        // extracting maxes one by one must yield DESCENDING order
        StringBuilder sb = new StringBuilder();
        int prev = Integer.MAX_VALUE; boolean desc = true;
        while (!h.isEmpty()) {
            int x = h.deleteMax();
            sb.append(x).append(' ');
            if (x > prev) desc = false;
            prev = x;
        }
        System.out.println("deleteMax sequence : " + sb.toString().trim());
        System.out.println("strictly non-increasing? " + (desc ? "[OK]" : "[FAIL]"));

        System.out.println("\n=== TEST 2: insert (up-heap) keeps root = current max ===");
        Heap g = new Heap(4);
        int[] ins = {10, 40, 20, 55, 5, 33, 70, 2};   // capacity grows automatically
        int runningMax = Integer.MIN_VALUE; boolean ok2 = true;
        for (int v : ins) {
            g.insert(v);
            runningMax = Math.max(runningMax, v);
            if (g.findMax() != runningMax) ok2 = false;
        }
        System.out.println("after each insert findMax()==runningMax ? " + (ok2 ? "[OK]" : "[FAIL]"));

        System.out.println("\n=== TEST 3: heapSort -> ascending, in place ===");
        int[] arr = {4, 31, 61, 9, 37, 1, 15, 8, 25, 25, 0, 100, -7};
        heapSort(arr);
        System.out.print("sorted: ");
        for (int v : arr) System.out.print(v + " ");
        System.out.println();
        System.out.println("ascending? " + (isAscending(arr) ? "[OK]" : "[FAIL]"));

        System.out.println("\n=== TEST 4: edge cases ===");
        int[] one = {42}; heapSort(one);
        int[] empty = {}; heapSort(empty);
        System.out.println("size-1 and size-0 arrays handled  [OK]");
    }
}
