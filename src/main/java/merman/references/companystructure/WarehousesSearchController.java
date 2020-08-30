package merman.references.companystructure;

import merman.util.dao.DAOFactory;
import merman.util.dao.interfaces.EmployeesDAO;
import merman.util.dao.interfaces.WarehousesDAO;
import merman.util.model.Warehouses;
import javafx.beans.property.SimpleBooleanProperty;
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
public class WarehousesSearchController implements Initializable {

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String STOREKEEPER = "storekeeper";
    private static final String PHYSICAL_ADDRESS = "physicalAddress";
    private static final String EMAIL = "email";
    private final Map<String, ObservableValue<Boolean>> optionsMap = new LinkedHashMap<>();
    private final Map<String, String> textColumnNameMap = new LinkedHashMap<>();
    @FXML
    private Button btnSearch;
    @FXML
    private ListView<String> lvOptions;
    @FXML
    private TextField tfSearch;
    private DAOFactory daoFactory;
    @FXML
    private TableColumn<Warehouses, String> tcName;
    @FXML
    private TableColumn<Warehouses, Number> tcStorekeeper;
    @FXML
    private TableView<Warehouses> tvWarehouses;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcStorekeeper.setCellFactory((col) -> new TableCell<>() {
            @Override
            public void updateItem(Number storekeeperId, boolean empty) {
                super.updateItem(storekeeperId, empty);
                if (empty || storekeeperId == null) {
                    setText(null);
                } else {
                    EmployeesDAO employeesDAO = daoFactory.getEmployeesDAO();
                    Optional.of(storekeeperId.longValue()).map(employeesDAO::get).ifPresent((employee) -> setText(employee.getSurname() + " " + employee.getName() + " " + employee.getPatronymic()));
                }
            }
        });
        optionsMap.put("Наименование", new SimpleBooleanProperty(false));
        optionsMap.put("Кладовщик", new SimpleBooleanProperty(false));
        optionsMap.put("Адрес", new SimpleBooleanProperty(false));
        optionsMap.put("Эл. почта", new SimpleBooleanProperty(false));
        lvOptions.getItems().addAll(optionsMap.keySet());
        Callback<String, ObservableValue<Boolean>> itemToBoolean = optionsMap::get;
        lvOptions.setCellFactory(CheckBoxListCell.forListView(itemToBoolean));
        textColumnNameMap.put("Наименование", NAME);
        textColumnNameMap.put("Кладовщик", STOREKEEPER);
        textColumnNameMap.put("Адрес", PHYSICAL_ADDRESS);
        textColumnNameMap.put("Эл. почта", EMAIL);
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
        WarehousesDAO warehousesDAO = daoFactory.getWarehousesDAO();
        List<Warehouses> warehouses = warehousesDAO.find(searchString, columnNames);
        tvWarehouses.getItems().setAll(warehouses);
    }
}
