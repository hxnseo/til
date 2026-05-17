public class ModifiedEfficiency1Final {
    public static void main(String[] args) {
        // input: a[0] ... a[n-1]
        // output: min

        // an array for test
        int[] a = {1, 5, 8, 2, 9};
        int n = a.length;
        int min = a[0];

        // first attempt
        for (int i = 0; i < n; i++) {
            // check if a[i] is the min
            boolean isMin = true;
            for (int j = 0; j < n; j++) {
                if (a[j] < a[i]) isMin = false;
                break;
            }

            if (isMin) min = a[i];
        }

        System.out.println("min: " + min);
    }
}
