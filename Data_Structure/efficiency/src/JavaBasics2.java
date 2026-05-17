public class JavaBasics2 {
    public static void main(String[] args) {
        String a, b;
        a = "3";
        b = a;
        System.out.println(a + " " + b);

        a = "4";
        System.out.println(a + " " + b);

        if (a.equals(b)) {
            System.out.println("True");
        } else {
            System.out.println("False");
        }
        // .equals() method returns boolean result of comparing "value"

        String c = "abcdeabcde";
        int x;

        x = c.length();
        System.out.println(x);

        // 파라미터에 입력된 문자열이 존재하는 첫 번째 인덱스 반환
        x = c.indexOf("bc");
        System.out.println(x);

        // 2번째 인덱스부터 3번째 인덱스까지 (n ~ n-1) 문자열 반환
        System.out.println(c.substring(2,4));
    }
}

class Array {
    public static void main(String[] args) {
        int[] a, b;
        a = new int[10];
        a[3] = 4;
        b = a;
        b[3] = 5;
        b[4] = b[3];
        b[4] = 6;

        System.out.println(a[3] + " " + b[4]);
        // array is also an object, so it copies the "reference" through assign

        // ----------------------
        MyInt[] c, d;
        c = new MyInt[10];
        c[3] = new MyInt();
        c[3].setVal(4);
        d = c;
        d[3].setVal(5);
        d[4] = d[3];
        d[4].setVal(6);

        System.out.println(c[3].getVal() + " " + c[4].getVal());
            // array stores the "reference", so if you want to get some value of that array index, you should use .getVal()
    }
}