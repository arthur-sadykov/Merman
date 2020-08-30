package merman.references.services;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Arthur Sadykov
 */
public class ServicesController implements Initializable {

    @FXML
    private ComboBox<?> cbCategory;
    @FXML
    private ComboBox<?> cbGroup;
    @FXML
    private CheckBox cbRent;
    @FXML
    private ComboBox<?> cbUnits;
    @FXML
    private ComboBox<?> cbVATRate;
    @FXML
    private MenuItem handleMiCardPrintPress;
    @FXML
    private MenuItem handleMiRegistryPrintPress;
    @FXML
    private TextArea taAdditionalInformation;
    @FXML
    private TextArea taComment;
    @FXML
    private TableColumn<?, ?> tcCategory;
    @FXML
    private TableColumn<?, ?> tcDateOfChange;
    @FXML
    private TableColumn<?, ?> tcName;
    @FXML
    private TableColumn<?, ?> tcPrice;
    @FXML
    private TableColumn<?, ?> tcPriceType;
    @FXML
    private TableColumn<?, ?> tcUnits;
    @FXML
    private TextField tfFullName;
    @FXML
    private TextField tfName;
    @FXML
    private TableView<?> tvPrices;
    @FXML
    private TableView<?> tvServices;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void handleBtnAddServicePress(ActionEvent event) {
    }

    @FXML
    private void handleBtnCancelPress(ActionEvent event) {
    }


    @FXML
    private void handleBtnDeletePricePress(ActionEvent event) {
    }

    @FXML
    private void handleBtnUpdateServicePress(ActionEvent event) {
    }

    @FXML
    private void handleBtnNewPricePress(ActionEvent event) {
    }

    @FXML
    private void handleBtnRemoveServicePress(ActionEvent event) {
    }

    @FXML
    private void handleBtnSavePress(ActionEvent event) {
    }

    @FXML
    private void handleBtnSearchPricePress(ActionEvent event) {
    }

    @FXML
    private void handleBtnSearchServicePress(ActionEvent event) {
    }
}
