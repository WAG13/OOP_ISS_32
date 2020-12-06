package reentrantlock;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

public class CustomReentrantLockTest {
    static Logger log = Logger.getLogger(CustomReentrantLock.class.getName());

    @Test
    void LockAndReleaseTimesTest() throws InterruptedException {
        //Given
        CustomReentrantLock lock = new CustomReentrantLock();
        Thread exampleThread = new Thread(() -> {
            try {
                lock.lock();
                lock.lock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.unlock();
            lock.unlock();
        });
        exampleThread.start();
        //When
        exampleThread.join();
        //Then
        assertTrue(lock.tryLock());
        lock.unlock();
    }

    @Test
    void TwoLocksAndOneUnlockTest() throws InterruptedException {
        //Given
        CustomReentrantLock lock = new CustomReentrantLock();
        //When
        lock.lock();
        lock.lock();
        lock.unlock();
        //Then
        assertFalse(lock.tryLock());
        lock.unlock();
        assertTrue(lock.tryLock());
    }

    @Test
    void LockTest() {
        //Given
        CustomReentrantLock customReentrantLock = new CustomReentrantLock();
        int n = 10000;
        AtomicInteger counter = new AtomicInteger(0);
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < n; i++) {

                try {
                    customReentrantLock.lock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                counter.incrementAndGet();
                customReentrantLock.unlock();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < n; i++) {
                try {
                    customReentrantLock.lock();
                    customReentrantLock.lock();
                    customReentrantLock.lock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                counter.decrementAndGet();
                customReentrantLock.unlock();
                customReentrantLock.unlock();
                customReentrantLock.unlock();
            }
        });
        Thread thread3 = new Thread(() -> {
            for (int i = 0; i < n; i++) {
                try {
                    customReentrantLock.lock();
                    customReentrantLock.lock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                counter.incrementAndGet();
                customReentrantLock.unlock();
                customReentrantLock.unlock();
            }
        });
        //When
        thread1.start();
        thread2.start();
        thread3.start();
        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            log.info(e.getMessage());
        }
        //Then
        assertEquals(10000, counter.get());
    }

}