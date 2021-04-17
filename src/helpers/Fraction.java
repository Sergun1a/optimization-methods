package helpers;

import com.sun.jdi.InvalidTypeException;
import javafx.scene.control.Alert;
import sample.ApplicationMenu;

/**
 * Класс реализующий функционал обыкновенных дробей
 */
public class Fraction {
    private long dividend; //  делимое
    private long divider;  //  делитель

    public Fraction(long newDividend, long newDivider) {
        dividend = newDividend;
        divider = newDivider;
    }

    public String toString() {
        return dividend + " / " + divider;
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
        long newDividend = first.getDividend() * second.getDividend();
        long newDivider = first.getDivider() * second.getDivider();
        long nok = MathMiddleware.nok(newDividend, newDivider);

        return new Fraction(newDividend / nok, newDivider / nok);
    }

    /**
     * Деление двух обыкновенных дробей
     *
     * @param first  - делимая дробь
     * @param second - дробь делитель
     * @return результат деления двух дробей
     */
    public static Fraction divisionFractions(Fraction first, Fraction second) {
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
        return summFractions(first, new Fraction(-second.getDividend(), second.getDivider()));
    }

    public static Fraction toFracture(Object futureFracture) throws InvalidTypeException {
        if (futureFracture instanceof String) {
            String[] subStr = ((String) futureFracture).split("/");
            if (subStr.length < 2) {
                ApplicationMenu.showAlert("warning", "Некорректный ввод",
                        "Некорректный ввод чисел", "Не могу перевести число в обыкновенную дробь");
                throw new InvalidTypeException("Не могу перевести число в обыкновенную дробь из строки");
            }
            long newDividend = Long.parseLong(subStr[0]);
            long newDivider = Long.parseLong(subStr[1]);
            long nok = MathMiddleware.nok(newDividend, newDivider);
            return new Fraction(newDividend / nok, newDivider / nok);
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
        ApplicationMenu.showAlert("warning", "Некорректный ввод",
                "Некорректный ввод чисел", "Не могу перевести число в обыкновенную дробь");
        throw new InvalidTypeException("Не могу перевести число в обыкновенную дробь");
    }
}
