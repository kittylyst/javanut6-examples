package javanut6.ch01;

import java.io.IOException;

/*
 * A standard scratch pad class that I often use when exploring a simple
 * java library or reminding myself of how Java langauge features interact
 *
 */
public class ScratchImpl {

    private static ScratchImpl instance = null;

    // Constructor
    public ScratchImpl() {
        super();
    }

    /*
     * This is where your actual code will go
     */
    private void run() {

    }

    /**
     * @param args
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        instance = new ScratchImpl();
        instance.run();
    }

}
