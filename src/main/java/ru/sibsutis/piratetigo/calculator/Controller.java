package ru.sibsutis.piratetigo.calculator;

import ru.sibsutis.piratetigo.calculator.editor.AEditor;
import ru.sibsutis.piratetigo.calculator.forms.HistoryForm;
import ru.sibsutis.piratetigo.calculator.number.ANumber;

/**
 * Управление универсальным калькулятором.
 */
public class Controller {

    /** Редактор абстрактных чисел. */
    private final AEditor editor;

    /** Процессор вычислений над абстрактными числами. */
    private final Processor processor;

    /** Память для хранения абстрактного числа. */
    private final Memory memory;

    /** Состояние устройства управления калькулятором. */
    private State state;

    /** Результат выполнения последней команды. */
    private ANumber number;

    /** Признак редактирования первого операнда. */
    private boolean firstEditing;

    /**
     * Форма истории вычислений.
     */
    private final HistoryForm historyForm;

    /**
     * Конструктор экземпляра устройства управления калькулятором.
     *
     * @param initialValue Экземпляр начального значения числа.
     * @param editor Редактор абстрактных чисел.
     * @param historyForm История вычислений.
     */
    public Controller(ANumber initialValue, AEditor editor, HistoryForm historyForm) {
        this.editor = editor;
        updateEditor(initialValue.clone());

        processor = new Processor(initialValue, initialValue);
        memory = new Memory(initialValue);
        state = State.START;
        firstEditing = true;
        this.historyForm = historyForm;
    }

    /**
     * Выполняет команду калькулятора.
     *
     * @param command Команда.
     */
    public void handleCommand(Command command) {
        switch (state) {
            case START:
            case EXPRESSION_DONE:
            case ERROR:
                handleStart(command);
                break;
            case EDITING:
            case VALUE_ENTERED:
            case FUNCTION_DONE:
                handleEditing(command);
                break;
            case OPERATION_CHANGED:
                handleOperationChanged(command);
                break;
            case OPERATION_DONE:
                handleOperationDone(command);
                break;
        }
    }

    /**
     * Возвращает состояние устройства управления калькулятором.
     *
     * @return Состояние устройства управления калькулятором.
     */
    public State getState() {
        return state;
    }

    /**
     * Возвращает состояние памяти калькулятора.
     *
     * @return Состояние памяти калькулятора (true - включена, false - выключена).
     */
    public boolean getMemoryState() {
        return memory.getState();
    }

    /**
     * Устанавливает начальное состояние устройства управления калькулятором.
     */
    public void setStartState() {
        editor.clear();
        state = State.START;
        firstEditing = true;
        processor.reset();
    }

    /**
     * Выполнить команду редактора.
     *
     * @param command Команда.
     */
    private void handleEditorCommand(Command command) {
        switch (command) {
            case FRACTION_DELIMITER:
                editor.edit(AEditor.EditCommand.FRACTION_DELIMITER);
                break;
            case SIGN:
                editor.edit(AEditor.EditCommand.SIGN);
                break;
            case BACKSPACE:
                editor.edit(AEditor.EditCommand.BACKSPACE);
                break;
            case ZERO:
                editor.edit(AEditor.EditCommand.ZERO);
                break;
            case ONE:
                editor.edit(AEditor.EditCommand.ONE);
                break;
            case TWO:
                editor.edit(AEditor.EditCommand.TWO);
                break;
            case THREE:
                editor.edit(AEditor.EditCommand.THREE);
                break;
            case FOUR:
                editor.edit(AEditor.EditCommand.FOUR);
                break;
            case FIVE:
                editor.edit(AEditor.EditCommand.FIVE);
                break;
            case SIX:
                editor.edit(AEditor.EditCommand.SIX);
                break;
            case SEVEN:
                editor.edit(AEditor.EditCommand.SEVEN);
                break;
            case EIGHT:
                editor.edit(AEditor.EditCommand.EIGHT);
                break;
            case NINE:
                editor.edit(AEditor.EditCommand.NINE);
                break;
            case A:
                editor.edit(AEditor.EditCommand.A);
                break;
            case B:
                editor.edit(AEditor.EditCommand.B);
                break;
            case C:
                editor.edit(AEditor.EditCommand.C);
                break;
            case D:
                editor.edit(AEditor.EditCommand.D);
                break;
            case E:
                editor.edit(AEditor.EditCommand.E);
                break;
            case F:
                editor.edit(AEditor.EditCommand.F);
                break;
        }
    }

    /**
     * Обрабатывает команду работы с памятью.
     *
     * @param command Команда.
     */
    private void handleMemoryCommand(Command command) {
        switch (command) {
            case MC:
                memory.clear();
                break;
            case MR:
                state = State.EDITING;
                editor.setNumberStr(memory.getValueStr());
                break;
            case MS:
                number.setNumberStr(editor.getNumberStr());
                memory.record(number);
                break;
            case M_PLUS:
                number.setNumberStr(editor.getNumberStr());
                memory.addValue(number);
                break;
        }
    }

