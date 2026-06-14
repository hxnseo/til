/* ===========================================================================
 *  Binary Search Tree — 기말 손코딩 대비 완성본
 *  한 파일에 다 넣으려고 public 클래스는 BST 하나만 두고,
 *  나머지는 같은 파일의 top-level (non-public) 클래스로 작성함.
 * ======================================================================== */

// ---------------------------------------------------------------------------
//  Node : pointer 표현. 데이터(key) + 왼/오 자식 참조
// ---------------------------------------------------------------------------
class Node {
    public int key;
    public Node left, right;

    public Node(int key) { this.key = key; }
}

// ---------------------------------------------------------------------------
//  Stack : iterator 가 재귀 콜스택을 흉내내기 위해 쓰는 "직접 구현" 스택.
//  저장하는 건 값이 아니라 Node 참조(노드 덩어리 전체)라는 점이 핵심.
//  (java.util.Stack 금지 규칙 때문에 연결리스트로 from-scratch 구현)
// ---------------------------------------------------------------------------
class Stack {
    private static class Cell { Node data; Cell next; }
    private Cell top = null;

    public boolean isEmpty() { return top == null; }

    public void push(Node n) {
        Cell c = new Cell();
        c.data = n;
        c.next = top;
        top = c;
    }

    public Node pop() {                       // 비었을 때 호출 안 한다고 가정
        Node n = top.data;
        top = top.next;
        return n;
    }

    public Node peek() { return top.data; }   // 삭제 없이 맨 위만 확인
}

// ===========================================================================
//  BST : 이진 탐색 트리 본체
// ===========================================================================
public class BST {

    private Node root;

    // -----------------------------------------------------------------------
    //  1) SEARCH  —  O(h)
    //     매 노드에서 key 와 비교 → 작으면 왼쪽, 크면 오른쪽.
    //     단순 트리 순회(모든 노드 방문)와 달리 "방향을 정해" 내려가므로 빠름.
    // -----------------------------------------------------------------------
    public boolean contains(int key) {
        Node cur = root;
        while (cur != null) {
            if (key == cur.key)      return true;
            else if (key < cur.key)  cur = cur.left;
            else                     cur = cur.right;
        }
        return false;                          // 빈 자리(null) 도달 = 없음
    }

    // -----------------------------------------------------------------------
    //  2) INSERT  —  O(h)
    //     탐색으로 들어갈 자리를 찾고, "리프로 덧붙이기"만 함.
    //     정렬된 배열 삽입은 뒤 원소들을 밀어야 해서 O(n) → BST 는 O(h).
    //     중복 key 는 BST 정의상 허용 X → 발견 시 무시(false 반환).
    // -----------------------------------------------------------------------
    public boolean insert(int key) {
        if (root == null) { root = new Node(key); return true; }

        Node cur = root, parent = null;
        while (cur != null) {
            if (key == cur.key) return false;  // 이미 존재 → 삽입 실패
            parent = cur;
            cur = (key < cur.key) ? cur.left : cur.right;
        }
        // parent 의 빈 자식 자리에 새 리프 부착 (상수 시간)
        if (key < parent.key) parent.left  = new Node(key);
        else                  parent.right = new Node(key);
        return true;
    }

