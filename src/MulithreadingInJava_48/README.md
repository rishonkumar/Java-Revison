# Multithreading in Java (Part 2)

> Continued concepts on Thread creation, Execution Order, and the full Thread Lifecycle.

---

## 📖 Theory & Core Concepts

### 1. Thread vs Runnable (`Main.java` & `Main2.java`)
- **`Thread` class**: You can create a thread by extending the `Thread` class.
- **`Runnable` interface**: You can implement `Runnable` and pass it to a `Thread`. This is the preferred way because it separates the task from the runner, and allows your class to extend another class if needed.

### 2. `start()` vs `run()` (`Main3.java`)
- Calling `t.start()` asks the JVM and OS to allocate a new thread and then it executes `run()`.
- Calling `t.run()` just executes the method on the current thread, completely defeating the purpose of multithreading.
- A thread cannot be started twice (`IllegalThreadStateException`).

### 3. Execution Order (`ThreadExeceutionOrder.java`)
- Multithreading introduces **non-determinism**. The order of execution between two concurrent threads is unpredictable and depends entirely on the OS scheduler (round-robin, priority, time-slicing).

### 4. Thread Lifecycle (`ThreadLifeCycle.java`)
- `NEW`: Created but not started.
- `RUNNABLE`: Started and waiting for CPU.
- `RUNNING`: Executing on CPU.
- `TIMED_WAITING`: Sleeping or waiting with a timeout.
- `WAITING`: Waiting indefinitely (e.g., `join()`).
- `BLOCKED`: Waiting to acquire a synchronized lock.
- `TERMINATED`: Execution completed.

---


---

## 💻 Full Source Code

> Below is the complete, beautified source code for all examples in this topic.

### Main.java

```java
package MulithreadingInJava_48;

/*
 * ==========================================
 *        CREATING THREADS IN JAVA
 * ==========================================
 * 
 * 1. Extending `Thread` Class
 * 2. Implementing `Runnable` Interface (Preferred)
 * 
 * HIERARCHY:
 * 
 *     <<interface>>
 *      Runnable         <--- Defines a task: void run()
 *         ^
 *         | implements
 *       Thread          <--- Defines a thread (Runner)
 *         ^
 *         | extends
 *      MyThread         <--- Custom thread class
 * 
 * WHY IS RUNNABLE PREFERRED?
 * 1. Separation of concerns (Task vs Runner).
 * 2. Reusability (Pass the same Runnable to multiple threads).
 * 3. Multiple Inheritance (Java doesn't support multiple class inheritance. 
 *    If you extend Thread, you can't extend anything else!).
 */
public class Main {

    static void main() {
        // ── Method 1: Extending Thread ──
        MyThread t1 = new MyThread();
        
        // t1.start() -> JVM asks OS to create a new thread -> Allocates Stack/PC space -> Executes run()
        t1.start(); 
    }
}

class MyThread extends Thread {

    @Override
    public void run() {
        System.out.println("Thread is running");
    }
}
```

### Main2.java

```java
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

```

### Main3.java

```java
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

```

### ThreadExeceutionOrder.java

```java
package MulithreadingInJava_48;

/*
 * ==========================================
 *     THREAD EXECUTION NON-DETERMINISM
 * ==========================================
 * 
 * Thread execution order is completely up to the OS Scheduler.
 * 
 * Example Execution Interleaving:
 * 
 * Time |  Thread-1 (Even)  |  Thread-2 (Odd)
 * -----|-------------------|------------------
 *  t1  |    Prints T1:2    |
 *  t2  |    Prints T1:4    |
 *  t3  |    [CONTEXT SW]   |    Prints T2:1
 *  t4  |                   |    Prints T2:3
 *  t5  |    Prints T1:6    |    [CONTEXT SW]
 * 
 * The output changes EVERY time you run the code because the 
 * OS uses Time Slicing / Round-Robin scheduling to swap threads 
 * rapidly on the CPU cores.
 */
public class ThreadExeceutionOrder {

    static void main() {

        // Thread 1: Prints Even numbers (1 to 100)
        Thread t1 = new Thread(() -> {
            for(int i = 1 ; i < 100 ; i++) {
                if(i % 2 == 0) {
                    System.out.println("T1: " + i);
                }
            }
        });

        // Thread 2: Prints Odd numbers (1 to 100)
        Thread t2 = new Thread(() -> {
            for(int i = 1 ; i < 100 ; i++) {
                if(i % 2 != 0) {
                    System.out.println("T2: " + i);
                }
            }
        });

        t1.start();
        t2.start();

        // There is no guarantee which thread finishes first.
    }
}

```

### ThreadLifeCycle.java

```java
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

```

