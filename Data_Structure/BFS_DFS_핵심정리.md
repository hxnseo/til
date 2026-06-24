# Graphs — BFS / DFS 핵심정리

> 범위(Course Review): Basic terminology → Internal representation → BFS → DFS
> (MST·Topological·Dijkstra는 이후 토픽)

---

## 0. 그래프 = 추상적 수학 객체

- 그래프 `G = (V, E)`. **V**: 유한·비어있지 않은 정점 집합, **E**: 정점 쌍들의 집합.
- 무방향 간선 = **순서 없는** 쌍 `{u,w}`, 방향 간선(=호/arc) = **순서 있는** 쌍 `(u,w)`.
- 기원: 오일러의 쾨니히스베르크 다리 문제(각 다리를 정확히 한 번씩 건너기).
- **트리 ⊂ 그래프**: 트리는 "사이클 없고 연결된" 특수 그래프일 뿐. 트리 정의에는 루트 개념이 없음 — 루트는 그냥 "어느 노드가 루트야"라는 정보를 추가한 것.

### 용어 (시험 함정 단골)
- **경로 길이** = 거치는 **간선의 개수** (정점 개수 아님). 자기 자신으로의 최단경로 길이 = 0.
- 방향그래프: **진입차수(in-degree)** = 들어오는 간선 수, **진출차수(out-degree)** = 나가는 간선 수. 호 `(u,w)`에서 u=꼬리(tail), w=머리(head).
- **강연결(strongly connected)**: 모든 정점 쌍 (u,w)에 대해 u→w, w→u 경로가 **양방향 모두** 존재.
- **약연결(weakly connected)**: 방향 무시한 **기저 그래프(underlying graph)**가 연결이면 충분.
- 다중그래프(중복 간선 허용), 루프(자기 자신으로의 간선), 유사그래프(둘 다 허용).

> ⚠️ **출제포인트 — Handshake Lemma**
> sample final 2c: "정점 2103개, 모든 정점의 차수가 503인 그래프가 존재하는가?"
> 무방향 그래프에서 **Σ(차수) = 2·|E|** 이므로 차수 합은 항상 짝수.
> 2103 × 503 = **홀수** → 그런 그래프는 **존재하지 않음 (false)**.
> (홀수개의 정점 × 홀수 차수 = 홀수 합 → 불가능)

---

## 1. 내부 표현 (Internal representation)

| 표현 | 공간 | 간선 존재 확인 | 인접 정점 순회 | 특징 |
|---|---|---|---|---|
| **인접 행렬** | O(V²) | O(1) | O(V) | 무방향이면 대각선 대칭. 희소 그래프엔 낭비 |
| **인접 리스트** | O(V+E) | O(deg) | O(deg) | 정점별 연결 리스트 배열. 희소 그래프에 효율적 |
| **인접 다중 리스트** | O(V+E) | — | — | 간선당 객체 **하나**를 두 리스트가 공유 → 중복 제거 |

- **인접 리스트의 중복 문제**: 무방향 간선 1개를 표현하려면 양쪽 정점 리스트에 노드 2개 필요 → 간선 정보(가중치 등)가 두 곳에 중복 저장 → 업데이트 시 한쪽만 고치는 버그 위험.
- **인접 다중 리스트**가 이를 해결: 간선 노드가 양 끝점 인덱스 + **두 개의 next 포인터**(각 끝점 리스트용)를 가져 두 리스트에 동시 소속.
- 교수님 스켈레톤은 **인접 리스트, header node 없음**: `adjlist[i]` = 정점 i의 첫 노드 직접 참조.

---

## 2. DFS (깊이 우선 탐색)

**원리**: 현재 정점 방문 → 인접한 미방문 정점으로 **재귀** 진입 → 막히면 되돌아옴(backtrack).
**암묵적으로 스택을 사용** (재귀 호출 스택).

```java
public void DFS(int v) {
    boolean[] visited = new boolean[n];
    for (int i = 0; i < n; i++) visited[i] = false;
    doDFS(v, visited);
}
private void doDFS(int v, boolean[] visited) {
    visit(v, visited);
    for (AdjListNode e = adjlist[v]; e != null; e = e.next)
        if (!visited[e.v]) doDFS(e.v, visited);
}
```

- 새 정점을 발견할 때 쓴 간선들(=실선)의 모음 → **DFS 트리** 형성.
- **트리에 DFS = 전위 순회(preorder)** 와 정확히 일치. (이게 DFS라는 이름의 직관)

---

## 3. BFS (너비 우선 탐색)

**원리**: DFS가 스택(재귀)이라면 BFS는 **큐(queue)**. 시작점에 가까운 정점부터 층층이 탐색.
**발견 즉시 방문 표시 후 enqueue** (재방문/중복 enqueue 방지).

```java
public void BFS(int v) {
    boolean[] visited = new boolean[n];
    Queue q = new Queue(n);
    for (int i = 0; i < n; i++) visited[i] = false;
    visit(v, visited);  q.enqueue(v);
    while (!q.isEmpty()) {
        int u = q.dequeue();
        for (AdjListNode e = adjlist[u]; e != null; e = e.next)
            if (!visited[e.v]) { visit(e.v, visited); q.enqueue(e.v); }
    }
}
```

