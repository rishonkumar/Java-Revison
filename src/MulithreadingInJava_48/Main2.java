package MulithreadingInJava_48;

import java.sql.SQLOutput;

public class Main2 {
    //THreads using runnable

   // Runnable is functional interface

    static void main() {
        MyRUnnable myRUnnable = new MyRUnnable();
        Thread t1 = new Thread();
        t1.start();

        //if u use function interface then no need to write below class MyRunnable
        Thread t2 = new Thread(() -> System.out.println("Thread is running"));

        t2.start();
    }

}

class MyRUnnable implements Runnable {
    @Override
    public void run() {
        System.out.println("Running");
    }
}