    /**
     * Обрабатывает команды буфера обмена.
     *
     * @param command Команда.
     */
    private void handleClipBoardCommand(Command command) {
        switch (command) {
            case COPY:
                ClipBoard.setString(editor.getNumberStr());
                break;
            case INSERT:
                editor.setNumberStr(ClipBoard.getString());
                state = State.EDITING;
                break;
        }
    }

    /**
     * Обрабатывает начальное состояние.
     *
     * @param command Команда.
     */
    private void handleStart(Command command) {
        switch (command) {
            case FRACTION_DELIMITER:
            case ZERO:
            case ONE:
            case TWO:
            case THREE:
            case FOUR:
            case FIVE:
            case SIX:
            case SEVEN:
            case EIGHT:
            case NINE:
            case A:
            case B:
            case C:
            case D:
            case E:
            case F:
                state = State.EDITING;
                editor.clear();
                handleEditorCommand(command);
                break;
            case CLEAR:
            case CLEAR_ENTER:
                setStartState();
                break;
            case MC:
            case MR:
            case MS:
            case M_PLUS:
                handleMemoryCommand(command);
                break;
            case COPY:
            case INSERT:
                handleClipBoardCommand(command);
                break;
            default:
        }
    }

    /**
     * Обрабатывает состояние редактирования.
     *
     * @param command Команда.
     */
    private void handleEditing(Command command) {
        State oldState = state;
        state = State.EDITING;
        switch (command) {
            case FRACTION_DELIMITER:
            case SIGN:
            case BACKSPACE:
            case ZERO:
            case ONE:
            case TWO:
            case THREE:
            case FOUR:
            case FIVE:
            case SIX:
            case SEVEN:
            case EIGHT:
            case NINE:
            case A:
            case B:
            case C:
            case D:
            case E:
            case F:
                handleEditorCommand(command);
                break;
            case COPY:
            case INSERT:
                handleClipBoardCommand(command);
                break;
            case CLEAR:
                setStartState();
                break;
            case CLEAR_ENTER:
                editor.clear();
                if (firstEditing) {
                    state = State.START;
                }
                break;
            case SQUARE:
                calculateFunction(Processor.ProcessorCommand.SQUARE);
                break;
            case REVERT:
                calculateFunction(Processor.ProcessorCommand.REVERT);
                break;
            case ADD:
                calculateOperation(Processor.ProcessorCommand.ADD);
                break;
            case SUB:
                calculateOperation(Processor.ProcessorCommand.SUB);
                break;
            case MUL:
                calculateOperation(Processor.ProcessorCommand.MUL);
                break;
            case DIV:
                calculateOperation(Processor.ProcessorCommand.DIV);
                break;
            case MS:
            case M_PLUS:
                state = State.VALUE_ENTERED;
            case MC:
            case MR:
                handleMemoryCommand(command);
                break;
            case RESULT:
                number.setNumberStr(editor.getNumberStr());
                if (firstEditing) {
                    state = State.EXPRESSION_DONE;
                } else {
                    processor.setRightOperand(number);
                    calculateOperation();
                }
                break;
            default:
                state = oldState;
        }
    }

    /**
     * Обрабатывает состояние после изменения текущей операции.
     *
     * @param command Команда.
     */
    private void handleOperationChanged(Command command) {
        switch (command) {
            case FRACTION_DELIMITER:
            case ZERO:
            case ONE:
            case TWO:
            case THREE:
            case FOUR:
            case FIVE:
            case SIX:
            case SEVEN:
            case EIGHT:
            case NINE:
            case A:
            case B:
            case C:
            case D:
            case E:
            case F:
                state = State.EDITING;
                editor.clear();
                handleEditing(command);
                break;
            case COPY:
            case INSERT:
                handleClipBoardCommand(command);
                break;
            case CLEAR:
                setStartState();
                break;
            case CLEAR_ENTER:
                editor.clear();
                state = State.EDITING;
                break;
            case ADD:
                processor.setOperation(Processor.ProcessorCommand.ADD);
                break;
            case SUB:
                processor.setOperation(Processor.ProcessorCommand.SUB);
                break;
            case MUL:
                processor.setOperation(Processor.ProcessorCommand.MUL);
                break;
            case DIV:
                processor.setOperation(Processor.ProcessorCommand.DIV);
                break;
            case MS:
            case M_PLUS:
                state = State.VALUE_ENTERED;
            case MC:
            case MR:
                handleMemoryCommand(command);
                break;
            case RESULT:
                number.setNumberStr(editor.getNumberStr());
                processor.setRightOperand(number);
                calculateOperation();
                break;
            default:
        }
    }

