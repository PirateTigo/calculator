package ru.sibsutis.piratetigo.calculator.number;

/**
 * Абстрактное число.
 */
public abstract class ANumber {

    /** Разделитель целой и дробной частей числа. */
    public static final String DELIMITER = ".";

    /** Строковое представление нуля. */
    public static final String ZERO = "0";

    /**
     * Вычисляет признак равенства нулю числа.
     *
     * @return Признак равенства нулю числа.
     */
    public abstract boolean isZero();

    /**
     * Выполняет копирование экземпляра числа.
     *
     * @return Копия экземпляра.
     */
    @Override
    public abstract ANumber clone();

    /**
     * Выполняет сложение с указанным числом.
     *
     * @param operand Второе слагаемое.
     * @return Результат сложения (новый экземпляр).
     */
    public abstract ANumber add(ANumber operand);

    /**
     * Выполняет вычитание указанного числа.
     *
     * @param operand Вычитаемое.
     * @return Результат вычитания (новый экземпляр).
     */
    public abstract ANumber sub(ANumber operand);

    /**
     * Выполняет умножение на указанное число.
     *
     * @param operand Второй множитель.
     * @return Результат умножения (новый экземпляр).
     */
    public abstract ANumber mul(ANumber operand);

    /**
     * Выполняет деление на указанное число.
     *
     * @param operand Делитель.
     * @return Результат деления (новый экземпляр).
     */
    public abstract ANumber div(ANumber operand);

    /**
     * Сравнивает текущее число с другим.
     *
     * @param other Другое число.
     * @return Признак равенства числа с другим.
     */
    public abstract boolean equals(ANumber other);

    /**
     * Возводит число в квадрат.
     */
    public abstract ANumber square();

    /**
     * Обращает число, вычисляя значение 1/x.
     */
    public abstract ANumber revert();

    /**
     * Возвращает строковое представление числа.
     */
    public abstract String getNumberStr();

    /**
     * Устанавливает значение числа из строки.
     *
     * @param value Новое значение числа.
     */
    public abstract void setNumberStr(String value);

}
