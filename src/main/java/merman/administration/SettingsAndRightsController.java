package merman.administration;

import merman.util.dao.DAOFactory;
import merman.util.dao.interfaces.RightsDAO;
import merman.util.model.Rights;
import javafx.beans.Observable;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author Arthur Sadykov
 */
public class SettingsAndRightsController implements Initializable {

    private DAOFactory daoFactory;
    private RightsDAO rightsDAO;
    @FXML
    private ButtonBar bbSaveCancel;
    @FXML
    private Button btnAddRight;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnRemoveRight;

    @FXML
    private Button btnSave;
    @FXML
    private Button btnSearchRight;
    @FXML
    private Button btnUpdateRight;
    @FXML
    private CheckBox chbAdministrative;
    @FXML
    private CheckBox chbDisallowWriteFailedDocument;
    @FXML
    private CheckBox chbEditOtherPeopleDirectoryEntries;
    @FXML
    private CheckBox chbEditOtherPeopleDocuments;
    @FXML
    private CheckBox chbGroupProcessingOfDirectoriesDocuments;
    @FXML
    private CheckBox chbPrintRegistryAndExportDirectory;
    @FXML
    private CheckBox chbPrintUnsentDocuments;
    @FXML
    private GridPane gpBasic;
    @FXML
    private Label lbId;


