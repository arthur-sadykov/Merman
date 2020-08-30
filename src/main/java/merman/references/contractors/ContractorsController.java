package merman.references.contractors;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import javafx.beans.Observable;
import javafx.beans.property.LongProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LocalDateStringConverter;
import merman.util.dao.DAOFactory;
import merman.util.dao.interfaces.ContractorTypesDAO;
import merman.util.dao.interfaces.ContractorsDAO;
import merman.util.dao.interfaces.DiscountsDAO;
import merman.util.dao.interfaces.EmployeesDAO;
import merman.util.dao.interfaces.TypesOfPricesDAO;
import merman.util.model.ContractorTypes;
import merman.util.model.Contractors;
import merman.util.model.ContractorsContracts;
import merman.util.model.Discounts;
import merman.util.model.TypesOfPrices;

/**
 * @author Arthur Sadykov
 */
public class ContractorsController implements Initializable {

    private static final String PATH_TO_CONTRACTORS_SEARCH =
            "/merman/references/contractors/contractors-search.fxml";
    private DAOFactory daoFactory;
    private ContractorsDAO contractorsDAO;
    @FXML
    private ButtonBar bbSaveCancel;
    @FXML
    private Button btnAddContractor;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnRemoveContractor;
    @FXML
    private Button btnSearchContractor;
    @FXML
    private Button btnUpdateContractor;
    @FXML
    private Button btnSave;
    @FXML
    private ComboBox<LongProperty> cbContractorType;
    @FXML
    private ComboBox<LongProperty> cbDealer;
    @FXML
    private ComboBox<LongProperty> cbDiscountType;
    @FXML
    private ComboBox<LongProperty> cbDispatcher;
    @FXML
    private ComboBox<LongProperty> cbFirm;
    @FXML
    private ComboBox<LongProperty> cbFormOfPayment;
    @FXML
    private ComboBox<LongProperty> cbPriceType;
    @FXML
    private GridPane gpBasic;
    @FXML
    private GridPane gpRequisites;
    @FXML
    private Label lbId;
    @FXML
    private TableColumn<Contractors, StringProperty> tcCode;
    @FXML
    private TableColumn<Contractors, String> tcName;
    @FXML
    private TableColumn<Contractors, StringProperty> tcPhones;
    @FXML
    private TextField tfActingOnTheBasisOf;
    @FXML
    private TextArea tfAdditionalInformation;
    @FXML
    private TextField tfCardNumber;
    @FXML
    private TextField tfDirector;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfFullName;
    @FXML
    private TextField tfIdentifierSED;
    @FXML
    private TextField tfInFaceOf;
    @FXML
    private TextField tfInn;
    @FXML
    private TextField tfLegalAddress;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfOgrn;
    @FXML
    private TextField tfOkpo;
    @FXML
    private TextField tfOkved;
    @FXML
    private TextField tfPassport;
    @FXML
    private TextField tfPhone;
    @FXML
    private TextField tfPhysicalAddress;
    private TabPane tpContentPane;
    @FXML
    private TableView<Contractors> tvContractors;
    private TableView<ContractorsContracts> tvContracts;

