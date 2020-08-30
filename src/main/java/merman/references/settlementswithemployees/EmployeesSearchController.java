package merman.references.settlementswithemployees;

import merman.util.dao.DAOFactory;
import merman.util.dao.interfaces.EmployeesDAO;
import merman.util.model.Employees;
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
public class EmployeesSearchController implements Initializable {

    private static final String ID = "id";
    private static final String SURNAME = "surname";
    private static final String NAME = "name";
    private static final String PATRONYMIC = "patronymic";
    private static final String POSITION = "position";
    private static final String PHONE = "phone";
    private static final String ADDITIONAL_INFORMATION = "additionalInformation";
    private static final String PASSPORT = "passport";
    private static final String BIRTH_DATE = "birthDate";
    private static final String HIRE_DATE = "hireDate";
    private static final String DISMISSED = "dismissed";
    private static final String DRIVER_LICENSE = "driverLicense";
    private static final String TRUSTED_PERSON = "trustedPerson";
    private static final String NOT_SHOW_IN_STATEMENTS = "notShowInStatements";
    private final Map<String, ObservableValue<Boolean>> optionsMap = new LinkedHashMap<>();
    private final Map<String, String> textColumnNameMap = new LinkedHashMap<>();
    @FXML
    private Button btnSearch;
    @FXML
    private ListView<String> lvOptions;
    @FXML
    private TableColumn<Employees, StringProperty> tcName;
    @FXML
    private TableColumn<Employees, StringProperty> tcPatronymic;
    @FXML
    private TableColumn<Employees, StringProperty> tcPosition;
    @FXML
    private TableColumn<Employees, StringProperty> tcSurname;
    @FXML
    private TextField tfSearch;
    @FXML
    private TableView<Employees> tvEmployees;
    private DAOFactory daoFactory;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        optionsMap.put("Фамилия", new SimpleBooleanProperty(false));
        optionsMap.put("Имя", new SimpleBooleanProperty(false));
        optionsMap.put("Отчество", new SimpleBooleanProperty(false));
        optionsMap.put("Должность", new SimpleBooleanProperty(false));
        optionsMap.put("Телефоны", new SimpleBooleanProperty(false));
        optionsMap.put("Документ, удостоверяющий личность", new SimpleBooleanProperty(false));
        optionsMap.put("Дата рождения", new SimpleBooleanProperty(false));
        optionsMap.put("Дата приема на работу", new SimpleBooleanProperty(false));
        optionsMap.put("Водительское удостоверение", new SimpleBooleanProperty(false));
        optionsMap.put("Доверенное лицо", new SimpleBooleanProperty(false));
        optionsMap.put("Дополнительная информация", new SimpleBooleanProperty(false));
        optionsMap.put("Код", new SimpleBooleanProperty(false));
        lvOptions.getItems().addAll(optionsMap.keySet());
        Callback<String, ObservableValue<Boolean>> itemToBoolean = optionsMap::get;
        lvOptions.setCellFactory(CheckBoxListCell.forListView(itemToBoolean));
        textColumnNameMap.put("Фамилия", SURNAME);
        textColumnNameMap.put("Имя", NAME);
        textColumnNameMap.put("Отчество", PATRONYMIC);
        textColumnNameMap.put("Должность", POSITION);
        textColumnNameMap.put("Телефоны", PHONE);
        textColumnNameMap.put("Документ, удостоверяющий личность", PASSPORT);
        textColumnNameMap.put("Дата рождения", BIRTH_DATE);
        textColumnNameMap.put("Дата приема на работу", HIRE_DATE);
        textColumnNameMap.put("Водительское удостоверение", DRIVER_LICENSE);
        textColumnNameMap.put("Доверенное лицо", TRUSTED_PERSON);
        textColumnNameMap.put("Дополнительная информация", ADDITIONAL_INFORMATION);
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
        EmployeesDAO employeesDAO = daoFactory.getEmployeesDAO();
        List<Employees> employees = employeesDAO.find(searchString, columnNames);
        tvEmployees.getItems().setAll(employees);
    }
}
