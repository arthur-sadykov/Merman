package merman.login;

import java.io.File;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import merman.alert.AlertFX;
import merman.core.CoreController;
import merman.util.dao.DAOFactory;
import merman.util.dao.DAOProperties;
import merman.util.dao.DatabaseName;
import org.jodconverter.office.LocalOfficeManager;
import org.jodconverter.office.OfficeException;
import org.jodconverter.office.OfficeUtils;

/**
 * @author Arthur Sadykov
 */
public class LoginController implements Initializable {

    private static final String PROGRAM_TITLE = "Информационная система предприятия по доставке питьевой воды";
    private static final String PATH_TO_MERMAN_DIRECTORY = System.getProperty("java.io.tmpdir") + "/merman/";
    private static final String PATH_TO_CORE = "/merman/core/core.fxml";
    private static final String CONNECTION_FAILED = "Не удается установить соединение с информационной системой";
    private static final String PATH_TO_LIBRE_OFFICE = "C:\\Program Files\\LibreOffice";
    @FXML
    private Button btnEnterSystem;
    @FXML
    private ComboBox<String> cbDatabase;
    @FXML
    private PasswordField pfPassword;
    @FXML
    private TextField tfUserName;
    private Stage loginStage;
    private Connection connection;
    private DAOFactory daoFactory;
    private LocalOfficeManager officeManager;
    @FXML
    private ProgressIndicator piProgress;
    @FXML
    private Label lbProgress;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnEnterSystem.disableProperty().bind(tfUserName.lengthProperty().isEqualTo(0).or(pfPassword.lengthProperty().isEqualTo(0)));
        DAOProperties daoProperties = new DAOProperties(DatabaseName.MySQL.toString());
        List<String> urls = daoProperties.getURLs();
        cbDatabase.getItems().addAll(urls);
        cbDatabase.focusedProperty().addListener((observable, wasFocused, isFocused) -> {
            if (!isFocused) {
                if (cbDatabase.getValue() != null && !cbDatabase.getValue().matches("")
                        && !cbDatabase.getItems().contains(cbDatabase.getValue())) {
                    DAOProperties properties = new DAOProperties(DatabaseName.MySQL.toString());
                    cbDatabase.getItems().add(cbDatabase.getValue());
                    List<String> urlKeys = properties.getURLKeys();
                    String urlKey = urlKeys.get(0);
                    Pattern pattern = Pattern.compile(".*?(\\d+)");
                    Matcher matcher = pattern.matcher(urlKey);
                    int index = 0;
                    if (matcher.matches()) {
                        index = parseInt(matcher.group(1)) + 1;
                    }
                    properties.setProperty("url" + index, cbDatabase.getValue());
                    properties.store();
                }
            }
        });
        Platform.runLater(() -> cbDatabase.requestFocus());
        cbDatabase.getSelectionModel().selectFirst();
        tfUserName.setText("root");
        pfPassword.setText("root");
    }

    private void enterToSystem() {
        String userName = tfUserName.getText();
        String password = pfPassword.getText();
        Task<Connection> connectionTask = new ConnectionTask(userName, password);
        btnEnterSystem.disableProperty().bind(connectionTask.runningProperty());
        tfUserName.disableProperty().bind(connectionTask.runningProperty());
        pfPassword.disableProperty().bind(connectionTask.runningProperty());
        cbDatabase.disableProperty().bind(connectionTask.runningProperty());
        lbProgress.visibleProperty().bind(connectionTask.runningProperty());
        piProgress.visibleProperty().bind(connectionTask.runningProperty());
        ExecutorService executor = Executors.newSingleThreadExecutor();
        connectionTask.setOnSucceeded((t) -> {
            connection = connectionTask.getValue();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PATH_TO_CORE));
            Parent root = null;
            try {
                root = fxmlLoader.load();
            } catch (IOException e) {
                AlertFX.showError("Ошибка при открытии окна", e);
            }
            CoreController coreController = fxmlLoader.getController();
            coreController.setDAOFactory(daoFactory);
            coreController.setupAfterInitialize();
            Stage coreStage = new Stage();
            List<Image> images = new ArrayList<>();
            images.add(new Image(getClass().getResourceAsStream("/merman/icons/water16x16.png")));
            images.add(new Image(getClass().getResourceAsStream("/merman/icons/water24x24.png")));
            images.add(new Image(getClass().getResourceAsStream("/merman/icons/water32x32.png")));
            images.add(new Image(getClass().getResourceAsStream("/merman/icons/water64x64.png")));
            images.add(new Image(getClass().getResourceAsStream("/merman/icons/water128x128.png")));
            images.add(new Image(getClass().getResourceAsStream("/merman/icons/water256x256.png")));
            coreStage.getIcons().addAll(images);
            Scene scene = new Scene(root);
            coreStage.setScene(scene);
            coreStage.setTitle(PROGRAM_TITLE);
            coreStage.setOnCloseRequest(event -> {
                OfficeUtils.stopQuietly(officeManager);
                try {
                    Path pathToBeDeleted = Paths.get(PATH_TO_MERMAN_DIRECTORY);
                    if (Files.exists(pathToBeDeleted)) {
                        Files.walk(pathToBeDeleted).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
                    }
                } catch (IOException e) {
                    AlertFX.showError("Ошибка удаления файла", e);
                }
            });
            loginStage.close();
            coreStage.show();
        });
        executor.submit(connectionTask);
    }

    @FXML
    private void handleBtnEnterSystemPress(ActionEvent event) {
        enterToSystem();
    }

    @FXML
    private void handleEnterKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if (tfUserName.getLength() != 0) {
                if (pfPassword.getLength() != 0) {
                } else {
                    pfPassword.requestFocus();
                }
            } else {
                if (pfPassword.getLength() != 0) {
                    tfUserName.requestFocus();
                }
            }
        }
    }

    public void setupAfterInitialize(Stage loginStage) {
        this.loginStage = loginStage;
    }

    class ConnectionTask extends Task<Connection> {

        private final String userName;
        private final String password;

        ConnectionTask(String userName, String password) {
            this.userName = userName;
            this.password = password;
        }

        @Override
        protected Connection call() {
            daoFactory = DAOFactory.getInstance(DatabaseName.MySQL, userName, password);
            Connection connection = null;
            try {
                Platform.runLater(() -> lbProgress.setText("Подключение к информационной базе..."));
                connection = daoFactory.getConnection();
                Platform.runLater(() -> {
                    lbProgress.setText("Подключение к информационной базе выполнено");
                    lbProgress.setText("Запуск службы офис-менеджера LibreOffice...");
                });
                officeManager = LocalOfficeManager.builder().install().officeHome(PATH_TO_LIBRE_OFFICE).build();
                try {
                    officeManager.start();
                } catch (OfficeException e) {
                    Platform.runLater(() -> AlertFX.showError("Ошибка при запуске офис-менеджера LibreOffice", e));
                }
                Platform.runLater(() -> lbProgress.setText("Служба офис-менеджера LibreOffice запущена"));
                try {
                    Thread.sleep(20);
                } catch (InterruptedException ignored) {
                }
            } catch (SQLException ignored) {
            }
            return connection;
        }
    }
}
