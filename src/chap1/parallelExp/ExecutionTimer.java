import java.util.concurrent.*;
public class ExecutionTimer {
    public static <T> T measureExecutionTime(Callable<T> task, String taskName) throws Exception {
        long startTime = System.nanoTime();
        try {
            return task.call();
        } finally {
            long endTime = System.nanoTime();
            long durationInMillis = (endTime - startTime) / 1_000_000;
            System.out.println(taskName + " executed in " + durationInMillis + " ms");
        }
    }
}