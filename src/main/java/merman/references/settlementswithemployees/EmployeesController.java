package merman.references.settlementswithemployees;

import merman.util.dao.DAOFactory;
import merman.util.dao.interfaces.EmployeesDAO;
import merman.util.dao.interfaces.PositionsDAO;
import merman.util.model.Employees;
import merman.util.model.Positions;
import javafx.beans.Observable;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.StringProperty;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author Arthur Sadykov
 */
public class EmployeesController implements Initializable {

    private static final String PATH_TO_EMPLOYEES_SEARCH = "/merman/references/settlementswithemployees/employees-search.fxml";
    @FXML
    private ButtonBar bbSaveCancel;
    @FXML
    private Button btnAddEmployee;
    @FXML
    private Button btnRemoveEmployee;

    @FXML
    private Button btnSearchEmployee;
    @FXML
    private Button btnUpdateEmployee;
    @FXML
    private ComboBox<LongProperty> cbPosition;
    @FXML
    private CheckBox chbDismissed;
    @FXML
    private CheckBox chbNotShowInStatements;
    private DAOFactory daoFactory;
    @FXML
    private DatePicker dpBirthDate;
    @FXML
    private DatePicker dpHireDate;
    private EmployeesDAO employeesDAO;
    @FXML
    private GridPane gpBasic;
    @FXML
    private MenuItem handleMiCardPrintPress;
    @FXML
    private MenuItem handleMiRegistryPrintPress;
    @FXML
    private Label lbId;
    @FXML
    private TextArea taAdditionalInformation;
    @FXML
    private TableColumn<Employees, StringProperty> tcName;
    @FXML
    private TableColumn<Employees, StringProperty> tcPatronymic;
    @FXML
    private TableColumn<Employees, String> tcPosition;
    @FXML
    private TableColumn<Employees, StringProperty> tcSurname;
    @FXML
    private TextField tfDriverLicense;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfPassport;
    @FXML
    private TextField tfPatronymic;
    @FXML
    private TextField tfPhones;
    @FXML
    private TextField tfSurname;
    @FXML
    private TextField tfTrustedPerson;
    private TabPane tpContentPane;
    @FXML
    private TableView<Employees> tvEmployees;

