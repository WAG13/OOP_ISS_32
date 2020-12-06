package phaser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomPhaserTests {
    int phase = 0;

    void startNewThread(CustomPhaser phaser) {
        Thread thread = new Thread(() -> {
            phase = phaser.arrive();
        });
        thread.start();
    }

    void startNewThreadWithAwait(CustomPhaser phaser) {
        Thread thread = new Thread(() -> {
            phase = phaser.arriveAndAwaitAdvance();
        });
        thread.start();
    }

    void startNewThreadWithDeregister(CustomPhaser phaser) {
        Thread thread = new Thread(() -> {
            phase = phaser.arriveAndDeregister();
        });
        thread.start();
    }

    @Test
    void reach2PhaseTest() throws InterruptedException {
        //Given
        CustomPhaser phaser = new CustomPhaser(3);
        //When
        for (int i=0;i<3;i++){
            Thread thread = new Thread(() -> {
                phase = phaser.arrive();
            });
            thread.start();
        }
        synchronized (this) {
            this.wait(500);
        }
        //Then
        assertEquals(1, phase);
    }

    @Test
    void reach2PhaseWithAwaitTest() throws InterruptedException {
        //Given
        CustomPhaser phaser = new CustomPhaser(3);
        startNewThread(phaser);
        startNewThreadWithAwait(phaser);
        startNewThread(phaser);
        //When
        synchronized (this) {
            this.wait(500);
        }
        //Then
        assertEquals(1, phase);
    }

    @Test
    void reach3PhaseWithDeregisterTest() throws InterruptedException {
        //Given
        CustomPhaser phaser = new CustomPhaser(3);
        phaser.arrive();
        startNewThreadWithDeregister(phaser);
        startNewThreadWithDeregister(phaser);
        //When
        synchronized (this) {
            this.wait(500);
        }
        phase = phaser.arrive();
        //Then
        assertEquals(2, phase);
    }
}