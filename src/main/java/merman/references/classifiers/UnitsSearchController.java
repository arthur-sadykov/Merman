package merman.references.classifiers;

import merman.util.dao.DAOFactory;
import merman.util.dao.interfaces.UnitsDAO;
import merman.util.model.Units;
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
public class UnitsSearchController implements Initializable {

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String UNIT_CODE = "unitCode";
    private static final String FULL_NAME = "fullName";
    private final Map<String, ObservableValue<Boolean>> optionsMap = new LinkedHashMap<>();
    private final Map<String, String> textColumnNameMap = new LinkedHashMap<>();
    @FXML
    private Button btnSearch;
    @FXML
    private ListView<String> lvOptions;
    @FXML
    private TableColumn<Units, StringProperty> tcName;
    @FXML
    private TableColumn<Units, StringProperty> tcUnitCode;
    @FXML
    private TextField tfSearch;
    @FXML
    private TableView<Units> tvUnits;
    private DAOFactory daoFactory;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        optionsMap.put("Наименование", new SimpleBooleanProperty(false));
        optionsMap.put("Полное наименование", new SimpleBooleanProperty(false));
        optionsMap.put("Код единицы по ОКЕИ", new SimpleBooleanProperty(false));
        optionsMap.put("Код", new SimpleBooleanProperty(false));
        lvOptions.getItems().addAll(optionsMap.keySet());
        Callback<String, ObservableValue<Boolean>> itemToBoolean = optionsMap::get;
        lvOptions.setCellFactory(CheckBoxListCell.forListView(itemToBoolean));
        textColumnNameMap.put("Наименование", NAME);
        textColumnNameMap.put("Полное наименование", FULL_NAME);
        textColumnNameMap.put("Код единицы по ОКЕИ", UNIT_CODE);
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
        UnitsDAO unitsDAO = daoFactory.getUnitsDAO();
        List<Units> units = unitsDAO.find(searchString, columnNames);
        tvUnits.getItems().setAll(units);
    }
}
