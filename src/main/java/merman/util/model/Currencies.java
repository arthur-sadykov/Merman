package merman.util.model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;

/**
 * @author Arthur Sadykov
 */
public class Currencies implements Serializable {

    private final LongProperty id;
    private final StringProperty name;
    private final StringProperty currencyCode;
    private final StringProperty fullName;
    private final StringProperty currencyShare;

    public Currencies(Long id, String name, String currencyCode, String fullName, String currencyShare) {
        this.id = id != null ? new SimpleLongProperty(id) : new SimpleLongProperty();
        this.name = name != null ? new SimpleStringProperty(name) : new SimpleStringProperty();
        this.currencyCode = currencyCode != null ? new SimpleStringProperty(currencyCode) : new SimpleStringProperty();
        this.fullName = fullName != null ? new SimpleStringProperty(fullName) : new SimpleStringProperty();
        this.currencyShare = currencyShare != null ? new SimpleStringProperty(currencyShare) : new SimpleStringProperty();
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

    public final String getCurrencyCode() {
        return currencyCode.get();
    }

    public final void setCurrencyCode(String currencyCode) {
        this.currencyCode.set(currencyCode);
    }

    public final StringProperty currencyCodeProperty() {
        return currencyCode;
    }

    public final String getFullName() {
        return fullName.get();
    }

    public final void setFullName(String fullName) {
        this.fullName.set(fullName);
    }

    public final StringProperty fullNameProperty() {
        return fullName;
    }

    public final String getCurrencyShare() {
        return currencyShare.get();
    }

    public final void setCurrencyShare(String currencyShare) {
        this.currencyShare.set(currencyShare);
    }

    public final StringProperty currencyShareProperty() {
        return currencyShare;
    }
}
