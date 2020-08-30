package merman.references.classifiers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.Observable;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import merman.util.dao.DAOFactory;
import merman.util.dao.interfaces.UnitsDAO;
import merman.util.model.Units;

/**
 * @author Arthur Sadykov
 */
public class UnitsController implements Initializable {

    private static final String PATH_TO_UNITS_SEARCH = "/merman/references/classifiers/units-search.fxml";
    private DAOFactory daoFactory;
    private TabPane tpContentPane;
    private UnitsDAO unitsDAO;
    @FXML
    private ButtonBar bbSaveCancel;
    @FXML
    private Button btnAddUnit;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnRemoveUnit;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnSearchUnit;
    @FXML
    private Button btnUpdateUnit;
    @FXML
    private GridPane gpBasic;
    @FXML
    private Label lbId;
    @FXML
    private TableColumn<Units, StringProperty> tcFullName;
    @FXML
    private TableColumn<Units, StringProperty> tcName;
    @FXML
    private TableColumn<Units, StringProperty> tcUnitCode;
    @FXML
    private TextField tfFullName;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfUnitCode;
    @FXML
    private TabPane tpRightTabPane;
    @FXML
    private TableView<Units> tvUnits;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<Units> list = FXCollections.observableArrayList((unit) -> new Observable[]{unit.nameProperty(),
            unit.unitCodeProperty(), unit.fullNameProperty()});
        tvUnits.setItems(list);
        tvUnits.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) ->
                Optional.ofNullable(tvUnits.getSelectionModel().getSelectedItem()).ifPresent((unit) -> {
                    fillControls(unit);
                }));
    }

    public void setupAfterInitialize(TabPane tpContentPane) {
        unitsDAO = daoFactory.getUnitsDAO();
        this.tpContentPane = tpContentPane;
        tvUnits.getItems().addAll(unitsDAO.list());
    }

    public void setDAOFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @FXML
    private void handleBtnAddUnitPress(ActionEvent event) {
        bbSaveCancel.setDisable(false);
        gpBasic.setDisable(false);
        btnAddUnit.setDisable(true);
        btnUpdateUnit.setDisable(true);
        btnRemoveUnit.setDisable(true);
        btnSearchUnit.setDisable(true);
        clearControls();
    }

    @FXML
    private void handleBtnCancelPress(ActionEvent event) {
        btnAddUnit.setDisable(false);
        if (!tvUnits.getItems().isEmpty()) {
            btnUpdateUnit.setDisable(false);
            btnRemoveUnit.setDisable(false);
            btnSearchUnit.setDisable(false);
            tvUnits.getSelectionModel().selectFirst();
        } else {
            btnUpdateUnit.setDisable(true);
            btnRemoveUnit.setDisable(true);
            btnSearchUnit.setDisable(true);
        }
        bbSaveCancel.setDisable(true);
        gpBasic.setDisable(true);
    }

    @FXML
    private void handleBtnRemoveUnitPress(ActionEvent event) {
        Optional.ofNullable(tvUnits.getSelectionModel().getSelectedItem()).ifPresent((unit) -> {
            int result = unitsDAO.delete(unit);
            if (result > 0) {
                tvUnits.getItems().remove(unit);
            }
            clearControls();
        });
    }

    @FXML
    private void handleBtnSavePress(ActionEvent event) {
        Long id = lbId.getText() == null || lbId.getText().matches("") ? null : Long.parseLong(lbId.getText());
        String name = tfName.getText();
        String unitCode = tfUnitCode.getText();
        String fullName = tfFullName.getText();
        if (id == null) {
            Units unit = new Units(id, name, unitCode, fullName);
            int result = unitsDAO.add(unit);
            if (result > 0) {
                tvUnits.getItems().add(unit);
            }
        } else {
            Units unit = new Units(id, name, unitCode, fullName);
            int result = unitsDAO.update(unit);
            if (result > 0) {
                tvUnits.getItems().set(tvUnits.getSelectionModel().getSelectedIndex(), unit);
            }
        }
        btnAddUnit.setDisable(false);
        btnUpdateUnit.setDisable(false);
        btnRemoveUnit.setDisable(false);
        btnSearchUnit.setDisable(false);
        gpBasic.setDisable(true);
        bbSaveCancel.setDisable(true);
    }

    @FXML
    private void handleBtnSearchUnitPress() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PATH_TO_UNITS_SEARCH));
        AnchorPane apSearchPane = fxmlLoader.load();
        UnitsSearchController unitsSearchController = fxmlLoader.getController();
        unitsSearchController.setDAOFactory(daoFactory);
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
    private void handleBtnUpdateUnitPress(ActionEvent event) {
        Optional.ofNullable(tvUnits.getSelectionModel().getSelectedItem()).ifPresent((unit) -> {
            bbSaveCancel.setDisable(false);
            gpBasic.setDisable(false);
            btnAddUnit.setDisable(true);
            btnUpdateUnit.setDisable(true);
            btnRemoveUnit.setDisable(true);
            btnSearchUnit.setDisable(true);
            fillControls(unit);
        });
    }

    private void fillControls(Units unit) {
        lbId.setText(unit.getId() == null ? "" : unit.getId().toString());
        tfName.setText(unit.getName());
        tfUnitCode.setText(unit.getUnitCode());
        tfFullName.setText(unit.getFullName());
    }

    private void clearControls() {
        lbId.setText("");
        tfName.setText("");
        tfUnitCode.setText("");
        tfFullName.setText("");
    }
}
