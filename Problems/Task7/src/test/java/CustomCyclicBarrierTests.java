import cyclicbarrier.CustomCyclicBarrier;
import org.junit.jupiter.api.Test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

class CustomCyclicBarrierTests {

    Thread startNewThread(CustomCyclicBarrier cyclicBarrier) {
        Thread thread = new Thread(() -> {
            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        return thread;
    }

    @Test
    void BarrierReachedTest() throws InterruptedException {
        //Given
        AtomicBoolean barrierReached = new AtomicBoolean(false);
        CustomCyclicBarrier cyclicBarrier = new CustomCyclicBarrier(3, () -> barrierReached.set(true));
        //When
        startNewThread(cyclicBarrier);
        startNewThread(cyclicBarrier);
        startNewThread(cyclicBarrier);
        synchronized (this) {
            this.wait(500);
        }
        //Then
        assertTrue(barrierReached.get());
    }

    @Test
    void OneThreadIsAwaitingTest() throws InterruptedException {
        //Given
        AtomicBoolean barrierReached = new AtomicBoolean(false);
        CustomCyclicBarrier cyclicBarrier = new CustomCyclicBarrier(3, () -> barrierReached.set(true));
        //When
        startNewThread(cyclicBarrier);
        startNewThread(cyclicBarrier);
        synchronized (this) {
            this.wait(500);
        }
        //Then
        assertFalse(barrierReached.get());
        assertEquals(1, cyclicBarrier.getNumberWaiting());
    }

    @Test
    void ThreadsAwaitingAfterReachedTest() throws InterruptedException {
        //Given
        AtomicBoolean barrierReached = new AtomicBoolean(false);
        CustomCyclicBarrier cyclicBarrier = new CustomCyclicBarrier(3, () -> barrierReached.set(true));
        //When
        startNewThread(cyclicBarrier);
        startNewThread(cyclicBarrier);
        startNewThread(cyclicBarrier);
        synchronized (this) {
            this.wait(500);
        }
        //Then
        assertTrue(barrierReached.get());
        assertEquals(3, cyclicBarrier.getNumberWaiting());
    }

    @Test
    void ThreadsNumberWhenNotReachedTest() throws InterruptedException {
        //Given
        AtomicBoolean barrierReached = new AtomicBoolean(false);
        CustomCyclicBarrier cyclicBarrier = new CustomCyclicBarrier(3, () -> barrierReached.set(true));
        //When
        startNewThread(cyclicBarrier);
        synchronized (this) {
            this.wait(500);
        }
        //Then
        assertFalse(barrierReached.get());
        assertEquals(3, cyclicBarrier.getThreadsNumber());
    }

    @Test
    void ThreadsNumberWhenReachedTest() throws InterruptedException {
        //Given
        AtomicBoolean barrierReached = new AtomicBoolean(false);
        CustomCyclicBarrier cyclicBarrier = new CustomCyclicBarrier(3, () -> barrierReached.set(true));
        //When
        startNewThread(cyclicBarrier);
        startNewThread(cyclicBarrier);
        startNewThread(cyclicBarrier);
        synchronized (this) {
            this.wait(500);
        }
        //Then
        assertTrue(barrierReached.get());
        assertEquals(3, cyclicBarrier.getThreadsNumber());
    }

    @Test
    void BarrierNotBrokenWhenNotReachedTest() throws InterruptedException {
        //Given
        AtomicBoolean barrierReached = new AtomicBoolean(false);
        CustomCyclicBarrier cyclicBarrier = new CustomCyclicBarrier(3, () -> barrierReached.set(true));
        //When
        startNewThread(cyclicBarrier);
        synchronized (this) {
            this.wait(500);
        }
        //Then
        assertFalse(barrierReached.get());
    }

    @Test
    void BarrierNotBrokenWhenReachedTest() throws InterruptedException {
        //Given
        AtomicBoolean barrierReached = new AtomicBoolean(false);
        CustomCyclicBarrier cyclicBarrier = new CustomCyclicBarrier(3, () -> barrierReached.set(true));
        //When
        startNewThread(cyclicBarrier);
        startNewThread(cyclicBarrier);
        startNewThread(cyclicBarrier);
        synchronized (this) {
            this.wait(500);
        }
        //Then
        assertFalse(cyclicBarrier.isBroken());
    }

    @Test
    void BrokeBarrierTest() throws InterruptedException {
        //Given
        AtomicBoolean barrierReached = new AtomicBoolean(false);
        CustomCyclicBarrier cyclicBarrier = new CustomCyclicBarrier(3, () -> barrierReached.set(true));

        Thread exampleThread = startNewThread(cyclicBarrier);
        //When
        exampleThread.interrupt();
        //Then
        exampleThread.join();
        assertTrue(cyclicBarrier.isBroken());
    }

    @Test
    void ExceptionOnAwaitAfterInterruptTest() throws InterruptedException {
        //Given
        AtomicBoolean barrierReached = new AtomicBoolean(false);
        CustomCyclicBarrier cyclicBarrier = new CustomCyclicBarrier(3, () -> barrierReached.set(true));

        Thread exampleThread = startNewThread(cyclicBarrier);
        //When
        exampleThread.interrupt();
        //Then
        exampleThread.join();
        assertThrows(BrokenBarrierException.class, cyclicBarrier::await);
    }
}
