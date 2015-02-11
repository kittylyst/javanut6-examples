package javanut6.ch05.burritos;

public class Jalapeno extends BurritoOptionalExtra {

    private final static double PRICE = 0.50;

    public Jalapeno(Burrito toDecorate) {
        super(toDecorate, PRICE);
    }
}
