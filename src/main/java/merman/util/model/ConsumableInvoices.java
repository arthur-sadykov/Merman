package merman.util.model;

import javafx.beans.property.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Arthur Sadykov
 */
public class ConsumableInvoices implements Serializable {

    private  LongProperty id;
    private  StringProperty number;
    private  ObjectProperty<LocalDateTime> documentDate;
    private  StringProperty comment;
    private  LongProperty firm;
    private  LongProperty warehouse;
    private  LongProperty contractor;
    private  LongProperty contract;
    private  StringProperty address;
    private  StringProperty deliveryTimeFrom;
    private  StringProperty deliveryTimeTo;
    private  StringProperty powerOfAttorneyNumber;
    private  ObjectProperty<LocalDateTime> powerOfAttorneyDate;
    private  StringProperty whoAndToWhomThePowerOfAttorneyWasIssued;
    private  StringProperty carrierPosition;
    private  StringProperty consigneePosition;
    private  LongProperty dispatcher;
    private  LongProperty director;
    private  LongProperty chiefAccountant;
    private  LongProperty checkingAccount;
    private  LongProperty discount;
    private  LongProperty typeOfPrices;
    public ConsumableInvoices(Long id, String number, LocalDateTime documentDate, String comment, Long firm, Long warehouse, Long contractor, Long contract, String address, String deliveryTimeFrom, String deliveryTimeTo, String powerOfAttorneyNumber, LocalDateTime powerOfAttorneyDate, String whoAndToWhomThePowerOfAttorneyWasIssued, String carrierPosition, String consigneePosition, Long dispatcher, Long director, Long chiefAccountant, Long checkingAccount, Long discount, Long typeOfPrices) {
        this.id = id != null ? new SimpleLongProperty(id) : new SimpleLongProperty();
        this.number = number != null ? new SimpleStringProperty(number) : new SimpleStringProperty();
        this.documentDate = documentDate != null ? new SimpleObjectProperty<>(documentDate) : new SimpleObjectProperty<>();
        this.comment = comment != null ? new SimpleStringProperty(comment) : new SimpleStringProperty();
        this.firm = firm != null ? new SimpleLongProperty(firm) : new SimpleLongProperty();
        this.warehouse = warehouse != null ? new SimpleLongProperty(warehouse) : new SimpleLongProperty();
        this.contractor = contractor != null ? new SimpleLongProperty(contractor) : new SimpleLongProperty();
        this.contract = contract != null ? new SimpleLongProperty(contract) : new SimpleLongProperty();
        this.address = address != null ? new SimpleStringProperty(address) : new SimpleStringProperty();
        this.deliveryTimeFrom = deliveryTimeFrom != null ? new SimpleStringProperty(deliveryTimeFrom) : new SimpleStringProperty();
        this.deliveryTimeTo = deliveryTimeTo != null ? new SimpleStringProperty(deliveryTimeTo) : new SimpleStringProperty();
        this.powerOfAttorneyNumber = powerOfAttorneyNumber != null ? new SimpleStringProperty(powerOfAttorneyNumber) : new SimpleStringProperty();
        this.powerOfAttorneyDate = powerOfAttorneyDate != null ? new SimpleObjectProperty<>(powerOfAttorneyDate) : new SimpleObjectProperty<>();
        this.whoAndToWhomThePowerOfAttorneyWasIssued = whoAndToWhomThePowerOfAttorneyWasIssued != null ? new SimpleStringProperty(whoAndToWhomThePowerOfAttorneyWasIssued) : new SimpleStringProperty();
        this.carrierPosition = carrierPosition != null ? new SimpleStringProperty(carrierPosition) : new SimpleStringProperty();
        this.consigneePosition = consigneePosition != null ? new SimpleStringProperty(consigneePosition) : new SimpleStringProperty();
        this.dispatcher = dispatcher != null ? new SimpleLongProperty(dispatcher) : new SimpleLongProperty();
        this.director = director != null ? new SimpleLongProperty(director) : new SimpleLongProperty();
        this.chiefAccountant = chiefAccountant != null ? new SimpleLongProperty(chiefAccountant) : new SimpleLongProperty();
        this.checkingAccount = checkingAccount != null ? new SimpleLongProperty(checkingAccount) : new SimpleLongProperty();
        this.discount = discount != null ? new SimpleLongProperty(discount) : new SimpleLongProperty();
        this.typeOfPrices = typeOfPrices != null ? new SimpleLongProperty(typeOfPrices) : new SimpleLongProperty();
    }
    public final Long getId() {
        return id.get();
    }

    public final void setId(Long id) {
        this.id.set(id);
    }

    public final  LongProperty idProperty() {
        return id;
    }

    public final String getNumber() {
        return number.get();
    }

    public final void setNumber(String number) {
        this.number.set(number);
    }

    public final  StringProperty numberProperty() {
        return number;
    }

    public final LocalDateTime getDocumentDate() {
        return documentDate.get();
    }

    public final void setDocumentDate(LocalDateTime documentDate) {
        this.documentDate.set(documentDate);
    }

    public final  ObjectProperty<LocalDateTime> documentDateProperty() {
        return documentDate;
    }

    public final String getComment() {
        return comment.get();
    }

    public final void setComment(String comment) {
        this.comment.set(comment);
    }

    public final  StringProperty commentProperty() {
        return comment;
    }

