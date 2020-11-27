package com.oop.task3;

public class Main {
    public static void main(String[] args) {
        ThreadGroupInfo threadService = new ThreadGroupInfo(2000);

        ThreadGroup threadGroup0 = new ThreadGroup("MASTER GROUP");
        ThreadGroup threadGroup1 = new ThreadGroup(threadGroup0, "FIRST GROUP");
        ThreadGroup threadGroup2 = new ThreadGroup(threadGroup0, "SECOND GROUP");
        ThreadGroup threadGroup3 = new ThreadGroup(threadGroup2, "THIRD GROUP");

        new Thread(threadGroup1, () -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "firstThread").start();


        new Thread(threadGroup2, () -> {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "secondThread").start();

        new Thread(threadGroup2, () -> {
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "thirdThread").start();

        new Thread(threadGroup3, () -> {
            try {
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "fourthThread").start();

        threadService.printTreadsInfo(threadGroup0);

    }
}
