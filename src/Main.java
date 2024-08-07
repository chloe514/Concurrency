public class Main {
    public static void main(String[] args) throws InterruptedException {
        SharedBuffer sharedBuffer = new SharedBuffer(10);  // Create a shared buffer with a maximum size of 10

        Producer producer = new Producer(sharedBuffer);  // Create a producer with the shared buffer
        Consumer consumer = new Consumer(sharedBuffer);  // Create a consumer with the shared buffer

        // Create and start threads for the producer and consumer
        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);

        producerThread.start();
        consumerThread.start();

        // Let the threads run for 5 seconds
        Thread.sleep(5000);

        // Stop the threads
        sharedBuffer.stop();

        // Wait for both threads to finish
        producerThread.join();
        consumerThread.join();

        System.out.println("Finished processing");
    }
}

