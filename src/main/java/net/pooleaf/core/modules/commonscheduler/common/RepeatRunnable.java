package net.pooleaf.core.modules.commonscheduler.common;

public class RepeatRunnable implements Runnable {

    private int count;
    private int repeatCount;

    private Runnable runnable;


    public RepeatRunnable(Runnable runnable, int repeatCount) {
        this.runnable = runnable;
        this.repeatCount = repeatCount;
    }


    @Override
    public void run() {
        if (count < repeatCount) {
            runnable.run();
        }

        count++;
    }

}
