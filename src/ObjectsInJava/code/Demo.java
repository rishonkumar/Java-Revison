package ObjectsInJava.code;

/*
 * ==========================================
 *        CALL BY VALUE IN JAVA
 * ==========================================
 * 
 *  main() Stack Frame         addTen() Stack Frame
 *  +--------------+           +--------------+
 *  | x = 4        | --copy--> | x = 4 -> 14  | (local copy modified)
 *  | y = 3        | --copy--> | y = 3 -> 13  | (local copy modified)
 *  +--------------+           +--------------+
 * 
 * CONCLUSION: 
 * Modifying primitive parameters inside a method 
 * does NOT affect the original variables in the caller.
 */
public class Demo {

    static void main() {
        int x = 4;
        int y = 3;

        System.out.println("Before: " + x + " " + y);

        addTen(x, y);

        // Values remain 4 and 3 because only a copy was passed
        System.out.println("After: " + x + " " + y);
    }

    static void addTen(int x, int y) {
        x = x + 10;
        y = y + 10;
    }
}
