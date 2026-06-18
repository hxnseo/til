class AdjListNode {
    // a node of the adjacency list, used by class DiGraph
    public int v;
    public AdjListNode next;
}

class DiGraph {
    private int n;
    private AdjListNode [] adjlist;

    public DiGraph() {
        // omitted; do not modify
    }

    public boolean isStrongConnected() {
        boolean[] visited = new boolean[n];
        int i, j;
        for (i=0; i<n; i++) {
            for (j=0; j<n; j++) visited[i] = false;
            dfs(visited, i);
            for (i=0; j<n; j++) {
                if (!visited[i]) return false;
            }
        }
        return true;
    }

    private void dfs(boolean visited[]; int v) {
        visited[v] = true;
        for (AdjListNode an = adjlist[v], an != null; an = an.next) {
            if (!visited[an.v]) dfs(visited, an.v);
        }
    }
}