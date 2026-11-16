package strategy;

/** Strategy interface for calculating an order discount. */
public interface Discount {
    double process(double price);
}
