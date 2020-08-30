package merman.references.products;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import static java.math.BigDecimal.valueOf;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
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
import javafx.print.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.transform.Scale;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import merman.util.dao.DAOFactory;
import merman.util.dao.interfaces.FlipContainersDAO;
import merman.util.dao.interfaces.ProductCategoriesDAO;
import merman.util.dao.interfaces.ProductsDAO;
import merman.util.dao.interfaces.ProductsPricesDAO;
import merman.util.dao.interfaces.TypesOfPricesDAO;
import merman.util.dao.interfaces.UnitsDAO;
import merman.util.dao.interfaces.VATRatesDAO;
import merman.util.model.FlipContainers;
import merman.util.model.ProductCategories;
import merman.util.model.Products;
import merman.util.model.ProductsPrices;
import merman.util.model.TypesOfPrices;
import merman.util.model.Units;
import merman.util.model.VATRates;

/**
 * @author Arthur Sadykov
 */
public class ProductsController implements Initializable {

    private static final String PLEASE_FILL_ALL_REQUIRED_FIELDS = "Пожалуйста, заполните все необходимые поля";
    private static final String REQUIRED_FIELD = "Укажите требуемую информацию";
    private static final String PATH_TO_SEARCH_PRODUCT =
            "/merman/references/products/products-search.fxml";
    private DAOFactory daoFactory;
    private ProductsDAO productsDAO;
    @FXML
    private ComboBox<LongProperty> cbProductCategory;
    @FXML
    private ComboBox<LongProperty> cbUnit;
    @FXML
    private ButtonBar bbSaveCancel;
    @FXML
    private Button btnAddProduct;
    @FXML
    private Button btnBarcode;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnClear;
    @FXML
    private Button btnDeletePrice;
    @FXML
    private Button btnUpdateProduct;
    @FXML
    private Button btnLoadPhoto;
    @FXML
    private Button btnMagnify;
    @FXML
    private Button btnNewPrice;
    @FXML
    private Button btnRemoveProduct;
    @FXML
    private Button btnSales;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnScanClear;
    @FXML
    private Button btnScanLoad;
    @FXML
    private Button btnScanMagnify;
    @FXML
    private Button btnSearchProduct;
    @FXML
    private Button btnSearchPrice;
    @FXML
    private Button btnTailings;
    @FXML
    private ComboBox<LongProperty> cbProducer;
    @FXML
    private ComboBox<LongProperty> cbTara;
    @FXML
    private ComboBox<LongProperty> cbVatRate;
    @FXML
    private DatePicker dpDateOfIssue;
    @FXML
    private GridPane gpAdditional;
    @FXML
    private GridPane gpBasic;
    @FXML
    private GridPane gpCertificate;
    @FXML
    private GridPane gpPhoto;
    @FXML
    private ImageView ivCertificateScan;
    @FXML
    private ImageView ivPhoto;
    @FXML
    private Label lbId;
    @FXML
    private SplitPane spSplitPane;
    @FXML
    private TextArea taComment;
    @FXML
    private TextArea taDetailedProductDescription;
    @FXML
    private TableColumn<ProductsPrices, Double> tcFromCount;
    @FXML
    private TableColumn<Products, StringProperty> tcName;
    @FXML
    private TableColumn<Products, StringProperty> tcShortName;
    @FXML
    private TableColumn<ProductsPrices, Double> tcPrice;
    @FXML
    private TableColumn<ProductsPrices, String> tcPriceType;
    @FXML
    private TextField tfAdditionalInformation;
    @FXML
    private TextField tfBarCode;
    @FXML
    private TextField tfProductTypeCode;
    @FXML
    private TextField tfVendorCode;
    @FXML
    private TextField tfBatchNumber;
    @FXML
    private TextField tfCertificate;
    @FXML
    private TextField tfCertificateOfStateRegistrationOfProducts;
    @FXML
    private TextField tfCostPrice;
    @FXML
    private TextField tfFullName;
    @FXML
    private TextField tfGOST;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfNumberOfSeats;
    @FXML
    private TextField tfNumberOfUnits;
    @FXML
    private TextField tfQuantityInABatch;
    @FXML
    private TextField tfShelfLife;
    @FXML
    private TextField tfShortName;
    @FXML
    private TextField tfStorageConditions;
    @FXML
    private TextField tfTypeOfPackaging;
    @FXML
    private TextField tfUnitWeight;
    @FXML
    private TextField tfWarrantyPeriod;
    @FXML
    private TabPane tpRightTabPane;
    private TabPane tpContentPane;
    @FXML
    private TableView<Products> tvProducts;
    @FXML
    private TableView<ProductsPrices> tvPrices;
    private int lastSelected;

