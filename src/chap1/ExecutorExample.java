import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class ExecutorExample {
	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		List<Integer> numbers = new ArrayList<>();
		Random rand = new Random();
		for(int i = 0; i < 1000000; i++) {
			numbers.add(rand.nextInt(1000) + 1);
		}
		double[] averages = new double[10];
		
		for(int i = 0; i < 10; i++) {
			final int taskId = i;
			executor.submit(()->{
				int startIdx = taskId * 100000;
				double sum = 0;
				for(int j = startIdx; j < (startIdx + 100000); j++) {
					sum += numbers.get(j);
				}
				System.out.println("Calculted average for task id " + taskId + " in thread " + Thread.currentThread().getName() + " is " + (sum/100000.0));
				averages[taskId] = sum / 100000.0;
			});	
		}
		executor.shutdown();
	}
}
