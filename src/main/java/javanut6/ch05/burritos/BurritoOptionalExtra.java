package javanut6.ch05.burritos;

// BEGIN BURRITO_DECORATOR
/*
 * This class is the Decorator for Burrito - it represents optional extras
 * that the burrito may or may not have.
 */
public abstract class BurritoOptionalExtra implements Burrito {

    private final Burrito burrito;
    private final double price;

    // This constructor is protected to protect against the default
    // constructor and to prevent rogue client code from directly
    // instantiating the base class.
    protected BurritoOptionalExtra(Burrito toDecorate, double myPrice) {
        burrito = toDecorate;
        price = myPrice;
    }

    public final double getPrice() {
        return (burrito.getPrice() + price);
    }
}
// END BURRITO_DECORATOR
