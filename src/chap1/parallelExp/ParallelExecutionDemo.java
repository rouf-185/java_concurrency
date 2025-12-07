import java.util.concurrent.*;
import java.util.*;

public class ParallelExecutionDemo {
    public static <T> T measure(Callable<T> task, String taskName) throws ExecutionException, InterruptedException {
        Long startTime = System.nanoTime();
        T value = null;
        try {
            value =  task.call();
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
            Long endTime = System.nanoTime();
            System.out.println("Task: " + taskName + " takes " + ((endTime - startTime) / 1000000) + " ms");
        }
        return value;
    }

    public static void main(String[] args) {
        try {
            CreditCalculatorService creditCalculatorService = new CreditCalculatorService();
            System.out.println("Simulating sequential Execution");
            Credit creditScore1 = measure(() -> creditCalculatorService.calculateCreditScoreSequential(1L), "Sequential");
            Credit creditScore2 = measure(() -> creditCalculatorService.calculateCreditScoreParallel(1L),  "Parallel");
            System.out.println(creditScore1.score() + " " + creditScore2.score());
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}