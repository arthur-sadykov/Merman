package merman.references.currenciescashesbanks;

import merman.util.dao.DAOFactory;
import merman.util.dao.interfaces.BanksDAO;
import merman.util.model.Banks;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
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
public class BanksController implements Initializable {

    private static final String PATH_TO_BANKS_SEARCH = "/merman/references/currenciescashesbanks/banks-search.fxml";
    @FXML
    private MenuItem handleMiCardPrintPress;
    @FXML
    private MenuItem handleMiRegistryPrintPress;
    @FXML
    private TableColumn<Banks, String> tcBIK;
    @FXML
    private TableColumn<Banks, String> tcCorrespondentAccount;
    @FXML
    private TableColumn<Banks, String> tcCity;
    @FXML
    private TableColumn<Banks, String> tcName;
    @FXML
    private TextField tfBIK;
    @FXML
    private TextField tfCorrespondentAccount;
    @FXML
    private TextField tfName;
    @FXML
    private TableView<Banks> tvBanks;
    @FXML
    private Button btnAddBank;
    @FXML
    private Button btnUpdateBank;
    @FXML
    private Button btnRemoveBank;
    @FXML
    private Button btnSearchBank;
    @FXML
    private ButtonBar bbSaveCancel;
    @FXML
    private GridPane gpBasic;
    @FXML
    private Label lbId;
    private BanksDAO banksDAO;
    private DAOFactory daoFactory;
    @FXML
    private TextField tfCity;
    private TabPane tpContentPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<Banks> list = FXCollections.observableArrayList((banks) -> new Observable[]{banks.idProperty(), banks.nameProperty(), banks.BIKProperty(), banks.correspondentAccountProperty(), banks.cityProperty()});
        tvBanks.setItems(list);
        tvBanks.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> Optional.ofNullable(tvBanks.getSelectionModel().getSelectedItem()).ifPresent((bank) -> {
            long id = bank.getId();
            if (id == 1) {
                btnAddBank.setDisable(false);
                btnUpdateBank.setDisable(true);
                btnRemoveBank.setDisable(true);
                bbSaveCancel.setDisable(true);
                gpBasic.setDisable(true);
            } else {
                btnUpdateBank.setDisable(false);
                btnRemoveBank.setDisable(false);
                bbSaveCancel.setDisable(true);
                gpBasic.setDisable(true);
            }
            fillControls(bank);
        }));
    }

    public void setupAfterInitialize(TabPane tpContentPane) {
        this.tpContentPane = tpContentPane;
        banksDAO = daoFactory.getBanksDAO();
        tvBanks.getItems().addAll(banksDAO.list());
    }

    public void setDAOFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @FXML
    private void handleBtnAddBankPress(ActionEvent event) {
        bbSaveCancel.setDisable(false);
        gpBasic.setDisable(false);
        btnAddBank.setDisable(true);
        btnUpdateBank.setDisable(true);
        btnRemoveBank.setDisable(true);
        btnSearchBank.setDisable(true);
        clearControls();
    }

    @FXML
    private void handleBtnCancelPress(ActionEvent event) {
        btnAddBank.setDisable(false);
        if (!tvBanks.getItems().isEmpty()) {
            btnUpdateBank.setDisable(false);
            btnRemoveBank.setDisable(false);
            btnSearchBank.setDisable(false);
            tvBanks.getSelectionModel().selectFirst();
        } else {
            btnUpdateBank.setDisable(true);
            btnRemoveBank.setDisable(true);
            btnSearchBank.setDisable(true);
        }
        bbSaveCancel.setDisable(true);
        gpBasic.setDisable(true);
    }


    @FXML
    private void handleBtnRemoveBankPress(ActionEvent event) {
        Optional.ofNullable(tvBanks.getSelectionModel().getSelectedItem()).ifPresent((bank) -> {
            int result = banksDAO.delete(bank);
            if (result > 0) {
                tvBanks.getItems().remove(bank);
            }
            clearControls();
        });
    }

    @FXML
    private void handleBtnSavePress(ActionEvent event) {
        Long id = lbId.getText() == null || lbId.getText().matches("") ? null : Long.parseLong(lbId.getText());
        String name = tfName.getText();
        String BIK = tfBIK.getText();
        String correspondentAccount = tfCorrespondentAccount.getText();
        String city = tfCity.getText();
        if (id == null) {
            Banks bank = new Banks(id, name, BIK, correspondentAccount, city);
            int result = banksDAO.add(bank);
            if (result > 0) {
                tvBanks.getItems().add(bank);
            }
        } else {
            Banks bank = new Banks(id, name, BIK, correspondentAccount, city);
            int result = banksDAO.update(bank);
            if (result > 0) {
                tvBanks.getItems().set(tvBanks.getSelectionModel().getSelectedIndex(), bank);
            }
        }
        btnAddBank.setDisable(false);
        btnUpdateBank.setDisable(false);
        btnRemoveBank.setDisable(false);
        btnSearchBank.setDisable(false);
        gpBasic.setDisable(true);
        bbSaveCancel.setDisable(true);
    }

    @FXML
    private void handleBtnSearchBankPress() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PATH_TO_BANKS_SEARCH));
        AnchorPane apSearchPane = fxmlLoader.load();
        BanksSearchController banksSearchController = fxmlLoader.getController();
        banksSearchController.setDAOFactory(daoFactory);
        Tab tab = new Tab("Поиск в таблице \"Банки\"", apSearchPane);
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
    private void handleBtnUpdateBankPress(ActionEvent event) {
        Optional.ofNullable(tvBanks.getSelectionModel().getSelectedItem()).ifPresent((bank) -> {
            bbSaveCancel.setDisable(false);
            gpBasic.setDisable(false);
            btnAddBank.setDisable(true);
            btnUpdateBank.setDisable(true);
            btnRemoveBank.setDisable(true);
            btnSearchBank.setDisable(true);
            fillControls(bank);
        });
    }


    private void fillControls(Banks bank) {
        lbId.setText(bank.getId() == null ? "" : bank.getId().toString());
        tfName.setText(bank.getName());
        tfBIK.setText(bank.getBIK());
        tfCorrespondentAccount.setText(bank.getCorrespondentAccount());
        tfCity.setText(bank.getCity());
    }

    private void clearControls() {
        lbId.setText("");
        tfName.setText("");
        tfBIK.setText("");
        tfCorrespondentAccount.setText("");
        tfCity.setText("");
    }
}
