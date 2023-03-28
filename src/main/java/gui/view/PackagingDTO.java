package gui.view;

import domain.Packaging;
import domain.PackagingType;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.math.BigDecimal;

public class PackagingDTO {

    private final SimpleIntegerProperty id;
    private final SimpleStringProperty name;
    private final SimpleObjectProperty<PackagingType> packagingType;
    private final SimpleStringProperty dimensions;
    private final SimpleObjectProperty<BigDecimal> price;
    private final SimpleBooleanProperty active;

    public PackagingDTO(Packaging packaging) {
    	this.id = new SimpleIntegerProperty(packaging.getPackagingId());
        this.name = new SimpleStringProperty(packaging.getName());
        this.packagingType = new SimpleObjectProperty<>(packaging.getType());
        this.dimensions = new SimpleStringProperty(String.format("%f x %f x %f", packaging.getWidth(), packaging.getHeight(), packaging.getLength()));
        this.price = new SimpleObjectProperty<>(packaging.getPrice());
        this.active = new SimpleBooleanProperty(packaging.isActive());
    }

    public int getPackagingId() {
        return id.get();
    }

    public SimpleIntegerProperty packagingIdProperty() {
        return id;
    }
    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public PackagingType getPackagingType() {
        return packagingType.get();
    }

    public SimpleObjectProperty<PackagingType> packagingTypeProperty() {
        return packagingType;
    }

    public String getDimensions() {
        return dimensions.get();
    }

    public SimpleStringProperty dimensionsProperty() {
        return dimensions;
    }

    public BigDecimal getPrice() {
        return price.get();
    }

    public SimpleObjectProperty<BigDecimal> priceProperty() {
        return price;
    }

    public boolean isActive() {
        return active.get();
    }

    public SimpleBooleanProperty activeProperty() {
        return active;
    }
}
