// 손코딩 할때 StackWithLinkedList를 Context 전용으로 수정,
// LinkedList의 Node도 int data 대신 Context data를 저장하도록 바꿔주어야 함

public class HanoiAdvanced {
    public static void Hanoi(int n, int from, int to) {
        StackWithLinkedList s = new StackWithLinkedList();
        Context ctx; boolean calldone;

        ctx = new Context(n, from, to, 0);

        while (true) {
            calldone = false;
            switch(ctx.whereToReturn) {

                case 0:
                    if (ctx.n <= 0) {
                        calldone = true;
                    } else {
                        s.push(new Context(ctx.n, ctx.from, ctx.to, 1)); // 현 상태를 1로 저장
                        ctx = new Context(ctx.n-1, ctx.from, 6-ctx.from-ctx.to, 0);
                    }
                    break;

                case 1:
                    System.out.print("Move a disk from rod " + ctx.from);
                    System.out.println(" to rod " + ctx.to);

                    s.push(new Context(ctx.n, ctx.from, ctx.to, 2));  // 현재 상태 백업
                    ctx = new Context(ctx.n-1, 6-ctx.from-ctx.to, ctx.to, 0);  // 두 번째 재귀호출
                    break;

                case 2:
                    calldone = true;
                    break;
            }
            if (calldone) {
                if (s.isEmpty()) break; else ctx = s.pop();
            }
        }
    }
}
