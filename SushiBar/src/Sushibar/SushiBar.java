package Sushibar;

import java.util.List;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class SushiBar {

    //SushiBar settings
    private static int waitingAreaCapacity = 20;
    private static int waitressCount = 10;
    private static int duration = 3; //Openingtime
    public static int maxOrder = 10;
    public static int waitressWait = 50; // Used to calculate the time the waitress spends before taking the order
    public static int customerWait = 3000; // Used to calculate the time the customer uses eating
    public static int doorWait = 100; // Used to calculate the interval at which the door tries to create a customer
    public static boolean isOpen = true;

    //Creating log file
    private static File log;
    private static String path = "./";
    
    //Variables related to statistics
    public static SynchronizedInteger customerCounter;
    public static SynchronizedInteger servedOrders;
    public static SynchronizedInteger takeawayOrders;
    public static SynchronizedInteger totalOrders;


    public static void main(String[] args) {
        log = new File(path + "log.txt");

        //Initializing shared variables for counting number of orders
        customerCounter = new SynchronizedInteger(0);
        totalOrders = new SynchronizedInteger(0);
        servedOrders = new SynchronizedInteger(0);
        takeawayOrders = new SynchronizedInteger(0);

        // TODO initialize the bar and start the different threads
        SushiBar sushibar = new SushiBar();
        Clock clock = new Clock(duration);
        WaitingArea waitingarea = new WaitingArea(waitingAreaCapacity);
        Thread door = new Thread(new Door(waitingarea));
        door.start();

        List<Thread> waitresses = new ArrayList<>();
        for (int i = 0; i < waitressCount; i++) {
            Waitress w = new Waitress(waitingarea);
            Thread waitress = new Thread(w);
            waitress.setName("Waitress #" + i);
            waitresses.add(waitress);
            waitress.start();
        }
       
        for (Thread wait : waitresses) {
            try {
                wait.join();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
       
        try {
            door.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
 
        printClosedShop();
    }


    //Writes actions in the log file and console
    public static void write(String str) {
        try {
            FileWriter fw = new FileWriter(log.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(Clock.getTime() + ", " + str + "\n");
            bw.close();
            System.out.println(Clock.getTime() + ", " + str);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public static void printClosedShop() {
        SushiBar.write("***** NO MORE CUSTOMERS - THE SHOP IS NOW CLOSED *****");
        SushiBar.write("");
        SushiBar.write("STATISTICS FOR TODAY:");
        SushiBar.write("Number of customers: " + SushiBar.customerCounter.get());
        SushiBar.write("Eaten orders: " + SushiBar.servedOrders.get());
        SushiBar.write("Takeaway orders: " + SushiBar.takeawayOrders.get());
        SushiBar.write("Total orders: " + SushiBar.totalOrders.get());
        SushiBar.write("FINITO");
        // Closing program.
        System.exit(0);
    }
}
