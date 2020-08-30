package merman.util.model;

import javafx.beans.property.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Arthur Sadykov
 */
public class VATRates implements Serializable {

    private LongProperty id;
    private StringProperty name;
    private ObjectProperty<BigDecimal> rate;

    public VATRates() {
    }

    public VATRates(Long id, String name, BigDecimal rate) {
        this.id = id != null ? new SimpleLongProperty(id) : new SimpleLongProperty();
        this.name = name != null ? new SimpleStringProperty(name) : new SimpleStringProperty();
        this.rate = rate != null ? new SimpleObjectProperty<>(rate) : new SimpleObjectProperty<>();
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

    public final BigDecimal getRate() {
        return rate.get();
    }

    public final void setRate(BigDecimal rate) {
        this.rate.set(rate);
    }

    public final ObjectProperty<BigDecimal> rateProperty() {
        return rate;
    }
}
