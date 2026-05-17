public class BubbleSort {
    public static void bubbleSort(int[] a, int n) {
        boolean sorted;
        int tmp;

        do {
            sorted = true;
            for (int i = 0; i < n-1; i++) {
                if (a[i] > a[i+1]) {
                    tmp = a[i];
                    a[i] = a[i+1];
                    a[i+1] = tmp;
                    sorted = false;
                }
            }
        } while (!sorted);
    }
}
