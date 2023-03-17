package ru.sibsutis.piratetigo.calculator;

import javafx.application.Platform;
import org.junit.jupiter.api.*;
import ru.sibsutis.piratetigo.calculator.editor.AEditor;
import ru.sibsutis.piratetigo.calculator.editor.PEditor;
import ru.sibsutis.piratetigo.calculator.forms.HistoryForm;
import ru.sibsutis.piratetigo.calculator.number.ANumber;
import ru.sibsutis.piratetigo.calculator.number.PNumber;

import static org.mockito.Mockito.mock;

/**
 * Модульные тесты класса {@link Controller}.
 */
public class ControllerTest {

    private HistoryForm historyFormMock;

    @BeforeAll
    static void setUpCLass() {
        try {
            Platform.startup(() -> {
            });
        } catch (IllegalStateException ex) {
            // Платформа JavaFX уже инициализирована
        }
    }

    @BeforeEach
    void setUp() {
        historyFormMock = mock(HistoryForm.class);
    }

    @Test
    void creation_default_ok() {
        // arrange
        ANumber initialValue = new PNumber(0, 10, 2);
        AEditor editor = new PEditor();

        // act
        Controller underTest = new Controller(initialValue, editor, historyFormMock);

        // assert
        Assertions.assertEquals(Controller.State.START, underTest.getState());
        Assertions.assertFalse(underTest.getMemoryState());
        Assertions.assertEquals(editor.getNumberStr(), initialValue.getNumberStr());
    }

    @Test
    void handleCommand_fractionDelimiter_ok() {
        // arrange
        ANumber initialValue = new PNumber(0, 10, 2);
        AEditor editor = new PEditor();
        Controller underTest = new Controller(initialValue, editor, historyFormMock);

        // act
        underTest.handleCommand(Controller.Command.FRACTION_DELIMITER);

        // assert
        Assertions.assertEquals("0.", editor.getNumberStr());
        Assertions.assertEquals(Controller.State.EDITING, underTest.getState());
    }

    @Test
    void handleCommand_ms_ok() {
        // arrange
        ANumber initialValue = new PNumber(0, 10, 2);
        AEditor editor = new PEditor();
        Controller underTest = new Controller(initialValue, editor, historyFormMock);

        // act
        underTest.handleCommand(Controller.Command.ONE);
        underTest.handleCommand(Controller.Command.MS);

        // assert
        Assertions.assertEquals("1", editor.getNumberStr());
        Assertions.assertEquals(Controller.State.VALUE_ENTERED, underTest.getState());
        Assertions.assertTrue(underTest.getMemoryState());
    }

    @Test
    void handleCommand_mr_ok() {
        // arrange
        ANumber initialValue = new PNumber(0, 10, 2);
        AEditor editor = new PEditor();
        Controller underTest = new Controller(initialValue, editor, historyFormMock);
        underTest.handleCommand(Controller.Command.ONE);
        underTest.handleCommand(Controller.Command.MS);
        underTest.handleCommand(Controller.Command.ZERO);

        // act
        underTest.handleCommand(Controller.Command.MR);

        // assert
        Assertions.assertEquals("1", editor.getNumberStr());
        Assertions.assertEquals(Controller.State.EDITING, underTest.getState());
        Assertions.assertTrue(underTest.getMemoryState());
    }

    @Test
    void handleCommand_mc_ok() {
        // arrange
        ANumber initialValue = new PNumber(0, 10, 2);
        AEditor editor = new PEditor();
        Controller underTest = new Controller(initialValue, editor, historyFormMock);
        underTest.handleCommand(Controller.Command.ONE);
        underTest.handleCommand(Controller.Command.MS);

        // act
        underTest.handleCommand(Controller.Command.MC);

        // assert
        Assertions.assertFalse(underTest.getMemoryState());
    }

