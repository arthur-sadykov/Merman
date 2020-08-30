package merman.references.settlementswithemployees;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Arthur Sadykov
 */
public class TypesOfDeductionsController implements Initializable {

    @FXML
    private ComboBox<?> cbGroup;
    @FXML
    private MenuItem handleMiCardPrintPress;
    @FXML
    private MenuItem handleMiRegistryPrintPress;
    @FXML
    private TableColumn<?, ?> tcName;
    @FXML
    private TextField tfAmountOfDeduction;
    @FXML
    private TextField tfName;
    @FXML
    private TableView<?> tvTypesOfDeductions;

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
    private void handleBtnUpdateServicePress(ActionEvent event) {
    }

    @FXML
    private void handleBtnRemoveServicePress(ActionEvent event) {
    }

    @FXML
    private void handleBtnSavePress(ActionEvent event) {
    }

    @FXML
    private void handleBtnSearchServicePress(ActionEvent event) {
    }
}
