# Multithreading in Java

> Concurrency is a critical topic for senior interviews. Covers processes, threads, thread lifecycle, synchronization, and common pitfalls.

---

## 📖 Theory

### Process vs Thread

```
Process (e.g., Chrome)
├── Thread 1 → Rendering
├── Thread 2 → Network requests
└── Thread 3 → JavaScript execution

Each process has its own memory space.
Threads within a process SHARE the same memory (Heap).
```

| | Process | Thread |
|-|---------|--------|
| Memory | Own memory space | Shares heap with other threads |
| Weight | Heavy (more resources) | Light (less overhead) |
| Communication | IPC (complex) | Shared memory (simpler but risky) |
| Creation cost | High | Low |

---

### Thread Lifecycle

```
NEW → RUNNABLE → RUNNING → [BLOCKED/WAITING/TIMED_WAITING] → TERMINATED
        ↑_____________↓
```

| State | Description |
|-------|-------------|
| `NEW` | Thread created, `start()` not called |
| `RUNNABLE` | Ready to run, waiting for CPU |
| `RUNNING` | Actively executing |
| `BLOCKED` | Waiting for a monitor lock |
| `WAITING` | Waiting indefinitely (`wait()`, `join()`) |
| `TIMED_WAITING` | Waiting for a timeout (`sleep()`, `wait(ms)`) |
| `TERMINATED` | Finished execution |

---

### Two Ways to Create Threads

```java
// Way 1: Extend Thread class
class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("Thread running: " + Thread.currentThread().getName());
    }
}
MyThread t = new MyThread();
t.start();  // DON'T call run() directly — that runs on same thread!

// Way 2: Implement Runnable (PREFERRED)
class MyTask implements Runnable {
    @Override
    public void run() {
        System.out.println("Task running");
    }
}
Thread t = new Thread(new MyTask());
t.start();

// Way 3: Lambda (most concise)
Thread t = new Thread(() -> System.out.println("Lambda thread"));
t.start();
```

---

### The Race Condition Problem

```java
class Counter {
    int count = 0;

    void increment() {
        count++;  // NOT atomic! → READ → INCREMENT → WRITE (3 ops)
    }
}
// If two threads call increment() simultaneously:
// Both read count=0, both write 1 → count=1 instead of 2! ❌
```

### Synchronization — Fixing Race Conditions

```java
class Counter {
    int count = 0;

    synchronized void increment() {
        count++;  // Only one thread can enter at a time
    }
}
```

---

## 🧪 Code Walkthroughs

### `Demo.java` — Concepts

```java
// Process: program that is currently being executed
// RAM, CPU, time, resources are needed to run a process
// Multiple processes (p1, p2) run concurrently in RAM

// Thread: smallest sequence of instructions executed independently
// A process can have multiple threads
// Threads are lightweight processes
```

### Enhanced Threading Demo

```java
// Demonstrating thread interleaving
class PrintTask implements Runnable {
    private String name;
    PrintTask(String name) { this.name = name; }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println(name + " → " + i);
        }
    }
}

Thread t1 = new Thread(new PrintTask("Thread-A"));
Thread t2 = new Thread(new PrintTask("Thread-B"));
t1.start();
t2.start();
// Output is interleaved — order NOT guaranteed!
```

---

## ❓ Critical Interview Questions

### Q1. What is the difference between `start()` and `run()`?

```java
Thread t = new Thread(() -> System.out.println("Hello"));
t.run();   // ❌ Runs on CURRENT thread — NOT a new thread (just a method call)
t.start(); // ✅ Creates NEW thread and calls run() on that new thread
```

---

### Q2. What is a race condition? How do you fix it?
> A **race condition** occurs when multiple threads access shared data simultaneously and the result depends on thread execution order.

**Fix:**
1. `synchronized` keyword (intrinsic locks)
2. `java.util.concurrent.locks.ReentrantLock`
3. `AtomicInteger`, `AtomicLong` from `java.util.concurrent.atomic`
4. Use thread-safe data structures (`ConcurrentHashMap`, `CopyOnWriteArrayList`)

---

### Q3. What is the difference between `synchronized` method and `synchronized` block?

```java
// Synchronized method — locks the ENTIRE object
synchronized void increment() { count++; }

// Synchronized block — locks only the critical section (more granular)
void increment() {
    // non-critical code runs without lock
    synchronized(this) {
        count++;  // only this part is locked
    }
    // non-critical code here too
}
```
> **Prefer synchronized block** — less contention, better performance.

---

### Q4. What is a deadlock?
> Two threads each hold a lock the other needs → both wait forever.

```
Thread 1: holds Lock A, wants Lock B
Thread 2: holds Lock B, wants Lock A
→ DEADLOCK
```

**Prevention:**
- Always acquire locks in the **same order**
- Use `tryLock()` with timeout
- Minimize the scope of locks

---

### Q5. What is the difference between `wait()` and `sleep()`?
| | `wait()` | `sleep()` |
|-|---------|-----------|
| Defined in | `Object` | `Thread` |
| Releases lock? | ✅ Yes | ❌ No |
| Wakes up when | `notify()` / `notifyAll()` called | Timer expires |
| Must be in `synchronized`? | ✅ Yes | ❌ No |

---

### Q6. What is a daemon thread?
> A thread that runs **in the background** to serve other threads. JVM exits when only daemon threads remain.

```java
Thread t = new Thread(() -> { /* background task */ });
t.setDaemon(true);  // must be set before start()
t.start();
// JVM shuts down if only this daemon thread remains
```
Examples: GC thread, timer threads, IntelliJ file watcher.

---

### Q7. What is `volatile` keyword?
> Ensures a variable is always **read from main memory**, not from thread's local cache. Solves **visibility** problem (not atomicity).

```java
volatile boolean running = true;

// Thread 1:
while (running) { ... }

// Thread 2:
running = false;  // With volatile: Thread 1 sees this change immediately
                  // Without volatile: Thread 1 may use stale cached value
```

---

### Q8. TRICKY: What happens if you call `start()` twice on the same Thread?

```java
Thread t = new Thread(() -> System.out.println("Hello"));
t.start();
t.start();  // ❌ Throws IllegalThreadStateException!
```
> Once a thread is terminated, it cannot be restarted. Create a new Thread instance.

---

### Q9. What is `ExecutorService` and why use it over raw threads?

```java
// Raw thread creation is expensive — don't create new threads for each task
ExecutorService pool = Executors.newFixedThreadPool(5);  // reuse 5 threads

pool.submit(() -> System.out.println("Task 1"));
pool.submit(() -> System.out.println("Task 2"));

pool.shutdown();  // graceful shutdown — waits for all tasks to finish
// pool.shutdownNow()  // forceful — interrupts running tasks
```

**Thread Pool Types:**
| Pool | Description |
|------|-------------|
| `newFixedThreadPool(n)` | Fixed n threads |
| `newCachedThreadPool()` | Grows as needed, reuses idle threads |
| `newSingleThreadExecutor()` | One thread, sequential |
| `newScheduledThreadPool(n)` | For delayed/periodic tasks |

---

## 📂 Files
| File | What it demonstrates |
|------|---------------------|
| `Demo.java` | Process vs Thread concepts (with code added) |
