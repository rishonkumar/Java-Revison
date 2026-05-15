package ThreadMethods_49;

/*
 * ==========================================
 *        CORE THREAD METHODS IN JAVA
 * ==========================================
 * 
 * 1. sleep(ms) : Pauses current thread (RUNNING -> TIMED_WAITING)
 * 2. join()    : Waits for a thread to die (Caller goes to WAITING)
 * 3. yield()   : Suggests OS to pause current thread and give CPU to another 
 *                thread of same priority (RUNNING -> RUNNABLE)
 * 4. interrupt(): Sets the interrupt flag. Gracefully stops a thread, 
 *                 especially if it's sleeping or waiting.
 * 5. isAlive() : Returns true if thread has been started and not yet dead.
 * 6. setDaemon(): Background threads (like Garbage Collector). JVM exits 
 *                 when ONLY Daemon threads remain. MUST be set before start().
 * 7. setPriority(): 1 (MIN), 5 (NORM), 10 (MAX). Depends heavily on OS.
 * 
 * INTERRUPT FLAG WORKFLOW:
 *    t.interrupt() ----> Sets flag to true
 *    t.isInterrupted() -> Reads flag (returns true/false)
 *    Thread.interrupted()-> Reads flag AND resets it to false
 */
public class Main {

    static void main(String[] args) throws InterruptedException {

        System.out.println("Main thread starts");

        // ── 1. sleep() ────────────────────────────────────────────────────
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.out.println("Main interrupted");
        }
        System.out.println("Main thread woke up");

        // ── 2. join() ─────────────────────────────────────────────────────
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {}
            System.out.println("T1 finishes heavy work");
        });

        t1.start();
        t1.join(); // Main thread WAITS here until t1 finishes
        // t1.join(2000); // Wait for max 2 seconds

        // ── 3. yield() ────────────────────────────────────────────────────
        Thread t5 = new Thread(() -> {
            for (int i = 1; i <= 3; i++) {
                System.out.println("Yield Thread -> " + i);
                Thread.yield(); // Suggests OS to let t6 run
            }
        });

        Thread t6 = new Thread(() -> {
            for (int i = 1; i <= 3; i++) {
                System.out.println("Normal Thread -> " + i);
            }
        });

        t5.start();
        t6.start();

        // ── 4. interrupt() ────────────────────────────────────────────────
        Thread t8 = new Thread(() -> {
            // Run gracefully until interrupted
            while (!Thread.currentThread().isInterrupted()) { 
                // System.out.println("Running...");
            }
            System.out.println("T8 was gracefully interrupted!");
        });
        t8.start();
        t8.interrupt(); // Sets the interrupt flag to true

        // ── 5. isAlive() ──────────────────────────────────────────────────
        Thread t12 = new Thread(() -> {
            try { Thread.sleep(100); } catch (Exception e) {}
        });
        System.out.println("T12 Alive before start? " + t12.isAlive()); // false
        t12.start();
        System.out.println("T12 Alive after start? " + t12.isAlive());  // true

        // ── 6. Thread Priority ────────────────────────────────────────────
        Thread t16 = new Thread(() -> {
            System.out.println("Priority Thread running");
        });
        System.out.println("Default Priority: " + t16.getPriority()); // 5
        t16.setPriority(Thread.MAX_PRIORITY); // 10
        System.out.println("New Priority: " + t16.getPriority());     // 10
        t16.start();

        // ── 7. Daemon Threads ─────────────────────────────────────────────
        Thread daemonThread = new Thread(() -> {
            while (true) {
                // System.out.println("Daemon running in background...");
            }
        });

        daemonThread.setDaemon(true); // Must be called BEFORE start()
        daemonThread.start();

        // When the Main thread (user thread) ends, the JVM shuts down.
        // It does NOT wait for Daemon threads to finish their infinite loops!
        System.out.println("Main thread ends");
    }
}
