package simplex_method;

import com.sun.jdi.InvalidTypeException;
import helpers.Fraction;
import helpers.MathMiddleware;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Класс хранящий условия симплекс метода и методы его решающие
 */
public class SimplexMethod {
    public static Fraction[][] cloneFractionArray(Fraction[][] array) {
        Fraction[][] newArray = new Fraction[array.length][array[0].length];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                newArray[i][j] = array[i][j];
            }
        }
        return newArray;
    }

    public SimplexMethod(String u_type, Fraction[] u_function, Fraction[][] u_system, Fraction[] u_basis) {
        type = u_type;
        function = u_function;
        system = u_system;
        basis = u_basis;
        if (previous_steps == null) {
            previous_steps = new LinkedList<SimplexMethod>();
        }
        previous_steps.add(this);
        //previous_steps.listIterator(previous_steps.size()-1).previous();
    }

    /**
     * Переменная для хранения предыдущих шагов решения
     */
    public static LinkedList<SimplexMethod> previous_steps;

    public static final String MAX = "max";
    public static final String MIN = "min";

    /**
     * Кол-во переменных функции
     */
    private int n;

    /**
     * Тип задачи которую решает функция. По умолчанию минимизация
     */
    private String type = MIN;

    /**
     * Сама функция. Индекс элемента массива = индексу параметра x функции
     */
    private Fraction[] function;

    /**
     * Система, которую необходимо решить
     */
    private Fraction[][] system;

    /**
     * Копия системы, которую необходимо решить
     */
    private Fraction[][] copy_system;

    /**
     * Базис для решения симплекс методом
     */
    private Fraction[] basis;


    /**
     * Подбираю подходящую строку в столбце, для шага симплекс метода
     *
     * @param column - столбец для которого нужно подобрать строку
     * @return номер строки, -1 - если строка не найдена
     */
    private int chooseRow(int column) throws InvalidTypeException {
        int choosedRow = -1;
        int rows = system.length;
        Fraction min = new Fraction(Long.MAX_VALUE, 1);
        for (int i = 0; i < rows - 1; i++) {
            if (Fraction.moreThen(system[i][column], (long) 0)) {
                Fraction currentMin = Fraction.divisionFractions(system[rows - 1][column], system[i][column]);
                if (Fraction.lowerThen(currentMin, min)) {
                    min = currentMin;
                    choosedRow = i;
                }
            }
        }
        return choosedRow;
    }

    /**
     * Подбираю опорный элемент для шага симплекс метода
     *
     * @return - массив int, где первый элемент строка выбранного элемента, а второй элемент столбец выбранного элемента. Если элемент не выбран возвращаю массив со значениями [-1, -1]
     * @throws InvalidTypeException
     */
    private int[] pickupElement() throws InvalidTypeException {
        int rows = system.length;
        int columns = system[0].length;

        int column = -1;

        // подбираю подходящие столбцы
        for (int i = 0; i < columns - 1; i++) {
            if (Fraction.lowerThen(system[rows - 1][i], (long) 0)) {
                column = i;
                if (chooseRow(i) != -1) {
                    return new int[]{chooseRow(i), i};
                }
            }
        }
        return new int[]{-1, column};
    }

    /**
     * Вычисляю новую симплекс таблицу для переданного опорного элемента
     *
     * @param row    - строка опорного элемента
     * @param column - столбец опорного элемента
     */
    private void calculateNewSystem(int row, int column) throws InvalidTypeException {
        Fraction[][] newSystem = cloneFractionArray(system);
        Fraction element = system[row][column];
        // высчитываю опорную строку
        for (int i = 0; i < system[0].length; i++) {
            if (i != column) {
                newSystem[row][i] = Fraction.divisionFractions(newSystem[row][i], element);
            } else {
                newSystem[row][i] = Fraction.reverseFraction(element);
            }
        }
        // высчитываю опорный столбец
        for (int i = 0; i < system.length; i++) {
            if (i != row) {
                newSystem[i][column] = Fraction.divisionFractions(newSystem[i][column], Fraction.multiplyFractions(element, Fraction.toFraction((long) -1)));
            }
        }

        // высчитываю оставшуюся матрицу
        for (int i = 0; i < system.length; i++) {
            if (i != row) {
                for (int j = 0; j < system[0].length; j++) {
                    if (j != column) {
                        newSystem[i][j] = Fraction.subtractionFractions(system[i][j], (Fraction.multiplyFractions(system[i][column], newSystem[row][j])));
                    }
                }
            }
        }
        copy_system = system;
        system = newSystem;
    }

    public void solve() throws InvalidTypeException {
        // переставляю столбцы системы согласно полученному базису
        int buf = -1;
        for (int j = 0; j < basis.length; j++) {
            buf = -1;
            for (int i = 0; i < basis.length; i++) {
                if (Fraction.equal(basis[i], (long) 0)) {
                    if (buf == -1) {
                        buf = i;
                    }
                }
                if (!Fraction.equal(basis[i], (long) 0)) {
                    if (buf != -1) {
                        system = MathMiddleware.swapCol(system, buf, i);
                        buf = -1;
                    }
                }
            }
        }
        // решаю систему методом гаусса
        system = MathMiddleware.gaus(system);
        // произвожу замены в  функции, отталкиваясь от решения метода гаусса

        // нахожу опорные элементы и считаю новые симплекс таблицы
        int[] element = pickupElement();
        if (element[0] == -1 && element[1] == -1) {
            System.out.println("Система решена");
            return;
        }
        if (element[0] == -1 && element[1] != -1) {
            System.out.println("Система не имеет решения");
            return;
        }
        calculateNewSystem(element[0], element[1]);
        previous_steps.add(this);
        System.out.println(Arrays.deepToString(system));
    }
}
