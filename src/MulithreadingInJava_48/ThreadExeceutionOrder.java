package MulithreadingInJava_48;

public class ThreadExeceutionOrder {
    /*
    Execution Order -> Non determinism

     */

    static void main() {

        // 1 to 100 event number
        Thread t1 = new Thread(() -> {
            for(int i = 1 ; i < 100 ; i++) {
                if(i % 2 == 0) {
                    System.out.println("T1" + i);
                }
            }
        });

        Thread t2 = new Thread(() -> {
            for(int i = 1 ; i < 100 ; i++) {
                if(i % 2 != 0) {
                    System.out.println("T2" + i);
                }
            }
        });

        t1.start();
        t2.start();

        // so here the ordering will be random its not necessary t1 overs and t2 goes  so basically no ordering
        // everytime the ouput eill br diffeent
        // the reason is there is CPU and register there will be 2 cpu and cpu has
        // sechduling function(round robin, time slicing, pritortu bases) may be cpu 1 take t1 and cpu take t2 then it will be parallerlism and may be context sitiatuon happens

    }


}
