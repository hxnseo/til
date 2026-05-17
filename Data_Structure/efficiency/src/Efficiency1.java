public class Efficiency1 {
    public static void main(String[]args) {
        // input: a[0] ... a[n-1]
        // output: min

        // an array for test
        int[] a = {1,5,8,2,9};
        int n = a.length;
        int min = a[0];

        // first attempt
        for (int i = 0; i < n; i++) {
            // check if a[i] is the min
            boolean isMin = true;
            for (int j = 0; j < n; j++) {
                if (a[j] < a[i]) isMin = false;
            }

            if (isMin) min = a[i];
        }

        System.out.println("min: " + min);

        // second attempt
            // a[0] is the tentative min
        int min2 = a[0];
        for (int i = 1; i < n; i++) {
            // see fo a[i] is smaller than min-so-far
            if (a[i] < min2) min2 = a[i];
        }

        System.out.println("min: " + min2);
    }
}