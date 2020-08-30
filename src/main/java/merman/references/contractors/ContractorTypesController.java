package merman.references.contractors;

import merman.util.dao.DAOFactory;
import merman.util.dao.interfaces.ContractorTypesDAO;
import merman.util.model.ContractorTypes;
import javafx.beans.Observable;
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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author Arthur Sadykov
 */
public class ContractorTypesController implements Initializable {

    private static final String PATH_TO_CONTRACTOR_TYPES_SEARCH = "/merman/references/contractors/contractor-types-search.fxml";
    private DAOFactory daoFactory;
    private ContractorTypesDAO contractorTypesDAO;
    @FXML
    private ButtonBar bbSaveCancel;
    @FXML
    private Button btnAddContractorType;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnRemoveContractorType;

    @FXML
    private Button btnSave;
    @FXML
    private Button btnSearchContractorType;
    @FXML
    private Button btnUpdateContractorType;
    @FXML
    private CheckBox chbIsCustomer;
    @FXML
    private CheckBox chbIsSupplier;
    @FXML
    private GridPane gpBasic;
    @FXML
    private Label lbId;


    @FXML
    private TableColumn<ContractorTypes, StringProperty> tcName;
    @FXML
    private TextField tfName;
    private TabPane tpContentPane;
    @FXML
    private TableView<ContractorTypes> tvContractorTypes;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<ContractorTypes> list = FXCollections.observableArrayList((contractorTypes) -> new Observable[]{contractorTypes.idProperty(), contractorTypes.nameProperty(), contractorTypes.isSupplierProperty(), contractorTypes.isCustomerProperty()});
        list.addListener((ListChangeListener.Change<? extends ContractorTypes> change) -> {
            while (change.next()) {
                if (change.wasRemoved()) {
                    if (list.isEmpty()) {
                        btnUpdateContractorType.setDisable(true);
                        btnRemoveContractorType.setDisable(true);
                        btnSearchContractorType.setDisable(true);
                    }
                }
                if (change.wasAdded()) {
                    btnUpdateContractorType.setDisable(false);
                    btnRemoveContractorType.setDisable(false);
                    btnSearchContractorType.setDisable(false);
                }
            }
        });
        tvContractorTypes.setItems(list);
        tvContractorTypes.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> Optional.ofNullable(tvContractorTypes.getSelectionModel().getSelectedItem()).ifPresent((contractorType) -> {
            long id = contractorType.getId();
            if (id == 1 || id == 2) {
                btnAddContractorType.setDisable(false);
                btnUpdateContractorType.setDisable(true);
                btnRemoveContractorType.setDisable(true);
                bbSaveCancel.setDisable(true);
                gpBasic.setDisable(true);
            } else {
                btnUpdateContractorType.setDisable(false);
                btnRemoveContractorType.setDisable(false);
                bbSaveCancel.setDisable(false);
                gpBasic.setDisable(false);
            }
            fillControls(contractorType);
        }));
    }

    public void setupAfterInitialize(TabPane tpContentPane) {
        contractorTypesDAO = daoFactory.getContractorTypesDAO();
        this.tpContentPane = tpContentPane;
        tvContractorTypes.getItems().addAll(contractorTypesDAO.list());
        if (!tvContractorTypes.getItems().isEmpty()) {
            btnUpdateContractorType.setDisable(false);
            btnRemoveContractorType.setDisable(false);
            btnSearchContractorType.setDisable(false);
            tvContractorTypes.getSelectionModel().selectFirst();
        } else {
            btnUpdateContractorType.setDisable(true);
            btnRemoveContractorType.setDisable(true);
            btnSearchContractorType.setDisable(true);
        }
    }

    public void setDAOFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @FXML
    private void handleBtnAddContractorTypePress(ActionEvent event) {
        bbSaveCancel.setDisable(false);
        gpBasic.setDisable(false);
        btnAddContractorType.setDisable(true);
        btnUpdateContractorType.setDisable(true);
        btnRemoveContractorType.setDisable(true);
        btnSearchContractorType.setDisable(true);
        clearControls();
    }

    @FXML
    private void handleBtnCancelPress(ActionEvent event) {
        btnAddContractorType.setDisable(false);
        if (!tvContractorTypes.getItems().isEmpty()) {
            btnUpdateContractorType.setDisable(false);
            btnRemoveContractorType.setDisable(false);
            btnSearchContractorType.setDisable(false);
            tvContractorTypes.getSelectionModel().selectFirst();
        } else {
            btnUpdateContractorType.setDisable(true);
            btnRemoveContractorType.setDisable(true);
            btnSearchContractorType.setDisable(true);
        }
        bbSaveCancel.setDisable(true);
        gpBasic.setDisable(true);
    }

    @FXML
    private void handleBtnRemoveContractorTypePress(ActionEvent event) {
        Optional.ofNullable(tvContractorTypes.getSelectionModel().getSelectedItem()).ifPresent((contractorType) -> {
            int result = contractorTypesDAO.delete(contractorType);
            if (result > 0) {
                tvContractorTypes.getItems().remove(contractorType);
            }
            clearControls();
        });
    }

    @FXML
    private void handleBtnSavePress(ActionEvent event) {
        Long id = lbId.getText() == null || lbId.getText().matches("") ? null : Long.parseLong(lbId.getText());
        String name = tfName.getText();
        Boolean isSupplier = chbIsSupplier.isSelected();
        Boolean isCustomer = chbIsCustomer.isSelected();
        if (id == null) {
            ContractorTypes contractorType = new ContractorTypes(id, name, isSupplier, isCustomer);
            int result = contractorTypesDAO.add(contractorType);
            if (result > 0) {
                tvContractorTypes.getItems().add(contractorType);
            }
        } else {
            ContractorTypes contractorType = new ContractorTypes(id, name, isSupplier, isCustomer);
            int result = contractorTypesDAO.update(contractorType);
            if (result > 0) {
                tvContractorTypes.getItems().set(tvContractorTypes.getSelectionModel().getSelectedIndex(), contractorType);
            }
        }
        btnAddContractorType.setDisable(false);
        btnUpdateContractorType.setDisable(false);
        btnRemoveContractorType.setDisable(false);
        btnSearchContractorType.setDisable(false);
        gpBasic.setDisable(true);
        bbSaveCancel.setDisable(true);
    }

    @FXML
    private void handleBtnSearchContractorTypePress() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PATH_TO_CONTRACTOR_TYPES_SEARCH));
        AnchorPane apSearchPane = fxmlLoader.load();
        ContractorTypesSearchController contractorTypesSearchController = fxmlLoader.getController();
        contractorTypesSearchController.setDAOFactory(daoFactory);
        Tab tab = new Tab("Поиск в таблице \"Контрагенты\"", apSearchPane);
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
    private void handleBtnUpdateContractorTypePress(ActionEvent event) {
        Optional.ofNullable(tvContractorTypes.getSelectionModel().getSelectedItem()).ifPresent((contractorType) -> {
            bbSaveCancel.setDisable(false);
            gpBasic.setDisable(false);
            btnAddContractorType.setDisable(true);
            btnUpdateContractorType.setDisable(true);
            btnRemoveContractorType.setDisable(true);
            btnSearchContractorType.setDisable(true);
            fillControls(contractorType);
        });
    }


    private void fillControls(ContractorTypes contractorType) {
        lbId.setText(contractorType.getId() == null ? "" : contractorType.getId().toString());
        tfName.setText(contractorType.getName());
        chbIsSupplier.setSelected(contractorType.getIsSupplier());
        chbIsCustomer.setSelected(contractorType.getIsCustomer());
    }

    private void clearControls() {
        lbId.setText("");
        tfName.setText("");
        chbIsSupplier.setSelected(false);
        chbIsCustomer.setSelected(false);
    }
}
