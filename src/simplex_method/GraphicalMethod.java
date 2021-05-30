package simplex_method;

import com.sun.jdi.InvalidTypeException;
import helpers.Draw;
import helpers.Fraction;
import helpers.MathMiddleware;

import java.awt.*;
import java.util.Comparator;
import java.util.LinkedList;

public class GraphicalMethod extends SimplexMethod {
    class DotComparator implements Comparator<Dot> {
        @Override
        public int compare(Dot o1, Dot o2) {
            if (o1.x - o2.x == 0) {
                return o1.y - o2.y;
            }
            return o1.x - o2.x;
        }
    }

    class Dot {
        public int x, y;

        Dot(Fraction xl, Fraction yl) {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            double width = screenSize.getWidth();
            double height = screenSize.getHeight();

            x = (int) Math.round((double) xl.getDividend() / (double) xl.getDivider());
            y = (int) Math.round((double) yl.getDividend() / (double) yl.getDivider());
            if (xl.getDividend() != 0 && xl.getDivider() == 0) {
                x = (int) Math.round(width);
            }
            if (yl.getDividend() != 0 && yl.getDivider() == 0) {
                y = (int) Math.round(height);
            }
        }
    }

    // рисовалка
    private Draw draw = new Draw();

    // масштаб графика
    private int scale = 50;

    private int originalSize;

    private Fraction[] copy_function;

    // список точек для графика
    private LinkedList<Dot> dots = new LinkedList<Dot>();

    // вектор антинормали
    private Fraction f_x = new Fraction((long) 0, (long) 1);
    private Fraction f_y = new Fraction((long) 0, (long) 1);

    // проверяют, что линия лежит во второй четверти
    private boolean validLine(Dot d1, Dot d2) {
        if (d1.x >= 0 && d1.y >= 0 && d2.x >= 0 && d2.y >= 0) {
            return true;
        }
        return false;
    }

    public GraphicalMethod(String u_type, Fraction[] u_function, Fraction[][] u_system, Fraction[] u_basis) throws InvalidTypeException {
        super(u_type, u_function, u_system, u_basis);
        originalSize = system[0].length;
    }

    /**
     * Привожу систему неравенств (<=) к системе равенств
     */

    private void toEquality() throws InvalidTypeException {
        Fraction[][] newSystem = new Fraction[system.length][system[0].length + system.length];
        Fraction[] newBasis = new Fraction[system[0].length + system.length - 1];

        for (int i = 0; i < system.length; i++) {
            // заношу всю систему, кроме последнего столбца с константами
            for (int j = 0; j < system[i].length - 1; j++) {
                newSystem[i][j] = system[i][j];
            }
            // вставляю новые переменные для приведения к равенству
            for (int j = system[i].length - 1; j < system[0].length + system.length - 1; j++) {
                if (i == j - (system[i].length - 1)) {
                    newSystem[i][j] = Fraction.toFraction((long) 1);
                } else {
                    newSystem[i][j] = Fraction.toFraction((long) 0);
                }
            }
            // заношу константы
            newSystem[i][system[i].length + system.length - 1] = system[i][system[i].length - 1];
        }
        // обновляю функцию
        Fraction[] newFunction = new Fraction[function.length + system.length];
        for (int i = 0; i < function.length; i++) {
            newFunction[i] = function[i];
        }
        for (int i = function.length; i < function.length + system.length; i++) {
            newFunction[i] = Fraction.toFraction((long) 0);
        }
        // обновляю базис
        for (int i = 0; i < basis.length; i++) {
            newBasis[i] = basis[i];
        }
        for (int i = basis.length; i < system[0].length + system.length - 1; i++) {
            newBasis[i] = Fraction.toFraction((long) 0);
        }

        system = newSystem;
        function = newFunction;
        basis = newBasis;
        masterSlave = new int[function.length - 1];
    }

