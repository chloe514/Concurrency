import java.util.Random;

public class Producer implements Runnable {
    private final SharedBuffer sharedBuffer;  // Reference to the shared buffer
    private final Random random;  // Random number generator

    // Constructor to initialize the producer with the shared buffer
    public Producer(SharedBuffer sharedBuffer) {
        this.sharedBuffer = sharedBuffer;
        this.random = new Random();
    }

    @Override
    public void run() {
        try {
            while (true) {
                int number = random.nextInt(100);  // Generate a random number between 0 and 99
                sharedBuffer.add(number);  // Add the number to the shared buffer
                System.out.println("Produced: " + number);
                Thread.sleep(500);  // Simulate time taken to produce a number

                // Check if we need to stop
                synchronized (sharedBuffer) {
                    if (!sharedBuffer.isRunning()) {
                        break;
                    }
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();  // Restore the interrupted status
        }
    }
}


