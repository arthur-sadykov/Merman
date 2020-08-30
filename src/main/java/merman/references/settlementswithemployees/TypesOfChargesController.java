package merman.references.settlementswithemployees;

import merman.util.dao.DAOFactory;
import merman.util.dao.interfaces.TypesOfChargesDAO;
import merman.util.model.TypesOfCharges;
import javafx.beans.Observable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

/**
 * @author Arthur Sadykov
 */
public class TypesOfChargesController implements Initializable {

    @FXML
    private Button btnAddTypeOfCharges;
    @FXML
    private Button btnRemoveTypeOfCharges;
    @FXML
    private Button btnSearchTypeOfCharges;
    @FXML
    private Button btnUpdateTypeOfCharges;

    private DAOFactory daoFactory;
    private TypesOfChargesDAO typeOfChargesDAO;
    @FXML
    private ButtonBar bbSaveCancel;

    @FXML
    private ComboBox<LongProperty> cbCategoryOfDelivery;
    @FXML
    private ComboBox<LongProperty> cbChangeOfDelivery;
    @FXML
    private ComboBox<LongProperty> cbConsumptionCategories;
    @FXML
    private CheckBox chbOnlyOnActiveSales;
    @FXML
    private CheckBox chbOnlyUponReceiptOfMoneyOrReturnOfDocuments;
    @FXML
    private ComboBox<LongProperty> cbProductCategory;
    @FXML
    private ComboBox<LongProperty> cbService;
    @FXML
    private ComboBox<LongProperty> cbServiceCategory;
    @FXML
    private DatePicker dpSurchargeIsValidFrom;
    @FXML
    private DatePicker dpSurchargeIsValidUntil;
    @FXML
    private GridPane gpBasic;
    @FXML
    private GridPane gpChargeFilters;
    @FXML
    private GridPane gpDebtRepayment;
    @FXML
    private GridPane gpDeliverySalesCalls;
    @FXML
    private GridPane gpServices;
    @FXML
    private MenuItem handleMiCardPrintPress;
    @FXML
    private MenuItem handleMiRegistryPrintPress;
    @FXML
    private Label lbId;
    @FXML
    private TableColumn<TypesOfCharges, Number> tcCategoryOfDelivery;
    @FXML
    private TableColumn<TypesOfCharges, Number> tcCategoryOfProducts;
    @FXML
    private TableColumn<TypesOfCharges, Number> tcCategoryOfServices;