    @Test
    void handleCommand_mPlus_ok() {
        // arrange
        ANumber initialValue = new PNumber(0, 10, 2);
        AEditor editor = new PEditor();
        Controller underTest = new Controller(initialValue, editor, historyFormMock);
        underTest.handleCommand(Controller.Command.TWO);
        underTest.handleCommand(Controller.Command.MS);
        underTest.handleCommand(Controller.Command.THREE);

        // act
        underTest.handleCommand(Controller.Command.M_PLUS);
        underTest.handleCommand(Controller.Command.BACKSPACE);
        underTest.handleCommand(Controller.Command.MR);

        // assert
        Assertions.assertEquals("25", editor.getNumberStr());
        Assertions.assertEquals(Controller.State.EDITING, underTest.getState());
        Assertions.assertTrue(underTest.getMemoryState());
    }

    @Test
    void handleCommand_addFirstPart_ok() {
        // arrange
        ANumber initialValue = new PNumber(0, 10, 2);
        AEditor editor = new PEditor();
        Controller underTest = new Controller(initialValue, editor, historyFormMock);

        // act
        underTest.handleCommand(Controller.Command.FOUR);
        underTest.handleCommand(Controller.Command.ADD);

        // assert
        Assertions.assertEquals("4", editor.getNumberStr());
        Assertions.assertEquals(Controller.State.OPERATION_CHANGED, underTest.getState());
    }

    @Test
    void handleCommand_subSecondPart_ok() {
        // arrange
        ANumber initialValue = new PNumber(0, 10, 2);
        AEditor editor = new PEditor();
        Controller underTest = new Controller(initialValue, editor, historyFormMock);

        // act
        underTest.handleCommand(Controller.Command.FIVE);
        underTest.handleCommand(Controller.Command.SUB);
        underTest.handleCommand(Controller.Command.SIX);
        underTest.handleCommand(Controller.Command.RESULT);

        // assert
        Assertions.assertEquals("-1", editor.getNumberStr());
        Assertions.assertEquals(Controller.State.OPERATION_DONE, underTest.getState());
    }

    @Test
    void handleCommand_clear_ok() {
        // arrange
        ANumber initialValue = new PNumber(0, 10, 2);
        AEditor editor = new PEditor();
        Controller underTest = new Controller(initialValue, editor, historyFormMock);
        underTest.handleCommand(Controller.Command.SEVEN);
        underTest.handleCommand(Controller.Command.MUL);
        underTest.handleCommand(Controller.Command.EIGHT);
        underTest.handleCommand(Controller.Command.RESULT);

        // act
        underTest.handleCommand(Controller.Command.CLEAR);

        // assert
        Assertions.assertEquals(ANumber.ZERO, editor.getNumberStr());
        Assertions.assertEquals(Controller.State.START, underTest.getState());
    }

    @Test
    void handleCommand_clearEnter_ok() {
        // arrange
        ANumber initialValue = new PNumber(0, 16, 2);
        AEditor editor = new PEditor();
        Controller underTest = new Controller(initialValue, editor, historyFormMock);
        underTest.handleCommand(Controller.Command.NINE);
        underTest.handleCommand(Controller.Command.DIV);
        underTest.handleCommand(Controller.Command.A);

        // act
        underTest.handleCommand(Controller.Command.CLEAR_ENTER);

        // assert
        Assertions.assertEquals(ANumber.ZERO, editor.getNumberStr());
        Assertions.assertEquals(Controller.State.EDITING, underTest.getState());
    }

    @Test
    void handleCommand_clearEnterFirstEditing_ok() {
        // arrange
        ANumber initialValue = new PNumber(0, 16, 2);
        AEditor editor = new PEditor();
        Controller underTest = new Controller(initialValue, editor, historyFormMock);
        underTest.handleCommand(Controller.Command.B);

        // act
        underTest.handleCommand(Controller.Command.CLEAR_ENTER);

        // assert
        Assertions.assertEquals(ANumber.ZERO, editor.getNumberStr());
        Assertions.assertEquals(Controller.State.START, underTest.getState());
    }

