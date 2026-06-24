// =====================================================================
//  Graphs — BFS / DFS   (CSI 2103 final-exam style)
//
//  교수님 스켈레톤 그대로:
//      class AdjListNode { public int v;  public AdjListNode next; }   // header node 없음
//      class Graph       { private int n; private AdjListNode[] adjlist; }
//      정점은 0 .. n-1 의 정수.  adjlist[i] = 정점 i 와 인접한 정점들의 단일 연결 리스트.
//
//  시험 제약 그대로 반영:
//      - java.util 등 JDK 라이브러리 금지  →  Queue / Stack 직접 구현
//      - 그래프 객체는 메서드 호출 전후로 불변 (adjlist 수정 금지)
//      - 최악 시간복잡도가 채점 기준  →  전부 O(V + E)
//
//  [강의 의사코드 그대로]      DFS / doDFS / visit / getNumConnComps / BFS
//  [그림 시뮬레이션 → 시험용]  bfsDistances / bfsParents / dfsParents / dfsIterative
//  [sample final Q7 대응]       DiGraph.isStronglyConnected   (DFS 응용)
// =====================================================================


// ---------------------------------------------------------------------
//  인접 리스트 노드 — 교수님 스켈레톤 그대로 (필드명 v, next 고정)
// ---------------------------------------------------------------------
class AdjListNode {
    public int v;            // 인접 정점의 번호
    public AdjListNode next; // 다음 노드 (없으면 null)

    public AdjListNode(int v, AdjListNode next) { this.v = v; this.next = next; }
}


// ---------------------------------------------------------------------
//  직접 구현한 정수 Queue  (원형 배열)  — BFS에서 사용
//  BFS에서 각 정점은 많아야 한 번만 enqueue 되므로 용량 n 이면 충분.
// ---------------------------------------------------------------------
class Queue {
    private int[] a;
    private int head, tail, size;

    public Queue(int cap)        { a = new int[cap]; head = 0; tail = 0; size = 0; }
    public boolean isEmpty()     { return size == 0; }
    public void enqueue(int x)   { a[tail] = x; tail = (tail + 1) % a.length; size++; }
    public int dequeue()         { int x = a[head]; head = (head + 1) % a.length; size--; return x; }
}


// ---------------------------------------------------------------------
//  직접 구현한 정수 Stack  (배열)  — 반복형(iterative) DFS에서 사용
// ---------------------------------------------------------------------
class Stack {
    private int[] a;
    private int top;

    public Stack(int cap)    { a = new int[cap]; top = 0; }
    public boolean isEmpty() { return top == 0; }
    public void push(int x)  { a[top++] = x; }
    public int pop()         { return a[--top]; }
}


// ---------------------------------------------------------------------
//  무방향 그래프  Graph  (인접 리스트 표현)
// ---------------------------------------------------------------------
class Graph {
    private int n;
    private AdjListNode[] adjlist;

    // 방문 순서를 확인하기 위한 테스트용 버퍼 (visit의 "do something" 자리)
    private StringBuilder log = new StringBuilder();

    // --- 생성/구성 (테스트용 보조 메서드. 시험에서는 주어진다고 가정) -------------
    public Graph(int n) { this.n = n; adjlist = new AdjListNode[n]; }

    // 무방향 간선 {u,w}: 양쪽 리스트에 "꼬리(tail)"로 추가  →  삽입 순서대로 열거됨
    public void addUndirectedEdge(int u, int w) { appendTail(u, w); appendTail(w, u); }
    private void appendTail(int u, int w) {
        AdjListNode node = new AdjListNode(w, null);
        if (adjlist[u] == null) { adjlist[u] = node; return; }
        AdjListNode cur = adjlist[u];
        while (cur.next != null) cur = cur.next;
        cur.next = node;
    }

    public String drainLog() { String s = log.toString().trim(); log.setLength(0); return s; }

