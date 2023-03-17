package ru.sibsutis.piratetigo.calculator.number;

/**
 * P-ичнное число.
 * <p>
 * Описывает число в системе счисления
 * с основанием p.
 */
public class PNumber extends ANumber {

    /** Внутреннее представление числа. */
    private double number;

    /** Основание системы счисления. */
    private int p;

    /** Точность представления числа. */
    int precision;

    /** Признак отрицательного числа. */
    private boolean isNegative;

    /**
     * Признак необходимости переноса разряда.
     */
    private boolean carry;

    /**
     * Конструктор экземпляра p-ичного числа.
     *
     * @param number Число в десятичной системе счисления.
     * @param p Основание системы счисления для представления числа.
     * @param precision Точность представления числа.
     */
    public PNumber(double number, int p, int precision) {
        setBase(p);
        setPrecision(precision);
        this.number = number;
    }

    /**
     * Конструктор экземпляра p-ичного числа.
     *
     * @param number Строковое представление числа в десятичной системе счисления.
     * @param p Строковое представление основания системы счисления
     *          для представления числа.
     * @param precision Строковое представление точности представления числа.
     */
    public PNumber(String number, String p, String precision) {
        setBaseStr(p);
        setPrecisionStr(precision);
        setNumberStr(number);
    }

    @Override
    public boolean isZero() {
        return number == 0;
    }

    @Override
    public PNumber clone() {
        return new PNumber(number, p, precision);
    }

    /**
     * Возвращает внутреннее представление числа.
     *
     * @return Внутреннее представление числа.
     */
    public double getNumber() {
        return number;
    }

    /**
     * Устанавливает основание системы счисления числа.
     *
     * @param p Основание системы счисления числа.
     */
    public void setBase(int p) {
        if (p >= 2 && p <= 16) {
            this.p = p;
        } else {
            throw new PNumberException("Illegal p-number base");
        }
    }

    /**
     * Возвращает основание системы счисления числа.
     *
     * @return Основание системы счисления числа.
     */
    public int getBase() {
        return p;
    }

    /**
     * Устанавливает точность представления числа.
     *
     * @param precision Точность представления числа.
     */
    public void setPrecision(int precision) {
        if (precision >= 0) {
            this.precision = precision;
        } else {
            throw new PNumberException("Illegal p-number precision");
        }
    }

    /**
     * Возвращает точность представления числа.
     *
     * @return Точность представления числа.
     */
    public int getPrecision() {
        return precision;
    }

    /**
     * Устанавливает основание представления числа.
     *
     * @param p Строковое представление основания представления числа.
     */
    public void setBaseStr(String p) {
        try {
            setBase(Integer.parseInt(p));
        } catch (NumberFormatException ex) {
            throw new PNumberException("Illegal p-number base", ex);
        }
    }

    /**
     * Возвращает строковое представление основания системы
     * счисления числа.
     */
    public String getBaseStr() {
        return String.valueOf(p);
    }

    /**
     * Устанавливает точность представления числа.
     *
     * @param precision Строковое представление точности
     *                  представления числа.
     */
    public void setPrecisionStr(String precision) {
        try {
            setPrecision(Integer.parseInt(precision));
        } catch (NumberFormatException ex) {
            throw new PNumberException("Illegal p-number precision", ex);
        }
    }

    /**
     * Возвращает строковое представление точности представления
     * числа.
     */
    public String getPrecisionStr() {
        return String.valueOf(precision);
    }

    @Override
    public PNumber add(ANumber operand) {
        checkCompatibility((PNumber) operand);
        PNumber res = this.clone();
        res.number += ((PNumber) operand).number;
        return res;
    }

    @Override
    public PNumber sub(ANumber operand) {
        checkCompatibility((PNumber) operand);
        PNumber res = this.clone();
        res.number -= ((PNumber) operand).number;
        return res;
    }

    @Override
    public PNumber mul(ANumber operand) {
        checkCompatibility((PNumber) operand);
        PNumber res = this.clone();
        res.number *= ((PNumber) operand).number;
        return res;
    }

