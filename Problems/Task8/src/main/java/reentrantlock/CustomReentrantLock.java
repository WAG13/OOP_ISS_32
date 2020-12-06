package reentrantlock;

public class CustomReentrantLock {
    private int waitingThreadsNumber = 0;
    private Thread currentThread = null;

    public synchronized void lock() throws InterruptedException {

        if (waitingThreadsNumber == 0) {
            currentThread = Thread.currentThread();
            waitingThreadsNumber++;
        }
        else if (waitingThreadsNumber > 0 && currentThread == Thread.currentThread()) {
            waitingThreadsNumber++;
        }
        else {
            while (waitingThreadsNumber > 0) {
                this.wait();
            }
            waitingThreadsNumber++;
            currentThread = Thread.currentThread();
        }

    }

    public synchronized void unlock()  throws IllegalMonitorStateException{

        if (waitingThreadsNumber == 0) {
            throw new IllegalMonitorStateException();
        }
        waitingThreadsNumber--;

        if (waitingThreadsNumber == 0) {
            this.notify();
        }
    }

    public synchronized boolean tryLock() throws InterruptedException {
        if (waitingThreadsNumber == 0) {
            lock();
            return true;
        }
        return false;
    }
}