    public void setDAOFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<Contractors> list = FXCollections.observableArrayList((contractors) -> new Observable[]{
            contractors.idProperty(), contractors.nameProperty(), contractors.contractorTypeProperty(),
            contractors.dispatcherProperty(), contractors.phoneProperty(), contractors.emailProperty(),
            contractors.priceTypeProperty(), contractors.discountTypeProperty(), contractors.formOfPaymentProperty(),
            contractors.firmProperty(), contractors.dealerProperty(),
            contractors.additionalInformationProperty(), contractors.fullNameProperty(),
            contractors.legalAddressProperty(), contractors.physicalAddressProperty(), contractors.innProperty(),
            contractors.ogrnProperty(), contractors.okpoProperty(), contractors.okvedProperty(),
            contractors.directorProperty(), contractors.inFaceOfProperty(),
            contractors.actingOnTheBasisOfProperty(), contractors.cardNumberProperty(), contractors.passportProperty(),
            contractors.identifierSEDProperty()});
        list.addListener((ListChangeListener.Change<? extends Contractors> change) -> {
            while (change.next()) {
                if (change.wasRemoved()) {
                    if (list.isEmpty()) {
                        btnUpdateContractor.setDisable(true);
                        btnRemoveContractor.setDisable(true);
                        btnSearchContractor.setDisable(true);
                    }
                }
                if (change.wasAdded()) {
                    btnUpdateContractor.setDisable(false);
                    btnRemoveContractor.setDisable(false);
                    btnSearchContractor.setDisable(false);
                }
            }
        });
        tvContractors.setItems(list);
        tvContractors.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) ->
                Optional.ofNullable(tvContractors.getSelectionModel().getSelectedItem()).ifPresent(this::fillControls));
        StringConverter<LongProperty> contractorTypeStringConverter = new StringConverter<>() {
            @Override
            public LongProperty fromString(String string) {
                return null;
            }

            @Override
            public String toString(LongProperty t) {
                ContractorTypesDAO contractorTypesDAO = daoFactory.getContractorTypesDAO();
                return Optional.ofNullable(contractorTypesDAO.get(t.longValue())).map(ContractorTypes::getName).get();
            }
        };
        StringConverter<LongProperty> priceTypeStringConverter = new StringConverter<>() {
            @Override
            public LongProperty fromString(String string) {
                return null;
            }

            @Override
            public String toString(LongProperty t) {
                TypesOfPricesDAO typesOfPricesDAO = daoFactory.getTypesOfPricesDAO();
                return Optional.ofNullable(t).map(LongProperty::longValue).map(typesOfPricesDAO::get).map(TypesOfPrices::getName).orElse(null);
            }
        };
        StringConverter<LongProperty> discountStringConverter = new StringConverter<>() {
            @Override
            public LongProperty fromString(String string) {
                return null;
            }

            @Override
            public String toString(LongProperty t) {
                DiscountsDAO discountsDAO = daoFactory.getDiscountsDAO();
                return Optional.ofNullable(t).map(LongProperty::longValue).map(discountsDAO::get).map(Discounts::getName).orElse(null);
            }
        };
        cbContractorType.setConverter(contractorTypeStringConverter);
        cbPriceType.setConverter(priceTypeStringConverter);
        cbDiscountType.setConverter(discountStringConverter);
        StringConverter<LongProperty> dispatcherStringConverter = new StringConverter<>() {
            @Override
            public LongProperty fromString(String string) {
                return null;
            }

            @Override
            public String toString(LongProperty t) {
                EmployeesDAO employeesDAO = daoFactory.getEmployeesDAO();
                return Optional.ofNullable(employeesDAO.get(t.longValue())).map((employee) -> employee.getSurname()
                        + " " + employee.getName() + " " + employee.getPatronymic()).orElse(null);
            }
        };
        cbDispatcher.setConverter(dispatcherStringConverter);
    }

    public void setupAfterInitialize(TabPane tpContentPane) {
        contractorsDAO = daoFactory.getContractorsDAO();
        ContractorTypesDAO contractorTypesDAO = daoFactory.getContractorTypesDAO();
        TypesOfPricesDAO typesOfPricesDAO = daoFactory.getTypesOfPricesDAO();
        DiscountsDAO discountsDAO = daoFactory.getDiscountsDAO();
        contractorTypesDAO.list().forEach((contractorType) ->
                cbContractorType.getItems().add(contractorType.idProperty()));
        typesOfPricesDAO.list().forEach((typeOfPrices) -> cbPriceType.getItems().add(typeOfPrices.idProperty()));
        discountsDAO.list().forEach((discount) -> cbDiscountType.getItems().add(discount.idProperty()));
        this.tpContentPane = tpContentPane;
        tvContractors.getItems().addAll(contractorsDAO.list());
        if (!tvContractors.getItems().isEmpty()) {
            btnUpdateContractor.setDisable(false);
            btnRemoveContractor.setDisable(false);
            btnSearchContractor.setDisable(false);
            tvContractors.getSelectionModel().selectFirst();
        } else {
            btnUpdateContractor.setDisable(true);
            btnRemoveContractor.setDisable(true);
            btnSearchContractor.setDisable(true);
        }
        UnaryOperator<TextFormatter.Change> integerFilter = (TextFormatter.Change change) -> {
            if (change.isReplaced()) {
                if (!change.getText().matches("\\d+")) {
                    change.setText(change.getControlText().substring(change.getRangeStart(), change.getRangeEnd()));
                }
            }
            if (change.isAdded()) {
                if (!change.getText().matches("\\d+")) {
                    change.setText("");
                }
            }
            return change;
        };
        EmployeesDAO employeesDAO = daoFactory.getEmployeesDAO();
        employeesDAO.list().forEach((employee) -> cbDispatcher.getItems().add(employee.idProperty()));
    }

    @FXML
    private void handleBtnAddContractorPress(ActionEvent event) {
        bbSaveCancel.setDisable(false);
        gpBasic.setDisable(false);
        gpRequisites.setDisable(false);
        btnAddContractor.setDisable(true);
        btnUpdateContractor.setDisable(true);
        btnRemoveContractor.setDisable(true);
        btnSearchContractor.setDisable(true);
        clearControls();
    }

    @FXML
    private void handleBtnCancelPress(ActionEvent event) {
        btnAddContractor.setDisable(false);
        if (!tvContractors.getItems().isEmpty()) {
            btnUpdateContractor.setDisable(false);
            btnRemoveContractor.setDisable(false);
            btnSearchContractor.setDisable(false);
            tvContractors.getSelectionModel().selectFirst();
        } else {
            btnUpdateContractor.setDisable(true);
            btnRemoveContractor.setDisable(true);
            btnSearchContractor.setDisable(true);
        }
        bbSaveCancel.setDisable(true);
        gpBasic.setDisable(true);
    }

    @FXML
    private void handleBtnRemoveContractorPress(ActionEvent event) {
        Optional.ofNullable(tvContractors.getSelectionModel().getSelectedItem()).ifPresent((contractor) -> {
            int result = contractorsDAO.delete(contractor);
            if (result > 0) {
                tvContractors.getItems().remove(contractor);
            }
            clearControls();
        });
    }

    @FXML
    private void handleBtnSearchContractorPress() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PATH_TO_CONTRACTORS_SEARCH));
        AnchorPane apSearchPane = fxmlLoader.load();
        ContractorsSearchController contractorsSearchController = fxmlLoader.getController();
        contractorsSearchController.setDAOFactory(daoFactory);
        Tab tab = new Tab("Поиск в таблице \"Контрагенты\"", apSearchPane);
        ContextMenu contextMenu = new ContextMenu();
        MenuItem miClose = new MenuItem("Закрыть вкладку");
        MenuItem miCloseAll = new MenuItem("Закрыть все вкладки");
        MenuItem miCloseOthers = new MenuItem("Закрыть другие вкладки");
        MenuItem miCloseLeftTabs = new MenuItem("Закрыть вкладки слева");
        MenuItem miCloseRightTabs = new MenuItem("Закрыть вкладки справа");
        miClose.setOnAction(event -> tpContentPane.getTabs().remove(tab));
        miCloseAll.setOnAction(event -> tpContentPane.getTabs().clear());
        miCloseOthers.setOnAction(event -> {
            List<Tab> tabs = new ArrayList<>();
            tpContentPane.getTabs().forEach(t -> {
                if (t != tab) {
                    tabs.add(t);
                }
            });
            tpContentPane.getTabs().removeAll(tabs);
        });
        miCloseLeftTabs.setOnAction(event -> {
            List<Tab> tabs = new ArrayList<>();
            for (Tab t : tpContentPane.getTabs()) {
                if (t != tab) {
                    tabs.add(t);
                } else {
                    break;
                }
            }
            tpContentPane.getTabs().removeAll(tabs);
        });
        miCloseRightTabs.setOnAction(event -> {
            List<Tab> tabs = new ArrayList<>();
            for (int i = tpContentPane.getTabs().size() - 1; i >= 0; i--) {
                if (tpContentPane.getTabs().get(i) != tab) {
                    tabs.add(tpContentPane.getTabs().get(i));
                } else {
                    break;
                }
            }
            tpContentPane.getTabs().removeAll(tabs);
        });
        contextMenu.getItems().addAll(miClose, miCloseAll, miCloseOthers, miCloseLeftTabs, miCloseRightTabs);
        tab.setContextMenu(contextMenu);
        tpContentPane.getTabs().add(tab);
        tpContentPane.getSelectionModel().select(tab);
    }

    @FXML
    private void handleBtnUpdateContractorPress(ActionEvent event) {
        Optional.ofNullable(tvContractors.getSelectionModel().getSelectedItem()).ifPresent((contractor) -> {
            bbSaveCancel.setDisable(false);
            gpBasic.setDisable(false);
            gpRequisites.setDisable(false);
            btnAddContractor.setDisable(true);
            btnUpdateContractor.setDisable(true);
            btnRemoveContractor.setDisable(true);
            btnSearchContractor.setDisable(true);
            fillControls(contractor);
        });
    }

    @FXML
    private void handleBtnSavePress(ActionEvent event) {
        Long id = lbId.getText() == null || lbId.getText().matches("") ? null : Long.parseLong(lbId.getText());
        String name = tfName.getText();
        Long contractorType = cbContractorType.getValue() == null ? null : cbContractorType.getValue().get();
        Long dispatcher = cbDispatcher.getValue() == null ? null : cbDispatcher.getValue().get();
        String phone = tfPhone.getText();
        String email = tfEmail.getText();
        Long priceType = cbPriceType.getValue() == null ? null : cbPriceType.getValue().get();
        Long discountType = cbDiscountType.getValue() == null ? null : cbDiscountType.getValue().get();
        Long formOfPayment = cbFormOfPayment.getValue() == null ? null : cbFormOfPayment.getValue().get();
        Long firm = cbFirm.getValue() == null ? null : cbFirm.getValue().get();
        Long dealer = cbDealer.getValue() == null ? null : cbDealer.getValue().get();
        String additionalInformation = tfAdditionalInformation.getText();
        String fullName = tfFullName.getText();
        String legalAddress = tfLegalAddress.getText();
        String physicalAddress = tfPhysicalAddress.getText();
        String inn = tfInn.getText();
        String ogrn = tfOgrn.getText();
        String okpo = tfOkpo.getText();
        String okved = tfOkved.getText();
        String director = tfDirector.getText();
        String inFaceOf = tfInFaceOf.getText();
        String actingOnTheBasisOf = tfActingOnTheBasisOf.getText();
        String cardNumber = tfCardNumber.getText();
        String passport = tfPassport.getText();
        String identifierSED = tfIdentifierSED.getText();
        if (id == null) {
            Contractors contractor =
                    new Contractors(id, name, contractorType, dispatcher, phone, email, priceType, discountType, formOfPayment, firm, dealer, additionalInformation, fullName, legalAddress, physicalAddress, inn, ogrn, okpo, okved, director, inFaceOf, actingOnTheBasisOf, cardNumber, passport, identifierSED);
            int result = contractorsDAO.add(contractor);
            if (result > 0) {
                tvContractors.getItems().add(contractor);
            }
        } else {
            Contractors contractor =
                    new Contractors(id, name, contractorType, dispatcher, phone, email, priceType, discountType, formOfPayment, firm, dealer, additionalInformation, fullName, legalAddress, physicalAddress, inn, ogrn, okpo, okved, director, inFaceOf, actingOnTheBasisOf, cardNumber, passport, identifierSED);
            int result = contractorsDAO.update(contractor);
            if (result > 0) {
                tvContractors.getItems().set(tvContractors.getSelectionModel().getSelectedIndex(), contractor);
            }
        }
        gpBasic.setDisable(true);
        bbSaveCancel.setDisable(true);
        btnAddContractor.setDisable(false);
        btnUpdateContractor.setDisable(false);
        btnRemoveContractor.setDisable(false);
        btnSearchContractor.setDisable(false);
        gpBasic.setDisable(true);
        gpRequisites.setDisable(true);
        bbSaveCancel.setDisable(true);
    }

    private void fillControls(Contractors contractor) {
        lbId.setText(contractor.getId() == null ? "" : contractor.getId().toString());
        tfName.setText(contractor.getName());
        cbContractorType.setValue(contractor.contractorTypeProperty());
        cbDispatcher.setValue(contractor.dispatcherProperty());
        tfPhone.setText(contractor.getPhone());
        tfEmail.setText(contractor.getEmail());
        cbPriceType.setValue(contractor.priceTypeProperty());
        cbDiscountType.setValue(contractor.discountTypeProperty());
        cbFormOfPayment.setValue(contractor.formOfPaymentProperty());
        cbFirm.setValue(contractor.firmProperty());
        cbDealer.setValue(contractor.dealerProperty());
        tfAdditionalInformation.setText(contractor.getAdditionalInformation());
        tfFullName.setText(contractor.getFullName());
        tfLegalAddress.setText(contractor.getLegalAddress());
        tfPhysicalAddress.setText(contractor.getPhysicalAddress());
        tfInn.setText(contractor.getInn());
        tfOgrn.setText(contractor.getOgrn());
        tfOkpo.setText(contractor.getOkpo());
        tfOkved.setText(contractor.getOkved());
        tfDirector.setText(contractor.getDirector());
        tfInFaceOf.setText(contractor.getInFaceOf());
        tfActingOnTheBasisOf.setText(contractor.getActingOnTheBasisOf());
        tfCardNumber.setText(contractor.getCardNumber());
        tfPassport.setText(contractor.getPassport());
        tfIdentifierSED.setText(contractor.getIdentifierSED());
    }

    private void clearControls() {
        lbId.setText("");
        tfName.setText("");
        cbContractorType.getSelectionModel().selectFirst();
        cbDispatcher.getSelectionModel().selectFirst();
        tfPhone.setText("");
        tfEmail.setText("");
        cbPriceType.getSelectionModel().selectFirst();
        cbDiscountType.getSelectionModel().selectFirst();
        cbFormOfPayment.getSelectionModel().selectFirst();
        cbFirm.getSelectionModel().selectFirst();
        cbDealer.getSelectionModel().selectFirst();
        tfAdditionalInformation.setText("");
        tfFullName.setText("");
        tfLegalAddress.setText("");
        tfPhysicalAddress.setText("");
        tfInn.setText("");
        tfOgrn.setText("");
        tfOkpo.setText("");
        tfOkved.setText("");
        tfDirector.setText("");
        tfInFaceOf.setText("");
        tfActingOnTheBasisOf.setText("");
        tfCardNumber.setText("");
        tfPassport.setText("");
        tfIdentifierSED.setText("");
    }

    class DatePickerTableCell<Contracts, LocalDate> extends TableCell<Contracts, LocalDate> {

        private final StringConverter converter;
        private DatePicker datePicker;
        private boolean datePickerEditable = true;

        public DatePickerTableCell() {
            this.converter = new LocalDateStringConverter();
        }

        public DatePickerTableCell(boolean datePickerEditable) {
            this.converter = new LocalDateStringConverter();
            this.datePickerEditable = datePickerEditable;
        }

        public DatePickerTableCell(StringConverter<java.time.LocalDate> converter) {
            this.converter = converter;
        }

        public DatePickerTableCell(StringConverter<java.time.LocalDate> converter, boolean datePickerEditable) {
            this.converter = converter;
            this.datePickerEditable = datePickerEditable;
        }

        @Override
        public void startEdit() {
            if (!isEditable() || !getTableView().isEditable() || !getTableColumn().isEditable()) {
                return;
            }
            super.startEdit();
            if (datePicker == null) {
                this.createDatePicker();
            }
            this.setGraphic(datePicker);
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();
            this.setText(converter.toString(this.getItem()));
            this.setGraphic(null);
        }

        @Override
        public void updateItem(LocalDate item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                this.setText(null);
                this.setGraphic(null);
            } else {
                if (this.isEditing()) {
                    if (datePicker != null) {
                        datePicker.setValue((java.time.LocalDate) item);
                    }
                    this.setText(null);
                    this.setGraphic(datePicker);
                } else {
                    this.setText(converter.toString(item));
                    this.setGraphic(null);
                }
            }
        }

        private void createDatePicker() {
            datePicker = new DatePicker();
            datePicker.setConverter(converter);
            datePicker.setValue((java.time.LocalDate) this.getItem());
            datePicker.setPrefWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            datePicker.setEditable(this.datePickerEditable);
            datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
                if (DatePickerTableCell.this.isEditing()) {
                    DatePickerTableCell.this.commitEdit((LocalDate) newValue);
                }
            });
        }
    }

    class IntegerTableCell<Contracts, Integer> extends TableCell<Contracts, Integer> {

        private final UnaryOperator<TextFormatter.Change> integerFilter = (TextFormatter.Change change) -> {
            if (change.isReplaced()) {
                if (!change.getText().matches("\\d+")) {
                    change.setText(change.getControlText().substring(change.getRangeStart(), change.getRangeEnd()));
                }
            }
            if (change.isAdded()) {
                if (!change.getText().matches("\\d+")) {
                    change.setText("");
                }
            }
            return change;
        };
        private TextField textField;

        @Override
        public void startEdit() {
            if (!isEditable() || !getTableView().isEditable() || !getTableColumn().isEditable()) {
                return;
            }
            super.startEdit();
            if (textField == null) {
                this.createTextField();
            }
            this.setGraphic(textField);
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();
            setText(textField.getText());
            setGraphic(null);
        }

        @Override
        public void updateItem(Integer item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(Optional.ofNullable(item).map(Object::toString).orElse(null));
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(Optional.ofNullable(item).map((it) -> item.toString()).orElse(null));
                    setGraphic(null);
                }
            }
        }

        private void createTextField() {
            textField = new TextField();
            Optional.ofNullable(this.getItem()).ifPresent((item) -> textField.setText(item.toString()));
            textField.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), null, integerFilter));
        }
    }

    class DoubleTableCell<Contracts, Double> extends TableCell<Contracts, Double> {

        final UnaryOperator<TextFormatter.Change> doubleFilter = (TextFormatter.Change change) -> {
            if (change.isReplaced()) {
                if (change.getControlText().contains(".")) {
                    if (!change.getText().matches("\\d*?\\.?\\d*?")) {
                        change.setText(change.getControlText().substring(change.getRangeStart(), change.getRangeEnd()));
                    } else {
                        if (change.getText().contains(".")) {
                            int oldDotIndex = change.getControlText().indexOf(".");
                            int newDotIndex = change.getText().indexOf(".");
                            int rangeStart = change.getRangeStart();
                            if (rangeStart + newDotIndex != oldDotIndex) {
                                change.setText(change.getControlText().substring(change.getRangeStart(), change.getRangeEnd()));
                            }
                        }
                    }
                } else {
                    if (!change.getText().matches("\\d*?\\.?\\d*?")) {
                        change.setText(change.getControlText().substring(change.getRangeStart(), change.getRangeEnd()));
                    }
                }
            }
            if (change.isAdded()) {
                if (change.getControlText().contains(".")) {
                    if (!change.getText().matches("\\d*?\\.?\\d*?")) {
                        change.setText("");
                    } else {
                        if (change.getText().contains(".")) {
                            int oldDotIndex = change.getControlText().indexOf(".");
                            int newDotIndex = change.getText().indexOf(".");
                            int rangeStart = change.getRangeStart();
                            if (rangeStart + newDotIndex != oldDotIndex) {
                                change.setText("");
                            }
                        }
                    }
                } else {
                    if (!change.getText().matches("\\d*?\\.?\\d*?")) {
                        change.setText("");
                    }
                }
            }
            return change;
        };
        private TextField textField;

        @Override
        public void startEdit() {
            if (!isEditable() || !getTableView().isEditable() || !getTableColumn().isEditable()) {
                return;
            }
            super.startEdit();
            if (textField == null) {
                this.createTextField();
            }
            this.setGraphic(textField);
        }

        @Override
        public void commitEdit(Double newValue) {
            super.commitEdit(newValue);
            setText(null);
            setGraphic(null);
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();
            setText(textField.getText());
            setGraphic(null);
        }

        @Override
        public void updateItem(Double item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(Optional.ofNullable(item).map(Object::toString).orElse(null));
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(Optional.ofNullable(item).map((it) -> item.toString()).orElse(null));
                    setGraphic(null);
                }
            }
        }

        private void createTextField() {
            textField = new TextField();
            Optional.ofNullable(this.getItem()).ifPresent((item) -> textField.setText(item.toString()));
            textField.setTextFormatter(new TextFormatter<>(new DoubleStringConverter(), null, doubleFilter));
        }
    }
}
