package cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;

public class CustomCyclicBarrier {
    private final int threadsNumber;
    private final Runnable barrierEvent;
    private int waiting;
    private boolean isBroken;

    public CustomCyclicBarrier(int threadsNumber, Runnable barrierEvent) {
        this.threadsNumber = threadsNumber;
        this.waiting = 0;
        this.barrierEvent = barrierEvent;
        this.isBroken = false;
    }

    public synchronized void await() throws BrokenBarrierException, InterruptedException {
        if (this.isBroken) {
            throw new BrokenBarrierException();
        }

        ++this.waiting;
        while (this.waiting != this.threadsNumber) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                this.isBroken = true;
                throw e;
            }
        }
        this.waiting = 0;
        notifyAll();
        if (this.barrierEvent != null) {
            this.barrierEvent.run();
        }
    }

    public boolean isBroken() {
        return this.isBroken;
    }

    public int getThreadsNumber() {
        return this.threadsNumber;
    }

    public int getNumberWaiting() {
        return this.threadsNumber-this.waiting;
    }
}
