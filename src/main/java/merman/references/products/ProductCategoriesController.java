package merman.references.products;

import merman.util.dao.DAOFactory;
import merman.util.dao.interfaces.ProductCategoriesDAO;
import merman.util.model.ProductCategories;
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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author Arthur Sadykov
 */
public class ProductCategoriesController implements Initializable {

    private static final String SUCH_CATEGORY_ALREADY_EXISTS = "Такая категория товаров уже существует";
    private static final String PATH_TO_SEARCH_PRODUCT_CATEGORY = "/merman/references/products/products-categories-search.fxml";
    private DAOFactory daoFactory;

    @FXML
    private ButtonBar bbSaveCancel;
    @FXML
    private Button btnAddProductCategory;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnRemoveProductCategory;

    @FXML
    private Button btnSave;
    @FXML
    private Button btnSearchProductCategory;
    @FXML
    private Button btnUpdateProductCategory;
    @FXML
    private CheckBox chbDisregardWeight;
    @FXML
    private GridPane gpBasic;
    @FXML
    private Label lbId;


    @FXML
    private TableColumn<ProductCategories, StringProperty> tcName;
    @FXML
    private TextField tfName;
    private TabPane tpContentPane;
    @FXML
    private TabPane tpRightTabPane;
    @FXML
    private TableView<ProductCategories> tvProductCategories;
    private ProductCategoriesDAO productCategoriesDAO;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<ProductCategories> list = FXCollections.observableArrayList((productCategories) -> new Observable[]{productCategories.nameProperty(), productCategories.disregardWeightProperty()});
        list.addListener((ListChangeListener.Change<? extends ProductCategories> change) -> {
            productCategoriesDAO = daoFactory.getProductCategoriesDAO();
            while (change.next()) {
                if (change.wasRemoved()) {
                    if (list.isEmpty()) {
                        btnUpdateProductCategory.setDisable(true);
                        btnRemoveProductCategory.setDisable(true);
                        btnSearchProductCategory.setDisable(true);
                    }
                }
                if (change.wasAdded()) {
                    btnUpdateProductCategory.setDisable(false);
                    btnRemoveProductCategory.setDisable(false);
                    btnSearchProductCategory.setDisable(false);
                }
            }
        });
        tvProductCategories.setItems(list);
        tvProductCategories.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> Optional.ofNullable(tvProductCategories.getSelectionModel().getSelectedItem()).ifPresent((productCategory) -> {
            long id = productCategory.getId();
            if (id == 1) {
                btnAddProductCategory.setDisable(false);
                btnUpdateProductCategory.setDisable(true);
                btnRemoveProductCategory.setDisable(true);
                bbSaveCancel.setDisable(true);
                gpBasic.setDisable(true);
            } else {
                btnUpdateProductCategory.setDisable(false);
                btnRemoveProductCategory.setDisable(false);
                bbSaveCancel.setDisable(true);
                gpBasic.setDisable(true);
            }
            fillControls(productCategory);
        }));
    }

    public void setupAfterInitialize(TabPane tpContentPane) {
        productCategoriesDAO = daoFactory.getProductCategoriesDAO();
        this.tpContentPane = tpContentPane;
        tvProductCategories.getItems().addAll(productCategoriesDAO.list());
        if (!tvProductCategories.getItems().isEmpty()) {
            btnUpdateProductCategory.setDisable(false);
            btnRemoveProductCategory.setDisable(false);
            btnSearchProductCategory.setDisable(false);
            tvProductCategories.getSelectionModel().selectFirst();
        } else {
            btnUpdateProductCategory.setDisable(true);
            btnRemoveProductCategory.setDisable(true);
            btnSearchProductCategory.setDisable(true);
        }
    }

    public void setDAOFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    private void clearControls() {
        lbId.setText("");
        tfName.setText("");
    }

    private void fillControls(ProductCategories productCategory) {
        lbId.setText(productCategory.getId().toString());
        tfName.setText(productCategory.getName());
        chbDisregardWeight.setSelected(productCategory.getDisregardWeight());
    }

    @FXML
    private void handleBtnAddProductCategoryPress(ActionEvent event) {
        bbSaveCancel.setDisable(false);
        gpBasic.setDisable(false);
        btnAddProductCategory.setDisable(true);
        btnUpdateProductCategory.setDisable(true);
        btnRemoveProductCategory.setDisable(true);
        btnSearchProductCategory.setDisable(true);
        clearControls();
    }

    @FXML
    private void handleBtnCancelPress(ActionEvent event) {
        btnAddProductCategory.setDisable(false);
        if (!tvProductCategories.getItems().isEmpty()) {
            btnUpdateProductCategory.setDisable(false);
            btnRemoveProductCategory.setDisable(false);
            btnSearchProductCategory.setDisable(false);
            tvProductCategories.getSelectionModel().selectFirst();
        } else {
            btnUpdateProductCategory.setDisable(true);
            btnRemoveProductCategory.setDisable(true);
            btnSearchProductCategory.setDisable(true);
        }
        bbSaveCancel.setDisable(true);
        gpBasic.setDisable(true);
    }

    @FXML
    private void handleBtnRemoveProductCategoryPress(ActionEvent event) {
        Optional.ofNullable(tvProductCategories.getSelectionModel().getSelectedItem()).ifPresent((productCategory) -> {
            int result = productCategoriesDAO.delete(productCategory);
            if (result > 0) {
                tvProductCategories.getItems().remove(productCategory);
            }
            clearControls();
        });
    }

    @FXML
    private void handleBtnSavePress(ActionEvent event) {
        Long id = lbId.getText() == null || lbId.getText().matches("") ? null : Long.parseLong(lbId.getText());
        String name = tfName.getText();
        Boolean disregardWeight = chbDisregardWeight.isSelected();
        if (id == null) {
            ProductCategories productCategories = new ProductCategories(id, name, disregardWeight);
            int result = productCategoriesDAO.add(productCategories);
            if (result > 0) {
                tvProductCategories.getItems().add(productCategories);
            }
        } else {
            ProductCategories productCategories = new ProductCategories(id, name, disregardWeight);
            int result = productCategoriesDAO.update(productCategories);
            if (result > 0) {
                tvProductCategories.getItems().set(tvProductCategories.getSelectionModel().getSelectedIndex(), productCategories);
            }
        }
        btnAddProductCategory.setDisable(false);
        btnUpdateProductCategory.setDisable(false);
        btnRemoveProductCategory.setDisable(false);
        btnSearchProductCategory.setDisable(false);
        gpBasic.setDisable(true);
        bbSaveCancel.setDisable(true);
    }

    @FXML
    private void handleBtnSearchProductCategoryPress() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PATH_TO_SEARCH_PRODUCT_CATEGORY));
        AnchorPane apSearchPane = fxmlLoader.load();
        ProductsCategoriesSearchController productsCategoriesSearchController = fxmlLoader.getController();
        productsCategoriesSearchController.setDAOFactory(daoFactory);
        Tab tab = new Tab("Поиск в таблице \"Категории товаров\"", apSearchPane);
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
    private void handleBtnUpdateProductCategoryPress(ActionEvent event) {
        ProductCategories product = tvProductCategories.getSelectionModel().getSelectedItem();
        if (product != null) {
            bbSaveCancel.setDisable(false);
            gpBasic.setDisable(false);
            btnAddProductCategory.setDisable(true);
            btnUpdateProductCategory.setDisable(true);
            btnRemoveProductCategory.setDisable(true);
            btnSearchProductCategory.setDisable(true);
            fillControls(product);
        }
    }
}
