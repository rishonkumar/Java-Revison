package LambdasFunctionalInterface_38;

public class Demo {

    static void main() {

//        Calculate c = new Addition();
//        print(4,5,c);

        //Instead of writing like this we can do lamda expression
        print(4,5,(a,b) -> a+b);

//        Calculate c = (a,b) -> a + b;
//        print(4,5,c);
    }

    static void print(int a, int b, Calculate c) {
        System.out.println(c.calulate(a,b));
    }



}

@FunctionalInterface
interface Calculate {
    int calulate(int a, int b);
}


//class Addition implements Calculate {
//
//    @Override
//    public int calulate(int a, int b) {
//        return a+b;
//    }
//}

/*
Lamdab expression
4 core Funcitonal interface

    Function
    Consumer
    Supplier
    Predicate

    Function Interface -> Take i/p -> o/p
    interfae Function(T,R>) {
        R apply(T t)
    }

    Consumer
    It wil take T but it will not give any output
    T -> void
    Public interface Consumer<T> {
        void accept(T t)
    }

    Suplier -> opposite of COnsumer
    Supplier<T> no input but gives some output

    pulic interface Suplier<T> {
            T.get();
    }

    Predicate Takes input and returns boolean

    public interface Predicate<T> {

            boolean test(T t)

    }


    Primitive Functional Interface




*/