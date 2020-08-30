package merman.references.currenciescashesbanks;

import merman.util.dao.DAOFactory;
import merman.util.dao.interfaces.CurrenciesDAO;
import merman.util.model.Currencies;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author Arthur Sadykov
 */
public class CurrenciesController implements Initializable {

    @FXML
    private MenuItem handleMiCardPrintPress;
    @FXML
    private MenuItem handleMiRegistryPrintPress;
    @FXML
    private TableColumn<Currencies, String> tcCurrencyCode;
    @FXML
    private TableColumn<Currencies, String> tcName;
    @FXML
    private TextField tfCurrencyShare;
    @FXML
    private TextField tfCurrencyCode;
    @FXML
    private TextField tfFullName;
    @FXML
    private TextField tfName;
    @FXML
    private TableView<Currencies> tvCurrencies;
    @FXML
    private Button btnSearchCurrency;
    @FXML
    private Label lbId;
    @FXML
    private Button btnAddCurrency;
    @FXML
    private Button btnUpdateCurrency;
    @FXML
    private Button btnRemoveCurrency;

    private DAOFactory daoFactory;
    @FXML
    private GridPane gpBasic;
    @FXML
    private ButtonBar bbSaveCancel;
    private CurrenciesDAO currencyDAO;


    public void setDAOFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<Currencies> list = FXCollections.observableArrayList((currencies) -> new Observable[]{currencies.idProperty(), currencies.nameProperty(), currencies.currencyCodeProperty(), currencies.fullNameProperty(), currencies.currencyShareProperty()});
        tvCurrencies.setItems(list);
        tvCurrencies.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> Optional.ofNullable(tvCurrencies.getSelectionModel().getSelectedItem()).ifPresent((currency) -> {
            long id = currency.getId();
            if (id == 1) {
                btnAddCurrency.setDisable(false);
                btnUpdateCurrency.setDisable(true);
                btnRemoveCurrency.setDisable(true);
                bbSaveCancel.setDisable(true);
                gpBasic.setDisable(true);
            } else {
                btnUpdateCurrency.setDisable(false);
                btnRemoveCurrency.setDisable(false);
                bbSaveCancel.setDisable(true);
                gpBasic.setDisable(true);
            }
            fillControls(currency);
        }));
    }

    public void setupAfterInitialize(TabPane tpContentPane) {
        currencyDAO = daoFactory.getCurrenciesDAO();
        tvCurrencies.getItems().addAll(currencyDAO.list());
        if (!tvCurrencies.getItems().isEmpty()) {
            btnUpdateCurrency.setDisable(false);
            btnRemoveCurrency.setDisable(false);
            btnSearchCurrency.setDisable(false);
            tvCurrencies.getSelectionModel().selectFirst();
        } else {
            btnUpdateCurrency.setDisable(true);
            btnRemoveCurrency.setDisable(true);
            btnSearchCurrency.setDisable(true);
        }
    }

    @FXML
    private void handleBtnAddCurrencyPress(ActionEvent event) {
        bbSaveCancel.setDisable(false);
        gpBasic.setDisable(false);
        btnAddCurrency.setDisable(true);
        btnUpdateCurrency.setDisable(true);
        btnRemoveCurrency.setDisable(true);
        btnSearchCurrency.setDisable(true);
        clearControls();
    }

    @FXML
    private void handleBtnCancelPress(ActionEvent event) {
        btnAddCurrency.setDisable(false);
        if (!tvCurrencies.getItems().isEmpty()) {
            btnUpdateCurrency.setDisable(false);
            btnRemoveCurrency.setDisable(false);
            btnSearchCurrency.setDisable(false);
            tvCurrencies.getSelectionModel().selectFirst();
        } else {
            btnUpdateCurrency.setDisable(true);
            btnRemoveCurrency.setDisable(true);
            btnSearchCurrency.setDisable(true);
        }
        bbSaveCancel.setDisable(true);
        gpBasic.setDisable(true);
    }

    @FXML
    private void handleBtnUpdateCurrencyPress(ActionEvent event) {
        Optional.ofNullable(tvCurrencies.getSelectionModel().getSelectedItem()).ifPresent((currency) -> {
            bbSaveCancel.setDisable(false);
            gpBasic.setDisable(false);
            btnAddCurrency.setDisable(true);
            btnUpdateCurrency.setDisable(true);
            btnRemoveCurrency.setDisable(true);
            btnSearchCurrency.setDisable(true);
            fillControls(currency);
        });
    }

    @FXML
    private void handleBtnRemoveCurrencyPress(ActionEvent event) {
        Optional.ofNullable(tvCurrencies.getSelectionModel().getSelectedItem()).ifPresent((currency) -> {
            int result = currencyDAO.delete(currency);
            if (result > 0) {
                tvCurrencies.getItems().remove(currency);
            }
            clearControls();
        });
    }

    @FXML
    private void handleBtnSavePress(ActionEvent event) {
        Long id = lbId.getText() == null || lbId.getText().matches("") ? null : Long.parseLong(lbId.getText());
        String name = tfName.getText();
        String currencyCode = tfCurrencyCode.getText();
        String fullName = tfFullName.getText();
        String currencyShare = tfCurrencyShare.getText();
        if (id == null) {
            Currencies currency = new Currencies(id, name, currencyCode, fullName, currencyShare);
            int result = currencyDAO.add(currency);
            if (result > 0) {
                tvCurrencies.getItems().add(currency);
            }
        } else {
            Currencies currency = new Currencies(id, name, currencyCode, fullName, currencyShare);
            int result = currencyDAO.update(currency);
            if (result > 0) {
                tvCurrencies.getItems().set(tvCurrencies.getSelectionModel().getSelectedIndex(), currency);
            }
        }
        gpBasic.setDisable(true);
        bbSaveCancel.setDisable(true);
        btnAddCurrency.setDisable(false);
        btnUpdateCurrency.setDisable(false);
        btnRemoveCurrency.setDisable(false);
        btnSearchCurrency.setDisable(false);
        gpBasic.setDisable(true);
        bbSaveCancel.setDisable(true);
    }

    @FXML
    private void handleBtnSearchCurrencyPress(ActionEvent event) {
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PATH_TO_DISCOUNTS_SEARCH));
//        AnchorPane apSearchPane = fxmlLoader.load();
//        CurrenciesSearchController currenciesSearchController = fxmlLoader.getController();
//        currenciesSearchController.setDAOFactory(daoFactory);
//        Tab tab = new Tab("Поиск в таблице \"Скидки\"", apSearchPane);
//        tpContentPane.getTabs().add(tab);
//        tpContentPane.getSelectionModel().select(tab);
    }


    private void fillControls(Currencies currency) {
        lbId.setText(currency.getId() == null ? "" : currency.getId().toString());
        tfName.setText(currency.getName());
        tfCurrencyCode.setText(currency.getCurrencyCode());
        tfFullName.setText(currency.getFullName());
        tfCurrencyShare.setText(currency.getCurrencyShare());
    }

    private void clearControls() {
        lbId.setText("");
        tfName.setText("");
        tfCurrencyCode.setText("");
        tfFullName.setText("");
        tfCurrencyShare.setText("");
    }
}
