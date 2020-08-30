package merman.references.companystructure;

import merman.util.dao.DAOFactory;
import merman.util.dao.interfaces.EmployeesDAO;
import merman.util.dao.interfaces.WarehousesDAO;
import merman.util.model.Warehouses;
import javafx.beans.Observable;
import javafx.beans.property.LongProperty;
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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author Arthur Sadykov
 */
public class WarehousesController implements Initializable {

    private static final String PATH_TO_WAREHOUSES_SEARCH = "/merman/references/companystructure/warehouses-search.fxml";
    private DAOFactory daoFactory;
    private WarehousesDAO warehousesDAO;
    @FXML
    private ButtonBar bbSaveCancel;
    @FXML
    private Button btnAddWarehouse;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnRemoveWarehouse;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnSearchWarehouse;
    @FXML
    private Button btnUpdateWarehouse;
    @FXML
    private ComboBox<LongProperty> cbStorekeeper;
    @FXML
    private GridPane gpBasic;
    @FXML
    private Label lbId;


    @FXML
    private TableColumn<Warehouses, String> tcName;
    @FXML
    private TableColumn<Warehouses, Number> tcStorekeeper;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfPhysicalAddress;
    @FXML
    private TabPane tpRightTabPane;
    @FXML
    private TableView<Warehouses> tvWarehouses;
    private TabPane tpContentPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<Warehouses> list = FXCollections.observableArrayList((warehouses) -> new Observable[]{warehouses.idProperty(), warehouses.nameProperty(), warehouses.storekeeperProperty(), warehouses.physicalAddressProperty(), warehouses.emailProperty()});
        tvWarehouses.setItems(list);
        tvWarehouses.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
            Optional.of(tvWarehouses.getSelectionModel().getSelectedItem()).ifPresent((warehouse) -> {
                long id = warehouse.getId();
                if (id == 1) {
                    btnRemoveWarehouse.setDisable(true);
                } else {
                    btnRemoveWarehouse.setDisable(false);
                }
                fillControls(warehouse);
            });
            StringConverter<LongProperty> storekeeperStringConverter = new StringConverter<>() {
                @Override
                public LongProperty fromString(String string) {
                    return null;
                }

                @Override
                public String toString(LongProperty t) {
                    EmployeesDAO employeesDAO = daoFactory.getEmployeesDAO();
                    return Optional.of(t.longValue()).map(employeesDAO::get).map((employee) -> employee.getSurname() + " " + employee.getName()).orElse(null);
                }
            };
            cbStorekeeper.setConverter(storekeeperStringConverter);
            tcStorekeeper.setCellFactory((col) -> new TableCell<>() {
                @Override
                public void updateItem(Number storekeeperId, boolean empty) {
                    super.updateItem(storekeeperId, empty);
                    if (empty || storekeeperId == null) {
                        setText(null);
                    } else {
                        EmployeesDAO employeesDAO = daoFactory.getEmployeesDAO();
                        Optional.of(storekeeperId.longValue()).map(employeesDAO::get).ifPresent((employee) -> setText(employee.getSurname() + " " + employee.getName()));
                    }
                }
            });
        });
    }

    public void setupAfterInitialize(TabPane tpContentPane) {
        this.tpContentPane = tpContentPane;
        warehousesDAO = daoFactory.getWarehousesDAO();
        tvWarehouses.getItems().addAll(warehousesDAO.list());
        if (!tvWarehouses.getItems().isEmpty()) {
            btnUpdateWarehouse.setDisable(false);
            btnRemoveWarehouse.setDisable(false);
            btnSearchWarehouse.setDisable(false);
            tvWarehouses.getSelectionModel().selectFirst();
        } else {
            btnUpdateWarehouse.setDisable(true);
            btnRemoveWarehouse.setDisable(true);
            btnSearchWarehouse.setDisable(true);
        }
        EmployeesDAO employeesDAO = daoFactory.getEmployeesDAO();
        employeesDAO.list().forEach((employee) -> cbStorekeeper.getItems().add(employee.idProperty()));
    }

    public void setDAOFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @FXML
    private void handleBtnAddWarehousePress(ActionEvent event) {
        bbSaveCancel.setDisable(false);
        gpBasic.setDisable(false);
        btnAddWarehouse.setDisable(true);
        btnUpdateWarehouse.setDisable(true);
        btnRemoveWarehouse.setDisable(true);
        btnSearchWarehouse.setDisable(true);
        clearControls();
    }

    @FXML
    private void handleBtnCancelPress(ActionEvent event) {
        btnAddWarehouse.setDisable(false);
        if (!tvWarehouses.getItems().isEmpty()) {
            btnUpdateWarehouse.setDisable(false);
            btnRemoveWarehouse.setDisable(false);
            btnSearchWarehouse.setDisable(false);
            tvWarehouses.getSelectionModel().selectFirst();
        } else {
            btnUpdateWarehouse.setDisable(true);
            btnRemoveWarehouse.setDisable(true);
            btnSearchWarehouse.setDisable(true);
        }
        bbSaveCancel.setDisable(true);
        gpBasic.setDisable(true);
    }


    @FXML
    private void handleBtnRemoveWarehousePress(ActionEvent event) {
        Optional.ofNullable(tvWarehouses.getSelectionModel().getSelectedItem()).ifPresent((warehouse) -> {
            int result = warehousesDAO.delete(warehouse);
            if (result > 0) {
                tvWarehouses.getItems().remove(warehouse);
            }
            clearControls();
        });
    }

    @FXML
    private void handleBtnSavePress(ActionEvent event) {
        Long id = lbId.getText() == null || lbId.getText().matches("") ? null : Long.parseLong(lbId.getText());
        String name = tfName.getText();
        Long storekeeper = cbStorekeeper.getValue() == null ? null : cbStorekeeper.getValue().get();
        String physicalAddress = tfPhysicalAddress.getText();
        String email = tfEmail.getText();
        if (id == null) {
            Warehouses warehouse = new Warehouses(id, name, storekeeper, physicalAddress, email);
            int result = warehousesDAO.add(warehouse);
            if (result > 0) {
                tvWarehouses.getItems().add(warehouse);
            }
        } else {
            Warehouses warehouse = new Warehouses(id, name, storekeeper, physicalAddress, email);
            int result = warehousesDAO.update(warehouse);
            if (result > 0) {
                tvWarehouses.getItems().set(tvWarehouses.getSelectionModel().getSelectedIndex(), warehouse);
            }
        }
        btnAddWarehouse.setDisable(false);
        btnUpdateWarehouse.setDisable(false);
        btnRemoveWarehouse.setDisable(false);
        btnSearchWarehouse.setDisable(false);
        gpBasic.setDisable(true);
        bbSaveCancel.setDisable(true);
    }

    @FXML
    private void handleBtnSearchWarehousePress() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PATH_TO_WAREHOUSES_SEARCH));
        AnchorPane apSearchPane = fxmlLoader.load();
        WarehousesSearchController warehousesSearchController = fxmlLoader.getController();
        warehousesSearchController.setDAOFactory(daoFactory);
        Tab tab = new Tab("Поиск в таблице \"Склады\"", apSearchPane);
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
    private void handleBtnUpdateWarehousePress(ActionEvent event) {
        Optional.ofNullable(tvWarehouses.getSelectionModel().getSelectedItem()).ifPresent((warehouse) -> {
            bbSaveCancel.setDisable(false);
            gpBasic.setDisable(false);
            btnAddWarehouse.setDisable(true);
            btnUpdateWarehouse.setDisable(true);
            btnRemoveWarehouse.setDisable(true);
            btnSearchWarehouse.setDisable(true);
            fillControls(warehouse);
        });
    }


    private void fillControls(Warehouses warehouse) {
        lbId.setText(warehouse.getId() == null ? "" : warehouse.getId().toString());
        tfName.setText(warehouse.getName());
        cbStorekeeper.setValue(warehouse.storekeeperProperty());
        tfPhysicalAddress.setText(warehouse.getPhysicalAddress());
        tfEmail.setText(warehouse.getEmail());
    }

    private void clearControls() {
        lbId.setText("");
        tfName.setText("");
        cbStorekeeper.getSelectionModel().selectFirst();
        tfPhysicalAddress.setText("");
        tfEmail.setText("");
    }
}
