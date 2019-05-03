package Sushibar;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class implements a customer, which is used for holding data and update the statistics
 *
 */
public class Customer {

	private final int ID;
	private static final AtomicInteger count = new AtomicInteger(0);
	private int numTakeAway;
	private int numEatingHere;
    /**
     *  Creates a new Customer.
     *  Each customer should be given a unique ID
     */
    public Customer() {
        // TODO Implement required functionality
    	this.ID = count.getAndIncrement();
    }

    /**
     * Here you should implement the functionality for ordering food as described in the assignment.
     */
    public synchronized void order(){
    	Random random = new Random(); 
        int orders = random.nextInt(SushiBar.maxOrder - 1) + 1;
        
        for (int i = 0; i < orders; i++) {
        	int orderType = random.nextInt(2);
        	if(orderType == 1) {
        		numTakeAway ++;
        		SushiBar.totalOrders.increment();
        		SushiBar.takeawayOrders.increment(); //øker synchronized int
        	}
        	else {
        		numEatingHere ++;
        		SushiBar.totalOrders.increment();
        		SushiBar.servedOrders.increment();
        	}
        }
    }
    
    public synchronized int getTakeAway() {
    	return this.numTakeAway;
    }
    
    public synchronized int getEatingHere() {
    	return this.numEatingHere;
    }
    
    public synchronized int getCustomerID() {
    	return ID;
    }

    public synchronized String toString() {
    	return "Customer #" + ID;
    }
    
    // Add more methods as you see fit
}
