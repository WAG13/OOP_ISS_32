package phaser;

public class CustomPhaser {
    private int parties = 0;
    private Integer waitingThreadsNumber = 0;
    private int phase = 0;

    public CustomPhaser() {
    }

    public CustomPhaser(int parties) {
        this.parties = parties;
        this.waitingThreadsNumber = parties;
    }

    int register() {
        ++parties;
        ++waitingThreadsNumber;
        return phase;
    }

    synchronized int arrive() {
        --waitingThreadsNumber;
        int currPhase = phase;

        if (waitingThreadsNumber == 0) {
            notifyAll();
            waitingThreadsNumber = parties;
            phase = currPhase + 1;
        }

        return phase;
    }

    synchronized int arriveAndAwaitAdvance() {
        --waitingThreadsNumber;
        int currPhase = phase;

        while (waitingThreadsNumber > 0) {
            try {
                this.wait();
            } catch (InterruptedException ignored) {
            }

        }
        waitingThreadsNumber = parties;
        phase = currPhase + 1;
        notifyAll();
        return phase;
    }

    synchronized int arriveAndDeregister() {
        --waitingThreadsNumber;
        --parties;
        int currPhase = phase;

        if (waitingThreadsNumber == 0) {
            notifyAll();
            phase = currPhase + 1;
            waitingThreadsNumber = parties;
            return -1;
        }

        return phase + 1;
    }

}
