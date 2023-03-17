package ru.sibsutis.piratetigo.calculator.forms;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Контроллер формы окна истории вычислений.
 */
public class HistoryForm {

    /**
     * Основное список записей истории.
     */
    @FXML
    ListView<String> history;

    /**
     * Записи истории.
     */
    private final List<String> records = new ArrayList<>();

    /**
     * Добавляет запись в историю.
     *
     * @param record Запись.
     */
    public void add(String record) {
        records.add(record);
    }

    /**
     * Вызывается автоматически после загрузки формы.
     */
    @FXML
    @SuppressWarnings("unused")
    private void initialize() {
        history.getItems().addAll(records);
    }

}
