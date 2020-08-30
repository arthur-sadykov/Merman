package merman.documents.consumableinvoices;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import merman.alert.AlertFX;
import merman.util.dao.DAOFactory;
import merman.util.dao.interfaces.ConsumableInvoicesDAO;
import merman.util.dao.interfaces.ContractorsDAO;
import merman.util.dao.interfaces.EmployeesDAO;
import merman.util.dao.interfaces.FirmsDAO;
import merman.util.model.ConsumableInvoices;

/**
 * @author Arthur Sadykov
 */
public class ConsumableInvoicesTableController implements Initializable {

    private static final String CONSUMABLE_INVOICES = "Новый документ \"Заказ контрагента\"";
    private static final String PATH_TO_CONSUMABLE_INVOICES =
            "/merman/documents/consumableinvoices/consumable-invoices.fxml";
    private static final String TEMP_DIR = System.getProperty("java.io.tmpdir");
    private static final String MERMAN_DIR = "merman";
    private TabPane tpContentPane;
    private ConsumableInvoicesDAO consumableInvoicesDAO;
    private DAOFactory daoFactory;
    @FXML
    private Button btnAddConsumableInvoice;
    @FXML
    private Button btnRemoveConsumableInvoice;
    @FXML
    private Button btnSearchConsumableInvoice;
    @FXML
    private TableColumn<ConsumableInvoices, String> tcAddress;
    @FXML
    private TableColumn<ConsumableInvoices, String> tcComment;
    @FXML
    private TableColumn<ConsumableInvoices, Number> tcContractor;
    @FXML
    private TableColumn<ConsumableInvoices, Number> tcDispatcher;
    @FXML
    private TableColumn<ConsumableInvoices, LocalDate> tcDocumentDate;
    @FXML
    private TableColumn<ConsumableInvoices, Number> tcFirm;
    @FXML
    private TableColumn<ConsumableInvoices, String> tcNumber;
    @FXML
    private TableView<ConsumableInvoices> tvConsumableInvoices;
    private ConsumableInvoices selectedConsumableInvoice;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcFirm.setCellFactory((col) -> new TableCell<>() {
            @Override
            public void updateItem(Number firmId, boolean empty) {
                super.updateItem(firmId, empty);
                if (empty || firmId == null) {
                    setText(null);
                } else {
                    FirmsDAO firmsDAO = daoFactory.getFirmsDAO();
                    Optional.of(firmId.longValue()).map(firmsDAO::get).ifPresent((firm) -> setText(firm.getName()));
                }
            }
        });
        tcContractor.setCellFactory((col) -> new TableCell<>() {
            @Override
            public void updateItem(Number contractorId, boolean empty) {
                super.updateItem(contractorId, empty);
                if (empty || contractorId == null) {
                    setText(null);
                } else {
                    ContractorsDAO contractorsDAO = daoFactory.getContractorsDAO();
                    Optional.of(contractorId.longValue()).map(contractorsDAO::get).ifPresent((contractor) ->
                            setText(contractor.getName()));
                }
            }
        });
        tcDispatcher.setCellFactory((col) -> new TableCell<>() {
            @Override
            public void updateItem(Number dispatcherId, boolean empty) {
                super.updateItem(dispatcherId, empty);
                if (empty || dispatcherId == null) {
                    setText(null);
                } else {
                    EmployeesDAO employeesDAO = daoFactory.getEmployeesDAO();
                    Optional.of(dispatcherId.longValue()).map(employeesDAO::get).ifPresent((employee) ->
                            setText(employee.getName()));
                }
            }
        });
    }

    public void setDAOFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public void setupAfterInitialize(TabPane tpContentPane) {
        consumableInvoicesDAO = daoFactory.getConsumableInvoicesDAO();
        this.tpContentPane = tpContentPane;
        tvConsumableInvoices.getItems().addAll(consumableInvoicesDAO.list());
        if (!tvConsumableInvoices.getItems().isEmpty()) {
            btnRemoveConsumableInvoice.setDisable(false);
            btnSearchConsumableInvoice.setDisable(false);
            tvConsumableInvoices.getSelectionModel().selectFirst();
        } else {
            btnRemoveConsumableInvoice.setDisable(true);
            btnSearchConsumableInvoice.setDisable(true);
        }
        try {
            tvConsumableInvoices.getSelectionModel().select(8);
            handleBtnEditConsumableInvoicePress();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBtnAddConsumableInvoicePress() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PATH_TO_CONSUMABLE_INVOICES));
        Parent root = fxmlLoader.load();
        ConsumableInvoicesController consumableInvoicesController = fxmlLoader.getController();
        consumableInvoicesController.setDAOFactory(daoFactory);
        consumableInvoicesController.setupAfterInitialize(tpContentPane, this, null);
        Tab tab = new Tab(CONSUMABLE_INVOICES, root);
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
        AnchorPane.setBottomAnchor(root, 0.0);
        AnchorPane.setLeftAnchor(root, 0.0);
        AnchorPane.setTopAnchor(root, 0.0);
        AnchorPane.setRightAnchor(root, 0.0);
    }

    @FXML
    private void handleBtnRemoveConsumableInvoicePress(ActionEvent event) {
        Optional.ofNullable(tvConsumableInvoices.getSelectionModel().getSelectedItem()).ifPresent((consumableInvoice) ->
        {
            int result = consumableInvoicesDAO.delete(consumableInvoice);
            if (result > 0) {
                tvConsumableInvoices.getItems().remove(consumableInvoice);
            }
        });
    }

    @FXML
    private void handleBtnSearchConsumableInvoicePress(ActionEvent event) {
    }

    public TableView<ConsumableInvoices> getTvConsumableInvoices() {
        return tvConsumableInvoices;
    }

    @FXML
    private void handleBtnEditConsumableInvoicePress() throws IOException {
        ConsumableInvoices consumableInvoice = tvConsumableInvoices.getSelectionModel().getSelectedItem();
        if (consumableInvoice != null) {
            try {
                Path pathToBeDeleted = Paths.get(TEMP_DIR).resolve(MERMAN_DIR);
                if (Files.exists(pathToBeDeleted)) {
                    Files.walk(pathToBeDeleted).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
                }
            } catch (IOException e) {
                AlertFX.showError(e.getMessage(), e);
            }
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PATH_TO_CONSUMABLE_INVOICES));
            Parent root = fxmlLoader.load();
            ConsumableInvoicesController consumableInvoicesController = fxmlLoader.getController();
            consumableInvoicesController.setDAOFactory(daoFactory);
            consumableInvoicesController.setupAfterInitialize(tpContentPane, this, consumableInvoice);
            Tab tab = new Tab(CONSUMABLE_INVOICES, root);
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
            AnchorPane.setBottomAnchor(root, 0.0);
            AnchorPane.setLeftAnchor(root, 0.0);
            AnchorPane.setTopAnchor(root, 0.0);
            AnchorPane.setRightAnchor(root, 0.0);
        }
    }
}
