package javanut6.ch05.burritos;

import java.io.IOException;

public class BurritoMain {

    private static BurritoMain instance = null;

    // Standard void constructor
    public BurritoMain() {
        super();
    }

    /*
     */
    private void run() {
        Burrito lunch = new Jalapeno(new Guacamole(new SuperBurrito()));
        System.out.println("Lunch cost: " + lunch.getPrice());
    }

    /**
     * @param args
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        instance = new BurritoMain();
        instance.run();
    }

}