    @Override
    public PNumber div(ANumber operand) {
        checkCompatibility((PNumber) operand);
        PNumber res = this.clone();
        if (((PNumber) operand).number == 0) {
            throw new PNumberException("Division by zero");
        }
        res.number /= ((PNumber) operand).number;
        return res;
    }

    @Override
    public boolean equals(ANumber other) {
        if (!PNumber.class.isAssignableFrom(other.getClass())) {
            return false;
        }

        PNumber pNumberOther = (PNumber) other;
        return number == pNumberOther.getNumber()
                && p == pNumberOther.getBase()
                && precision == pNumberOther.getPrecision();
    }

    /**
     * Возводит число в квадрат.
     *
     * @return Возведенное в квадрат число.
     */
    public PNumber square() {
        return new PNumber(number * number, p, precision);
    }

    /**
     * Обращает число, вычисляя значение 1/x.
     *
     * @return Обратное p-ичное число.
     */
    public PNumber revert() {
        if (number == 0) {
            throw new PNumberException("Division by zero");
        }
        return new PNumber(1.f / number, p, precision);
    }

    /**
     * Возвращает строковое представление числа.
     *
     * @return Строковое представление числа в указанной системе
     * счисления и с указанной точностью.
     */
    public String getNumberStr() {
        boolean isNegative = number < 0;
        int ceilValue = (int) Math.abs(number);
        double fraction = Math.abs(number) - ceilValue;
        String ceilStr = convertCeil(ceilValue);
        String fractionStr = convertFraction(fraction);
        if (carry) {
            fractionStr = normalize(fractionStr, true);
            if (carry) {
                ceilStr = normalize(ceilStr, false);
                if (carry) {
                    ceilStr = "1" + ceilStr;
                }
            }
        }
        if (isNegative) {
            ceilStr = "-" + ceilStr;
        }
        if (fractionStr.equals("")) {
            return ceilStr;
        } else {
            if (fractionStr.replace("0", "").equals("")) {
                return ceilStr;
            }
            String shortFraction = "0";
            for (int i = fractionStr.length() - 1; i >= 0; i--) {
                if (fractionStr.charAt(i) != '0') {
                    shortFraction = fractionStr.substring(0, i + 1);
                    break;
                }
            }
             return ceilStr + DELIMITER + shortFraction;
        }
    }

    @Override
    public void setNumberStr(String value) {
        if (value == null || value.trim().equals("")) {
            throw new PNumberException("Illegal empty string");
        }
        final String[] parts = value.trim().split(DELIMITER.replace(".", "\\."));
        if (parts.length > 1) {
            number = convertCeil(parts[0])
                    + (isNegative ? -convertFraction(parts[1]) : convertFraction(parts[1]));
        } else {
            if (value.contains(DELIMITER)) {
                throw new PNumberException("No fraction value");
            }
            number = convertCeil(parts[0]);
        }
    }

    /**
     * Проверяет операнд операции на совместимость.
     *
     * @param operand Проверяемый операнд.
     */
    private void checkCompatibility(PNumber operand) {
        if (p != operand.p || precision != operand.precision) {
            throw new PNumberException("Incompatible operand");
        }
    }

    /**
     * Конвертирует целую часть числа в представление в указанной
     * системе счисления.
     *
     * @param value Целая часть числа.
     * @return Строковое представление целой части числа в указанной
     * системе счисления.
     */
    private String convertCeil(int value) {
        int digit;
        char ch;
        StringBuilder result = new StringBuilder();
        boolean isNegative = false;

        if (value < 0) {
            value = -value;
            isNegative = true;
        }

        while (value > 0) {
            digit = value % p;
            if (digit <= 9) {
                ch = (char)('0' + digit);
            } else {
                ch = (char)('A' + digit - 10);
            }
            result.insert(0, ch);
            value /= p;
        }

        if (result.toString().equals("")) {
            return ZERO;
        }

        if (isNegative) {
            result.insert(0, '-');
            return result.toString();
        }

        return result.toString();
    }

