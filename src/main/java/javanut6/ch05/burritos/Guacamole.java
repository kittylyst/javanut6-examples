package javanut6.ch05.burritos;

public class Guacamole extends BurritoOptionalExtra {

    private final static double PRICE = 0.60;

    public Guacamole(Burrito toDecorate) {
        super(toDecorate, PRICE);
    }
}
