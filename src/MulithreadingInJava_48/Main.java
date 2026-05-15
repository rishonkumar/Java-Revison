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