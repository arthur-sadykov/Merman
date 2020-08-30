package merman.administration;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Arthur Sadykov
 */
public class SettingsAndRightsSearchController implements Initializable {

    @FXML
    private Button btnSearch;
    @FXML
    private ListView<?> lvOptions;
    @FXML
    private TableColumn<?, ?> tcName;
    @FXML
    private TextField tfSearch;
    @FXML
    private TableView<?> tvRights;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void handleBtnSearchPress(ActionEvent event) {
    }
}
