public class Consumer implements Runnable {
    private final SharedBuffer sharedBuffer;  // Reference to the shared buffer
    private int sum;  // Variable to keep track of the sum of consumed numbers

    // Constructor to initialize the consumer with the shared buffer
    public Consumer(SharedBuffer sharedBuffer) {
        this.sharedBuffer = sharedBuffer;
        this.sum = 0;
    }

    @Override
    public void run() {
        try {
            while (true) {
                int number = sharedBuffer.remove();  // Retrieve a number from the shared buffer
                if (number == -1) break;  // Exit if buffer is stopped
                sum += number;  // Add the number to the sum
                System.out.println("Consumed: " + number + " | Sum: " + sum);
                Thread.sleep(500);  // Simulate time taken to consume a number

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


