package javanut6.ch05.burritos;

public class StandardBurrito implements Burrito {

    private static final double BASE_PRICE = 5.99;

    public double getPrice() {
        return BASE_PRICE;
    }
}
