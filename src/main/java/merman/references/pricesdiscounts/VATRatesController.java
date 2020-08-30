package merman.references.pricesdiscounts;

import merman.util.dao.DAOFactory;
import merman.util.dao.interfaces.VATRatesDAO;
import merman.util.model.VATRates;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
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
import javafx.util.converter.DoubleStringConverter;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

/**
 * @author Arthur Sadykov
 */
public class VATRatesController implements Initializable {

    private static final String PATH_TO_VAT_RATES_SEARCH = "/merman/references/pricesdiscounts/vat-rates-search.fxml";
    @FXML
    private Button btnAddVATRate;
    @FXML
    private Button btnRemoveVATRate;

    @FXML
    private Button btnSearchVATRate;
    @FXML
    private Button btnUpdateVATRate;

    private DAOFactory daoFactory;
    @FXML
    private TableColumn<VATRates, StringProperty> tcName;
    @FXML
    private TableColumn<VATRates, ObjectProperty<BigDecimal>> tcRate;
    private TabPane tpContentPane;
    private VATRatesDAO vatRatesDAO;
    @FXML
    private ButtonBar bbSaveCancel;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSave;
    @FXML
    private GridPane gpBasic;
    @FXML
    private Label lbId;


    @FXML
    private TextField tfName;
    @FXML
    private TextField tfRate;
    @FXML
    private TabPane tpLeftTabPane;
    @FXML
    private TabPane tpRightTabPane;
    @FXML
    private TableView<VATRates> tvVATRates;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<VATRates> list = FXCollections.observableArrayList((vatRates) -> new Observable[]{vatRates.idProperty(), vatRates.nameProperty(), vatRates.rateProperty()});
        list.addListener((ListChangeListener.Change<? extends VATRates> change) -> {
            while (change.next()) {
                if (change.wasRemoved()) {
                    if (list.isEmpty()) {
                        btnUpdateVATRate.setDisable(true);
                        btnRemoveVATRate.setDisable(true);
                        btnSearchVATRate.setDisable(true);
                    }
                }
                if (change.wasAdded()) {
                    btnUpdateVATRate.setDisable(false);
                    btnRemoveVATRate.setDisable(false);
                    btnSearchVATRate.setDisable(false);
                }
            }
        });
        tvVATRates.setItems(list);
        tvVATRates.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> Optional.ofNullable(tvVATRates.getSelectionModel().getSelectedItem()).ifPresent((vatRate) -> {
            long id = vatRate.getId();
            if (id == 1 || id == 2) {
                btnAddVATRate.setDisable(false);
                btnUpdateVATRate.setDisable(true);
                btnRemoveVATRate.setDisable(true);
                bbSaveCancel.setDisable(true);
                gpBasic.setDisable(true);
            } else {
                btnAddVATRate.setDisable(false);
                btnUpdateVATRate.setDisable(false);
                btnRemoveVATRate.setDisable(false);
                bbSaveCancel.setDisable(false);
                gpBasic.setDisable(false);
            }
            fillControls(vatRate);
        }));
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
        tfRate.setTextFormatter(new TextFormatter<>(new DoubleStringConverter(), null, doubleFilter));
    }

    public void setupAfterInitialize(TabPane tpContentPane) {
        vatRatesDAO = daoFactory.getVATRatesDAO();
        this.tpContentPane = tpContentPane;
        tvVATRates.getItems().addAll(vatRatesDAO.list());
        if (!tvVATRates.getItems().isEmpty()) {
            btnUpdateVATRate.setDisable(false);
            btnRemoveVATRate.setDisable(false);
            btnSearchVATRate.setDisable(false);
            tvVATRates.getSelectionModel().selectFirst();
        } else {
            btnUpdateVATRate.setDisable(true);
            btnRemoveVATRate.setDisable(true);
            btnSearchVATRate.setDisable(true);
        }
    }

    public void setDAOFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @FXML
    private void handleBtnCancelPress(ActionEvent event) {
        btnAddVATRate.setDisable(false);
        if (!tvVATRates.getItems().isEmpty()) {
            btnUpdateVATRate.setDisable(false);
            btnRemoveVATRate.setDisable(false);
            btnSearchVATRate.setDisable(false);
            tvVATRates.getSelectionModel().selectFirst();
        } else {
            btnUpdateVATRate.setDisable(true);
            btnRemoveVATRate.setDisable(true);
            btnSearchVATRate.setDisable(true);
        }
        bbSaveCancel.setDisable(true);
        gpBasic.setDisable(true);
    }


    @FXML
    private void handleBtnRemoveVATRatePress(ActionEvent event) {
        Optional.ofNullable(tvVATRates.getSelectionModel().getSelectedItem()).ifPresent((vatRate) -> {
            int result = vatRatesDAO.delete(vatRate);
            if (result > 0) {
                tvVATRates.getItems().remove(vatRate);
            }
            clearControls();
        });
    }

    @FXML
    private void handleBtnSearchVATRatePress() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PATH_TO_VAT_RATES_SEARCH));
        AnchorPane apSearchPane = fxmlLoader.load();
        VatRatesSearchController vatRatesSearchController = fxmlLoader.getController();
        vatRatesSearchController.setDAOFactory(daoFactory);
        Tab tab = new Tab("Поиск в таблице \"Ставки НДС\"", apSearchPane);
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
    private void handleBtnSavePress(ActionEvent event) {
        Long id = lbId.getText() == null || lbId.getText().matches("") ? null : Long.parseLong(lbId.getText());
        String name = tfName.getText();
        BigDecimal rate = tfRate.getText() == null || tfRate.getText().matches("") ? null : new BigDecimal(tfRate.getText());
        if (id == null) {
            VATRates vatRate = new VATRates(id, name, rate);
            int result = vatRatesDAO.add(vatRate);
            if (result > 0) {
                tvVATRates.getItems().add(vatRate);
            }
        } else {
            VATRates vatRate = new VATRates(id, name, rate);
            int result = vatRatesDAO.update(vatRate);
            if (result > 0) {
                tvVATRates.getItems().set(tvVATRates.getSelectionModel().getSelectedIndex(), vatRate);
            }
        }
        btnAddVATRate.setDisable(false);
        btnUpdateVATRate.setDisable(false);
        btnRemoveVATRate.setDisable(false);
        btnSearchVATRate.setDisable(false);
        gpBasic.setDisable(true);
        bbSaveCancel.setDisable(true);
    }

    @FXML
    private void handleBtnAddVATRatePress(ActionEvent event) {
        Optional.ofNullable(tvVATRates.getSelectionModel().getSelectedItem()).ifPresent((vatRate) -> {
            bbSaveCancel.setDisable(false);
            gpBasic.setDisable(false);
            btnAddVATRate.setDisable(true);
            btnUpdateVATRate.setDisable(true);
            btnRemoveVATRate.setDisable(true);
            btnSearchVATRate.setDisable(true);
            clearControls();
        });
    }

    @FXML
    private void handleBtnUpdateVATRatePress(ActionEvent event) {
        Optional.ofNullable(tvVATRates.getSelectionModel().getSelectedItem()).ifPresent((unit) -> {
            bbSaveCancel.setDisable(false);
            gpBasic.setDisable(false);
            btnAddVATRate.setDisable(true);
            btnUpdateVATRate.setDisable(true);
            btnRemoveVATRate.setDisable(true);
            btnSearchVATRate.setDisable(true);
            fillControls(unit);
        });
    }


    private void fillControls(VATRates vatRate) {
        lbId.setText(vatRate.getId() == null ? "" : vatRate.getId().toString());
        tfName.setText(vatRate.getName());
        tfRate.setText(vatRate.getRate() == null ? null : vatRate.getRate().toString());
    }

    private void clearControls() {
        lbId.setText("");
        tfName.setText("");
        tfRate.setText("");
    }
}
