package LambdasFunctionalInterface_38;

import java.util.function.Function;

/*
 * Functional Composition
 *
 * Math: f(x) = x + 2,  g(x) = x * 3
 *
 * andThen : f.andThen(g) → apply f FIRST, then g   → g(f(x))
 * compose : f.compose(g) → apply g FIRST, then f   → f(g(x))
 *
 * Memory trick: andThen = left-to-right (like reading a sentence)
 *               compose = right-to-left (like math function notation)
 */
public class FunctionalComposition {

    public static void main(String[] args) {

        Function<Integer, Integer> add2      = x -> x + 2;
        Function<Integer, Integer> multiply3 = x -> x * 3;

        // ── Direct composition ─────────────────────────────────────────────
        // (2 + 2) * 3 = 12
        int manual = multiply3.apply(add2.apply(2));
        System.out.println("Manual compose: " + manual);  // 12

        // ── andThen: add2 first, THEN multiply3 ───────────────────────────
        // (2 + 2) * 3 = 12
        int andThenResult = add2.andThen(multiply3).apply(2);
        System.out.println("andThen (add first, multiply second): " + andThenResult);  // 12

        // ── compose: multiply3 first, THEN add2 ───────────────────────────
        // (2 * 3) + 2 = 8
        int composeResult = add2.compose(multiply3).apply(2);
        System.out.println("compose (multiply first, add second): " + composeResult);  // 8

        // ── Chaining multiple andThen ──────────────────────────────────────
        Function<Integer, String> pipeline =
            add2.andThen(multiply3)
                .andThen(x -> x - 1)
                .andThen(x -> "Result: " + x);

        System.out.println(pipeline.apply(2));  // Result: 11  → (2+2)*3 - 1 = 11

        // ── Real-world use case: data transformation pipeline ──────────────
        Function<String, String> trim    = String::trim;
        Function<String, String> toLower = String::toLowerCase;
        Function<String, String> addHash = s -> "#" + s;

        Function<String, String> normalizeTag = trim.andThen(toLower).andThen(addHash);
        System.out.println(normalizeTag.apply("  Java  "));  // #java
        System.out.println(normalizeTag.apply(" SPRING "));  // #spring
    }
}
