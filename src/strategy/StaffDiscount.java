package strategy;

/** Applies a fixed-amount staff discount. */
public class StaffDiscount implements Discount {
    private final double cut;

    public StaffDiscount(double cut) {
        if (cut < 0.0) {
            throw new IllegalArgumentException("Discount cannot be negative.");
        }
        this.cut = cut;
    }

    @Override
    public double process(double price) {
        return Math.max(0.0, price - cut);
    }
}
