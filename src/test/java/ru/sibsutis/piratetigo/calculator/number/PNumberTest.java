package ru.sibsutis.piratetigo.calculator.number;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.mockito.Mockito.mock;

/**
 * Модульные тесты класса {@link PNumber}.
 */
public class PNumberTest {

    @Test
    void creation_tooLowP_throws() {
        // arrange
        double number = 123.45;
        int p = 1;
        int precision = 2;

        // act
        Executable func = () -> new PNumber(number, p, precision);

        // assert
        Assertions.assertThrows(PNumber.PNumberException.class, func);
    }

    @Test
    void creation_tooHighP_throws() {
        // arrange
        double number = 123.45;
        int p = 17;
        int precision = 2;

        // act
        Executable func = () -> new PNumber(number, p, precision);

        // assert
        Assertions.assertThrows(PNumber.PNumberException.class, func);
    }

    @Test
    void creation_tooLowPrecision_throws() {
        // arrange
        double number = 123.45;
        int p = 2;
        int precision = -1;

        // act
        Executable func = () -> new PNumber(number, p, precision);

        // assert
        Assertions.assertThrows(PNumber.PNumberException.class, func);
    }

    @Test
    void creation_is_ok() {
        // arrange
        double number = 123.45;
        int p = 2;
        int precision = 2;

        // act
        PNumber actual = new PNumber(number, p, precision);

        // assert
        Assertions.assertEquals(number, actual.getNumber());
        Assertions.assertEquals(p, actual.getBase());
        Assertions.assertEquals(precision, actual.getPrecision());
    }

    @Test
    void creationStr_tooLowP_throws() {
        // arrange
        String number = "123.45";
        String p = "1";
        String precision = "2";

        // act
        Executable func = () -> new PNumber(number, p, precision);

        // assert
        Assertions.assertThrows(PNumber.PNumberException.class, func);
    }

    @Test
    void creationStr_tooHighP_throws() {
        // arrange
        String number = "123.45";
        String p = "17";
        String precision = "2";

        // act
        Executable func = () -> new PNumber(number, p, precision);

        // asserts
        Assertions.assertThrows(PNumber.PNumberException.class, func);
    }

    @Test
    void creationStr_notANumberP_throws() {
        // arrange
        String number = "123.45";
        String p = "p";
        String precision = "2";

        // act
        Executable func = () -> new PNumber(number, p, precision);

        // asserts
        Assertions.assertThrows(PNumber.PNumberException.class, func);
    }

    @Test
    void creationStr_tooLowPrecision_throws() {
        // arrange
        String number = "123.45";
        String p = "2";
        String precision = "-1";

        // act
        Executable func = () -> new PNumber(number, p, precision);

        // asserts
        Assertions.assertThrows(PNumber.PNumberException.class, func);
    }

    @Test
    void creationStr_notANumberPrecision_throws() {
        // arrange
        String number = "123.45";
        String p = "2";
        String precision = "precision";

        // act
        Executable func = () -> new PNumber(number, p, precision);

        // asserts
        Assertions.assertThrows(PNumber.PNumberException.class, func);
    }

    @Test
    void creationStr_notANumberCeil_throws() {
        // arrange
        String number = "123p";
        String p = "10";
        String precision = "2";

        // act
        Executable func = () -> new PNumber(number, p, precision);

        // assert
        Assertions.assertThrows(PNumber.PNumberException.class, func);
    }

    @Test
    void creationStr_notANumberFraction_throws() {
        // arrange
        String number = "123.A1";
        String p = "10";
        String precision = "2";

        // act
        Executable func = () -> new PNumber(number, p, precision);

        // assert
        Assertions.assertThrows(PNumber.PNumberException.class, func);
    }

    @Test
    void creationStr_nullNumber_throws() {
        // arrange
        String number = null;
        String p = "10";
        String precision = "2";

        // act
        Executable func = () -> new PNumber(number, p, precision);

        // assert
        Assertions.assertThrows(PNumber.PNumberException.class, func);
    }

    @Test
    void creationStr_badDot_throws() {
        // arrange
        String number = "123.";
        String p = "10";
        String precision = "2";

        // act
        Executable func = () -> new PNumber(number, p, precision);

        // assert
        Assertions.assertThrows(PNumber.PNumberException.class, func);
    }

    @Test
    void creationStr_is_ok() {
        // arrange
        String number = "123.45";
        String p = "10";
        String precision = "2";

        // act
        PNumber actual = new PNumber(number, p, precision);

        // assert
        Assertions.assertEquals(number, actual.getNumberStr());
        Assertions.assertEquals(p, actual.getBaseStr());
        Assertions.assertEquals(precision, actual.getPrecisionStr());
    }

    @Test
    void isZero_zero_true() {
        // arrange
        ANumber underTest = new PNumber(0, 2, 2);

        // act
        boolean actual = underTest.isZero();

        // assert
        Assertions.assertTrue(actual);
    }

    @Test
    void isZero_nonZero_false() {
        // arrange
        ANumber underTest = new PNumber(10, 2, 2);

        // act
        boolean actual = underTest.isZero();

        // assert
        Assertions.assertFalse(actual);
    }

