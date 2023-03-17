package ru.sibsutis.piratetigo.calculator;

import ru.sibsutis.piratetigo.calculator.number.ANumber;
import ru.sibsutis.piratetigo.calculator.number.PNumber;

/**
 * Процессор вычислений над абстрактными числами.
 */
public class Processor {

    /** Левый операнд или результат операции. */
    private ANumber leftOperandOrResult;

    /** Правый операнд. */
    private ANumber rightOperand;

    /** Выполняемая операция. */
    private ProcessorCommand operation;

    /** Ошибка при выполнении операции. */
    private String error;

    /**
     * Конструктор экземпляра процессора.
     *
     * @param leftOperand Левый операнд.
     * @param rightOperand Правый операнд.
     */
    public Processor(ANumber leftOperand, ANumber rightOperand) {
        this.leftOperandOrResult = leftOperand.clone();
        this.rightOperand = rightOperand.clone();
        operation = ProcessorCommand.NONE;
        error = "";
    }

    /**
     * Сбрасывает состояние процессора в начальное.
     * <p>
     * Обнуляются операнды, очищается текст ошибки,
     * сбрасывается выполняемая операция.
     */
    public void reset() {
        leftOperandOrResult.setNumberStr(ANumber.ZERO);
        rightOperand.setNumberStr(ANumber.ZERO);
        error = "";
        operation = ProcessorCommand.NONE;
    }

    /**
     * Сбрасывается выполняемая операция.
     */
    public void resetOperation() {
        operation = ProcessorCommand.NONE;
    }

    /**
     * Выполняет бинарную операцию над операндами.
     */
    public void calculateOperation() {
        try {
            switch (operation) {
                case ADD:
                    leftOperandOrResult = leftOperandOrResult.add(rightOperand);
                    break;
                case SUB:
                    leftOperandOrResult = leftOperandOrResult.sub(rightOperand);
                    break;
                case MUL:
                    leftOperandOrResult = leftOperandOrResult.mul(rightOperand);
                    break;
                case DIV:
                    leftOperandOrResult = leftOperandOrResult.div(rightOperand);
                    break;
                default:
                    error = "Incorrect operation";
            }
        } catch (PNumber.PNumberException ex) {
            error = ex.getMessage();
        }
    }

    /**
     * Выполняет вычисление функции (унарной операции) над правым операндом.
     *
     * @param function Унарная операция
     *                 ({@code ProcessorCommand.SQUARE} или {@code ProcessorCommand.REVERT}).
     */
    public void calculateFunction(ProcessorCommand function) {
        try {
            switch (function) {
                case SQUARE:
                    rightOperand = rightOperand.square();
                    break;
                case REVERT:
                    rightOperand = rightOperand.revert();
                    break;
                default:
                    error = "Incorrect function";
            }
        } catch (PNumber.PNumberException ex) {
            error = ex.getMessage();
        }
    }

    /**
     * Получает левый операнд.
     *
     * @return Копия левого операнда.
     */
    public ANumber getLeftOperand() {
        return leftOperandOrResult.clone();
    }

    /**
     * Устанавливает значение левого операнда.
     *
     * @param leftOperand Значение левого операнда.
     */
    public void setLeftOperand(ANumber leftOperand) {
        leftOperandOrResult = leftOperand.clone();
    }

    /**
     * Получает правый операнд.
     *
     * @return Копия правого операнда.
     */
    public ANumber getRightOperand() {
        return rightOperand.clone();
    }

    /**
     * Устанавливает значение правого операнда.
     *
     * @param rightOperand Значение правого операнда.
     */
    public void setRightOperand(ANumber rightOperand) {
        this.rightOperand = rightOperand.clone();
    }

    /**
     * Возвращает значение текущей операции.
     *
     * @return Текущая операция.
     */
    public ProcessorCommand getOperation() {
        return operation;
    }

    /**
     * Устанавливает значение текущей операции.
     *
     * @param operation Текущая операция.
     */
    public void setOperation(ProcessorCommand operation) {
        this.operation = operation;
    }

    /**
     * Возвращает значение ошибки процессора.
     *
     * @return Ошибка процессора.
     */
    public String getError() {
        return error;
    }

    /**
     * Сбрасывает ошибку процессора.
     */
    public void resetError() {
        error = "";
    }

    /**
     * Команды управления процессором.
     */
    public enum ProcessorCommand {
        NONE,
        ADD,
        SUB,
        MUL,
        DIV,
        SQUARE,
        REVERT
    }

}
