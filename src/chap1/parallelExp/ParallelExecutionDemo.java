public class ParallelExecutionDemo {
    public static void main(String[] args) throws Exception {
        CreditCalculatorService service = new CreditCalculatorService();
        Long personId = 1L;

        // Measure sequential execution
        ExecutionTimer.measureExecutionTime(() -> {
            Credit credit = service.calculateCreditScore(personId);
            System.out.println("Sequential Credit Score: " + credit.score());
            return credit;
        }, "Sequential Execution");

        // Measure parallel execution
        ExecutionTimer.measureExecutionTime(() -> {
            Credit credit = service.calculateCreditScoreParallel(personId);
            System.out.println("Parallel Credit Score: " + credit.score());
            return credit;
        }, "Parallel Execution");
    }
}