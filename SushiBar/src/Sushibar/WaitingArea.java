package Sushibar;

import java.util.LinkedList;
import java.util.Queue;

/**
 * This class implements a waiting area used as the bounded buffer, in the producer/consumer problem.
 */
public class WaitingArea {
	private int capacitySize;
	private Queue<Customer> queue;
	
    /**
     * Creates a new waiting area.
     * areaSize decides how many people can be waiting at the same time (how large the shared buffer is)
     *
     * @param size The maximum number of Customers that can be waiting.
     */
	
    public WaitingArea(int size) {
        // TODO Implement required functionality
    	this.queue = new LinkedList<>();
    	this.capacitySize = size;
    }

    /**
     * This method should put the customer into the waitingArea
     *
     * @param customer A customer created by Door, trying to enter the waiting area
     */
    public synchronized void enter(Customer customer) {
        // TODO Implement required functionality
    	if(hasCapacity()){
    		queue.add(customer);
    	}
    }

    /**
     * @return The customer that is first in line.
     */
    public synchronized Customer next() {
        // TODO Implement required functionality
    	if (hasNext()) {
    		return queue.remove();
    	}
    	return null;
    }
    
    public synchronized boolean hasNext() {
    	return (queue.size() > 0);
    }
  
    public synchronized boolean hasCapacity() {
    	return queue.size() < capacitySize;
    }
    // Add more methods as you see fit
}
