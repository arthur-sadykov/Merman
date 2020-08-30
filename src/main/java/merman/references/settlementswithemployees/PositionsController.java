package merman.references.settlementswithemployees;

import merman.util.dao.DAOFactory;
import merman.util.dao.interfaces.PositionsDAO;
import merman.util.model.Positions;
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
public class PositionsController implements Initializable {

    private static final String PATH_TO_POSITIONS_SEARCH = "/merman/references/settlementswithemployees/positions-search.fxml";

    private PositionsDAO positionsDAO;
    @FXML
    private TextField tfMinimumPaymentPerDay;

    private TabPane tpContentPane;
    private DAOFactory daoFactory;

    @FXML
    private ButtonBar bbSaveCancel;
    @FXML
    private Button btnAddPosition;
    @FXML
    private Button btnRemovePosition;

    @FXML
    private Button btnSearchPosition;
    @FXML
    private Button btnUpdatePosition;
    @FXML
    private GridPane gpBasic;
    @FXML
    private MenuItem handleMiCardPrintPress;
    @FXML
    private MenuItem handleMiRegistryPrintPress;
    @FXML
    private Label lbId;
    @FXML
    private TableColumn<Positions, StringProperty> tcName;
    @FXML
    private TextField tfName;
    @FXML
    private TableView<Positions> tvPositions;

    public void setDAOFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<Positions> list = FXCollections.observableArrayList((positions) -> new Observable[]{positions.idProperty(), positions.nameProperty(), positions.minimumPaymentPerDayProperty()});
        tvPositions.setItems(list);
        tvPositions.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> Optional.ofNullable(tvPositions.getSelectionModel().getSelectedItem()).ifPresent((position) -> {
            fillControls(position);
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
        tfMinimumPaymentPerDay.setTextFormatter(new TextFormatter<>(new DoubleStringConverter(), null, doubleFilter));
    }

    public void setupAfterInitialize(TabPane tpContentPane) {
        positionsDAO = daoFactory.getPositionsDAO();
        this.tpContentPane = tpContentPane;
        tvPositions.getItems().addAll(positionsDAO.list());
        if (!tvPositions.getItems().isEmpty()) {
            btnUpdatePosition.setDisable(false);
            btnRemovePosition.setDisable(false);
            btnSearchPosition.setDisable(false);
            tvPositions.getSelectionModel().selectFirst();
        } else {
            btnUpdatePosition.setDisable(true);
            btnRemovePosition.setDisable(true);
            btnSearchPosition.setDisable(true);
        }
    }

    @FXML
    private void handleBtnAddPositionPress(ActionEvent event) {
        bbSaveCancel.setDisable(false);
        gpBasic.setDisable(false);
        btnAddPosition.setDisable(true);
        btnUpdatePosition.setDisable(true);
        btnRemovePosition.setDisable(true);
        btnSearchPosition.setDisable(true);
        clearControls();
    }

    @FXML
    private void handleBtnCancelPress(ActionEvent event) {
        btnAddPosition.setDisable(false);
        if (!tvPositions.getItems().isEmpty()) {
            btnUpdatePosition.setDisable(false);
            btnRemovePosition.setDisable(false);
            btnSearchPosition.setDisable(false);
            tvPositions.getSelectionModel().selectFirst();
        } else {
            btnUpdatePosition.setDisable(true);
            btnRemovePosition.setDisable(true);
            btnSearchPosition.setDisable(true);
        }
        bbSaveCancel.setDisable(true);
        gpBasic.setDisable(true);
    }

    @FXML
    private void handleBtnRemovePositionPress(ActionEvent event) {
        Optional.ofNullable(tvPositions.getSelectionModel().getSelectedItem()).ifPresent((position) -> {
            int result = positionsDAO.delete(position);
            if (result > 0) {
                tvPositions.getItems().remove(position);
            }
            clearControls();
        });
    }


    @FXML
    private void handleBtnSearchPositionPress() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PATH_TO_POSITIONS_SEARCH));
        AnchorPane apSearchPane = fxmlLoader.load();
        PositionsSearchController positionsSearchController = fxmlLoader.getController();
        positionsSearchController.setDAOFactory(daoFactory);
        Tab tab = new Tab("Поиск в таблице \"Должности\"", apSearchPane);
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
    private void handleBtnUpdatePositionPress(ActionEvent event) {
        Optional.ofNullable(tvPositions.getSelectionModel().getSelectedItem()).ifPresent((position) -> {
            bbSaveCancel.setDisable(false);
            gpBasic.setDisable(false);
            btnAddPosition.setDisable(true);
            btnUpdatePosition.setDisable(true);
            btnRemovePosition.setDisable(true);
            btnSearchPosition.setDisable(true);
            fillControls(position);
        });
    }


    @FXML
    private void handleBtnSavePress(ActionEvent event) {
        Long id = lbId.getText() == null || lbId.getText().matches("") ? null : Long.parseLong(lbId.getText());
        String name = tfName.getText();
        BigDecimal minimumPaymentPerDay = tfMinimumPaymentPerDay.getText() == null || tfMinimumPaymentPerDay.getText().matches("") ? null : new BigDecimal(tfMinimumPaymentPerDay.getText());
        if (id == null) {
            Positions position = new Positions(id, name, minimumPaymentPerDay);
            int result = positionsDAO.add(position);
            if (result > 0) {
                tvPositions.getItems().add(position);
            }
        } else {
            Positions position = new Positions(id, name, minimumPaymentPerDay);
            int result = positionsDAO.update(position);
            if (result > 0) {
                tvPositions.getItems().set(tvPositions.getSelectionModel().getSelectedIndex(), position);
            }
        }
        gpBasic.setDisable(true);
        bbSaveCancel.setDisable(true);
        btnAddPosition.setDisable(false);
        btnUpdatePosition.setDisable(false);
        btnRemovePosition.setDisable(false);
        btnSearchPosition.setDisable(false);
        gpBasic.setDisable(true);
        bbSaveCancel.setDisable(true);
    }


    private void fillControls(Positions position) {
        lbId.setText(position.getId() == null ? "" : position.getId().toString());
        tfName.setText(position.getName());
        tfMinimumPaymentPerDay.setText(position.getMinimumPaymentPerDay() == null ? null : position.getMinimumPaymentPerDay().toString());
    }

    private void clearControls() {
        lbId.setText("");
        tfName.setText("");
        tfMinimumPaymentPerDay.setText("");
    }
}
