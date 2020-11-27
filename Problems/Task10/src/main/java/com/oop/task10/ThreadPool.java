package com.oop.task10;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;


public class ThreadPool implements Executor, AutoCloseable {
    private final Queue<Runnable> tasksQueue;
    private final List<Thread> threads;
    private volatile boolean isClosed;

    private void startThreads(int threadCount) {
        for (int i = 0; i < threadCount; i++) {
            var thread = new Thread(() -> {
                while (!this.isClosed || this.tasksQueue.size() > 0) {
                    Runnable nextTask = this.tasksQueue.poll();

                    if (nextTask != null) {
                        nextTask.run();
                    }
                }
            });

            threads.add(thread);
            thread.start();
        }
    }

    public ThreadPool(int threadCount) {
        this.tasksQueue = new ConcurrentLinkedQueue<>();
        this.threads = new ArrayList<>(threadCount);
        this.isClosed = false;

        this.startThreads(threadCount);
    }

    @Override
    public void execute(Runnable command) {
        if (!this.isClosed) {
            this.tasksQueue.offer(command);
        }
    }

    @Override
    public void close() throws InterruptedException {
        this.isClosed = true;

        for (Thread thread: threads) {
            thread.join();
        }
    }
}