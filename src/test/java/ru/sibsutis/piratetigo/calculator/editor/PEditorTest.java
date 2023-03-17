package ru.sibsutis.piratetigo.calculator.editor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

/**
 * Модульные тесты класса {@link PEditor}.
 */
public class PEditorTest {

    @Test
    void creation_defaults_ok() {
        // arrange
        AEditor underTest = new PEditor();

        // act
        String actual = underTest.getNumberStr();

        // assert
        Assertions.assertEquals(AEditor.ZERO, actual);
    }

    @Test
    void edit_addZeroWithZero_isZero() {
        // arrange
        AEditor underTest = new PEditor();

        // act
        underTest.edit(AEditor.EditCommand.ZERO);

        // assert
        Assertions.assertTrue(underTest.isZero());
    }

    @Test
    void edit_addOne_one() {
        // arrange
        AEditor underTest = new PEditor();

        // act
        String actual = underTest.edit(AEditor.EditCommand.ONE);

        // assert
        Assertions.assertEquals("1", actual);
    }

    @Test
    void edit_addZeroWithNoZero_added() {
        // arrange
        AEditor underTest = new PEditor();

        // act
        underTest.edit(AEditor.EditCommand.ONE);
        String actual = underTest.edit(AEditor.EditCommand.ZERO);

        // assert
        Assertions.assertEquals("10", actual);
    }

    @Test
    void edit_addTwo_two() {
        // arrange
        AEditor underTest = new PEditor();

        // act
        String actual = underTest.edit(AEditor.EditCommand.TWO);

        // assert
        Assertions.assertEquals("2", actual);
    }

    @Test
    void edit_addThree_three() {
        // arrange
        AEditor underTest = new PEditor();

        // act
        String actual = underTest.edit(AEditor.EditCommand.THREE);

        // assert
        Assertions.assertEquals("3", actual);
    }

    @Test
    void edit_addFour_four() {
        // arrange
        AEditor underTest = new PEditor();

        // act
        String actual = underTest.edit(AEditor.EditCommand.FOUR);

        // assert
        Assertions.assertEquals("4", actual);
    }

    @Test
    void edit_addFive_five() {
        // arrange
        AEditor underTest = new PEditor();

        // act
        String actual = underTest.edit(AEditor.EditCommand.FIVE);

        // assert
        Assertions.assertEquals("5", actual);
    }

    @Test
    void edit_addSix_six() {
        // arrange
        AEditor underTest = new PEditor();

        // act
        String actual = underTest.edit(AEditor.EditCommand.SIX);

        // assert
        Assertions.assertEquals("6", actual);
    }

    @Test
    void edit_addSeven_seven() {
        // arrange
        AEditor underTest = new PEditor();

        // act
        String actual = underTest.edit(AEditor.EditCommand.SEVEN);

        // assert
        Assertions.assertEquals("7", actual);
    }

    @Test
    void edit_addEight_eight() {
        // arrange
        AEditor underTest = new PEditor();

        // act
        String actual = underTest.edit(AEditor.EditCommand.EIGHT);

        // assert
        Assertions.assertEquals("8", actual);
    }

    @Test
    void edit_addNine_nine() {
        // arrange
        AEditor underTest = new PEditor();

        // act
        String actual = underTest.edit(AEditor.EditCommand.NINE);

        // assert
        Assertions.assertEquals("9", actual);
    }

    @Test
    void edit_addTen_A() {
        // arrange
        AEditor underTest = new PEditor();

        // act
        String actual = underTest.edit(AEditor.EditCommand.A);

        // assert
        Assertions.assertEquals("A", actual);
    }

    @Test
    void edit_addEleven_B() {
        // arrange
        AEditor underTest = new PEditor();

        // act
        String actual = underTest.edit(AEditor.EditCommand.B);

        // assert
        Assertions.assertEquals("B", actual);
    }

    @Test
    void edit_addTwelve_C() {
        // arrange
        AEditor underTest = new PEditor();

        // act
        String actual = underTest.edit(AEditor.EditCommand.C);

        // assert
        Assertions.assertEquals("C", actual);
    }

