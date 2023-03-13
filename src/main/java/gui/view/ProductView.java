package gui.view;

import domain.Product;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ProductView {

	private final SimpleStringProperty productName;
	private final SimpleIntegerProperty amount;
	private final SimpleDoubleProperty untitPrice;
	private final SimpleDoubleProperty totalAmount;
	
	public ProductView(Product product) {
		int count = 0;
		for (Product p : product.getOrders().get(0).getProductsList()) {
			if (p == product) {
				count += 1;
			}
		}
		productName = new SimpleStringProperty(product.getName());
		amount = new SimpleIntegerProperty(count);	
		untitPrice = new SimpleDoubleProperty(product.getPrice().doubleValue());	
		totalAmount = new SimpleDoubleProperty(product.getPrice().doubleValue() * (count * 1.00));
	}

	public SimpleStringProperty productNameProperty() {
		return productName;
	}
	
	public SimpleDoubleProperty untitPriceProperty() {
		return untitPrice;
	}
	
	public SimpleDoubleProperty totalAmountProperty() {
		return totalAmount;
	}
	
	public SimpleIntegerProperty amountProperty() {
		return amount;
	}
	
}