    /**
     * Обрабатывает состояние после изменения текущей операции.
     *
     * @param command Команда.
     */
    private void handleOperationDone(Command command) {
        switch (command) {
            case FRACTION_DELIMITER:
            case ZERO:
            case ONE:
            case TWO:
            case THREE:
            case FOUR:
            case FIVE:
            case SIX:
            case SEVEN:
            case EIGHT:
            case NINE:
            case A:
            case B:
            case C:
            case D:
            case E:
            case F:
                state = State.EDITING;
                editor.clear();
                handleEditorCommand(command);
                break;
            case SIGN:
                state = State.EDITING;
                editor.edit(AEditor.EditCommand.SIGN);
                break;
            case COPY:
            case INSERT:
                handleClipBoardCommand(command);
                break;
            case CLEAR:
                setStartState();
                break;
            case CLEAR_ENTER:
                editor.clear();
                state = State.EDITING;
                break;
            case SQUARE:
                calculateFunction(Processor.ProcessorCommand.SQUARE);
                break;
            case REVERT:
                calculateFunction(Processor.ProcessorCommand.REVERT);
                break;
            case ADD:
                calculateOperation(Processor.ProcessorCommand.ADD);
                break;
            case SUB:
                calculateOperation(Processor.ProcessorCommand.SUB);
                break;
            case MUL:
                calculateOperation(Processor.ProcessorCommand.MUL);
                break;
            case DIV:
                calculateOperation(Processor.ProcessorCommand.DIV);
                break;
            case MS:
            case M_PLUS:
                state = State.VALUE_ENTERED;
            case MC:
            case MR:
                handleMemoryCommand(command);
                break;
            case RESULT:
                calculateOperation();
                break;
        }
    }

    /**
     * Обновляет значение результата вычисления и значение редактора.
     *
     * @param newValue Новое значение.
     */
    private void updateEditor(ANumber newValue) {
        number = newValue;
        editor.setNumberStr(number.getNumberStr());
    }

    /**
     * Вычисляет функцию.
     *
     * @param processorCommand Команда процессора.
     */
    private void calculateFunction(Processor.ProcessorCommand processorCommand) {
        number.setNumberStr(editor.getNumberStr());
        processor.setRightOperand(number);
        processor.calculateFunction(processorCommand);
        updateEditor(processor.getRightOperand());
        state = State.FUNCTION_DONE;
    }

    /**
     * Вычисляет операцию.
     *
     * @param processorCommand Операция.
     */
    private void calculateOperation(Processor.ProcessorCommand processorCommand) {
        number.setNumberStr(editor.getNumberStr());
        if (firstEditing) {
            processor.setLeftOperand(number);
        } else {
            processor.setRightOperand(number);
            calculateOperation();
            if (state == State.ERROR) {
                return;
            }
        }
        firstEditing = false;
        processor.setOperation(processorCommand);
        state = State.OPERATION_CHANGED;
    }

    /**
     * Вычисляет текущую операцию.
     */
    private void calculateOperation() {
        saveOperationToHistory();
        processor.calculateOperation();
        String error = processor.getError();
        if (error.equals("")) {
            historyForm.add(processor.getLeftOperand().getNumberStr());
            state = State.OPERATION_DONE;
            updateEditor(processor.getLeftOperand());
        } else {
            state = State.ERROR;
            historyForm.add(processor.getError());
            editor.setNumberStr(processor.getError());
        }
        firstEditing = true;
    }

    /**
     * Сохраняет в историю запись о выполняемой операции.
     */
    private void saveOperationToHistory() {
        String leftOperand = processor.getLeftOperand().getNumberStr();
        if (leftOperand.charAt(0) == '-') {
            leftOperand = "(" + leftOperand + ")";
        }
        String rightOperand = processor.getRightOperand().getNumberStr();
        if (rightOperand.charAt(0) == '-') {
            rightOperand = "(" + rightOperand + ")";
        }
        String operation = "";

        switch (processor.getOperation()) {
            case ADD:
                operation = " + ";
                break;
            case SUB:
                operation = " - ";
                break;
            case MUL:
                operation = " * ";
                break;
            case DIV:
                operation = " / ";
        }
        historyForm.add(leftOperand + operation + rightOperand + " =");
    }

    /**
     * Состояния устройства управления калькулятором.
     */
    public enum State {
        START,
        EDITING,
        FUNCTION_DONE,
        OPERATION_DONE,
        VALUE_ENTERED,
        OPERATION_CHANGED,
        EXPRESSION_DONE,
        ERROR
    }

    /**
     * Команда пользователя.
     */
    public enum Command {
        COPY,
        INSERT,
        BACKSPACE,
        CLEAR_ENTER,
        CLEAR,
        MC,
        MR,
        MS,
        M_PLUS,
        ZERO,
        ONE,
        TWO,
        THREE,
        FOUR,
        FIVE,
        SIX,
        SEVEN,
        EIGHT,
        NINE,
        A,
        B,
        C,
        D,
        E,
        F,
        SIGN,
        FRACTION_DELIMITER,
        ADD,
        SUB,
        MUL,
        DIV,
        SQUARE,
        REVERT,
        RESULT
    }

}