    @Test
    void handleCommand_square_ok() {
        // arrange
        ANumber initialValue = new PNumber(0, 16, 2);
        AEditor editor = new PEditor();
        Controller underTest = new Controller(initialValue, editor, historyFormMock);
        underTest.handleCommand(Controller.Command.C);

        // act
        underTest.handleCommand(Controller.Command.SQUARE);

        // assert
        Assertions.assertEquals("90", editor.getNumberStr());
        Assertions.assertEquals(Controller.State.FUNCTION_DONE, underTest.getState());
    }

    @Test
    void handleCommand_revert_ok() {
        // arrange
        ANumber initialValue = new PNumber(0, 16, 2);
        AEditor editor = new PEditor();
        Controller underTest = new Controller(initialValue, editor, historyFormMock);
        underTest.handleCommand(Controller.Command.D);
        underTest.handleCommand(Controller.Command.ADD);
        underTest.handleCommand(Controller.Command.E);

        // act
        underTest.handleCommand(Controller.Command.REVERT);

        // assert
        Assertions.assertEquals("0.12", editor.getNumberStr());
        Assertions.assertEquals(Controller.State.FUNCTION_DONE, underTest.getState());
    }

    @Test
    void handleCommand_signEditing_ok() {
        // arrange
        ANumber initialValue = new PNumber(0, 16, 2);
        AEditor editor = new PEditor();
        Controller underTest = new Controller(initialValue, editor, historyFormMock);
        underTest.handleCommand(Controller.Command.ONE);

        // act
        underTest.handleCommand(Controller.Command.SIGN);

        // assert
        Assertions.assertEquals("-1", editor.getNumberStr());
        Assertions.assertEquals(Controller.State.EDITING, underTest.getState());
    }

    @Test
    void handleCommand_signSecondEditing_ok() {
        // arrange
        ANumber initialValue = new PNumber(0, 16, 2);
        AEditor editor = new PEditor();
        Controller underTest = new Controller(initialValue, editor, historyFormMock);
        underTest.handleCommand(Controller.Command.ONE);
        underTest.handleCommand(Controller.Command.ADD);
        underTest.handleCommand(Controller.Command.TWO);

        // act
        underTest.handleCommand(Controller.Command.SIGN);

        // assert
        Assertions.assertEquals("-2", editor.getNumberStr());
        Assertions.assertEquals(Controller.State.EDITING, underTest.getState());
    }

    @Test
    void handleCommand_signResult_ok() {
        // arrange
        ANumber initialValue = new PNumber(0, 16, 2);
        AEditor editor = new PEditor();
        Controller underTest = new Controller(initialValue, editor, historyFormMock);
        underTest.handleCommand(Controller.Command.E);
        underTest.handleCommand(Controller.Command.SUB);
        underTest.handleCommand(Controller.Command.F);
        underTest.handleCommand(Controller.Command.RESULT);

        // act
        underTest.handleCommand(Controller.Command.SIGN);

        // assert
        Assertions.assertEquals("1", editor.getNumberStr());
        Assertions.assertEquals(Controller.State.EDITING, underTest.getState());
    }

    @Test
    void handleCommand_mrStarting_ok() {
        // arrange
        ANumber initialValue = new PNumber(0, 16, 2);
        AEditor editor = new PEditor();
        Controller underTest = new Controller(initialValue, editor, historyFormMock);
        underTest.handleCommand(Controller.Command.E);
        underTest.handleCommand(Controller.Command.MS);
        underTest.handleCommand(Controller.Command.CLEAR_ENTER);

        // act
        underTest.handleCommand(Controller.Command.MR);

        // assert
        Assertions.assertEquals("E", editor.getNumberStr());
        Assertions.assertEquals(Controller.State.EDITING, underTest.getState());
    }