    @Test
    void creation_copy_ok() {
        // arrange
        ANumber from = new PNumber(123.45, 2, 2);

        // act
        ANumber to = from.clone();

        // assert
        PNumber pNumberFrom = (PNumber) from;
        PNumber pNumberTo = (PNumber) to;
        Assertions.assertEquals(pNumberFrom.getNumber(), pNumberTo.getNumber());
        Assertions.assertEquals(pNumberFrom.getBase(), pNumberTo.getBase());
        Assertions.assertEquals(pNumberFrom.getPrecision(), pNumberTo.getPrecision());
    }

    @Test
    void add_is_ok() {
        // arrange
        PNumber left = new PNumber(123.45, 10, 2);
        PNumber right = new PNumber(876.54, 10, 2);

        // act
        ANumber actual = left.add(right);

        // assert
        Assertions.assertEquals("999.99", actual.getNumberStr());
    }

    @Test
    void add_badP_throws() {
        // arrange
        PNumber left = new PNumber(123.45, 10, 2);
        PNumber right = new PNumber(876.54, 2, 2);

        // act
        Executable func = () -> left.add(right);

        // assert
        Assertions.assertThrows(PNumber.PNumberException.class, func);
    }

    @Test
    void add_badPrecision_throws() {
        // arrange
        PNumber left = new PNumber(123.45, 10, 2);
        PNumber right = new PNumber(876.54, 10, 3);

        // act
        Executable func = () -> left.add(right);

        // assert
        Assertions.assertThrows(PNumber.PNumberException.class, func);
    }

    @Test
    void mul_is_ok() {
        // arrange
        PNumber left = new PNumber(1.23, 10, 2);
        PNumber right = new PNumber(2, 10, 2);

        // act
        ANumber actual = left.mul(right);

        // assert
        Assertions.assertEquals("2.46", actual.getNumberStr());
    }

    @Test
    void sub_is_ok() {
        // arrange
        PNumber left = new PNumber(1.23, 10, 2);
        PNumber right = new PNumber(0.23, 10, 2);

        // act
        ANumber actual = left.sub(right);

        // assert
        Assertions.assertEquals("1", actual.getNumberStr());
    }

    @Test
    void div_byZero_throws() {
        // arrange
        PNumber left = new PNumber(12.62, 10, 2);
        PNumber right = new PNumber(0, 10, 2);

        // act
        Executable func = () -> left.div(right);

        // assert
        Assertions.assertThrows(PNumber.PNumberException.class, func);
    }

    @Test
    void div_is_ok() {
        // arrange
        PNumber left = new PNumber(12.62, 10, 2);
        PNumber right = new PNumber(2, 10, 2);

        // act
        ANumber actual = left.div(right);

        // assert
        Assertions.assertEquals("6.31", actual.getNumberStr());
    }

    @Test
    void equals_anotherClass_false() {
        // arrange
        ANumber otherMock = mock(ANumber.class);
        ANumber underTest = new PNumber(10, 2, 2);

        // act
        boolean actual = underTest.equals(otherMock);

        // assert
        Assertions.assertFalse(actual);
    }

    @Test
    void equals_isEquals_true() {
        // arrange
        ANumber other = new PNumber(10, 2, 2);
        ANumber underTest = other.clone();

        // act
        boolean actual = underTest.equals(other);

        // assert
        Assertions.assertTrue(actual);
    }

    @Test
    void square_is_ok() {
        // arrange
        PNumber underTest = new PNumber(10, 10, 2);

        // act
        PNumber actual = underTest.square();

        // assert
        Assertions.assertEquals("100", actual.getNumberStr());
    }

    @Test
    void revert_is_ok() {
        // arrange
        PNumber underTest = new PNumber(0.1, 10, 2);

        // act
        PNumber actual = underTest.revert();

        // assert
        Assertions.assertEquals("10", actual.getNumberStr());
    }

    @Test
    void setBase_is_ok() {
        // arrange
        PNumber underTest = new PNumber(5, 10, 2);

        // act
        underTest.setBase(3);

        // assert
        Assertions.assertEquals("12", underTest.getNumberStr());
    }

    @Test
    void setBaseStr_is_ok() {
        // arrange
        PNumber underTest = new PNumber(5.5, 10, 2);

        // act
        underTest.setBaseStr("3");

        // assert
        Assertions.assertEquals("12.11", underTest.getNumberStr());
    }

    @Test
    void setPrecision_is_ok() {
        // arrange
        PNumber underTest = new PNumber(1.23, 10, 2);

        // act
        underTest.setPrecision(1);

        // assert
        Assertions.assertEquals("1.2", underTest.getNumberStr());
    }

    @Test
    void setPrecisionStr_is_ok() {
        // arrange
        PNumber underTest = new PNumber(1.23, 10, 2);

        // act
        underTest.setPrecisionStr("1");

        // assert
        Assertions.assertEquals("1.2", underTest.getNumberStr());
    }

    @Test
    void getNumberStr_negative_ok() {
        // arrange
        PNumber underTest = new PNumber("-1A.1A", "16", "2");

        // act
        String actual = underTest.getNumberStr();

        // assert
        Assertions.assertEquals("-1A.1A", actual);
    }

    @Test
    void getNumberStr_zero_ok() {
        // arrange
        PNumber underTest = new PNumber("0", "16", "0");

        // act
        String actual = underTest.getNumberStr();

        // assert
        Assertions.assertEquals("0", actual);
    }

}
