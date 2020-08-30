package merman.util.model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;

/**
 * @author Arthur Sadykov
 */
public class Warehouses implements Serializable {

    private LongProperty id;
    private StringProperty name;
    private LongProperty storekeeper;
    private StringProperty physicalAddress;
    private StringProperty email;

    public Warehouses() {
    }

    public Warehouses(Long id, String name, Long storekeeper, String physicalAddress, String email) {
        this.id = id != null ? new SimpleLongProperty(id) : new SimpleLongProperty();
        this.name = name != null ? new SimpleStringProperty(name) : new SimpleStringProperty();
        this.storekeeper = storekeeper != null ? new SimpleLongProperty(storekeeper) : new SimpleLongProperty();
        this.physicalAddress = physicalAddress != null ? new SimpleStringProperty(physicalAddress) : new SimpleStringProperty();
        this.email = email != null ? new SimpleStringProperty(email) : new SimpleStringProperty();
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

    public final Long getStorekeeper() {
        return storekeeper.get();
    }

    public final void setStorekeeper(Long storekeeper) {
        this.storekeeper.set(storekeeper);
    }

    public final LongProperty storekeeperProperty() {
        return storekeeper;
    }

    public final String getPhysicalAddress() {
        return physicalAddress.get();
    }

    public final void setPhysicalAddress(String physicalAddress) {
        this.physicalAddress.set(physicalAddress);
    }

    public final StringProperty physicalAddressProperty() {
        return physicalAddress;
    }

    public final String getEmail() {
        return email.get();
    }

    public final void setEmail(String email) {
        this.email.set(email);
    }

    public final StringProperty emailProperty() {
        return email;
    }
}
