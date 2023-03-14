package gui.view;

import domain.Product;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.math.BigDecimal;

public class ProductView {

	private final SimpleStringProperty productName;
	private final SimpleIntegerProperty amount;
	private final SimpleDoubleProperty untitPrice;
	private final SimpleObjectProperty<BigDecimal> totalPrice;
	
	public ProductView(Product product, int amount) {
		this.productName = new SimpleStringProperty(product.getName());
		this.amount = new SimpleIntegerProperty(amount);
		this.untitPrice = new SimpleDoubleProperty(product.getPrice().doubleValue());
		this.totalPrice = new SimpleObjectProperty<>(product.getPrice().multiply(BigDecimal.valueOf(amount))); // TODO
	}

	public SimpleStringProperty productNameProperty() {
		return productName;
	}
	
	public SimpleDoubleProperty untitPriceProperty() {
		return untitPrice;
	}
	
	public SimpleObjectProperty<BigDecimal> totalPriceProperty() {
		return totalPrice;
	}
	
	public SimpleIntegerProperty amountProperty() {
		return amount;
	}
	
}