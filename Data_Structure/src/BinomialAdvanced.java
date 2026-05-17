public class BinomialAdvanced {
    public static int binom(int n, int k) {
        int r1, r2;

        if ((k < 0) || (k > n)) return 0;
        if ((k == 0) || (k == n)) return 1;

        // C(n+1, k) - C(n, k-1)
        r1 = binom(n + 1, k);
        r2 = binom(n, k - 1);
        return r1 - r2;
    }
}
