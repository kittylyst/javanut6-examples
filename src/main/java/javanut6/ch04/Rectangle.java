package javanut6.ch04;

class Rectangle extends Shape {

    protected double w, h;                               // Instance data

    public Rectangle(double w, double h) {               // Constructor
        this.w = w;
        this.h = h;
    }

    public double getWidth() {
        return w;
    }               // Accessor method

    public double getHeight() {
        return h;
    }              // Another accessor

    @Override
    public double area() {
        return w * h;
    }                 // Implementations of abstract methods.

    @Override
    public double circumference() {
        return 2 * (w + h);
    }
}