    // =================================================================
    //  [강의 의사코드 그대로]  DFS  (암묵적 스택 = 재귀)
    // =================================================================
    public void DFS(int v) {
        boolean[] visited = new boolean[n];
        for (int i = 0; i < n; i++) visited[i] = false;
        doDFS(v, visited);
    }

    private void doDFS(int v, boolean[] visited) {
        visit(v, visited);
        for (AdjListNode e = adjlist[v]; e != null; e = e.next) {
            if (!visited[e.v]) doDFS(e.v, visited);
        }
    }

    private void visit(int v, boolean[] visited) {
        visited[v] = true;
        log.append(v).append(' ');   // ← "do something": 방문 순서 기록 (시험에선 실제 처리)
    }

    // =================================================================
    //  [강의 의사코드 그대로]  연결 요소 개수  (DFS 응용)
    //  ※ 강의 노트의 doDFS(v,...) 는 오타. 올바른 인자는 doDFS(i,...).
    // =================================================================
    public int getNumConnComps() {
        boolean[] visited = new boolean[n];
        int ncomps = 0;
        for (int i = 0; i < n; i++) visited[i] = false;
        for (int i = 0; i < n; i++)
            if (!visited[i]) {
                ncomps++;
                doDFS(i, visited);
            }
        return ncomps;
    }

    // =================================================================
    //  [강의 의사코드 그대로]  BFS  (큐 사용, "발견 시 방문 표시")
    // =================================================================
    public void BFS(int v) {
        boolean[] visited = new boolean[n];
        Queue q = new Queue(n);
        int u;
        for (int i = 0; i < n; i++) visited[i] = false;
        visit(v, visited);
        q.enqueue(v);
        while (!q.isEmpty()) {
            u = q.dequeue();
            for (AdjListNode e = adjlist[u]; e != null; e = e.next) {
                if (!visited[e.v]) {
                    visit(e.v, visited);
                    q.enqueue(e.v);
                }
            }
        }
    }

    // =================================================================
    //  [그림 → 시험용]  BFS 최단 경로 "길이"  (무가중 그래프)
    //  핵심 성질: BFS는 시작점으로부터 간선 수 최소 경로를 거리 비내림차순으로 방문.
    //  반환: dist[i] = s→i 최단 경로 길이,  도달 불가면 -1.
    //  dist[] 가 visited 역할을 겸함 (-1 = 미방문).
    // =================================================================
    public int[] bfsDistances(int s) {
        int[] dist = new int[n];
        for (int i = 0; i < n; i++) dist[i] = -1;
        Queue q = new Queue(n);
        dist[s] = 0;
        q.enqueue(s);
        while (!q.isEmpty()) {
            int u = q.dequeue();
            for (AdjListNode e = adjlist[u]; e != null; e = e.next) {
                if (dist[e.v] == -1) {
                    dist[e.v] = dist[u] + 1;
                    q.enqueue(e.v);
                }
            }
        }
        return dist;
    }

    // =================================================================
    //  [그림 → 시험용]  BFS 트리 부모 배열 (= 실선 간선)
    //  성질: BFS 트리 상 s→i 유일 경로 = 원 그래프에서의 최단 경로.
    //  parent[s] = -1 (루트),  미방문 = -2.
    // =================================================================
    public int[] bfsParents(int s) {
        int[] parent = new int[n];
        for (int i = 0; i < n; i++) parent[i] = -2;
        Queue q = new Queue(n);
        parent[s] = -1;
        q.enqueue(s);
        while (!q.isEmpty()) {
            int u = q.dequeue();
            for (AdjListNode e = adjlist[u]; e != null; e = e.next) {
                if (parent[e.v] == -2) {
                    parent[e.v] = u;       // u를 통해 발견 → BFS 트리 간선
                    q.enqueue(e.v);
                }
            }
        }
        return parent;
    }

