package merman.references.pricesdiscounts;

import merman.util.dao.DAOFactory;
import merman.util.dao.interfaces.CurrenciesDAO;
import merman.util.dao.interfaces.TypesOfPricesDAO;
import merman.util.model.Currencies;
import merman.util.model.TypesOfPrices;
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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author Arthur Sadykov
 */
public class TypesOfPricesController implements Initializable {

    private static final String PATH_TO_TYPES_OF_PRICES_SEARCH = "/merman/references/pricesdiscounts/types-of-prices-search.fxml";
    @FXML
    private ComboBox<LongProperty> cbCurrency;
    @FXML
    private CheckBox chbVatIncluded;

    private DAOFactory daoFactory;

    private TypesOfPricesDAO typesOfPricesDAO;
    private TabPane tpContentPane;
    @FXML
    private ButtonBar bbSaveCancel;

    @FXML
    private Button btnAddTypeOfPrices;
    @FXML
    private Button btnRemoveTypeOfPrices;

    @FXML
    private Button btnSearchTypeOfPrices;
    @FXML
    private Button btnUpdateTypeOfPrices;
    @FXML
    private GridPane gpBasic;
    @FXML
    private MenuItem handleMiCardPrintPress;
    @FXML
    private MenuItem handleMiRegistryPrintPress;
    @FXML
    private Label lbId;
    @FXML
    private TableColumn<TypesOfPrices, StringProperty> tcName;
    @FXML
    private TextField tfName;
    @FXML
    private TableView<TypesOfPrices> tvTypesOfPrices;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<TypesOfPrices> list = FXCollections.observableArrayList((typesOfPrices) -> new Observable[]{typesOfPrices.idProperty(), typesOfPrices.nameProperty(), typesOfPrices.vatIncludedProperty(), typesOfPrices.currencyProperty()});
        list.addListener((ListChangeListener.Change<? extends TypesOfPrices> change) -> {
            while (change.next()) {
                if (change.wasRemoved()) {
                    if (list.isEmpty()) {
                        btnUpdateTypeOfPrices.setDisable(true);
                        btnRemoveTypeOfPrices.setDisable(true);
                        btnSearchTypeOfPrices.setDisable(true);
                    }
                }
                if (change.wasAdded()) {
                    btnUpdateTypeOfPrices.setDisable(false);
                    btnRemoveTypeOfPrices.setDisable(false);
                    btnSearchTypeOfPrices.setDisable(false);
                }
            }
        });
        tvTypesOfPrices.setItems(list);
        tvTypesOfPrices.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> Optional.ofNullable(tvTypesOfPrices.getSelectionModel().getSelectedItem()).ifPresent((typesOfPricess) -> {
            long id = typesOfPricess.getId();
            if (id == 1 || id == 2) {
                btnAddTypeOfPrices.setDisable(false);
                btnRemoveTypeOfPrices.setDisable(true);
                bbSaveCancel.setDisable(true);
                gpBasic.setDisable(true);
            } else {
                btnRemoveTypeOfPrices.setDisable(false);
                bbSaveCancel.setDisable(false);
                gpBasic.setDisable(false);
            }
            fillControls(typesOfPricess);
        }));
        StringConverter<LongProperty> currencyStringConverter = new StringConverter<>() {
            @Override
            public LongProperty fromString(String string) {
                return null;
            }

            @Override
            public String toString(LongProperty t) {
                CurrenciesDAO currenciesDAO = daoFactory.getCurrenciesDAO();
                return Optional.ofNullable(currenciesDAO.get(t.longValue())).map(Currencies::getName).orElse(null);
            }
        };
        cbCurrency.setConverter(currencyStringConverter);
    }

    public void setupAfterInitialize(TabPane tpContentPane) {
        typesOfPricesDAO = daoFactory.getTypesOfPricesDAO();
        this.tpContentPane = tpContentPane;
        tvTypesOfPrices.getItems().addAll(typesOfPricesDAO.list());
        if (!tvTypesOfPrices.getItems().isEmpty()) {
            btnUpdateTypeOfPrices.setDisable(false);
            btnRemoveTypeOfPrices.setDisable(false);
            btnSearchTypeOfPrices.setDisable(false);
            tvTypesOfPrices.getSelectionModel().selectFirst();
        } else {
            btnUpdateTypeOfPrices.setDisable(true);
            btnRemoveTypeOfPrices.setDisable(true);
            btnSearchTypeOfPrices.setDisable(true);
        }
    }

    public void setDAOFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @FXML
    private void handleBtnAddTypeOfPricesPress(ActionEvent event) {
        bbSaveCancel.setDisable(false);
        gpBasic.setDisable(false);
        btnAddTypeOfPrices.setDisable(true);
        btnUpdateTypeOfPrices.setDisable(true);
        btnRemoveTypeOfPrices.setDisable(true);
        btnSearchTypeOfPrices.setDisable(true);
        clearControls();
    }

    @FXML
    private void handleBtnCancelPress(ActionEvent event) {
        btnAddTypeOfPrices.setDisable(false);
        if (!tvTypesOfPrices.getItems().isEmpty()) {
            btnUpdateTypeOfPrices.setDisable(false);
            btnRemoveTypeOfPrices.setDisable(false);
            btnSearchTypeOfPrices.setDisable(false);
            tvTypesOfPrices.getSelectionModel().selectFirst();
        } else {
            btnUpdateTypeOfPrices.setDisable(true);
            btnRemoveTypeOfPrices.setDisable(true);
            btnSearchTypeOfPrices.setDisable(true);
        }
        bbSaveCancel.setDisable(true);
        gpBasic.setDisable(true);
    }

    @FXML
    private void handleBtnRemoveTypeOfPricesPress(ActionEvent event) {
        Optional.ofNullable(tvTypesOfPrices.getSelectionModel().getSelectedItem()).ifPresent((typesOfPricess) -> {
            int result = typesOfPricesDAO.delete(typesOfPricess);
            if (result > 0) {
                tvTypesOfPrices.getItems().remove(typesOfPricess);
            }
            clearControls();
        });
    }

    @FXML
    private void handleBtnSavePress(ActionEvent event) {
        Long id = lbId.getText() == null || lbId.getText().matches("") ? null : Long.parseLong(lbId.getText());
        String name = tfName.getText();
        Boolean vatIncluded = chbVatIncluded.isSelected();
        Long currency = cbCurrency.getValue() == null ? null : cbCurrency.getValue().get();
        if (id == null) {
            TypesOfPrices typeOfPrices = new TypesOfPrices(id, name, vatIncluded, currency);
            int result = typesOfPricesDAO.add(typeOfPrices);
            if (result > 0) {
                tvTypesOfPrices.getItems().add(typeOfPrices);
            }
        } else {
            TypesOfPrices typeOfPrices = new TypesOfPrices(id, name, vatIncluded, currency);
            int result = typesOfPricesDAO.update(typeOfPrices);
            if (result > 0) {
                tvTypesOfPrices.getItems().set(tvTypesOfPrices.getSelectionModel().getSelectedIndex(), typeOfPrices);
            }
        }
        btnAddTypeOfPrices.setDisable(false);
        btnUpdateTypeOfPrices.setDisable(false);
        btnRemoveTypeOfPrices.setDisable(false);
        btnSearchTypeOfPrices.setDisable(false);
        gpBasic.setDisable(true);
        bbSaveCancel.setDisable(true);
    }

    @FXML
    private void handleBtnSearchTypeOfPricesPress() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PATH_TO_TYPES_OF_PRICES_SEARCH));
        AnchorPane apSearchPane = fxmlLoader.load();
        TypesOfPricesSearchController typesOfPricesSearchController = fxmlLoader.getController();
        typesOfPricesSearchController.setDAOFactory(daoFactory);
        Tab tab = new Tab("Поиск в таблице \"Типы цен\"", apSearchPane);
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
    private void handleBtnUpdateTypeOfPricesPress(ActionEvent event) {
        Optional.ofNullable(tvTypesOfPrices.getSelectionModel().getSelectedItem()).ifPresent((typesOfPricess) -> {
            bbSaveCancel.setDisable(false);
            gpBasic.setDisable(false);
            btnAddTypeOfPrices.setDisable(true);
            btnUpdateTypeOfPrices.setDisable(true);
            btnRemoveTypeOfPrices.setDisable(true);
            btnSearchTypeOfPrices.setDisable(true);
            fillControls(typesOfPricess);
        });
    }


    private void fillControls(TypesOfPrices typesOfPrices) {
        lbId.setText(typesOfPrices.getId() == null ? "" : typesOfPrices.getId().toString());
        tfName.setText(typesOfPrices.getName());
        chbVatIncluded.setSelected(typesOfPrices.getVatIncluded());
        cbCurrency.setValue(typesOfPrices.currencyProperty());
    }

    private void clearControls() {
        lbId.setText("");
        tfName.setText("");
        chbVatIncluded.setSelected(false);
        cbCurrency.getSelectionModel().selectFirst();
    }
}
