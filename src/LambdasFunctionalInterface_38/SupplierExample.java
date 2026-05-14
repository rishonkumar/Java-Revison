package LambdasFunctionalInterface_38;

import java.util.function.Supplier;
import java.util.ArrayList;
import java.util.List;

/*
 * Supplier<T> — () → T  (opposite of Consumer)
 * Abstract method: T get()
 *
 * No input, produces output.
 * Key uses:
 *   - Lazy initialization (don't compute until needed)
 *   - Factory methods
 *   - Random value generation
 *   - Deferred computation
 */
public class SupplierExample {

    public static void main(String[] args) {

        // ── Basic Supplier ─────────────────────────────────────────────────
        Supplier<Double> random = () -> Math.random();
        System.out.println(random.get());  // some random double (0.0 to 1.0)
        System.out.println(random.get());  // different value each call

        Supplier<String> greeting = () -> "Hello, Rishon!";
        System.out.println(greeting.get());

        // ── Supplier for lazy initialization ──────────────────────────────
        // The list is only created when .get() is called
        Supplier<List<String>> listFactory = ArrayList::new;  // constructor reference
        List<String> names1 = listFactory.get();  // creates new list
        List<String> names2 = listFactory.get();  // creates ANOTHER new list (independent)

        names1.add("Alice");
        System.out.println("names1: " + names1);  // [Alice]
        System.out.println("names2: " + names2);  // []  ← independent!

        // ── Supplier as a factory ──────────────────────────────────────────
        Supplier<StudentDemo> studentFactory = () -> new StudentDemo("Default", 0);
        StudentDemo s = studentFactory.get();
        System.out.println(s.name);  // Default

        // ── Lazy evaluation with Supplier ──────────────────────────────────
        // Without supplier: expensive() is ALWAYS called
        // printIfTrue(true, expensiveComputation());  // called even if condition is false!

        // With supplier: only called if actually needed
        printIfTrue(true, () -> "Expensive Result");  // ✅ lazy — computed only when needed
        printIfTrue(false, () -> "Never computed");   // ✅ this lambda body never runs
    }

    // Only call supplier.get() if condition is true (lazy evaluation)
    static void printIfTrue(boolean condition, Supplier<String> valueSupplier) {
        if (condition) {
            System.out.println(valueSupplier.get());  // computed on demand
        }
    }
}

class StudentDemo {
    String name;
    int age;
    StudentDemo(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
