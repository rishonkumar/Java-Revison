package LambdasFunctionalInterface_38;

import java.util.function.Predicate;
import java.util.List;
import java.util.stream.Collectors;

/*
 * ==========================================
 *        PREDICATE INTERFACE & CHAINING
 * ==========================================
 * 
 * Predicate<T> — T → boolean
 * Abstract method: boolean test(T t)
 *
 * PREDICATE COMPOSITION:
 * 
 *               [ Object ]
 *                   |
 *     +-------------+-------------+
 *     |                           |
 * [ Predicate A ]           [ Predicate B ]
 *     |                           |
 *     +----->  [ .and() ]  <------+  = True only if BOTH are true
 *     +----->  [ .or()  ]  <------+  = True if AT LEAST ONE is true
 *     +-----> [ .negate() ]          = Flips the boolean result
 * 
 * Key Use: Stream.filter(), input validation, conditional logic.
 */
public class PredicateExample {

    public static void main(String[] args) {

        // ── Basic predicate ────────────────────────────────────────────────
        Predicate<Integer> isEven = x -> x % 2 == 0;
        System.out.println("Is 4 even? " + isEven.test(4));   // true
        System.out.println("Is 7 even? " + isEven.test(7));   // false

        // ── Combining predicates ───────────────────────────────────────────
        Predicate<Integer> isPositive = x -> x > 0;
        Predicate<Integer> isLarge    = x -> x > 100;

        Predicate<Integer> isEvenAndPositive = isEven.and(isPositive);
        Predicate<Integer> isOdd             = isEven.negate();
        Predicate<Integer> isEvenOrLarge     = isEven.or(isLarge);

        System.out.println("4 is Even & Positive: " + isEvenAndPositive.test(4));   // true
        System.out.println("-4 is Even & Positive: " + isEvenAndPositive.test(-4));  // false
        System.out.println("3 is Odd: " + isOdd.test(3));               // true
        System.out.println("101 is Even or Large: " + isEvenOrLarge.test(101));     // true (large)
        System.out.println("3 is Even or Large: " + isEvenOrLarge.test(3));       // false

        // ── Predicate with Stream — filter ────────────────────────────────
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        List<Integer> evenNumbers = numbers.stream()
                                           .filter(isEven)          // pass predicate
                                           .collect(Collectors.toList());
        System.out.println("Even Stream: " + evenNumbers);  // [2, 4, 6, 8, 10]

        List<Integer> oddNumbers = numbers.stream()
                                          .filter(isEven.negate())
                                          .collect(Collectors.toList());
        System.out.println("Odd Stream: " + oddNumbers);   // [1, 3, 5, 7, 9]

        // ── Predicate<String> — common use case ───────────────────────────
        Predicate<String> isNullOrEmpty = s -> (s == null || s.isEmpty());
        Predicate<String> isNotEmpty    = isNullOrEmpty.negate();

        System.out.println("Is 'hello' not empty? " + isNotEmpty.test("hello"));  // true
        System.out.println("Is '' not empty? " + isNotEmpty.test(""));        // false

        // ── Real-world Object Predicate Chaining ───────────────────────────
        Predicate<Student> passed = s -> s.mark >= 40;
        Predicate<Student> isAdult = s -> s.age >= 18;

        Predicate<Student> isEligible = passed.and(isAdult);

        Student testStudent = new Student(50, 19);
        System.out.println("Is student eligible? " + isEligible.test(testStudent)); // true
    }
}

// Model class for object predicates
class Student {
    int age;
    int mark;

    public Student(int mark, int age) {
        this.age = age;
        this.mark = mark;
    }
}
