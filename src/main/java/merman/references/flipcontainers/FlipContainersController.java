package merman.references.flipcontainers;

import merman.util.dao.DAOFactory;
import merman.util.dao.interfaces.FlipContainersDAO;
import merman.util.dao.interfaces.UnitsDAO;
import merman.util.dao.interfaces.VATRatesDAO;
import merman.util.model.FlipContainers;
import merman.util.model.Units;
import merman.util.model.VATRates;
import javafx.beans.Observable;
import javafx.beans.property.LongProperty;
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
public class FlipContainersController implements Initializable {

    private static final String PATH_TO_FLIP_CONTAINERS_SEARCH = "/merman/references/flipcontainers/flip-containers-search.fxml";
    @FXML
    private ComboBox<LongProperty> cbUnit;
    private TabPane tpContentPane;
    @FXML
    private ButtonBar bbSaveCancel;
    @FXML
    private Button btnAddFlipContainer;
    @FXML
    private Button btnRemoveFlipContainer;

    @FXML
    private Button btnSearchFlipContainer;
    @FXML
    private Button btnUpdateFlipContainer;

    @FXML
    private ComboBox<LongProperty> cbVatRate;
    private DAOFactory daoFactory;
    private FlipContainersDAO flipContainersDAO;
    @FXML
    private GridPane gpBasic;

    @FXML
    private MenuItem handleMiCardPrintPress;
    @FXML
    private MenuItem handleMiRegistryPrintPress;
    @FXML
    private Label lbId;
    @FXML
    private TextArea taComment;
    @FXML
    private TableColumn<FlipContainers, StringProperty> tcName;
    @FXML
    private TableColumn<FlipContainers, StringProperty> tcShortName;
    @FXML
    private TextField tfDepositForOnePiece;
    @FXML
    private TextField tfFullName;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfPrice;
    @FXML
    private TextField tfShortName;
    @FXML
    private TextField tfVendorCode;
    @FXML
    private TableView<FlipContainers> tvFlipContainers;

