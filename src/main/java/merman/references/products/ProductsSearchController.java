package merman.references.products;

import merman.util.dao.DAOFactory;
import merman.util.dao.interfaces.ProductsDAO;
import merman.util.model.Products;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.util.Callback;

import java.net.URL;
import java.util.*;

/**
 * @author Arthur Sadykov
 */
public class ProductsSearchController implements Initializable {

    private static final String NAME = "name";
    private static final String FULL_NAME = "fullName";
    private static final String SHORT_NAME = "shortName";
    private static final String UNIT = "unit";
    private static final String TARA = "tara";
    private static final String UNIT_WEIGHT = "unitWeight";
    private static final String NUMBER_OF_SEATS = "numberOfSeats";
    private static final String TYPE_OF_PACKAGING = "typeOfPackaging";
    private static final String NUMBER_OF_UNITS = "numberOfUnits";
    private static final String COMMENT = "comment";
    private static final String PRODUCT_CATEGORY = "productCategory";
    private static final String PRODUCT_TYPE = "productType";
    private static final String BAR_CODE = "barCode";
    private static final String VAT_RATE = "vatRate";
    private static final String COST_PRICE = "costPrice";
    private static final String PRODUCER = "producer";
    private static final String VENDOR_CODE = "vendorCode";
    private static final String ADDITIONAL_INFORMATION = "additionalInformation";
    private static final String SHELF_LIFE = "shelfLife";
    private static final String WARRANTY_PERIOD = "warrantyPeriod";
    private static final String STORAGE_CONDITIONS = "storageConditions";
    private static final String GOST = "GOST";
    private static final String DETAILED_PRODUCT_DESCRIPTION = "detailedProductDescription";
    private static final String PRODUCT_TYPE_CODE = "productTypeCode";
    private static final String CERTIFICATE = "certificate";
    private static final String CERTIFICATE_OF_STATE_REGISTRATION_OF_PRODUCTS = "certificateOfStateRegistrationOfProducts";
    private static final String BATCH_NUMBER = "batchNumber";
    private static final String QUANTITY_IN_A_BATCH = "quantityInABatch";
    private static final String DATE_OF_ISSUE = "dateOfIssue";
    private static final String CERTIFICATE_SCAN = "certificateScan";
    private static final String ID = "id";
    private final Map<String, ObservableValue<Boolean>> optionsMap = new LinkedHashMap<>();
    private final Map<String, String> textColumnNameMap = new LinkedHashMap<>();
    private DAOFactory daoFactory;
    @FXML
    private Button btnSearch;

    @FXML
    private ListView<String> lvOptions;

