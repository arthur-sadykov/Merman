package merman.documents.consumableinvoices;

import merman.alert.AlertFX;
import merman.util.dao.DAOFactory;
import merman.util.dao.interfaces.ConsumableInvoicesProductsDAO;
import merman.util.dao.interfaces.FlipContainersDAO;
import merman.util.dao.interfaces.ProductsDAO;
import merman.util.dao.interfaces.VATRatesDAO;
import merman.util.model.ConsumableInvoicesProducts;
import merman.util.model.FlipContainers;
import merman.util.model.Products;
import merman.util.model.VATRates;
import javafx.beans.property.LongProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import static java.lang.String.valueOf;

/**
 * @author Arthur Sadykov
 */
public class ConsumableInvoicesProductsController implements Initializable {

    private DAOFactory daoFactory;
    private ConsumableInvoicesController consumableInvoicesController;
    @FXML
    private ButtonBar bbSaveCancel;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSave;
    @FXML
    private ComboBox<LongProperty> cbFlipContainer;
    @FXML
    private ComboBox<LongProperty> cbProduct;
    @FXML
    private ComboBox<LongProperty> cbVatRate;
    @FXML
    private GridPane gpBasic;
    @FXML
    private TextField tfAmount;
    @FXML
    private TextField tfPrice;
    @FXML
    private TextField tfProductRemainder;
    @FXML
    private TextField tfQuantity;
    @FXML
    private TextField tfVatAmount;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        StringConverter<LongProperty> productStringConverter = new StringConverter<>() {
            @Override
            public LongProperty fromString(String string) {
                return null;
            }

            @Override
            public String toString(LongProperty t) {
                ProductsDAO productsDAO = daoFactory.getProductsDAO();
                return Optional.ofNullable(productsDAO.get(t.longValue())).map(Products::getName).orElse(null);
            }
        };
        StringConverter<LongProperty> vatRateStringConverter = new StringConverter<>() {
            @Override
            public LongProperty fromString(String string) {
                return null;
            }

            @Override
            public String toString(LongProperty t) {
                VATRatesDAO vatRatesDAO = daoFactory.getVATRatesDAO();
                return Optional.ofNullable(vatRatesDAO.get(t.longValue())).map(VATRates::getName).orElse(null);
            }
        };
        StringConverter<LongProperty> flipContainerStringConverter = new StringConverter<>() {
            @Override
            public LongProperty fromString(String string) {
                return null;
            }

            @Override
            public String toString(LongProperty t) {
                FlipContainersDAO flipContainersDAO = daoFactory.getFlipContainersDAO();
                return Optional.ofNullable(flipContainersDAO.get(t.longValue())).map(FlipContainers::getName).orElse(null);
            }
        };
        cbFlipContainer.setConverter(flipContainerStringConverter);
        cbProduct.setConverter(productStringConverter);
        cbVatRate.setConverter(vatRateStringConverter);
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
        StringConverter<Double> doubleStringConverter = new StringConverter<>() {
            final DecimalFormatSymbols decimalFormatSymbols;
            final DecimalFormat decimalFormat;

            {
                decimalFormatSymbols = DecimalFormatSymbols.getInstance(Locale.getDefault());
                decimalFormatSymbols.setDecimalSeparator('.');
                decimalFormat = new DecimalFormat("#.##", decimalFormatSymbols);
            }

            @Override
            public String toString(Double number) {
                return Optional.ofNullable(number).map(decimalFormat::format).orElse(null);
            }

            @Override
            public Double fromString(String string) {
                return Optional.ofNullable(string).map(Double::parseDouble).orElse(null);
            }
        };
        tfPrice.setTextFormatter(new TextFormatter<>(doubleStringConverter, null, doubleFilter));
        tfProductRemainder.setTextFormatter(new TextFormatter<>(doubleStringConverter, null, doubleFilter));
        tfQuantity.setTextFormatter(new TextFormatter<>(doubleStringConverter, null, doubleFilter));
        tfPrice.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("")) {
                if (!tfQuantity.getText().matches("")) {
                    tfAmount.setText(valueOf(Double.parseDouble(newValue) * Double.parseDouble(tfQuantity.getText())));
                }
            }
        });
        tfQuantity.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("")) {
                if (!tfPrice.getText().matches("")) {
                    tfAmount.setText(valueOf(Double.parseDouble(newValue) * Double.parseDouble(tfPrice.getText())));
                }
            }
        });
        tfAmount.setTextFormatter(new TextFormatter<>(doubleStringConverter));
        tfVatAmount.setTextFormatter(new TextFormatter<>(doubleStringConverter));
        tfAmount.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("")) {
                Optional.ofNullable(cbVatRate.getValue()).map((vatRateId) -> {
                    VATRatesDAO vatRatesDAO = daoFactory.getVATRatesDAO();
                    return vatRatesDAO.get(vatRateId.get());
                }).map(VATRates::getRate).ifPresent((rate) -> tfVatAmount.setText(valueOf(Double.parseDouble(newValue) * rate.doubleValue() / (100 + rate.doubleValue()))));
            }
        });
        cbVatRate.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (!tfAmount.getText().matches("")) {
                long vatRateId = newValue.get();
                VATRatesDAO vatRatesDAO = daoFactory.getVATRatesDAO();
                VATRates vatRate = vatRatesDAO.get(vatRateId);
                BigDecimal rate = vatRate.getRate();
                tfVatAmount.setText(valueOf(Double.parseDouble(tfAmount.getText()) * rate.doubleValue() / (100 + rate.doubleValue())));
            }
        });
    }

    public void setupAfterInitialize(ConsumableInvoicesController consumableInvoicesController) {
        ConsumableInvoicesProductsDAO consumableInvoicesProductsDAO = daoFactory.getConsumableInvoicesProductsDAO();
        this.consumableInvoicesController = consumableInvoicesController;
        FlipContainersDAO flipContainersDAO = daoFactory.getFlipContainersDAO();
        ProductsDAO productsDAO = daoFactory.getProductsDAO();
        VATRatesDAO vatRatesDAO = daoFactory.getVATRatesDAO();
        flipContainersDAO.list().forEach((flipContainer) -> cbFlipContainer.getItems().add(flipContainer.idProperty()));
        productsDAO.list().forEach((product) -> cbProduct.getItems().add(product.idProperty()));
        vatRatesDAO.list().forEach((vatRate) -> cbVatRate.getItems().add(vatRate.idProperty()));
    }

    public void setDAOFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @FXML
    private void handleBtnCancelPress(ActionEvent event) {
        ((Stage) (bbSaveCancel.getScene().getWindow())).close();
    }

    @FXML
    private void handleBtnSavePress(ActionEvent event) {
        ObservableList<ConsumableInvoicesProducts> items = consumableInvoicesController.getTvConsumableInvoicesProducts().getItems();
        Long product = cbProduct.getValue() == null ? null : cbProduct.getValue().get();
        for (ConsumableInvoicesProducts item : items) {
            if (item.getProduct() == product) {
                AlertFX.showWarning("Товар с таким названием уже есть в таблице");
                return;
            }
        }
        BigDecimal quantity = tfQuantity.getText() == null || tfQuantity.getText().matches("") ? null : new BigDecimal(tfQuantity.getText());
        BigDecimal price = tfPrice.getText() == null || tfPrice.getText().matches("") ? null : new BigDecimal(tfPrice.getText());
        BigDecimal amount = tfAmount.getText() == null || tfAmount.getText().matches("") ? null : new BigDecimal(tfAmount.getText());
        Long vatRate = cbVatRate.getValue() == null ? null : cbVatRate.getValue().get();
        BigDecimal vatAmount = tfVatAmount.getText() == null || tfVatAmount.getText().matches("") ? null : new BigDecimal(tfVatAmount.getText());
        Long flipContainer = cbFlipContainer.getValue() == null ? null : cbFlipContainer.getValue().get();
        BigDecimal productRemainder = tfProductRemainder.getText() == null || tfProductRemainder.getText().matches("") ? null : new BigDecimal(tfProductRemainder.getText());
        ConsumableInvoicesProducts consumableInvoicesProduct = new ConsumableInvoicesProducts(null, product, quantity, price, amount, vatRate, vatAmount, flipContainer, productRemainder, null);
        items.add(consumableInvoicesProduct);
        ((Stage) (bbSaveCancel.getScene().getWindow())).close();
    }
}
