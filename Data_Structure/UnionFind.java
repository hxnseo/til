class UnionFind {
    // union-find data structure using tree representation
    private int[] parent;
    private int[] card;
    private int n;

    public UnionFind(int _n) {
        n = _n;
        if (n>0) {
            parent = new int[n];
            card = new int[n];
            for (int i=0; i<n; i++) {
                parent[i] = -1;
                card[i] = 1;
            }
        } else {
            parent = card = null;
        }
    }

    public void union(int r1, int r2) {
        int tmp;
        if (card[r1] < card[r2]) {
            tmp = r1;
            r1 = r2;
            r2 = tmp;
        }
        parent[r2] = r1;
        card[r1] += card[r2];
    }

    public int find(int e) {
        int ans, etmp;
        etmp = e;
        while (parent[etmp] != -1) etmp = parent[etmp];
        ans = etmp;
        while (parent[e] != -1) {
            etmp = parent[e];
            parent[e] = ans;
            e = etmp;
        }
        return ans;
    }
}