import java.util.concurrent.*;


public class CallableExample {
    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Long startTime = System.nanoTime();
        int delay = 1500;
        Callable<Integer> task = () -> {
            try {
                Thread.sleep(delay);
            } catch(Exception ex) {
                ex.printStackTrace();
            }
            return 42;
        };
        Future<Integer> result = executor.submit(task);
        try {
            int val = result.get();
            System.out.println("Result " + val + " after " + ((System.nanoTime() - startTime) / (1000 * 1000)) + " ms");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }
}