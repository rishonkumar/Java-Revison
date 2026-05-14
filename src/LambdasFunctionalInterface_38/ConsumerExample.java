package LambdasFunctionalInterface_38;

import java.util.function.Consumer;
import java.util.function.BiConsumer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
 * Consumer<T> — T → void
 * Abstract method: void accept(T t)
 *
 * BiConsumer<T,U> — (T, U) → void
 * Abstract method: void accept(T t, U u)
 *
 * andThen() — chain two consumers (execute both, one after another)
 *
 * Key use: forEach loops, logging, side effects
 */
public class ConsumerExample {

    public static void main(String[] args) {

        // ── Basic Consumer ─────────────────────────────────────────────────
        Consumer<Integer> print = x -> System.out.println("Value: " + x);
        print.accept(6);  // Value: 6

        Consumer<String> shout = s -> System.out.println(s.toUpperCase() + "!");
        shout.accept("hello");  // HELLO!

        // ── forEach — internally uses Consumer ────────────────────────────
        List<Integer> list = new ArrayList<>(List.of(1, 2, 3, 4, 5));

        // Old way:
        for (Integer i : list) { System.out.print(i + " "); }
        System.out.println();

        // Lambda way:
        list.forEach(x -> System.out.print(x * x + " "));  // 1 4 9 16 25
        System.out.println();

        // Method reference way:
        list.forEach(System.out::println);

        // ── Consumer andThen — chain two consumers ────────────────────────
        Consumer<String> log    = s -> System.out.println("[LOG] " + s);
        Consumer<String> audit  = s -> System.out.println("[AUDIT] " + s);

        Consumer<String> logAndAudit = log.andThen(audit);
        logAndAudit.accept("User login");
        // [LOG] User login
        // [AUDIT] User login

        // ── BiConsumer — two inputs, no return ────────────────────────────
        BiConsumer<String, Integer> printPair = (name, age) ->
            System.out.println(name + " is " + age + " years old");
        printPair.accept("Rishon", 22);  // Rishon is 22 years old

        // BiConsumer with Map.forEach
        Map<String, Integer> scores = Map.of("Alice", 95, "Bob", 87, "Charlie", 92);
        scores.forEach((name, score) ->
            System.out.println(name + " → " + score));
    }
}