    public void setDAOFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<FlipContainers> list = FXCollections.observableArrayList((flipContainers) -> new Observable[]{flipContainers.idProperty(), flipContainers.nameProperty(), flipContainers.fullNameProperty(), flipContainers.shortNameProperty(), flipContainers.unitProperty(),
                                                                                                                     flipContainers.vendorCodeProperty(), flipContainers.commentProperty(), flipContainers.priceProperty(), flipContainers.depositForOnePieceProperty(), flipContainers.vatRateProperty()});
        list.addListener((ListChangeListener.Change<? extends FlipContainers> change) -> {
            while (change.next()) {
                if (change.wasRemoved()) {
                    if (list.isEmpty()) {
                        btnUpdateFlipContainer.setDisable(true);
                        btnRemoveFlipContainer.setDisable(true);
                        btnSearchFlipContainer.setDisable(true);
                    }
                }
                if (change.wasAdded()) {
                    btnUpdateFlipContainer.setDisable(false);
                    btnRemoveFlipContainer.setDisable(false);
                    btnSearchFlipContainer.setDisable(false);
                }
            }
        });
        tvFlipContainers.setItems(list);
        tvFlipContainers.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> Optional.ofNullable(tvFlipContainers.getSelectionModel().getSelectedItem()).ifPresent(this::fillControls));
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
        tfPrice.setTextFormatter(new TextFormatter<>(new DoubleStringConverter(), null, doubleFilter));
        tfDepositForOnePiece.setTextFormatter(new TextFormatter<>(new DoubleStringConverter(), null, doubleFilter));
        StringConverter<LongProperty> unitsStringConverter = new StringConverter<>() {
            @Override
            public LongProperty fromString(String string) {
                return null;
            }

            @Override
            public String toString(LongProperty t) {
                UnitsDAO unitsDAO = daoFactory.getUnitsDAO();
                return Optional.ofNullable(unitsDAO.get(t.longValue())).map(Units::getName).orElse(null);
            }
        };
        StringConverter<LongProperty> vatRateStringConverter = new StringConverter<>() {
            @Override
            public LongProperty fromString(String string) {
                return null;
            }

            @Override
            public String toString(LongProperty t) {
                VATRatesDAO vatRatesDAO = daoFactory.getVATRatesDAO();
                return Optional.ofNullable(vatRatesDAO.get(t.longValue())).map(VATRates::getName).orElse(null);
            }
        };
        cbUnit.setConverter(unitsStringConverter);
        cbVatRate.setConverter(vatRateStringConverter);
    }

    public void setupAfterInitialize(TabPane tpContentPane) {
        flipContainersDAO = daoFactory.getFlipContainersDAO();
        this.tpContentPane = tpContentPane;
        tvFlipContainers.getItems().addAll(flipContainersDAO.list());
        if (!tvFlipContainers.getItems().isEmpty()) {
            btnUpdateFlipContainer.setDisable(false);
            btnRemoveFlipContainer.setDisable(false);
            btnSearchFlipContainer.setDisable(false);
            tvFlipContainers.getSelectionModel().selectFirst();
        } else {
            btnUpdateFlipContainer.setDisable(true);
            btnRemoveFlipContainer.setDisable(true);
            btnSearchFlipContainer.setDisable(true);
        }
        VATRatesDAO vatRatesDAO = daoFactory.getVATRatesDAO();
        vatRatesDAO.list().forEach((vatRate) -> cbVatRate.getItems().add(vatRate.idProperty()));
        UnitsDAO unitsDAO = daoFactory.getUnitsDAO();
        unitsDAO.list().forEach((unit) -> cbUnit.getItems().add(unit.idProperty()));
    }

    @FXML
    private void handleBtnAddFlipContainerPress(ActionEvent event) {
        bbSaveCancel.setDisable(false);
        gpBasic.setDisable(false);
        btnAddFlipContainer.setDisable(true);
        btnUpdateFlipContainer.setDisable(true);
        btnRemoveFlipContainer.setDisable(true);
        btnSearchFlipContainer.setDisable(true);
        clearControls();
    }

    @FXML
    private void handleBtnCancelPress(ActionEvent event) {
        btnAddFlipContainer.setDisable(false);
        if (!tvFlipContainers.getItems().isEmpty()) {
            btnUpdateFlipContainer.setDisable(false);
            btnRemoveFlipContainer.setDisable(false);
            btnSearchFlipContainer.setDisable(false);
            tvFlipContainers.getSelectionModel().selectFirst();
        } else {
            btnUpdateFlipContainer.setDisable(true);
            btnRemoveFlipContainer.setDisable(true);
            btnSearchFlipContainer.setDisable(true);
        }
        bbSaveCancel.setDisable(true);
        gpBasic.setDisable(true);
    }

    @FXML
    private void handleBtnSearchFlipContainerPress() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PATH_TO_FLIP_CONTAINERS_SEARCH));
        AnchorPane apSearchPane = fxmlLoader.load();
        FlipContainersSearchController flipContainersSearchController = fxmlLoader.getController();
        flipContainersSearchController.setDAOFactory(daoFactory);
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
    private void handleBtnUpdateFlipContainerPress(ActionEvent event) {
        Optional.ofNullable(tvFlipContainers.getSelectionModel().getSelectedItem()).ifPresent((flipContainer) -> {
            bbSaveCancel.setDisable(false);
            gpBasic.setDisable(false);
            btnAddFlipContainer.setDisable(true);
            btnUpdateFlipContainer.setDisable(true);
            btnRemoveFlipContainer.setDisable(true);
            btnSearchFlipContainer.setDisable(true);
            fillControls(flipContainer);
        });
    }

    @FXML
    private void handleBtnRemoveFlipContainerPress(ActionEvent event) {
        Optional.ofNullable(tvFlipContainers.getSelectionModel().getSelectedItem()).ifPresent((flipContainer) -> {
            int result = flipContainersDAO.delete(flipContainer);
            if (result > 0) {
                tvFlipContainers.getItems().remove(flipContainer);
            }
            clearControls();
        });
    }

    @FXML
    private void handleBtnSavePress(ActionEvent event) {
        Long id = lbId.getText() == null || lbId.getText().matches("") ? null : Long.parseLong(lbId.getText());
        String name = tfName.getText();
        String fullName = tfFullName.getText();
        String shortName = tfShortName.getText();
        Long unit = cbUnit.getValue() == null ? null : cbUnit.getValue().get();
        String vendorCode = tfVendorCode.getText();
        String comment = taComment.getText();
        BigDecimal price = tfPrice.getText() == null || tfPrice.getText().matches("") ? null : new BigDecimal(tfPrice.getText());
        BigDecimal depositForOnePiece = tfDepositForOnePiece.getText() == null || tfDepositForOnePiece.getText().matches("") ? null : new BigDecimal(tfDepositForOnePiece.getText());
        Long vatRate = cbVatRate.getValue() == null ? null : cbVatRate.getValue().get();
        if (id == null) {
            FlipContainers flipContainer = new FlipContainers(id, name, fullName, shortName, unit, vendorCode, comment, price, depositForOnePiece, vatRate);
            int result = flipContainersDAO.add(flipContainer);
            if (result > 0) {
                tvFlipContainers.getItems().add(flipContainer);
            }
        } else {
            FlipContainers flipContainer = new FlipContainers(id, name, fullName, shortName, unit, vendorCode, comment, price, depositForOnePiece, vatRate);
            int result = flipContainersDAO.update(flipContainer);
            if (result > 0) {
                tvFlipContainers.getItems().set(tvFlipContainers.getSelectionModel().getSelectedIndex(), flipContainer);
            }
        }
        gpBasic.setDisable(true);
        bbSaveCancel.setDisable(true);
        btnAddFlipContainer.setDisable(false);
        btnUpdateFlipContainer.setDisable(false);
        btnRemoveFlipContainer.setDisable(false);
        btnSearchFlipContainer.setDisable(false);
        gpBasic.setDisable(true);
        bbSaveCancel.setDisable(true);
    }


    private void fillControls(FlipContainers flipContainer) {
        lbId.setText(flipContainer.getId() == null ? "" : flipContainer.getId().toString());
        tfName.setText(flipContainer.getName());
        tfFullName.setText(flipContainer.getFullName());
        tfShortName.setText(flipContainer.getShortName());
        cbUnit.setValue(flipContainer.unitProperty());
        tfVendorCode.setText(flipContainer.getVendorCode());
        taComment.setText(flipContainer.getComment());
        tfPrice.setText(flipContainer.getPrice() == null ? null : flipContainer.getPrice().toString());
        tfDepositForOnePiece.setText(flipContainer.getDepositForOnePiece() == null ? null : flipContainer.getDepositForOnePiece().toString());
        cbVatRate.setValue(flipContainer.vatRateProperty());
    }

    private void clearControls() {
        lbId.setText("");
        tfName.setText("");
        tfFullName.setText("");
        tfShortName.setText("");
        cbUnit.getSelectionModel().selectFirst();
        tfVendorCode.setText("");
        taComment.setText("");
        tfPrice.setText("");
        tfDepositForOnePiece.setText("");
        cbVatRate.getSelectionModel().selectFirst();
    }
}
