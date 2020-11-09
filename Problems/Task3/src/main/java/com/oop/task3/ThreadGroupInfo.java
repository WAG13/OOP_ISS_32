package com.oop.task3;

public class ThreadGroupInfo {
    private int interval = 5000;

    public ThreadGroupInfo(int interval){
        this.interval = interval;
    }

    public void printTreadsInfo(ThreadGroup threadGroup){
        new Thread(() -> printInfoWithIntervals(threadGroup)).start();
    }

    private void printInfoWithIntervals(ThreadGroup threadGroup) {
        while(threadGroup.activeCount() > 0){
            printThreadGroupInfo(threadGroup, 0);
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized void printThreadGroupInfo(ThreadGroup threadGroup, int levelOfThread) {
        if (levelOfThread!=0)
            printWithIndent("-"+threadGroup.getName(), levelOfThread);
        else
            printWithIndent("\n"+threadGroup.getName(), levelOfThread);

        Thread [] threadsInCurrentGroup = new Thread[(threadGroup.activeCount())];
        int threadsCount = threadGroup.enumerate(threadsInCurrentGroup, false);
        if(threadsCount > 0){
            //printWithIndent(" Threads at " + threadGroup.getName() + ":", levelOfThread);
            for(int i = 0; i < threadsCount; i++ ){
                printWithIndent("-"+threadsInCurrentGroup[i].getName(), levelOfThread + 1);
            }
        }

        ThreadGroup [] threadGroups = new ThreadGroup[threadGroup.activeGroupCount()];
        int groupsCount = threadGroup.enumerate(threadGroups, false);
        if(groupsCount > 0){
            //printWithIndent(" Thread groups in " + threadGroup.getName() + ":", levelOfThread);
            for(int i = 0; i < groupsCount; i++){
                printThreadGroupInfo(threadGroups[i], levelOfThread + 1);
            }
        }

    }

    private void printWithIndent(String name, int levelOfThread) {
        for(int i = 0; i < levelOfThread; i++){
            System.out.print("\t|");
        }
        System.out.print(name + "\n");
    }

}
