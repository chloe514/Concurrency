import java.util.LinkedList;
import java.util.Queue;

public class SharedBuffer {
    private final int maxSize;  // Maximum size of the buffer
    private final Queue<Integer> buffer;  // The buffer to hold the integers
    private boolean running = true;  // Flag to indicate if the producer and consumer should keep running

    // Constructor to initialize the buffer with a specified maximum size
    public SharedBuffer(int maxSize) {
        this.maxSize = maxSize;
        this.buffer = new LinkedList<>();
    }

    // Synchronized method to add an element to the buffer
    public synchronized void add(int value) throws InterruptedException {
        // Wait if the buffer is full
        while (buffer.size() == maxSize && running) {
            wait();
        }
        if (!running) return;  // Exit if not running
        buffer.add(value);  // Add the value to the buffer
        notifyAll();  // Notify waiting threads that buffer state has changed
    }

    // Synchronized method to remove an element from the buffer
    public synchronized int remove() throws InterruptedException {
        // Wait if the buffer is empty
        while (buffer.isEmpty() && running) {
            wait();
        }
        if (!running) return -1;  // Return -1 if the buffer is stopped
        int value = buffer.remove();  // Remove the value from the buffer
        notifyAll();  // Notify waiting threads that buffer state has changed
        return value;
    }

    // Method to stop the buffer, allowing threads to exit
    public synchronized void stop() {
        running = false;
        notifyAll();  // Wake up any waiting threads
    }

    // Check if the buffer is running
    public synchronized boolean isRunning() {
        return running;
    }
}


