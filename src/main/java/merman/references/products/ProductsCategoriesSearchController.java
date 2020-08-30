package merman.references.products;

import merman.util.dao.DAOFactory;
import merman.util.dao.interfaces.ProductCategoriesDAO;
import merman.util.model.ProductCategories;
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
public class ProductsCategoriesSearchController implements Initializable {

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String BUDGET_ITEM_INCOME = "budgetItemIncome";
    private static final String BUDGET_ITEM_EXPENSE = "budgetItemExpense";
    private static final String DISREGARD_WEIGHT = "disregardWeight";
    private final Map<String, ObservableValue<Boolean>> optionsMap = new LinkedHashMap<>();
    private final Map<String, String> textColumnNameMap = new LinkedHashMap<>();
    private DAOFactory daoFactory;
    @FXML
    private Button btnSearch;
    @FXML
    private ListView<String> lvOptions;
    @FXML
    private TableColumn<ProductCategories, StringProperty> tcName;
    @FXML
    private TextField tfSearch;
    @FXML
    private TableView<ProductCategories> tvProductCategories;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        optionsMap.put("Наименование", new SimpleBooleanProperty(false));
        optionsMap.put("Статья бюджета (приход)", new SimpleBooleanProperty(false));
        optionsMap.put("Статья бюджета (расход)", new SimpleBooleanProperty(false));
        optionsMap.put("Не учитывать вес", new SimpleBooleanProperty(false));
        optionsMap.put("Код", new SimpleBooleanProperty(false));
        lvOptions.getItems().addAll(optionsMap.keySet());
        Callback<String, ObservableValue<Boolean>> itemToBoolean = optionsMap::get;
        lvOptions.setCellFactory(CheckBoxListCell.forListView(itemToBoolean));
        textColumnNameMap.put("Наименование", NAME);
        textColumnNameMap.put("Статья бюджета (приход)", BUDGET_ITEM_INCOME);
        textColumnNameMap.put("Статья бюджета (расход)", BUDGET_ITEM_EXPENSE);
        textColumnNameMap.put("Не учитывать вес", DISREGARD_WEIGHT);
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
        ProductCategoriesDAO productCategoriesDAO = daoFactory.getProductCategoriesDAO();
        List<ProductCategories> productCategories = productCategoriesDAO.find(searchString, columnNames);
        tvProductCategories.getItems().setAll(productCategories);
    }
}
