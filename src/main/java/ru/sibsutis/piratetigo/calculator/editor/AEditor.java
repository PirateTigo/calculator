package ru.sibsutis.piratetigo.calculator.editor;

/**
 * Абстрактный редактор данных.
 */
public abstract class AEditor {

    /** Разделитель целой и дробной частей. */
    public static final String FRACTION_DELIMITER = ".";

    /** Строковое представление нуля. */
    public static final String ZERO = "0";

    /** Строковое представление редактируемого числа. */
    protected String numberStr = ZERO;

    /**
     * Определяет, является ли редактируемое число нулем.
     *
     * @return Признак нуля.
     */
    public abstract boolean isZero();

    /**
     * Изменить знак числа на противоположный.
     *
     * @return Строковое представление редактируемого числа.
     */
    public abstract String toggleSign();

    /**
     * Добавляет p-ичную цифру.
     *
     * @param pDigit Десятичное значение цифры в системе
     *               счисления с основанием p.
     * @return Строковое представление редактируемого числа.
     */
    public abstract String addDigit(int pDigit);

    /**
     * Добавляет ноль справа к редактируемому числу.
     *
     * @return Строковое представление редактируемого числа.
     */
    public abstract String addZero();

    /**
     * Добавляет разделитель целой и дробной частей
     * в редактируемое число справа.
     *
     * @return Строковое представление редактируемого числа.
     */
    public abstract String addDelimiter();

    /**
     * Забой символа, стоящего справа в редактируемом числе.
     *
     * @return Строковое представление редактируемого числа.
     */
    public abstract String backSpace();

    /**
     * Очищает редактируемое число.
     *
     * @return Строковое представление редактируемого числа.
     */
    public abstract String clear();

    /**
     * Чтение строкового представления редактируемого числа.
     *
     * @return Строковое представление редактируемого числа.
     */
    public String getNumberStr() {
        return numberStr;
    }

    /**
     * Запись строкового представления редактируемого числа.
     *
     * @param numberStr Строковое представление
     *                  редактируемого числа.
     */
    public void setNumberStr(String numberStr) {
        this.numberStr = numberStr;
    }

    /**
     * Редактирует строковое представление числа.
     *
     * @param editCommand Команда редактора.
     * @return Результат редактирования.
     */
    public String edit(EditCommand editCommand) {
        switch (editCommand) {
            case ZERO:
                numberStr = addZero();
                break;
            case ONE:
                numberStr = addDigit(1);
                break;
            case TWO:
                numberStr = addDigit(2);
                break;
            case THREE:
                numberStr = addDigit(3);
                break;
            case FOUR:
                numberStr = addDigit(4);
                break;
            case FIVE:
                numberStr = addDigit(5);
                break;
            case SIX:
                numberStr = addDigit(6);
                break;
            case SEVEN:
                numberStr = addDigit(7);
                break;
            case EIGHT:
                numberStr = addDigit(8);
                break;
            case NINE:
                numberStr = addDigit(9);
                break;
            case A:
                numberStr = addDigit(10);
                break;
            case B:
                numberStr = addDigit(11);
                break;
            case C:
                numberStr = addDigit(12);
                break;
            case D:
                numberStr = addDigit(13);
                break;
            case E:
                numberStr = addDigit(14);
                break;
            case F:
                numberStr = addDigit(15);
                break;
            case SIGN:
                numberStr = toggleSign();
                break;
            case FRACTION_DELIMITER:
                numberStr = addDelimiter();
                break;
            case BACKSPACE:
                numberStr = backSpace();
                break;
            case CLEAR_ENTER:
                numberStr = clear();
                break;
        }
        return numberStr;
    }

    /**
     * Команда редактора.
     */
    public enum EditCommand {
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
        BACKSPACE,
        CLEAR_ENTER
    }

}
