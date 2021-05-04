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
        masterSlave = new int[function.length - 1];
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
    protected int n;

    /**
     * Тип задачи которую решает функция. По умолчанию минимизация
     */
    protected String type = MIN;

    /**
     * Сама функция. Индекс элемента массива = индексу параметра x функции
     */
    protected Fraction[] function;

    /**
     * Система, которую необходимо решить
     */
    protected Fraction[][] system;

    /**
     * Копия системы, которую необходимо решить
     */
    protected Fraction[][] copy_system;

    /**
     * Базис для решения симплекс методом
     */
    protected Fraction[] basis;

    /**
     * Массив где хранится информация о том какие переменные главные, а какие зависимые
     */
    protected int[] masterSlave;

    /**
     * Подбираю подходящую строку в столбце, для шага симплекс метода
     *
     * @param column - столбец для которого нужно подобрать строку
     * @return номер строки, -1 - если строка не найдена
     */
    protected int chooseRow(int column) throws InvalidTypeException {
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
    protected int[] pickupElement() throws InvalidTypeException {
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
    protected void calculateNewSystem(int row, int column) throws InvalidTypeException {
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

    /**
     * Сохраняю информацию об основных и зависимых переменных
     * Знак + указывает на то что переменная основная, а цифра на номер её строки в симплекс таблице
     * Знак - указывает на то что переменная зависимая, а цифра на номер её столбца в симплекс таблице
     *
     * @throws InvalidTypeException
     */
    protected void masterSlaveInfo() throws InvalidTypeException {
        int master_counter = 0;
        int slave_counter = 0;
        for (int i = 0; i < basis.length; i++) {
            // i-элемент равен нулю значит переменная x(i+1) зависимая
            if (Fraction.equal(basis[i], (long) 0)) {
                masterSlave[i] = --slave_counter;
            }
            // i-элемент равен нулю значит переменная x(i+1) независимая
            if (!Fraction.equal(basis[i], (long) 0)) {
                masterSlave[i] = ++master_counter;
            }
        }
    }

    /**
     * Переставляю столбцы системы согласно полученному базису
     *
     * @throws InvalidTypeException
     */
    protected void gausTableBasisSwap() throws InvalidTypeException {
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
    }

    /**
     * Произвожу замены в  функции, отталкиваясь от решения метода гаусса
     * Заменяю основные переменные на зависимые
     *
     * @throws InvalidTypeException
     */
    protected void gausUpdateFunction() throws InvalidTypeException {
        for (int master = 0; master < system.length; master++) {
            Fraction coef = function[master + 1];
            function[master + 1] = Fraction.toFraction((long) 0);
            for (int slave = system.length; slave < system[master].length - 1; slave++) {
                function[slave + 1] = Fraction.summFractions(
                        function[slave + 1],
                        Fraction.multiplyFractions(
                                coef,
                                Fraction.multiplyFractions(system[master][slave], Fraction.toFraction((long) -1))
                        )
                );
            }
            // складываю константы
            function[0] = Fraction.summFractions(
                    function[0],
                    Fraction.multiplyFractions(coef, system[master][system[master].length - 1])
            );
        }
    }

    /**
     * Конвертирую решенную методом гаусса систему и обновленную функцию в симплекс таблицу
     */
    protected void gausToSimplex() throws InvalidTypeException {
        Fraction[][] simplexTable = new Fraction[system.length + 1][system[0].length - system.length];
        // отрезаю от гаусса основные переменные
        for (int i = 0; i < system.length; i++) {
            for (int j = system.length; j < system[0].length; j++) {
                simplexTable[i][j - system.length] = system[i][j];
            }
        }
        // вставляю обновленную функцию
        for (int i = 1; i < function.length; i++) {
            if (!Fraction.equal(function[i], (long) 0)) {
                if (masterSlave[i - 1] < 0) {
                    simplexTable[system.length][i - 1 - system.length] = function[i];
                } else {
                    System.out.println("Возникла ошибка. Основная переменная попала на место зависимой");
                }
            }
        }
        simplexTable[system.length][system[0].length - system.length - 1] = Fraction.multiplyFractions(function[0], Fraction.toFraction((long) -1));
        system = simplexTable;
    }

    public void quickSolve() throws InvalidTypeException {
        //сохраняю информацию об основных и зависимых переменных
        masterSlaveInfo();
        // переставляю столбцы системы согласно полученному базису
        gausTableBasisSwap();
        // решаю систему методом гаусса
        system = MathMiddleware.gaus(system);
        // произвожу замены в  функции, отталкиваясь от решения метода гаусса
        gausUpdateFunction();
        // приводу матрицу гаусса к стартовой симплекс таблице
        gausToSimplex();

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