    @Test
    void handleCommand_resultFirstEditing_ok() {
        // arrange
        ANumber initialValue = new PNumber(0, 16, 2);
        AEditor editor = new PEditor();
        Controller underTest = new Controller(initialValue, editor, historyFormMock);
        underTest.handleCommand(Controller.Command.E);

        // act
        underTest.handleCommand(Controller.Command.RESULT);

        // assert
        Assertions.assertEquals("E", editor.getNumberStr());
        Assertions.assertEquals(Controller.State.EXPRESSION_DONE, underTest.getState());
    }

    @Test
    void handleCommand_addAfterOperationChanged_ok() {
        // arrange
        ANumber initialValue = new PNumber(0, 16, 2);
        AEditor editor = new PEditor();
        Controller underTest = new Controller(initialValue, editor, historyFormMock);
        underTest.handleCommand(Controller.Command.ONE);
        underTest.handleCommand(Controller.Command.ADD);

        // act
        underTest.handleCommand(Controller.Command.ADD);

        // assert
        Assertions.assertEquals("1", editor.getNumberStr());
        Assertions.assertEquals(Controller.State.OPERATION_CHANGED, underTest.getState());
    }

    @Test
    void handleCommand_subAfterOperationChanged_ok() {
        // arrange
        ANumber initialValue = new PNumber(0, 16, 2);
        AEditor editor = new PEditor();
        Controller underTest = new Controller(initialValue, editor, historyFormMock);
        underTest.handleCommand(Controller.Command.ONE);
        underTest.handleCommand(Controller.Command.ADD);

        // act
        underTest.handleCommand(Controller.Command.SUB);

        // assert
        Assertions.assertEquals("1", editor.getNumberStr());
        Assertions.assertEquals(Controller.State.OPERATION_CHANGED, underTest.getState());
    }

    @Test
    void handleCommand_mulAfterOperationChanged_ok() {
        // arrange
        ANumber initialValue = new PNumber(0, 16, 2);
        AEditor editor = new PEditor();
        Controller underTest = new Controller(initialValue, editor, historyFormMock);
        underTest.handleCommand(Controller.Command.ONE);
        underTest.handleCommand(Controller.Command.ADD);

        // act
        underTest.handleCommand(Controller.Command.MUL);

        // assert
        Assertions.assertEquals("1", editor.getNumberStr());
        Assertions.assertEquals(Controller.State.OPERATION_CHANGED, underTest.getState());
    }

    @Test
    void handleCommand_divAfterOperationChanged_ok() {
        // arrange
        ANumber initialValue = new PNumber(0, 16, 2);
        AEditor editor = new PEditor();
        Controller underTest = new Controller(initialValue, editor, historyFormMock);
        underTest.handleCommand(Controller.Command.ONE);
        underTest.handleCommand(Controller.Command.ADD);

        // act
        underTest.handleCommand(Controller.Command.DIV);

        // assert
        Assertions.assertEquals("1", editor.getNumberStr());
        Assertions.assertEquals(Controller.State.OPERATION_CHANGED, underTest.getState());
    }

    @Test
    void handleCommand_resultShort_ok() {
        // arrange
        ANumber initialValue = new PNumber(0, 16, 2);
        AEditor editor = new PEditor();
        Controller underTest = new Controller(initialValue, editor, historyFormMock);
        underTest.handleCommand(Controller.Command.ONE);
        underTest.handleCommand(Controller.Command.ADD);

        // act
        underTest.handleCommand(Controller.Command.RESULT);

        // assert
        Assertions.assertEquals("2", editor.getNumberStr());
        Assertions.assertEquals(Controller.State.OPERATION_DONE, underTest.getState());
    }

    @Test
    void handleCommand_mrAfterOperationChanged_ok() {
        // arrange
        ANumber initialValue = new PNumber(0, 16, 2);
        AEditor editor = new PEditor();
        Controller underTest = new Controller(initialValue, editor, historyFormMock);
        underTest.handleCommand(Controller.Command.ONE);
        underTest.handleCommand(Controller.Command.ADD);

        // act
        underTest.handleCommand(Controller.Command.MS);
        underTest.handleCommand(Controller.Command.MR);

        // assert
        Assertions.assertEquals("1", editor.getNumberStr());
        Assertions.assertEquals(Controller.State.EDITING, underTest.getState());
    }

