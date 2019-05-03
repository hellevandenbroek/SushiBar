package Sushibar;

import java.util.Random;
public class Door implements Runnable {
	 
    WaitingArea waitingarea;
    Random randomInt = new Random();
 
 
    public Door(WaitingArea waitingArea) {
        this.waitingarea = waitingArea;
    }
 
    @Override
    public void run() {
            try {
                startDoor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
   
    public void startDoor() throws InterruptedException {
        while(SushiBar.isOpen) {
            int interval = randomInt.nextInt(SushiBar.doorWait);
            if (!waitingarea.hasCapacity()) {
                synchronized (waitingarea) {
                    waitingarea.wait();
                }
            }
            synchronized (waitingarea) {
            	Customer c = new Customer();
                waitingarea.enter(c);
                SushiBar.customerCounter.increment(); //øker synchronized int ved navn customercount
                SushiBar.write("Customer #" + c.getCustomerID() + " is now waiting.");

                waitingarea.notify();
            }
            Thread.sleep(interval);
           
        }
        SushiBar.write("----Door is closed----");
    }  
}