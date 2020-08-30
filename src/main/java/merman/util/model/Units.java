package merman.util.model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Arthur Sadykov
 */
public class Units {

    private LongProperty id;
    private StringProperty name;
    private StringProperty unitCode;
    private StringProperty fullName;

    public Units() {
    }

    public Units(Long id, String name, String unitCode, String fullName) {
        this.id = id != null ? new SimpleLongProperty(id) : new SimpleLongProperty();
        this.name = name != null ? new SimpleStringProperty(name) : new SimpleStringProperty();
        this.unitCode = unitCode != null ? new SimpleStringProperty(unitCode) : new SimpleStringProperty();
        this.fullName = fullName != null ? new SimpleStringProperty(fullName) : new SimpleStringProperty();
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

    public final String getUnitCode() {
        return unitCode.get();
    }

    public final void setUnitCode(String unitCode) {
        this.unitCode.set(unitCode);
    }

    public final StringProperty unitCodeProperty() {
        return unitCode;
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
}
