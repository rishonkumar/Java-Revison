package LambdasFunctionalInterface_38;

import java.time.chrono.IsoChronology;
import java.util.function.Predicate;
import java.util.List;
import java.util.stream.Collectors;

/*
 * Predicate<T> — T → boolean
 * Abstract method: boolean test(T t)
 *
 * Key combinator methods:
 *   and(other)   → both must be true
 *   or(other)    → at least one must be true
 *   negate()     → flip the result
 *   not(pred)    → static method in Java 11+ (same as negate)
 */
public class PredicateExample {

    public static void main(String[] args) {

        // ── Basic predicate ────────────────────────────────────────────────
        Predicate<Integer> isEven = x -> x % 2 == 0;
        System.out.println(isEven.test(4));   // true
        System.out.println(isEven.test(7));   // false

        // ── Combining predicates ───────────────────────────────────────────
        Predicate<Integer> isPositive = x -> x > 0;
        Predicate<Integer> isLarge    = x -> x > 100;

        Predicate<Integer> isEvenAndPositive = isEven.and(isPositive);
        Predicate<Integer> isOdd             = isEven.negate();
        Predicate<Integer> isEvenOrLarge     = isEven.or(isLarge);

        System.out.println(isEvenAndPositive.test(4));   // true
        System.out.println(isEvenAndPositive.test(-4));  // false (not positive)
        System.out.println(isOdd.test(3));               // true
        System.out.println(isEvenOrLarge.test(101));     // true (large)
        System.out.println(isEvenOrLarge.test(3));       // false (odd and small)

        // ── Predicate with Stream — filter ────────────────────────────────
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        List<Integer> evenNumbers = numbers.stream()
                                           .filter(isEven)          // pass predicate
                                           .collect(Collectors.toList());
        System.out.println("Even: " + evenNumbers);  // [2, 4, 6, 8, 10]

        List<Integer> oddNumbers = numbers.stream()
                                          .filter(isEven.negate())
                                          .collect(Collectors.toList());
        System.out.println("Odd: " + oddNumbers);   // [1, 3, 5, 7, 9]

        // ── Predicate<String> — common use case ───────────────────────────
        Predicate<String> isNullOrEmpty = s -> (s == null || s.isEmpty());
        Predicate<String> isNotEmpty    = isNullOrEmpty.negate();

        System.out.println(isNotEmpty.test("hello"));  // true
        System.out.println(isNotEmpty.test(""));        // false

        // Java 11+: Predicate.not() for even cleaner code
        // Predicate<String> isNotEmpty = Predicate.not(String::isEmpty);

        //PRedicate chainging
//        Suppose x > 100 and x % 2 == 0

        Predicate<Integer>isGreater = x -> x > 100;
        Predicate<Integer>isEvenn = x -> x % 2 == 0;

        System.out.println(isGreater.and(isEvenn).test(102));

        System.out.println(isGreater.or(isEvenn).test(102)); // or condition

        //negate !
        Predicate<Integer>isOddd = isEvenn.negate();
        System.out.println(isOddd.test(111));

        Predicate<Student>passed = s -> s.mark >=40;
        Predicate<Student>isAdult = s -> s.age >= 18;

        Predicate<Student> isEligible = passed.and(isAdult);

        System.out.println(isEligible.test(new Student(50,19)));

    }
}

//example for predicate

class Student {
    int age;
    int mark;

    public Student(int mark, int age) {
        this.age = age;
        this.mark = mark;
    }
}
