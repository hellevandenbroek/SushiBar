	package Sushibar;
 
/**
 * This class implements the consumer part of the producer/consumer problem.
 * One waitress instance corresponds to one consumer.
 */
public class Waitress implements Runnable {
 
    private WaitingArea waitingarea;
   

    Waitress(WaitingArea waitingArea) {
        this.waitingarea =  waitingArea;
    }
 
    /**
     * This is the code that will run when a new thread is
     * created for this instance
     */
    @Override
    public void run() {
        try {
            startWaitress();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public void startWaitress() throws InterruptedException {
        while (SushiBar.isOpen) {
            Customer c;
            if (!waitingarea.hasNext()) {
                synchronized (waitingarea) {
                    waitingarea.wait();
                }
            }
            synchronized (waitingarea) {
                c = waitingarea.next();
                waitingarea.notify();
            }
            if (c != null) {
                handleCustomer(c);
            }
        }
        while (waitingarea.hasNext()) {
            Customer c;

            synchronized (waitingarea) {
                c = waitingarea.next();
                waitingarea.notify();
            }
            if (c != null) {
                handleCustomer(c);
            }
        }
    }
   
    public void handleCustomer(Customer c) {
        if (c != null) {   
            SushiBar.write(Thread.currentThread().getName() + ": " + c.toString() + " is now fetched." );
            try {
                Thread.sleep(SushiBar.waitressWait);
                c.order();
                SushiBar.write(Thread.currentThread().getName() + ": " + c.toString() + " is now eating " + c.getEatingHere() + " meals here.");
                Thread.sleep(SushiBar.customerWait * c.getEatingHere());
                SushiBar.write(Thread.currentThread().getName() + ": " + c.toString() + " is now leaving.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}