    @Override
    public void solution() throws InvalidTypeException {
        dots.add(new Dot(Fraction.toFraction((long) 0), Fraction.toFraction((long) 0)));
        // минимальное значение функции
        Fraction min_func_value = new Fraction((long) Integer.MAX_VALUE, (long) 1);
        // точки решения
        Fraction x = new Fraction((long) 0, (long) 1);
        Fraction y = new Fraction((long) 0, (long) 1);


        copy_function = cloneFractionArray(function);
        //сохраняю информацию об основных и зависимых переменных
        masterSlaveInfo();
        // переставляю столбцы системы согласно полученному базису
        gausTableBasisSwap();
        // выбирую пару неравенств в системе
        for (int i = 0; i < system.length - 1; i++) {
            for (int j = i + 1; j < system.length; j++) {
                // если в системе только две переменных и константа
                if (system[0].length == 3) {
                    Fraction[][] systemClone = cloneFractionArray(system);
                    Fraction[][] subSystem = new Fraction[2][system[0].length];
                    subSystem[0] = systemClone[i];
                    subSystem[1] = systemClone[j];

                    // граничные неравенства
                    Fraction x_dot = Fraction.divisionFractions(subSystem[0][2], subSystem[0][0]);
                    Dot d1 = (new Dot(x_dot, Fraction.toFraction((long) 0)));
                    Fraction y_dot = Fraction.divisionFractions(subSystem[0][2], subSystem[0][1]);
                    Dot d2 = (new Dot(Fraction.toFraction((long) 0), y_dot));
                    if (validLine(d1, d2))
                        draw.addLine(d1.x * scale, d1.y * scale, d2.x * scale, d2.y * scale, Color.blue);


                    x_dot = Fraction.divisionFractions(subSystem[1][2], subSystem[1][0]);
                    Dot d3 = (new Dot(x_dot, Fraction.toFraction((long) 0)));
                    y_dot = Fraction.divisionFractions(subSystem[1][2], subSystem[1][1]);
                    Dot d4 = (new Dot(Fraction.toFraction((long) 0), y_dot));
                    if (validLine(d3, d4))
                        draw.addLine(d3.x * scale, d3.y * scale, d4.x * scale, d4.y * scale, Color.blue);

                    subSystem = MathMiddleware.gaus(subSystem);
                    // получаю решение
                    x_dot = Fraction.divisionFractions(subSystem[0][2], subSystem[0][0]);
                    y_dot = Fraction.divisionFractions(subSystem[1][2], subSystem[1][1]);
                    // получаю значение функции
                    Fraction x1_value = Fraction.multiplyFractions(function[1], x_dot);
                    Fraction x2_value = Fraction.multiplyFractions(function[2], y_dot);
                    Fraction func_value = Fraction.summFractions(x1_value, x2_value);
                    if (Fraction.lowerThen(func_value, min_func_value)) {
                        min_func_value = func_value;
                        x = x_dot;
                        y = y_dot;
                    }
                    // коэффициенты функции
                    f_x = Fraction.multiplyFractions(function[1], Fraction.toFraction((long) -1));
                    f_y = Fraction.multiplyFractions(function[2], Fraction.toFraction((long) -1));
                    System.out.println("x1 = " + x.toString());
                    System.out.println("x2 = " + y.toString());
                    System.out.println("f = " + min_func_value.toString());
                }
                // если больше двух переменных
                if (system[0].length > 3) {
                    function = cloneFractionArray(copy_function);
                    Fraction[][] systemClone = cloneFractionArray(system);
                    Fraction[][] subSystem = new Fraction[2][system[0].length];
                    subSystem[0] = systemClone[i];
                    subSystem[1] = systemClone[j];
                    // решаю выборку гауссом
                    subSystem = MathMiddleware.gaus(subSystem);
                    // обновляю функцию
                    updateFunction(subSystem);

                    // определяю индекс зависимых переменных
                    int slave_1 = findVar(-1);
                    int slave_2 = findVar(-2);
                    int const_val = subSystem[0].length - 1;

                    // граничные неравенства
                    Fraction x_dot = Fraction.divisionFractions(subSystem[0][const_val], subSystem[0][slave_1]);
                    Dot d1 = (new Dot(x_dot, Fraction.toFraction((long) 0)));
                    Fraction y_dot = Fraction.divisionFractions(subSystem[0][const_val], subSystem[0][slave_2]);
                    Dot d2 = (new Dot(Fraction.toFraction((long) 0), y_dot));
                    if (validLine(d1, d2))
                        draw.addLine(d1.x * scale, d1.y * scale, d2.x * scale, d2.y * scale, Color.blue);

                    x_dot = Fraction.divisionFractions(subSystem[1][const_val], subSystem[1][slave_1]);
                    Dot d3 = (new Dot(x_dot, Fraction.toFraction((long) 0)));
                    y_dot = Fraction.divisionFractions(subSystem[1][const_val], subSystem[1][slave_2]);
                    Dot d4 = (new Dot(Fraction.toFraction((long) 0), y_dot));
                    if (validLine(d3, d4))
                        draw.addLine(d3.x * scale, d3.y * scale, d4.x * scale, d4.y * scale, Color.blue);

                    // получаю значения основных переменных через зависимые


                    // получаю решение
                    x_dot = Fraction.divisionFractions(subSystem[0][2], subSystem[0][slave_1]);
                    y_dot = Fraction.divisionFractions(subSystem[1][2], subSystem[1][slave_2]);
                    // получаю значение функции
                    Fraction x1_value = Fraction.multiplyFractions(function[slave_1 + 1], x_dot);
                    Fraction x2_value = Fraction.multiplyFractions(function[slave_2 + 1], y_dot);
                    Fraction func_value = Fraction.summFractions(Fraction.summFractions(x1_value, x2_value), function[0]);
                    if (Fraction.lowerThen(func_value, min_func_value)) {
                        min_func_value = func_value;
                        x = x_dot;
                        y = y_dot;
                    }
                    // коэффициенты функции
                    f_x = Fraction.multiplyFractions(function[1], Fraction.toFraction((long) -1));
                    f_y = Fraction.multiplyFractions(function[2], Fraction.toFraction((long) -1));
                }
            }
        }
        // вектор антинормали
        Dot av = new Dot(f_x, f_y);
        draw.addLine(0, 0, av.x * scale, av.y * scale, Color.red);
        draw.show();
    }

