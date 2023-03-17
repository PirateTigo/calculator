package ru.sibsutis.piratetigo.calculator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.sibsutis.piratetigo.calculator.number.ANumber;
import ru.sibsutis.piratetigo.calculator.number.PNumber;

/**
 * Модульные тесты класса {@link Processor}.
 */
public class ProcessorTest {

    @Test
    void creation_default_ok() {
        // arrange
        ANumber leftOperand = new PNumber(10, 10, 2);
        ANumber rightOperand = new PNumber(10, 10, 2);

        // act
        Processor underTest = new Processor(leftOperand, rightOperand);

        // assert
        Assertions.assertTrue(underTest.getLeftOperand().equals(leftOperand));
        Assertions.assertTrue(underTest.getRightOperand().equals(rightOperand));
        Assertions.assertEquals(Processor.ProcessorCommand.NONE, underTest.getOperation());
        Assertions.assertEquals("", underTest.getError());
    }

    @Test
    void reset_default_ok() {
        // arrange
        ANumber leftOperand = new PNumber(10, 10, 2);
        ANumber rightOperand = new PNumber(10, 10, 2);
        Processor underTest = new Processor(leftOperand, rightOperand);

        // act
        underTest.reset();

        // assert
        Assertions.assertTrue(underTest.getLeftOperand().isZero());
        Assertions.assertTrue(underTest.getRightOperand().isZero());
        Assertions.assertEquals(Processor.ProcessorCommand.NONE, underTest.getOperation());
        Assertions.assertEquals("", underTest.getError());
    }

    @Test
    void setOperation_default_ok() {
        // arrange
        ANumber leftOperand = new PNumber(10, 10, 2);
        ANumber rightOperand = new PNumber(10, 10, 2);
        Processor underTest = new Processor(leftOperand, rightOperand);

        // act
        underTest.setOperation(Processor.ProcessorCommand.ADD);
        underTest.resetOperation();
        Processor.ProcessorCommand actual = underTest.getOperation();

        // assert
        Assertions.assertEquals(Processor.ProcessorCommand.NONE, actual);
    }

    @Test
    void calculateOperation_add_ok() {
        // arrange
        ANumber leftOperand = new PNumber(10, 10, 2);
        ANumber rightOperand = new PNumber(10, 10, 2);
        Processor underTest = new Processor(leftOperand, rightOperand);

        // act
        underTest.setOperation(Processor.ProcessorCommand.ADD);
        underTest.calculateOperation();

        // assert
        Assertions.assertEquals("20", underTest.getLeftOperand().getNumberStr());
        Assertions.assertEquals("", underTest.getError());
    }

    @Test
    void calculateOperation_sub_ok() {
        // arrange
        ANumber leftOperand = new PNumber(10, 10, 2);
        ANumber rightOperand = new PNumber(10, 10, 2);
        Processor underTest = new Processor(leftOperand, rightOperand);

        // act
        underTest.setOperation(Processor.ProcessorCommand.SUB);
        underTest.calculateOperation();

        // assert
        Assertions.assertEquals("0", underTest.getLeftOperand().getNumberStr());
        Assertions.assertEquals("", underTest.getError());
    }

    @Test
    void calculateOperation_mul_ok() {
        // arrange
        ANumber leftOperand = new PNumber(10, 10, 2);
        ANumber rightOperand = new PNumber(10, 10, 2);
        Processor underTest = new Processor(leftOperand, rightOperand);

        // act
        underTest.setOperation(Processor.ProcessorCommand.MUL);
        underTest.calculateOperation();

        // assert
        Assertions.assertEquals("100", underTest.getLeftOperand().getNumberStr());
        Assertions.assertEquals("", underTest.getError());
    }

    @Test
    void calculateOperation_div_ok() {
        // arrange
        ANumber leftOperand = new PNumber(10, 10, 2);
        ANumber rightOperand = new PNumber(10, 10, 2);
        Processor underTest = new Processor(leftOperand, rightOperand);

        // act
        underTest.setOperation(Processor.ProcessorCommand.DIV);
        underTest.calculateOperation();

        // assert
        Assertions.assertEquals("1", underTest.getLeftOperand().getNumberStr());
        Assertions.assertEquals("", underTest.getError());
    }

