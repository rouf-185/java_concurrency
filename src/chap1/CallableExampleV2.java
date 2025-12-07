import java.util.concurrent.*;


class ImplementedCallable implements Callable<Integer> {
    private final int a;
    private final int b;
    public ImplementedCallable(int a, int b) {
        this.a = a;
        this.b = b;
    }
    @Override
    public Integer call() {
        try {
            Thread.sleep(a + b);
        } catch(InterruptedException ex) {
            ex.printStackTrace();
        }
        return a + b;
    }
}

public class CallableExampleV2 {
    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Long startTime = System.nanoTime();
        Future<Integer> final1 = executor.submit(new ImplementedCallable(1000, 200));
        Future<Integer> final2 = executor.submit(new ImplementedCallable(2000, 100));
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