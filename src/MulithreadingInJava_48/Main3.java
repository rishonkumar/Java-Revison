package MulithreadingInJava_48;

public class Main3 {

    /*
    Knowing thrad id and thread name because it will be easy to debugnb in productioon
    we might know which thread id is creating the issue

    Difference between start and run
    start will start the thread  t1.start() under the hood run the run method
    if we do t1.run() without t1.start then thread never run

    can we start the same thread twice the answer is NO we get the exception Illegalexception

     */

    static void main() {
        //Main method alaways create a thread

        System.out.println(Thread.currentThread().getName()); // this is the main thread¡
        System.out.println(Thread.currentThread().getId()); // this id is depercaited

        //we can not do below on because it is non - static mehtod
       // Thread.getName();

        Thread t3 = new Thread(() -> {
            System.out.println("Name of my thread is " + Thread.currentThread().getName());
            System.out.println("Id of my thread is " + Thread.currentThread().getId());
        });

        Thread t2 = new Thread(() -> {
            System.out.println("Name of my thread is " + Thread.currentThread().getName());
            System.out.println("Id of my thread is " + Thread.currentThread().getId());
        });

        //if u dont do start then thread never starts
        t3.start(); // now this iwll start th thread
        t2.start();

        //whenver we will run the order can be different everytime so bascailly there no ordering




    }
}
