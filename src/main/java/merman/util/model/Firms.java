package merman.util.model;

import javafx.beans.property.*;
import javafx.scene.image.Image;

import java.io.Serializable;

/**
 * @author Arthur Sadykov
 */
public class Firms implements Serializable {

    private LongProperty id;
    private StringProperty name;
    private StringProperty fullName;
    private StringProperty phone;
    private StringProperty email;
    private StringProperty physicalAddress;
    private StringProperty legalAddress;
    private StringProperty identifierSED;
    private LongProperty director;
    private LongProperty chiefAccountant;
    private StringProperty certificateOfIndividualEntrepreneur;
    private StringProperty inFaceOf;
    private StringProperty actingOnTheBasisOf;
    private StringProperty additionalInformation;
    private StringProperty inn;
    private StringProperty kpp;
    private StringProperty ogrn;
    private StringProperty okpo;
    private StringProperty okved;
    private StringProperty postcode;
    private StringProperty city;
    private StringProperty street;
    private StringProperty house;
    private StringProperty housing;
    private StringProperty apartmentsOffice;
    private ObjectProperty<Image> logo;

    public Firms() {
    }

    public Firms(Long id, String name, String fullName, String phone, String email, String physicalAddress, String legalAddress, String identifierSED, Long director, Long chiefAccountant, String certificateOfIndividualEntrepreneur, String inFaceOf, String actingOnTheBasisOf, String additionalInformation, String inn, String kpp, String ogrn, String okpo, String okved, String postcode, String city, String street, String house, String housing, String apartmentsOffice, Image logo) {
        this.id = id != null ? new SimpleLongProperty(id) : new SimpleLongProperty();
        this.name = name != null ? new SimpleStringProperty(name) : new SimpleStringProperty();
        this.fullName = fullName != null ? new SimpleStringProperty(fullName) : new SimpleStringProperty();
        this.phone = phone != null ? new SimpleStringProperty(phone) : new SimpleStringProperty();
        this.email = email != null ? new SimpleStringProperty(email) : new SimpleStringProperty();
        this.physicalAddress = physicalAddress != null ? new SimpleStringProperty(physicalAddress) : new SimpleStringProperty();
        this.legalAddress = legalAddress != null ? new SimpleStringProperty(legalAddress) : new SimpleStringProperty();
        this.identifierSED = identifierSED != null ? new SimpleStringProperty(identifierSED) : new SimpleStringProperty();
        this.director = director != null ? new SimpleLongProperty(director) : new SimpleLongProperty();
        this.chiefAccountant = chiefAccountant != null ? new SimpleLongProperty(chiefAccountant) : new SimpleLongProperty();
        this.certificateOfIndividualEntrepreneur = certificateOfIndividualEntrepreneur != null ? new SimpleStringProperty(certificateOfIndividualEntrepreneur) : new SimpleStringProperty();
        this.inFaceOf = inFaceOf != null ? new SimpleStringProperty(inFaceOf) : new SimpleStringProperty();
        this.actingOnTheBasisOf = actingOnTheBasisOf != null ? new SimpleStringProperty(actingOnTheBasisOf) : new SimpleStringProperty();
        this.additionalInformation = additionalInformation != null ? new SimpleStringProperty(additionalInformation) : new SimpleStringProperty();
        this.inn = inn != null ? new SimpleStringProperty(inn) : new SimpleStringProperty();
        this.kpp = kpp != null ? new SimpleStringProperty(kpp) : new SimpleStringProperty();
        this.ogrn = ogrn != null ? new SimpleStringProperty(ogrn) : new SimpleStringProperty();
        this.okpo = okpo != null ? new SimpleStringProperty(okpo) : new SimpleStringProperty();
        this.okved = okved != null ? new SimpleStringProperty(okved) : new SimpleStringProperty();
        this.postcode = postcode != null ? new SimpleStringProperty(postcode) : new SimpleStringProperty();
        this.city = city != null ? new SimpleStringProperty(city) : new SimpleStringProperty();
        this.street = street != null ? new SimpleStringProperty(street) : new SimpleStringProperty();
        this.house = house != null ? new SimpleStringProperty(house) : new SimpleStringProperty();
        this.housing = housing != null ? new SimpleStringProperty(housing) : new SimpleStringProperty();
        this.apartmentsOffice = apartmentsOffice != null ? new SimpleStringProperty(apartmentsOffice) : new SimpleStringProperty();
        this.logo = logo != null ? new SimpleObjectProperty<>(logo) : new SimpleObjectProperty<>();
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

    public final String getFullName() {
        return fullName.get();
    }

    public final void setFullName(String fullName) {
        this.fullName.set(fullName);
    }

    public final StringProperty fullNameProperty() {
        return fullName;
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

    public final String getPhysicalAddress() {
        return physicalAddress.get();
    }

    public final void setPhysicalAddress(String physicalAddress) {
        this.physicalAddress.set(physicalAddress);
    }

    public final StringProperty physicalAddressProperty() {
        return physicalAddress;
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

    public final String getIdentifierSED() {
        return identifierSED.get();
    }

    public final void setIdentifierSED(String identifierSED) {
        this.identifierSED.set(identifierSED);
    }

    public final StringProperty identifierSEDProperty() {
        return identifierSED;
    }

    public final Long getDirector() {
        return director.get();
    }

    public final void setDirector(Long director) {
        this.director.set(director);
    }

    public final LongProperty directorProperty() {
        return director;
    }

    public final Long getChiefAccountant() {
        return chiefAccountant.get();
    }

    public final void setChiefAccountant(Long chiefAccountant) {
        this.chiefAccountant.set(chiefAccountant);
    }

    public final LongProperty chiefAccountantProperty() {
        return chiefAccountant;
    }

    public final String getCertificateOfIndividualEntrepreneur() {
        return certificateOfIndividualEntrepreneur.get();
    }

    public final void setCertificateOfIndividualEntrepreneur(String certificateOfIndividualEntrepreneur) {
        this.certificateOfIndividualEntrepreneur.set(certificateOfIndividualEntrepreneur);
    }

    public final StringProperty certificateOfIndividualEntrepreneurProperty() {
        return certificateOfIndividualEntrepreneur;
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

    public final String getAdditionalInformation() {
        return additionalInformation.get();
    }

    public final void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation.set(additionalInformation);
    }

    public final StringProperty additionalInformationProperty() {
        return additionalInformation;
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

    public final String getKpp() {
        return kpp.get();
    }

    public final void setKpp(String kpp) {
        this.kpp.set(kpp);
    }

    public final StringProperty kppProperty() {
        return kpp;
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

    public final String getPostcode() {
        return postcode.get();
    }

    public final void setPostcode(String postcode) {
        this.postcode.set(postcode);
    }

    public final StringProperty postcodeProperty() {
        return postcode;
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

    public final String getStreet() {
        return street.get();
    }

    public final void setStreet(String street) {
        this.street.set(street);
    }

    public final StringProperty streetProperty() {
        return street;
    }

    public final String getHouse() {
        return house.get();
    }

    public final void setHouse(String house) {
        this.house.set(house);
    }

    public final StringProperty houseProperty() {
        return house;
    }

    public final String getHousing() {
        return housing.get();
    }

    public final void setHousing(String housing) {
        this.housing.set(housing);
    }

    public final StringProperty housingProperty() {
        return housing;
    }

    public final String getApartmentsOffice() {
        return apartmentsOffice.get();
    }

    public final void setApartmentsOffice(String apartmentsOffice) {
        this.apartmentsOffice.set(apartmentsOffice);
    }

    public final StringProperty apartmentsOfficeProperty() {
        return apartmentsOffice;
    }

    public final Image getLogo() {
        return logo.get();
    }

    public final void setLogo(Image logo) {
        this.logo.set(logo);
    }

    public final ObjectProperty<Image> logoProperty() {
        return logo;
    }
}
