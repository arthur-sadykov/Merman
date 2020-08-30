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
public class ConsumableInvoicesProducts implements Serializable {

    private LongProperty id;
    private LongProperty product;
    private ObjectProperty<BigDecimal> quantity;
    private ObjectProperty<BigDecimal> price;
    private ObjectProperty<BigDecimal> amount;
    private LongProperty vatRate;
    private ObjectProperty<BigDecimal> vatAmount;
    private LongProperty flipContainer;
    private ObjectProperty<BigDecimal> productRemainder;
    private LongProperty baseTable;

    public ConsumableInvoicesProducts() {
    }

    public ConsumableInvoicesProducts(Long id, Long product, BigDecimal quantity, BigDecimal price, BigDecimal amount, Long vatRate, BigDecimal vatAmount, Long flipContainer, BigDecimal productRemainder, Long baseTable) {
        this.id = id != null ? new SimpleLongProperty(id) : new SimpleLongProperty();
        this.product = product != null ? new SimpleLongProperty(product) : new SimpleLongProperty();
        this.quantity = quantity != null ? new SimpleObjectProperty<>(quantity) : new SimpleObjectProperty<>();
        this.price = price != null ? new SimpleObjectProperty<>(price) : new SimpleObjectProperty<>();
        this.amount = amount != null ? new SimpleObjectProperty<>(amount) : new SimpleObjectProperty<>();
        this.vatRate = vatRate != null ? new SimpleLongProperty(vatRate) : new SimpleLongProperty();
        this.vatAmount = vatAmount != null ? new SimpleObjectProperty<>(vatAmount) : new SimpleObjectProperty<>();
        this.flipContainer = flipContainer != null ? new SimpleLongProperty(flipContainer) : new SimpleLongProperty();
        this.productRemainder = productRemainder != null ? new SimpleObjectProperty<>(productRemainder) : new SimpleObjectProperty<>();
        this.baseTable = baseTable != null ? new SimpleLongProperty(baseTable) : new SimpleLongProperty();
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

    public final Long getProduct() {
        return product.get();
    }

    public final void setProduct(Long product) {
        this.product.set(product);
    }

    public final LongProperty productProperty() {
        return product;
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

    public final BigDecimal getPrice() {
        return price.get();
    }

    public final void setPrice(BigDecimal price) {
        this.price.set(price);
    }

    public final ObjectProperty<BigDecimal> priceProperty() {
        return price;
    }

    public final BigDecimal getAmount() {
        return amount.get();
    }

    public final void setAmount(BigDecimal amount) {
        this.amount.set(amount);
    }

    public ObjectProperty<BigDecimal> amountProperty() {
        return amount;
    }

    public final Long getVatRate() {
        return vatRate.get();
    }

    public final void setVatRate(Long vatRate) {
        this.vatRate.set(vatRate);
    }

    public final LongProperty vatRateProperty() {
        return vatRate;
    }

    public final BigDecimal getVatAmount() {
        return vatAmount.get();
    }

    public final void setVatAmount(BigDecimal vatAmount) {
        this.vatAmount.set(vatAmount);
    }

    public ObjectProperty<BigDecimal> vatAmountProperty() {
        return vatAmount;
    }

    public final Long getFlipContainer() {
        return flipContainer.get();
    }

    public final void setFlipContainer(Long flipContainer) {
        this.flipContainer.set(flipContainer);
    }

    public final LongProperty flipContainerProperty() {
        return flipContainer;
    }

    public final BigDecimal getProductRemainder() {
        return productRemainder.get();
    }

    public final void setProductRemainder(BigDecimal productRemainder) {
        this.productRemainder.set(productRemainder);
    }

    public final ObjectProperty<BigDecimal> productRemainderProperty() {
        return productRemainder;
    }

    public final Long getBaseTable() {
        return baseTable.get();
    }

    public final void setBaseTable(Long baseTable) {
        this.baseTable.set(baseTable);
    }

    public final LongProperty baseTableProperty() {
        return baseTable;
    }
}
