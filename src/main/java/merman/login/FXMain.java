package merman.login;

import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.WinReg;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * @author Arthur Sadykov
 */
public class FXMain extends Application {


    private static final String PATH_TO_LOGIN = "/merman/login/login.fxml";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage loginStage) throws IOException {
        String libreOfficeRegistryKey64 = "SOFTWARE\\LibreOffice\\UNO\\InstallPath";
        String libreOfficeRegistryKey32 = "SOFTWARE\\Wow6432Node\\LibreOffice\\UNO\\InstallPath";
        boolean libreOfficeInstalled = false;
        if (Advapi32Util.registryKeyExists(WinReg.HKEY_LOCAL_MACHINE, libreOfficeRegistryKey32)) {
            TreeMap<String, Object> map = Advapi32Util.registryGetValues(WinReg.HKEY_LOCAL_MACHINE, libreOfficeRegistryKey32);
            for (String s : map.keySet()) {
                if (s.matches("LibreOffice.+?")) {
                    libreOfficeInstalled = true;
                    break;
                }
            }
        }
        if (Advapi32Util.registryKeyExists(WinReg.HKEY_LOCAL_MACHINE, libreOfficeRegistryKey64)) {
            TreeMap<String, Object> map = Advapi32Util.registryGetValues(WinReg.HKEY_LOCAL_MACHINE, libreOfficeRegistryKey64);
            for (String s : map.keySet()) {
                if (s.matches("LibreOffice.+?")) {
                    libreOfficeInstalled = true;
                    break;
                }
            }
        }
        if (!libreOfficeInstalled) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Информационная система предприятия по доставке питьевой воды");
            alert.setHeaderText("Для работы программы требуется установка офисного пакета LibreOffice");
            List<Image> images = new ArrayList<>();
            images.add(new Image(getClass().getResourceAsStream("/merman/icons/water16x16.png")));
            images.add(new Image(getClass().getResourceAsStream("/merman/icons/water24x24.png")));
            images.add(new Image(getClass().getResourceAsStream("/merman/icons/water32x32.png")));
            images.add(new Image(getClass().getResourceAsStream("/merman/icons/water64x64.png")));
            images.add(new Image(getClass().getResourceAsStream("/merman/icons/water128x128.png")));
            images.add(new Image(getClass().getResourceAsStream("/merman/icons/water256x256.png")));
            ((Stage) (alert.getDialogPane().getScene().getWindow())).getIcons().addAll(images);
            ButtonType btCancel = new ButtonType("Отмена", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(btCancel);
            Hyperlink hyperlink = new Hyperlink("Перейти на страницу загрузки LibreOffice");
            hyperlink.setOnAction(event -> {
                getHostServices().showDocument("https://ru.libreoffice.org/download/");
                System.exit(1);
            });
            alert.getDialogPane().setContent(hyperlink);
            alert.showAndWait();
            System.exit(1);
        }
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PATH_TO_LOGIN));
        Parent root = fxmlLoader.load();
        LoginController loginController = fxmlLoader.getController();
        loginController.setupAfterInitialize(loginStage);
        Scene scene = new Scene(root);
        loginStage.setScene(scene);
        loginStage.setTitle("Информационная система предприятия по доставке питьевой воды");
        loginStage.setResizable(false);
        List<Image> images = new ArrayList<>();
        images.add(new Image(getClass().getResourceAsStream("/merman/icons/water16x16.png")));
        images.add(new Image(getClass().getResourceAsStream("/merman/icons/water24x24.png")));
        images.add(new Image(getClass().getResourceAsStream("/merman/icons/water32x32.png")));
        images.add(new Image(getClass().getResourceAsStream("/merman/icons/water64x64.png")));
        images.add(new Image(getClass().getResourceAsStream("/merman/icons/water128x128.png")));
        images.add(new Image(getClass().getResourceAsStream("/merman/icons/water256x256.png")));
        loginStage.getIcons().addAll(images);
        loginStage.show();
    }
}