    public void setDAOFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<Products> list = FXCollections.observableArrayList((products) -> new Observable[]{
            products.idProperty(), products.nameProperty(), products.fullNameProperty(), products.shortNameProperty(),
            products.unitProperty(), products.taraProperty(), products.unitWeightProperty(),
            products.numberOfSeatsProperty(), products.typeOfPackagingProperty(), products.numberOfUnitsProperty(),
            products.commentProperty(), products.productCategoryProperty(), products.barCodeProperty(),
            products.vatRateProperty(), products.costPriceProperty(), products.producerProperty(),
            products.vendorCodeProperty(), products.additionalInformationProperty(), products.shelfLifeProperty(),
            products.warrantyPeriodProperty(), products.storageConditionsProperty(), products.gOSTProperty(),
            products.detailedProductDescriptionProperty(), products.photoProperty(),
            products.productTypeCodeProperty(), products.certificateProperty(),
            products.certificateOfStateRegistrationOfProductsProperty(), products.batchNumberProperty(),
            products.quantityInABatchProperty(), products.dateOfIssueProperty(), products.certificateScanProperty()});
        list.addListener((ListChangeListener.Change<? extends Products> change) -> {
            while (change.next()) {
                if (change.wasRemoved()) {
                    if (list.isEmpty()) {
                        btnUpdateProduct.setDisable(true);
                        btnRemoveProduct.setDisable(true);
                        btnSearchProduct.setDisable(true);
                    }
                }
                if (change.wasAdded()) {
                    if (!list.isEmpty()) {
                        btnUpdateProduct.setDisable(false);
                        btnRemoveProduct.setDisable(false);
                        btnSearchProduct.setDisable(false);
                    }
                }
            }
        });
        tvProducts.setItems(list);
        tvProducts.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) ->
                Optional.ofNullable(tvProducts.getSelectionModel().getSelectedItem()).ifPresent((product) -> {
                    fillControls(product);
                    ProductsPricesDAO productsPricesDAO = daoFactory.getProductsPricesDAO();
                    List<ProductsPrices> productsPrices = productsPricesDAO.list();
                    tvPrices.getItems().addAll(productsPrices);
                }));
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
        tfCostPrice.setTextFormatter(new TextFormatter<>(new DoubleStringConverter(), null, doubleFilter));
        tfNumberOfUnits.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), null, integerFilter));
        tfShelfLife.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), null, integerFilter));
        tfWarrantyPeriod.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), null, integerFilter));
        tfQuantityInABatch.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), null, integerFilter));
        tfNumberOfSeats.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), null, integerFilter));
        tfUnitWeight.setTextFormatter(new TextFormatter<>(new DoubleStringConverter(), null, doubleFilter));
        StringConverter<LongProperty> productCategoryStringConverter = new StringConverter<>() {
            @Override
            public LongProperty fromString(String string) {
                return null;
            }

            @Override
            public String toString(LongProperty t) {
                ProductCategoriesDAO productCategoriesDAO = daoFactory.getProductCategoriesDAO();
                return Optional.ofNullable(productCategoriesDAO.get(t.longValue())).map(ProductCategories::getName).orElse(null);
            }
        };
        StringConverter<LongProperty> unitStringConverter = new StringConverter<>() {
            @Override
            public LongProperty fromString(String string) {
                return null;
            }

            @Override
            public String toString(LongProperty t) {
                UnitsDAO unitsDAO = daoFactory.getUnitsDAO();
                return Optional.ofNullable(unitsDAO.get(t.longValue())).map(Units::getName).orElse(null);
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
        cbTara.setConverter(flipContainerStringConverter);
        cbProductCategory.setConverter(productCategoryStringConverter);
        cbUnit.setConverter(unitStringConverter);
        cbVatRate.setConverter(vatRateStringConverter);
        tcPrice.setCellFactory((column) -> new DoubleTableCell<>());
        tcFromCount.setCellFactory((column) -> new DoubleTableCell<>());
        tcPrice.setOnEditCommit((event) -> {
            ProductsPrices productsPrices = event.getRowValue();
            productsPrices.setPrice(valueOf(event.getNewValue()));
            ProductsPricesDAO productsPricesDAO = daoFactory.getProductsPricesDAO();
            int update = productsPricesDAO.update(productsPrices);
        });
    }

    public void setupAfterInitialize(TabPane tpContentPane) {
        productsDAO = daoFactory.getProductsDAO();
        this.tpContentPane = tpContentPane;
        ProductCategoriesDAO productCategoriesDAO = daoFactory.getProductCategoriesDAO();
        UnitsDAO unitsDAO = daoFactory.getUnitsDAO();
        VATRatesDAO vatRatesDAO = daoFactory.getVATRatesDAO();
        FlipContainersDAO flipContainersDAO = daoFactory.getFlipContainersDAO();
        vatRatesDAO.list().forEach((vatRate) -> cbVatRate.getItems().add(vatRate.idProperty()));
        productCategoriesDAO.list().forEach((productCategory) ->
                cbProductCategory.getItems().add(productCategory.idProperty()));
        unitsDAO.list().forEach((unit) -> cbUnit.getItems().add(unit.idProperty()));
        flipContainersDAO.list().forEach((flipContainer) -> cbTara.getItems().add(flipContainer.idProperty()));
        tvProducts.getItems().addAll(productsDAO.list());
        if (!tvProducts.getItems().isEmpty()) {
            btnUpdateProduct.setDisable(false);
            btnRemoveProduct.setDisable(false);
            btnSearchProduct.setDisable(false);
            tvProducts.getSelectionModel().selectFirst();
        } else {
            btnUpdateProduct.setDisable(true);
            btnRemoveProduct.setDisable(true);
            btnSearchProduct.setDisable(true);
        }
        TypesOfPricesDAO typesOfPricesDAO = daoFactory.getTypesOfPricesDAO();
        List<TypesOfPrices> typesOfPrices = typesOfPricesDAO.list();
        List<String> l = typesOfPrices.stream().map(TypesOfPrices::getName).collect(Collectors.toList());
        tcPriceType.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(l)));
        ProductsPricesDAO productsPricesDAO = daoFactory.getProductsPricesDAO();
        tvPrices.getItems().addAll(productsPricesDAO.list());
    }

    @FXML
    private void handleBtnAddProductPress(ActionEvent event) {
        bbSaveCancel.setDisable(false);
        gpBasic.setDisable(false);
        gpAdditional.setDisable(false);
        gpCertificate.setDisable(false);
        gpPhoto.setDisable(false);
        btnAddProduct.setDisable(true);
        btnUpdateProduct.setDisable(true);
        btnRemoveProduct.setDisable(true);
        btnSearchProduct.setDisable(true);
        clearControls();
    }

    @FXML
    private void handleBtnBarcodePress(ActionEvent event) {
        Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout =
                printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.HARDWARE_MINIMUM);
        PrinterAttributes attr = printer.getPrinterAttributes();
        PrinterJob job = PrinterJob.createPrinterJob();
        double scaleX = pageLayout.getPrintableWidth() / tpRightTabPane.getBoundsInParent().getWidth();
        double scaleY = pageLayout.getPrintableHeight() / tpRightTabPane.getBoundsInParent().getHeight();
        Scale scale = new Scale(scaleX, scaleY);
        tpRightTabPane.getTransforms().add(scale);
        if (job != null && job.showPrintDialog(tpRightTabPane.getScene().getWindow())) {
            boolean success = job.printPage(pageLayout, tpRightTabPane);
            if (success) {
                job.endJob();
            }
        }
        tpRightTabPane.getTransforms().remove(scale);
    }

    @FXML
    private void handleBtnCancelPress(ActionEvent event) {
        btnAddProduct.setDisable(false);
        if (!tvProducts.getItems().isEmpty()) {
            btnUpdateProduct.setDisable(false);
            btnRemoveProduct.setDisable(false);
            btnSearchProduct.setDisable(false);
            tvProducts.getSelectionModel().selectFirst();
        } else {
            btnUpdateProduct.setDisable(true);
            btnRemoveProduct.setDisable(true);
            btnSearchProduct.setDisable(true);
        }
        bbSaveCancel.setDisable(true);
        gpBasic.setDisable(true);
    }

    @FXML
    private void handleBtnClearPhotoPress(ActionEvent event) {
    }

    @FXML
    private void handleBtnDeletePricePress(ActionEvent event) {
        ProductsPrices productsPrices = tvPrices.getSelectionModel().getSelectedItem();
        if (productsPrices != null) {
            ProductsPricesDAO productsPricesDAO = daoFactory.getProductsPricesDAO();
            int deleted = productsPricesDAO.delete(productsPrices);
            if (deleted > 0) {
                tvPrices.getItems().remove(productsPrices);
            }
        }
    }

    @FXML
    private void handleBtnMagnifyPhotoPress(ActionEvent event) {
    }

    @FXML
    private void handleBtnUpdateProductPress(ActionEvent event) {
        Optional.ofNullable(tvProducts.getSelectionModel().getSelectedItem()).ifPresent((product) -> {
            bbSaveCancel.setDisable(false);
            gpBasic.setDisable(false);
            gpAdditional.setDisable(false);
            gpCertificate.setDisable(false);
            gpPhoto.setDisable(false);
            btnAddProduct.setDisable(true);
            btnUpdateProduct.setDisable(true);
            btnRemoveProduct.setDisable(true);
            btnSearchProduct.setDisable(true);
            fillControls(product);
        });
    }

    @FXML
    private void handleBtnLoadPhotoPress(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Графические файлы", "*.png", "*.jpg ", ".gif"));
        File file = fileChooser.showOpenDialog(btnLoadPhoto.getScene().getWindow());
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            ivPhoto.setImage(image);
        }
    }

    @FXML
    private void handleBtnNewPricePress(ActionEvent event) {
        ProductsPrices productsPrices = new ProductsPrices();
        ProductsPricesDAO productsPricesDAO = daoFactory.getProductsPricesDAO();
        int added = productsPricesDAO.add(productsPrices);
        if (added > 0) {
            tvPrices.getItems().add(productsPrices);
        }
    }

    @FXML
    private void handleBtnRemoveProductPress(ActionEvent event) {
        Optional.ofNullable(tvProducts.getSelectionModel().getSelectedItem()).ifPresent((product) -> {
            int result = productsDAO.delete(product);
            if (result > 0) {
                tvProducts.getItems().remove(product);
            }
            clearControls();
        });
    }

    @FXML
    private void handleBtnSalesPress(ActionEvent event) {
    }

    @FXML
    private void handleBtnSavePress(ActionEvent event) {
        Long id = lbId.getText() == null || lbId.getText().matches("") ? null : Long.parseLong(lbId.getText());
        String name = tfName.getText();
        String fullName = tfFullName.getText();
        String shortName = tfShortName.getText();
        Long unit = cbUnit.getValue() == null ? null : cbUnit.getValue().get();
        Long tara = cbTara.getValue() == null ? null : cbTara.getValue().get();
        BigDecimal unitWeight = tfUnitWeight.getText() == null || tfUnitWeight.getText().matches("") ? null
                : new BigDecimal(tfUnitWeight.getText());
        BigDecimal numberOfSeats = tfNumberOfSeats.getText() == null || tfNumberOfSeats.getText().matches("") ? null
                : new BigDecimal(tfNumberOfSeats.getText());
        String typeOfPackaging = tfTypeOfPackaging.getText();
        Integer numberOfUnits = tfNumberOfUnits.getText().matches("") ? null
                : Integer.parseInt(tfNumberOfUnits.getText());
        String comment = taComment.getText();
        Long productCategory = cbProductCategory.getValue() == null ? null : cbProductCategory.getValue().get();
        String barCode = tfBarCode.getText();
        Long vatRate = cbVatRate.getValue() == null ? null : cbVatRate.getValue().get();
        BigDecimal costPrice = tfCostPrice.getText() == null || tfCostPrice.getText().matches("") ? null
                : new BigDecimal(tfCostPrice.getText());
        Long producer = cbProducer.getValue() == null ? null : cbProducer.getValue().get();
        String vendorCode = tfVendorCode.getText();
        String additionalInformation = tfAdditionalInformation.getText();
        Integer shelfLife = tfShelfLife.getText().matches("") ? null : Integer.parseInt(tfShelfLife.getText());
        Integer warrantyPeriod = tfWarrantyPeriod.getText().matches("") ? null
                : Integer.parseInt(tfWarrantyPeriod.getText());
        String storageConditions = tfStorageConditions.getText();
        String GOST = tfGOST.getText();
        String detailedProductDescription = taDetailedProductDescription.getText();
        Image photo = ivPhoto.getImage();
        String productTypeCode = tfProductTypeCode.getText();
        String certificate = tfCertificate.getText();
        String certificateOfStateRegistrationOfProducts = tfCertificateOfStateRegistrationOfProducts.getText();
        String batchNumber = tfBatchNumber.getText();
        Integer quantityInABatch = tfQuantityInABatch.getText().matches("") ? null
                : Integer.parseInt(tfQuantityInABatch.getText());
        LocalDateTime dateOfIssue = dpDateOfIssue.getValue() == null ? null : dpDateOfIssue.getValue().atStartOfDay();
        Image certificateScan = ivCertificateScan.getImage();
        if (id == null) {
            Products product =
                    new Products(id, name, fullName, shortName, unit, tara, unitWeight, numberOfSeats, typeOfPackaging, numberOfUnits, comment, productCategory, barCode, vatRate, costPrice, producer, vendorCode, additionalInformation, shelfLife, warrantyPeriod, storageConditions, GOST, detailedProductDescription, photo, productTypeCode, certificate, certificateOfStateRegistrationOfProducts, batchNumber, quantityInABatch, dateOfIssue, certificateScan);
            int result = productsDAO.add(product);
            if (result > 0) {
                tvProducts.getItems().add(product);
            }
        } else {
            Products product =
                    new Products(id, name, fullName, shortName, unit, tara, unitWeight, numberOfSeats, typeOfPackaging, numberOfUnits, comment, productCategory, barCode, vatRate, costPrice, producer, vendorCode, additionalInformation, shelfLife, warrantyPeriod, storageConditions, GOST, detailedProductDescription, photo, productTypeCode, certificate, certificateOfStateRegistrationOfProducts, batchNumber, quantityInABatch, dateOfIssue, certificateScan);
            int result = productsDAO.update(product);
            if (result > 0) {
                tvProducts.getItems().set(tvProducts.getSelectionModel().getSelectedIndex(), product);
            }
        }
        gpBasic.setDisable(true);
        bbSaveCancel.setDisable(true);
        btnAddProduct.setDisable(false);
        btnUpdateProduct.setDisable(false);
        btnRemoveProduct.setDisable(false);
        btnSearchProduct.setDisable(false);
        gpBasic.setDisable(true);
        gpAdditional.setDisable(true);
        gpCertificate.setDisable(true);
        gpPhoto.setDisable(true);
        bbSaveCancel.setDisable(true);
    }

    @FXML
    private void handleBtnScanClearPress(ActionEvent event) {
    }

    @FXML
    private void handleBtnScanLoadPress(ActionEvent event) {
    }

    @FXML
    private void handleBtnScanMagnifyPress(ActionEvent event) {
    }

    @FXML
    private void handleBtnSearchProductPress() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PATH_TO_SEARCH_PRODUCT));
        AnchorPane apSearchPane = fxmlLoader.load();
        ProductsSearchController productsSearchController = fxmlLoader.getController();
        productsSearchController.setDAOFactory(daoFactory);
        Tab tab = new Tab("Поиск в таблице \"Товары\"", apSearchPane);
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
    private void handleBtnSearchPricePress(ActionEvent event) {
    }

    @FXML
    private void handleBtnTailingsPress(ActionEvent event) {
    }

    private void fillControls(Products product) {
        lbId.setText(product.getId() == null ? "" : product.getId().toString());
        tfName.setText(product.getName());
        tfFullName.setText(product.getFullName());
        tfShortName.setText(product.getShortName());
        cbTara.setValue(product.taraProperty());
        tfUnitWeight.setText(product.getUnitWeight() == null ? null : product.getUnitWeight().toString());
        tfNumberOfSeats.setText(product.getNumberOfSeats() == null ? null : product.getNumberOfSeats().toString());
        tfTypeOfPackaging.setText(product.getTypeOfPackaging());
        tfNumberOfUnits.setText(product.getNumberOfUnits() == null ? null : product.getNumberOfUnits().toString());
        taComment.setText(product.getComment());
        cbVatRate.setValue(product.vatRateProperty());
        tfCostPrice.setText(product.getCostPrice() == null ? null : product.getCostPrice().toString());
        cbProducer.setValue(product.producerProperty());
        tfVendorCode.setText(product.getVendorCode());
        tfAdditionalInformation.setText(product.getAdditionalInformation());
        tfShelfLife.setText(product.getShelfLife() == null ? null : product.getShelfLife().toString());
        tfWarrantyPeriod.setText(product.getWarrantyPeriod() == null ? null : product.getWarrantyPeriod().toString());
        tfStorageConditions.setText(product.getStorageConditions());
        tfGOST.setText(product.getGOST());
        taDetailedProductDescription.setText(product.getDetailedProductDescription());
        ivPhoto.setImage(product.getPhoto());
        tfCertificate.setText(product.getCertificate());
        tfCertificateOfStateRegistrationOfProducts.setText(product.getCertificateOfStateRegistrationOfProducts());
        tfBatchNumber.setText(product.getBatchNumber());
        tfQuantityInABatch.setText(product.getQuantityInABatch() == null ? null
                : product.getQuantityInABatch().toString());
        dpDateOfIssue.setValue(product.getDateOfIssue() == null ? null : product.getDateOfIssue().toLocalDate());
        ivCertificateScan.setImage(product.getCertificateScan());
    }

    private void clearControls() {
        lbId.setText("");
        tfName.setText("");
        tfFullName.setText("");
        tfShortName.setText("");
        cbUnit.getSelectionModel().selectFirst();
        cbTara.getSelectionModel().selectFirst();
        tfUnitWeight.setText("");
        tfNumberOfSeats.setText("");
        tfTypeOfPackaging.setText("");
        tfNumberOfUnits.setText("");
        taComment.setText("");
        cbProductCategory.getSelectionModel().selectFirst();
        tfBarCode.setText("");
        cbVatRate.getSelectionModel().selectFirst();
        tfCostPrice.setText("");
        cbProducer.getSelectionModel().selectFirst();
        tfVendorCode.setText("");
        tfAdditionalInformation.setText("");
        tfShelfLife.setText("");
        tfWarrantyPeriod.setText("");
        tfStorageConditions.setText("");
        tfGOST.setText("");
        taDetailedProductDescription.setText("");
        ivPhoto.setImage(null);
        tfProductTypeCode.setText("");
        tfCertificate.setText("");
        tfCertificateOfStateRegistrationOfProducts.setText("");
        tfBatchNumber.setText("");
        tfQuantityInABatch.setText("");
        dpDateOfIssue.setValue(null);
        ivCertificateScan.setImage(null);
    }

    class DoubleTableCell<ProductsPrices, Double> extends TableCell<ProductsPrices, Double> {

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
            setText(newValue.toString());
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
