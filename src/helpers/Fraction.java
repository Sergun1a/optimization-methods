package helpers;

import com.sun.jdi.InvalidTypeException;

/**
 * Класс реализующий функционал обыкновенных дробей
 */
public class Fraction {
    private long dividend; //  делимое
    private long divider;  //  делитель

    public static Fraction toStandartFraction(Fraction fraction) {
        if (fraction.getDivider() < 0 && fraction.getDividend() > 0) {
            fraction.setDivider(-fraction.getDivider());
            fraction.setDividend(-fraction.getDividend());
        }
        if (fraction.getDivider() == 0) {
            fraction.setDivider((long) 1);
            fraction.setDividend((long) 1);
        }

        return fraction;
    }

    public Fraction(long newDividend, long newDivider) {
        dividend = newDividend;
        divider = newDivider;
    }

    public Fraction(long newDividend) {
        dividend = newDividend;
        divider = 1;
    }

    public String toString() {
        toStandartFraction(this);
        if (divider == 1) {
            return String.valueOf(dividend);
        }
        return dividend + "/" + divider;
    }

    public long getDividend() {
        return dividend;
    }

    public long getDivider() {
        return divider;
    }

    public void setDividend(long newDividend) {
        dividend = newDividend;
    }

    public void setDivider(long newDivider) {
        divider = newDivider;
    }

    /**
     * Перемножение двух обыкновенных дробей
     *
     * @param first  - первая дробь
     * @param second - вторая дробь
     * @return результат перемножения двух дробей
     */
    public static Fraction multiplyFractions(Fraction first, Fraction second) {
        first = toStandartFraction(first);
        second = toStandartFraction(second);

        long newDividend = first.getDividend() * second.getDividend();
        long newDivider = first.getDivider() * second.getDivider();
        long nok = MathMiddleware.nok(newDividend, newDivider);

        return toStandartFraction(new Fraction(newDividend / nok, newDivider / nok));
    }

    /**
     * Деление двух обыкновенных дробей
     *
     * @param first  - делимая дробь
     * @param second - дробь делитель
     * @return результат деления двух дробей
     */
    public static Fraction divisionFractions(Fraction first, Fraction second) {
        first = toStandartFraction(first);
        second = toStandartFraction(second);
        return Fraction.multiplyFractions(first, new Fraction(second.getDivider(), second.getDividend()));
    }

    /**
     * Сложение двух обыкновенных дробей
     *
     * @param first  - первая дробь
     * @param second - вторая дробь
     * @return результат сложения двух дробей
     */
    public static Fraction summFractions(Fraction first, Fraction second) {
        first = toStandartFraction(first);
        second = toStandartFraction(second);
        long newDivider = MathMiddleware.nod(first.getDivider(), second.getDivider());
        long newDividend = first.getDividend() * (newDivider / first.getDivider()) + second.getDividend() * (newDivider / second.getDivider());
        long nok = MathMiddleware.nok(newDividend, newDivider);
        return new Fraction(newDividend / nok, newDivider / nok);
    }


    /**
     * Вычитание двух обыкновенных дробей
     *
     * @param first  - умешьнаемая дробь
     * @param second - вычитаемая дробь
     * @return результат вычитания двух дробей
     */
    public static Fraction subtractionFractions(Fraction first, Fraction second) {
        first = toStandartFraction(first);
        second = toStandartFraction(second);
        return summFractions(first, new Fraction(-second.getDividend(), second.getDivider()));
    }

    /**
     * Конвертирую String, Long и Double во Fraction. Ожидаю строку вида (long)/(long).
     *
     * @param futureFracture - то что нужно конвертировать в дробь
     * @return Дробь
     * @throws InvalidTypeException - если получил атрибут некорректного типа
     */
    public static Fraction toFraction(Object futureFracture) throws InvalidTypeException {
        if (futureFracture instanceof Fraction) {
            return (Fraction) futureFracture;
        }

        if (futureFracture instanceof String) {
            String[] subStr = ((String) futureFracture).split("/");
            if (subStr.length < 2) {
                /*ApplicationMenu.showAlert("warning", "Некорректный ввод",
                        "Некорректный ввод чисел", "Не могу перевести число в обыкновенную дробь");*/
                throw new InvalidTypeException("Не могу перевести число в обыкновенную дробь из строки");
            }
            long newDividend = 0;
            long newDivider = 1;
            try {
                newDividend = Long.parseLong(subStr[0]);
                newDivider = Long.parseLong(subStr[1]);
            } catch (NumberFormatException ex) {
                throw new InvalidTypeException("Не могу перевести число в обыкновенную дробь из строки");
            }
            long nok = MathMiddleware.nok(newDividend, newDivider);
            return new Fraction(newDividend / nok, newDivider / nok);
        }
        if (futureFracture instanceof Integer) {
            return new Fraction((long) futureFracture, (long) 1);
        }
        if (futureFracture instanceof Long) {
            return new Fraction((long) futureFracture, (long) 1);
        }
        if (futureFracture instanceof Double) {
            long divider = 1;
            while (((Double) futureFracture).longValue() != (double) futureFracture) {
                futureFracture = (double) futureFracture * 10;
                divider = divider * 10;
            }
            return new Fraction(((Double) futureFracture).longValue(), divider);
        }
        /*ApplicationMenu.showAlert("warning", "Некорректный ввод",
                "Некорректный ввод чисел", "Не могу перевести число в обыкновенную дробь");*/
        throw new InvalidTypeException("Не могу перевести число в обыкновенную дробь");
    }

    /**
     * Возвращает дробь обратную переданной
     *
     * @param fraction - дробь, для которой нужно вернуть обратную
     * @return обратную дробь
     */
    public static Fraction reverseFraction(Fraction fraction) {
        fraction = toStandartFraction(fraction);
        return new Fraction(fraction.getDivider(), fraction.getDividend());
    }


    /**
     * Проверяет что переданная дробь СТРОГО меньше переданного значения
     *
     * @param fraction - дробь, которую нужно сравнить
     * @param value    - значение с которым нужно сравнить
     * @return bool
     * @throws InvalidTypeException
     */
    public static boolean lowerThen(Fraction fraction, Object value) throws InvalidTypeException {
        Fraction compareFraction = toFraction(value);
        fraction = toStandartFraction(fraction);
        if (fraction.getDividend() * compareFraction.getDivider() <
                compareFraction.getDividend() * fraction.getDivider()) {
            return true;
        }
        return false;
    }

    /**
     * Проверяет что переданная дробь СТРОГО больше переданного значения
     *
     * @param fraction - дробь, которую нужно сравнить
     * @param value    - значение с которым нужно сравнить
     * @return bool
     * @throws InvalidTypeException
     */
    public static boolean moreThen(Fraction fraction, Object value) throws InvalidTypeException {
        fraction = toStandartFraction(fraction);
        Fraction compareFraction = toFraction(value);
        if (fraction.getDividend() * compareFraction.getDivider() >
                compareFraction.getDividend() * fraction.getDivider()) {
            return true;
        }
        return false;
    }

    /**
     * Проверяет что переданная дробь равна переданному значению
     *
     * @param fraction - дробь, которую нужно сравнить
     * @param value    - значение с которым нужно сравнить
     * @return bool
     * @throws InvalidTypeException
     */
    public static boolean equal(Fraction fraction, Object value) throws InvalidTypeException {
        fraction = toStandartFraction(fraction);
        Fraction compareFraction = toFraction(value);
        if (fraction.getDividend() * compareFraction.getDivider() ==
                compareFraction.getDividend() * fraction.getDivider()) {
            return true;
        }
        return false;
    }
}