    /**
     * Конвертирует дробную часть числа в представление в указанной
     * системе счисления.
     *
     * @param value Дробная часть числа.
     * @return Строковое представление дробной части числа в
     * указанной системе счисления.
     */
    private String convertFraction(double value) {
        StringBuilder result = new StringBuilder();
        int ceilValue;

        if (precision == 0) {
            return "";
        }

        for (int depth = precision - 1; depth > 0; depth--) {
            value *= p;
            ceilValue = (int) value;
            if (ceilValue >= 1) {
                result.append(ceilValue);
                value -= ceilValue;
            } else {
                result.append(ZERO);
            }
        }

        value *= p;
        ceilValue = (int)value;

        if (ceilValue >= 1) {
            value -= ceilValue;
        } else {
            ceilValue = 0;
        }

        value *= p;
        int ceilValue2 = (int)value;
        if (ceilValue2 >= 1) {
            if ((double)ceilValue2 > p / 2.f) {
                ceilValue++;
            }
        }
        if (ceilValue == p) {
            carry = true;
        }
        if (ceilValue >= 10) {
            result.append((char)('A' + (ceilValue - 10)));
        } else {
            result.append(ceilValue);
        }
        return result.toString();
    }

    /**
     * Конвертирует целую часть числа из строкового представления в текущей
     * системе счисления в десятичное целое.
     *
     * @param value Строковое представление целой части числа в текущей
     *              системе счисления.
     * @return Десятичная целая часть числа.
     */
    private long convertCeil(String value) {
        long result = 0;

        String trimValue = value.trim();
        isNegative = false;

        if (trimValue.length() > 1 && trimValue.charAt(0) == '-') {
            isNegative = true;
            trimValue = trimValue.substring(1);
        }

        char digit;
        int bound = trimValue.length();

        for (int basePow = 0; basePow < bound; basePow++) {
            digit = trimValue.charAt(trimValue.length() - basePow - 1);
            result += calcDigitValue(digit, basePow);
        }

        if (isNegative) {
            return -result;
        } else {
            return result;
        }
    }

    /**
     * Конвертирует строковое представление дробной части числа в текущей
     * системе счисления в десятичное представление.
     *
     * @param value Строковое представление дробной части числа.
     * @return Десятичная дробная часть числа.
     */
    private double convertFraction(String value) {
        double result = 0;
        String trimValue = value.trim();

        char digit;
        int bound = trimValue.length();

        for (int basePow = 0; basePow < bound && basePow < precision + 2; basePow++) {
            digit = trimValue.charAt(basePow);
            result += calcDigitValue(digit, -basePow - 1);
        }

        return result;
    }

    private double calcDigitValue(char digit, int basePow) {
        int digitFactor;
        if (digit >= 'A' && digit <= 'F') {
            digitFactor = digit - 'A' + 10;
        } else {
            if (digit >= '0' && digit <= '9') {
                digitFactor = digit - '0';
            } else {
                throw new PNumberException("Illegal symbol");
            }
        }
        if (digitFactor < p) {
            return Math.pow(p, basePow) * digitFactor;
        } else {
            throw new PNumberException("Illegal symbol");
        }
    }

    /**
     * Выполняет перенос разряда.
     *
     * @param digits Обрабатываемое значение.
     * @param little Признак необходимости корректировки младшего разряда.
     * @return Новое значение.
     */
    private String normalize(String digits, boolean little) {
        StringBuilder builder = new StringBuilder(digits);
        int length = digits.length();
        char maxChar = p > 10 ? (char)('A' + p - 11) : (char)('0' + p - 1);
        int startIndex = length - 1;
        if (little) {
            builder.setCharAt(length - 1, '0');
            startIndex = length - 2;
        }
        char currentChar;
        for (int i = startIndex; i >= 0; i--) {
            currentChar = digits.charAt(i);
            if (currentChar == maxChar) {
                builder.setCharAt(i, '0');
                if (i == 0) {
                    carry = true;
                }
            } else {
                builder.setCharAt(i, (char)(currentChar + 1));
                carry = false;
                break;
            }
        }
        return builder.toString();
    }

    /**
     * Исключительная ситуация при работе с p-ичным числом.
     */
    public static class PNumberException extends RuntimeException {
        public PNumberException(String msg) {
            super(msg);
        }

        public PNumberException(String msg, Throwable cause) {
            super(msg, cause);
        }
    }

}
