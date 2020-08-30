package merman.references.flipcontainers;

import merman.util.dao.DAOFactory;
import merman.util.dao.interfaces.FlipContainersDAO;
import merman.util.model.FlipContainers;
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
public class FlipContainersSearchController implements Initializable {

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String FULL_NAME = "fullName";
    private static final String SHORT_NAME = "shortName";
    private static final String UNIT = "unit";
    private static final String VENDOR_CODE = "vendorCode";
    private static final String COMMENT = "comment";
    private static final String PRICE = "price";
    private static final String DEPOSIT_FOR_ONE_PIECE = "depositForOnePiece";
    private static final String VAT_RATE = "vatRate";
    private final Map<String, ObservableValue<Boolean>> optionsMap = new LinkedHashMap<>();
    private final Map<String, String> textColumnNameMap = new LinkedHashMap<>();
    @FXML
    private Button btnSearch;
    @FXML
    private ListView<String> lvOptions;
    @FXML
    private TableColumn<FlipContainers, StringProperty> tcFullName;
    @FXML
    private TableColumn<FlipContainers, StringProperty> tcShortName;
    @FXML
    private TextField tfSearch;
    @FXML
    private TableView<FlipContainers> tvFlipContainer;
    private DAOFactory daoFactory;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        optionsMap.put("Наименование", new SimpleBooleanProperty(false));
        optionsMap.put("Полное наименование", new SimpleBooleanProperty(false));
        optionsMap.put("Краткое наименование", new SimpleBooleanProperty(false));
        optionsMap.put("Единицы измерения", new SimpleBooleanProperty(false));
        optionsMap.put("Арктикул", new SimpleBooleanProperty(false));
        optionsMap.put("Комментарий", new SimpleBooleanProperty(false));
        optionsMap.put("Цена", new SimpleBooleanProperty(false));
        optionsMap.put("Залог за одну штуку", new SimpleBooleanProperty(false));
        optionsMap.put("Ставка НДС", new SimpleBooleanProperty(false));
        optionsMap.put("Код", new SimpleBooleanProperty(false));
        lvOptions.getItems().addAll(optionsMap.keySet());
        Callback<String, ObservableValue<Boolean>> itemToBoolean = optionsMap::get;
        lvOptions.setCellFactory(CheckBoxListCell.forListView(itemToBoolean));
        textColumnNameMap.put("Наименование", NAME);
        textColumnNameMap.put("Полное наименование", FULL_NAME);
        textColumnNameMap.put("Краткое наименование", SHORT_NAME);
        textColumnNameMap.put("Единицы измерения", UNIT);
        textColumnNameMap.put("Арктикул", VENDOR_CODE);
        textColumnNameMap.put("Комментарий", COMMENT);
        textColumnNameMap.put("Цена", PRICE);
        textColumnNameMap.put("Залог за одну штуку", DEPOSIT_FOR_ONE_PIECE);
        textColumnNameMap.put("Ставка НДС", VAT_RATE);
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
        FlipContainersDAO flipContainersDAO = daoFactory.getFlipContainersDAO();
        List<FlipContainers> contractorTypes = flipContainersDAO.find(searchString, columnNames);
        tvFlipContainer.getItems().setAll(contractorTypes);
    }
}
