package merman.references.companystructure;

import merman.util.dao.DAOFactory;
import merman.util.dao.interfaces.BanksDAO;
import merman.util.dao.interfaces.EmployeesDAO;
import merman.util.dao.interfaces.FirmsBankAccountsDAO;
import merman.util.dao.interfaces.FirmsDAO;
import merman.util.model.Firms;
import merman.util.model.FirmsBankAccounts;
import javafx.beans.Observable;
import javafx.beans.property.LongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author Arthur Sadykov
 */
public class FirmsController implements Initializable {

    private static final String PATH_TO_FIRMS_BANK_ACCOUNTS = "/merman/references/companystructure/firms-bank-accounts.fxml";
    private static final String PATH_TO_FIRMS_SEARCH = "/merman/references/companystructure/firms-search.fxml";

    private FirmsDAO firmsDAO;
    private DAOFactory daoFactory;
    @FXML
    private ButtonBar bbSaveCancel;
    @FXML
    private Button btnAddFirm;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnClear;
    @FXML
    private Button btnLoadLogotype;
    @FXML
    private Button btnMagnify;
    @FXML
    private Button btnRemoveFirm;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnSearchFirm;
    @FXML
    private Button btnUpdateFirm;
    @FXML
    private ComboBox<LongProperty> cbChiefAccountant;
    @FXML
    private ComboBox<LongProperty> cbDirector;
    @FXML
    private GridPane gpAddress;
    @FXML
    private GridPane gpAdministration;
    @FXML
    private GridPane gpBasic;
    @FXML
    private GridPane gpCodes;
    @FXML
    private GridPane gpLogotype;
    @FXML
    private ImageView ivLogo;
    @FXML
    private Label lbId;


