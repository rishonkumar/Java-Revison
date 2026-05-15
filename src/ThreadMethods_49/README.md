# Thread Methods in Java

> Mastering the core methods of the `Thread` class is essential for controlling thread execution, coordinating tasks, and managing the thread lifecycle.

---

## 📖 Theory & Core Methods

### Thread Lifecycle State Changes
*   **`sleep(ms)`**: `RUNNING` → `TIMED_WAITING`
*   **`join()`**: Main thread goes to `WAITING` until the target thread is `TERMINATED`.
*   **`yield()`**: `RUNNING` → `RUNNABLE` (Suggests to the OS to give CPU time to other threads of the same priority).

### 1. `sleep()` and `join()`
*   **`Thread.sleep(ms)`**: Pauses the current thread for the specified milliseconds. It does NOT release any monitor locks.
*   **`t1.join()`**: The *calling* thread (usually `main`) pauses execution and waits for thread `t1` to finish (terminate) before continuing. You can also pass a timeout `t1.join(2000)`.

### 2. `yield()`
*   A hint to the thread scheduler that the current thread is willing to yield its current use of a processor.
*   The scheduler is free to ignore this hint.
*   It moves the thread from `RUNNING` to `RUNNABLE`.
*   **Note:** Rarely used in production code.

### 3. `interrupt()` and `isInterrupted()`
*   **`t1.interrupt()`**: Sends a signal to a thread to stop what it is doing. It sets the thread's interrupt flag to `true`.
*   If the thread is currently blocked in `sleep()`, `wait()`, or `join()`, it wakes up immediately and throws an `InterruptedException`.
*   **`isInterrupted()`**: Checks if the thread's interrupt flag is set to true.
*   **`Thread.interrupted()`**: Static method that checks the flag *and* clears it back to false.

### 4. `isAlive()`
*   Returns `true` if the thread has been started and has not yet died (terminated).
*   Returns `false` if the thread is `NEW` (not started) or `TERMINATED`.

### 5. Thread Priority
*   Priorities range from `Thread.MIN_PRIORITY` (1) to `Thread.MAX_PRIORITY` (10). Default is `NORM_PRIORITY` (5).
*   **`setPriority(int)`** / **`getPriority()`**
*   **Warning:** Thread priority is highly OS-dependent. The JVM maps these to OS priorities, but the OS may completely ignore them. Never rely on priorities for program correctness.

### 6. Daemon Threads
*   A daemon thread is a background thread (e.g., Garbage Collector).
*   The JVM terminates itself when all user (non-daemon) threads finish execution. It will kill daemon threads immediately to shut down.
*   **`t.setDaemon(true)`**: Must be called *before* `start()`.

---

## 🧪 Code Walkthroughs

### `Main.java` — Core Thread Methods

```java
// 1. Sleep and Join
Thread t1 = new Thread(() -> {
    try { Thread.sleep(2000); } catch (Exception e) {}
    System.out.println("Thread 1 finished");
});
t1.start();
t1.join(); // Main thread waits here until t1 finishes

// 2. Yield
Thread t5 = new Thread(() -> {
    for (int i = 1; i <= 10; i++) {
        System.out.println("T5: " + i);
        Thread.yield(); // Hints OS to let T6 run
    }
});
Thread t6 = new Thread(() -> { ... });
t5.start(); t6.start();

// 3. Interrupt
Thread t8 = new Thread(() -> {
    // Graceful stopping using the interrupt flag
    while (!Thread.currentThread().isInterrupted()) { 
        System.out.println("Running...");
    }
});
t8.start();
t8.interrupt(); // Sends interrupt signal, loop condition becomes false

// 4. isAlive
Thread t12 = new Thread(() -> { /* ... */ });
System.out.println(t12.isAlive()); // false (not started)
t12.start();
System.out.println(t12.isAlive()); // true (running)

// 5. Priority
Thread t16 = new Thread(() -> System.out.println("Custom thread"));
t16.setPriority(10); // MAX_PRIORITY
t16.start();

// 6. Daemon Thread
Thread daemon = new Thread(() -> {
    while (true) System.out.println("Background logging...");
});
daemon.setDaemon(true); 
daemon.start();
// If the main thread ends here, the JVM exits immediately. 
// The daemon thread is forcefully stopped.
```

---

## ❓ Critical Interview Questions
*(These have also been added to the master `INTERVIEW_QUESTIONS.md`)*

### Q1. What is the difference between `sleep()` and `yield()`?
*   `sleep(ms)`: Forces the thread to pause for a specific time. State becomes `TIMED_WAITING`.
*   `yield()`: Suggests the OS to let other threads of the same priority run. State becomes `RUNNABLE`. The OS can completely ignore `yield()`.

### Q2. How do you gracefully stop a running thread?
> Never use `Thread.stop()` (it is deprecated and unsafe). Instead, use the **interrupt flag**.
```java
Thread t = new Thread(() -> {
    while (!Thread.currentThread().isInterrupted()) {
        // Do work
    }
});
t.start();
t.interrupt(); // Safely signals the thread to stop
```

### Q3. What happens if you call `interrupt()` on a sleeping thread?
> It will immediately wake up and throw an `InterruptedException`, and its interrupt flag will be cleared (set back to false).

### Q4. Can we rely on Thread Priority for execution order?
> **No.** Thread scheduling depends entirely on the underlying OS. Some operating systems completely ignore thread priorities. It should only be used as a hint, never for correctness.

---

## 📂 Files
| File | What it demonstrates |
|------|---------------------|
| `Main.java` | Practical examples of `sleep`, `join`, `yield`, `interrupt`, `isAlive`, priority, and daemon threads. |


---

## 💻 Full Source Code

> Below is the complete, beautified source code for all examples in this topic.

### Main.java

```java
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

```

