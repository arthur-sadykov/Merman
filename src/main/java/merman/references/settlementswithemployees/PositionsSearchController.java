package merman.references.settlementswithemployees;

import merman.util.dao.DAOFactory;
import merman.util.dao.interfaces.PositionsDAO;
import merman.util.model.Positions;
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
public class PositionsSearchController implements Initializable {

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String MINIMUM_PAYMENT_PER_DAY = "minimumPaymentPerDay";
    private final Map<String, ObservableValue<Boolean>> optionsMap = new LinkedHashMap<>();
    private final Map<String, String> textColumnNameMap = new LinkedHashMap<>();
    @FXML
    private Button btnSearch;
    @FXML
    private ListView<String> lvOptions;
    @FXML
    private TableColumn<Positions, StringProperty> tcName;
    @FXML
    private TextField tfSearch;
    @FXML
    private TableView<Positions> tvPositions;
    private DAOFactory daoFactory;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        optionsMap.put("Наименование", new SimpleBooleanProperty(false));
        optionsMap.put("Минимальная оплата в день", new SimpleBooleanProperty(false));
        optionsMap.put("Код", new SimpleBooleanProperty(false));
        lvOptions.getItems().addAll(optionsMap.keySet());
        Callback<String, ObservableValue<Boolean>> itemToBoolean = optionsMap::get;
        lvOptions.setCellFactory(CheckBoxListCell.forListView(itemToBoolean));
        textColumnNameMap.put("Наименование", NAME);
        textColumnNameMap.put("Минимальная оплата в день", MINIMUM_PAYMENT_PER_DAY);
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
        PositionsDAO positionsDAO = daoFactory.getPositionsDAO();
        List<Positions> positions = positionsDAO.find(searchString, columnNames);
        tvPositions.getItems().setAll(positions);
    }
}