> ⭐ **BFS의 핵심 성질 (출제 1순위)**
> 1. **거리 비내림차순 방문**: 시작점으로부터 최단경로 길이가 증가(또는 같음) 순서로 정점을 방문.
> 2. **BFS 트리 = 최단 경로**: BFS 트리 상 s→i의 **유일 경로**가 곧 원 그래프의 **최단 경로**(무가중일 때).
> → 그래서 BFS로 무가중 그래프 **최단경로 길이**를 바로 계산 가능 (`dist[]`).

- **트리에 BFS = 레벨 순서(level-order)** 와 일치. (이게 BFS라는 이름의 직관)

---

## 4. 시간 복잡도 — O(V + E) vs O(V²)

**"멍청한 분석"**: doDFS가 정점마다 호출, 내부 루프 최대 V번 → O(V²). 느슨함.

**"똑똑한 분석" (교수님 강조)**:
- `if(!visited[e.v])` 덕분에 **각 정점당 재귀 호출은 전체에서 최대 1번** (호출 즉시 방문 처리되므로).
- **정점 기준**: 초기화·방문 처리는 정점당 상수 → O(V).
- **간선 기준**: 인접 간선 열거 루프는 **각 간선이 정확히 2번씩**(무방향, 양 끝점에서) 열거 → 전체 O(E).
- ⇒ 합산 **O(V + E)**.

| 그래프 | 관계 | 결론 |
|---|---|---|
| 완전 그래프 (dense) | E ∝ V² | O(V+E) = O(V²) — 둘이 같아짐 |
| 트리·희소 그래프 (sparse) | E ≈ V | O(V+E) ≪ O(V²) — V+E가 훨씬 정확·유용 |

- BFS도 동일 논증: 각 정점은 많아야 한 번 enqueue/dequeue → 각 간선 많아야 2번 고려 → **O(V+E)**.

---

## 5. DFS 응용 — 손코딩 출제 패턴

### (a) 연결 요소 개수 `getNumConnComps()` [강의]
모든 정점을 훑으며 미방문이면 카운트++ 후 그 정점에서 DFS.
```java
for (int i = 0; i < n; i++)
    if (!visited[i]) { ncomps++; doDFS(i, visited); }
```
> ⚠️ 강의 노트의 `doDFS(v,...)`는 **오타**. 올바른 인자는 `doDFS(i,...)`.

### (b) 강연결 판정 `isStronglyConnected()` [sample final Q7, 16점]
**알고리즘 (O(V+E))**:
1. 정점 0에서 정방향 DFS → 전부 도달하나? 아니면 false.
2. **역방향 그래프**(모든 호 뒤집기) 새로 생성 → 0에서 DFS → 전부 도달하나? 아니면 false.
3. 둘 다 통과 → true.
- **원 그래프 불변 유지** 필수 → 역방향은 새 인접 리스트로 구성(원본 수정 금지).
- 직관: 0에서 모두 도달 가능 ∧ 모두가 0에 도달 가능 ⇔ 임의의 두 정점이 0을 경유해 서로 도달 가능.

---

## 6. 시험장 체크리스트 (교수님 제약)

- ❌ **`java.util` / JDK 라이브러리 금지** → Queue, Stack 직접 구현해서 답안에 **전체 정의** 포함.
- ❌ 생성자 수정 금지, 그래프 객체는 **호출 전후 불변**.
- ✅ **최악 시간복잡도로 채점** → 반드시 O(V+E) 알고리즘.
- ✅ 스켈레톤 그대로: `AdjListNode {int v; AdjListNode next;}`, `adjlist[i]`, header node 없음, 정점 0..n-1.
- ✅ "Use the algorithm presented in class" — 강의 알고리즘 그대로 쓸 것 (창의적 변형 X).

---

## 7. 자주 나오는 True/False 함정

| 명제 | 답 | 이유 |
|---|---|---|
| 트리에 DFS(전위 순회) 하면 방문 순서가 전위 순회와 같다 | T | 정의상 일치 |
| 트리에 BFS 하면 레벨 비내림차순으로 노드를 열거한다 | T | level-order |
| 무가중 그래프에서 BFS 트리 경로가 최단 경로다 | T | BFS 성질 2 |
| 무가중 그래프에서 **DFS** 트리 경로가 최단 경로다 | **F** | DFS는 깊게 파고듦 — 최단 보장 X |
| 인접 리스트 표현의 DFS/BFS는 O(V+E) | T | 똑똑한 분석 |
| 인접 **행렬** 표현의 DFS/BFS는 O(V+E) | **F** | 인접 정점 순회가 정점당 O(V) → **O(V²)** |
| 정점 2103개, 모든 차수 503인 그래프 존재 | **F** | 차수합 = 홀수 → 2·E 불가 (handshake) |
