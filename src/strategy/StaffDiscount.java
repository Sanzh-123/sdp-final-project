public class StaffDiscount extends Discount {
	private double cut;
	
	public StaffDiscount(double cut) {
		this.cut = cut;
	}
	
	@Override
	public double process (double price) {
		return Math.max(0.0, price - cut);
	}
}