    @FXML
    private TableColumn<Products, StringProperty> tcName;
    @FXML
    private TableColumn<Products, StringProperty> tcShortName;
    @FXML
    private TextField tfSearch;
    @FXML
    private TableView<Products> tvProducts;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        optionsMap.put("Наименование", new SimpleBooleanProperty(false));
        optionsMap.put("Полное наименование", new SimpleBooleanProperty(false));
        optionsMap.put("Единицы измерения", new SimpleBooleanProperty(false));
        optionsMap.put("Ставка НДС", new SimpleBooleanProperty(false));
        optionsMap.put("Комментарий", new SimpleBooleanProperty(false));
        optionsMap.put("Наименование для ведомости", new SimpleBooleanProperty(false));
        optionsMap.put("Оборотная тара", new SimpleBooleanProperty(false));
        optionsMap.put("Масса единицы (брутто)", new SimpleBooleanProperty(false));
        optionsMap.put("Количество мест в машине", new SimpleBooleanProperty(false));
        optionsMap.put("Вид упаковки", new SimpleBooleanProperty(false));
        optionsMap.put("В упаковке штук", new SimpleBooleanProperty(false));
        optionsMap.put("Категория", new SimpleBooleanProperty(false));
        optionsMap.put("Тип товара", new SimpleBooleanProperty(false));
        optionsMap.put("Штрих-код", new SimpleBooleanProperty(false));
        optionsMap.put("Плановая себестоимость продукции", new SimpleBooleanProperty(false));
        optionsMap.put("Производитель", new SimpleBooleanProperty(false));
        optionsMap.put("Артикул", new SimpleBooleanProperty(false));
        optionsMap.put("Срок годности", new SimpleBooleanProperty(false));
        optionsMap.put("Дополнительные сведения", new SimpleBooleanProperty(false));
        optionsMap.put("Условия хранения", new SimpleBooleanProperty(false));
        optionsMap.put("ГОСТ", new SimpleBooleanProperty(false));
        optionsMap.put("Подробное описание товара", new SimpleBooleanProperty(false));
        optionsMap.put("Гарантийный срок", new SimpleBooleanProperty(false));
        optionsMap.put("Код вида товара", new SimpleBooleanProperty(false));
        optionsMap.put("Cертификат", new SimpleBooleanProperty(false));
        optionsMap.put("Свидетельство о гос. регистрации продукции", new SimpleBooleanProperty(false));
        optionsMap.put("№ партии", new SimpleBooleanProperty(false));
        optionsMap.put("Количество в партии", new SimpleBooleanProperty(false));
        optionsMap.put("Дата выпуска", new SimpleBooleanProperty(false));
        optionsMap.put("Скан сертификата", new SimpleBooleanProperty(false));
        optionsMap.put("Код", new SimpleBooleanProperty(false));
        lvOptions.getItems().addAll(optionsMap.keySet());
        Callback<String, ObservableValue<Boolean>> itemToBoolean = optionsMap::get;
        lvOptions.setCellFactory(CheckBoxListCell.forListView(itemToBoolean));
        textColumnNameMap.put("Наименование", NAME);
        textColumnNameMap.put("Полное наименование", FULL_NAME);
        textColumnNameMap.put("Единицы измерения", UNIT);
        textColumnNameMap.put("Ставка НДС", VAT_RATE);
        textColumnNameMap.put("Комментарий", COMMENT);
        textColumnNameMap.put("Наименование для ведомости", SHORT_NAME);
        textColumnNameMap.put("Оборотная тара", TARA);
        textColumnNameMap.put("Масса единицы (брутто)", UNIT_WEIGHT);
        textColumnNameMap.put("Количество мест в машине", NUMBER_OF_SEATS);
        textColumnNameMap.put("Вид упаковки", TYPE_OF_PACKAGING);
        textColumnNameMap.put("В упаковке штук", NUMBER_OF_UNITS);
        textColumnNameMap.put("Категория", PRODUCT_CATEGORY);
        textColumnNameMap.put("Тип товара", PRODUCT_TYPE);
        textColumnNameMap.put("Штрих-код", BAR_CODE);
        textColumnNameMap.put("Плановая себестоимость продукции", COST_PRICE);
        textColumnNameMap.put("Производитель", PRODUCER);
        textColumnNameMap.put("Артикул", VENDOR_CODE);
        textColumnNameMap.put("Срок годности", SHELF_LIFE);
        textColumnNameMap.put("Дополнительные сведения", ADDITIONAL_INFORMATION);
        textColumnNameMap.put("Условия хранения", STORAGE_CONDITIONS);
        textColumnNameMap.put("ГОСТ", GOST);
        textColumnNameMap.put("Подробное описание товара", DETAILED_PRODUCT_DESCRIPTION);
        textColumnNameMap.put("Гарантийный срок", WARRANTY_PERIOD);
        textColumnNameMap.put("Код вида товара", PRODUCT_TYPE_CODE);
        textColumnNameMap.put("Cертификат", CERTIFICATE);
        textColumnNameMap.put("Свидетельство о гос. регистрации продукции", CERTIFICATE_OF_STATE_REGISTRATION_OF_PRODUCTS);
        textColumnNameMap.put("№ партии", BATCH_NUMBER);
        textColumnNameMap.put("Количество в партии", QUANTITY_IN_A_BATCH);
        textColumnNameMap.put("Дата выпуска", DATE_OF_ISSUE);
        textColumnNameMap.put("Скан сертификата", CERTIFICATE_SCAN);
        textColumnNameMap.put("Код", ID);
    }

    public void setDAOFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @FXML
    private void handleBtnSearchPress(ActionEvent event) {
        List<String> columnNames = new ArrayList<>();
        optionsMap.keySet().forEach((key) -> {
            ObservableValue<Boolean> value = optionsMap.get(key);
            if (value.getValue()) {
                columnNames.add(textColumnNameMap.get(key));
            }
        });
        String searchString = tfSearch.getText();
        ProductsDAO productsDAO = daoFactory.getProductsDAO();
        List<Products> products = productsDAO.find(searchString, columnNames);
        tvProducts.getItems().setAll(products);
    }
}
