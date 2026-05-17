public class Hanoi {
    public static void Hanoi(int n, int from, int to) {
        if (n <= 0) return;

        int aux = 6 - from - to;
        Hanoi(n-1, from, aux);
        System.out.print("Move a disk from rod " + from);
        System.out.println(" to rod " + to);
        Hanoi(n-1, aux, to);
    }

    public static void main(String[] args) {
        Hanoi(3,1,2);
    }
}
