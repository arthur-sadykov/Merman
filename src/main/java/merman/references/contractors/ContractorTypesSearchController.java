package merman.references.contractors;

import merman.util.dao.DAOFactory;
import merman.util.dao.interfaces.ContractorTypesDAO;
import merman.util.model.ContractorTypes;
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
public class ContractorTypesSearchController implements Initializable {

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String IS_SUPPLIER = "isSupplier";
    private static final String IS_CUSTOMER = "isCustomer";
    private final Map<String, ObservableValue<Boolean>> optionsMap = new LinkedHashMap<>();
    private final Map<String, String> textColumnNameMap = new LinkedHashMap<>();
    @FXML
    private Button btnSearch;
    @FXML
    private ListView<String> lvOptions;
    @FXML
    private TableColumn<ContractorTypes, StringProperty> tcName;
    @FXML
    private TextField tfSearch;
    @FXML
    private TableView<ContractorTypes> tvContractorTypes;
    private DAOFactory daoFactory;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        optionsMap.put("Наименование", new SimpleBooleanProperty(false));
        optionsMap.put("Код", new SimpleBooleanProperty(false));
        lvOptions.getItems().addAll(optionsMap.keySet());
        Callback<String, ObservableValue<Boolean>> itemToBoolean = optionsMap::get;
        lvOptions.setCellFactory(CheckBoxListCell.forListView(itemToBoolean));
        textColumnNameMap.put("Наименование", NAME);
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
        ContractorTypesDAO contractorTypesDAO = daoFactory.getContractorTypesDAO();
        List<ContractorTypes> positions = contractorTypesDAO.find(searchString, columnNames);
        tvContractorTypes.getItems().setAll(positions);
    }
}