    protected void updateFunction(Fraction[][] user_system) throws InvalidTypeException {
        for (int master = 0; master < masterSlave.length; master++) {
            if (masterSlave[master] > 0) {
                Fraction coef = function[master + 1];
                function[master + 1] = Fraction.toFraction((long) 0);
                for (int slave = 0; slave < masterSlave.length; slave++) {
                    if (masterSlave[slave] < 0) {
                        function[slave + 1] = Fraction.summFractions(
                                function[slave + 1],
                                Fraction.multiplyFractions(
                                        coef,
                                        Fraction.multiplyFractions(user_system[masterSlave[master] - 1][-masterSlave[slave] + user_system.length - 1], Fraction.toFraction((long) -1))
                                )
                        );
                    }
                }
                // складываю константы
                function[0] = Fraction.summFractions(
                        function[0],
                        Fraction.multiplyFractions(coef, user_system[masterSlave[master] - 1][user_system[0].length - 1])
                );
            }
        }
    }

    /**
     * Вывод решения графического метода
     */
    @Override
    public void printSolution() throws InvalidTypeException {
        for (int i = 0; i < originalSize - 1; i++) {
            if (masterSlave[i] <= 0) {
                System.out.println("x" + (i + 1) + " = " + 0);
            } else {
                System.out.println("x" + (i + 1) + " = " + system[masterSlave[i] - 1][system[masterSlave[i] - 1].length - 1]);
            }
        }
        System.out.println("f = " + Fraction.multiplyFractions(system[system.length - 1][system[system.length - 1].length - 1], Fraction.toFraction((long) -1)));
    }
}