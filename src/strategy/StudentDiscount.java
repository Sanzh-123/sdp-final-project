public class StudentDiscount extends Discount {
	private double limit;
	
	public StudentDiscount(double rate, double limit) {
		super(rate);
		this.limit = limit;
	}
	
	@Override
	public double process (double price) {
		return price - Math.min(limit, price * rate);
	}
}
