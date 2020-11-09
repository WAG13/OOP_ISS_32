package com.oop.task3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ThreadGroupInfoTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testThreadServiceMethods(){
        ThreadGroup threadGroup1 = new ThreadGroup("FIRST GROUP");
        ThreadGroup threadGroup2 = new ThreadGroup(threadGroup1, "SECOND GROUP");
        ThreadGroup threadGroup3 = new ThreadGroup(threadGroup2, "THIRD GROUP");

        new Thread(threadGroup1, () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "firstThread").start();


        new Thread(threadGroup2, () -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "secondThread").start();

        new Thread(threadGroup2, () -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "thirdThread").start();

        new Thread(threadGroup3, () -> {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "fourthThread").start();

        ThreadGroupInfo threadService = new ThreadGroupInfo(2000);
        threadService.printTreadsInfo(threadGroup1);

        long endTime = System.currentTimeMillis() + 6000;
        while (System.currentTimeMillis() < endTime);

        assertEquals("\nFIRST GROUP\n" +
                "\t|-firstThread\n" +
                "\t|-SECOND GROUP\n" +
                "\t|\t|-secondThread\n" +
                "\t|\t|-thirdThread\n" +
                "\t|\t|-THIRD GROUP\n" +
                "\t|\t|\t|-fourthThread\n" +
                "\nFIRST GROUP\n"+
	            "\t|-SECOND GROUP\n" +
                "\t|\t|-thirdThread\n" +
                "\t|\t|-THIRD GROUP\n" +
                "\t|\t|\t|-fourthThread\n", outContent.toString());

    }
}