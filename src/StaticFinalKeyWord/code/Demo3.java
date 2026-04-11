package StaticFinalKeyWord.code;



public class Demo3 {

    static void main() {
        Random r1  = new Random();
        System.out.println(r1.PI);

        final int x = 4;
        System.out.println(x);
    }
}


class Random {
    final double PI; // means this will not change (CONSTANTS)

    static final double CHECK = 3;

    Random() {
        this.PI = 3.14;
    }

//    static {
//        CHECK = 3;
//    }
}
