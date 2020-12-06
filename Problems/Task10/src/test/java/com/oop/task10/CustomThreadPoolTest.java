package com.oop.task10;

import org.junit.jupiter.api.Test;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomThreadPoolTest {

    void task(int sleepTime, AtomicInteger counter) throws InterruptedException {
        Thread.sleep(sleepTime);
        counter.incrementAndGet();
    }

    @Test
    void shouldDoFiveTasksInParallelThreadsAndIncrementCountOnPoolSize() throws InterruptedException {
        //given
        final AtomicInteger counter = new AtomicInteger(0);
        final int poolSize = 5;
        final CustomThreadPool threadPool = new CustomThreadPool(poolSize);
        final int sleepTime = 1000;
        //when
        for (int i = 0; i < poolSize; i++) {
            threadPool.execute(() -> {
                try {
                    task(sleepTime, counter);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        Thread.sleep(sleepTime + 200);
        //then
        assertEquals(poolSize, counter.get());
    }

    @Test
    void shouldDoTaskTwiceInTwoThreadAndIncrementCounterOnDoublePoolSize() throws InterruptedException {
        //given
        final AtomicInteger counter = new AtomicInteger(0);
        final int poolSize = 2;
        final CustomThreadPool threadPool = new CustomThreadPool(poolSize);
        final int sleepTime = 200;
        //when
        for (int i = 0; i < poolSize * 2; i++) {
            threadPool.execute(() -> {
                try {
                    task(sleepTime, counter);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        Thread.sleep(sleepTime * 2 + 200);
        //then
        assertEquals(poolSize * 2, counter.get());
    }

    @Test
    void shouldBeInterruptedBecauseOfShutDown() throws InterruptedException {
        //given
        final AtomicInteger counter = new AtomicInteger(0);
        final int poolSize = 3;
        final CustomThreadPool threadPool = new CustomThreadPool(poolSize);
        final int sleepTime = 2000;
        //when
        for (int i = 0; i < poolSize; i++) {
            threadPool.execute(() -> {
                try {
                    task(sleepTime, counter);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        threadPool.close();
        //then
        assertEquals(3, counter.get());
        assertTrue(threadPool.isClosed());
    }
}
