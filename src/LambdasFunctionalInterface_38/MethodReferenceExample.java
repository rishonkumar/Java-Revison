package LambdasFunctionalInterface_38;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

/*
 * Method References — shorthand for lambdas that ONLY call an existing method
 *
 * Syntax: ClassName::methodName  OR  instance::methodName
 *
 * 4 Types:
 *   1. Static method         : ClassName::staticMethod      (Math::abs)
 *   2. Instance method (obj) : instance::method             (System.out::println)
 *   3. Instance method (type): ClassName::instanceMethod    (String::toLowerCase)
 *   4. Constructor           : ClassName::new               (ArrayList::new)
 *
 * When can you use method reference?
 *   ONLY when the lambda does NOTHING except call a single existing method
 *   and the arguments map directly to that method's parameters.
 */
public class MethodReferenceExample {

    public static void main(String[] args) {

        List<Integer> numbers = new ArrayList<>(List.of(1, 2, 3, -4, -5));
        List<String> names = List.of("Alice", "Bob", "Charlie", "Dave");

        // ── Type 1: Static Method Reference ───────────────────────────────
        // Lambda:           x -> Math.abs(x)
        // Method reference: Math::abs
        numbers.stream()
               .map(Math::abs)          // same as x -> Math.abs(x)
               .forEach(System.out::println);

        // Lambda:           x -> Integer.parseInt(x)
        // Method reference: Integer::parseInt
        List<String> numStrings = List.of("1", "2", "3");
        numStrings.stream()
                  .map(Integer::parseInt)   // same as s -> Integer.parseInt(s)
                  .forEach(System.out::println);

        // ── Type 2: Instance Method Reference (specific object) ───────────
        // Lambda:           x -> System.out.println(x)
        // Method reference: System.out::println
        names.forEach(System.out::println);  // System.out is a specific instance

        // ── Type 3: Instance Method Reference (arbitrary instance) ────────
        // Lambda:           s -> s.toLowerCase()
        // Method reference: String::toLowerCase
        names.stream()
             .map(String::toLowerCase)    // same as s -> s.toLowerCase()
             .forEach(System.out::println);

        // Lambda:           s -> s.isEmpty()
        // Method reference: String::isEmpty
        Predicate<String> isEmpty = String::isEmpty;
        System.out.println(isEmpty.test(""));       // true
        System.out.println(isEmpty.test("hello"));  // false

        // ── Type 4: Constructor Reference ─────────────────────────────────
        // Lambda:           () -> new ArrayList<String>()
        // Method reference: ArrayList::new
        Supplier<List<String>> listMaker = ArrayList::new;
        List<String> newList = listMaker.get();  // creates new ArrayList

        // ── When you CANNOT use method reference ──────────────────────────
        // x -> x * x          ❌ custom logic — no direct method to reference
        // (a, b) -> a + b     ❌ operator — not a method call
        // x -> x > 0 ? x : 0 ❌ conditional — not a single method call

        // ── Chaining with stream pipeline ─────────────────────────────────
        long count = names.stream()
                          .map(String::toLowerCase)     // method ref
                          .filter(s -> s.startsWith("a")) // lambda (no method for this)
                          .count();
        System.out.println("Names starting with 'a': " + count);  // 1 (alice)
    }
}