    public final Long getFirm() {
        return firm.get();
    }

    public final void setFirm(Long firm) {
        this.firm.set(firm);
    }

    public final  LongProperty firmProperty() {
        return firm;
    }

    public final Long getWarehouse() {
        return warehouse.get();
    }

    public final void setWarehouse(Long warehouse) {
        this.warehouse.set(warehouse);
    }

    public final  LongProperty warehouseProperty() {
        return warehouse;
    }

    public final Long getContractor() {
        return contractor.get();
    }

    public final void setContractor(Long contractor) {
        this.contractor.set(contractor);
    }

    public final  LongProperty contractorProperty() {
        return contractor;
    }

    public final Long getContract() {
        return contract.get();
    }

    public final void setContract(Long contract) {
        this.contract.set(contract);
    }

    public final  LongProperty contractProperty() {
        return contract;
    }

    public final String getAddress() {
        return address.get();
    }

    public final void setAddress(String address) {
        this.address.set(address);
    }

    public final  StringProperty addressProperty() {
        return address;
    }

    public final String getDeliveryTimeFrom() {
        return deliveryTimeFrom.get();
    }

    public final void setDeliveryTimeFrom(String deliveryTimeFrom) {
        this.deliveryTimeFrom.set(deliveryTimeFrom);
    }

    public final  StringProperty deliveryTimeFromProperty() {
        return deliveryTimeFrom;
    }

    public final String getDeliveryTimeTo() {
        return deliveryTimeTo.get();
    }

    public final void setDeliveryTimeTo(String deliveryTimeTo) {
        this.deliveryTimeTo.set(deliveryTimeTo);
    }

    public final  StringProperty deliveryTimeToProperty() {
        return deliveryTimeTo;
    }

    public final String getPowerOfAttorneyNumber() {
        return powerOfAttorneyNumber.get();
    }

    public final void setPowerOfAttorneyNumber(String powerOfAttorneyNumber) {
        this.powerOfAttorneyNumber.set(powerOfAttorneyNumber);
    }

    public final  StringProperty powerOfAttorneyNumberProperty() {
        return powerOfAttorneyNumber;
    }

    public final LocalDateTime getPowerOfAttorneyDate() {
        return powerOfAttorneyDate.get();
    }

    public final void setPowerOfAttorneyDate(LocalDateTime powerOfAttorneyDate) {
        this.powerOfAttorneyDate.set(powerOfAttorneyDate);
    }

    public final  ObjectProperty<LocalDateTime> powerOfAttorneyDateProperty() {
        return powerOfAttorneyDate;
    }

    public final String getWhoAndToWhomThePowerOfAttorneyWasIssued() {
        return whoAndToWhomThePowerOfAttorneyWasIssued.get();
    }

    public final void setWhoAndToWhomThePowerOfAttorneyWasIssued(String whoAndToWhomThePowerOfAttorneyWasIssued) {
        this.whoAndToWhomThePowerOfAttorneyWasIssued.set(whoAndToWhomThePowerOfAttorneyWasIssued);
    }

    public final  StringProperty whoAndToWhomThePowerOfAttorneyWasIssuedProperty() {
        return whoAndToWhomThePowerOfAttorneyWasIssued;
    }

    public final String getCarrierPosition() {
        return carrierPosition.get();
    }

    public final void setCarrierPosition(String carrierPosition) {
        this.carrierPosition.set(carrierPosition);
    }

    public final  StringProperty carrierPositionProperty() {
        return carrierPosition;
    }

    public final String getConsigneePosition() {
        return consigneePosition.get();
    }

    public final void setConsigneePosition(String consigneePosition) {
        this.consigneePosition.set(consigneePosition);
    }

    public final  StringProperty consigneePositionProperty() {
        return consigneePosition;
    }

    public final Long getDispatcher() {
        return dispatcher.get();
    }

    public final void setDispatcher(Long dispatcher) {
        this.dispatcher.set(dispatcher);
    }

    public final  LongProperty dispatcherProperty() {
        return dispatcher;
    }

    public final Long getDirector() {
        return director.get();
    }

    public final void setDirector(Long director) {
        this.director.set(director);
    }

    public final  LongProperty directorProperty() {
        return director;
    }

    public final Long getChiefAccountant() {
        return chiefAccountant.get();
    }

    public final void setChiefAccountant(Long chiefAccountant) {
        this.chiefAccountant.set(chiefAccountant);
    }

    public final  LongProperty chiefAccountantProperty() {
        return chiefAccountant;
    }

    public final Long getCheckingAccount() {
        return checkingAccount.get();
    }

    public final void setCheckingAccount(Long checkingAccount) {
        this.checkingAccount.set(checkingAccount);
    }

    public final  LongProperty checkingAccountProperty() {
        return checkingAccount;
    }

    public final Long getDiscount() {
        return discount.get();
    }

    public final void setDiscount(Long discount) {
        this.discount.set(discount);
    }

    public final  LongProperty discountProperty() {
        return discount;
    }

    public final Long getTypeOfPrices() {
        return typeOfPrices.get();
    }

    public final void setTypeOfPrices(Long typeOfPrices) {
        this.typeOfPrices.set(typeOfPrices);
    }

    public final  LongProperty typeOfPricesProperty() {
        return typeOfPrices;
    }


}