    public void setDAOFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<Employees> list = FXCollections.observableArrayList((employees) -> new Observable[]{employees.idProperty(), employees.surnameProperty(), employees.nameProperty(), employees.patronymicProperty(), employees.positionProperty(), employees.phoneProperty(),
                                                                                                           employees.additionalInformationProperty(), employees.passportProperty(), employees.birthDateProperty(), employees.hireDateProperty(), employees.dismissedProperty(),
                                                                                                           employees.driverLicenseProperty(), employees.trustedPersonProperty(), employees.notShowInStatementsProperty()});
        list.addListener((ListChangeListener.Change<? extends Employees> change) -> {
            while (change.next()) {
                if (change.wasRemoved()) {
                    if (list.isEmpty()) {
                        btnUpdateEmployee.setDisable(true);
                        btnRemoveEmployee.setDisable(true);
                        btnSearchEmployee.setDisable(true);
                    }
                }
                if (change.wasAdded()) {
                    btnUpdateEmployee.setDisable(false);
                    btnRemoveEmployee.setDisable(false);
                    btnSearchEmployee.setDisable(false);
                }
            }
        });
        tvEmployees.setItems(list);
        tvEmployees.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> Optional.ofNullable(tvEmployees.getSelectionModel().getSelectedItem()).ifPresent(this::fillControls));
        StringConverter<LongProperty> positionStringConverter = new StringConverter<>() {
            @Override
            public LongProperty fromString(String string) {
                return null;
            }

            @Override
            public String toString(LongProperty t) {
                PositionsDAO positionsDAO = daoFactory.getPositionsDAO();
                return Optional.ofNullable(positionsDAO.get(t.longValue())).map(Positions::getName).orElse(null);
            }
        };
        cbPosition.setConverter(positionStringConverter);
        tcPosition.setCellValueFactory((p) -> {
            PositionsDAO positionsDAO = daoFactory.getPositionsDAO();
            return new ReadOnlyObjectWrapper<>(Optional.ofNullable(p.getValue().getPosition()).map(positionsDAO::get).map(Positions::getName).orElse(null));
        });
    }

    public void setupAfterInitialize(TabPane tpContentPane) {
        employeesDAO = daoFactory.getEmployeesDAO();
        this.tpContentPane = tpContentPane;
        tvEmployees.getItems().addAll(employeesDAO.list());
        if (!tvEmployees.getItems().isEmpty()) {
            btnUpdateEmployee.setDisable(false);
            btnRemoveEmployee.setDisable(false);
            btnSearchEmployee.setDisable(false);
            tvEmployees.getSelectionModel().selectFirst();
        } else {
            btnUpdateEmployee.setDisable(true);
            btnRemoveEmployee.setDisable(true);
            btnSearchEmployee.setDisable(true);
        }
        PositionsDAO positionsDAO = daoFactory.getPositionsDAO();
        positionsDAO.list().forEach((position) -> cbPosition.getItems().add(position.idProperty()));
    }

    @FXML
    private void handleBtnAddEmployeePress(ActionEvent event) {
        bbSaveCancel.setDisable(false);
        gpBasic.setDisable(false);
        btnAddEmployee.setDisable(true);
        btnUpdateEmployee.setDisable(true);
        btnRemoveEmployee.setDisable(true);
        btnSearchEmployee.setDisable(true);
        clearControls();
    }

    @FXML
    private void handleBtnCancelPress(ActionEvent event) {
        btnAddEmployee.setDisable(false);
        if (!tvEmployees.getItems().isEmpty()) {
            btnUpdateEmployee.setDisable(false);
            btnRemoveEmployee.setDisable(false);
            btnSearchEmployee.setDisable(false);
            tvEmployees.getSelectionModel().selectFirst();
        } else {
            btnUpdateEmployee.setDisable(true);
            btnRemoveEmployee.setDisable(true);
            btnSearchEmployee.setDisable(true);
        }
        bbSaveCancel.setDisable(true);
        gpBasic.setDisable(true);
    }

    @FXML
    private void handleBtnRemoveEmployeePress(ActionEvent event) {
        Optional.ofNullable(tvEmployees.getSelectionModel().getSelectedItem()).ifPresent((employee) -> {
            int result = employeesDAO.delete(employee);
            if (result > 0) {
                tvEmployees.getItems().remove(employee);
            }
            clearControls();
        });
    }

    @FXML
    private void handleBtnSearchEmployeePress() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PATH_TO_EMPLOYEES_SEARCH));
        AnchorPane apSearchPane = fxmlLoader.load();
        EmployeesSearchController employeesSearchController = fxmlLoader.getController();
        employeesSearchController.setDAOFactory(daoFactory);
        Tab tab = new Tab("Поиск в таблице \"Сотрудники\"", apSearchPane);
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
    private void handleBtnUpdateEmployeePress(ActionEvent event) {
        Optional.ofNullable(tvEmployees.getSelectionModel().getSelectedItem()).ifPresent((employee) -> {
            bbSaveCancel.setDisable(false);
            gpBasic.setDisable(false);
            btnAddEmployee.setDisable(true);
            btnUpdateEmployee.setDisable(true);
            btnRemoveEmployee.setDisable(true);
            btnSearchEmployee.setDisable(true);
            fillControls(employee);
        });
    }

    @FXML
    private void handleBtnSavePress(ActionEvent event) {
        Long id = lbId.getText() == null || lbId.getText().matches("") ? null : Long.parseLong(lbId.getText());
        String surname = tfSurname.getText();
        String name = tfName.getText();
        String patronymic = tfPatronymic.getText();
        Long position = cbPosition.getValue() == null ? null : cbPosition.getValue().get();
        String phone = tfPhones.getText();
        String additionalInformation = taAdditionalInformation.getText();
        String passport = tfPassport.getText();
        LocalDateTime birthDate = dpBirthDate.getValue() == null ? null : dpBirthDate.getValue().atStartOfDay();
        LocalDateTime hireDate = dpHireDate.getValue() == null ? null : dpHireDate.getValue().atStartOfDay();
        Boolean dismissed = chbDismissed.isSelected();
        String driverLicense = tfDriverLicense.getText();
        String trustedPerson = tfTrustedPerson.getText();
        Boolean notShowInStatements = chbNotShowInStatements.isSelected();
        if (id == null) {
            Employees employee = new Employees(id, surname, name, patronymic, position, phone, additionalInformation, passport, birthDate, hireDate, dismissed, driverLicense, trustedPerson, notShowInStatements);
            int result = employeesDAO.add(employee);
            if (result > 0) {
                tvEmployees.getItems().add(employee);
            }
        } else {
            Employees employee = new Employees(id, surname, name, patronymic, position, phone, additionalInformation, passport, birthDate, hireDate, dismissed, driverLicense, trustedPerson, notShowInStatements);
            int result = employeesDAO.update(employee);
            if (result > 0) {
                tvEmployees.getItems().set(tvEmployees.getSelectionModel().getSelectedIndex(), employee);
            }
        }
        gpBasic.setDisable(true);
        bbSaveCancel.setDisable(true);
        btnAddEmployee.setDisable(false);
        btnUpdateEmployee.setDisable(false);
        btnRemoveEmployee.setDisable(false);
        btnSearchEmployee.setDisable(false);
        gpBasic.setDisable(true);
        bbSaveCancel.setDisable(true);
    }


    private void fillControls(Employees employee) {
        lbId.setText(employee.getId() == null ? "" : employee.getId().toString());
        tfSurname.setText(employee.getSurname());
        tfName.setText(employee.getName());
        tfPatronymic.setText(employee.getPatronymic());
        cbPosition.setValue(employee.positionProperty());
        tfPhones.setText(employee.getPhone());
        taAdditionalInformation.setText(employee.getAdditionalInformation());
        tfPassport.setText(employee.getPassport());
        dpBirthDate.setValue(employee.getBirthDate() == null ? null : employee.getBirthDate().toLocalDate());
        dpHireDate.setValue(employee.getHireDate() == null ? null : employee.getHireDate().toLocalDate());
        chbDismissed.setSelected(employee.getDismissed());
        tfDriverLicense.setText(employee.getDriverLicense());
        tfTrustedPerson.setText(employee.getTrustedPerson());
        chbNotShowInStatements.setSelected(employee.getNotShowInStatements());
    }

    private void clearControls() {
        lbId.setText("");
        tfSurname.setText("");
        tfName.setText("");
        tfPatronymic.setText("");
        cbPosition.getSelectionModel().selectFirst();
        tfPhones.setText("");
        taAdditionalInformation.setText("");
        tfPassport.setText("");
        dpBirthDate.setValue(null);
        dpHireDate.setValue(null);
        chbDismissed.setSelected(false);
        tfDriverLicense.setText("");
        tfTrustedPerson.setText("");
        chbNotShowInStatements.setSelected(false);
    }
}
