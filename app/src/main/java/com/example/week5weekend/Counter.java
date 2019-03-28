package com.example.week5weekend;

public class Counter {
    private long start;
    private long stop;
    private boolean isRunning;

    public Counter() {
        this.start = 0;
        this.stop = 0;
        this.isRunning = false;
    }

    public void startCounter() {
        start = System.currentTimeMillis();
        isRunning = true;
    }

    public void stopCounter() {
        stop = System.currentTimeMillis();
        isRunning = false;
    }

    public long getTime() {
        if (isRunning) {
            return (System.currentTimeMillis() - start) / 1000;
        }
        return (stop - start) / 1000;
    }

}
