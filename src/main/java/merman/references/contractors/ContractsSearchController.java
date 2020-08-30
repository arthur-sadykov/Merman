package merman.references.contractors;

import merman.util.dao.DAOFactory;
import merman.util.dao.interfaces.ContractorsContractsDAO;
import merman.util.model.ContractorTypes;
import merman.util.model.ContractorsContracts;
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
public class ContractsSearchController implements Initializable {

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String CREDIT_DAYS = "creditDays";
    private static final String CREDIT_AMOUNT = "creditAmount";
    private static final String DATE_OF_CONTRACT = "dateOfContract";
    private static final String DATE_OF_TERMINATION = "dateOfTermination";
    private static final String BECAUSE_OF = "becauseOf";
    private static final String DISPATCHER = "dispatcher";
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
    private DAOFactory daoFactory;
    @FXML
    private TableView<ContractorsContracts> tvContractors;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        optionsMap.put("Код", new SimpleBooleanProperty(false));
        optionsMap.put("", new SimpleBooleanProperty(false));
        optionsMap.put("Наименование", new SimpleBooleanProperty(false));
        optionsMap.put("Число дней кредита", new SimpleBooleanProperty(false));
        optionsMap.put("Сумма кредита", new SimpleBooleanProperty(false));
        optionsMap.put("Заключён", new SimpleBooleanProperty(false));
        optionsMap.put("Расторгнут", new SimpleBooleanProperty(false));
        optionsMap.put("По причине", new SimpleBooleanProperty(false));
        optionsMap.put("Диспетчер", new SimpleBooleanProperty(false));
        lvOptions.getItems().addAll(optionsMap.keySet());
        Callback<String, ObservableValue<Boolean>> itemToBoolean = optionsMap::get;
        lvOptions.setCellFactory(CheckBoxListCell.forListView(itemToBoolean));
        textColumnNameMap.put("Код", ID);
        textColumnNameMap.put("Наименование", NAME);
        textColumnNameMap.put("Число дней кредита", CREDIT_DAYS);
        textColumnNameMap.put("Сумма кредита", CREDIT_AMOUNT);
        textColumnNameMap.put("Заключён", DATE_OF_CONTRACT);
        textColumnNameMap.put("Расторгнут", DATE_OF_TERMINATION);
        textColumnNameMap.put("По причине", BECAUSE_OF);
        textColumnNameMap.put("Диспетчер", DISPATCHER);
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
        ContractorsContractsDAO contractorsContractsDAO = daoFactory.getContractorsContractsDAO();
        List<ContractorsContracts> contracts = contractorsContractsDAO.find(searchString, columnNames);
        tvContractors.getItems().setAll(contracts);
    }
}
