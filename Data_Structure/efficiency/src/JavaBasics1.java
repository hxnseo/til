public class JavaBasics1 {
    public static void main(String[] args) {
        // cannot define a function "outside class"
        System.out.println("Hello, world!");

        int a = 1, b = 2;
        String c = "abc" + 3;
            // string concatenation
        System.out.println(c);
        System.out.println(a + " " + b);
        System.out.println(a+b);

        int A, B;
        A = 3;
        B = A;
            // separate after the assign
        System.out.println(A + " " + B);

        A = 4;
        System.out.println(A + " " + B);

    //--------------------------------------------------
        // Object variables work like "pointers"
        MyInt one = new MyInt(), two = new MyInt();
        one.setVal(3);
        one = two;
            // now two => one 0, these are same objects ("point" same object)
            // 3 사라지고 초기값 0이었던 two와 같은 값을 가리킴
        System.out.println(one.getVal() + " " + two.getVal());
            // 0 0

        one.setVal(4);
        System.out.println(one.getVal() + " " + two.getVal());
            // 4 4
    }
}

class Hello {
    private static void reset(MyInt x) {
        x.setVal(0);
    }

    public static void main(String[] args) {
        MyInt a = new MyInt(), b = new MyInt();
        a.setVal(10);
        reset(a);
            // 0
        System.out.println(a.getVal());

        a.setVal(3);
        b.setVal(3);
        if (a==b) {
            System.out.println("True");
        } else {
            System.out.println("False");
        }
        // Note that == means "reference(pointer) comparing"
    }
}

class MyInt {
    private int val;
    public int getVal() { return val; }
    public void setVal(int x) { val = x; }
}