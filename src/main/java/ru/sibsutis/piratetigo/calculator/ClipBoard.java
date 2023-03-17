package ru.sibsutis.piratetigo.calculator;

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;

/**
 * Устройство взаимодействия с буфером обмена.
 */
public final class ClipBoard {

    /**
     * Возвращает содержимое буфера обмена.
     *
     * @return Содержимое буфера обмена.
     */
    public static String getString() {
        Clipboard clipboard = Clipboard.getSystemClipboard();

        if (clipboard.hasString()) {
            return (String) clipboard.getContent(DataFormat.PLAIN_TEXT);
        }
        return null;
    }

    /**
     * Копирует данные в буфер обмена.
     *
     * @param data Данные для буфера обмена.
     */
    public static void setString(String data) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString(data);
        clipboard.setContent(clipboardContent);
    }

}
