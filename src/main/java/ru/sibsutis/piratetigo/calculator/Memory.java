package ru.sibsutis.piratetigo.calculator;

import ru.sibsutis.piratetigo.calculator.number.ANumber;

/**
 * Память для хранения абстрактного числа.
 */
public class Memory {

    /** Абстрактное число. */
    private ANumber number;

    /** Состояние памяти (включена - true, выключена - false). */
    private boolean enabled;

    /**
     * Конструктор экземпляра памяти.
     *
     * @param initialValue Начальное значение памяти.
     */
    public Memory(ANumber initialValue) {
        number = initialValue;
        enabled = false;
    }

    /**
     * Записывает в память очередное значение.
     * <p>
     * Внесение записи автоматически включает память.
     *
     * @param nextValue Значение.
     */
    public void record(ANumber nextValue) {
        number = nextValue.clone();
        enabled = true;
    }

    /**
     * Возвращает значение памяти.
     *
     * @return Значение памяти.
     */
    public ANumber getValue() {
        return number.clone();
    }

    /**
     * Добавляет к значению в памяти переданное значение.
     * <p>
     * Сложение автоматически включает память.
     *
     * @param operand Второе слагаемое.
     */
    public void addValue(ANumber operand) {
        number = number.add(operand);
        enabled = true;
    }

    /**
     * Очищает память.
     * <p>
     * Очищение автоматически выключает память.
     */
    public void clear() {
        if (enabled) {
            number.setNumberStr(ANumber.ZERO);
            enabled = false;
        }
    }

    /**
     * Возвращает состояние памяти.
     *
     * @return Состояние памяти (true - включена, false - выключена).
     */
    public boolean getState() {
        return enabled;
    }

    /**
     * Возвращает значение памяти в строковом представлении.
     *
     * @return Значение памяти в строковом представлении.
     */
    public String getValueStr() {
        return number.getNumberStr();
    }

}
