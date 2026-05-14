package LambdasFunctionalInterface_38;

import java.util.function.Function;
import java.util.function.BiFunction;

/*
 * Function<T,R> — T → R
 * Abstract method: R apply(T t)
 *
 * BiFunction<T,U,R> — (T,U) → R
 *
 * Composition:
 *   andThen(f) → apply this, THEN f
 *   compose(f) → apply f FIRST, then this
 */
public class FunctionExample {

    public static void main(String[] args) {

        // ── Basic Function ─────────────────────────────────────────────────
        Function<Integer, Integer> square = x -> x * x;
        System.out.println(square.apply(5));  // 25

        Function<String, Integer> length = String::length;
        System.out.println(length.apply("Hello"));  // 5

        Function<String, String> toUpper = String::toUpperCase;
        System.out.println(toUpper.apply("hello"));  // HELLO

        // ── BiFunction — two inputs ────────────────────────────────────────
        BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
        System.out.println(add.apply(3, 4));  // 7

        BiFunction<String, String, String> fullName = (f, l) -> f + " " + l;
        System.out.println(fullName.apply("Rishon", "Kumar"));  // Rishon Kumar

        // ── Function composition ───────────────────────────────────────────
        Function<Integer, Integer> doubleIt  = x -> x * 2;
        Function<Integer, String>  toDisplay = x -> "Score: " + x;

        // andThen: double first, then convert to string
        Function<Integer, String> pipeline = doubleIt.andThen(toDisplay);
        System.out.println(pipeline.apply(10));  // Score: 20

        // ── Returning Function from Function ───────────────────────────────
        // Higher-order function: a function that returns another function
        Function<Integer, Function<Integer, Integer>> multiplier = x -> (y -> x * y);
        Function<Integer, Integer> triple = multiplier.apply(3);
        System.out.println(triple.apply(5));   // 15
        System.out.println(triple.apply(10));  // 30
    }
}
