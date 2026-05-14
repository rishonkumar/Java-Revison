package MultithreadingInJava;

/*
 * Multithreading in Java
 *
 * Process : A program that is currently being executed / Application running in RAM
 *           RAM, CPU, TIME, RESOURCES are needed to run a process
 *           Multiple processes (p1, p2) run concurrently — each has its OWN memory space
 *
 * Thread  : Smallest sequence of instructions executed by CPU independently
 *           A "lightweight process" — a process can have multiple threads
 *           Threads SHARE the same heap memory within a process
 */
public class Demo {

    public static void main(String[] args) throws InterruptedException {

        // ── Way 1: Extend Thread class ────────────────────────────────────
        Thread t1 = new Thread() {
            @Override
            public void run() {
                for (int i = 1; i <= 5; i++) {
                    System.out.println("Thread-A → " + i);
                }
            }
        };

        // ── Way 2: Implement Runnable (PREFERRED — decouples task from thread) ──
        Runnable task = () -> {
            for (int i = 1; i <= 5; i++) {
                System.out.println("Thread-B → " + i);
            }
        };
        Thread t2 = new Thread(task);

        t1.start();   // ✅ creates a new thread — do NOT call run() directly!
        t2.start();

        // Output is INTERLEAVED — order is NOT guaranteed

        // ── Joining — wait for thread to finish ──────────────────────────
        t1.join();   // main thread waits for t1 to finish
        t2.join();   // main thread waits for t2 to finish
        System.out.println("Both threads done.");

        // ── Race Condition Demo ───────────────────────────────────────────
        RaceCounter unsafeCounter = new RaceCounter();
        SafeCounter safeCounter = new SafeCounter();

        Thread r1 = new Thread(() -> { for (int i = 0; i < 1000; i++) unsafeCounter.increment(); });
        Thread r2 = new Thread(() -> { for (int i = 0; i < 1000; i++) unsafeCounter.increment(); });

        Thread s1 = new Thread(() -> { for (int i = 0; i < 1000; i++) safeCounter.increment(); });
        Thread s2 = new Thread(() -> { for (int i = 0; i < 1000; i++) safeCounter.increment(); });

        r1.start(); r2.start(); r1.join(); r2.join();
        s1.start(); s2.start(); s1.join(); s2.join();

        System.out.println("Unsafe count (expected 2000): " + unsafeCounter.count); // usually < 2000
        System.out.println("Safe count   (expected 2000): " + safeCounter.count);   // always 2000

        // ── Daemon Thread ─────────────────────────────────────────────────
        Thread daemon = new Thread(() -> {
            while (true) {
                System.out.println("Daemon running...");
                try { Thread.sleep(500); } catch (InterruptedException e) { break; }
            }
        });
        daemon.setDaemon(true);  // JVM exits when only daemon threads remain
        // daemon.start();       // commented out to not pollute output
    }
}

// ── Race Condition: count++ is NOT atomic (read → increment → write) ──
class RaceCounter {
    int count = 0;
    void increment() { count++; }  // ❌ NOT thread-safe
}

// ── Synchronized: only one thread executes at a time ──────────────────
class SafeCounter {
    int count = 0;
    synchronized void increment() { count++; }  // ✅ thread-safe
}

// ── Thread lifecycle example ──────────────────────────────────────────
class LifecycleDemo extends Thread {
    @Override
    public void run() {
        System.out.println("State inside run(): " + Thread.currentThread().getState()); // RUNNABLE
        try {
            Thread.sleep(100);  // → TIMED_WAITING
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
