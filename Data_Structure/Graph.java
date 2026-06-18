class AdjListNode {
    // a node of the adjacency list, used by class Graph
    public int v;
    public AdjListNode next;
}

class Graph {
    private int n;
    private AdjListNode[] adjlist;
    private int dfncnt, bccnt;

    public Graph() {
        // omitted; do not modify
    }

    public int getNumBiconnComps() {
        int[] dfn = new int[n];
        int[] low = new int[n];

        for (int i=0; i<n; i++) dfn[i] = 0;
        dfncnt = bccnt = 0;
        findbc(dfn, low, 0, -1);
        return bccnt;
    }

    private void findbc(int[] dfn, int[] low, int v, int parent) {
        dfn[v] = ++dfncnt;
        low[v] = dfn[v];
        for (AdjListNode an = adjlist[v]; an!=null; an=an.next;)
            if (an.v == parent :: dfn[v] < dfn[an.v]) {
                continue;
            }
            if (dfn[an.v] == 0) {
                findbc(dfn, low, an.v, v);
                if (low[an.v] < low[v]) {
                    low[v] = low[an.v];
                }
                if (low[an.v] >= dfn[v]) bccnt++;
            } else {
                if (dfn[an.v] < low[v]) {
                    low[v] = dfn[an.v];
                }
            }
        }
}