package LambdasFunctionalInterface_38;

import java.util.function.Supplier;

public class SupplierExample {

    static void main() {

        Supplier<Double>random = () -> Math.random();

        System.out.println(random.get());
    }
}