    @Test
    void handleCommand_clearAfterOperationChanged_ok() {
        // arrange
        ANumber initialValue = new PNumber(0, 16, 2);
        AEditor editor = new PEditor();
        Controller underTest = new Controller(initialValue, editor, historyFormMock);
        underTest.handleCommand(Controller.Command.ONE);
        underTest.handleCommand(Controller.Command.ADD);

        // act
        underTest.handleCommand(Controller.Command.CLEAR);

        // assert
        Assertions.assertEquals("0", editor.getNumberStr());
        Assertions.assertEquals(Controller.State.START, underTest.getState());
    }

    @Test
    void handleCommand_clearEnterAfterOperationChanged_ok() {
        // arrange
        ANumber initialValue = new PNumber(0, 16, 2);
        AEditor editor = new PEditor();
        Controller underTest = new Controller(initialValue, editor, historyFormMock);
        underTest.handleCommand(Controller.Command.ONE);
        underTest.handleCommand(Controller.Command.ADD);

        // act
        underTest.handleCommand(Controller.Command.CLEAR_ENTER);

        // assert
        Assertions.assertEquals("0", editor.getNumberStr());
        Assertions.assertEquals(Controller.State.EDITING, underTest.getState());
    }

    @Test
    void handleCommand_oneAfterOperationDone_ok() {
        // arrange
        ANumber initialValue = new PNumber(0, 16, 2);
        AEditor editor = new PEditor();
        Controller underTest = new Controller(initialValue, editor, historyFormMock);
        underTest.handleCommand(Controller.Command.ONE);
        underTest.handleCommand(Controller.Command.ADD);
        underTest.handleCommand(Controller.Command.TWO);
        underTest.handleCommand(Controller.Command.RESULT);

        // act
        underTest.handleCommand(Controller.Command.ONE);

        // assert
        Assertions.assertEquals("1", editor.getNumberStr());
        Assertions.assertEquals(Controller.State.EDITING, underTest.getState());
    }

    @Test
    void handleCommand_clearEnterAfterOperationDone_ok() {
        // arrange
        ANumber initialValue = new PNumber(0, 16, 2);
        AEditor editor = new PEditor();
        Controller underTest = new Controller(initialValue, editor, historyFormMock);
        underTest.handleCommand(Controller.Command.ONE);
        underTest.handleCommand(Controller.Command.ADD);
        underTest.handleCommand(Controller.Command.RESULT);

        // act
        underTest.handleCommand(Controller.Command.CLEAR_ENTER);

        // assert
        Assertions.assertEquals("0", editor.getNumberStr());
        Assertions.assertEquals(Controller.State.EDITING, underTest.getState());
    }

    @Test
    void handleCommand_squareAfterOperationDone_ok() {
        // arrange
        ANumber initialValue = new PNumber(0, 16, 2);
        AEditor editor = new PEditor();
        Controller underTest = new Controller(initialValue, editor, historyFormMock);
        underTest.handleCommand(Controller.Command.ONE);
        underTest.handleCommand(Controller.Command.ADD);
        underTest.handleCommand(Controller.Command.RESULT);

        // act
        underTest.handleCommand(Controller.Command.SQUARE);

        // assert
        Assertions.assertEquals("4", editor.getNumberStr());
        Assertions.assertEquals(Controller.State.FUNCTION_DONE, underTest.getState());
    }

    @Test
    void handleCommand_revertAfterOperationDone_ok() {
        // arrange
        ANumber initialValue = new PNumber(0, 16, 2);
        AEditor editor = new PEditor();
        Controller underTest = new Controller(initialValue, editor, historyFormMock);
        underTest.handleCommand(Controller.Command.ONE);
        underTest.handleCommand(Controller.Command.ADD);
        underTest.handleCommand(Controller.Command.RESULT);

        // act
        underTest.handleCommand(Controller.Command.REVERT);

        // assert
        Assertions.assertEquals("0.8", editor.getNumberStr());
        Assertions.assertEquals(Controller.State.FUNCTION_DONE, underTest.getState());
    }

