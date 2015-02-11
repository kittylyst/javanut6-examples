package javanut6.ch05;

class Circle extends Shape {

    public static final double PI = 3.14159265358979323846;
    protected double r;                              // Instance data

    public Circle(double r) {
        this.r = r;
    }          // Constructor

    public double getRadius() {
        return r;
    }          // Accessor

    @Override
    public double area() {
        return PI * r * r;
    }          // Implementations of abstract methods.

    @Override
    public double circumference() {
        return 2 * PI * r;
    }
}