    // -----------------------------------------------------------------------
    //  3) DELETE  —  O(h)   ★시험 단골★
    //     세 가지 경우:
    //       (a) 리프            : 그냥 떼어냄
    //       (b) 자식 1개        : 그 자식 서브트리를 부모에 그대로 재부착
    //       (c) 자식 2개        : "오른쪽 서브트리의 최솟값(successor)" 또는
    //                            "왼쪽 서브트리의 최댓값(predecessor)" 을
    //                            그 자리로 끌어올리고, 그 successor 를 삭제.
    //                            → 이러면 BST 속성이 유지되고 높이도 안 커짐.
    //     아래 구현은 successor(오른쪽 최솟값) 버전.
    // -----------------------------------------------------------------------
    public void delete(int key) {
        Node parent = null, cur = root;

        // (1) 삭제할 노드와 그 부모 찾기
        while (cur != null && cur.key != key) {
            parent = cur;
            cur = (key < cur.key) ? cur.left : cur.right;
        }
        if (cur == null) return;               // 없는 key → 아무 것도 안 함

        // (2) 자식이 둘이면: successor(오른쪽 서브트리 최솟값)로 치환
        if (cur.left != null && cur.right != null) {
            Node succParent = cur, succ = cur.right;
            while (succ.left != null) {         // 오른쪽 한 번 간 뒤 왼쪽으로 끝까지
                succParent = succ;
                succ = succ.left;
            }
            cur.key = succ.key;                 // 값만 복사해서 끌어올림
            // 이제 실제로 제거할 대상은 succ. succ 는 왼쪽 자식이 없음(최솟값이므로)
            cur = succ;
            parent = succParent;
        }

        // (3) 여기 도달하면 cur 은 자식이 최대 1개 → 그 자식을 부모에 재부착
        Node child = (cur.left != null) ? cur.left : cur.right;
        if (parent == null)            root = child;          // 루트를 지우는 경우
        else if (parent.left == cur)   parent.left  = child;
        else                           parent.right = child;
    }

    // -----------------------------------------------------------------------
    //  4) JOIN (합치기)  —  O(h)
    //     전제: A 의 모든 key < B 의 모든 key.
    //     A 의 최댓값(가장 오른쪽)을 새 루트로 올리고,
    //     A 의 나머지를 왼쪽 서브트리, B 전체를 오른쪽 서브트리로 부착.
    //     (자식 2개 삭제에서 successor 빼내던 방식과 사실상 동일한 원리)
    //     두 입력 트리는 파괴됨 → static 으로 두고 새 루트를 반환.
    // -----------------------------------------------------------------------
    public static Node join(Node A, Node B) {
        if (A == null) return B;
        if (B == null) return A;

        Node parent = null, m = A;             // m = A 의 최댓값(rightmost)
        while (m.right != null) { parent = m; m = m.right; }

        if (parent == null) {
            // A 의 루트가 곧 최댓값 (오른쪽 자식이 없음)
            // m.left 에는 이미 A 의 나머지(전부 < m)가 들어있음
            m.right = B;
            return m;
        } else {
            // m 을 A 에서 떼어냄. m 은 오른쪽 자식이 없으므로 m.left 를 끌어올림
            parent.right = m.left;
            m.left  = A;                       // A 의 나머지(루트 그대로) — 전부 < m
            m.right = B;                       // 전부 > m
            return m;
        }
    }

    // 편의 메서드: 이 트리(this)와 다른 트리(other)를 합쳐 this 에 반영
    public void joinWith(BST other) {
        this.root = join(this.root, other.root);
        other.root = null;                     // other 는 파괴됨
    }

    // -----------------------------------------------------------------------
    //  5) SPLIT (분할)  —  O(h)
    //     입력: 트리 + 기준 key x.
    //     출력: (x 가 트리에 있었는지 found) + 두 트리
    //           less = x 보다 작은 모든 원소, greater = x 보다 큰 모든 원소.
    //     x 가 있으면 그 노드는 버려짐(어느 쪽에도 안 들어감).
    //
    //     원리(정리본의 "점선 부착"): 루트에서 한 번 내려갈 때마다
    //       - cur.key > x  → cur 와 그 오른쪽 서브트리는 전부 greater 행.
    //                        greater 의 "열린 왼쪽 슬롯"에 cur 를 붙이고,
    //                        cur.left 쪽으로 계속 탐색.
    //       - cur.key < x  → 대칭(less 의 열린 오른쪽 슬롯에 붙임).
    //     재부착이 항상 아래 방향으로만 일어나므로
    //     결과 트리들의 높이는 원본보다 절대 커지지 않음.
    // -----------------------------------------------------------------------
    public static class SplitResult {
        public Node less, greater;
        public boolean found;
        public SplitResult(Node less, Node greater, boolean found) {
            this.less = less; this.greater = greater; this.found = found;
        }
    }