    // parent[] 로부터 s→t 최단 경로 복원 (t에서 거꾸로 따라가 뒤집음). 도달불가면 null.
    public int[] bfsShortestPath(int s, int t) {
        int[] parent = bfsParents(s);
        if (parent[t] == -2) return null;        // 도달 불가
        int len = 0;
        for (int x = t; x != -1; x = parent[x]) len++;
        int[] path = new int[len];
        int x = t;
        for (int k = len - 1; k >= 0; k--) { path[k] = x; x = parent[x]; }
        return path;
    }

    // =================================================================
    //  [그림 → 시험용]  DFS 트리 부모 배열 (= 실선 간선)
    //  parent[s] = -1 (루트),  미방문 = -2.
    // =================================================================
    public int[] dfsParents(int s) {
        int[] parent = new int[n];
        for (int i = 0; i < n; i++) parent[i] = -2;
        parent[s] = -1;
        doDFSParents(s, parent);
        return parent;
    }

    private void doDFSParents(int u, int[] parent) {
        for (AdjListNode e = adjlist[u]; e != null; e = e.next) {
            if (parent[e.v] == -2) {
                parent[e.v] = u;            // tree edge (실선)
                doDFSParents(e.v, parent);
            }
        }
    }

    // =================================================================
    //  [그림/개념 → 시험용]  반복형 DFS (명시적 스택)
    //  교수님: "DFS는 암묵적으로 스택(재귀)을 쓴다" → 재귀를 명시적 스택으로 변환.
    //  "발견 시 표시(mark-on-push)"라 각 정점은 많아야 한 번 push → 용량 n 안전.
    //  ※ 방문 순서는 재귀(전위)와 동일하진 않지만 유효한 DFS 순서임.
    // =================================================================
    public void dfsIterative(int s) {
        boolean[] visited = new boolean[n];
        Stack st = new Stack(n);
        visited[s] = true;
        st.push(s);
        while (!st.isEmpty()) {
            int u = st.pop();
            log.append(u).append(' ');     // do something
            for (AdjListNode e = adjlist[u]; e != null; e = e.next) {
                if (!visited[e.v]) { visited[e.v] = true; st.push(e.v); }
            }
        }
    }
}


// ---------------------------------------------------------------------
//  방향 그래프  DiGraph  — sample final Q7: isStronglyConnected()
//  adjlist[i] = i 에서 "나가는" 호(arc)의 도착지 목록.
// ---------------------------------------------------------------------
class DiGraph {
    private int n;
    private AdjListNode[] adjlist;

    public DiGraph(int n) { this.n = n; adjlist = new AdjListNode[n]; }
    public void addArc(int u, int w) { adjlist[u] = new AdjListNode(w, adjlist[u]); } // prepend

    // =================================================================
    //  강연결 판정  —  O(V + E)
    //  알고리즘: (1) 0에서 정방향 DFS → 전부 도달?  (2) 역방향 그래프에서 0부터
    //            DFS → 전부 도달?   둘 다 참이면 강연결.
    //  원 그래프는 수정하지 않음 (역방향 리스트를 새로 생성).
    // =================================================================
    public boolean isStronglyConnected() {
        // (1) 정방향 DFS
        boolean[] visited = new boolean[n];
        dfs(0, adjlist, visited);
        for (int i = 0; i < n; i++) if (!visited[i]) return false;

        // (2) 역방향 인접 리스트 구성 (호 u→w 를 w→u 로 뒤집어 저장)
        AdjListNode[] radj = new AdjListNode[n];
        for (int u = 0; u < n; u++)
            for (AdjListNode e = adjlist[u]; e != null; e = e.next)
                radj[e.v] = new AdjListNode(u, radj[e.v]);

        // (3) 역방향에서 0부터 DFS
        boolean[] rvisited = new boolean[n];
        dfs(0, radj, rvisited);
        for (int i = 0; i < n; i++) if (!rvisited[i]) return false;

        return true;
    }

    private void dfs(int u, AdjListNode[] adj, boolean[] visited) {
        visited[u] = true;
        for (AdjListNode e = adj[u]; e != null; e = e.next)
            if (!visited[e.v]) dfs(e.v, adj, visited);
    }
}


