package ThreadMethods_49;

public class Main {

    static void main() throws InterruptedException {

        System.out.println("Main threads starts");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }

        System.out.println("Main threads sleeps");

        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
            }
            System.out.println("Thread 0 starts");
        });

        t1.start();

        t1.join(); // let the t1 thread first complete its excecution

        // t1.join(2000); wait for 2 secs to wait

        System.out.println("Main thread ends");

        // Yield
        Thread t5 = new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                System.out.println("T1 " + i);
                Thread.yield(); // now this gives priority to t2 to complete first
            }
        });

        Thread t6 = new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                System.out.println("T1 " + i);
            }
        });

        t5.start();
        t6.start();

        Thread t8 = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) { // run till the thread is interrpted
                System.out.println("RUnning");
            }
        });
        t8.start();
        t8.interrupt();

        // alive
        Thread t12 = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
            }
        });
        System.out.println(t12.isAlive()); // false

        t12.start();

        System.out.println(t12.isAlive()); // true

        Thread t16 = new Thread(() -> {
            System.out.println("Custom thread running");
        });

        t16.start();

        System.out.println(t16.getPriority()); // 5

        t16.setPriority(10);

        System.out.println(t16.getPriority()); // 10

        // Daemon threads
        // once the main thread is over then the particluar thread should be over
        Thread t88 = new Thread(() -> {
            while (true) {
                System.out.println("Running");
            }
        });

        t88.setDaemon(true);
        t88.start();

        // now if we run there will be no running because our main thread is over let
        // sleep the main
        // thread for 2s so we can see the logs if we dont use setDaemion then we will
        // keep getting
        // the running even tho oour main thread is closed

        Thread.sleep(2000);
    }
}

/*
 * Thread important methods
 * sleep()(millisceobds) -> TIMED_WAITING
 * 
 * RUNNING --> TIMED_WAITING ----> RUNNABLE
 * 
 * Join
 * Main thread => WAITING
 * t1 thread -> RUNNABLE -> Terminated
 * Main thread -> Waiting -> Runnable -> Terminated
 * 
 * yield() I am waillingto give my spu time to someone else with same priority
 * and that wants to run
 * 1. OS can reject this we dont use this much in production
 * 2 It i slike a suggestion to the OS
 * 3 Current thread does not go to waiting , timed waiting, blocked
 * It does only go to runnable state
 * 
 * 
 * Thread -> interrupt flag (default true)
 * t1.interrupt() --> Sends a signal to t1 thread that is should strop doing
 * whats its doing
 * 
 * We can gracefully handle
 * You can make a thread run untill a condition
 * Cancelling a long running tasks
 * Use to stop Thread pool
 * 
 * isInterrupted --> return interrupt flag value
 * interrupted() -> Return interrupt flag value but also set it back to false
 * 
 * 
 * sleep , join , wait : TIMED WAITING , WAITING ---> interrupt()
 * 
 * isAlive() check cuurent thread is alive or not (start ----- terminate)
 * 
 * 
 * cuurentThread -> reference of cuurent running thread
 * 
 * Thread Priority ->
 * MAX PRIORITY = 10
 * MIN _ PRIority = 1
 * NORM prioty = 5
 * 
 * Depends on OS
 * may respect priority
 * may partially respect
 * may not at all
 * 
 * Daemon threads Background running threads
 * 
 * Threads -> User threads, Daemon threads
 * Stops immediatlyey once main thread is completed 
 * 
 * Garbage collection --> Daemon thread 
 * 
 * 
 * 
 * 
 * 
 */
