import java.util.concurrent.*;

public class CallableExampleV3 {
    private static Callable<Integer> createTask(int a, int b) {
        return () -> {
            try {
                Thread.sleep(a + b);
            } catch(InterruptedException ex) {
                ex.printStackTrace();
            }
            return a + b;
        };
    }
    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Long startTime = System.nanoTime();
        Future<Integer> final1 = executor.submit(createTask(1000, 200));
        Future<Integer> final2 = executor.submit(createTask(2000, 100));
        try {
            System.out.println(final1.get() + "  " + final2.get());
            Long endTime = System.nanoTime();
            System.out.println("Start Time" + endTime + " End Time " + startTime + " " + "duration " + (endTime - startTime));
            System.out.println("Total Duration  " + ((endTime - startTime) / (1000 * 1000 )) + " ms");
        } catch (Exception ex) {
            ex.printStackTrace();   
        }
        executor.shutdown();
    }
}