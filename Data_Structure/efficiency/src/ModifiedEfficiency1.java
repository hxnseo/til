public class ModifiedEfficiency1 {
    public static void main(String[] args) {
        // input: a[0] ... a[n-1]
        // output: min

        // an array for test
        int[] a = {2,5,7,4,24,6};
        int n = a.length;
        int min;

        for (int i = 0; i < n; i++) {
            int candidate = a[i];
            boolean isMin = true;

            for (int j = 0; j < n; j++) {
                if (a[j] < candidate) {
                    isMin = false;
                }
            }

            if (isMin) {
                min = candidate;
            }
        }
    }
}