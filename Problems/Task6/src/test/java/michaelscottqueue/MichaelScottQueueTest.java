package michaelscottqueue;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MichaelScottQueueTest {

    private Thread addElementsFromThread(MichaelScottQueue<Integer> michaelScottQueue, int element, int n) {

        Thread thread = new Thread(() -> {
            for (int i = 0; i < n; i++) {
                michaelScottQueue.enqueue(element + i);
            }
        });
        thread.start();
        return thread;
    }

    private Thread addElementFromThread(MichaelScottQueue<Integer> michaelScottQueue, int element) {
        Thread thread = new Thread(() -> michaelScottQueue.enqueue(element));
        thread.start();
        return thread;
    }

    private Thread removeElementFromThread(MichaelScottQueue<Integer> michaelScottQueue) {
        Thread thread = new Thread(michaelScottQueue::dequeue);
        thread.start();
        return thread;
    }

    @Test
    void addElementsFromDifferentThreadsTest() throws InterruptedException {
        MichaelScottQueue<Integer> michaelScottQueue = new MichaelScottQueue<>();

        List<Thread> threads = new ArrayList<>();
        threads.add(addElementsFromThread(michaelScottQueue, 1, 2));
        threads.add(addElementsFromThread(michaelScottQueue, 3, 3));
        threads.add(addElementsFromThread(michaelScottQueue, 6, 2));
        for (Thread thread : threads) {
            thread.join();
        }
        List<Integer> resultsAfterRemove = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            resultsAfterRemove.add(michaelScottQueue.dequeue());
        }
        for (int i = 1; i < 8; i++) {
            assertTrue(resultsAfterRemove.contains(i));
        }
    }

    @Test
    void removeElementsFromDifferentThreadsTest() throws InterruptedException {
        MichaelScottQueue<Integer> michaelScottQueue = new MichaelScottQueue<>();

        List<Thread> threads = new ArrayList<>();
        threads.add(addElementFromThread(michaelScottQueue, 1));
        threads.add(addElementFromThread(michaelScottQueue, 2));
        threads.add(addElementFromThread(michaelScottQueue, 3));
        for (Thread thread : threads) {
            thread.join();
        }
        threads.clear();
        threads.add(removeElementFromThread(michaelScottQueue));
        threads.add(removeElementFromThread(michaelScottQueue));
        for (Thread thread : threads) {
            thread.join();
        }
        Integer dequeueResult = michaelScottQueue.dequeue();
        assertNull(michaelScottQueue.dequeue());
        assertTrue(dequeueResult == 1 || dequeueResult == 2 || dequeueResult == 3);
    }

    @Test
    void addAndRemoveElementsAtTheSameTimeTest() throws InterruptedException {
        MichaelScottQueue<Integer> michaelScottQueue = new MichaelScottQueue<>();

        List<Thread> threads = new ArrayList<>();
        threads.add(addElementFromThread(michaelScottQueue, 1));
        for (Thread thread : threads) {
            thread.join();
        }
        threads.clear();
        threads.add(addElementFromThread(michaelScottQueue, 2));
        threads.add(removeElementFromThread(michaelScottQueue));
        threads.add(addElementFromThread(michaelScottQueue, 3));
        threads.add(removeElementFromThread(michaelScottQueue));
        for (Thread thread : threads) {
            thread.join();
        }
        Integer dequeueResult = michaelScottQueue.dequeue();
        assertNull(michaelScottQueue.dequeue());
        assertTrue(dequeueResult == 1 || dequeueResult == 2 || dequeueResult == 3);
    }
}