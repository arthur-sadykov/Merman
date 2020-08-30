package merman.util.model;

import javafx.beans.property.*;

import java.io.Serializable;

/**
 * @author Arthur Sadykov
 */
public class TypesOfPrices implements Serializable {

    private LongProperty id;
    private StringProperty name;
    private BooleanProperty vatIncluded;
    private LongProperty currency;

    public TypesOfPrices() {
    }

    public TypesOfPrices(Long id, String name, Boolean vatIncluded, Long currency) {
        this.id = id != null ? new SimpleLongProperty(id) : new SimpleLongProperty();
        this.name = name != null ? new SimpleStringProperty(name) : new SimpleStringProperty();
        this.vatIncluded = vatIncluded != null ? new SimpleBooleanProperty(vatIncluded) : new SimpleBooleanProperty();
        this.currency = currency != null ? new SimpleLongProperty(currency) : new SimpleLongProperty();
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

    public final String getName() {
        return name.get();
    }

    public final void setName(String name) {
        this.name.set(name);
    }

    public final StringProperty nameProperty() {
        return name;
    }

    public final Boolean getVatIncluded() {
        return vatIncluded.get();
    }

    public final void setVatIncluded(Boolean vatIncluded) {
        this.vatIncluded.set(vatIncluded);
    }

    public final BooleanProperty vatIncludedProperty() {
        return vatIncluded;
    }

    public final Long getCurrency() {
        return currency.get();
    }

    public final void setCurrency(Long currency) {
        this.currency.set(currency);
    }

    public final LongProperty currencyProperty() {
        return currency;
    }
}
