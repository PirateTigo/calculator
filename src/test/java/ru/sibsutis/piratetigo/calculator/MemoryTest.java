package ru.sibsutis.piratetigo.calculator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.sibsutis.piratetigo.calculator.number.ANumber;
import ru.sibsutis.piratetigo.calculator.number.PNumber;

/**
 * Модульные тесты класса {@link Memory}.
 */
public class MemoryTest {

    @Test
    void creation_default_ok() {
        // arrange
        ANumber value = new PNumber("1", "10", "2");

        // act
        Memory underTest = new Memory(value);

        // assert
        Assertions.assertTrue(value.equals(underTest.getValue()));
        Assertions.assertFalse(underTest.getState());
    }

    @Test
    void record_default_ok() {
        // arrange
        ANumber value = new PNumber(0, 10, 2);
        ANumber nextValue = new PNumber(10, 10, 2);
        Memory underTest = new Memory(value);

        // act
        underTest.record(nextValue);

        // assert
        Assertions.assertTrue(underTest.getValue().equals(nextValue));
        Assertions.assertTrue(underTest.getState());
    }

    @Test
    void addValue_default_ok() {
        // arrange
        ANumber value = new PNumber(0, 10, 2);
        ANumber nextValue = new PNumber(10, 10, 2);
        Memory underTest = new Memory(value);

        // act
        underTest.addValue(nextValue);

        // assert
        Assertions.assertEquals("10", underTest.getValueStr());
        Assertions.assertTrue(underTest.getState());
    }

    @Test
    void clear_default_ok() {
        // arrange
        ANumber value = new PNumber(0, 10, 2);
        ANumber nextValue = new PNumber(10, 10, 2);
        Memory underTest = new Memory(value);

        // act
        underTest.addValue(nextValue);
        underTest.clear();

        // assert
        Assertions.assertTrue(underTest.getValue().isZero());
        Assertions.assertFalse(underTest.getState());
    }
}
