package strategy;

/** Context object that delegates discount calculation to the selected strategy. */
public class Context {
    private Discount discount;

    public Context() {
        this.discount = new NoDiscount();
    }

    public Context(Discount discount) {
        validateDiscount(discount);
        this.discount = discount;
    }

    public void setDiscount(Discount discount) {
        validateDiscount(discount);
        this.discount = discount;
    }

    private static void validateDiscount(Discount discount) {
        if (discount == null) {
            throw new IllegalArgumentException("Discount strategy cannot be null.");
        }
    }

    public double process(double price) {
        return discount.process(price);
    }
}