    public static SplitResult split(Node root, int x) {
        Node lessHead = null,    lessTail = null;   // less 트리: 열린 슬롯 = lessTail.right
        Node greaterHead = null, greaterTail = null;// greater 트리: 열린 슬롯 = greaterTail.left
        Node cur = root;

        while (cur != null) {
            if (x > cur.key) {
                // cur(+왼쪽 서브트리)는 less 행. less 의 오른쪽 슬롯에 부착.
                Node next = cur.right;          // x 보다 큰 값들은 아직 미정 → 따로 탐색
                cur.right = null;               // 슬롯 초기화(다음에 채워짐)
                if (lessHead == null) lessHead = cur;
                else                  lessTail.right = cur;
                lessTail = cur;
                cur = next;
            } else if (x < cur.key) {
                // cur(+오른쪽 서브트리)는 greater 행. greater 의 왼쪽 슬롯에 부착.
                Node next = cur.left;
                cur.left = null;
                if (greaterHead == null) greaterHead = cur;
                else                     greaterTail.left = cur;
                greaterTail = cur;
                cur = next;
            } else {
                // x 발견 → cur 는 버림. 왼쪽 서브트리 → less, 오른쪽 → greater
                if (lessHead == null)    lessHead    = cur.left;
                else                     lessTail.right = cur.left;
                if (greaterHead == null) greaterHead = cur.right;
                else                     greaterTail.left = cur.right;
                return new SplitResult(lessHead, greaterHead, true);
            }
        }
        // null 까지 와도 못 찾은 경우 (열린 슬롯들은 이미 null 로 닫혀 있음)
        return new SplitResult(lessHead, greaterHead, false);
    }

    // -----------------------------------------------------------------------
    //  6) TRAVERSAL (재귀) — 강의 제공 코드
    //     ★핵심★ BST 에 INORDER 순회 = 정렬된 오름차순 출력
    //       (왼<루트<오른) ∧ (왼→루트→오른) ⇒ 전체가 오름차순
    // -----------------------------------------------------------------------
    private StringBuilder sb;   // 데모용 출력 버퍼 (visit 을 출력으로 가정)

    private void doInorder(Node r) {
        if (r == null) return;
        doInorder(r.left);
        sb.append(r.key).append(' ');          // visit(r)
        doInorder(r.right);
    }
    private void doPreorder(Node r) {
        if (r == null) return;
        sb.append(r.key).append(' ');          // visit(r)
        doPreorder(r.left);
        doPreorder(r.right);
    }
    private void doPostorder(Node r) {
        if (r == null) return;
        doPostorder(r.left);
        doPostorder(r.right);
        sb.append(r.key).append(' ');          // visit(r)
    }

    public String inorder()  { sb = new StringBuilder(); doInorder(root);  return sb.toString().trim(); }
    public String preorder() { sb = new StringBuilder(); doPreorder(root); return sb.toString().trim(); }
    public String postorder(){ sb = new StringBuilder(); doPostorder(root);return sb.toString().trim(); }

    public Node getRoot() { return root; }
    public void setRoot(Node r) { this.root = r; }

    // =======================================================================
    //  DEMO / 자가 테스트
    // =======================================================================
    public static void main(String[] args) {
        BST t = new BST();
        int[] keys = {13, 9, 20, 3, 11, 19, 23, 10, 22};
        for (int k : keys) t.insert(k);

        System.out.println("inorder  (정렬돼야 함): " + t.inorder());
        System.out.println("preorder              : " + t.preorder());
        System.out.println("postorder             : " + t.postorder());

        System.out.println("contains(19): " + t.contains(19));
        System.out.println("contains(99): " + t.contains(99));
        System.out.println("insert(11) 중복 시도 : " + t.insert(11)); // false

        // 삭제 3케이스
        t.delete(3);   // 리프
        System.out.println("delete 3  (leaf)        -> " + t.inorder());
        t.delete(10);  // 자식 1개
        System.out.println("delete 10 (one child)   -> " + t.inorder());
        t.delete(20);  // 자식 2개
        System.out.println("delete 20 (two children)-> " + t.inorder());

        // SPLIT
        SplitResult sr = split(t.getRoot(), 13);
        System.out.println("split at 13  found=" + sr.found);
        System.out.println("   less    : " + inorderOf(sr.less));
        System.out.println("   greater : " + inorderOf(sr.greater));

        // JOIN (less 의 모든 key < greater 의 모든 key 이므로 다시 합칠 수 있음)
        Node merged = join(sr.less, sr.greater);
        System.out.println("join back    : " + inorderOf(merged));

        // 직접 구현 Stack 으로 만든 InOrder ITERATOR 데모
        System.out.print("InIter 출력  : ");
        InIter it = new InIter(merged);
        while (!it.atEnd()) { System.out.print(it.getData() + " "); it.next(); }
        System.out.println();
    }

