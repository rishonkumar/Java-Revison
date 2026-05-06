package LambdasFunctionalInterface_38;

import java.util.function.Predicate;

public class PredicateExample {



    static void main() {
        Predicate<Integer>isEven = (x -> x%2 == 0);
        System.out.println(isEven.test(4));
    }
}
