public class Efficiency2 {
    public static void main(String[] args) {
        // settings
        int[] a = {2,3,4,5,6};
        int n = a.length;

        // query (x,y)
        int x = 1, y = 3;

        //-------------------- version1
            // preprocessing: do nothing
        int answer1 = 0;
        for (int i = x; i <=y; i++) {
            answer1 += a[i];
        }
        System.out.println("answer(ver.1): " + answer1);

        // ------------------- version2
        int[][] sum = new int[n][n];
            // preprocessing
        for (int i = 0; i < n; i++) {
            sum[i][i] = a[i];
            for (int j = i+1; j < n; j++) {
                sum[i][j] = sum[i][j-1] + a[j];
            }
        }
            // now query comes
        int answer2 = sum[x][y];
        System.out.println("answer(ver.2): " + answer2);
    }
}
