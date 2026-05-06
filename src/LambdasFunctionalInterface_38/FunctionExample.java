package LambdasFunctionalInterface_38;
import java.util.function.*;
public class FunctionExample {

    static void main() {
        Function<Integer,Integer>square = x -> x*x;

        System.out.println(square.apply(5));
    }
}
