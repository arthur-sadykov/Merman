package merman.util.model;

import javafx.beans.property.*;

import java.io.Serializable;

/**
 * @author Arthur Sadykov
 */
public class ContractorTypes implements Serializable {

    private LongProperty id;
    private StringProperty name;
    private BooleanProperty isSupplier;
    private BooleanProperty isCustomer;

    public ContractorTypes() {
    }

    public ContractorTypes(Long id, String name, Boolean isSupplier, Boolean isCustomer) {
        this.id = id != null ? new SimpleLongProperty(id) : new SimpleLongProperty();
        this.name = name != null ? new SimpleStringProperty(name) : new SimpleStringProperty();
        this.isSupplier = isSupplier != null ? new SimpleBooleanProperty(isSupplier) : new SimpleBooleanProperty();
        this.isCustomer = isCustomer != null ? new SimpleBooleanProperty(isCustomer) : new SimpleBooleanProperty();
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

    public final Boolean getIsSupplier() {
        return isSupplier.get();
    }

    public final void setIsSupplier(Boolean isSupplier) {
        this.isSupplier.set(isSupplier);
    }

    public final BooleanProperty isSupplierProperty() {
        return isSupplier;
    }

    public final Boolean getIsCustomer() {
        return isCustomer.get();
    }

    public final void setIsCustomer(Boolean isCustomer) {
        this.isCustomer.set(isCustomer);
    }

    public final BooleanProperty isCustomerProperty() {
        return isCustomer;
    }
}
