package merman.util.model;

import javafx.beans.property.*;

/**
 * @author Arthur Sadykov
 */
public class Users {

    private LongProperty id;
    private StringProperty name;
    private StringProperty password;
    private StringProperty surnameNamePatronymic;
    private LongProperty rights;
    private StringProperty smtpAddress;
    private StringProperty smtpUser;
    private StringProperty smtpPassword;
    private StringProperty smtpHost;
    private StringProperty smtpPort;
    private BooleanProperty smtpAuthenticationRequired;

    public Users() {
    }

    public Users(Long id, String name, String password, String surnameNamePatronymic, Long rights, String smtpAddress, String smtpUser, String smtpPassword, String smtpHost, String smtpPort, Boolean smtpAuthenticationRequired) {
        this.id = id != null ? new SimpleLongProperty(id) : new SimpleLongProperty();
        this.name = name != null ? new SimpleStringProperty(name) : new SimpleStringProperty();
        this.password = password != null ? new SimpleStringProperty(password) : new SimpleStringProperty();
        this.surnameNamePatronymic = surnameNamePatronymic != null ? new SimpleStringProperty(surnameNamePatronymic) : new SimpleStringProperty();
        this.rights = rights != null ? new SimpleLongProperty(rights) : new SimpleLongProperty();
        this.smtpAddress = smtpAddress != null ? new SimpleStringProperty(smtpAddress) : new SimpleStringProperty();
        this.smtpUser = smtpUser != null ? new SimpleStringProperty(smtpUser) : new SimpleStringProperty();
        this.smtpPassword = smtpPassword != null ? new SimpleStringProperty(smtpPassword) : new SimpleStringProperty();
        this.smtpHost = smtpHost != null ? new SimpleStringProperty(smtpHost) : new SimpleStringProperty();
        this.smtpPort = smtpPort != null ? new SimpleStringProperty(smtpPort) : new SimpleStringProperty();
        this.smtpAuthenticationRequired = smtpAuthenticationRequired != null ? new SimpleBooleanProperty(smtpAuthenticationRequired) : new SimpleBooleanProperty();
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

    public final void setUser(String name) {
        this.name.set(name);
    }

    public final StringProperty nameProperty() {
        return name;
    }

    public final String getPassword() {
        return password.get();
    }

    public final void setPassword(String password) {
        this.password.set(password);
    }

    public final StringProperty passwordProperty() {
        return password;
    }

    public final String getSurnameNamePatronymic() {
        return surnameNamePatronymic.get();
    }

    public final void setSurnameNamePatronymic(String surnameNamePatronymic) {
        this.surnameNamePatronymic.set(surnameNamePatronymic);
    }

    public final StringProperty surnameNamePatronymicProperty() {
        return surnameNamePatronymic;
    }

    public final Long getRights() {
        return rights.get();
    }

    public final void setRights(Long rights) {
        this.rights.set(rights);
    }

    public final LongProperty rightsProperty() {
        return rights;
    }

    public final String getSmtpAddress() {
        return smtpAddress.get();
    }

    public final void setSmtpAddress(String smtpAddress) {
        this.smtpAddress.set(smtpAddress);
    }

    public final StringProperty smtpAddressProperty() {
        return smtpAddress;
    }

    public final String getSmtpUser() {
        return smtpUser.get();
    }

    public final void setSmtpUser(String smtpUser) {
        this.smtpUser.set(smtpUser);
    }

    public final StringProperty smtpUserProperty() {
        return smtpUser;
    }

    public final String getSmtpPassword() {
        return smtpPassword.get();
    }

    public final void setSmtpPassword(String smtpPassword) {
        this.smtpPassword.set(smtpPassword);
    }

    public final StringProperty smtpPasswordProperty() {
        return smtpPassword;
    }

    public final String getSmtpHost() {
        return smtpHost.get();
    }

    public final void setSmtpHost(String smtpHost) {
        this.smtpHost.set(smtpHost);
    }

    public final StringProperty smtpHostProperty() {
        return smtpHost;
    }

    public final String getSmtpPort() {
        return smtpPort.get();
    }

    public final void setSmtpPort(String smtpPort) {
        this.smtpPort.set(smtpPort);
    }

    public final StringProperty smtpPortProperty() {
        return smtpPort;
    }

    public final Boolean getSmtpAuthenticationRequired() {
        return smtpAuthenticationRequired.get();
    }

    public final void setSmtpAuthenticationRequired(Boolean smtpAuthenticationRequired) {
        this.smtpAuthenticationRequired.set(smtpAuthenticationRequired);
    }

    public final BooleanProperty smtpAuthenticationRequiredProperty() {
        return smtpAuthenticationRequired;
    }
}
