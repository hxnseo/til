public class ModifiedEfficiency2 {
    public static void main(String[] args) {
        // settings
        int[] a = {2,3,4,5,6};
        int n = a.length;

        //-----------------------------
        int[] sum = new int[n];
            // preprocessing
        sum[0] = a[0];
        for (int i = 1; i < n; i++) {
            sum[i] = sum[i-1] + a[i];
        }
            // query comes
        int x = 1, y = 3;

        int answer;
        if (x == 0) {
            answer = sum[y];
        } else {
            answer = sum[y] - sum[x-1];
        }
        System.out.println("answer(optimized): " + answer);
    }
}