    @Test
    void handleCommand_addAfterOperationDone_ok() {
        // arrange
        ANumber initialValue = new PNumber(0, 16, 2);
        AEditor editor = new PEditor();
        Controller underTest = new Controller(initialValue, editor, historyFormMock);
        underTest.handleCommand(Controller.Command.ONE);
        underTest.handleCommand(Controller.Command.ADD);
        underTest.handleCommand(Controller.Command.RESULT);

        // act
        underTest.handleCommand(Controller.Command.ADD);

        // assert
        Assertions.assertEquals("2", editor.getNumberStr());
        Assertions.assertEquals(Controller.State.OPERATION_CHANGED, underTest.getState());
    }

    @Test
    void handleCommand_subAfterOperationDone_ok() {
        // arrange
        ANumber initialValue = new PNumber(0, 16, 2);
        AEditor editor = new PEditor();
        Controller underTest = new Controller(initialValue, editor, historyFormMock);
        underTest.handleCommand(Controller.Command.ONE);
        underTest.handleCommand(Controller.Command.ADD);
        underTest.handleCommand(Controller.Command.RESULT);

        // act
        underTest.handleCommand(Controller.Command.SUB);

        // assert
        Assertions.assertEquals("2", editor.getNumberStr());
        Assertions.assertEquals(Controller.State.OPERATION_CHANGED, underTest.getState());
    }

    @Test
    void handleCommand_mulAfterOperationDone_ok() {
        // arrange
        ANumber initialValue = new PNumber(0, 16, 2);
        AEditor editor = new PEditor();
        Controller underTest = new Controller(initialValue, editor, historyFormMock);
        underTest.handleCommand(Controller.Command.ONE);
        underTest.handleCommand(Controller.Command.ADD);
        underTest.handleCommand(Controller.Command.RESULT);

        // act
        underTest.handleCommand(Controller.Command.MUL);

        // assert
        Assertions.assertEquals("2", editor.getNumberStr());
        Assertions.assertEquals(Controller.State.OPERATION_CHANGED, underTest.getState());
    }

    @Test
    void handleCommand_divAfterOperationDone_ok() {
        // arrange
        ANumber initialValue = new PNumber(0, 16, 2);
        AEditor editor = new PEditor();
        Controller underTest = new Controller(initialValue, editor, historyFormMock);
        underTest.handleCommand(Controller.Command.ONE);
        underTest.handleCommand(Controller.Command.ADD);
        underTest.handleCommand(Controller.Command.RESULT);

        // act
        underTest.handleCommand(Controller.Command.DIV);

        // assert
        Assertions.assertEquals("2", editor.getNumberStr());
        Assertions.assertEquals(Controller.State.OPERATION_CHANGED, underTest.getState());
    }

    @Test
    void handleCommand_mrAfterOperationDone_ok() {
        // arrange
        ANumber initialValue = new PNumber(0, 16, 2);
        AEditor editor = new PEditor();
        Controller underTest = new Controller(initialValue, editor, historyFormMock);
        underTest.handleCommand(Controller.Command.ONE);
        underTest.handleCommand(Controller.Command.ADD);
        underTest.handleCommand(Controller.Command.RESULT);

        // act
        underTest.handleCommand(Controller.Command.MS);
        underTest.handleCommand(Controller.Command.MR);

        // assert
        Assertions.assertEquals("2", editor.getNumberStr());
        Assertions.assertEquals(Controller.State.EDITING, underTest.getState());
    }