// ---------------------------------------------------------------------
//  자체 검증 main  —  강의 시뮬레이션 그래프로 모든 출력 확인
//  강의 예제(무방향): 간선 {0,1} {0,2} {1,3} {1,4} {3,4}
//      DFS(0) 방문순서 = 0 1 3 4 2
//      BFS(0) 방문순서 = 0 1 2 3 4,  거리 = [0,1,1,2,2]
// ---------------------------------------------------------------------
public class GraphBFSDFS {
    public static void main(String[] args) {
        Graph g = new Graph(5);
        g.addUndirectedEdge(0, 1);
        g.addUndirectedEdge(0, 2);
        g.addUndirectedEdge(1, 3);
        g.addUndirectedEdge(1, 4);
        g.addUndirectedEdge(3, 4);

        System.out.println("=== 무방향 강의 예제 (V=5, E=5) ===");

        g.DFS(0);
        System.out.println("DFS(0) 방문순서        : " + g.drainLog() + "   (기대: 0 1 3 4 2)");

        g.BFS(0);
        System.out.println("BFS(0) 방문순서        : " + g.drainLog() + "   (기대: 0 1 2 3 4)");

        g.dfsIterative(0);
        System.out.println("DFS(0) 반복형(스택)    : " + g.drainLog() + "   (유효한 DFS 순서)");

        int[] dist = g.bfsDistances(0);
        System.out.print("BFS 최단경로 길이      : [");
        for (int i = 0; i < dist.length; i++) System.out.print(dist[i] + (i < dist.length-1 ? ", " : ""));
        System.out.println("]   (기대: [0, 1, 1, 2, 2])");

        int[] path = g.bfsShortestPath(0, 4);
        System.out.print("0→4 최단경로           : ");
        for (int i = 0; i < path.length; i++) System.out.print(path[i] + (i < path.length-1 ? " -> " : ""));
        System.out.println("   (길이 " + (path.length-1) + ")");

        int[] dp = g.dfsParents(0);
        System.out.print("DFS 트리 부모          : [");
        for (int i = 0; i < dp.length; i++) System.out.print(dp[i] + (i < dp.length-1 ? ", " : ""));
        System.out.println("]   (-1=루트)");

        int[] bp = g.bfsParents(0);
        System.out.print("BFS 트리 부모          : [");
        for (int i = 0; i < bp.length; i++) System.out.print(bp[i] + (i < bp.length-1 ? ", " : ""));
        System.out.println("]   (-1=루트)");

        // 연결 요소: 고립 정점 2개(5,6) 추가한 새 그래프로 확인
        Graph g2 = new Graph(7);
        g2.addUndirectedEdge(0, 1);
        g2.addUndirectedEdge(0, 2);
        g2.addUndirectedEdge(1, 3);
        g2.addUndirectedEdge(1, 4);
        g2.addUndirectedEdge(3, 4);
        g2.addUndirectedEdge(5, 6);                 // 별도 컴포넌트
        System.out.println("연결 요소 개수(V=7)    : " + g2.getNumConnComps() + "   (기대: 2 — {0,1,2,3,4} 와 {5,6})");

        System.out.println();
        System.out.println("=== 방향 그래프: 강연결 판정 (Q7) ===");

        // 0→1→2→0 사이클: 강연결
        DiGraph d1 = new DiGraph(3);
        d1.addArc(0, 1); d1.addArc(1, 2); d1.addArc(2, 0);
        System.out.println("사이클 0→1→2→0         : " + d1.isStronglyConnected() + "   (기대: true)");

        // 0→1→2 경로만: 2에서 0으로 돌아갈 수 없음 → 강연결 아님
        DiGraph d2 = new DiGraph(3);
        d2.addArc(0, 1); d2.addArc(1, 2);
        System.out.println("경로 0→1→2             : " + d2.isStronglyConnected() + "   (기대: false)");
    }
}
