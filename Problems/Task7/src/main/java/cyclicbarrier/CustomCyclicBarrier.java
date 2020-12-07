package cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;

public class CustomCyclicBarrier {
    private final int threadsNumber;
    private final Runnable barrierEvent;
    private int waiting;
    private boolean broken;

    public CustomCyclicBarrier(int threadsNumber, Runnable barrierEvent) {
        this.threadsNumber = threadsNumber;
        this.waiting = 0;
        this.barrierEvent = barrierEvent;
        this.broken = false;
    }

    public synchronized void await() throws BrokenBarrierException, InterruptedException {
        if (this.broken) {
            throw new BrokenBarrierException();
        }

        ++this.waiting;
        while (this.waiting != this.threadsNumber) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                this.broken = true;
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
        return this.broken;
    }

    public int getThreadsNumber() {
        return this.threadsNumber;
    }

    public int getNumberWaiting() {
        return this.threadsNumber-this.waiting;
    }
}
