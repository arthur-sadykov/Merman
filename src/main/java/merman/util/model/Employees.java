package merman.util.model;

import javafx.beans.property.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Arthur Sadykov
 */
public class Employees implements Serializable {

    private LongProperty id;
    private StringProperty surname;
    private StringProperty name;
    private StringProperty patronymic;
    private LongProperty position;
    private StringProperty phone;
    private StringProperty additionalInformation;
    private StringProperty passport;
    private ObjectProperty<LocalDateTime> birthDate;
    private ObjectProperty<LocalDateTime> hireDate;
    private BooleanProperty dismissed;
    private StringProperty driverLicense;
    private StringProperty trustedPerson;
    private BooleanProperty notShowInStatements;

    public Employees() {
    }

    public Employees(Long id, String surname, String name, String patronymic, Long position, String phone, String additionalInformation, String passport, LocalDateTime birthDate, LocalDateTime hireDate, Boolean dismissed, String driverLicense, String trustedPerson, Boolean notShowInStatements) {
        this.id = id != null ? new SimpleLongProperty(id) : new SimpleLongProperty();
        this.surname = surname != null ? new SimpleStringProperty(surname) : new SimpleStringProperty();
        this.name = name != null ? new SimpleStringProperty(name) : new SimpleStringProperty();
        this.patronymic = patronymic != null ? new SimpleStringProperty(patronymic) : new SimpleStringProperty();
        this.position = position != null ? new SimpleLongProperty(position) : new SimpleLongProperty();
        this.phone = phone != null ? new SimpleStringProperty(phone) : new SimpleStringProperty();
        this.additionalInformation = additionalInformation != null ? new SimpleStringProperty(additionalInformation) : new SimpleStringProperty();
        this.passport = passport != null ? new SimpleStringProperty(passport) : new SimpleStringProperty();
        this.birthDate = birthDate != null ? new SimpleObjectProperty<>(birthDate) : new SimpleObjectProperty<>();
        this.hireDate = hireDate != null ? new SimpleObjectProperty<>(hireDate) : new SimpleObjectProperty<>();
        this.dismissed = dismissed != null ? new SimpleBooleanProperty(dismissed) : new SimpleBooleanProperty();
        this.driverLicense = driverLicense != null ? new SimpleStringProperty(driverLicense) : new SimpleStringProperty();
        this.trustedPerson = trustedPerson != null ? new SimpleStringProperty(trustedPerson) : new SimpleStringProperty();
        this.notShowInStatements = notShowInStatements != null ? new SimpleBooleanProperty(notShowInStatements) : new SimpleBooleanProperty();
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


    public final String getSurname() {
        return surname.get();
    }

    public final void setSurname(String surname) {
        this.surname.set(surname);
    }

    public final StringProperty surnameProperty() {
        return surname;
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

    public final String getPatronymic() {
        return patronymic.get();
    }

    public final void setPatronymic(String patronymic) {
        this.patronymic.set(patronymic);
    }

    public final StringProperty patronymicProperty() {
        return patronymic;
    }

    public final Long getPosition() {
        return position.get();
    }

    public final void setPosition(Long position) {
        this.position.set(position);
    }

    public final LongProperty positionProperty() {
        return position;
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

    public final String getAdditionalInformation() {
        return additionalInformation.get();
    }

    public final void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation.set(additionalInformation);
    }

    public final StringProperty additionalInformationProperty() {
        return additionalInformation;
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

    public final LocalDateTime getBirthDate() {
        return birthDate.get();
    }

    public final void setBirthDate(LocalDateTime birthDate) {
        this.birthDate.set(birthDate);
    }

    public final ObjectProperty<LocalDateTime> birthDateProperty() {
        return birthDate;
    }

    public final LocalDateTime getHireDate() {
        return hireDate.get();
    }

    public final void setHireDate(LocalDateTime hireDate) {
        this.hireDate.set(hireDate);
    }

    public final ObjectProperty<LocalDateTime> hireDateProperty() {
        return hireDate;
    }

    public final Boolean getDismissed() {
        return dismissed.get();
    }

    public final void setDismissed(Boolean dismissed) {
        this.dismissed.set(dismissed);
    }

    public final BooleanProperty dismissedProperty() {
        return dismissed;
    }

    public final String getDriverLicense() {
        return driverLicense.get();
    }

    public final void setDriverLicense(String driverLicense) {
        this.driverLicense.set(driverLicense);
    }

    public final StringProperty driverLicenseProperty() {
        return driverLicense;
    }

    public final String getTrustedPerson() {
        return trustedPerson.get();
    }

    public final void setTrustedPerson(String trustedPerson) {
        this.trustedPerson.set(trustedPerson);
    }

    public final StringProperty trustedPersonProperty() {
        return trustedPerson;
    }

    public final Boolean getNotShowInStatements() {
        return notShowInStatements.get();
    }

    public final void setNotShowInStatements(Boolean notShowInStatements) {
        this.notShowInStatements.set(notShowInStatements);
    }

    public final BooleanProperty notShowInStatementsProperty() {
        return notShowInStatements;
    }
}
