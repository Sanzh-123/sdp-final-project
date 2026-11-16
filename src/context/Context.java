package context;

import strategy.*;

public class Context {
    private Discount discount;
    public Context() {this.discount = new NoDiscount();}
    public Context(Discount discount) {setDiscount(discount);}
    public void setDiscount(Discount discount) {this.discount = discount;}
    public double process(double price) {
        return discount.process(price);
    }
}