    @Test
    void handleCommand_resultAfterOperationDone_ok() {
        // arrange
        ANumber initialValue = new PNumber(0, 16, 2);
        AEditor editor = new PEditor();
        Controller underTest = new Controller(initialValue, editor, historyFormMock);
        underTest.handleCommand(Controller.Command.ONE);
        underTest.handleCommand(Controller.Command.ADD);
        underTest.handleCommand(Controller.Command.RESULT);

        // act
        underTest.handleCommand(Controller.Command.RESULT);

        // assert
        Assertions.assertEquals("3", editor.getNumberStr());
        Assertions.assertEquals(Controller.State.OPERATION_DONE, underTest.getState());
    }

    @Test
    void handleCommand_addWithSecondEditing_ok() {
        // arrange
        ANumber initialValue = new PNumber(0, 16, 2);
        AEditor editor = new PEditor();
        Controller underTest = new Controller(initialValue, editor, historyFormMock);
        underTest.handleCommand(Controller.Command.ONE);
        underTest.handleCommand(Controller.Command.ADD);
        underTest.handleCommand(Controller.Command.ONE);

        // act
        underTest.handleCommand(Controller.Command.ADD);

        // assert
        Assertions.assertEquals("2", editor.getNumberStr());
        Assertions.assertEquals(Controller.State.OPERATION_CHANGED, underTest.getState());
    }

    @Test
    void handleCommand_insertStart_ok() throws Throwable {
        FXBlock test = new FXBlock(() -> {
            // arrange
            ANumber initialValue = new PNumber(0, 16, 2);
            AEditor editor = new PEditor();
            Controller underTest = new Controller(initialValue, editor, historyFormMock);

            // act
            underTest.handleCommand(Controller.Command.COPY);
            underTest.handleCommand(Controller.Command.ONE);
            underTest.handleCommand(Controller.Command.INSERT);
            String actual = editor.getNumberStr();

            // assert
            Assertions.assertEquals("0", actual);
            Assertions.assertEquals(Controller.State.EDITING, underTest.getState());
        });
        test.run();
    }

    @Test
    void handleCommand_insertDone_ok() throws Throwable {
        FXBlock test = new FXBlock(() -> {
            // arrange
            ANumber initialValue = new PNumber(0, 16, 2);
            AEditor editor = new PEditor();
            Controller underTest = new Controller(initialValue, editor, historyFormMock);

            // act
            underTest.handleCommand(Controller.Command.ONE);
            underTest.handleCommand(Controller.Command.ADD);
            underTest.handleCommand(Controller.Command.COPY);
            underTest.handleCommand(Controller.Command.RESULT);
            underTest.handleCommand(Controller.Command.INSERT);
            String actual = editor.getNumberStr();

            // assert
            Assertions.assertEquals("1", actual);
            Assertions.assertEquals(Controller.State.EDITING, underTest.getState());
        });
        test.run();
    }

    @Test
    void handleCommand_clearWithEditing_ok() {
        // arrange
        ANumber initialValue = new PNumber(0, 16, 2);
        AEditor editor = new PEditor();
        Controller underTest = new Controller(initialValue, editor, historyFormMock);
        underTest.handleCommand(Controller.Command.ONE);

        // act
        underTest.handleCommand(Controller.Command.CLEAR);

        // assert
        Assertions.assertEquals("0", editor.getNumberStr());
        Assertions.assertEquals(Controller.State.START, underTest.getState());
    }

    @Test
    void handleCommand_divByZero_error() {
        // arrange
        ANumber initialValue = new PNumber(0, 16, 2);
        AEditor editor = new PEditor();
        Controller underTest = new Controller(initialValue, editor, historyFormMock);
        underTest.handleCommand(Controller.Command.ONE);
        underTest.handleCommand(Controller.Command.DIV);
        underTest.handleCommand(Controller.Command.ZERO);

        // act
        underTest.handleCommand(Controller.Command.RESULT);

        // assert
        Assertions.assertEquals("Division by zero", editor.getNumberStr());
        Assertions.assertEquals(Controller.State.ERROR, underTest.getState());
    }

}
