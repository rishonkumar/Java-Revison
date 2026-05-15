package MulithreadingInJava_48;

public class Main {

    /*
   Threads can be createe using to wasyts threads and runnable

   Each threads has its own stacl and PC space
    Process has multuple threads together
    t1.start () -> JVM asks os to create a new thread --> Thread gets stack/PC space
    Thread exceutre run()

    Using thread we definina a thread

    Using runnable degining a task

    Heriachy -> Runnable <Interface>
                <class> Thread
                MyThread extebdes Thred


                In cae of my runnable we need to crate a thread and then run

                Better wa of doing custom is using MyRunnable
                reason sepration of concern =? we wrill define task
                Rreusablitlity => We can give to other threads t1 t2 t3
                Multiple Inhertiance


     */

    static void main() {
        // Thread using extending thread
        MyThread t1 = new MyThread();
        t1.start(); // to start the thread
    }
}


class MyThread extends Thread {

    @Override
    public void run() {
        System.out.println("Thread is running");
    }
}