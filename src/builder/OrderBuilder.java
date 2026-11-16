public class OrderBuilder {
	static int idCounter;
	private int id;
	private String name;
	private String date;
	private List<T> meals;
	private double price; // To do
	
	public Order() {
		this.id = ++idCounter;
	}
	
	public void setName(String name) {this.name = name;}
	public void setDate(String date) {this.date = date;}
	public void addMeal(T item) {this.meals.add(meal);}
	
	public T build() {return new Order(id, name, date, meals, price);}
	
	@Override
	public String toString {
		String mealList;
		for (T item: meals) {
			mealList += item.toString() + "\n";
		}
		return "Order{" +
			   "ID: " + id + ", " +
			   "Customer name: " + name + ", " +
			   "Order Date: " + date + "\n" +
			   mealList +
			   "Total Price: " + price + "}";
	}
}
