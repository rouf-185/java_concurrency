import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

record Credit(double score){}
record Person(Long id, String name){}
record Asset(String type, double value){}
record Liability(String type, double amount){}

public class CreditCalculatorService {
    private Person getPerson(Long personId) {
        // Simulate fetching person data
        simulateDelay(2000);
        return new Person(personId, "John Doe");
    }
    private List<Asset> getAssets(Person person) {
        // Simulate fetching assets
        simulateDelay(3000);
        return Arrays.asList(new Asset("House", 300000), new Asset("Car", 20000));
    }
    private List<Liability> getLiabilities(Person person) {
        // Simulate fetching liabilities
        simulateDelay(4000);
        return Arrays.asList(new Liability("Mortgage", 150000), new Liability("Loan", 5000));
    }
    private void simulateDelay(int delayInMillis) {
        try {
            Thread.sleep(delayInMillis); // Simulate delay
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void importantWork() {
        // Simulate some important work
        simulateDelay(1000);
    }
    private Credit calculateCreditScore(Person person, List<Asset> assets, List<Liability> liabilities) {
        double assetValue = assets.stream().mapToDouble(Asset::value).sum();
        double liabilityAmount = liabilities.stream().mapToDouble(Liability::amount).sum();
        double score = (assetValue - liabilityAmount) / 1000; // Simplified scoring logic
        return new Credit(score);  
    }   
    public Credit calculateCreditScoreSequential(Long personId) throws Exception {
        var person = getPerson(personId);
        var assets = getAssets(person);
        var liabilities = getLiabilities(person); 
        importantWork();
        return calculateCreditScore(person, assets, liabilities);
    }
    public Credit calculateCreditScoreUsingExecutor(Long personId) throws ExecutionException, InterruptedException {
        Person person = getPerson(personId);
        try(ExecutorService executor = Executors.newFixedThreadPool(5)) {
            Future<List<Asset>> assetsFuture = executor.submit(() -> getAssets(person));
            Future<List<Liability>> liabilitiesFuture = executor.submit(() -> getLiabilities(person));
            executor.submit(() -> importantWork());
            List<Asset> assets = assetsFuture.get();
            List<Liability> liabilities = liabilitiesFuture.get();
            return calculateCreditScore(person, assets, liabilities);
        }
    }
    public Credit calculateCreditScoreParallel(Long personId) throws Exception {
        var person = getPerson(personId);
        var assetsRef = new AtomicReference<List<Asset>>();
        var liabilitiesRef = new AtomicReference<List<Liability>>();
        Thread thread2 = new Thread(() -> {
            var assets = getAssets(person);
            assetsRef.set(assets);
        });
        Thread thread3 = new Thread(() -> {
            var liabilities = getLiabilities(person);
            liabilitiesRef.set(liabilities);
        });
        Thread thread1 = new Thread(() -> {
            importantWork();
        });
        thread1.start();
        thread2.start();
        thread3.start();
        thread2.join();
        thread3.join();
        Credit credit = calculateCreditScore(person, assetsRef.get(), liabilitiesRef.get());
        thread1.join();
        return credit;
    }
}