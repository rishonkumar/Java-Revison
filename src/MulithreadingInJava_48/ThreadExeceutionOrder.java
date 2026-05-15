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
