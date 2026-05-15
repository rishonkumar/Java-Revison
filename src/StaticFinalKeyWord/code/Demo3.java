package StaticFinalKeyWord.code;

/*
 * ==========================================
 *        FINAL KEYWORD (CONSTANTS)
 * ==========================================
 * 
 * 1. final variable: Value cannot be changed once initialized.
 * 2. final method: Cannot be overridden by a subclass.
 * 3. final class: Cannot be inherited (e.g., String class).
 * 
 * BLANK FINAL VARIABLE:
 * A final variable that is not initialized at declaration. 
 * It MUST be initialized inside the constructor (exactly once).
 * 
 * STATIC FINAL VARIABLE:
 * Acts as a true compile-time constant for the whole class.
 */
public class Demo3 {

    static void main() {
        Random r1  = new Random();
        System.out.println("PI: " + r1.PI);
        System.out.println("STATIC CONSTANT: " + Random.CHECK);

        // Local final variable
        final int x = 4;
        // x = 5; // ERROR: Cannot assign a value to final variable 'x'
        System.out.println("Local final x: " + x);
    }
}

class Random {
    // Blank final variable (Initialized in constructor)
    final double PI; 

    // Static Final (True constant, shared across all objects, ALL_CAPS naming)
    static final double CHECK = 3;

    Random() {
        // Can only be assigned ONCE
        this.PI = 3.14;
    }
}
