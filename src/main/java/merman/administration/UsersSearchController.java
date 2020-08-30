package merman.administration;

import merman.util.dao.DAOFactory;
import merman.util.dao.interfaces.UsersDAO;
import merman.util.model.Users;
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
public class UsersSearchController implements Initializable {

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String PASSWORD = "password";
    private static final String SURNAME_NAME_PATRONYMIC = "surnameNamePatronymic";
    private static final String RIGHTS = "rights";
    private static final String SMTP_ADDRESS = "smtpAddress";
    private static final String SMTP_USER = "smtpUser";
    private static final String SMTP_PASSWORD = "smtpPassword";
    private static final String SMTP_HOST = "smtpHost";
    private static final String SMTP_PORT = "smtpPort";
    private static final String SMTP_AUTHENTICATION_REQUIRED = "smtpAuthenticationRequired";
    private final Map<String, ObservableValue<Boolean>> optionsMap = new LinkedHashMap<>();
    private final Map<String, String> textColumnNameMap = new LinkedHashMap<>();
    @FXML
    private Button btnSearch;
    @FXML
    private ListView<String> lvOptions;
    @FXML
    private TableColumn<Users, StringProperty> tcName;
    @FXML
    private TableColumn<Users, StringProperty> tcShortName;
    @FXML
    private TextField tfSearch;
    @FXML
    private TableView<Users> tvUsers;
    private DAOFactory daoFactory;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        optionsMap.put("Имя пользователя", new SimpleBooleanProperty(false));
        optionsMap.put("Ф.И.О", new SimpleBooleanProperty(false));
        optionsMap.put("Набор прав", new SimpleBooleanProperty(false));
        optionsMap.put("Код", new SimpleBooleanProperty(false));
        lvOptions.getItems().addAll(optionsMap.keySet());
        Callback<String, ObservableValue<Boolean>> itemToBoolean = optionsMap::get;
        lvOptions.setCellFactory(CheckBoxListCell.forListView(itemToBoolean));
        textColumnNameMap.put("Имя пользователя", NAME);
        textColumnNameMap.put("Ф.И.О", SURNAME_NAME_PATRONYMIC);
        textColumnNameMap.put("Набор прав", RIGHTS);
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
        UsersDAO usersDAO = daoFactory.getUsersDAO();
        List<Users> users = usersDAO.find(searchString, columnNames);
        tvUsers.getItems().setAll(users);
    }
}
