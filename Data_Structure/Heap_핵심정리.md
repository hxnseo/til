# Heaps 핵심정리 (CSI2103 Final)

> 정리본 기준: max-heap · 배열표현 · Heapify 서브루틴 · 1-indexed
> 강의개요 위치: **Trees** 챕터 → BST 다음 (Definition / Basic operations / Heap sort)

---

## 1. Priority Queue vs Heap — "추상 vs 구체"

- **Priority Queue (우선순위 큐)** = *ADT*. 스택/큐처럼 **인터페이스로만** 정의됨 — 내부 표현은 신경 안 씀. 지원 연산: `insert`, `findMax`, `deleteMax`.
- **Heap (힙)** = 그 PQ를 **구현하는 가장 합리적인 자료구조**. PQ와 달리 **데이터의 구체적 표현·동작 방식으로 정의**됨.

→ 시험 표현 주의: "priority queue를 구현하라" = 보통 heap을 쓰라는 뜻.

---

## 2. Heap의 두 속성 (둘 다 항상 유지)

| 속성 | 내용 |
|---|---|
| **Structural (구조)** | **완전 이진 트리** (complete binary tree). 마지막 레벨 제외 모든 레벨 꽉 참, 마지막 레벨은 **왼쪽부터** 채움 |
| **Order (관계)** | (max-heap) 모든 노드 key ≥ 자식들의 key ⇒ **루트 = 최댓값** |

> ⚠️ Heap order는 **BST order와 다름**: 형제·삼촌 간 대소 관계는 **보장 없음**. 부모-자식 관계만.

---

## 3. 배열 표현 (왜 가능한가)

- **이전 배열표현의 단점** = 트리가 완전 이진 트리가 아니면 중간 빈칸 낭비.
- **Heap은 정의상 항상 완전 이진 트리** ⇒ 빈칸이 없어 **낭비 0** ⇒ 배열표현이 **딱 맞음**.
- **이게 BST와의 결정적 차이**: BST는 편향 가능 → 배열표현 불가. Heap은 완전 트리라 배열표현 유일하게 정당.

**Index 규칙 (1-indexed, A[0] 미사용 — 정리본 컨벤션):**
```
parent(i) = i / 2
left(i)   = 2i
right(i)  = 2i + 1
```
> Java는 0부터지만 교수님이 "편의를 위해 1부터"로 설명 → 손코딩도 1-indexed로 맞추면 식이 깔끔.

---

## 4. 기본 연산 + 복잡도

| 연산 | 방법 | 복잡도 |
|---|---|---|
| empty heap 생성 | 배열 할당 + n=0 | **O(1)** |
| `findMax` | 루트(A[1]) 반환 | **O(1)** |
| `insert` | 맨 끝 추가 → **up-heap** (부모와 비교하며 위로) | **O(log n)** |
| `deleteMax` | 루트↔마지막 swap → n-- → **Heapify(root)** | **O(log n)** |
| `buildHeap` | 배열을 트리로 간주 → **bottom-up heapify** | **O(n)** ⭐ |
| `heapSort` | build O(n) + deleteMax n번 O(n log n) | **O(n log n)** |

핵심: 높이 h = **O(log n)** — 힙은 꽉 찬 완전 이진 트리라 **편향 BST와 달리** 항상 균형. 그래서 insert/deleteMax가 O(log n)으로 보장됨.

---

## 5. Heapify (= down-heap / sift-down)

**정의:** 노드 i의 **왼쪽·오른쪽 서브트리가 이미 유효한 힙**이라는 전제 하에, i를 루트로 하는 서브트리를 **다시 유효한 힙으로** 만드는 작업.

**왜 "더 큰 자식"과 swap?**
두 자식 중 **작은** 쪽과 바꾸면 → 올라간 작은 값이 *다른* 자식보다 작아져서 위반이 **여러 곳으로 복제**됨. **더 큰 자식**을 올리면 그 값은 형제보다 크므로 위쪽엔 문제 안 생김.

```
heapify(i):
  while i has a left child:
    larger = (더 큰 자식 인덱스)      # right가 있고 더 크면 right, 아니면 left
    if A[i] >= A[larger]: break       # 이미 힙 → 종료
    swap(i, larger); i = larger       # 문제는 한 칸 아래로 전이
```
deleteMax 직후엔 루트의 양쪽 서브트리가 멀쩡하므로 항상 **Heapify(root)** 호출.

---

## 6. Heap Sort — 2단계

**Phase 1 (전반부):** 주어진 배열을 **구조변경 없이** 완전 이진 트리로 간주 → 자식 있는 마지막 노드(i = n/2)부터 루트까지 **bottom-up heapify** → 전체가 유효한 max-heap. **O(n)**

**Phase 2 (후반부):** max-heap에서 `deleteMax` 반복.
- 루트(최댓값) ↔ 현재 마지막 노드 swap → n-- (물리 삭제 X, **개념적 삭제**) → Heapify(root)
- 매번 빠진 최댓값이 **배열 뒤쪽에 차곡차곡** → 끝나면 배열이 **오름차순 정렬**.
- **O(n log n)** (∑ log i = log(n!) = O(n log n))

**성질:** **in-place** (배열 외 추가 메모리 거의 X) · **unstable** (같은 값 원래 순서 보장 X)

---

## 7. ⭐ Bottom-up heapify가 왜 O(n)인가 (단순 삽입 반복은 O(n log n))

각 노드 heapify 비용 ∝ 그 노드를 루트로 하는 **서브트리 높이**. 모든 노드의 서브트리 높이 합을 **S**라 하자 (S ∝ 전체 비용).

**트릭 (2S − S):**
- 각 노드의 (빨강)높이를 두 자식에게 그대로 복사 = (파랑). 파랑 총합 = 빨강의 정확히 **2배 = 2S**.
- 같은 위치에서 **(파랑 − 빨강)** = "부모로부터 받은 높이 − 내 서브트리 높이". 이걸 전부 합산 = 2S − S = **S**.
- 이 합은 **O(n)으로 수렴** (높이 큰 루트 쪽은 노드 수가 적어 상쇄). log항은 n보다 훨씬 느리게 자람.

∴ **S = O(n)** ⇒ buildHeap은 **선형 시간**.

> 직관: 잎(높이 0)이 절반, 높이 1이 1/4 … 높이 큰 노드는 극소수 ⇒ 기하급수 수렴.

---

## 8. 🎯 출제 포인트 (finals sample 연계)

- **(2b) "max heap에 preorder 하면 nonincreasing 순서다" → FALSE.**
  Heap order는 **부모≥자식만** 보장. preorder(root→left subtree→right subtree)는 형제·서브트리 간 대소를 보장 못 함. *내림차순을 원하면* heap order가 아니라 **deleteMax 반복**이 필요. (T/F 단골 함정)
- **buildHeap O(n) vs 삽입반복 O(n log n)** 구분 — 증명 논리(2S−S, 기하급수 수렴) 한 줄 설명 가능해야.
- **Heap sort: in-place + unstable + 오름차순** 3종 세트 암기. (안정성 표 비교: Bubble/Insertion/Merge = stable / **Selection/Quick/Heap = unstable**)
- **왜 배열표현이 heap엔 정당하고 BST엔 부당한가** = "heap은 항상 complete" (서술형/T-F).
- **복잡도 한 줄 근거**: 힙 높이가 항상 O(log n)인 이유 = 완전 이진 트리(편향 불가).
- `log(n!) = Θ(n log n)` (Stirling) → **(2a)와 직결**, heap sort 하한과도 연결.
