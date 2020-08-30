package merman.documents.consumableinvoices;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.property.LongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import merman.alert.AlertFX;
import merman.util.dao.DAOFactory;
import merman.util.dao.interfaces.BanksDAO;
import merman.util.dao.interfaces.ConsumableInvoicesDAO;
import merman.util.dao.interfaces.ConsumableInvoicesProductsDAO;
import merman.util.dao.interfaces.ContractorsContractsDAO;
import merman.util.dao.interfaces.ContractorsDAO;
import merman.util.dao.interfaces.CurrenciesDAO;
import merman.util.dao.interfaces.DiscountsDAO;
import merman.util.dao.interfaces.EmployeesDAO;
import merman.util.dao.interfaces.FirmsBankAccountsDAO;
import merman.util.dao.interfaces.FirmsDAO;
import merman.util.dao.interfaces.FlipContainersDAO;
import merman.util.dao.interfaces.ProductsDAO;
import merman.util.dao.interfaces.TypesOfPricesDAO;
import merman.util.dao.interfaces.UnitsDAO;
import merman.util.dao.interfaces.VATRatesDAO;
import merman.util.dao.interfaces.WarehousesDAO;
import merman.util.model.Banks;
import merman.util.model.ConsumableInvoices;
import merman.util.model.ConsumableInvoicesProducts;
import merman.util.model.ConsumableInvoicesProductsTotal;
import merman.util.model.Contractors;
import merman.util.model.ContractorsContracts;
import merman.util.model.Currencies;
import merman.util.model.Discounts;
import merman.util.model.Firms;
import merman.util.model.FirmsBankAccounts;
import merman.util.model.Products;
import merman.util.model.TypesOfPrices;
import merman.util.model.Units;
import merman.util.model.VATRates;
import merman.util.model.Warehouses;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STHeightRule;

/**
 * @author Arthur Sadykov
 */
public class ConsumableInvoicesController implements Initializable {

