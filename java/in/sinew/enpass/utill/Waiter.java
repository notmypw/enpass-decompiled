package in.sinew.enpass.utill;

public class Waiter extends Thread {
    private long lastUsed;
    private long period;
    private boolean stop = false;

    public Waiter(long period) {
        this.period = period;
    }

    public void run() {
        touch();
        do {
            long idle = System.currentTimeMillis() - this.lastUsed;
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (idle > this.period) {
            }
        } while (!this.stop);
    }

    public synchronized void touch() {
        this.lastUsed = System.currentTimeMillis();
    }

    public synchronized void forceInterrupt() {
        interrupt();
    }

    public synchronized void stopThread() {
        this.stop = true;
    }

    public synchronized void setPeriod(long period) {
        this.period = period;
    }

    public long getLastUsedTime() {
        return this.lastUsed;
    }
}
