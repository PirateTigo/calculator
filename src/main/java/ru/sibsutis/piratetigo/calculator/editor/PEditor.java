package ru.sibsutis.piratetigo.calculator.editor;

/**
 * Редактор p-ичных чисел.
 */
public class PEditor extends AEditor {

    @Override
    public boolean isZero() {
        return numberStr.equals(ZERO);
    }

    @Override
    public String toggleSign() {
        if (!isZero()) {
            if (numberStr.charAt(0) == '-') {
                numberStr = numberStr.substring(1);
            } else {
                numberStr = "-" + numberStr;
            }
        }
        return numberStr;
    }

    @Override
    public String addDigit(int pDigit) {
        if (pDigit >= 0 && pDigit <= 15) {
            char addedDigit;
            if (pDigit < 10) {
                addedDigit = (char)('0' + pDigit);
            } else {
                addedDigit = (char)('A' + pDigit - 10);
            }
            if (isZero()) {
                numberStr = String.valueOf(addedDigit);
            } else {
                numberStr += addedDigit;
            }
        } else {
            throw new PEditorException("Illegal p-number digit");
        }
        return numberStr;
    }

    @Override
    public String addZero() {
        if (!isZero()) {
            numberStr += ZERO;
        }
        return numberStr;
    }

    @Override
    public String addDelimiter() {
        if (!numberStr.contains(FRACTION_DELIMITER)) {
            numberStr += FRACTION_DELIMITER;
        }
        return numberStr;
    }

    @Override
    public String backSpace() {
        if (numberStr.length() > 1) {
            numberStr = numberStr.substring(0, numberStr.length() - 1);
        } else {
            numberStr = ZERO;
        }
        return numberStr;
    }

    @Override
    public String clear() {
        numberStr = ZERO;
        return numberStr;
    }

    /**
     * Исключительная ситуация при работе с редактором p-ичных чисел.
     */
    public static class PEditorException extends RuntimeException {
        public PEditorException(String msg) {
            super(msg);
        }
    }

}
