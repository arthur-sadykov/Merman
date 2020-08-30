package merman.references.pricesdiscounts;

import merman.util.dao.DAOFactory;
import merman.util.dao.interfaces.DiscountsDAO;
import merman.util.model.Discounts;
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
public class DiscountsController implements Initializable {

    private static final String PATH_TO_DISCOUNTS_SEARCH = "/merman/references/pricesdiscounts/discounts-search.fxml";
    @FXML
    private Button btnAddDiscount;
    @FXML
    private Button btnRemoveDiscount;

    @FXML
    private Button btnSearchDiscount;
    @FXML
    private Button btnUpdateDiscount;

    private DAOFactory daoFactory;
    private DiscountsDAO discountDAO;
    @FXML
    private ButtonBar bbSaveCancel;
    @FXML
    private GridPane gpBasic;
    @FXML
    private MenuItem handleMiCardPrintPress;
    @FXML
    private MenuItem handleMiRegistryPrintPress;
    @FXML
    private Label lbId;
    @FXML
    private TableColumn<Discounts, StringProperty> tcName;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfRate;
    private TabPane tpContentPane;
    @FXML
    private TableView<Discounts> tvDiscounts;

    public void setDAOFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<Discounts> list = FXCollections.observableArrayList((discount) -> new Observable[]{discount.idProperty(), discount.nameProperty(), discount.rateProperty()});
        list.addListener((ListChangeListener.Change<? extends Discounts> change) -> {
            while (change.next()) {
                if (change.wasRemoved()) {
                    if (list.isEmpty()) {
                        btnUpdateDiscount.setDisable(true);
                        btnRemoveDiscount.setDisable(true);
                        btnSearchDiscount.setDisable(true);
                    }
                }
                if (change.wasAdded()) {
                    btnUpdateDiscount.setDisable(false);
                    btnRemoveDiscount.setDisable(false);
                    btnSearchDiscount.setDisable(false);
                }
            }
        });
        tvDiscounts.setItems(list);
        tvDiscounts.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> Optional.ofNullable(tvDiscounts.getSelectionModel().getSelectedItem()).ifPresent((discount) -> {
            long id = discount.getId();
            if (id == 1) {
                btnAddDiscount.setDisable(false);
                btnUpdateDiscount.setDisable(true);
                btnRemoveDiscount.setDisable(true);
                bbSaveCancel.setDisable(true);
                gpBasic.setDisable(true);
            } else {
                btnUpdateDiscount.setDisable(false);
                btnRemoveDiscount.setDisable(false);
                bbSaveCancel.setDisable(false);
                gpBasic.setDisable(false);
            }
            fillControls(discount);
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
        discountDAO = daoFactory.getDiscountsDAO();
        this.tpContentPane = tpContentPane;
        tvDiscounts.getItems().addAll(discountDAO.list());
        if (!tvDiscounts.getItems().isEmpty()) {
            btnUpdateDiscount.setDisable(false);
            btnRemoveDiscount.setDisable(false);
            btnSearchDiscount.setDisable(false);
            tvDiscounts.getSelectionModel().selectFirst();
        } else {
            btnUpdateDiscount.setDisable(true);
            btnRemoveDiscount.setDisable(true);
            btnSearchDiscount.setDisable(true);
        }
    }

    @FXML
    private void handleBtnAddDiscountPress(ActionEvent event) {
        bbSaveCancel.setDisable(false);
        gpBasic.setDisable(false);
        btnAddDiscount.setDisable(true);
        btnUpdateDiscount.setDisable(true);
        btnRemoveDiscount.setDisable(true);
        btnSearchDiscount.setDisable(true);
        clearControls();
    }

    @FXML
    private void handleBtnCancelPress(ActionEvent event) {
        btnAddDiscount.setDisable(false);
        if (!tvDiscounts.getItems().isEmpty()) {
            btnUpdateDiscount.setDisable(false);
            btnRemoveDiscount.setDisable(false);
            btnSearchDiscount.setDisable(false);
            tvDiscounts.getSelectionModel().selectFirst();
        } else {
            btnUpdateDiscount.setDisable(true);
            btnRemoveDiscount.setDisable(true);
            btnSearchDiscount.setDisable(true);
        }
        bbSaveCancel.setDisable(true);
        gpBasic.setDisable(true);
    }

    @FXML
    private void handleBtnUpdateDiscountPress(ActionEvent event) {
        Optional.ofNullable(tvDiscounts.getSelectionModel().getSelectedItem()).ifPresent((discount) -> {
            bbSaveCancel.setDisable(false);
            gpBasic.setDisable(false);
            btnAddDiscount.setDisable(true);
            btnUpdateDiscount.setDisable(true);
            btnRemoveDiscount.setDisable(true);
            btnSearchDiscount.setDisable(true);
            fillControls(discount);
        });
    }

    @FXML
    private void handleBtnRemoveDiscountPress(ActionEvent event) {
        Optional.ofNullable(tvDiscounts.getSelectionModel().getSelectedItem()).ifPresent((discount) -> {
            int result = discountDAO.delete(discount);
            if (result > 0) {
                tvDiscounts.getItems().remove(discount);
            }
            clearControls();
        });
    }

    @FXML
    private void handleBtnSavePress(ActionEvent event) {
        Long id = lbId.getText() == null || lbId.getText().matches("") ? null : Long.parseLong(lbId.getText());
        String name = tfName.getText();
        BigDecimal rate = tfRate.getText() == null || tfRate.getText().matches("") ? null : new BigDecimal(tfRate.getText());
        if (id == null) {
            Discounts discount = new Discounts(id, name, rate);
            int result = discountDAO.add(discount);
            if (result > 0) {
                tvDiscounts.getItems().add(discount);
            }
        } else {
            Discounts discount = new Discounts(id, name, rate);
            int result = discountDAO.update(discount);
            if (result > 0) {
                tvDiscounts.getItems().set(tvDiscounts.getSelectionModel().getSelectedIndex(), discount);
            }
        }
        gpBasic.setDisable(true);
        bbSaveCancel.setDisable(true);
        btnAddDiscount.setDisable(false);
        btnUpdateDiscount.setDisable(false);
        btnRemoveDiscount.setDisable(false);
        btnSearchDiscount.setDisable(false);
        gpBasic.setDisable(true);
        bbSaveCancel.setDisable(true);
    }

    @FXML
    private void handleBtnSearchDiscountPress() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PATH_TO_DISCOUNTS_SEARCH));
        AnchorPane apSearchPane = fxmlLoader.load();
        DiscountsSearchController discountsSearchController = fxmlLoader.getController();
        discountsSearchController.setDAOFactory(daoFactory);
        Tab tab = new Tab("Поиск в таблице \"Скидки\"", apSearchPane);
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


    private void fillControls(Discounts discount) {
        lbId.setText(discount.getId() == null ? "" : discount.getId().toString());
        tfName.setText(discount.getName());
        tfRate.setText(discount.getRate() == null ? null : discount.getRate().toString());
    }

    private void clearControls() {
        lbId.setText("");
        tfName.setText("");
        tfRate.setText("");
    }
}
