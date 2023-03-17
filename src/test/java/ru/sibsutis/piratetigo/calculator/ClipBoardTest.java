package ru.sibsutis.piratetigo.calculator;

import javafx.application.Platform;
import org.junit.jupiter.api.*;

/**
 * Модульные тесты для класса {@link ClipBoard}.
 */
public class ClipBoardTest {

    @BeforeAll
    static void setUp() {
        try {
            Platform.startup(() -> {
            });
        } catch (IllegalStateException ex) {
            // Платформа JavaFX уже инициализирована
        }
    }

    @Test
    void getString_default_ok() throws Throwable {
        FXBlock test = new FXBlock(() -> {
            // arrange
            String expected = "tested";

            // act
            ClipBoard.setString(expected);
            String actual = ClipBoard.getString();

            // assert
            Assertions.assertEquals(expected, actual);
        });
        test.run();
    }

}
