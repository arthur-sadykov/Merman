package merman.documents.consumableinvoices;

import java.awt.image.BufferedImage;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import merman.alert.AlertFX;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.jodconverter.JodConverter;
import org.jodconverter.office.OfficeException;

public class DocumentController implements Initializable {

    private static final String PATH_TO_MERMAN_DIR = System.getProperty("java.io.tmpdir") + "/merman/";
    private static final String DOCUMENT_PDF = "document.pdf";
    private static final String DOCUMENT_DOCX = "document.docx";
    private static final String DOCUMENT_RTF = "document.rtf";
    private static final String PNG_EXT = ".png";
    private static final double INITIAL_DOCUMENT_WIDTH = 800.0;
    private static final double INITIAL_DOCUMENT_HEIGHT = 800.0;
    @FXML
    private ImageView ivDocument;
    @FXML
    private ScrollPane spScroller;
    @FXML
    private StackPane spImageHolder;
    @FXML
    private Button btnPrint;
    @FXML
    private Button btnSaveInPdf;
    @FXML
    private Button btnSaveInDocx;
    @FXML
    private Button btnSaveInRtf;
    @FXML
    private Button btnGoToPreviousPage;
    @FXML
    private Button btnGoToNextPage;
    @FXML
    private Button btnZoomOut;
    @FXML
    private ComboBox<String> cbChangeSize;
    @FXML
    private Button btnZoomIn;
    private List<String> pngFiles;
    private int currentDocumentPage;
    @FXML
    private ProgressBar pbProgress;
    @FXML
    private Label lbStatus;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Task<Void> convertTask = new ConvertTask(PATH_TO_MERMAN_DIR + DOCUMENT_DOCX, PATH_TO_MERMAN_DIR + DOCUMENT_PDF);
        convertTask.setOnSucceeded(e -> {
            Task<Void> imagesGenerationTask = new ImagesGenerationTask(new File(PATH_TO_MERMAN_DIR + DOCUMENT_PDF));
            pbProgress.visibleProperty().bind(imagesGenerationTask.runningProperty());
            lbStatus.visibleProperty().bind(imagesGenerationTask.runningProperty());
            imagesGenerationTask.setOnSucceeded(event -> {
                Image image = new Image("file:///" + PATH_TO_MERMAN_DIR + "document-1.png");
                ivDocument.setImage(image);
                try {
                    Path pathToMermanDir = Paths.get(PATH_TO_MERMAN_DIR);
                    if (Files.exists(pathToMermanDir)) {
                        long pngCount =
                                Files.walk(pathToMermanDir).filter(path -> path.getFileName().endsWith("png")).count();
                        if (pngCount <= 1) {
                            btnGoToPreviousPage.setDisable(true);
                            btnGoToNextPage.setDisable(true);
                        }
                    }
                } catch (IOException ex) {
                    AlertFX.showError(ex.getMessage(), ex);
                }
            });
            executorService.submit(imagesGenerationTask);
        });
        pbProgress.visibleProperty().bind(convertTask.runningProperty());
        lbStatus.visibleProperty().bind(convertTask.runningProperty());
        executorService.submit(convertTask);
        cbChangeSize.valueProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue) {
                case "25%":
                    ivDocument.setFitWidth(INITIAL_DOCUMENT_WIDTH * 0.25);
                    ivDocument.setFitHeight(INITIAL_DOCUMENT_HEIGHT * 0.25);
                    break;
                case "50%":
                    ivDocument.setFitWidth(INITIAL_DOCUMENT_WIDTH * 0.5);
                    ivDocument.setFitHeight(INITIAL_DOCUMENT_HEIGHT * 0.5);
                    break;
                case "75%":
                    ivDocument.setFitWidth(INITIAL_DOCUMENT_WIDTH * 0.75);
                    ivDocument.setFitHeight(INITIAL_DOCUMENT_HEIGHT * 0.75);
                    break;
                case "100%":
                    ivDocument.setFitWidth(INITIAL_DOCUMENT_WIDTH * 1.0);
                    ivDocument.setFitHeight(INITIAL_DOCUMENT_HEIGHT * 1.0);
                    break;
                case "150%":
                    ivDocument.setFitWidth(INITIAL_DOCUMENT_WIDTH * 1.5);
                    ivDocument.setFitHeight(INITIAL_DOCUMENT_HEIGHT * 1.5);
                    break;
                case "200%":
                    ivDocument.setFitWidth(INITIAL_DOCUMENT_WIDTH * 2.0);
                    ivDocument.setFitHeight(INITIAL_DOCUMENT_HEIGHT * 2.0);
                    break;
                case "300%":
                    ivDocument.setFitWidth(INITIAL_DOCUMENT_WIDTH * 3.0);
                    ivDocument.setFitHeight(INITIAL_DOCUMENT_HEIGHT * 3.0);
                    break;
                case "400%":
                    ivDocument.setFitWidth(INITIAL_DOCUMENT_WIDTH * 4.0);
                    ivDocument.setFitHeight(INITIAL_DOCUMENT_HEIGHT * 4.0);
                    break;
            }
        });
        spImageHolder.minWidthProperty().bind(Bindings.createDoubleBinding(() ->
                spScroller.getViewportBounds().getWidth(), spScroller.viewportBoundsProperty()));
    }

    @FXML
    private void handleBtnPrintPress(ActionEvent event) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Task<Void> task = new PrintTask();
        executorService.submit(task);
    }

    @FXML
    private void handleBtnSaveInPdfPress(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Документ PDF", "*.pdf"));
        File file = fileChooser.showSaveDialog(btnSaveInPdf.getScene().getWindow());
        Optional.ofNullable(file).ifPresent(f -> {
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            Task<Void> task = new ConvertTask(PATH_TO_MERMAN_DIR + DOCUMENT_DOCX, PATH_TO_MERMAN_DIR + DOCUMENT_PDF);
            task.setOnSucceeded(e -> {
                try {
                    Path pathToPdf = Paths.get(PATH_TO_MERMAN_DIR + DOCUMENT_PDF);
                    if (Files.exists(pathToPdf)) {
                        Files.copy(pathToPdf, Paths.get(file.toURI()), StandardCopyOption.REPLACE_EXISTING);
                    }
                } catch (IOException ex) {
                    AlertFX.showError(ex.getMessage(), ex);
                }
            });
            pbProgress.visibleProperty().bind(task.runningProperty());
            lbStatus.visibleProperty().bind(task.runningProperty());
            executorService.submit(task);
        });
    }

    @FXML
    private void handleBtnSaveInDocxPress(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Документ Word", "*.docx"));
        File file = fileChooser.showSaveDialog(btnSaveInDocx.getScene().getWindow());
        Optional.ofNullable(file).ifPresent(f -> {
            Path pathToDocx = Paths.get(PATH_TO_MERMAN_DIR + DOCUMENT_DOCX);
            if (Files.exists(pathToDocx)) {
                try {
                    Files.copy(pathToDocx, Paths.get(file.toURI()));
                } catch (IOException e) {
                    AlertFX.showError(e.getMessage(), e);
                }
            } else {
                AlertFX.showError("Ошибка при сохранении файла " + file.getAbsolutePath());
            }
        });
    }

    @FXML
    private void handleBtnSaveInRtfPress(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Документ RTF", "*.rtf"));
        File file = fileChooser.showSaveDialog(btnSaveInRtf.getScene().getWindow());
        Optional.ofNullable(file).ifPresent(element -> {
            if (file != null) {
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                Task<Void> task = new ConvertTask(PATH_TO_MERMAN_DIR + DOCUMENT_DOCX, PATH_TO_MERMAN_DIR + DOCUMENT_RTF);
                task.setOnSucceeded(e -> {
                    try {
                        Path pathToRtf = Paths.get(PATH_TO_MERMAN_DIR + DOCUMENT_RTF);
                        if (Files.exists(pathToRtf)) {
                            Files.copy(pathToRtf, Paths.get(file.toURI()), StandardCopyOption.REPLACE_EXISTING);
                        }
                    } catch (IOException ex) {
                        AlertFX.showError(ex.getMessage(), ex);
                    }
                });
                pbProgress.visibleProperty().bind(task.runningProperty());
                lbStatus.visibleProperty().bind(task.runningProperty());
                executorService.submit(task);
            }
        });
    }

    @FXML
    private void handleBtnGoToPreviousPagePress(ActionEvent event) {
        currentDocumentPage = currentDocumentPage == 0 ? pngFiles.size() - 1 : currentDocumentPage - 1;
        String pngFile = pngFiles.get(currentDocumentPage);
        if (Files.exists(Paths.get(pngFile))) {
            Image image = new Image("file:///".concat(pngFile));
            ivDocument.setImage(image);
        } else {
            AlertFX.showError("Произошла ошибка при загрузке страницы документа");
        }
    }

    @FXML
    private void handleBtnGoToNextPagePress(ActionEvent event) {
        currentDocumentPage = currentDocumentPage == pngFiles.size() - 1 ? 0 : currentDocumentPage + 1;
        String pngFile = pngFiles.get(currentDocumentPage);
        if (Files.exists(Paths.get(pngFile))) {
            Image image = new Image("file:///".concat(pngFile));
            ivDocument.setImage(image);
        } else {
            AlertFX.showError("Произошла ошибка при загрузке страницы документа");
        }
    }

    @FXML
    private void handleBtnZoomOutPress(ActionEvent event) {
        ivDocument.setFitWidth(ivDocument.getBoundsInParent().getWidth() - 50);
    }

    @FXML
    private void handleBtnZoomInPress(ActionEvent event) {
        ivDocument.setFitWidth(ivDocument.getBoundsInParent().getWidth() + 50);
    }

    public void setupAfterInitialize(List<String> pngFiles) {
        this.pngFiles = pngFiles;
    }

    class PrintTask extends Task<Void> {

        @Override
        protected Void call() throws IOException {
            File file = new File(PATH_TO_MERMAN_DIR + DOCUMENT_PDF);
            if (file.exists()) {
                try (PDDocument document = PDDocument.load(file)) {
                    PrinterJob job = PrinterJob.getPrinterJob();
                    PDFPageable pageable = new PDFPageable(document);
                    job.setPageable(pageable);
                    if (job.printDialog()) {
                        job.print();
                    }
                } catch (PrinterException e) {
                    AlertFX.showError(e.getMessage(), e);
                }
            }
            return null;
        }
    }

    class ConvertTask extends Task<Void> {

        private final String inputFile;
        private final String outputFile;

        ConvertTask(String inputFile, String outputFile) {
            this.inputFile = inputFile;
            this.outputFile = outputFile;
        }

        @Override
        protected Void call() {
            File input = new File(inputFile);
            if (!input.exists()) {
                return null;
            }
            File output = new File(outputFile);
            try {
                JodConverter.convert(input).to(output).execute();
            } catch (OfficeException e) {
                Platform.runLater(() -> AlertFX.showError(e.getMessage(), e));
            }
            return null;
        }
    }

    class ImagesGenerationTask extends Task<Void> {

        private final File pdfFile;

        ImagesGenerationTask(File pdfFile) {
            this.pdfFile = pdfFile;
        }

        @Override
        protected Void call() {
            try (PDDocument document = PDDocument.load(pdfFile)) {
                PDFRenderer pdfRenderer = new PDFRenderer(document);
                for (int page = 0; page < document.getNumberOfPages(); ++page) {
                    BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 100, ImageType.RGB);
                    String pathToPdf = pdfFile.getAbsolutePath();
                    ImageIOUtil.writeImage(bim, pathToPdf.substring(0, pathToPdf.length() - 4) + "-" + (page + 1)
                            + ".png", 300);
                }
            } catch (IOException ignored) {
            }
            return null;
        }
    }
}
