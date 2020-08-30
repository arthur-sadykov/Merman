package merman.util.model;

import javafx.beans.property.*;

import java.io.Serializable;

/**
 * @author Arthur Sadykov
 */
public class ProductCategories implements Serializable {

    private LongProperty id;
    private StringProperty name;
    private BooleanProperty disregardWeight;

    public ProductCategories() {
    }

    public ProductCategories(Long id, String name, Boolean disregardWeight) {
        this.id = id != null ? new SimpleLongProperty(id) : new SimpleLongProperty();
        this.name = name != null ? new SimpleStringProperty(name) : new SimpleStringProperty();
        this.disregardWeight = disregardWeight != null ? new SimpleBooleanProperty(disregardWeight) : new SimpleBooleanProperty();
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

    public final Boolean getDisregardWeight() {
        return disregardWeight.get();
    }

    public final void setDisregardWeight(Boolean disregardWeight) {
        this.disregardWeight.set(disregardWeight);
    }

    public final BooleanProperty disregardWeightProperty() {
        return disregardWeight;
    }
}
