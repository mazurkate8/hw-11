package com.tasks.thread;

public class Task01 {
    public static void main(String[] args) {
        Timer timer = new Timer();
        FiveSec fiveSec = new FiveSec(timer);
        EverySecond everySecond = new EverySecond(timer);

        Thread t1 = new Thread(everySecond);
        Thread t2 = new Thread(fiveSec);

        t1.setName("EverySecond Thread ");
        t2.setName("Fivesec Thread ");
        t1.start();
        t2.start();
    }
}

class Timer {

    private int incrementTime = 1;

    public synchronized void alarmFiveSec() {
        while (incrementTime % 5 != 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        incrementTime++;
        System.out.println("Прошло 5 секунд ");
        notify();
    }

    public synchronized void alarmEverySec() {
        while (incrementTime % 5 == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        incrementTime++;
        System.out.println(" от начала сессии (запуска программы) прошло " + incrementTime + " секунд");
        notify();
    }
}

class FiveSec implements Runnable {

    Timer timer;

    public FiveSec(final Timer timer) {
        this.timer = timer;
    }

    @Override
    public void run() {
        for (int i = 0; i < 500; i++) {
            timer.alarmFiveSec();
        }
    }
}

class EverySecond implements Runnable {

    private Timer timer;

    public EverySecond(final Timer timer) {
        this.timer = timer;
    }

    @Override
    public void run() {
        for (int i = 0; i < 500; i++) {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            timer.alarmEverySec();
        }
    }
}
