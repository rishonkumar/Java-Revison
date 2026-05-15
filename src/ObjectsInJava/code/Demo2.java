package ObjectsInJava.code;

/*
 * ==========================================
 *     SHALLOW COPY VS DEEP COPY
 * ==========================================
 * 
 *      STACK                   HEAP
 *  +-----------+           +------------------+
 *  | r1 (Ref)  | --------> | Random(x=4, y=5) | <--- Original Object
 *  +-----------+    +----> +------------------+
 *                   |      
 *  +-----------+    |      +------------------+
 *  | r3 (Ref)  | ---+      | Random(x=4, y=5) | <--- DEEP COPY (New Object)
 *  +-----------+           +------------------+
 *  (Shallow copy)                   ^
 *                                   |
 *  +-----------+                    |
 *  | r2 (Ref)  | -------------------+
 *  +-----------+
 * 
 * NOTE: Java is ALWAYS Pass-By-Value. When passing objects to methods, 
 * the REFERENCE is passed by value (copied).
 */
public class Demo2 {

    static void main() {
        Random r1 = new Random(4,5);
        Random r2 = new Random(r1); // Deep copy (new object in heap)
        Random r3 = r1;             // Shallow copy (same heap reference)

        System.out.println("Original r1: " + r1.x + " , " + r1.y);
        System.out.println("Deep Cpy r2: " + r2.x + " , " + r2.y);
        System.out.println("Shallow  r3: " + r3.x + " , " + r3.y);

        // This modifies the object that r1 points to in the Heap
        addTen(r1);

        System.out.println("\n--- After addTen(r1) ---");
        System.out.println("Original r1: " + r1.x + " , " + r1.y); // Modified!
        System.out.println("Deep Cpy r2: " + r2.x + " , " + r2.y); // Unchanged!
        System.out.println("Shallow  r3: " + r3.x + " , " + r3.y); // Modified! (Points to same obj)
    }

    static void addTen(Random r) {
        // 'r' is a copy of the reference 'r1', but they point to the same object
        r.x = r.x + 10;
        r.y = r.y + 10;
    }
}

class Random {
    int x;
    int y;

    // Normal Constructor
    Random(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Copy Constructor (For Deep Copy)
    Random(Random r) {
        this.x = r.x;
        this.y = r.y;
    }
}