    @Test
    void calculateOperation_function_error() {
        // arrange
        ANumber leftOperand = new PNumber(10, 10, 2);
        ANumber rightOperand = new PNumber(10, 10, 2);
        Processor underTest = new Processor(leftOperand, rightOperand);

        // act
        underTest.setOperation(Processor.ProcessorCommand.SQUARE);
        underTest.calculateOperation();

        // assert
        Assertions.assertEquals("Incorrect operation", underTest.getError());
    }

    @Test
    void calculateOperation_badCalculation_error() {
        // arrange
        ANumber leftOperand = new PNumber(10, 10, 2);
        ANumber rightOperand = new PNumber(0, 10, 2);
        Processor underTest = new Processor(leftOperand, rightOperand);

        // act
        underTest.setOperation(Processor.ProcessorCommand.DIV);
        underTest.calculateOperation();

        // assert
        Assertions.assertEquals("Division by zero", underTest.getError());
    }

    @Test
    void calculateFunction_square_ok() {
        // arrange
        ANumber leftOperand = new PNumber(10, 10, 2);
        ANumber rightOperand = new PNumber(10, 10, 2);
        Processor underTest = new Processor(leftOperand, rightOperand);

        // act
        underTest.calculateFunction(Processor.ProcessorCommand.SQUARE);

        // assert
        Assertions.assertEquals("100", underTest.getRightOperand().getNumberStr());
        Assertions.assertEquals("", underTest.getError());
    }

    @Test
    void calculateFunction_revert_ok() {
        // arrange
        ANumber leftOperand = new PNumber(10, 10, 2);
        ANumber rightOperand = new PNumber(10, 10, 2);
        Processor underTest = new Processor(leftOperand, rightOperand);

        // act
        underTest.calculateFunction(Processor.ProcessorCommand.REVERT);

        // assert
        Assertions.assertEquals("0.1", underTest.getRightOperand().getNumberStr());
        Assertions.assertEquals("", underTest.getError());
    }

    @Test
    void calculateFunction_noFunction_error() {
        // arrange
        ANumber leftOperand = new PNumber(10, 10, 2);
        ANumber rightOperand = new PNumber(10, 10, 2);
        Processor underTest = new Processor(leftOperand, rightOperand);

        // act
        underTest.calculateFunction(Processor.ProcessorCommand.ADD);

        // assert
        Assertions.assertEquals("Incorrect function", underTest.getError());
    }

    @Test
    void calculateFunction_badCalculation_error() {
        // arrange
        ANumber leftOperand = new PNumber(10, 10, 2);
        ANumber rightOperand = new PNumber(0, 10, 2);
        Processor underTest = new Processor(leftOperand, rightOperand);

        // act
        underTest.calculateFunction(Processor.ProcessorCommand.REVERT);

        // assert
        Assertions.assertEquals("Division by zero", underTest.getError());
    }

    @Test
    void setLeftOperand_default_ok() {
        // arrange
        ANumber leftOperand = new PNumber(0, 10, 2);
        ANumber nextLeftOperand = new PNumber(10, 10, 2);
        Processor underTest = new Processor(leftOperand, leftOperand);

        // act
        underTest.setLeftOperand(nextLeftOperand);

        // assert
        Assertions.assertEquals("10", underTest.getLeftOperand().getNumberStr());
    }

    @Test
    void setRightOperand_default_ok() {
        // arrange
        ANumber rightOperand = new PNumber(0, 10, 2);
        ANumber nextRightOperand = new PNumber(10, 10, 2);
        Processor underTest = new Processor(rightOperand, rightOperand);

        // act
        underTest.setRightOperand(nextRightOperand);

        // assert
        Assertions.assertEquals("10", underTest.getRightOperand().getNumberStr());
    }

    @Test
    void resetError_default_ok() {
        // arrange
        ANumber leftOperand = new PNumber(10, 10, 2);
        ANumber rightOperand = new PNumber(0, 10, 2);
        Processor underTest = new Processor(leftOperand, rightOperand);

        // act
        underTest.calculateFunction(Processor.ProcessorCommand.REVERT);
        underTest.resetError();

        // assert
        Assertions.assertEquals("", underTest.getError());
    }

}
