package LambdasFunctionalInterface_38;

import java.util.ArrayList;
import java.util.List;
import java.util.function.*;

public class ConsumerExample {

    static void main() {
        Consumer<Integer>print = x -> System.out.println(x);

        print.accept(6);

//        for each example  it takes consumer

        List<Integer>list = new ArrayList<>(List.of(1,2,3,4,4));

        for(Integer i : list) {
            System.out.println(i);
        }

        //same can be done is list interanlly it use for loop

        list.forEach(x -> System.out.println(x));
    }
}