    private static final String PNG_EXT = "png";
    private static final String DOCUMENT_PRINT = "Документ \"Счёт-фактура\"";
    private static final String PATH_TO_CONSUMABLE_INVOICES_PRODUCTS =
            "/merman/documents/consumableinvoices/consumable-invoices-products.fxml";
    private static final String PATH_TO_MERMAN_DIR = System.getProperty("java.io.tmpdir") + "/merman/";
    private static final String DOCUMENT_DOCX = "document.docx";
    private static final String SCHET_FAKTURA_DOCX = "schet-faktura.docx";
    private static final String SCHET_NA_OPLATU_DOCX = "schet-na-oplatu.docx";
    private static final String DOCUMENT_FXML = "document.fxml";
    private ConsumableInvoicesTableController consumableInvoicesTableController;
    private DAOFactory daoFactory;
    private ConsumableInvoicesDAO consumableInvoicesDAO;
    private TabPane tpContentPane;
    @FXML
    private ButtonBar bbSaveCancel;
    @FXML
    private Button btnAddProduct;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnRemoveProduct;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnSearchProduct;
    @FXML
    private TextField tfAddress;
    @FXML
    private ComboBox<LongProperty> cbChiefAccountant;
    @FXML
    private ComboBox<LongProperty> cbContract;
    @FXML
    private ComboBox<LongProperty> cbContractor;
    @FXML
    private ComboBox<LongProperty> cbDirector;
    @FXML
    private ComboBox<LongProperty> cbDispatcher;
    @FXML
    private ComboBox<LongProperty> cbFirm;
    @FXML
    private ComboBox<LongProperty> cbWarehouse;
    @FXML
    private TextField tfDeliveryTimeFrom;
    @FXML
    private TextField tfDeliveryTimeTo;
    @FXML
    private DatePicker dpDocumentDate;
    @FXML
    private DatePicker dpPowerOfAttorneyDate;
    @FXML
    private GridPane gpHeader;
    @FXML
    private GridPane gpPrint;
    @FXML
    private GridPane gpSignatures;
    @FXML
    private TableColumn<ConsumableInvoicesProducts, Double> tcAmount;
    @FXML
    private TableColumn<ConsumableInvoicesProducts, Number> tcFlipContainer;
    @FXML
    private TableColumn<ConsumableInvoicesProducts, Double> tcPrice;
    @FXML
    private TableColumn<ConsumableInvoicesProducts, Number> tcProduct;
    @FXML
    private TableColumn<ConsumableInvoicesProducts, Double> tcProductRemainder;
    @FXML
    private TableColumn<ConsumableInvoicesProducts, Double> tcQuantity;
    @FXML
    private TableColumn<ConsumableInvoicesProducts, Double> tcVatAmount;
    @FXML
    private TableColumn<ConsumableInvoicesProducts, Number> tcVatRate;
    @FXML
    private TextField tfCarrierPosition;
    @FXML
    private TextField tfComment;
    @FXML
    private TextField tfConsigneePosition;
    @FXML
    private TextField tfNumber;
    @FXML
    private TextField tfPowerOfAttorneyNumber;
    @FXML
    private TextField tfWhoAndToWhomThePowerOfAttorneyWasIssued;
    @FXML
    private TabPane tpBottomTabPane;
    @FXML
    private TabPane tpTopTabPane;
    @FXML
    private TableView<ConsumableInvoicesProducts> tvConsumableInvoicesProducts;
    @FXML
    private MenuItem miInvoice;
    @FXML
    private TableView<ConsumableInvoicesProductsTotal> tvSumTable;
    @FXML
    private TableColumn<ConsumableInvoicesProducts, Double> tcTotal;
    @FXML
    private TableColumn<ConsumableInvoicesProducts, Double> tcVatTotal;
    @FXML
    private TableColumn tcProductTotal;
    @FXML
    private TableColumn tcVatRateTotal;
    private ConsumableInvoices consumableInvoice;
    @FXML
    private ProgressBar pbProgress;
    @FXML
    private Label lbDocumentPreparing;
    @FXML
    private MenuItem miPaymentInvoice;
    @FXML
    private Tab tbAdditionalInformation;
    @FXML
    private ComboBox<LongProperty> cbCheckingAccount;
    @FXML
    private ComboBox<LongProperty> cbDiscount;
    @FXML
    private ComboBox<LongProperty> cbPriceType;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        StringConverter<LongProperty> checkingAccountStringConverter = new StringConverter<>() {
            @Override
            public LongProperty fromString(String string) {
                return null;
            }

            @Override
            public String toString(LongProperty t) {
                FirmsBankAccountsDAO bankAccountsDAO = daoFactory.getFirmsBankAccountsDAO();
                return Optional.ofNullable(t).map(LongProperty::get).map(bankAccountsDAO::get).map(FirmsBankAccounts::getName).orElse(null);
            }
        };
        cbCheckingAccount.setConverter(checkingAccountStringConverter);
        StringConverter<LongProperty> discountStringConverter = new StringConverter<>() {
            @Override
            public LongProperty fromString(String string) {
                return null;
            }

            @Override
            public String toString(LongProperty t) {
                DiscountsDAO discountsDAO = daoFactory.getDiscountsDAO();
                return Optional.ofNullable(t).map(LongProperty::get).map(discountsDAO::get).map(Discounts::getName).orElse(null);
            }
        };
        cbDiscount.setConverter(discountStringConverter);
        StringConverter<LongProperty> typeOfPricesStringConverter = new StringConverter<>() {
            @Override
            public LongProperty fromString(String string) {
                return null;
            }

            @Override
            public String toString(LongProperty t) {
                TypesOfPricesDAO typeOfPricesDAO = daoFactory.getTypesOfPricesDAO();
                return Optional.ofNullable(t).map(LongProperty::get).map(typeOfPricesDAO::get).map(TypesOfPrices::getName).orElse(null);
            }
        };
        cbPriceType.setConverter(typeOfPricesStringConverter);
        StringConverter<LongProperty> employeeStringConverter = new StringConverter<>() {
            @Override
            public LongProperty fromString(String string) {
                return null;
            }

            @Override
            public String toString(LongProperty t) {
                EmployeesDAO employeesDAO = daoFactory.getEmployeesDAO();
                return Optional.ofNullable(employeesDAO.get(t.longValue())).map((employee) -> employee.getSurname()
                        + " " + employee.getName()).orElse(null);
            }
        };
        cbDirector.setConverter(employeeStringConverter);
        cbChiefAccountant.setConverter(employeeStringConverter);
        cbDispatcher.setConverter(employeeStringConverter);
        StringConverter<LongProperty> firmsStringConverter = new StringConverter<>() {
            @Override
            public LongProperty fromString(String string) {
                return null;
            }

            @Override
            public String toString(LongProperty t) {
                FirmsDAO firmsDAO = daoFactory.getFirmsDAO();
                return Optional.ofNullable(t).map(LongProperty::get).map(firmsDAO::get).map(Firms::getName).orElse(null);
            }
        };
        cbFirm.setConverter(firmsStringConverter);
        StringConverter<LongProperty> warehouseStringConverter = new StringConverter<>() {
            @Override
            public LongProperty fromString(String string) {
                return null;
            }

            @Override
            public String toString(LongProperty t) {
                WarehousesDAO warehousesDAO = daoFactory.getWarehousesDAO();
                return Optional.ofNullable(warehousesDAO.get(t.longValue())).map(Warehouses::getName).orElse(null);
            }
        };
        cbWarehouse.setConverter(warehouseStringConverter);
        StringConverter<LongProperty> contractorsStringConverter = new StringConverter<>() {
            @Override
            public LongProperty fromString(String string) {
                return null;
            }

            @Override
            public String toString(LongProperty t) {
                ContractorsDAO contractorsDAO = daoFactory.getContractorsDAO();
                return Optional.ofNullable(contractorsDAO.get(t.longValue())).map(Contractors::getName).orElse(null);
            }
        };
        cbContractor.setConverter(contractorsStringConverter);
        StringConverter<LongProperty> contractsStringConverter = new StringConverter<>() {
            @Override
            public LongProperty fromString(String string) {
                return null;
            }

            @Override
            public String toString(LongProperty t) {
                ContractorsContractsDAO contractorsContractsDAO = daoFactory.getContractorsContractsDAO();
                return Optional.ofNullable(contractorsContractsDAO.get(t.longValue())).map(ContractorsContracts::getName).orElse(null);
            }
        };
        cbContract.setConverter(contractsStringConverter);
        tcProduct.setCellFactory((col) -> new TableCell<>() {
            @Override
            public void updateItem(Number product, boolean empty) {
                super.updateItem(product, empty);
                if (empty || product == null) {
                    setText(null);
                } else {
                    ProductsDAO productsDAO = daoFactory.getProductsDAO();
                    Optional.of(product.longValue()).map(productsDAO::get).ifPresent((prod) -> setText(prod.getName()));
                }
            }
        });
        tcFlipContainer.setCellFactory((col) -> new TableCell<>() {
            @Override
            public void updateItem(Number flipContainer, boolean empty) {
                super.updateItem(flipContainer, empty);
                if (empty || flipContainer == null) {
                    setText(null);
                } else {
                    FlipContainersDAO flipContainersDAO = daoFactory.getFlipContainersDAO();
                    Optional.of(flipContainer.longValue()).map(flipContainersDAO::get).ifPresent((fc) ->
                            setText(fc.getName()));
                }
            }
        });
        tcVatRate.setCellFactory((col) -> new TableCell<>() {
            @Override
            public void updateItem(Number vatRate, boolean empty) {
                super.updateItem(vatRate, empty);
                if (empty || vatRate == null) {
                    setText(null);
                } else {
                    VATRatesDAO vatRatesDAO = daoFactory.getVATRatesDAO();
                    Optional.of(vatRate.longValue()).map(vatRatesDAO::get).ifPresent((vr) -> setText(vr.getName()));
                }
            }
        });
        tcTotal.prefWidthProperty().bind(tcAmount.widthProperty());
        tcVatTotal.prefWidthProperty().bind(tcVatAmount.widthProperty());
        tcProductTotal.prefWidthProperty().bind(tcProduct.widthProperty().add(tcQuantity.widthProperty()).add(tcPrice.widthProperty()));
        tcVatRateTotal.prefWidthProperty().bind(tcVatRate.widthProperty());
        ObservableList<ConsumableInvoicesProducts> items = FXCollections.observableArrayList(item -> new Observable[]{
            item.amountProperty(), item.vatAmountProperty()});
        items.addListener((ListChangeListener.Change<? extends ConsumableInvoicesProducts> change) -> {
            while (change.next()) {
                if (change.wasRemoved()) {
                    tvSumTable.setItems(FXCollections.observableArrayList(new ConsumableInvoicesProductsTotal(tvConsumableInvoicesProducts.getItems())));
                }
                if (change.wasAdded()) {
                    tvSumTable.setItems(FXCollections.observableArrayList(new ConsumableInvoicesProductsTotal(tvConsumableInvoicesProducts.getItems())));
                }
            }
        });
        tvConsumableInvoicesProducts.setItems(items);
        tcAmount.setReorderable(false);
        tcPrice.setReorderable(false);
        tcVatRate.setReorderable(false);
        tcVatAmount.setReorderable(false);
        tcFlipContainer.setReorderable(false);
        tcProductRemainder.setReorderable(false);
        tcQuantity.setReorderable(false);
        tcProduct.setReorderable(false);
    }

    public void setDAOFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public void setupAfterInitialize(TabPane tpContentPane,
            ConsumableInvoicesTableController consumableInvoicesTableController, ConsumableInvoices consumableInvoice) {
        this.consumableInvoice = consumableInvoice;
        consumableInvoicesDAO = daoFactory.getConsumableInvoicesDAO();
        this.consumableInvoicesTableController = consumableInvoicesTableController;
        this.tpContentPane = tpContentPane;
        FirmsBankAccountsDAO bankAccountsDAO = daoFactory.getFirmsBankAccountsDAO();
        bankAccountsDAO.list().forEach(account -> {
            cbCheckingAccount.getItems().add(account.idProperty());
        });
        EmployeesDAO employeesDAO = daoFactory.getEmployeesDAO();
        employeesDAO.list().forEach((employee) -> {
            cbDirector.getItems().add(employee.idProperty());
            cbChiefAccountant.getItems().add(employee.idProperty());
            cbDispatcher.getItems().add(employee.idProperty());
        });
        FirmsDAO firmsDAO = daoFactory.getFirmsDAO();
        firmsDAO.list().forEach((firm) -> cbFirm.getItems().add(firm.idProperty()));
        DiscountsDAO discountsDAO = daoFactory.getDiscountsDAO();
        discountsDAO.list().forEach((discount) -> {
            cbDiscount.getItems().add(discount.idProperty());
        });
        TypesOfPricesDAO typesOfPricesDAO = daoFactory.getTypesOfPricesDAO();
        typesOfPricesDAO.list().forEach((typeOfPrices) -> {
            cbPriceType.getItems().add(typeOfPrices.idProperty());
        });
        WarehousesDAO warehousesDAO = daoFactory.getWarehousesDAO();
        warehousesDAO.list().forEach((warehouse) -> cbWarehouse.getItems().add(warehouse.idProperty()));
        ContractorsDAO contractorsDAO = daoFactory.getContractorsDAO();
        contractorsDAO.list().forEach((contractor) -> cbContractor.getItems().add(contractor.idProperty()));
        ContractorsContractsDAO contractorsContractsDAO = daoFactory.getContractorsContractsDAO();
        contractorsContractsDAO.list().forEach((contract) -> cbContract.getItems().add(contract.idProperty()));
        tvSumTable.setItems(FXCollections.observableArrayList(new ConsumableInvoicesProductsTotal(tvConsumableInvoicesProducts.getItems())));
        fillControls(consumableInvoice);
    }

    @FXML
    private void handleBtnAddProductPress() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PATH_TO_CONSUMABLE_INVOICES_PRODUCTS));
        Stage stage = new Stage();
        Parent root = fxmlLoader.load();
        ConsumableInvoicesProductsController consumableInvoicesProductsController = fxmlLoader.getController();
        consumableInvoicesProductsController.setDAOFactory(daoFactory);
        consumableInvoicesProductsController.setupAfterInitialize(this);
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(cbCheckingAccount.getScene().getWindow());
        stage.setTitle("Информационная система предприятия по доставке питьевой воды");
        List<Image> images = new ArrayList<>();
        images.add(new Image(getClass().getResourceAsStream("/merman/icons/water16x16.png")));
        images.add(new Image(getClass().getResourceAsStream("/merman/icons/water24x24.png")));
        images.add(new Image(getClass().getResourceAsStream("/merman/icons/water32x32.png")));
        images.add(new Image(getClass().getResourceAsStream("/merman/icons/water64x64.png")));
        images.add(new Image(getClass().getResourceAsStream("/merman/icons/water128x128.png")));
        images.add(new Image(getClass().getResourceAsStream("/merman/icons/water256x256.png")));
        stage.getIcons().addAll(images);
        stage.setResizable(false);
        stage.showAndWait();
    }

    @FXML
    private void handleBtnCancelPress() {
        tpContentPane.getTabs().remove(tpContentPane.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void handleBtnRemoveProductPress() {
        Optional.ofNullable(tvConsumableInvoicesProducts.getSelectionModel().getSelectedItem()).ifPresent(consumableInvoicesProduct ->
        {
            ConsumableInvoicesProductsDAO consumableInvoicesProductsDAO = daoFactory.getConsumableInvoicesProductsDAO();
            int deleted = consumableInvoicesProductsDAO.delete(consumableInvoicesProduct);
            if (deleted > 0) {
                tvConsumableInvoicesProducts.getItems().remove(consumableInvoicesProduct);
            }
        });
    }

    @FXML
    private void handleBtnSavePress() {
        String number = tfNumber.getText();
        LocalDateTime documentDate = dpDocumentDate.getValue() == null ? null : dpDocumentDate.getValue().atStartOfDay();
        String comment = tfComment.getText();
        Long firm = cbFirm.getValue() == null ? null : cbFirm.getValue().get();
        Long warehouse = cbWarehouse.getValue() == null ? null : cbWarehouse.getValue().get();
        Long contractor = cbContractor.getValue() == null ? null : cbContractor.getValue().get();
        Long contract = cbContract.getValue() == null ? null : cbContract.getValue().get();
        String address = tfAddress.getText();
        String deliveryTimeFrom = tfDeliveryTimeFrom.getText();
        String deliveryTimeTo = tfDeliveryTimeTo.getText();
        String powerOfAttorneyNumber = tfPowerOfAttorneyNumber.getText();
        LocalDateTime powerOfAttorneyDate = dpPowerOfAttorneyDate.getValue() == null ? null
                : dpPowerOfAttorneyDate.getValue().atStartOfDay();
        String whoAndToWhomThePowerOfAttorneyWasIssued = tfWhoAndToWhomThePowerOfAttorneyWasIssued.getText();
        String carrierPosition = tfCarrierPosition.getText();
        String consigneePosition = tfConsigneePosition.getText();
        Long dispatcher = cbDispatcher.getValue() == null ? null : cbDispatcher.getValue().get();
        Long director = cbDirector.getValue() == null ? null : cbDirector.getValue().get();
        Long chiefAccountant = cbChiefAccountant.getValue() == null ? null : cbChiefAccountant.getValue().get();
        Long checkingAccount = cbCheckingAccount.getValue() == null ? null : cbCheckingAccount.getValue().get();
        Long discount = cbDiscount.getValue() == null ? null : cbDiscount.getValue().get();
        Long typeOfPrices = cbPriceType.getValue() == null ? null : cbPriceType.getValue().get();
        consumableInvoicesDAO = daoFactory.getConsumableInvoicesDAO();
        if (number.isEmpty()) {
            ConsumableInvoices consumableInvoice =
                    new ConsumableInvoices(null, number, documentDate, comment, firm, warehouse, contractor, contract, address, deliveryTimeFrom, deliveryTimeTo, powerOfAttorneyNumber, powerOfAttorneyDate, whoAndToWhomThePowerOfAttorneyWasIssued, carrierPosition, consigneePosition, dispatcher, director, chiefAccountant, checkingAccount, discount, typeOfPrices);
            int added = consumableInvoicesDAO.add(consumableInvoice);
            if (added > 0) {
                consumableInvoicesTableController.getTvConsumableInvoices().getItems().add(consumableInvoice);
                tpContentPane.getTabs().remove(tpContentPane.getSelectionModel().getSelectedItem());
            }
        } else {
            consumableInvoice.setNumber(number);
            consumableInvoice.setDocumentDate(documentDate);
            consumableInvoice.setComment(comment);
            consumableInvoice.setFirm(firm);
            consumableInvoice.setWarehouse(warehouse);
            consumableInvoice.setContractor(contractor);
            consumableInvoice.setContract(contract);
            consumableInvoice.setAddress(address);
            consumableInvoice.setDeliveryTimeFrom(deliveryTimeFrom);
            consumableInvoice.setDeliveryTimeTo(deliveryTimeTo);
            consumableInvoice.setPowerOfAttorneyNumber(powerOfAttorneyNumber);
            consumableInvoice.setPowerOfAttorneyDate(powerOfAttorneyDate);
            consumableInvoice.setWhoAndToWhomThePowerOfAttorneyWasIssued(whoAndToWhomThePowerOfAttorneyWasIssued);
            consumableInvoice.setCarrierPosition(carrierPosition);
            consumableInvoice.setConsigneePosition(consigneePosition);
            consumableInvoice.setDispatcher(dispatcher);
            consumableInvoice.setDirector(director);
            consumableInvoice.setChiefAccountant(chiefAccountant);
            consumableInvoice.setCheckingAccount(checkingAccount);
            consumableInvoice.setDiscount(discount);
            consumableInvoice.setTypeOfPrices(typeOfPrices);
            int updated = consumableInvoicesDAO.update(consumableInvoice);
            if (updated > 0) {
                tpContentPane.getTabs().remove(tpContentPane.getSelectionModel().getSelectedItem());
            }
        }
        ConsumableInvoicesProductsDAO consumableInvoicesProductsDAO = daoFactory.getConsumableInvoicesProductsDAO();
        ObservableList<ConsumableInvoicesProducts> products = tvConsumableInvoicesProducts.getItems();
        products.forEach((product) -> {
            product.setBaseTable(consumableInvoice.getId());
            if (consumableInvoicesProductsDAO.exists(product)) {
                consumableInvoicesProductsDAO.update(product);
            } else {
                consumableInvoicesProductsDAO.add(product);
            }
        });
    }

    @FXML
    private void handleBtnSearchProductPress() {
    }

    private void fillControls(ConsumableInvoices consumableInvoice) {
        Optional.ofNullable(consumableInvoice).ifPresent(c -> {
            tfNumber.setText(consumableInvoice.getNumber());
            dpDocumentDate.setValue(consumableInvoice.getDocumentDate() == null ? null
                    : consumableInvoice.getDocumentDate().toLocalDate());
            tfComment.setText(consumableInvoice.getComment());
            cbFirm.setValue(consumableInvoice.firmProperty());
            cbWarehouse.setValue(consumableInvoice.warehouseProperty());
            cbContractor.setValue(consumableInvoice.contractorProperty());
            cbContract.setValue(consumableInvoice.contractProperty());
            tfAddress.setText(consumableInvoice.getAddress());
            tfDeliveryTimeFrom.setText(consumableInvoice.getDeliveryTimeFrom());
            tfDeliveryTimeTo.setText(consumableInvoice.getDeliveryTimeTo());
            tfPowerOfAttorneyNumber.setText(consumableInvoice.getPowerOfAttorneyNumber());
            dpPowerOfAttorneyDate.setValue(consumableInvoice.getPowerOfAttorneyDate() == null ? null
                    : consumableInvoice.getPowerOfAttorneyDate().toLocalDate());
            tfWhoAndToWhomThePowerOfAttorneyWasIssued.setText(consumableInvoice.getWhoAndToWhomThePowerOfAttorneyWasIssued());
            tfCarrierPosition.setText(consumableInvoice.getCarrierPosition());
            tfConsigneePosition.setText(consumableInvoice.getConsigneePosition());
            cbDispatcher.setValue(consumableInvoice.dispatcherProperty());
            cbDirector.setValue(consumableInvoice.directorProperty());
            cbChiefAccountant.setValue(consumableInvoice.chiefAccountantProperty());
            cbCheckingAccount.setValue(consumableInvoice.checkingAccountProperty());
            cbDiscount.setValue(consumableInvoice.discountProperty());
            cbPriceType.setValue(consumableInvoice.typeOfPricesProperty());
            ConsumableInvoicesProductsDAO consumableInvoicesProductsDAO = daoFactory.getConsumableInvoicesProductsDAO();
            List<ConsumableInvoicesProducts> consumableInvoicesProducts =
                    consumableInvoicesProductsDAO.getByConsumableInvoicesId(consumableInvoice.getId());
            tvConsumableInvoicesProducts.getItems().setAll(consumableInvoicesProducts);
        });
    }

    public TableView<ConsumableInvoicesProducts> getTvConsumableInvoicesProducts() {
        return tvConsumableInvoicesProducts;
    }

    @FXML
    private void handleMiInvoicePress() {
        try {
            Path pathToBeDeleted = Paths.get(PATH_TO_MERMAN_DIR);
            if (Files.exists(pathToBeDeleted)) {
                Files.walk(pathToBeDeleted).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
            }
        } catch (IOException e) {
            AlertFX.showError("Ошибка удаления файла", e);
        }
        Task<Void> task = new InvoiceCreatingTask();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        task.setOnSucceeded(event -> {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(DOCUMENT_FXML));
            Parent root = null;
            try {
                root = fxmlLoader.load();
                DocumentController documentController = fxmlLoader.getController();
                if (Files.exists(Paths.get(PATH_TO_MERMAN_DIR))) {
                    List<String> pngFiles = Files.walk(Paths.get(PATH_TO_MERMAN_DIR)).filter(path ->
                            path.getFileName().endsWith(Paths.get(PNG_EXT))).map(path ->
                            path.toAbsolutePath().toString()).collect(Collectors.toList());
                    documentController.setupAfterInitialize(pngFiles);
                }
            } catch (IOException e) {
                AlertFX.showError(e.getMessage(), e);
            }
            Tab tab = new Tab(DOCUMENT_PRINT, root);
            tpContentPane.getTabs().add(tab);
            tpContentPane.getSelectionModel().selectNext();
            pbProgress.setVisible(false);
            lbDocumentPreparing.setVisible(false);
        });
        task.setOnFailed(event -> {
            pbProgress.setVisible(false);
            lbDocumentPreparing.setVisible(false);
        });
        task.setOnCancelled(event -> {
            pbProgress.setVisible(false);
            lbDocumentPreparing.setVisible(false);
        });
        pbProgress.setVisible(true);
        lbDocumentPreparing.setVisible(true);
        executor.submit(task);
    }

    private void writeToTable(String text, XWPFDocument document, int t, int r, int c) {
        Optional.ofNullable(document).map(d -> d.getTableArray(t)).map(table -> table.getRow(r)).map(row ->
                row.getCell(c)).map(cell -> {
            cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
            return cell.getParagraphs().get(0);
        }).map(paragraph -> {
            paragraph.setAlignment(ParagraphAlignment.CENTER);
            return paragraph.createRun();
        }).ifPresent(run -> {
            run.setFontSize(8);
            run.setFontFamily("Times New Roman");
            run.setText(text);
        });
    }

    private void writeToTable(String text, XWPFTableCell cell) {
        Optional.ofNullable(cell).ifPresent(c -> {
            XWPFParagraph paragraph = cell.getParagraphs().get(0);
            paragraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun run = paragraph.createRun();
            run.setFontSize(8);
            run.setFontFamily("Times New Roman");
            cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
            run.setText(text);
        });
    }

    @FXML
    private void handleMiPaymentInvoicePress() {
        try {
            Path pathToBeDeleted = Paths.get(PATH_TO_MERMAN_DIR);
            if (Files.exists(pathToBeDeleted)) {
                Files.walk(pathToBeDeleted).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
            }
        } catch (IOException e) {
            AlertFX.showError("Ошибка удаления файла", e);
        }
        Task<Void> task = new PaymentInvoiceCreatingTask();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        task.setOnSucceeded(event -> {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(DOCUMENT_FXML));
            Parent root = null;
            try {
                root = fxmlLoader.load();
                DocumentController documentController = fxmlLoader.getController();
                if (Files.exists(Paths.get(PATH_TO_MERMAN_DIR))) {
                    List<String> pngFiles = Files.walk(Paths.get(PATH_TO_MERMAN_DIR)).filter(path ->
                            path.getFileName().endsWith(Paths.get(PNG_EXT))).map(path ->
                            path.toAbsolutePath().toString()).collect(Collectors.toList());
                    documentController.setupAfterInitialize(pngFiles);
                }
            } catch (IOException e) {
                AlertFX.showError(e.getMessage(), e);
            }
            Tab tab = new Tab(DOCUMENT_PRINT, root);
            tpContentPane.getTabs().add(tab);
            tpContentPane.getSelectionModel().selectNext();
            pbProgress.setVisible(false);
            lbDocumentPreparing.setVisible(false);
        });
        task.setOnFailed(event -> {
            pbProgress.setVisible(false);
            lbDocumentPreparing.setVisible(false);
        });
        task.setOnCancelled(event -> {
            pbProgress.setVisible(false);
            lbDocumentPreparing.setVisible(false);
        });
        pbProgress.setVisible(true);
        lbDocumentPreparing.setVisible(true);
        executor.submit(task);
    }

    class InvoiceCreatingTask extends Task<Void> {

        @Override
        protected Void call() {
            try (XWPFDocument doc = new XWPFDocument(getClass().getResourceAsStream(SCHET_FAKTURA_DOCX))) {
                writeToTable(tfNumber.getText(), doc, 0, 0, 2);
                writeToTable(Optional.ofNullable(dpDocumentDate.getValue()).map(date -> date.getDayOfMonth() + "."
                        + date.getMonthValue()).orElse(null), doc, 0, 0, 4);
                writeToTable(Optional.ofNullable(dpDocumentDate.getValue()).map(date -> String.valueOf(date.getYear())).orElse(null), doc, 0, 0, 6);
                writeToTable(cbFirm.getConverter().toString(cbFirm.getValue()), doc, 0, 2, 1);
                FirmsDAO firmsDAO = daoFactory.getFirmsDAO();
                Firms firm = firmsDAO.get(Optional.ofNullable(cbFirm.getValue()).map(LongProperty::get).orElse(null));
                Optional.ofNullable(firm).ifPresent(f -> {
                    writeToTable(firm.getLegalAddress(), doc, 0, 3, 1);
                    writeToTable(firm.getInn(), doc, 0, 4, 1);
                    writeToTable(firm.getName() + ", " + firm.getLegalAddress(), doc, 0, 5, 1);
                });
                ContractorsDAO contractorsDAO = daoFactory.getContractorsDAO();
                Contractors contractor =
                        contractorsDAO.get(Optional.ofNullable(cbContractor.getValue()).map(LongProperty::get).orElse(null));
                writeToTable(Optional.ofNullable(contractor).map(c -> c.getName() + ", " + c.getPhysicalAddress()).orElse(null), doc, 0, 6, 1);
                //writeToTable(Optional.ofNullable(contractor).map(Contractors::getName).orElse(null), doc, 0, 7, 1);
                writeToTable(Optional.ofNullable(contractor).map(Contractors::getName).orElse(null), doc, 0, 8, 1);
                writeToTable(Optional.ofNullable(contractor).map(Contractors::getPhysicalAddress).orElse(null), doc, 0, 9, 1);
                writeToTable(Optional.ofNullable(contractor).map(Contractors::getInn).orElse(null), doc, 0, 10, 1);
                CurrenciesDAO currenciesDAO = daoFactory.getCurrenciesDAO();
                Currencies currency = currenciesDAO.get(1);
                writeToTable(Optional.ofNullable(currency).map(c -> c.getName() + ", " + c.getCurrencyCode()).orElse(null), doc, 0, 11, 1);
                ContractorsContractsDAO contractorsContractsDAO = daoFactory.getContractorsContractsDAO();
                ContractorsContracts contract = contractorsContractsDAO.get(contractor);
                writeToTable(Optional.ofNullable(contract).map(ContractorsContracts::getName).orElse(null), doc, 0, 12, 1);
                XWPFTable table = doc.getTableArray(1);
                ProductsDAO productsDAO = daoFactory.getProductsDAO();
                tvConsumableInvoicesProducts.getItems().forEach(product -> {
                    XWPFTableRow row = new XWPFTableRow(CTRow.Factory.newInstance(), table);
                    row.setHeight(300);
                    row.getCtRow().getTrPr().getTrHeightArray(0).setHRule(STHeightRule.EXACT);
                    for (int i = 0; i < 14; i++) {
                        XWPFTableCell cell = row.addNewTableCell();
                        switch (i) {
                            case 0:
                                writeToTable(Optional.ofNullable(product).map(ConsumableInvoicesProducts::getProduct).map(productsDAO::get).map(Products::getName).orElse(null), cell);
                                break;
                            case 1:
                                writeToTable(Optional.ofNullable(product).map(ConsumableInvoicesProducts::getProduct).map(productsDAO::get).map(Products::getProductTypeCode).orElse(null), cell);
                                break;
                            case 2:
                                UnitsDAO unitsDAO = daoFactory.getUnitsDAO();
                                writeToTable(Optional.ofNullable(product).map(ConsumableInvoicesProducts::getProduct).map(productsDAO::get).map(Products::getUnit).map(unitsDAO::get).map(Units::getUnitCode).orElse(null), cell);
                                break;
                            case 3:
                                UnitsDAO ud = daoFactory.getUnitsDAO();
                                writeToTable(Optional.ofNullable(product).map(ConsumableInvoicesProducts::getProduct).map(productsDAO::get).map(Products::getUnit).map(ud::get).map(Units::getName).orElse(null), cell);
                                break;
                            case 4:
                                writeToTable(Optional.ofNullable(product).map(ConsumableInvoicesProducts::getQuantity).map(BigDecimal::toString).orElse(null), cell);
                                break;
                            case 5:
                                writeToTable(Optional.ofNullable(product).map(ConsumableInvoicesProducts::getPrice).map(BigDecimal::toString).orElse(null), cell);
                                break;
                            case 6:
                                writeToTable(Optional.ofNullable(product).map(ConsumableInvoicesProducts::getAmount).map(BigDecimal::toString).orElse(null), cell);
                                break;
                            case 7:
                                writeToTable("Без акциза", cell);
                                break;
                            case 8:
                                VATRatesDAO vatRatesDAO = daoFactory.getVATRatesDAO();
                                writeToTable(Optional.ofNullable(product).map(ConsumableInvoicesProducts::getVatRate).map(vatRatesDAO::get).map(VATRates::getRate).map(BigDecimal::toString).orElse(null), cell);
                                break;
                            case 9:
                                writeToTable(Optional.ofNullable(product).map(ConsumableInvoicesProducts::getVatAmount).map(BigDecimal::toString).orElse(null), cell);
                                break;
                            case 10:
                                writeToTable(Optional.ofNullable(product).map(ConsumableInvoicesProducts::getAmount).map(amount ->
                                        amount.add(product.getVatAmount())).map(BigDecimal::toString).orElse(null), cell);
                                break;
                            case 11:
                            case 12:
                            case 13:
                                writeToTable("-", cell);
                                break;
                        }
                    }
                    table.addRow(row, table.getNumberOfRows() - 1);
                });
                writeToTable(tvSumTable.getItems().get(0).getAmount().toString(), doc, 1, table.getNumberOfRows() - 1, 1);
                writeToTable(tvSumTable.getItems().get(0).getVatAmount().toString(), doc, 1, table.getNumberOfRows() - 1, 3);
                writeToTable(tvSumTable.getItems().get(0).getAmount().add(tvSumTable.getItems().get(0).getVatAmount()).toString(), doc, 1, table.getNumberOfRows()
                        - 1, 4);
                CTBorder ctBorder = CTBorder.Factory.newInstance();
                ctBorder.setVal(STBorder.SINGLE);
                table.getCTTbl().getTblPr().getTblBorders().setInsideH(ctBorder);
                table.getCTTbl().getTblPr().getTblBorders().setInsideV(ctBorder);
                writeToTable(Optional.ofNullable(firm).map(Firms::getCertificateOfIndividualEntrepreneur).orElse(null), doc, 2, 2, 6);
                Files.createDirectories(Paths.get(PATH_TO_MERMAN_DIR));
                try (OutputStream stream = new FileOutputStream(PATH_TO_MERMAN_DIR + DOCUMENT_DOCX)) {
                    doc.write(stream);
                } catch (IOException e) {
                    Platform.runLater(() -> AlertFX.showError(e.getMessage(), e));
                }
            } catch (Exception e) {
                Platform.runLater(() -> AlertFX.showError(e.getMessage(), e));
            }
            return null;
        }
    }

    private class PaymentInvoiceCreatingTask extends Task<Void> {

        @Override
        protected Void call() {
            try (XWPFDocument doc = new XWPFDocument(getClass().getResourceAsStream(SCHET_NA_OPLATU_DOCX))) {
                FirmsDAO firmsDAO = daoFactory.getFirmsDAO();
                Firms firm = firmsDAO.get(Optional.ofNullable(cbFirm.getValue()).map(LongProperty::get).orElse(null));
                Optional.ofNullable(firm).ifPresent(f -> {
                    writeToTable(firm.getName(), doc, 0, 0, 1);
                    writeToTable(firm.getLegalAddress(), doc, 0, 1, 1);
                    writeToTable(firm.getInn(), doc, 1, 0, 1);
                    writeToTable(firm.getKpp(), doc, 1, 0, 3);
                    writeToTable(firm.getName(), doc, 1, 1, 1);
                    FirmsBankAccountsDAO firmsBankAccountsDAO = daoFactory.getFirmsBankAccountsDAO();
                    BanksDAO banksDAO = daoFactory.getBanksDAO();
                    String bankName =
                            Optional.ofNullable(cbCheckingAccount.getValue()).map(LongProperty::get).map(firmsBankAccountsDAO::get).map(FirmsBankAccounts::getBank).map(banksDAO::get).map(Banks::getName).orElse("");
                    String bankCity =
                            Optional.ofNullable(cbCheckingAccount.getValue()).map(LongProperty::get).map(firmsBankAccountsDAO::get).map(FirmsBankAccounts::getBank).map(banksDAO::get).map(Banks::getCity).orElse("");
                    String bankBIK =
                            Optional.ofNullable(cbCheckingAccount.getValue()).map(LongProperty::get).map(firmsBankAccountsDAO::get).map(FirmsBankAccounts::getBank).map(banksDAO::get).map(Banks::getBIK).orElse("");
                    String bankCorrespondentAccount =
                            Optional.ofNullable(cbCheckingAccount.getValue()).map(LongProperty::get).map(firmsBankAccountsDAO::get).map(FirmsBankAccounts::getBank).map(banksDAO::get).map(Banks::getCorrespondentAccount).orElse("");
                    writeToTable(bankName + " " + bankCity, doc, 1, 2, 1);
                    writeToTable(Optional.ofNullable(cbCheckingAccount.getValue()).map(LongProperty::get).map(firmsBankAccountsDAO::get).map(FirmsBankAccounts::getName).orElse(null), doc, 1, 0, 2);
                    writeToTable(bankBIK + "\n", doc, 1, 1, 3);
                    writeToTable(bankCorrespondentAccount, doc, 1, 2, 2);
                });
                writeToTable(tfNumber.getText(), doc, 2, 0, 1);
                writeToTable(Optional.ofNullable(dpDocumentDate.getValue()).map(date ->
                        String.valueOf(date.getDayOfMonth())).orElse(null), doc, 2, 0, 3);
                writeToTable(Optional.ofNullable(dpDocumentDate.getValue()).map(date ->
                        String.valueOf(date.getMonth())).orElse(null), doc, 2, 0, 4);
                writeToTable(Optional.ofNullable(dpDocumentDate.getValue()).map(date -> String.valueOf(date.getYear())).orElse(null), doc, 2, 0, 5);
                ContractorsDAO contractorsDAO = daoFactory.getContractorsDAO();
                writeToTable(Optional.ofNullable(cbContractor.getValue()).map(LongProperty::get).map(contractorsDAO::get).map(Contractors::getName).orElse(null), doc, 3, 0, 1);
                writeToTable(Optional.ofNullable(cbContractor.getValue()).map(LongProperty::get).map(contractorsDAO::get).map(Contractors::getName).orElse(null), doc, 3, 1, 1);
                XWPFTable table = doc.getTableArray(4);
                ProductsDAO productsDAO = daoFactory.getProductsDAO();
                AtomicInteger productIndex = new AtomicInteger(0);
                tvConsumableInvoicesProducts.getItems().forEach(product -> {
                    XWPFTableRow row = new XWPFTableRow(CTRow.Factory.newInstance(), table);
                    row.setHeight(300);
                    row.getCtRow().getTrPr().getTrHeightArray(0).setHRule(STHeightRule.EXACT);
                    for (int i = 0; i < 6; i++) {
                        XWPFTableCell cell = row.addNewTableCell();
                        switch (i) {
                            case 0:
                                writeToTable(String.valueOf(productIndex.incrementAndGet()), cell);
                            case 1:
                                writeToTable(Optional.ofNullable(product).map(ConsumableInvoicesProducts::getProduct).map(productsDAO::get).map(Products::getName).orElse(null), cell);
                                break;
                            case 2:
                                UnitsDAO unitsDAO = daoFactory.getUnitsDAO();
                                writeToTable(Optional.ofNullable(product).map(ConsumableInvoicesProducts::getProduct).map(productsDAO::get).map(Products::getUnit).map(unitsDAO::get).map(Units::getUnitCode).orElse(null), cell);
                                break;
                            case 3:
                                writeToTable(Optional.ofNullable(product).map(ConsumableInvoicesProducts::getQuantity).map(BigDecimal::toString).orElse(null), cell);
                                break;
                            case 4:
                                writeToTable(Optional.ofNullable(product).map(ConsumableInvoicesProducts::getPrice).map(BigDecimal::toString).orElse(null), cell);
                                break;
                            case 5:
                                writeToTable(Optional.ofNullable(product).map(ConsumableInvoicesProducts::getAmount).map(BigDecimal::toString).orElse(null), cell);
                                break;
                        }
                    }
                    table.addRow(row, table.getNumberOfRows() - 3);
                });
                writeToTable(tvSumTable.getItems().get(0).getAmount().toString(), doc, 4, table.getNumberOfRows() - 3, 5);
                writeToTable(tvSumTable.getItems().get(0).getVatAmount().toString(), doc, 4, table.getNumberOfRows() - 2, 5);
                writeToTable(tvSumTable.getItems().get(0).getAmount().add(tvSumTable.getItems().get(0).getVatAmount()).toString(), doc, 4, table.getNumberOfRows()
                        - 1, 5);
                writeToTable(String.valueOf(tvConsumableInvoicesProducts.getItems().size()), doc, 5, 0, 1);
                writeToTable(tvSumTable.getItems().get(0).getAmount().toString(), doc, 5, 0, 3);
                CTBorder ctBorder = CTBorder.Factory.newInstance();
                ctBorder.setVal(STBorder.SINGLE);
                table.getCTTbl().getTblPr().getTblBorders().setInsideH(ctBorder);
                table.getCTTbl().getTblPr().getTblBorders().setInsideV(ctBorder);
                Files.createDirectories(Paths.get(PATH_TO_MERMAN_DIR));
                try (OutputStream stream = new FileOutputStream(PATH_TO_MERMAN_DIR + DOCUMENT_DOCX)) {
                    doc.write(stream);
                } catch (IOException e) {
                    Platform.runLater(() -> AlertFX.showError(e.getMessage(), e));
                }
            } catch (Exception e) {
                Platform.runLater(() -> AlertFX.showError(e.getMessage(), e));
            }
            return null;
        }
    }
}
