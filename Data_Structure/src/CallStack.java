public class CallStack {
    private int a = 0;
    private void funcA() {
        funcB(true);
        System.out.println(a);
    }

    private void funcB(boolean flag) {
        int c;
        if (flag) c = 10; else c = 20;
        funcC(flag);
        System.out.println(c);
    }

    private void funcC(boolean flag) {
        a++;
        if (flag) funcB(false);
        System.out.println(a);
    }

    public static void main(String[] args) {new CallStack().funcA();}
}
