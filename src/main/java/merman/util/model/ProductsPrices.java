package merman.util.model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Arthur Sadykov
 */
public class ProductsPrices implements Serializable {

    private final LongProperty id;
    private final LongProperty priceType;
    private final ObjectProperty<BigDecimal> price;
    private final ObjectProperty<BigDecimal> quantity;

    public ProductsPrices() {
        this.id = new SimpleLongProperty();
        this.priceType = new SimpleLongProperty();
        this.price = new SimpleObjectProperty<>();
        this.quantity = new SimpleObjectProperty<>();
    }


    public ProductsPrices(Long id, Long priceType, BigDecimal price, BigDecimal quantity) {
        this.id = id != null ? new SimpleLongProperty(id) : new SimpleLongProperty();
        this.priceType = priceType != null ? new SimpleLongProperty(priceType) : new SimpleLongProperty();
        this.price = price != null ? new SimpleObjectProperty<>(price) : new SimpleObjectProperty<>();
        this.quantity = quantity != null ? new SimpleObjectProperty<>(quantity) : new SimpleObjectProperty<>();
    }

    public final Long getId() {
        return id.get();
    }

    public final void setId(Long id) {
        this.id.set(id);
    }

    public final LongProperty idProperty() {
        return id;
    }

    public final Long getPriceType() {
        return priceType.get();
    }

    public final void setPriceType(Long priceType) {
        this.priceType.set(priceType);
    }

    public final LongProperty priceTypeProperty() {
        return priceType;
    }

    public final BigDecimal getPrice() {
        return price.get();
    }

    public final void setPrice(BigDecimal price) {
        this.price.set(price);
    }

    public final ObjectProperty<BigDecimal> priceProperty() {
        return price;
    }

    public final BigDecimal getQuantity() {
        return quantity.get();
    }

    public final void setQuantity(BigDecimal quantity) {
        this.quantity.set(quantity);
    }

    public final ObjectProperty<BigDecimal> quantityProperty() {
        return quantity;
    }
}
