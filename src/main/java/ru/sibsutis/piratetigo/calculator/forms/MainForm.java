package ru.sibsutis.piratetigo.calculator.forms;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.sibsutis.piratetigo.calculator.Controller;
import ru.sibsutis.piratetigo.calculator.editor.AEditor;
import ru.sibsutis.piratetigo.calculator.editor.PEditor;
import ru.sibsutis.piratetigo.calculator.number.ANumber;
import ru.sibsutis.piratetigo.calculator.number.PNumber;
import ru.sibsutis.piratetigo.calculator.windows.AuthorWindow;
import ru.sibsutis.piratetigo.calculator.windows.HistoryWindow;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static ru.sibsutis.piratetigo.calculator.windows.AuthorWindow.AUTHOR_FORM_PATH;
import static ru.sibsutis.piratetigo.calculator.windows.HistoryWindow.HISTORY_FORM_PATH;

/**
 * Контроллер формы основного окна приложения.
 */
public class MainForm {

    private final static int NUMBER_PRECISION = 20;

    /**
     * URL размещения иконки приложения.
     */
    private URL faviconPath;

    /**
     * Контроллер формы информации об авторе.
     */
    private AuthorForm authorFormController;

    /**
     * Контроллер формы истории вычислений.
     */
    private HistoryForm historyFormController;

    /**
     * Список цифровых кнопок (для удобства обработки).
     */
    private List<Button> numbers;

    /**
     * Устройство управления калькулятором.
     */
    private Controller controller;

    /**
     * Состояние табло ввода-вывода.
     */
    private AEditor editor;

    /**
     * Контейнер визуальных компонентов формы.
     */
    @FXML
    AnchorPane root;

    /**
     * Поле вывода вычислений.
     */
    @FXML
    TextField output;

    /**
     * Поле, отображающее состояние памяти калькулятора
     * (включена - M/ выключена - пусто).
     */
    @FXML
    Label memoryUsed;

    /**
     * Кнопка "Забой".
     */
    @FXML
    Button backspace;

    /**
     * Кнопка "CE".
     */
    @FXML
    Button clearEnter;

    /**
     * Кнопка сброса калькулятора "C".
     */
    @FXML
    Button clear;

    /**
     * Кнопка "MC".
     */
    @FXML
    Button memoryClean;

    /**
     * Кнопка "MR".
     */
    @FXML
    Button memoryResult;

    /**
     * Кнопка "MS".
     */
    @FXML
    Button memorySave;

    /**
     * Кнопка "M+".
     */
    @FXML
    Button memoryAdd;

    /**
     * Кнопка "Sqr".
     */
    @FXML
    Button square;

    /**
     * Кнопка "1/x".
     */
    @FXML
    Button revert;

    /**
     * Кнопка "Сложить".
     */
    @FXML
    Button plus;

    /**
     * Кнопка "Вычесть".
     */
    @FXML
    Button minus;

    /**
     * Кнопка "Умножить".
     */
    @FXML
    Button multiple;

    /**
     * Кнопка "Разделить".
     */
    @FXML
    Button divide;

    /**
     * Кнопка "+/-".
     */
    @FXML
    Button sign;

    /**
     * Кнопка ",".
     */
    @FXML
    Button point;

    /**
     * Кнопка "Девять".
     */
    @FXML
    Button nine;

    /**
     * Кнопка "Восемь".
     */
    @FXML
    Button eight;

    /**
     * Кнопка "Семь".
     */
    @FXML
    Button seven;

    /**
     * Кнопка "Шесть".
     */
    @FXML
    Button six;

    /**
     * Кнопка "Пять".
     */
    @FXML
    Button five;

    /**
     * Кнопка "Четыре".
     */
    @FXML
    Button four;

    /**
     * Кнопка "Три".
     */
    @FXML
    Button three;

    /**
     * Кнопка "Два".
     */
    @FXML
    Button two;

    /**
     * Кнопка "Один".
     */
    @FXML
    Button one;

    /**
     * Кнопка "Ноль".
     */
    @FXML
    Button zero;

    /**
     * Кнопка "A".
     */
    @FXML
    Button aNumber;

    /**
     * Кнопка "B".
     */
    @FXML
    Button bNumber;

    /**
     * Кнопка "C".
     */
    @FXML
    Button cNumber;

    /**
     * Кнопка "D".
     */
    @FXML
    Button dNumber;

