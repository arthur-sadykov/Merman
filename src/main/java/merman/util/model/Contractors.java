package merman.util.model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;


public class Contractors implements Serializable {

    private LongProperty id;
    private StringProperty name;
    private LongProperty contractorType;
    private LongProperty dispatcher;
    private StringProperty phone;
    private StringProperty email;
    private LongProperty priceType;
    private LongProperty discountType;
    private LongProperty formOfPayment;
    private LongProperty firm;
    private LongProperty dealer;
    private StringProperty additionalInformation;
    private StringProperty fullName;
    private StringProperty legalAddress;
    private StringProperty physicalAddress;
    private StringProperty inn;
    private StringProperty ogrn;
    private StringProperty okpo;
    private StringProperty okved;
    private StringProperty director;
    private StringProperty inFaceOf;
    private StringProperty actingOnTheBasisOf;
    private StringProperty cardNumber;
    private StringProperty passport;
    private StringProperty identifierSED;

    public Contractors() {
    }

    public Contractors(Long id, String name, Long contractorType, Long dispatcher, String phone, String email, Long priceType, Long discountType, Long formOfPayment, Long firm, Long dealer, String additionalInformation, String fullName, String legalAddress, String physicalAddress, String inn, String ogrn, String okpo, String okved, String director, String inFaceOf, String actingOnTheBasisOf, String cardNumber, String passport, String identifierSED) {
        this.id = id != null ? new SimpleLongProperty(id) : new SimpleLongProperty();
        this.name = name != null ? new SimpleStringProperty(name) : new SimpleStringProperty();
        this.contractorType = contractorType != null ? new SimpleLongProperty(contractorType) : new SimpleLongProperty();
        this.dispatcher = dispatcher != null ? new SimpleLongProperty(dispatcher) : new SimpleLongProperty();
        this.phone = phone != null ? new SimpleStringProperty(phone) : new SimpleStringProperty();
        this.email = email != null ? new SimpleStringProperty(email) : new SimpleStringProperty();
        this.priceType = priceType != null ? new SimpleLongProperty(priceType) : new SimpleLongProperty();
        this.discountType = discountType != null ? new SimpleLongProperty(discountType) : new SimpleLongProperty();
        this.formOfPayment = formOfPayment != null ? new SimpleLongProperty(formOfPayment) : new SimpleLongProperty();
        this.firm = firm != null ? new SimpleLongProperty(firm) : new SimpleLongProperty();
        this.dealer = dealer != null ? new SimpleLongProperty(dealer) : new SimpleLongProperty();
        this.additionalInformation = additionalInformation != null ? new SimpleStringProperty(additionalInformation) : new SimpleStringProperty();
        this.fullName = fullName != null ? new SimpleStringProperty(fullName) : new SimpleStringProperty();
        this.legalAddress = legalAddress != null ? new SimpleStringProperty(legalAddress) : new SimpleStringProperty();
        this.physicalAddress = physicalAddress != null ? new SimpleStringProperty(physicalAddress) : new SimpleStringProperty();
        this.inn = inn != null ? new SimpleStringProperty(inn) : new SimpleStringProperty();
        this.ogrn = ogrn != null ? new SimpleStringProperty(ogrn) : new SimpleStringProperty();
        this.okpo = okpo != null ? new SimpleStringProperty(okpo) : new SimpleStringProperty();
        this.okved = okved != null ? new SimpleStringProperty(okved) : new SimpleStringProperty();
        this.director = director != null ? new SimpleStringProperty(director) : new SimpleStringProperty();
        this.inFaceOf = inFaceOf != null ? new SimpleStringProperty(inFaceOf) : new SimpleStringProperty();
        this.actingOnTheBasisOf = actingOnTheBasisOf != null ? new SimpleStringProperty(actingOnTheBasisOf) : new SimpleStringProperty();
        this.cardNumber = cardNumber != null ? new SimpleStringProperty(cardNumber) : new SimpleStringProperty();
        this.passport = passport != null ? new SimpleStringProperty(passport) : new SimpleStringProperty();
        this.identifierSED = identifierSED != null ? new SimpleStringProperty(identifierSED) : new SimpleStringProperty();
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

    public final Long getContractorType() {
        return contractorType.get();
    }

    public final void setContractorType(Long contractorType) {
        this.contractorType.set(contractorType);
    }

    public final LongProperty contractorTypeProperty() {
        return contractorType;
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

    public final String getPhone() {
        return phone.get();
    }

    public final void setPhone(String phone) {
        this.phone.set(phone);
    }

    public final StringProperty phoneProperty() {
        return phone;
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

    public final Long getPriceType() {
        return priceType.get();
    }

    public final void setPriceType(Long priceType) {
        this.priceType.set(priceType);
    }

    public final LongProperty priceTypeProperty() {
        return priceType;
    }

    public final Long getDiscountType() {
        return discountType.get();
    }

    public final void setDiscountType(Long discountType) {
        this.discountType.set(discountType);
    }

    public final LongProperty discountTypeProperty() {
        return discountType;
    }

    public final Long getFormOfPayment() {
        return formOfPayment.get();
    }

    public final void setFormOfPayment(Long formOfPayment) {
        this.formOfPayment.set(formOfPayment);
    }

    public final LongProperty formOfPaymentProperty() {
        return formOfPayment;
    }

    public final Long getFirm() {
        return firm.get();
    }

    public final void setFirm(Long firm) {
        this.firm.set(firm);
    }

    public final LongProperty firmProperty() {
        return firm;
    }

    public final Long getDealer() {
        return dealer.get();
    }

    public final void setDealer(Long dealer) {
        this.dealer.set(dealer);
    }

    public final LongProperty dealerProperty() {
        return dealer;
    }

    public final String getAdditionalInformation() {
        return additionalInformation.get();
    }

    public final void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation.set(additionalInformation);
    }

    public final StringProperty additionalInformationProperty() {
        return additionalInformation;
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

    public final String getLegalAddress() {
        return legalAddress.get();
    }

    public final void setLegalAddress(String legalAddress) {
        this.legalAddress.set(legalAddress);
    }

    public final StringProperty legalAddressProperty() {
        return legalAddress;
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

    public final String getInn() {
        return inn.get();
    }

    public final void setInn(String inn) {
        this.inn.set(inn);
    }

    public final StringProperty innProperty() {
        return inn;
    }

    public final String getOgrn() {
        return ogrn.get();
    }

    public final void setOgrn(String ogrn) {
        this.ogrn.set(ogrn);
    }

    public final StringProperty ogrnProperty() {
        return ogrn;
    }

    public final String getOkpo() {
        return okpo.get();
    }

    public final void setOkpo(String okpo) {
        this.okpo.set(okpo);
    }

    public final StringProperty okpoProperty() {
        return okpo;
    }

    public final String getOkved() {
        return okved.get();
    }

    public final void setOkved(String okved) {
        this.okved.set(okved);
    }

    public final StringProperty okvedProperty() {
        return okved;
    }

    public final String getDirector() {
        return director.get();
    }

    public final void setDirector(String director) {
        this.director.set(director);
    }

    public final StringProperty directorProperty() {
        return director;
    }

    public final String getInFaceOf() {
        return inFaceOf.get();
    }

    public final void setInFaceOf(String inFaceOf) {
        this.inFaceOf.set(inFaceOf);
    }

    public final StringProperty inFaceOfProperty() {
        return inFaceOf;
    }

    public final String getActingOnTheBasisOf() {
        return actingOnTheBasisOf.get();
    }

    public final void setActingOnTheBasisOf(String actingOnTheBasisOf) {
        this.actingOnTheBasisOf.set(actingOnTheBasisOf);
    }

    public final StringProperty actingOnTheBasisOfProperty() {
        return actingOnTheBasisOf;
    }

    public final String getCardNumber() {
        return cardNumber.get();
    }

    public final void setCardNumber(String cardNumber) {
        this.cardNumber.set(cardNumber);
    }

    public final StringProperty cardNumberProperty() {
        return cardNumber;
    }

    public final String getPassport() {
        return passport.get();
    }

    public final void setPassport(String passport) {
        this.passport.set(passport);
    }

    public final StringProperty passportProperty() {
        return passport;
    }

    public final String getIdentifierSED() {
        return identifierSED.get();
    }

    public final void setIdentifierSED(String identifierSED) {
        this.identifierSED.set(identifierSED);
    }

    public final StringProperty identifierSEDProperty() {
        return identifierSED;
    }
}
