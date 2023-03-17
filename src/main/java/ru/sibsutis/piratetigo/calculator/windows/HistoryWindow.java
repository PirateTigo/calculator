package ru.sibsutis.piratetigo.calculator.windows;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 * Диалоговое окно истории вычислений.
 */
public class HistoryWindow {
    public static final String HISTORY_FORM_PATH = "/forms/history.fxml";
    private static final String HISTORY_WINDOW_TITLE = "История вычислений";

    /**
     * Наполняет контейнер компонентов окна.
     *
     * @param stage Контейнер компонентов окна.
     * @param faviconLocation URL размещения иконки приложения.
     * @param historyFormLocation URL размещения fxml-файла
     * описания формы истории вычислений.
     * @param controller Контроллер для формы.
     * @throws IOException Если fxml-файл описания формы недоступен.
     */
    public static void prepareStage(
            Stage stage,
            URL faviconLocation,
            URL historyFormLocation,
            Object controller
    ) throws IOException {
        FXMLLoader historyFormLoader = new FXMLLoader(historyFormLocation);
        historyFormLoader.setController(controller);
        stage.setScene(new Scene(historyFormLoader.load()));
        stage.setTitle(HISTORY_WINDOW_TITLE);
        stage.getIcons().add(new Image(faviconLocation.toExternalForm()));
        stage.setResizable(false);
    }
}
