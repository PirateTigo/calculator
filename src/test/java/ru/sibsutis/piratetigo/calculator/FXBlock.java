package ru.sibsutis.piratetigo.calculator;

import javafx.application.Platform;

import java.util.concurrent.Semaphore;

/**
 * Выполняет блок кода, тестирующий JavaFX-компонент.
 */
public class FXBlock {

    /**
     * Выполняемый тест.
     */
    private final Runnable runnable;

    /**
     * Успешность выполнения.
     */
    private boolean success;

    /**
     * Исключение.
     */
    private Throwable error = null;

    /**
     * Конструктор экземпляра блока.
     *
     * @param runnable Выполняемый тест.
     */
    public FXBlock(Runnable runnable) {
        this.runnable = runnable;
    }

    /**
     * Выполняет тест.
     *
     * @throws Throwable Если произошла ошибка, она будет выброшена.
     */
    public void run() throws Throwable {
        success = false;
        Semaphore semaphore = new Semaphore(0);

        Platform.runLater(() -> {
            try {
                runnable.run();
                success = true;
            } catch (Throwable ex) {
                error = ex;
            } finally {
                semaphore.release();
            }
        });

        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!success) {
            throw error;
        }
    }

}
