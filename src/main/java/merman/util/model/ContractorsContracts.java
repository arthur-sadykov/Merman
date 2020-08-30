package merman.util.model;

import javafx.beans.property.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Arthur Sadykov
 */
public class ContractorsContracts implements Serializable {

    private LongProperty id;
    private StringProperty name;
    private IntegerProperty creditDays;
    private ObjectProperty<BigDecimal> creditAmount;
    private ObjectProperty<LocalDateTime> dateOfContract;
    private ObjectProperty<LocalDateTime> dateOfTermination;
    private StringProperty becauseOf;
    private LongProperty dispatcher;

    public ContractorsContracts() {
    }

    public ContractorsContracts(Long id, String name, Integer creditDays, BigDecimal creditAmount, LocalDateTime dateOfContract, LocalDateTime dateOfTermination, String becauseOf, Long dispatcher) {
        this.id = id != null ? new SimpleLongProperty(id) : new SimpleLongProperty();
        this.name = name != null ? new SimpleStringProperty(name) : new SimpleStringProperty();
        this.creditDays = creditDays != null ? new SimpleIntegerProperty(creditDays) : new SimpleIntegerProperty();
        this.creditAmount = creditAmount != null ? new SimpleObjectProperty<>(creditAmount) : new SimpleObjectProperty<>();
        this.dateOfContract = dateOfContract != null ? new SimpleObjectProperty<>(dateOfContract) : new SimpleObjectProperty<>();
        this.dateOfTermination = dateOfTermination != null ? new SimpleObjectProperty<>(dateOfTermination) : new SimpleObjectProperty<>();
        this.becauseOf = becauseOf != null ? new SimpleStringProperty(becauseOf) : new SimpleStringProperty();
        this.dispatcher = dispatcher != null ? new SimpleLongProperty(dispatcher) : new SimpleLongProperty();
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

    public final Integer getCreditDays() {
        return creditDays.get();
    }

    public final void setCreditDays(Integer creditDays) {
        this.creditDays.set(creditDays);
    }

    public final IntegerProperty creditDaysProperty() {
        return creditDays;
    }

    public final BigDecimal getCreditAmount() {
        return creditAmount.get();
    }

    public final void setCreditAmount(BigDecimal creditAmount) {
        this.creditAmount.set(creditAmount);
    }

    public final ObjectProperty<BigDecimal> creditAmountProperty() {
        return creditAmount;
    }

    public final LocalDateTime getDateOfContract() {
        return dateOfContract.get();
    }

    public final void setDateOfContract(LocalDateTime dateOfContract) {
        this.dateOfContract.set(dateOfContract);
    }

    public final ObjectProperty<LocalDateTime> dateOfContractProperty() {
        return dateOfContract;
    }

    public final LocalDateTime getDateOfTermination() {
        return dateOfTermination.get();
    }

    public final void setDateOfTermination(LocalDateTime dateOfTermination) {
        this.dateOfTermination.set(dateOfTermination);
    }

    public final ObjectProperty<LocalDateTime> dateOfTerminationProperty() {
        return dateOfTermination;
    }

    public final String getBecauseOf() {
        return becauseOf.get();
    }

    public final void setBecauseOf(String becauseOf) {
        this.becauseOf.set(becauseOf);
    }

    public final StringProperty becauseOfProperty() {
        return becauseOf;
    }

    public final Long getDispatcher() {
        return dispatcher.get();
    }

    public final void setDispatcher(Long dispatcher) {
        this.dispatcher.set(dispatcher);
    }

    public final LongProperty dispatcherProperty() {
        return dispatcher;
    }
}
