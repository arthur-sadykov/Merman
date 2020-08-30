package merman.administration;

import merman.util.dao.DAOFactory;
import merman.util.dao.interfaces.RightsDAO;
import merman.util.dao.interfaces.UsersDAO;
import merman.util.model.Rights;
import merman.util.model.Users;
import javafx.beans.Observable;
import javafx.beans.property.LongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author Arthur Sadykov
 */
public class UsersController implements Initializable {

    private static final String PATH_TO_USERS_SEARCH = "/merman/administration/users-search.fxml";
    @FXML
    private Button btnSetPassword;
    private DAOFactory daoFactory;
    private TabPane tpContentPane;
    private UsersDAO usersDAO;

    @FXML
    private ButtonBar bbSaveCancel;
    @FXML
    private Button btnAddUser;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnRemoveUser;

    @FXML
    private Button btnSave;
    @FXML
    private Button btnSearchUser;
    @FXML
    private Button btnUpdateUser;
    @FXML
    private ComboBox<LongProperty> cbRights;
    @FXML
    private CheckBox chbSmtpAuthenticationRequired;
    @FXML
    private GridPane gpBasic;
    @FXML
    private GridPane gpEmail;
    @FXML
    private Label lbId;


    @FXML
    private Tab tbBasic;
    @FXML
    private Tab tbEmail;
    @FXML
    private TableColumn<Users, String> tcName;
    @FXML
    private TableColumn<Users, String> tcSmtpAddress;
    @FXML
    private TableColumn<Users, String> tcSurnameNamePatronymic;
    @FXML
    private TableColumn<Users, Number> tcRights;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfSmtpAddress;
    @FXML
    private TextField tfSmtpHost;
    @FXML
    private TextField tfSmtpPassword;
    @FXML
    private TextField tfSmtpPort;
    @FXML
    private TextField tfSmtpUser;
    @FXML
    private TextField tfSurnameNamePatronymic;
    @FXML
    private TabPane tpUserTabPane;
    @FXML
    private TableView<Users> tvUsers;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<Users> list = FXCollections.observableArrayList((users) -> new Observable[]{users.idProperty(), users.nameProperty(), users.passwordProperty(), users.surnameNamePatronymicProperty(), users.rightsProperty(), users.smtpAddressProperty(), users.smtpUserProperty(),
                                                                                                   users.smtpPasswordProperty(), users.smtpHostProperty(), users.smtpPortProperty(), users.smtpAuthenticationRequiredProperty()});
        list.addListener((ListChangeListener.Change<? extends Users> change) -> {
            while (change.next()) {
                if (change.wasRemoved()) {
                    if (list.isEmpty()) {
                        btnUpdateUser.setDisable(true);
                        btnRemoveUser.setDisable(true);
                        btnSearchUser.setDisable(true);
                    }
                }
                if (change.wasAdded()) {
                    btnUpdateUser.setDisable(false);
                    btnRemoveUser.setDisable(false);
                    btnSearchUser.setDisable(false);
                }
            }
        });
        tvUsers.setItems(list);
        tvUsers.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> Optional.ofNullable(tvUsers.getSelectionModel().getSelectedItem()).ifPresent(this::fillControls));
        StringConverter<LongProperty> rightsStringConverter = new StringConverter<>() {
            @Override
            public LongProperty fromString(String string) {
                return null;
            }

            @Override
            public String toString(LongProperty t) {
                RightsDAO rightsDAO = daoFactory.getRightsDAO();
                return Optional.ofNullable(rightsDAO.get(t.longValue())).map(Rights::getName).get();
            }
        };
        cbRights.setConverter(rightsStringConverter);
        tcRights.setCellFactory((col) -> new TableCell<>() {
            @Override
            public void updateItem(Number rightId, boolean empty) {
                super.updateItem(rightId, empty);
                if (empty) {
                    setText(null);
                } else {
                    RightsDAO rightsDAO = daoFactory.getRightsDAO();
                    Rights right = rightsDAO.get(rightId.longValue());
                    setText(right.getName());
                }
            }
        });
    }

    public void setupAfterInitialize(TabPane tpContentPane) {
        usersDAO = daoFactory.getUsersDAO();
        this.tpContentPane = tpContentPane;
        tvUsers.getItems().addAll(usersDAO.list());
        if (!tvUsers.getItems().isEmpty()) {
            btnUpdateUser.setDisable(false);
            btnRemoveUser.setDisable(false);
            btnSearchUser.setDisable(false);
            tvUsers.getSelectionModel().selectFirst();
        } else {
            btnUpdateUser.setDisable(true);
            btnRemoveUser.setDisable(true);
            btnSearchUser.setDisable(true);
        }
        RightsDAO rightsDAO = daoFactory.getRightsDAO();
        rightsDAO.list().forEach((right) -> cbRights.getItems().add(right.idProperty()));
    }

    public void setDAOFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @FXML
    private void handleBtnAddUserPress(ActionEvent event) {
        bbSaveCancel.setDisable(false);
        gpBasic.setDisable(false);
        btnAddUser.setDisable(true);
        btnUpdateUser.setDisable(true);
        btnRemoveUser.setDisable(true);
        btnSearchUser.setDisable(true);
        clearControls();
    }

    @FXML
    private void handleBtnCancelPress(ActionEvent event) {
        btnAddUser.setDisable(false);
        if (!tvUsers.getItems().isEmpty()) {
            btnUpdateUser.setDisable(false);
            btnRemoveUser.setDisable(false);
            btnSearchUser.setDisable(false);
            tvUsers.getSelectionModel().selectFirst();
        } else {
            btnUpdateUser.setDisable(true);
            btnRemoveUser.setDisable(true);
            btnSearchUser.setDisable(true);
        }
        bbSaveCancel.setDisable(true);
        gpBasic.setDisable(true);
    }

    @FXML
    private void handleBtnRemoveUserPress(ActionEvent event) {
        Optional.ofNullable(tvUsers.getSelectionModel().getSelectedItem()).ifPresent((user) -> {
            int result = usersDAO.delete(user);
            if (result > 0) {
                tvUsers.getItems().remove(user);
            }
            clearControls();
        });
    }

    @FXML
    private void handleBtnSavePress(ActionEvent event) {
        Long id = lbId.getText() == null || lbId.getText().matches("") ? null : Long.parseLong(lbId.getText());
        String name = tfName.getText();
        String surnameNamePatronymic = tfSurnameNamePatronymic.getText();
        Long rights = cbRights.getValue() == null ? null : cbRights.getValue().get();
        String smtpAddress = tfSmtpAddress.getText();
        String smtpUser = tfSmtpUser.getText();
        String smtpPassword = tfSmtpPassword.getText();
        String smtpHost = tfSmtpHost.getText();
        String smtpPort = tfSmtpPort.getText();
        Boolean smtpAuthenticationRequired = chbSmtpAuthenticationRequired.isSelected();
        if (id == null) {
            Users user = new Users(id, name, smtpPassword, surnameNamePatronymic, rights, smtpAddress, smtpUser, smtpPassword, smtpHost, smtpPort, smtpAuthenticationRequired);
            int result = usersDAO.add(user);
            if (result > 0) {
                tvUsers.getItems().add(user);
            }
        } else {
            Users user = new Users(id, name, smtpPassword, surnameNamePatronymic, rights, smtpAddress, smtpUser, smtpPassword, smtpHost, smtpPort, smtpAuthenticationRequired);
            int result = usersDAO.update(user);
            if (result > 0) {
                tvUsers.getItems().set(tvUsers.getSelectionModel().getSelectedIndex(), user);
            }
        }
        btnAddUser.setDisable(false);
        btnUpdateUser.setDisable(false);
        btnRemoveUser.setDisable(false);
        btnSearchUser.setDisable(false);
        gpBasic.setDisable(true);
        gpEmail.setDisable(true);
        bbSaveCancel.setDisable(true);
    }

    @FXML
    private void handleBtnSearchUserPress() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PATH_TO_USERS_SEARCH));
        AnchorPane apSearchPane = fxmlLoader.load();
        UsersSearchController usersSearchController = fxmlLoader.getController();
        usersSearchController.setDAOFactory(daoFactory);
        Tab tab = new Tab("Поиск в таблице \"Единицы измерения\"", apSearchPane);
        ContextMenu contextMenu = new ContextMenu();
        MenuItem miClose = new MenuItem("Закрыть вкладку");
        MenuItem miCloseAll = new MenuItem("Закрыть все вкладки");
        MenuItem miCloseOthers = new MenuItem("Закрыть другие вкладки");
        MenuItem miCloseLeftTabs = new MenuItem("Закрыть вкладки слева");
        MenuItem miCloseRightTabs = new MenuItem("Закрыть вкладки справа");
        miClose.setOnAction(event -> tpContentPane.getTabs().remove(tab));
        miCloseAll.setOnAction(event -> tpContentPane.getTabs().clear());
        miCloseOthers.setOnAction(event -> {
            List<Tab> tabs = new ArrayList<>();
            tpContentPane.getTabs().forEach(t -> {
                if (t != tab) {
                    tabs.add(t);
                }
            });
            tpContentPane.getTabs().removeAll(tabs);
        });
        miCloseLeftTabs.setOnAction(event -> {
            List<Tab> tabs = new ArrayList<>();
            for (Tab t : tpContentPane.getTabs()) {
                if (t != tab) {
                    tabs.add(t);
                } else {
                    break;
                }
            }
            tpContentPane.getTabs().removeAll(tabs);
        });
        miCloseRightTabs.setOnAction(event -> {
            List<Tab> tabs = new ArrayList<>();
            for (int i = tpContentPane.getTabs().size() - 1; i >= 0; i--) {
                if (tpContentPane.getTabs().get(i) != tab) {
                    tabs.add(tpContentPane.getTabs().get(i));
                } else {
                    break;
                }
            }
            tpContentPane.getTabs().removeAll(tabs);
        });
        contextMenu.getItems().addAll(miClose, miCloseAll, miCloseOthers, miCloseLeftTabs, miCloseRightTabs);
        tab.setContextMenu(contextMenu);
        tpContentPane.getTabs().add(tab);
        tpContentPane.getSelectionModel().select(tab);
    }

    @FXML
    private void handleBtnSetPasswordPress(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        String userName = tvUsers.getSelectionModel().getSelectedItem().getName();
        dialog.setTitle("Введите пароль для пользователя " + userName);
        dialog.setContentText("Пожалуйста, введите пароль:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(password -> usersDAO.setPassword(userName, password));
    }

    @FXML
    private void handleBtnUpdateUserPress(ActionEvent event) {
        Optional.ofNullable(tvUsers.getSelectionModel().getSelectedItem()).ifPresent((user) -> {
            bbSaveCancel.setDisable(false);
            gpBasic.setDisable(false);
            btnAddUser.setDisable(true);
            btnUpdateUser.setDisable(true);
            btnRemoveUser.setDisable(true);
            btnSearchUser.setDisable(true);
            fillControls(user);
        });
    }


    private void fillControls(Users user) {
        lbId.setText(user.getId() == null ? "" : user.getId().toString());
        tfName.setText(user.getName());
        tfSurnameNamePatronymic.setText(user.getSurnameNamePatronymic());
        cbRights.setValue(user.rightsProperty());
        tfSmtpAddress.setText(user.getSmtpAddress());
        tfSmtpUser.setText(user.getSmtpUser());
        tfSmtpPassword.setText(user.getSmtpPassword());
        tfSmtpHost.setText(user.getSmtpHost());
        tfSmtpPort.setText(user.getSmtpPort());
        chbSmtpAuthenticationRequired.setSelected(user.getSmtpAuthenticationRequired());
    }

    private void clearControls() {
        lbId.setText("");
        tfName.setText("");
        tfSurnameNamePatronymic.setText("");
        cbRights.getSelectionModel().selectFirst();
        tfSmtpAddress.setText("");
        tfSmtpUser.setText("");
        tfSmtpPassword.setText("");
        tfSmtpHost.setText("");
        tfSmtpPort.setText("");
        chbSmtpAuthenticationRequired.setSelected(false);
    }
}
