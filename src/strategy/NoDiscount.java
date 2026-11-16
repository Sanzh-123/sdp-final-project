package strategy;

public class NoDiscount implements Discount {
    @Override
    public double process(double price) {
        return Math.max(0.0, price);
    }
}
