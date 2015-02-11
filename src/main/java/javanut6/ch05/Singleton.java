package javanut6.ch05;

public class Singleton {

    private final static Singleton instance = new Singleton();
    private static boolean initialized = false;

    // Constructor
    private Singleton() {
        super();
    }

    private void init() {
        /* Do initialization */
    }

    public static synchronized Singleton getInstance() {
        if (initialized)
            return instance;
        instance.init();
        initialized = true;
        return instance;
    }
}