    @FXML
    private TableColumn<Rights, StringProperty> tcName;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfNumberOfDaysForAddingDocumentsInTheFuture;
    @FXML
    private TextField tfNumberOfEditingDays;
    @FXML
    private TabPane tpRightTabPane;
    @FXML
    private TableView<Rights> tvRights;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<Rights> list = FXCollections.observableArrayList((rights) -> new Observable[]{rights.idProperty(), rights.nameProperty(), rights.administrativeProperty(), rights.editOtherPeopleDocumentsProperty(), rights.editOtherPeopleDirectoryEntriesProperty(),
                                                                                                     rights.printUnsentDocumentsProperty(), rights.printRegistryAndExportDirectoryProperty(), rights.numberOfEditingDaysProperty(), rights.numberOfDaysForAddingDocumentsInTheFutureProperty(),
                                                                                                     rights.groupProcessingOfDirectoriesDocumentsProperty(), rights.disallowWriteFailedDocumentProperty()});
        list.addListener((ListChangeListener.Change<? extends Rights> change) -> {
            while (change.next()) {
                if (change.wasRemoved()) {
                    if (list.isEmpty()) {
                        btnUpdateRight.setDisable(true);
                        btnRemoveRight.setDisable(true);
                        btnSearchRight.setDisable(true);
                    }
                }
                if (change.wasAdded()) {
                    btnUpdateRight.setDisable(false);
                    btnRemoveRight.setDisable(false);
                    btnSearchRight.setDisable(false);
                }
            }
        });
        tvRights.setItems(list);
        tvRights.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> Optional.ofNullable(tvRights.getSelectionModel().getSelectedItem()).ifPresent((right) -> {
            long id = right.getId();
            if (id == 1) {
                btnAddRight.setDisable(false);
                btnUpdateRight.setDisable(true);
                btnRemoveRight.setDisable(true);
                bbSaveCancel.setDisable(true);
                gpBasic.setDisable(true);
            } else {
                btnUpdateRight.setDisable(false);
                btnRemoveRight.setDisable(false);
                bbSaveCancel.setDisable(false);
                gpBasic.setDisable(false);
            }
            fillControls(right);
        }));
    }

    public void setupAfterInitialize() {
        rightsDAO = daoFactory.getRightsDAO();
        List<Rights> rights = rightsDAO.list();
        tvRights.getItems().addAll(rights);
        if (!tvRights.getItems().isEmpty()) {
            btnUpdateRight.setDisable(false);
            btnRemoveRight.setDisable(false);
            btnSearchRight.setDisable(false);
            tvRights.getSelectionModel().selectFirst();
        } else {
            btnUpdateRight.setDisable(true);
            btnRemoveRight.setDisable(true);
            btnSearchRight.setDisable(true);
        }
    }

    public void setDAOFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @FXML
    private void handleBtnAddRightPress(ActionEvent event) {
        bbSaveCancel.setDisable(false);
        gpBasic.setDisable(false);
        btnAddRight.setDisable(true);
        btnUpdateRight.setDisable(true);
        btnRemoveRight.setDisable(true);
        btnSearchRight.setDisable(true);
        clearControls();
    }

    @FXML
    private void handleBtnCancelPress(ActionEvent event) {
        btnAddRight.setDisable(false);
        if (!tvRights.getItems().isEmpty()) {
            btnUpdateRight.setDisable(false);
            btnRemoveRight.setDisable(false);
            btnSearchRight.setDisable(false);
            tvRights.getSelectionModel().selectFirst();
        } else {
            btnUpdateRight.setDisable(true);
            btnRemoveRight.setDisable(true);
            btnSearchRight.setDisable(true);
        }
        bbSaveCancel.setDisable(true);
        gpBasic.setDisable(true);
    }


    @FXML
    private void handleBtnRemoveRightPress(ActionEvent event) {
        Optional.ofNullable(tvRights.getSelectionModel().getSelectedItem()).ifPresent((right) -> {
            int result = rightsDAO.delete(right);
            if (result > 0) {
                tvRights.getItems().remove(right);
            }
            clearControls();
        });
    }

    @FXML
    private void handleBtnSavePress(ActionEvent event) {
        Long id = lbId.getText() == null || lbId.getText().matches("") ? null : Long.parseLong(lbId.getText());
        String name = tfName.getText();
        Boolean administrative = chbAdministrative.isSelected();
        Boolean editOtherPeopleDocuments = chbEditOtherPeopleDocuments.isSelected();
        Boolean editOtherPeopleDirectoryEntries = chbEditOtherPeopleDirectoryEntries.isSelected();
        Boolean printUnsentDocuments = chbPrintUnsentDocuments.isSelected();
        Boolean printRegistryAndExportDirectory = chbPrintRegistryAndExportDirectory.isSelected();
        Integer numberOfEditingDays = tfNumberOfEditingDays.getText().matches("") ? null : Integer.parseInt(tfNumberOfEditingDays.getText());
        Integer numberOfDaysForAddingDocumentsInTheFuture = tfNumberOfDaysForAddingDocumentsInTheFuture.getText().matches("") ? null : Integer.parseInt(tfNumberOfDaysForAddingDocumentsInTheFuture.getText());
        Boolean groupProcessingOfDirectoriesDocuments = chbGroupProcessingOfDirectoriesDocuments.isSelected();
        Boolean disallowWriteFailedDocument = chbDisallowWriteFailedDocument.isSelected();
        if (id == null) {
            Rights right = new Rights(id, name, administrative, editOtherPeopleDocuments, editOtherPeopleDirectoryEntries, printUnsentDocuments, printRegistryAndExportDirectory, numberOfEditingDays, numberOfDaysForAddingDocumentsInTheFuture, groupProcessingOfDirectoriesDocuments, disallowWriteFailedDocument);
            int result = rightsDAO.add(right);
            if (result > 0) {
                tvRights.getItems().add(right);
            }
        } else {
            Rights right;
            right = new Rights(id, name, administrative, editOtherPeopleDocuments, editOtherPeopleDirectoryEntries, printUnsentDocuments, printRegistryAndExportDirectory, numberOfEditingDays, numberOfDaysForAddingDocumentsInTheFuture, groupProcessingOfDirectoriesDocuments, disallowWriteFailedDocument);
            int result = rightsDAO.update(right);
            if (result > 0) {
                tvRights.getItems().set(tvRights.getSelectionModel().getSelectedIndex(), right);
            }
        }
        btnAddRight.setDisable(false);
        btnUpdateRight.setDisable(false);
        btnRemoveRight.setDisable(false);
        btnSearchRight.setDisable(false);
        gpBasic.setDisable(true);
        bbSaveCancel.setDisable(true);
    }

    @FXML
    private void handleBtnSearchRightPress(ActionEvent event) {
    }

    @FXML
    private void handleBtnUpdateRightPress(ActionEvent event) {
        Optional.ofNullable(tvRights.getSelectionModel().getSelectedItem()).ifPresent((right) -> {
            bbSaveCancel.setDisable(false);
            gpBasic.setDisable(false);
            btnAddRight.setDisable(true);
            btnUpdateRight.setDisable(true);
            btnRemoveRight.setDisable(true);
            btnSearchRight.setDisable(true);
            fillControls(right);
        });
    }


    private void fillControls(Rights right) {
        lbId.setText(right.getId() == null ? "" : right.getId().toString());
        tfName.setText(right.getName());
        chbAdministrative.setSelected(right.getAdministrative());
        chbEditOtherPeopleDocuments.setSelected(right.getEditOtherPeopleDocuments());
        chbEditOtherPeopleDirectoryEntries.setSelected(right.getEditOtherPeopleDirectoryEntries());
        chbPrintUnsentDocuments.setSelected(right.getPrintUnsentDocuments());
        chbPrintRegistryAndExportDirectory.setSelected(right.getPrintRegistryAndExportDirectory());
        tfNumberOfEditingDays.setText(right.getNumberOfEditingDays() == null ? null : right.getNumberOfEditingDays().toString());
        tfNumberOfDaysForAddingDocumentsInTheFuture.setText(right.getNumberOfDaysForAddingDocumentsInTheFuture() == null ? null : right.getNumberOfDaysForAddingDocumentsInTheFuture().toString());
        chbGroupProcessingOfDirectoriesDocuments.setSelected(right.getGroupProcessingOfDirectoriesDocuments());
        chbDisallowWriteFailedDocument.setSelected(right.getDisallowWriteFailedDocument());
    }

    private void clearControls() {
        lbId.setText("");
        tfName.setText("");
        chbAdministrative.setSelected(false);
        chbEditOtherPeopleDocuments.setSelected(false);
        chbEditOtherPeopleDirectoryEntries.setSelected(false);
        chbPrintUnsentDocuments.setSelected(false);
        chbPrintRegistryAndExportDirectory.setSelected(false);
        tfNumberOfEditingDays.setText("");
        tfNumberOfDaysForAddingDocumentsInTheFuture.setText("");
        chbGroupProcessingOfDirectoriesDocuments.setSelected(false);
        chbDisallowWriteFailedDocument.setSelected(false);
    }
}
