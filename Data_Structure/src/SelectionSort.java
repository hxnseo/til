public class SelectionSort {
    public static void selectionSort(int[] a, int n) {
        int i, j, k, tmp;

        for (i = 0; i < n-1; i++) {
            j = i;
            for (k = i+1; k < n; k++) {
                if (a[k] < a[j]) j = k;
            }

            tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;
        }
    }
}
