package merman.util.model;

import javafx.beans.property.*;

import java.io.Serializable;

/**
 * @author Arthur Sadykov
 */
public class FirmsBankAccounts implements Serializable {

    private final LongProperty id;
    private final StringProperty name;
    private final LongProperty bank;
    private final BooleanProperty main;
    private final LongProperty basicTable;

    public FirmsBankAccounts(Long id, String name, Long bank, Boolean main, Long basicTable) {
        this.id = id != null ? new SimpleLongProperty(id) : new SimpleLongProperty();
        this.name = name != null ? new SimpleStringProperty(name) : new SimpleStringProperty();
        this.bank = bank != null ? new SimpleLongProperty(bank) : new SimpleLongProperty();
        this.main = main != null ? new SimpleBooleanProperty(main) : new SimpleBooleanProperty();
        this.basicTable = basicTable != null ? new SimpleLongProperty(basicTable) : new SimpleLongProperty();
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

    public final Long getBank() {
        return bank.get();
    }

    public final void setBank(Long bank) {
        this.bank.set(bank);
    }

    public final LongProperty bankProperty() {
        return bank;
    }

    public final Boolean getMain() {
        return main.get();
    }

    public final void setMain(Boolean main) {
        this.main.set(main);
    }

    public final BooleanProperty mainProperty() {
        return main;
    }

    public final Long getBasicTable() {
        return basicTable.get();
    }

    public final void setBasicTable(Long basicTable) {
        this.basicTable.set(basicTable);
    }

    public final LongProperty basicTableProperty() {
        return basicTable;
    }
}
