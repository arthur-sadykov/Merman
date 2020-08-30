package merman.util.model;

import javafx.beans.property.*;

import java.io.Serializable;

/**
 * @author Arthur Sadykov
 */
public class Rights implements Serializable {

    private LongProperty id;
    private StringProperty name;
    private BooleanProperty administrative;
    private BooleanProperty editOtherPeopleDocuments;
    private BooleanProperty editOtherPeopleDirectoryEntries;
    private BooleanProperty printUnsentDocuments;
    private BooleanProperty printRegistryAndExportDirectory;
    private IntegerProperty numberOfEditingDays;
    private IntegerProperty numberOfDaysForAddingDocumentsInTheFuture;
    private BooleanProperty groupProcessingOfDirectoriesDocuments;
    private BooleanProperty disallowWriteFailedDocument;

    public Rights() {
    }

    public Rights(Long id, String name, Boolean administrative, Boolean editOtherPeopleDocuments, Boolean editOtherPeopleDirectoryEntries, Boolean printUnsentDocuments, Boolean printRegistryAndExportDirectory, Integer numberOfEditingDays, Integer numberOfDaysForAddingDocumentsInTheFuture, Boolean groupProcessingOfDirectoriesDocuments, Boolean disallowWriteFailedDocument) {
        this.id = id != null ? new SimpleLongProperty(id) : new SimpleLongProperty();
        this.name = name != null ? new SimpleStringProperty(name) : new SimpleStringProperty();
        this.administrative = administrative != null ? new SimpleBooleanProperty(administrative) : new SimpleBooleanProperty();
        this.editOtherPeopleDocuments = editOtherPeopleDocuments != null ? new SimpleBooleanProperty(editOtherPeopleDocuments) : new SimpleBooleanProperty();
        this.editOtherPeopleDirectoryEntries = editOtherPeopleDirectoryEntries != null ? new SimpleBooleanProperty(editOtherPeopleDirectoryEntries) : new SimpleBooleanProperty();
        this.printUnsentDocuments = printUnsentDocuments != null ? new SimpleBooleanProperty(printUnsentDocuments) : new SimpleBooleanProperty();
        this.printRegistryAndExportDirectory = printRegistryAndExportDirectory != null ? new SimpleBooleanProperty(printRegistryAndExportDirectory) : new SimpleBooleanProperty();
        this.numberOfEditingDays = numberOfEditingDays != null ? new SimpleIntegerProperty(numberOfEditingDays) : new SimpleIntegerProperty();
        this.numberOfDaysForAddingDocumentsInTheFuture = numberOfDaysForAddingDocumentsInTheFuture != null ? new SimpleIntegerProperty(numberOfDaysForAddingDocumentsInTheFuture) : new SimpleIntegerProperty();
        this.groupProcessingOfDirectoriesDocuments = groupProcessingOfDirectoriesDocuments != null ? new SimpleBooleanProperty(groupProcessingOfDirectoriesDocuments) : new SimpleBooleanProperty();
        this.disallowWriteFailedDocument = disallowWriteFailedDocument != null ? new SimpleBooleanProperty(disallowWriteFailedDocument) : new SimpleBooleanProperty();
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


    public final Boolean getAdministrative() {
        return administrative.get();
    }

    public final void setAdministrative(Boolean administrative) {
        this.administrative.set(administrative);
    }

    public final BooleanProperty administrativeProperty() {
        return administrative;
    }

    public final Boolean getEditOtherPeopleDocuments() {
        return editOtherPeopleDocuments.get();
    }

    public final void setEditOtherPeopleDocuments(Boolean editOtherPeopleDocuments) {
        this.editOtherPeopleDocuments.set(editOtherPeopleDocuments);
    }

    public final BooleanProperty editOtherPeopleDocumentsProperty() {
        return editOtherPeopleDocuments;
    }

    public final Boolean getEditOtherPeopleDirectoryEntries() {
        return editOtherPeopleDirectoryEntries.get();
    }

    public final void setEditOtherPeopleDirectoryEntries(Boolean editOtherPeopleDirectoryEntries) {
        this.editOtherPeopleDirectoryEntries.set(editOtherPeopleDirectoryEntries);
    }

    public final BooleanProperty editOtherPeopleDirectoryEntriesProperty() {
        return editOtherPeopleDirectoryEntries;
    }

    public final Boolean getPrintUnsentDocuments() {
        return printUnsentDocuments.get();
    }

    public final void setPrintUnsentDocuments(Boolean printUnsentDocuments) {
        this.printUnsentDocuments.set(printUnsentDocuments);
    }

    public final BooleanProperty printUnsentDocumentsProperty() {
        return printUnsentDocuments;
    }

    public final Boolean getPrintRegistryAndExportDirectory() {
        return printRegistryAndExportDirectory.get();
    }

    public final void setPrintRegistryAndExportDirectory(Boolean printRegistryAndExportDirectory) {
        this.printRegistryAndExportDirectory.set(printRegistryAndExportDirectory);
    }

    public final BooleanProperty printRegistryAndExportDirectoryProperty() {
        return printRegistryAndExportDirectory;
    }

    public final Integer getNumberOfEditingDays() {
        return numberOfEditingDays.get();
    }

    public final void setNumberOfEditingDays(Integer numberOfEditingDays) {
        this.numberOfEditingDays.set(numberOfEditingDays);
    }

    public final IntegerProperty numberOfEditingDaysProperty() {
        return numberOfEditingDays;
    }

    public final Integer getNumberOfDaysForAddingDocumentsInTheFuture() {
        return numberOfDaysForAddingDocumentsInTheFuture.get();
    }

    public final void setNumberOfDaysForAddingDocumentsInTheFuture(Integer numberOfDaysForAddingDocumentsInTheFuture) {
        this.numberOfDaysForAddingDocumentsInTheFuture.set(numberOfDaysForAddingDocumentsInTheFuture);
    }

    public final IntegerProperty numberOfDaysForAddingDocumentsInTheFutureProperty() {
        return numberOfDaysForAddingDocumentsInTheFuture;
    }

    public final Boolean getGroupProcessingOfDirectoriesDocuments() {
        return groupProcessingOfDirectoriesDocuments.get();
    }

    public final void setGroupProcessingOfDirectoriesDocuments(Boolean groupProcessingOfDirectoriesDocuments) {
        this.groupProcessingOfDirectoriesDocuments.set(groupProcessingOfDirectoriesDocuments);
    }

    public final BooleanProperty groupProcessingOfDirectoriesDocumentsProperty() {
        return groupProcessingOfDirectoriesDocuments;
    }

    public final Boolean getDisallowWriteFailedDocument() {
        return disallowWriteFailedDocument.get();
    }

    public final void setDisallowWriteFailedDocument(Boolean disallowWriteFailedDocument) {
        this.disallowWriteFailedDocument.set(disallowWriteFailedDocument);
    }

    public final BooleanProperty disallowWriteFailedDocumentProperty() {
        return disallowWriteFailedDocument;
    }
}
