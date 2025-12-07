import java.util.concurrent.*;

public class ExecutionTimer {
    public static <T> T measure(Callable<T> task, String taskName) throws ExecutionException, InterruptedException {
        Long startTime = System.nanoTime();
        try {
            return task.call();
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
            Long endTime = System.nanoTime();
            System.out.println("Task: " + taskName + " takes " + ((endTime - startTime) / 1000000) + " ms");
        }
        return null;
    }
}