    // 임의의 서브트리 루트에 대한 inorder 문자열 (테스트 헬퍼)
    static String inorderOf(Node r) {
        StringBuilder b = new StringBuilder();
        inHelp(r, b);
        return b.toString().trim();
    }
    private static void inHelp(Node r, StringBuilder b) {
        if (r == null) return;
        inHelp(r.left, b);
        b.append(r.key).append(' ');
        inHelp(r.right, b);
    }
}

/* ===========================================================================
 *  ITERATORS — 강의 제공 코드 (직접 구현 Stack 으로 재귀 콜스택을 시뮬레이션)
 *  재귀 순회는 한 번 호출되면 끝까지 돌아 "일시정지 후 한 노드 반환"이 불가능.
 *  그래서 명시적 Stack 에 Node 참조를 push/pop 하여 상태를 보존함.
 * ======================================================================== */

// PREORDER : 루트 → 왼 → 오. 스택이 LIFO 라 오른쪽을 먼저 push, 왼쪽을 나중에 push.
class PreIter {
    Stack s;
    public PreIter(Node r) {
        s = new Stack();
        if (r != null) s.push(r);              // 가장 먼저 방문할 루트만 push
    }
    public int getData() { return atEnd() ? -1 : s.peek().key; }
    public boolean atEnd() { return s.isEmpty(); }
    public void next() {
        if (atEnd()) return;
        Node cur = s.pop();
        if (cur.right != null) s.push(cur.right);   // 오른쪽 먼저
        if (cur.left  != null) s.push(cur.left);    // 왼쪽 나중 → 먼저 pop 됨
    }
}

// INORDER : 왼 → 루트 → 오. 생성 시 왼쪽 경로를 끝까지 push.
class InIter {
    Stack s;
    public InIter(Node r) { s = new Stack(); pushLeft(r); }
    public void pushLeft(Node n) {
        while (n != null) { s.push(n); n = n.left; }
    }
    public int getData() { return atEnd() ? -1 : s.peek().key; }
    public boolean atEnd() { return s.isEmpty(); }
    public void next() {
        if (atEnd()) return;
        Node n = s.pop();                      // 왼쪽+자기 처리 끝
        if (n.right != null) pushLeft(n.right);// 오른쪽 서브트리의 왼쪽 경로 준비
    }
}

// POSTORDER : 왼 → 오 → 루트. 가장 까다로움.
//   pushNextDesc: 갈 수 있는 데까지 왼쪽(없으면 오른쪽)으로 내려가며 전부 push.
//   next: pop 한 노드가 부모의 "왼쪽 자식"이었다면, 이제 부모의 오른쪽 서브트리 차례.
class PostIter {
    Stack s;
    public PostIter(Node r) { s = new Stack(); pushNextDesc(r); }
    public void pushNextDesc(Node n) {
        while (n != null) {
            s.push(n);
            if (n.left == null) n = n.right; else n = n.left;
        }
    }
    public int getData() { return atEnd() ? -1 : s.peek().key; }
    public boolean atEnd() { return s.isEmpty(); }
    public void next() {
        if (atEnd()) return;
        Node prev = s.pop();
        if (!s.isEmpty()) {
            if (s.peek().left == prev) pushNextDesc(s.peek().right);
        }
    }
}
