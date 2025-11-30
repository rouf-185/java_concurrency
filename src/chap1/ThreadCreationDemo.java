public class ThreadCreationDemo {
	public static void main(String[] args) {
		Thread t1 = new ThreadExtension("Thread-1");
		Thread t2 = new Thread(new RunnableImplementation(), "Thread-2");
		Thread t3 = new Thread(new Runnable(){
			@Override
			public void run() {
				System.out.println("Current Thread Name: " + Thread.currentThread().getName());
			}
		}, "Thread-4");
		Thread t4 = new Thread(() -> System.out.println("Current Thread Name: " + Thread.currentThread().getName()));
		t1.start();
		t2.start();
		t3.start();
		t4.start();
	}

}

class ThreadExtension extends Thread {
	public ThreadExtension(String name) {
		super(name);
	}

	@Override
	public void run() {
		System.out.println("Current Thread Name: " + getName());
	}
}

class RunnableImplementation implements Runnable {
	@Override
	public void run() {
		System.out.println("Current Thread Name: " + Thread.currentThread().getName());
	}
}
