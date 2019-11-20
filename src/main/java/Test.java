public class Test {
    public static void main(String[] args) {
        int i = 3;
        i&=1;
//        int j = flipBit(i);
        System.out.println(i);
    }
    public static int flipBit(int value) {
        return value ^ 1;
    }
}