    @FXML
    private TableColumn<TypesOfCharges, String> tcName;
    @FXML
    private TableColumn<TypesOfCharges, Number> tcService;
    @FXML
    private TextField tfAmountChargedPerUnit;
    @FXML
    private TextField tfAmountPer1KilogramOfDeliveredProducts;
    @FXML
    private TextField tfDaysOverdueFrom;
    @FXML
    private TextField tfDaysOverdueUntil;
    @FXML
    private ComboBox<LongProperty> cbPosition;
    @FXML
    private TextField tfForCashReception;
    @FXML
    private TextField tfForDepartureOnTheRoute;
    @FXML
    private TextField tfForOneDeliveryAddress;
    @FXML
    private TextField tfForOneDepartureWithoutProducts;
    @FXML
    private TextField tfForOneReturnedInvoice;
    @FXML
    private TextField tfForReturningAUnitOfPackaging;
    @FXML
    private TextField tfName;
    @FXML
    private ComboBox<IntegerProperty> cbOptionOfTheBaseOfCharges;
    @FXML
    private TextField tfPerUnitOfServiceProvided;
    @FXML
    private TextField tfPercentageOfRepaymentAmount;
    @FXML
    private TextField tfPercentageOfSalesAmount;
    @FXML
    private TextField tfStartingFromTheNumberOfProducts;
    @FXML
    private TextField tfToTheNumberOfProducts;
    @FXML
    private TableView<TypesOfCharges> tvTypesOfCharges;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<TypesOfCharges> list = FXCollections.observableArrayList((typesOfCharges) -> new Observable[]{typesOfCharges.idProperty(), typesOfCharges.nameProperty(), typesOfCharges.positionProperty(), typesOfCharges.optionOfTheBaseOfChargesProperty(),
                                                                                                                     typesOfCharges.surchargeIsValidFromProperty(), typesOfCharges.surchargeIsValidUntilProperty(), typesOfCharges.percentageOfSalesAmountProperty(),
                                                                                                                     typesOfCharges.amountChargedPerUnitProperty(), typesOfCharges.amountPer1KilogramOfDeliveredProductsProperty(), typesOfCharges.forOneDeliveryAddressProperty(),
                                                                                                                     typesOfCharges.forReturningAUnitOfPackagingProperty(), typesOfCharges.forOneDepartureWithoutProductsProperty(), typesOfCharges.forDepartureOnTheRouteProperty(),
                                                                                                                     typesOfCharges.forOneReturnedInvoiceProperty(), typesOfCharges.forCashReceptionProperty(), typesOfCharges.changeOfDeliveryProperty(), typesOfCharges.categoryOfDeliveryProperty(),
                                                                                                                     typesOfCharges.productCategoryProperty(), typesOfCharges.startingFromTheNumberOfProductsProperty(), typesOfCharges.toTheNumberOfProductsProperty(),
                                                                                                                     typesOfCharges.onlyOnActiveSalesProperty(), typesOfCharges.onlyUponReceiptOfMoneyOrReturnOfDocumentsProperty(), typesOfCharges.serviceCategoryProperty(),
                                                                                                                     typesOfCharges.serviceProperty(), typesOfCharges.perUnitOfServiceProvidedProperty(), typesOfCharges.consumptionCategoriesProperty(),
                                                                                                                     typesOfCharges.percentageOfRepaymentAmountProperty(), typesOfCharges.daysOverdueFromProperty(), typesOfCharges.daysOverdueUntilProperty()});
        list.addListener((ListChangeListener.Change<? extends TypesOfCharges> change) -> {
            while (change.next()) {
                if (change.wasRemoved()) {
                    if (list.isEmpty()) {
                        btnUpdateTypeOfCharges.setDisable(true);
                        btnRemoveTypeOfCharges.setDisable(true);
                        btnSearchTypeOfCharges.setDisable(true);
                    }
                }
                if (change.wasAdded()) {
                    btnUpdateTypeOfCharges.setDisable(false);
                    btnRemoveTypeOfCharges.setDisable(false);
                    btnSearchTypeOfCharges.setDisable(false);
                }
            }
        });
        tvTypesOfCharges.setItems(list);
        tvTypesOfCharges.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> Optional.ofNullable(tvTypesOfCharges.getSelectionModel().getSelectedItem()).ifPresent(this::fillControls));
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
        UnaryOperator<TextFormatter.Change> doubleFilter = (TextFormatter.Change change) -> {
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
        tfAmountChargedPerUnit.setTextFormatter(new TextFormatter<>(new DoubleStringConverter(), null, doubleFilter));
        tfPercentageOfSalesAmount.setTextFormatter(new TextFormatter<>(new DoubleStringConverter(), null, doubleFilter));
        tfAmountPer1KilogramOfDeliveredProducts.setTextFormatter(new TextFormatter<>(new DoubleStringConverter(), null, doubleFilter));
        tfForOneDeliveryAddress.setTextFormatter(new TextFormatter<>(new DoubleStringConverter(), null, doubleFilter));
        tfForReturningAUnitOfPackaging.setTextFormatter(new TextFormatter<>(new DoubleStringConverter(), null, doubleFilter));
        tfForOneDepartureWithoutProducts.setTextFormatter(new TextFormatter<>(new DoubleStringConverter(), null, doubleFilter));
        tfForDepartureOnTheRoute.setTextFormatter(new TextFormatter<>(new DoubleStringConverter(), null, doubleFilter));
        tfForOneReturnedInvoice.setTextFormatter(new TextFormatter<>(new DoubleStringConverter(), null, doubleFilter));
        tfForCashReception.setTextFormatter(new TextFormatter<>(new DoubleStringConverter(), null, doubleFilter));
        tfStartingFromTheNumberOfProducts.setTextFormatter(new TextFormatter<>(new DoubleStringConverter(), null, doubleFilter));
        tfToTheNumberOfProducts.setTextFormatter(new TextFormatter<>(new DoubleStringConverter(), null, doubleFilter));
        tfPerUnitOfServiceProvided.setTextFormatter(new TextFormatter<>(new DoubleStringConverter(), null, doubleFilter));
        tfPercentageOfRepaymentAmount.setTextFormatter(new TextFormatter<>(new DoubleStringConverter(), null, doubleFilter));
        tfDaysOverdueFrom.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), null, integerFilter));
        tfDaysOverdueUntil.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), null, integerFilter));
    }

    public void setupAfterInitialize(TabPane tpContentPane) {
        typeOfChargesDAO = daoFactory.getTypesOfChargesDAO();
        tvTypesOfCharges.getItems().addAll(typeOfChargesDAO.list());
        if (!tvTypesOfCharges.getItems().isEmpty()) {
            btnUpdateTypeOfCharges.setDisable(false);
            btnRemoveTypeOfCharges.setDisable(false);
            btnSearchTypeOfCharges.setDisable(false);
            tvTypesOfCharges.getSelectionModel().selectFirst();
        } else {
            btnUpdateTypeOfCharges.setDisable(true);
            btnRemoveTypeOfCharges.setDisable(true);
            btnSearchTypeOfCharges.setDisable(true);
        }
    }

    public void setDAOFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @FXML
    private void handleBtnAddTypeOfChargesPress(ActionEvent event) {
        bbSaveCancel.setDisable(false);
        gpBasic.setDisable(false);
        gpChargeFilters.setDisable(false);
        gpDebtRepayment.setDisable(false);
        gpDeliverySalesCalls.setDisable(false);
        gpServices.setDisable(false);
        btnAddTypeOfCharges.setDisable(true);
        btnUpdateTypeOfCharges.setDisable(true);
        btnRemoveTypeOfCharges.setDisable(true);
        btnSearchTypeOfCharges.setDisable(true);
        clearControls();
    }

    @FXML
    private void handleBtnCancelPress(ActionEvent event) {
        btnAddTypeOfCharges.setDisable(false);
        if (!tvTypesOfCharges.getItems().isEmpty()) {
            btnUpdateTypeOfCharges.setDisable(false);
            btnRemoveTypeOfCharges.setDisable(false);
            btnSearchTypeOfCharges.setDisable(false);
            tvTypesOfCharges.getSelectionModel().selectFirst();
        } else {
            btnUpdateTypeOfCharges.setDisable(true);
            btnRemoveTypeOfCharges.setDisable(true);
            btnSearchTypeOfCharges.setDisable(true);
        }
        bbSaveCancel.setDisable(true);
        gpBasic.setDisable(true);
    }

    @FXML
    private void handleBtnRemoveTypeOfChargesPress(ActionEvent event) {
    }

    private void handleBtnRemoveUnitPress(ActionEvent event) {
        Optional.ofNullable(tvTypesOfCharges.getSelectionModel().getSelectedItem()).ifPresent((typeOfCharges) -> {
            int result = typeOfChargesDAO.delete(typeOfCharges);
            if (result > 0) {
                tvTypesOfCharges.getItems().remove(typeOfCharges);
            }
            clearControls();
        });
    }

    @FXML
    private void handleBtnSavePress(ActionEvent event) {
        Long id = lbId.getText() == null || lbId.getText().matches("") ? null : Long.parseLong(lbId.getText());
        String name = tfName.getText();
        Long position = cbPosition.getValue() == null ? null : cbPosition.getValue().get();
        Integer optionOfTheBaseOfCharges = cbOptionOfTheBaseOfCharges.getValue() == null ? null : cbOptionOfTheBaseOfCharges.getValue().get();
        LocalDateTime surchargeIsValidFrom = dpSurchargeIsValidFrom.getValue() == null ? null : dpSurchargeIsValidFrom.getValue().atStartOfDay();
        LocalDateTime surchargeIsValidUntil = dpSurchargeIsValidUntil.getValue() == null ? null : dpSurchargeIsValidUntil.getValue().atStartOfDay();
        BigDecimal percentageOfSalesAmount = tfPercentageOfSalesAmount.getText() == null || tfPercentageOfSalesAmount.getText().matches("") ? null : new BigDecimal(tfPercentageOfSalesAmount.getText());
        BigDecimal amountChargedPerUnit = tfAmountChargedPerUnit.getText() == null || tfAmountChargedPerUnit.getText().matches("") ? null : new BigDecimal(tfAmountChargedPerUnit.getText());
        BigDecimal amountPer1KilogramOfDeliveredProducts = tfAmountPer1KilogramOfDeliveredProducts.getText() == null || tfAmountPer1KilogramOfDeliveredProducts.getText().matches("") ? null : new BigDecimal(tfAmountPer1KilogramOfDeliveredProducts.getText());
        BigDecimal forOneDeliveryAddress = tfForOneDeliveryAddress.getText() == null || tfForOneDeliveryAddress.getText().matches("") ? null : new BigDecimal(tfForOneDeliveryAddress.getText());
        BigDecimal forReturningAUnitOfPackaging = tfForReturningAUnitOfPackaging.getText() == null || tfForReturningAUnitOfPackaging.getText().matches("") ? null : new BigDecimal(tfForReturningAUnitOfPackaging.getText());
        BigDecimal forOneDepartureWithoutProducts = tfForOneDepartureWithoutProducts.getText() == null || tfForOneDepartureWithoutProducts.getText().matches("") ? null : new BigDecimal(tfForOneDepartureWithoutProducts.getText());
        BigDecimal forDepartureOnTheRoute = tfForDepartureOnTheRoute.getText() == null || tfForDepartureOnTheRoute.getText().matches("") ? null : new BigDecimal(tfForDepartureOnTheRoute.getText());
        BigDecimal forOneReturnedInvoice = tfForOneReturnedInvoice.getText() == null || tfForOneReturnedInvoice.getText().matches("") ? null : new BigDecimal(tfForOneReturnedInvoice.getText());
        BigDecimal forCashReception = tfForCashReception.getText() == null || tfForCashReception.getText().matches("") ? null : new BigDecimal(tfForCashReception.getText());
        Long changeOfDelivery = cbChangeOfDelivery.getValue() == null ? null : cbChangeOfDelivery.getValue().get();
        Long categoryOfDelivery = cbCategoryOfDelivery.getValue() == null ? null : cbCategoryOfDelivery.getValue().get();
        Long productCategory = cbProductCategory.getValue() == null ? null : cbProductCategory.getValue().get();
        BigDecimal startingFromTheNumberOfProducts = tfStartingFromTheNumberOfProducts.getText() == null || tfStartingFromTheNumberOfProducts.getText().matches("") ? null : new BigDecimal(tfStartingFromTheNumberOfProducts.getText());
        BigDecimal toTheNumberOfProducts = tfToTheNumberOfProducts.getText() == null || tfToTheNumberOfProducts.getText().matches("") ? null : new BigDecimal(tfToTheNumberOfProducts.getText());
        Boolean onlyOnActiveSales = chbOnlyOnActiveSales.isSelected();
        Boolean onlyUponReceiptOfMoneyOrReturnOfDocuments = chbOnlyUponReceiptOfMoneyOrReturnOfDocuments.isSelected();
        Long serviceCategory = cbServiceCategory.getValue() == null ? null : cbServiceCategory.getValue().get();
        Long service = cbService.getValue() == null ? null : cbService.getValue().get();
        BigDecimal perUnitOfServiceProvided = tfPerUnitOfServiceProvided.getText() == null || tfPerUnitOfServiceProvided.getText().matches("") ? null : new BigDecimal(tfPerUnitOfServiceProvided.getText());
        Long consumptionCategories = cbConsumptionCategories.getValue() == null ? null : cbConsumptionCategories.getValue().get();
        BigDecimal percentageOfRepaymentAmount = tfPercentageOfRepaymentAmount.getText() == null || tfPercentageOfRepaymentAmount.getText().matches("") ? null : new BigDecimal(tfPercentageOfRepaymentAmount.getText());
        Integer daysOverdueFrom = tfDaysOverdueFrom.getText().matches("") ? null : Integer.parseInt(tfDaysOverdueFrom.getText());
        Integer daysOverdueUntil = tfDaysOverdueUntil.getText().matches("") ? null : Integer.parseInt(tfDaysOverdueUntil.getText());
        if (id == null) {
            TypesOfCharges typeOfCharges = new TypesOfCharges(id, name, position, optionOfTheBaseOfCharges, surchargeIsValidFrom, surchargeIsValidUntil, percentageOfSalesAmount, amountChargedPerUnit, amountPer1KilogramOfDeliveredProducts, forOneDeliveryAddress, forReturningAUnitOfPackaging, forOneDepartureWithoutProducts, forDepartureOnTheRoute, forOneReturnedInvoice, forCashReception, changeOfDelivery, categoryOfDelivery, productCategory, startingFromTheNumberOfProducts, toTheNumberOfProducts, onlyOnActiveSales, onlyUponReceiptOfMoneyOrReturnOfDocuments, serviceCategory, service, perUnitOfServiceProvided, consumptionCategories, percentageOfRepaymentAmount, daysOverdueFrom, daysOverdueUntil);
            int result = typeOfChargesDAO.add(typeOfCharges);
            if (result > 0) {
                tvTypesOfCharges.getItems().add(typeOfCharges);
            }
        } else {
            TypesOfCharges typeOfCharges = new TypesOfCharges(id, name, position, optionOfTheBaseOfCharges, surchargeIsValidFrom, surchargeIsValidUntil, percentageOfSalesAmount, amountChargedPerUnit, amountPer1KilogramOfDeliveredProducts, forOneDeliveryAddress, forReturningAUnitOfPackaging, forOneDepartureWithoutProducts, forDepartureOnTheRoute, forOneReturnedInvoice, forCashReception, changeOfDelivery, categoryOfDelivery, productCategory, startingFromTheNumberOfProducts, toTheNumberOfProducts, onlyOnActiveSales, onlyUponReceiptOfMoneyOrReturnOfDocuments, serviceCategory, service, perUnitOfServiceProvided, consumptionCategories, percentageOfRepaymentAmount, daysOverdueFrom, daysOverdueUntil);
            int result = typeOfChargesDAO.update(typeOfCharges);
            if (result > 0) {
                tvTypesOfCharges.getItems().set(tvTypesOfCharges.getSelectionModel().getSelectedIndex(), typeOfCharges);
            }
        }
        btnAddTypeOfCharges.setDisable(false);
        btnUpdateTypeOfCharges.setDisable(false);
        btnRemoveTypeOfCharges.setDisable(false);
        btnSearchTypeOfCharges.setDisable(false);
        gpBasic.setDisable(true);
        bbSaveCancel.setDisable(true);
    }

    @FXML
    private void handleBtnSearchTypeOfChargesPress(ActionEvent event) {
    }

    private void handleBtnSearchUnitPress(ActionEvent event) {
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PATH_TO_UNITS_SEARCH));
//        AnchorPane apSearchPane = fxmlLoader.load();
//        TypesOfChargesSearchController typeOfChargessSearchController = fxmlLoader.getController();
//        typeOfChargesSearchController.setDAOFactory(daoFactory);
//        Tab tab = new Tab("Поиск в таблице \"Единицы измерения\"", apSearchPane);
//        tpContentPane.getTabs().add(tab);
//        tpContentPane.getSelectionModel().select(tab);
    }

    @FXML
    private void handleBtnUpdateTypeOfChargesPress(ActionEvent event) {
        Optional.ofNullable(tvTypesOfCharges.getSelectionModel().getSelectedItem()).ifPresent((typeOfCharges) -> {
            bbSaveCancel.setDisable(false);
            gpBasic.setDisable(false);
            gpChargeFilters.setDisable(false);
            gpDebtRepayment.setDisable(false);
            gpDeliverySalesCalls.setDisable(false);
            gpServices.setDisable(false);
            btnAddTypeOfCharges.setDisable(true);
            btnUpdateTypeOfCharges.setDisable(true);
            btnRemoveTypeOfCharges.setDisable(true);
            btnSearchTypeOfCharges.setDisable(true);
            fillControls(typeOfCharges);
        });
    }


    private void fillControls(TypesOfCharges typeOfCharges) {
        lbId.setText(typeOfCharges.getId() == null ? "" : typeOfCharges.getId().toString());
        tfName.setText(typeOfCharges.getName());
        cbPosition.setValue(typeOfCharges.positionProperty());
        cbOptionOfTheBaseOfCharges.setValue(typeOfCharges.optionOfTheBaseOfChargesProperty());
        dpSurchargeIsValidFrom.setValue(typeOfCharges.getSurchargeIsValidFrom() == null ? null : typeOfCharges.getSurchargeIsValidFrom().toLocalDate());
        dpSurchargeIsValidUntil.setValue(typeOfCharges.getSurchargeIsValidUntil() == null ? null : typeOfCharges.getSurchargeIsValidUntil().toLocalDate());
        tfPercentageOfSalesAmount.setText(typeOfCharges.getPercentageOfSalesAmount() == null ? null : typeOfCharges.getPercentageOfSalesAmount().toString());
        tfAmountChargedPerUnit.setText(typeOfCharges.getAmountChargedPerUnit() == null ? null : typeOfCharges.getAmountChargedPerUnit().toString());
        tfAmountPer1KilogramOfDeliveredProducts.setText(typeOfCharges.getAmountPer1KilogramOfDeliveredProducts() == null ? null : typeOfCharges.getAmountPer1KilogramOfDeliveredProducts().toString());
        tfForOneDeliveryAddress.setText(typeOfCharges.getForOneDeliveryAddress() == null ? null : typeOfCharges.getForOneDeliveryAddress().toString());
        tfForReturningAUnitOfPackaging.setText(typeOfCharges.getForReturningAUnitOfPackaging() == null ? null : typeOfCharges.getForReturningAUnitOfPackaging().toString());
        tfForOneDepartureWithoutProducts.setText(typeOfCharges.getForOneDepartureWithoutProducts() == null ? null : typeOfCharges.getForOneDepartureWithoutProducts().toString());
        tfForDepartureOnTheRoute.setText(typeOfCharges.getForDepartureOnTheRoute() == null ? null : typeOfCharges.getForDepartureOnTheRoute().toString());
        tfForOneReturnedInvoice.setText(typeOfCharges.getForOneReturnedInvoice() == null ? null : typeOfCharges.getForOneReturnedInvoice().toString());
        tfForCashReception.setText(typeOfCharges.getForCashReception() == null ? null : typeOfCharges.getForCashReception().toString());
        cbChangeOfDelivery.setValue(typeOfCharges.changeOfDeliveryProperty());
        cbCategoryOfDelivery.setValue(typeOfCharges.categoryOfDeliveryProperty());
        cbProductCategory.setValue(typeOfCharges.productCategoryProperty());
        tfStartingFromTheNumberOfProducts.setText(typeOfCharges.getStartingFromTheNumberOfProducts() == null ? null : typeOfCharges.getStartingFromTheNumberOfProducts().toString());
        tfToTheNumberOfProducts.setText(typeOfCharges.getToTheNumberOfProducts() == null ? null : typeOfCharges.getToTheNumberOfProducts().toString());
        chbOnlyOnActiveSales.setSelected(typeOfCharges.getOnlyOnActiveSales());
        chbOnlyUponReceiptOfMoneyOrReturnOfDocuments.setSelected(typeOfCharges.getOnlyUponReceiptOfMoneyOrReturnOfDocuments());
        cbServiceCategory.setValue(typeOfCharges.serviceCategoryProperty());
        cbService.setValue(typeOfCharges.serviceProperty());
        tfPerUnitOfServiceProvided.setText(typeOfCharges.getPerUnitOfServiceProvided() == null ? null : typeOfCharges.getPerUnitOfServiceProvided().toString());
        cbConsumptionCategories.setValue(typeOfCharges.consumptionCategoriesProperty());
        tfPercentageOfRepaymentAmount.setText(typeOfCharges.getPercentageOfRepaymentAmount() == null ? null : typeOfCharges.getPercentageOfRepaymentAmount().toString());
        tfDaysOverdueFrom.setText(typeOfCharges.getDaysOverdueFrom() == null ? null : typeOfCharges.getDaysOverdueFrom().toString());
        tfDaysOverdueUntil.setText(typeOfCharges.getDaysOverdueUntil() == null ? null : typeOfCharges.getDaysOverdueUntil().toString());
    }

    private void clearControls() {
        lbId.setText("");
        tfName.setText("");
        cbPosition.getSelectionModel().selectFirst();
        cbOptionOfTheBaseOfCharges.getSelectionModel().selectFirst();
        dpSurchargeIsValidFrom.setValue(null);
        dpSurchargeIsValidUntil.setValue(null);
        tfPercentageOfSalesAmount.setText("");
        tfAmountChargedPerUnit.setText("");
        tfAmountPer1KilogramOfDeliveredProducts.setText("");
        tfForOneDeliveryAddress.setText("");
        tfForReturningAUnitOfPackaging.setText("");
        tfForOneDepartureWithoutProducts.setText("");
        tfForDepartureOnTheRoute.setText("");
        tfForOneReturnedInvoice.setText("");
        tfForCashReception.setText("");
        cbChangeOfDelivery.getSelectionModel().selectFirst();
        cbCategoryOfDelivery.getSelectionModel().selectFirst();
        cbProductCategory.getSelectionModel().selectFirst();
        tfStartingFromTheNumberOfProducts.setText("");
        tfToTheNumberOfProducts.setText("");
        chbOnlyOnActiveSales.setSelected(false);
        chbOnlyUponReceiptOfMoneyOrReturnOfDocuments.setSelected(false);
        cbServiceCategory.getSelectionModel().selectFirst();
        cbService.getSelectionModel().selectFirst();
        tfPerUnitOfServiceProvided.setText("");
        cbConsumptionCategories.getSelectionModel().selectFirst();
        tfPercentageOfRepaymentAmount.setText("");
        tfDaysOverdueFrom.setText("");
        tfDaysOverdueUntil.setText("");
    }
}
