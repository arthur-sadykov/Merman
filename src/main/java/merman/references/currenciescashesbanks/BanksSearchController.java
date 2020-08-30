package merman.references.currenciescashesbanks;

import merman.util.dao.DAOFactory;
import merman.util.dao.interfaces.BanksDAO;
import merman.util.model.Banks;
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
public class BanksSearchController implements Initializable {
    private static final String NAME = "name";
    private static final String BIK = "BIK";
    private static final String CORRESPONDENT_ACCOUNT = "correspondentAccount";
    private static final String CITY = "city";
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
    private TableView<Banks> tvBanks;
    @FXML
    private TableColumn<Banks, String> tcName;
    @FXML
    private TableColumn<Banks, String> tcBIK;
    @FXML
    private TableColumn<Banks, String> tcCorrespondentAccount;
    @FXML
    private TableColumn<Banks, String> tcCity;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        optionsMap.put("Наименование", new SimpleBooleanProperty(false));
        optionsMap.put("БИК", new SimpleBooleanProperty(false));
        optionsMap.put("Корреспондентский счет", new SimpleBooleanProperty(false));
        optionsMap.put("Местонахождение", new SimpleBooleanProperty(false));
        lvOptions.getItems().addAll(optionsMap.keySet());
        Callback<String, ObservableValue<Boolean>> itemToBoolean = optionsMap::get;
        lvOptions.setCellFactory(CheckBoxListCell.forListView(itemToBoolean));
        textColumnNameMap.put("Наименование", NAME);
        textColumnNameMap.put("БИК", BIK);
        textColumnNameMap.put("Корреспондентский счет", CORRESPONDENT_ACCOUNT);
        textColumnNameMap.put("Местонахождение", CITY);
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
        BanksDAO banksDAO = daoFactory.getBanksDAO();
        List<Banks> banks = banksDAO.find(searchString, columnNames);
        tvBanks.getItems().setAll(banks);
    }
}
