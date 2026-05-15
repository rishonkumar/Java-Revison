package MulithreadingInJava_48;

public class ThreadLifeCycle {
    /*
     * ==========================================
     *          THREAD LIFE CYCLE DIAGRAM
     * ==========================================
     * 
     *                  [ NEW ]
     *                     |  start()
     *                     v
     *               [ RUNNABLE ] <------------------+
     *                     |                         |
     *               Scheduler picks                 |
     *                     |                         |
     *                     v                         |
     *                [ RUNNING ]                    |
     *                 |   |   |                     |
     *       +---------+   |   +---------+           | (yield(), sleep() over,
     *       |             |             |           |  lock acquired,
     *  sleep(), wait(),   | run()      wait()       |  notify())
     *  join()        completes      for lock        |
     *       |             |             |           |
     *       v             v             v           |
     * [ TIMED_WAITING ]   |        [ BLOCKED ]      |
     * [ WAITING ]         |             |           |
     *       |             |             |           |
     *       +-------------|-------------+-----------+
     *                     v
     *               [ TERMINATED ]
     *
     * ------------------------------------------
     * 
     * STATES EXPLANATION:
     * 1. NEW: Thread is created but start() is not called yet.
     * 2. RUNNABLE: start() is called. Thread is ready to run, waiting for CPU time.
     * 3. RUNNING: Thread scheduler allocates CPU to the thread.
     * 4. BLOCKED: Thread is waiting to acquire a monitor lock to enter a synchronized block.
     * 5. WAITING: Thread is waiting indefinitely for another thread to perform a particular action (e.g., join, wait).
     * 6. TIMED_WAITING: Thread is waiting for a specified time (e.g., sleep, wait(ms)).
     * 7. TERMINATED: Thread has completed execution.
     */

    public static void main(String[] args) throws InterruptedException {
        // 1. NEW State
        Thread t1 = new Thread(() -> {
            try {
                // Thread will sleep for 2 seconds (TIMED_WAITING)
                Thread.sleep(2000); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        
        System.out.println("1. State after creating: " + t1.getState()); // NEW

        // 2. RUNNABLE State
        t1.start();
        System.out.println("2. State after start(): " + t1.getState()); // RUNNABLE (or RUNNING)

        // 3. TIMED_WAITING State
        // Wait a bit in the main thread to ensure t1 enters sleep()
        Thread.sleep(100); 
        System.out.println("3. State while t1 is sleeping: " + t1.getState()); // TIMED_WAITING

        // 4. WAITING State example
        Thread t2 = new Thread(() -> {
            try {
                t1.join(); // t2 waits indefinitely for t1 to finish
            } catch (InterruptedException e) {}
        });
        t2.start();
        Thread.sleep(100); // Give t2 time to execute t1.join()
        System.out.println("4. State of t2 while waiting for t1 to join: " + t2.getState()); // WAITING

        // 5. BLOCKED State example
        Object lock = new Object();
        
        Thread t3 = new Thread(() -> {
            synchronized (lock) {
                try { Thread.sleep(3000); } catch (Exception e) {}
            }
        });
        
        Thread t4 = new Thread(() -> {
            synchronized (lock) {
                System.out.println("T4 acquired lock");
            }
        });

        t3.start();
        Thread.sleep(100); // Ensure t3 acquires the lock first
        t4.start();
        Thread.sleep(100); // Ensure t4 is trying to acquire the already-held lock
        System.out.println("5. State of t4 trying to enter locked block: " + t4.getState()); // BLOCKED

        // 6. TERMINATED State
        t1.join(); // main thread waits for t1 to finish
        System.out.println("6. State of t1 after it finishes: " + t1.getState()); // TERMINATED
    }
}
