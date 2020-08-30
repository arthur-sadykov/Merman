package merman.references.companystructure;

import merman.util.dao.DAOFactory;
import merman.util.dao.interfaces.BanksDAO;
import merman.util.dao.interfaces.FirmsBankAccountsDAO;
import merman.util.model.Banks;
import merman.util.model.Firms;
import merman.util.model.FirmsBankAccounts;
import javafx.beans.property.LongProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author Arthur Sadykov
 */
public class FirmsBankAccountsController implements Initializable {
    @FXML
    private GridPane gpBasic;
    @FXML
    private CheckBox chbMain;
    @FXML
    private TextField tfNumber;
    @FXML
    private ComboBox<LongProperty> cbBank;
    @FXML
    private ButtonBar bbSaveCancel;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;
    private DAOFactory daoFactory;
    private FirmsBankAccountsDAO dao;
    private FirmsController firmsController;
    private Firms firm;

    @FXML
    private void handleBtnSavePress(ActionEvent event) {
        ObservableList<FirmsBankAccounts> firmsBankAccounts = firmsController.getTvFirmsBankAccounts().getItems();
        String name = tfNumber.getText();
        Long bank = cbBank.getValue() == null ? null : cbBank.getValue().get();
        Boolean main = chbMain.isSelected();
        FirmsBankAccounts bankAccount = new FirmsBankAccounts(null, name, bank, main, firm.getId());
        firmsBankAccounts.add(bankAccount);
        ((Stage) (bbSaveCancel.getScene().getWindow())).close();
    }

    @FXML
    private void handleBtnCancelPress(ActionEvent event) {
        ((Stage) (bbSaveCancel.getScene().getWindow())).close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        StringConverter<LongProperty> bankStringConverter = new StringConverter<>() {
            @Override
            public LongProperty fromString(String string) {
                return null;
            }

            @Override
            public String toString(LongProperty t) {
                BanksDAO banksDAO = daoFactory.getBanksDAO();
                return Optional.ofNullable(banksDAO.get(t.longValue())).map(Banks::getName).orElse(null);
            }
        };
        cbBank.setConverter(bankStringConverter);
    }

    public void setDAOFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public void setupAfterInitialize(FirmsController firmsController, Firms firm) {
        this.firm = firm;
        FirmsBankAccountsDAO firmsBankAccountsDAO = daoFactory.getFirmsBankAccountsDAO();
        this.firmsController = firmsController;
        BanksDAO banksDAO = daoFactory.getBanksDAO();
        List<Banks> banks = banksDAO.list();
        banks.forEach(bank -> cbBank.getItems().add(bank.idProperty()));
    }
}
