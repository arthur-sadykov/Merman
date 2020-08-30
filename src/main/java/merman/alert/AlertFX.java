package merman.alert;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AlertFX {
    private static final Logger LOGGER = Logger.getLogger(AlertFX.class.getName());
    private static final Stage stage;

    static {
        ObservableList<Window> stages = Stage.getWindows();
        stage = (Stage) stages.get(stages.size() - 1);
    }

    public static void showError(String error, Throwable e) {
        Optional.ofNullable(e).ifPresent(throwable -> {
            LOGGER.log(Level.SEVERE, error, e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(stage.getTitle());
            alert.setHeaderText("Ошибка");
            alert.setContentText(error);
            alert.initOwner(stage);
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            e.printStackTrace(printWriter);
            String exceptionText = stringWriter.toString();
            Label label = new Label("Трассировка стека:");
            TextArea textArea = new TextArea(exceptionText);
            textArea.setEditable(false);
            textArea.setWrapText(true);
            textArea.setMaxWidth(Double.MAX_VALUE);
            textArea.setMaxHeight(Double.MAX_VALUE);
            GridPane.setVgrow(textArea, Priority.ALWAYS);
            GridPane.setHgrow(textArea, Priority.ALWAYS);
            GridPane expContent = new GridPane();
            expContent.setMaxWidth(Double.MAX_VALUE);
            expContent.add(label, 0, 0);
            expContent.add(textArea, 0, 1);
            alert.getDialogPane().setExpandableContent(expContent);
            alert.showAndWait();
        });
    }

    public static void showError(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(stage.getTitle());
        alert.setHeaderText("Ошибка");
        alert.setContentText(error);
        alert.initOwner(stage);
        alert.showAndWait();
    }

    public static void showWarning(String warning) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(stage.getTitle());
        alert.setHeaderText("Предупреждение");
        alert.setContentText(warning);
        alert.initOwner(stage);
        alert.showAndWait();
    }
}
