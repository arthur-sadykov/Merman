package merman.util.model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;

/**
 * @author Arthur Sadykov
 */
public class Banks implements Serializable {

    private final LongProperty id;
    private final StringProperty name;
    private final StringProperty BIK;
    private final StringProperty correspondentAccount;
    private final StringProperty city;

    public Banks(Long id, String name, String BIK, String correspondentAccount, String city) {
        this.id = id != null ? new SimpleLongProperty(id) : new SimpleLongProperty();
        this.name = name != null ? new SimpleStringProperty(name) : new SimpleStringProperty();
        this.BIK = BIK != null ? new SimpleStringProperty(BIK) : new SimpleStringProperty();
        this.correspondentAccount = correspondentAccount != null ? new SimpleStringProperty(correspondentAccount) : new SimpleStringProperty();
        this.city = city != null ? new SimpleStringProperty(city) : new SimpleStringProperty();
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

    public final String getBIK() {
        return BIK.get();
    }

    public final void setBIK(String BIK) {
        this.BIK.set(BIK);
    }

    public final StringProperty BIKProperty() {
        return BIK;
    }

    public final String getCorrespondentAccount() {
        return correspondentAccount.get();
    }

    public final void setCorrespondentAccount(String correspondentAccount) {
        this.correspondentAccount.set(correspondentAccount);
    }

    public final StringProperty correspondentAccountProperty() {
        return correspondentAccount;
    }

    public final String getCity() {
        return city.get();
    }

    public final void setCity(String city) {
        this.city.set(city);
    }

    public final StringProperty cityProperty() {
        return city;
    }
}