    @Test
    void edit_addThirteen_D() {
        // arrange
        AEditor underTest = new PEditor();

        // act
        String actual = underTest.edit(AEditor.EditCommand.D);

        // assert
        Assertions.assertEquals("D", actual);
    }

    @Test
    void edit_addFourteen_E() {
        // arrange
        AEditor underTest = new PEditor();

        // act
        String actual = underTest.edit(AEditor.EditCommand.E);

        // assert
        Assertions.assertEquals("E", actual);
    }

    @Test
    void edit_addFifteen_F() {
        // arrange
        AEditor underTest = new PEditor();

        // act
        String actual = underTest.edit(AEditor.EditCommand.F);

        // assert
        Assertions.assertEquals("F", actual);
    }

    @Test
    void edit_signWithZero_zero() {
        // arrange
        AEditor underTest = new PEditor();

        // act
        String actual = underTest.edit(AEditor.EditCommand.SIGN);

        // assert
        Assertions.assertEquals(AEditor.ZERO, actual);
        Assertions.assertTrue(underTest.isZero());
    }

    @Test
    void edit_signWithPositive_negative() {
        // arrange
        AEditor underTest = new PEditor();

        // act
        underTest.addDigit(1);
        char actual = underTest.edit(AEditor.EditCommand.SIGN).charAt(0);

        // assert
        Assertions.assertEquals('-', actual);
    }

    @Test
    void edit_signWithNegative_positive() {
        // arrange
        AEditor underTest = new PEditor();

        // act
        underTest.setNumberStr("-1");
        String actual = underTest.edit(AEditor.EditCommand.SIGN);

        // assert
        Assertions.assertEquals("1", actual);
    }

    @Test
    void edit_fractionDelimiterWithCeil_added() {
        // arrange
        AEditor underTest = new PEditor();

        // act
        underTest.setNumberStr("123");
        String actual = underTest.edit(AEditor.EditCommand.FRACTION_DELIMITER);

        // assert
        Assertions.assertEquals("123.", actual);
    }

    @Test
    void edit_fractionDelimiterWithFraction_noAdded() {
        // arrange
        AEditor underTest = new PEditor();

        // act
        underTest.setNumberStr("123.5");
        String actual = underTest.edit(AEditor.EditCommand.FRACTION_DELIMITER);

        // assert
        Assertions.assertEquals("123.5", actual);
    }

    @Test
    void edit_backspaceWithOneSymbol_zero() {
        // arrange
        AEditor underTest = new PEditor();

        // act
        String actual = underTest.edit(AEditor.EditCommand.BACKSPACE);

        // assert
        Assertions.assertEquals(AEditor.ZERO, actual);
        Assertions.assertTrue(underTest.isZero());
    }

    @Test
    void edit_backspaceWithSeveralSymbol_ok() {
        // arrange
        AEditor underTest = new PEditor();

        // act
        underTest.setNumberStr("123.");
        String actual = underTest.edit(AEditor.EditCommand.BACKSPACE);

        // assert
        Assertions.assertEquals("123", actual);
    }

    @Test
    void edit_clearEnter_cleared() {
        // arrange
        AEditor underTest = new PEditor();

        // act
        underTest.setNumberStr("123");
        String actual = underTest.edit(AEditor.EditCommand.CLEAR_ENTER);

        // assert
        Assertions.assertEquals(AEditor.ZERO, actual);
        Assertions.assertTrue(underTest.isZero());
    }

    @Test
    void addDigit_toNonZero_ok() {
        // arrange
        AEditor underTest = new PEditor();

        // act
        underTest.setNumberStr("1");
        String actual = underTest.addDigit(1);

        // assert
        Assertions.assertEquals("11", actual);
    }

    @Test
    void addDigit_tooLowDigit_throws() {
        // arrange
        AEditor underTest = new PEditor();

        // act
        Executable func = () -> underTest.addDigit(-1);

        // assert
        Assertions.assertThrows(PEditor.PEditorException.class, func);
    }

    @Test
    void addDigit_tooHighDigit_throws() {
        // arrange
        AEditor underTest = new PEditor();

        // act
        Executable func = () -> underTest.addDigit(16);

        // assert
        Assertions.assertThrows(PEditor.PEditorException.class, func);
    }
}