    /**
     * Кнопка "E".
     */
    @FXML
    Button eNumber;

    /**
     * Кнопка "F".
     */
    @FXML
    Button fNumber;

    /**
     * Кнопка "Результат".
     */
    @FXML
    Button result;

    /**
     * Основание системы счисления p-ичных чисел (от 2 до 16).
     */
    @FXML
    Spinner<Integer> base;

    /**
     * Устанавливает основное окно приложения.
     */
    public void setMainStage(Stage stage) {
        // Устанавливаем обработчик закрытия окна приложения
        stage.setOnCloseRequest(windowEvent -> Platform.exit());
    }

    /**
     * Устанавливает путь до иконки приложения.
     */
    public void setFaviconPath(URL faviconPath) {
        this.faviconPath = faviconPath;
    }

    /**
     * Обрабатывает кнопку меню "Автор".
     */
    @SuppressWarnings("unused")
    public void authorHandler() {
        try {
            Stage stage = new Stage();
            AuthorWindow.prepareStage(
                    stage,
                    faviconPath,
                    getClass().getResource(AUTHOR_FORM_PATH),
                    authorFormController
            );
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Обрабатывает кнопку меню "История".
     */
    @SuppressWarnings("unused")
    public void historyHandler() {
        try {
            Stage stage = new Stage();
            HistoryWindow.prepareStage(
                    stage,
                    faviconPath,
                    getClass().getResource(HISTORY_FORM_PATH),
                    historyFormController
            );
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Обрабатывает кнопку меню "Копировать".
     */
    public void copyHandler() {
        commandHandler(Controller.Command.COPY);
    }

    /**
     * Обрабатывает кнопку меню "Вставить".
     */
    public void insertHandler() {
        commandHandler(Controller.Command.INSERT);
    }

    /**
     * Вызывается автоматически после загрузки формы.
     */
    @FXML
    @SuppressWarnings("unused")
    private void initialize() {
        authorFormController = new AuthorForm();
        historyFormController = new HistoryForm();

        editor = new PEditor();
        controller = new Controller(
                new PNumber(0, 10, NUMBER_PRECISION),
                editor,
                historyFormController
        );

        // Устанавливаем обработчик нажатий кнопок клавиатуры
        root.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case DIGIT0:
                case NUMPAD0:
                    commandHandler(Controller.Command.ZERO);
                    break;
                case DIGIT1:
                case NUMPAD1:
                    commandHandler(Controller.Command.ONE);
                    break;
                case DIGIT2:
                case NUMPAD2:
                    if (!two.isDisabled()) {
                        commandHandler(Controller.Command.TWO);
                    }
                    break;
                case DIGIT3:
                case NUMPAD3:
                    if (!three.isDisabled()) {
                        commandHandler(Controller.Command.THREE);
                    }
                    break;
                case DIGIT4:
                case NUMPAD4:
                    if (!four.isDisabled()) {
                        commandHandler(Controller.Command.FOUR);
                    }
                    break;
                case DIGIT5:
                case NUMPAD5:
                    if (!five.isDisabled()) {
                        commandHandler(Controller.Command.FIVE);
                    }
                    break;
                case DIGIT6:
                case NUMPAD6:
                    if (!six.isDisabled()) {
                        commandHandler(Controller.Command.SIX);
                    }
                    break;
                case DIGIT7:
                case NUMPAD7:
                    if (!seven.isDisabled()) {
                        commandHandler(Controller.Command.SEVEN);
                    }
                    break;
                case DIGIT8:
                case NUMPAD8:
                    if (!eight.isDisabled()) {
                        commandHandler(Controller.Command.EIGHT);
                    }
                    break;
                case DIGIT9:
                case NUMPAD9:
                    if (!nine.isDisabled()) {
                        commandHandler(Controller.Command.NINE);
                    }
                    break;
                case A:
                    if (!aNumber.isDisabled()) {
                        commandHandler(Controller.Command.A);
                    }
                    break;
                case B:
                    if (!bNumber.isDisabled()) {
                        commandHandler(Controller.Command.B);
                    }
                    break;
                case C:
                    if (event.isControlDown()) {
                        copyHandler();
                    } else {
                        if (!cNumber.isDisabled()) {
                            commandHandler(Controller.Command.C);
                        }
                    }
                    break;
                case D:
                    if (!dNumber.isDisabled()) {
                        commandHandler(Controller.Command.D);
                    }
                    break;
                case E:
                    if (!eNumber.isDisabled()) {
                        commandHandler(Controller.Command.E);
                    }
                    break;
                case F:
                    if (!fNumber.isDisabled()) {
                        commandHandler(Controller.Command.F);
                    }
                    break;
                case V:
                    if (event.isControlDown()) {
                        insertHandler();
                    }
                    break;
                case DECIMAL:
                    commandHandler(Controller.Command.FRACTION_DELIMITER);
                    break;
                case BACK_SPACE:
                    commandHandler(Controller.Command.BACKSPACE);
                    break;
                case PLUS:
                case ADD:
                    commandHandler(Controller.Command.ADD);
                    break;
                case SUBTRACT:
                case MINUS:
                    commandHandler(Controller.Command.SUB);
                    break;
                case MULTIPLY:
                    commandHandler(Controller.Command.MUL);
                    break;
                case DIVIDE:
                    commandHandler(Controller.Command.DIV);
                    break;
                case ENTER:
                    commandHandler(Controller.Command.RESULT);
                    break;
                default:
            }
        });

        output.setText(ANumber.ZERO);

        // Устанавливаем обработчик сброса фокуса
        output.focusedProperty().addListener(
                (observable, oldValue, newValue) -> root.requestFocus());
        backspace.focusedProperty().addListener(
                (observable, oldValue, newValue) -> root.requestFocus());
        clearEnter.focusedProperty().addListener(
                (observable, oldValue, newValue) -> root.requestFocus());
        clear.focusedProperty().addListener(
                (observable, oldValue, newValue) -> root.requestFocus());
        memoryClean.focusedProperty().addListener(
                (observable, oldValue, newValue) -> root.requestFocus());
        memoryResult.focusedProperty().addListener(
                (observable, oldValue, newValue) -> root.requestFocus());
        memorySave.focusedProperty().addListener(
                (observable, oldValue, newValue) -> root.requestFocus());
        memoryAdd.focusedProperty().addListener(
                (observable, oldValue, newValue) -> root.requestFocus());
        square.focusedProperty().addListener(
                (observable, oldValue, newValue) -> root.requestFocus());
        revert.focusedProperty().addListener(
                (observable, oldValue, newValue) -> root.requestFocus());
        plus.focusedProperty().addListener(
                (observable, oldValue, newValue) -> root.requestFocus());
        minus.focusedProperty().addListener(
                (observable, oldValue, newValue) -> root.requestFocus());
        multiple.focusedProperty().addListener(
                (observable, oldValue, newValue) -> root.requestFocus());
        divide.focusedProperty().addListener(
                (observable, oldValue, newValue) -> root.requestFocus());
        sign.focusedProperty().addListener(
                (observable, oldValue, newValue) -> root.requestFocus());
        point.focusedProperty().addListener(
                (observable, oldValue, newValue) -> root.requestFocus());
        nine.focusedProperty().addListener(
                (observable, oldValue, newValue) -> root.requestFocus());
        eight.focusedProperty().addListener(
                (observable, oldValue, newValue) -> root.requestFocus());
        seven.focusedProperty().addListener(
                (observable, oldValue, newValue) -> root.requestFocus());
        six.focusedProperty().addListener(
                (observable, oldValue, newValue) -> root.requestFocus());
        five.focusedProperty().addListener(
                (observable, oldValue, newValue) -> root.requestFocus());
        four.focusedProperty().addListener(
                (observable, oldValue, newValue) -> root.requestFocus());
        three.focusedProperty().addListener(
                (observable, oldValue, newValue) -> root.requestFocus());
        two.focusedProperty().addListener(
                (observable, oldValue, newValue) -> root.requestFocus());
        one.focusedProperty().addListener(
                (observable, oldValue, newValue) -> root.requestFocus());
        zero.focusedProperty().addListener(
                (observable, oldValue, newValue) -> root.requestFocus());
        aNumber.focusedProperty().addListener(
                (observable, oldValue, newValue) -> root.requestFocus());
        bNumber.focusedProperty().addListener(
                (observable, oldValue, newValue) -> root.requestFocus());
        cNumber.focusedProperty().addListener(
                (observable, oldValue, newValue) -> root.requestFocus());
        dNumber.focusedProperty().addListener(
                (observable, oldValue, newValue) -> root.requestFocus());
        eNumber.focusedProperty().addListener(
                (observable, oldValue, newValue) -> root.requestFocus());
        fNumber.focusedProperty().addListener(
                (observable, oldValue, newValue) -> root.requestFocus());
        result.focusedProperty().addListener(
                (observable, oldValue, newValue) -> root.requestFocus());
        base.focusedProperty().addListener(
                (observable, oldValue, newValue) -> root.requestFocus());

        numbers = new ArrayList<>();
        numbers.add(zero);
        numbers.add(one);
        numbers.add(two);
        numbers.add(three);
        numbers.add(four);
        numbers.add(five);
        numbers.add(six);
        numbers.add(seven);
        numbers.add(eight);
        numbers.add(nine);
        numbers.add(aNumber);
        numbers.add(bNumber);
        numbers.add(cNumber);
        numbers.add(dNumber);
        numbers.add(eNumber);
        numbers.add(fNumber);

        // Устанавливаем обработчик изменения системы счисления
        base.valueProperty().addListener(
                (observable, oldValue, newValue) -> {
            for (int i = 2; i < newValue; i++) {
                numbers.get(i).setDisable(false);
            }

            for (int j = newValue; j < 16; j++) {
                numbers.get(j).setDisable(true);
            }
            editor.clear();
            controller = new Controller(
                    new PNumber(0, newValue, NUMBER_PRECISION),
                    editor,
                    historyFormController
            );
            output.setText(ANumber.ZERO);
        });

        // Устанавливаем обработчики кнопок
        backspace.setOnAction(event -> commandHandler(Controller.Command.BACKSPACE));
        clearEnter.setOnAction(event -> commandHandler(Controller.Command.CLEAR_ENTER));
        clear.setOnAction(event -> commandHandler(Controller.Command.CLEAR));
        memoryClean.setOnAction(event -> commandHandler(Controller.Command.MC));
        memoryResult.setOnAction(event -> commandHandler(Controller.Command.MR));
        memorySave.setOnAction(event -> commandHandler(Controller.Command.MS));
        memoryAdd.setOnAction(event -> commandHandler(Controller.Command.M_PLUS));
        square.setOnAction(event -> commandHandler(Controller.Command.SQUARE));
        revert.setOnAction(event -> commandHandler(Controller.Command.REVERT));
        plus.setOnAction(event -> commandHandler(Controller.Command.ADD));
        minus.setOnAction(event -> commandHandler(Controller.Command.SUB));
        multiple.setOnAction(event -> commandHandler(Controller.Command.MUL));
        divide.setOnAction(event -> commandHandler(Controller.Command.DIV));
        sign.setOnAction(event -> commandHandler(Controller.Command.SIGN));
        point.setOnAction(event -> commandHandler(Controller.Command.FRACTION_DELIMITER));
        zero.setOnAction(event -> commandHandler(Controller.Command.ZERO));
        one.setOnAction(event -> commandHandler(Controller.Command.ONE));
        two.setOnAction(event -> commandHandler(Controller.Command.TWO));
        three.setOnAction(event -> commandHandler(Controller.Command.THREE));
        four.setOnAction(event -> commandHandler(Controller.Command.FOUR));
        five.setOnAction(event -> commandHandler(Controller.Command.FIVE));
        six.setOnAction(event -> commandHandler(Controller.Command.SIX));
        seven.setOnAction(event -> commandHandler(Controller.Command.SEVEN));
        eight.setOnAction(event -> commandHandler(Controller.Command.EIGHT));
        nine.setOnAction(event -> commandHandler(Controller.Command.NINE));
        aNumber.setOnAction(event -> commandHandler(Controller.Command.A));
        bNumber.setOnAction(event -> commandHandler(Controller.Command.B));
        cNumber.setOnAction(event -> commandHandler(Controller.Command.C));
        dNumber.setOnAction(event -> commandHandler(Controller.Command.D));
        eNumber.setOnAction(event -> commandHandler(Controller.Command.E));
        fNumber.setOnAction(event -> commandHandler(Controller.Command.F));
        result.setOnAction(event -> commandHandler(Controller.Command.RESULT));
    }

    /**
     * Обработчик ввода команд.
     *
     * @param command Команда.
     */
    private void commandHandler(Controller.Command command) {
        controller.handleCommand(command);
        output.setText(editor.getNumberStr());
        if (controller.getMemoryState()) {
            memoryUsed.setText("M");
        } else {
            memoryUsed.setText("");
        }
    }

}
