package strategy;

public class StudentDiscount implements Discount {
    private double rate, limit;
    public StudentDiscount(double rate, double limit) {
        this.rate = rate;
        this.limit = limit;
    }
    @Override
    public double process (double price) {return price - Math.min(limit, price * rate);}
}
