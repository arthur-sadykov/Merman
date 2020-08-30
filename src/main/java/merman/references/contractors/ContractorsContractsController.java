package merman.references.contractors;

import merman.util.dao.DAOFactory;
import merman.util.dao.interfaces.ContractorsContractsDAO;
import merman.util.dao.interfaces.EmployeesDAO;
import merman.util.model.ContractorsContracts;
import javafx.beans.Observable;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

/**
 * @author Arthur Sadykov
 */
public class ContractorsContractsController implements Initializable {

    private static final String PATH_TO_CONTRACTS_SEARCH = "/merman/references/contractors/contractors-contracts-search.fxml";
    private DAOFactory daoFactory;
    @FXML
    private TextField tfCreditAmount;
    private ContractorsContractsDAO contractorsContractsDAO;
    @FXML
    private ButtonBar bbSaveCancel;
    @FXML
    private Button btnAddContract;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnRemoveContract;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnSearchContract;
    @FXML
    private Button btnUpdateContract;
    @FXML
    private ComboBox<LongProperty> cbDispatcher;
    @FXML
    private DatePicker dpDateOfContract;
    @FXML
    private DatePicker dpDateOfTermination;
    @FXML
    private GridPane gpBasic;
    @FXML
    private Label lbId;


    @FXML
    private TableColumn<ContractorsContracts, String> tcBecauseOf;
    @FXML
    private TableColumn<ContractorsContracts, Double> tcCreditAmount;
    @FXML
    private TableColumn<ContractorsContracts, Integer> tcCreditDays;
    @FXML
    private TableColumn<ContractorsContracts, LocalDate> tcDateOfContract;
    @FXML
    private TableColumn<ContractorsContracts, LocalDate> tcDateOfTermination;
    @FXML
    private TableColumn<ContractorsContracts, String> tcDispatcher;
    @FXML
    private TableColumn<ContractorsContracts, String> tcName;
    @FXML
    private TextField tfBecauseOf;
    @FXML
    private TextField tfCreditDays;
    @FXML
    private TextField tfName;
    @FXML
    private TabPane tpRightTabPane;
    @FXML
    private TableView<ContractorsContracts> tvContracts;
    private TabPane tpContentPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<ContractorsContracts> list = FXCollections.observableArrayList((contractorsContracts) -> new Observable[]{contractorsContracts.idProperty(), contractorsContracts.nameProperty(), contractorsContracts.creditDaysProperty(), contractorsContracts.creditAmountProperty(),
                                                                                                                                 contractorsContracts.dateOfContractProperty(), contractorsContracts.dateOfTerminationProperty(), contractorsContracts.becauseOfProperty(),
                                                                                                                                 contractorsContracts.dispatcherProperty()});
        tvContracts.setItems(list);
        Optional.ofNullable(tvContracts.getSelectionModel().getSelectedItem()).ifPresent(this::fillControls);
        UnaryOperator<TextFormatter.Change> integerFilter = (TextFormatter.Change change) -> {
            if (change.isReplaced()) {
                if (!change.getText().matches("\\d+")) {
                    change.setText(change.getControlText().substring(change.getRangeStart(), change.getRangeEnd()));
                }
            }
            if (change.isAdded()) {
                if (!change.getText().matches("\\d+")) {
                    change.setText("");
                }
            }
            return change;
        };
        UnaryOperator<TextFormatter.Change> doubleFilter = (TextFormatter.Change change) -> {
            if (change.isReplaced()) {
                if (change.getControlText().contains(".")) {
                    if (!change.getText().matches("\\d*?\\.?\\d*?")) {
                        change.setText(change.getControlText().substring(change.getRangeStart(), change.getRangeEnd()));
                    } else {
                        if (change.getText().contains(".")) {
                            int oldDotIndex = change.getControlText().indexOf(".");
                            int newDotIndex = change.getText().indexOf(".");
                            int rangeStart = change.getRangeStart();
                            if (rangeStart + newDotIndex != oldDotIndex) {
                                change.setText(change.getControlText().substring(change.getRangeStart(), change.getRangeEnd()));
                            }
                        }
                    }
                } else {
                    if (!change.getText().matches("\\d*?\\.?\\d*?")) {
                        change.setText(change.getControlText().substring(change.getRangeStart(), change.getRangeEnd()));
                    }
                }
            }
            if (change.isAdded()) {
                if (change.getControlText().contains(".")) {
                    if (!change.getText().matches("\\d*?\\.?\\d*?")) {
                        change.setText("");
                    } else {
                        if (change.getText().contains(".")) {
                            int oldDotIndex = change.getControlText().indexOf(".");
                            int newDotIndex = change.getText().indexOf(".");
                            int rangeStart = change.getRangeStart();
                            if (rangeStart + newDotIndex != oldDotIndex) {
                                change.setText("");
                            }
                        }
                    }
                } else {
                    if (!change.getText().matches("\\d*?\\.?\\d*?")) {
                        change.setText("");
                    }
                }
            }
            return change;
        };
        tfCreditAmount.setTextFormatter(new TextFormatter<>(new DoubleStringConverter(), null, doubleFilter));
        tfCreditDays.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), null, integerFilter));
        StringConverter<LongProperty> dispatcherStringConverter = new StringConverter<>() {
            @Override
            public LongProperty fromString(String string) {
                return null;
            }

            @Override
            public String toString(LongProperty t) {
                EmployeesDAO employeesDAO = daoFactory.getEmployeesDAO();
                return Optional.ofNullable(employeesDAO.get(t.longValue())).map((employee) -> employee.getSurname() + " " + employee.getName() + " " + employee.getPatronymic()).orElse(null);
            }
        };
        cbDispatcher.setConverter(dispatcherStringConverter);
        tcDispatcher.setCellValueFactory((contract) -> {
            EmployeesDAO employeesDAO = daoFactory.getEmployeesDAO();
            return new ReadOnlyObjectWrapper<>(Optional.ofNullable(employeesDAO.get(contract.getValue().getDispatcher())).map((employee) -> employee.getSurname() + " " + employee.getName() + " " + employee.getPatronymic()).orElse(null));
        });
    }

    public void setupAfterInitialize(TabPane tpContentPane) {
        this.tpContentPane = tpContentPane;
        contractorsContractsDAO = daoFactory.getContractorsContractsDAO();
        tvContracts.getItems().addAll(contractorsContractsDAO.list());
        EmployeesDAO employeesDAO = daoFactory.getEmployeesDAO();
        employeesDAO.list().forEach((employee) -> cbDispatcher.getItems().add(employee.idProperty()));
    }

    public void setDAOFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @FXML
    private void handleBtnAddContractPress(ActionEvent event) {
        bbSaveCancel.setDisable(false);
        gpBasic.setDisable(false);
        btnAddContract.setDisable(true);
        btnUpdateContract.setDisable(true);
        btnRemoveContract.setDisable(true);
        btnSearchContract.setDisable(true);
        clearControls();
    }

    @FXML
    private void handleBtnCancelPress(ActionEvent event) {
        btnAddContract.setDisable(false);
        if (!tvContracts.getItems().isEmpty()) {
            btnUpdateContract.setDisable(false);
            btnRemoveContract.setDisable(false);
            btnSearchContract.setDisable(false);
            tvContracts.getSelectionModel().selectFirst();
        } else {
            btnUpdateContract.setDisable(true);
            btnRemoveContract.setDisable(true);
            btnSearchContract.setDisable(true);
        }
        bbSaveCancel.setDisable(true);
        gpBasic.setDisable(true);
    }

    @FXML
    private void handleBtnRemoveContractPress(ActionEvent event) {
        Optional.ofNullable(tvContracts.getSelectionModel().getSelectedItem()).ifPresent((contract) -> {
            int result = contractorsContractsDAO.delete(contract);
            if (result > 0) {
                tvContracts.getItems().remove(contract);
            }
            clearControls();
        });
    }

    @FXML
    private void handleBtnSavePress(ActionEvent event) {
        Long id = lbId.getText() == null || lbId.getText().matches("") ? null : Long.parseLong(lbId.getText());
        String name = tfName.getText();
        Integer creditDays = tfCreditDays.getText().matches("") ? null : Integer.parseInt(tfCreditDays.getText());
        BigDecimal creditAmount = tfCreditAmount.getText() == null || tfCreditAmount.getText().matches("") ? null : new BigDecimal(tfCreditAmount.getText());
        LocalDateTime dateOfContract = dpDateOfContract.getValue() == null ? null : dpDateOfContract.getValue().atStartOfDay();
        LocalDateTime dateOfTermination = dpDateOfTermination.getValue() == null ? null : dpDateOfTermination.getValue().atStartOfDay();
        String becauseOf = tfBecauseOf.getText();
        Long dispatcher = cbDispatcher.getValue() == null ? null : cbDispatcher.getValue().get();
        if (id == null) {
            ContractorsContracts contract = new ContractorsContracts(id, name, creditDays, creditAmount, dateOfContract, dateOfTermination, becauseOf, dispatcher);
            int result = contractorsContractsDAO.add(contract);
            if (result > 0) {
                tvContracts.getItems().add(contract);
            }
        } else {
            ContractorsContracts contract = new ContractorsContracts(id, name, creditDays, creditAmount, dateOfContract, dateOfTermination, becauseOf, dispatcher);
            int result = contractorsContractsDAO.update(contract);
            if (result > 0) {
                tvContracts.getItems().set(tvContracts.getSelectionModel().getSelectedIndex(), contract);
            }
        }
        btnAddContract.setDisable(false);
        btnUpdateContract.setDisable(false);
        btnRemoveContract.setDisable(false);
        btnSearchContract.setDisable(false);
        gpBasic.setDisable(true);
        bbSaveCancel.setDisable(true);
    }

    @FXML
    private void handleBtnSearchContractPress() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PATH_TO_CONTRACTS_SEARCH));
        AnchorPane apSearchPane = fxmlLoader.load();
        ContractsSearchController contractsSearchController = fxmlLoader.getController();
        contractsSearchController.setDAOFactory(daoFactory);
        Tab tab = new Tab("Поиск в таблице \"Договоры\"", apSearchPane);
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
    private void handleBtnUpdateContractPress(ActionEvent event) {
        Optional.ofNullable(tvContracts.getSelectionModel().getSelectedItem()).ifPresent((contract) -> {
            bbSaveCancel.setDisable(false);
            gpBasic.setDisable(false);
            btnAddContract.setDisable(true);
            btnUpdateContract.setDisable(true);
            btnRemoveContract.setDisable(true);
            btnSearchContract.setDisable(true);
            fillControls(contract);
        });
    }


    private void fillControls(ContractorsContracts contract) {
        lbId.setText(contract.getId() == null ? "" : contract.getId().toString());
        tfName.setText(contract.getName());
        tfCreditDays.setText(contract.getCreditDays() == null ? null : contract.getCreditDays().toString());
        tfCreditAmount.setText(contract.getCreditAmount() == null ? null : contract.getCreditAmount().toString());
        dpDateOfContract.setValue(contract.getDateOfContract() == null ? null : contract.getDateOfContract().toLocalDate());
        dpDateOfTermination.setValue(contract.getDateOfTermination() == null ? null : contract.getDateOfTermination().toLocalDate());
        tfBecauseOf.setText(contract.getBecauseOf());
        cbDispatcher.setValue(contract.dispatcherProperty());
    }

    private void clearControls() {
        lbId.setText("");
        tfName.setText("");
        tfCreditDays.setText("");
        tfCreditAmount.setText("");
        dpDateOfContract.setValue(null);
        dpDateOfTermination.setValue(null);
        tfBecauseOf.setText("");
        cbDispatcher.getSelectionModel().selectFirst();
    }

}
