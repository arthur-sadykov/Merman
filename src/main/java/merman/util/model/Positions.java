package merman.util.model;

import javafx.beans.property.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Arthur Sadykov
 */
public class Positions implements Serializable {

    private LongProperty id;
    private StringProperty name;
    private ObjectProperty<BigDecimal> minimumPaymentPerDay;

    public Positions() {
    }

    public Positions(Long id, String name, BigDecimal minimumPaymentPerDay) {
        this.id = id != null ? new SimpleLongProperty(id) : new SimpleLongProperty();
        this.name = name != null ? new SimpleStringProperty(name) : new SimpleStringProperty();
        this.minimumPaymentPerDay = minimumPaymentPerDay != null ? new SimpleObjectProperty<>(minimumPaymentPerDay) : new SimpleObjectProperty<>();
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

    public final BigDecimal getMinimumPaymentPerDay() {
        return minimumPaymentPerDay.get();
    }

    public final void setMinimumPaymentPerDay(BigDecimal minimumPaymentPerDay) {
        this.minimumPaymentPerDay.set(minimumPaymentPerDay);
    }

    public final ObjectProperty<BigDecimal> minimumPaymentPerDayProperty() {
        return minimumPaymentPerDay;
    }
}