    @FXML
    private SplitPane spSplitPane;
    @FXML
    private TableColumn<Firms, String> tcInn;
    @FXML
    private TableColumn<Firms, String> tcName;
    @FXML
    private TextField tfActingOnTheBasisOf;
    @FXML
    private TextField tfAdditionalInformation;
    @FXML
    private TextField tfApartmentsOffice;
    @FXML
    private TextField tfCertificateOfIndividualEntrepreneur;
    @FXML
    private TextField tfCity;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfFullName;
    @FXML
    private TextField tfHouse;
    @FXML
    private TextField tfHousing;
    @FXML
    private TextField tfIdentifierSED;
    @FXML
    private TextField tfInFaceOf;
    @FXML
    private TextField tfInn;
    @FXML
    private TextField tfKpp;
    @FXML
    private TextField tfLegalAddress;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfOgrn;
    @FXML
    private TextField tfOkpo;
    @FXML
    private TextField tfOkved;
    @FXML
    private TextField tfPhone;
    @FXML
    private TextField tfPhysicalAddress;
    @FXML
    private TextField tfPostcode;
    @FXML
    private TextField tfStreet;
    @FXML
    private TabPane tpRightTabPane;
    @FXML
    private TableView<Firms> tvFirms;
    @FXML
    private Button btnSearchBankAccount;
    @FXML
    private TableView<FirmsBankAccounts> tvBankAccounts;
    @FXML
    private TableColumn<FirmsBankAccounts, String> tcNumber;
    @FXML
    private TableColumn<FirmsBankAccounts, Number> tcBank;
    @FXML
    private TableColumn<FirmsBankAccounts, Boolean> tcMain;
    @FXML
    private Button btnDeleteBankAccount;
    @FXML
    private Button btnNewBankAccount;
    private Firms currentFirm;
    private TabPane tpContentPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<Firms> list = FXCollections.observableArrayList((firms) -> new Observable[]{firms.idProperty(), firms.nameProperty(), firms.fullNameProperty(), firms.phoneProperty(), firms.emailProperty(), firms.physicalAddressProperty(), firms.legalAddressProperty(),
                                                                                                   firms.identifierSEDProperty(), firms.directorProperty(), firms.chiefAccountantProperty(), firms.certificateOfIndividualEntrepreneurProperty(), firms.inFaceOfProperty(),
                                                                                                   firms.actingOnTheBasisOfProperty(), firms.additionalInformationProperty(), firms.innProperty(), firms.kppProperty(), firms.ogrnProperty(), firms.okpoProperty(), firms.okvedProperty(),
                                                                                                   firms.postcodeProperty(), firms.cityProperty(), firms.streetProperty(), firms.houseProperty(), firms.housingProperty(), firms.apartmentsOfficeProperty(), firms.logoProperty()});
        tvFirms.setItems(list);
        tvFirms.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> Optional.ofNullable(tvFirms.getSelectionModel().getSelectedItem()).ifPresent((firm) -> {
            currentFirm = firm;
            fillControls(firm);
        }));
        StringConverter<LongProperty> directorStringConverter = new StringConverter<>() {
            @Override
            public LongProperty fromString(String string) {
                return null;
            }

            @Override
            public String toString(LongProperty t) {
                EmployeesDAO employeesDAO = daoFactory.getEmployeesDAO();
                return Optional.ofNullable(employeesDAO.get(t.longValue())).map((employee) -> employee.getSurname() + " " + employee.getName() + " " + employee.getPatronymic()).orElse(null);
            }
        };
        cbDirector.setConverter(directorStringConverter);
        StringConverter<LongProperty> chiefAccountantStringConverter = new StringConverter<>() {
            @Override
            public LongProperty fromString(String string) {
                return null;
            }

            @Override
            public String toString(LongProperty t) {
                EmployeesDAO employeesDAO = daoFactory.getEmployeesDAO();
                return Optional.ofNullable(employeesDAO.get(t.longValue())).map((employee) -> employee.getSurname() + " " + employee.getName() + " " + employee.getPatronymic()).orElse(null);
            }
        };
        cbChiefAccountant.setConverter(chiefAccountantStringConverter);
        tcBank.setCellFactory((col) -> new TableCell<>() {
            @Override
            public void updateItem(Number bank, boolean empty) {
                super.updateItem(bank, empty);
                if (empty || bank == null) {
                    setText(null);
                } else {
                    BanksDAO banksDAO = daoFactory.getBanksDAO();
                    Optional.ofNullable(bank.longValue()).map(banksDAO::get).ifPresent((b) -> setText(b.getName()));
                }
            }
        });
        tcMain.setCellFactory((col) -> new TableCell<>() {
            @Override
            public void updateItem(Boolean main, boolean empty) {
                super.updateItem(main, empty);
                if (empty || main == null) {
                    setText(null);
                } else {
                    this.setText(main ? "Да" : "Нет");
                }
            }
        });
    }

    public void setupAfterInitialize(TabPane tpContentPane) {
        this.tpContentPane = tpContentPane;
        firmsDAO = daoFactory.getFirmsDAO();
        EmployeesDAO employeesDAO = daoFactory.getEmployeesDAO();
        employeesDAO.list().forEach((employee) -> {
            cbChiefAccountant.getItems().add(employee.idProperty());
            cbDirector.getItems().add(employee.idProperty());
        });
        tvFirms.getItems().addAll(firmsDAO.list());
        FirmsBankAccountsDAO firmsBankAccountsDAO = daoFactory.getFirmsBankAccountsDAO();
        tvBankAccounts.getItems().addAll(firmsBankAccountsDAO.list());
    }

    public void setDAOFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @FXML
    private void handleBtnAddFirmPress(ActionEvent event) {
        bbSaveCancel.setDisable(false);
        gpAddress.setDisable(false);
        gpAdministration.setDisable(false);
        gpBasic.setDisable(false);
        gpCodes.setDisable(false);
        gpLogotype.setDisable(false);
        btnAddFirm.setDisable(true);
        btnUpdateFirm.setDisable(true);
        btnRemoveFirm.setDisable(true);
        btnSearchFirm.setDisable(true);
        clearControls();
    }

    @FXML
    private void handleBtnCancelPress(ActionEvent event) {
        btnAddFirm.setDisable(false);
        if (!tvFirms.getItems().isEmpty()) {
            btnUpdateFirm.setDisable(false);
            btnRemoveFirm.setDisable(false);
            btnSearchFirm.setDisable(false);
            tvFirms.getSelectionModel().selectFirst();
        } else {
            btnUpdateFirm.setDisable(true);
            btnRemoveFirm.setDisable(true);
            btnSearchFirm.setDisable(true);
        }
        bbSaveCancel.setDisable(true);
        gpBasic.setDisable(true);
    }

    @FXML
    private void handleBtnClearLogotypePress(ActionEvent event) {
    }

    @FXML
    private void handleBtnLoadLogotypePress(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Графические файлы", "*.png", "*.jpg ", ".gif"));
        File file = fileChooser.showOpenDialog(btnLoadLogotype.getScene().getWindow());
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            ivLogo.setImage(image);
        }
    }

    @FXML
    private void handleBtnMagnifyLogotypePress(ActionEvent event) {
    }

    @FXML
    private void handleBtnRemoveFirmPress(ActionEvent event) {
        Optional.ofNullable(tvFirms.getSelectionModel().getSelectedItem()).ifPresent((product) -> {
            int result = firmsDAO.delete(product);
            if (result > 0) {
                tvFirms.getItems().remove(product);
            }
            clearControls();
        });
    }

    @FXML
    private void handleBtnSavePress(ActionEvent event) {
        Long id = lbId.getText() == null || lbId.getText().matches("") ? null : Long.parseLong(lbId.getText());
        String name = tfName.getText();
        String fullName = tfFullName.getText();
        String phone = tfPhone.getText();
        String email = tfEmail.getText();
        String physicalAddress = tfPhysicalAddress.getText();
        String legalAddress = tfLegalAddress.getText();
        String identifierSED = tfIdentifierSED.getText();
        Long director = cbDirector.getValue() == null ? null : cbDirector.getValue().get();
        Long chiefAccountant = cbChiefAccountant.getValue() == null ? null : cbChiefAccountant.getValue().get();
        String certificateOfIndividualEntrepreneur = tfCertificateOfIndividualEntrepreneur.getText();
        String inFaceOf = tfInFaceOf.getText();
        String actingOnTheBasisOf = tfActingOnTheBasisOf.getText();
        String additionalInformation = tfAdditionalInformation.getText();
        String inn = tfInn.getText();
        String kpp = tfKpp.getText();
        String ogrn = tfOgrn.getText();
        String okpo = tfOkpo.getText();
        String okved = tfOkved.getText();
        String postcode = tfPostcode.getText();
        String city = tfCity.getText();
        String street = tfStreet.getText();
        String house = tfHouse.getText();
        String housing = tfHousing.getText();
        String apartmentsOffice = tfApartmentsOffice.getText();
        Image logo = ivLogo.getImage();
        if (id == null) {
            Firms firm = new Firms(id, name, fullName, phone, email, physicalAddress, legalAddress, identifierSED, director, chiefAccountant, certificateOfIndividualEntrepreneur, inFaceOf, actingOnTheBasisOf, additionalInformation, inn, kpp, ogrn, okpo, okved, postcode, city, street, house, housing, apartmentsOffice, logo);
            int result = firmsDAO.add(firm);
            if (result > 0) {
                tvFirms.getItems().add(firm);
            }
        } else {
            Firms firm = new Firms(id, name, fullName, phone, email, physicalAddress, legalAddress, identifierSED, director, chiefAccountant, certificateOfIndividualEntrepreneur, inFaceOf, actingOnTheBasisOf, additionalInformation, inn, kpp, ogrn, okpo, okved, postcode, city, street, house, housing, apartmentsOffice, logo);
            int result = firmsDAO.update(firm);
            if (result > 0) {
                tvFirms.getItems().set(tvFirms.getSelectionModel().getSelectedIndex(), firm);
            }
        }
        FirmsBankAccountsDAO firmsBankAccountsDAO = daoFactory.getFirmsBankAccountsDAO();
        tvBankAccounts.getItems().forEach(bankAccount -> {
            if (!firmsBankAccountsDAO.exists(bankAccount)) {
                firmsBankAccountsDAO.add(bankAccount);
            } else {
                firmsBankAccountsDAO.update(bankAccount);
            }
        });
        gpBasic.setDisable(true);
        bbSaveCancel.setDisable(true);
        btnAddFirm.setDisable(false);
        btnUpdateFirm.setDisable(false);
        btnRemoveFirm.setDisable(false);
        btnSearchFirm.setDisable(false);
        gpBasic.setDisable(true);
        gpAddress.setDisable(true);
        gpAdministration.setDisable(true);
        gpCodes.setDisable(true);
        gpLogotype.setDisable(true);
        bbSaveCancel.setDisable(true);
    }

    @FXML
    private void handleBtnSearchFirmPress() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PATH_TO_FIRMS_SEARCH));
        AnchorPane apSearchPane = fxmlLoader.load();
        FirmsSearchController firmsSearchController = fxmlLoader.getController();
        firmsSearchController.setDAOFactory(daoFactory);
        Tab tab = new Tab("Поиск в таблице \"Фирмы\"", apSearchPane);
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
    private void handleBtnUpdateFirmPress(ActionEvent event) {
        Optional.ofNullable(tvFirms.getSelectionModel().getSelectedItem()).ifPresent((firm) -> {
            bbSaveCancel.setDisable(false);
            gpBasic.setDisable(false);
            gpAddress.setDisable(false);
            gpAdministration.setDisable(false);
            gpCodes.setDisable(false);
            gpLogotype.setDisable(false);
            btnAddFirm.setDisable(true);
            btnUpdateFirm.setDisable(true);
            btnRemoveFirm.setDisable(true);
            btnSearchFirm.setDisable(true);
            fillControls(firm);
        });
    }


    private void fillControls(Firms firm) {
        lbId.setText(firm.getId() == null ? "" : firm.getId().toString());
        tfName.setText(firm.getName());
        tfFullName.setText(firm.getFullName());
        tfPhone.setText(firm.getPhone());
        tfEmail.setText(firm.getEmail());
        tfPhysicalAddress.setText(firm.getPhysicalAddress());
        tfLegalAddress.setText(firm.getLegalAddress());
        tfIdentifierSED.setText(firm.getIdentifierSED());
        cbDirector.setValue(firm.directorProperty());
        cbChiefAccountant.setValue(firm.chiefAccountantProperty());
        tfCertificateOfIndividualEntrepreneur.setText(firm.getCertificateOfIndividualEntrepreneur());
        tfInFaceOf.setText(firm.getInFaceOf());
        tfActingOnTheBasisOf.setText(firm.getActingOnTheBasisOf());
        tfAdditionalInformation.setText(firm.getAdditionalInformation());
        tfInn.setText(firm.getInn());
        tfKpp.setText(firm.getKpp());
        tfOgrn.setText(firm.getOgrn());
        tfOkpo.setText(firm.getOkpo());
        tfOkved.setText(firm.getOkved());
        tfPostcode.setText(firm.getPostcode());
        tfCity.setText(firm.getCity());
        tfStreet.setText(firm.getStreet());
        tfHouse.setText(firm.getHouse());
        tfHousing.setText(firm.getHousing());
        tfApartmentsOffice.setText(firm.getApartmentsOffice());
        ivLogo.setImage(firm.getLogo());
        FirmsBankAccountsDAO firmsBankAccountsDAO = daoFactory.getFirmsBankAccountsDAO();
        List<FirmsBankAccounts> firmsBankAccounts = firmsBankAccountsDAO.list();
        tvBankAccounts.getItems().clear();
        firmsBankAccounts.stream().filter(bankAccount -> bankAccount.getBasicTable() == firm.getId()).forEach(bA -> tvBankAccounts.getItems().add(bA));
    }

    private void clearControls() {
        lbId.setText("");
        tfName.setText("");
        tfFullName.setText("");
        tfPhone.setText("");
        tfEmail.setText("");
        tfPhysicalAddress.setText("");
        tfLegalAddress.setText("");
        tfIdentifierSED.setText("");
        cbDirector.getSelectionModel().selectFirst();
        cbChiefAccountant.getSelectionModel().selectFirst();
        tfCertificateOfIndividualEntrepreneur.setText("");
        tfInFaceOf.setText("");
        tfActingOnTheBasisOf.setText("");
        tfAdditionalInformation.setText("");
        tfInn.setText("");
        tfKpp.setText("");
        tfOgrn.setText("");
        tfOkpo.setText("");
        tfOkved.setText("");
        tfPostcode.setText("");
        tfCity.setText("");
        tfStreet.setText("");
        tfHouse.setText("");
        tfHousing.setText("");
        tfApartmentsOffice.setText("");
        ivLogo.setImage(null);
    }

    @FXML
    private void handleBtnNewBankAccountPress(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PATH_TO_FIRMS_BANK_ACCOUNTS));
        Stage stage = new Stage();
        Parent root = fxmlLoader.load();
        FirmsBankAccountsController firmsBankAccountsController = fxmlLoader.getController();
        firmsBankAccountsController.setDAOFactory(daoFactory);
        firmsBankAccountsController.setupAfterInitialize(this, currentFirm);
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.showAndWait();
    }

    @FXML
    private void handleBtnDeleteBankAccountPress(ActionEvent event) {
        Optional.ofNullable(tvBankAccounts.getSelectionModel().getSelectedItem()).ifPresent(bankAccount -> {
            FirmsBankAccountsDAO firmsBankAccountsDAO = daoFactory.getFirmsBankAccountsDAO();
            if (firmsBankAccountsDAO.exists(bankAccount)) {
                int deleted = firmsBankAccountsDAO.delete(bankAccount);
                if (deleted > 0) {
                    tvBankAccounts.getItems().remove(bankAccount);
                }
            } else {
                tvBankAccounts.getItems().remove(bankAccount);
            }
        });
    }

    @FXML
    private void handleBtnSearchBankAccountPress(ActionEvent event) {
    }

    public TableView<FirmsBankAccounts> getTvFirmsBankAccounts() {
        return tvBankAccounts;
    }
}
