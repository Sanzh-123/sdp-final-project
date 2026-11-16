package strategy;

/** Applies a percentage discount, capped at a maximum discount amount. */
public class StudentDiscount implements Discount {
    private final double rate;
    private final double limit;

    public StudentDiscount(double rate, double limit) {
        if (rate < 0.0 || rate > 1.0) {
            throw new IllegalArgumentException("Discount rate must be between 0 and 1.");
        }
        if (limit < 0.0) {
            throw new IllegalArgumentException("Discount limit cannot be negative.");
        }
        this.rate = rate;
        this.limit = limit;
    }

    @Override
    public double process(double price) {
        double safePrice = Math.max(0.0, price);
        double discountAmount = Math.min(limit, safePrice * rate);
        return safePrice - discountAmount;
    }
}
