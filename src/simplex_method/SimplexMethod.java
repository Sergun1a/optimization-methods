package simplex_method;

import com.sun.jdi.InvalidTypeException;
import helpers.Fraction;
import helpers.MathMiddleware;

import java.util.LinkedList;

/**
 * Класс хранящий условия симплекс метода и методы его решающие
 */
public class SimplexMethod {
    public String status = "new";

    /**
     * Название аргументов нужных для работы метода. Нужно для сохранения и открытия файла
     *
     * @return массив аргументов
     */
    public static String[] fileArguments() {
        return new String[]{
                "type", // тип решаемой задачи
                "sys_number", // кол-во уравнений в системе
                "var_number", // кол-во уникальных переменных
                "f", // указатель на коэффициент функции (пример полного вида f2,где 2 - указатель на номер переменной)
                "s", // указатель на коэффициент системы (пример полного вида s1_2,где 2 - указатель на номер переменной, а 1 - номер уравнения в системе)
                "b", // указатель на коэффициент базиса (по сути идентичен f, только c не нужно)
        };
    }

    public Fraction[][] getSystem() {
        return system;
    }

    public static Fraction[][] cloneFractionArray(Fraction[][] array) {
        Fraction[][] newArray = new Fraction[array.length][array[0].length];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                newArray[i][j] = array[i][j];
            }
        }
        return newArray;
    }

    public static Fraction[] cloneFractionArray(Fraction[] array) {
        Fraction[] newArray = new Fraction[array.length];
        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i];
        }
        return newArray;
    }

    public SimplexMethod(String u_type, Fraction[] u_function, Fraction[][] u_system, Fraction[] u_basis) throws InvalidTypeException {
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

    protected int supporting_elem_col = -2;
    protected int supporting_elem_row = -2;

    /**
     * Устанавливаю пользовательский опорный элемент
     *
     * @param col - выбранный столбец
     * @param row - выбранная строка
     * @throws InvalidTypeException
     */
    public void setUserSupportingElem(int col, int row) throws InvalidTypeException {
        if (checkPickedUpElement(row, col)) {
            supporting_elem_col = col;
            supporting_elem_row = row;
        }
    }

    /**
     * Удаляю пользовательский опорный элемент
     */
    public void deleteUserSupportingElem() {
        supporting_elem_col = -2;
        supporting_elem_row = -2;
    }

    /**
     * Установка пользовательского базиса
     *
     * @param new_basis - пользовательский базис
     */
    public void setBasis(Fraction[] new_basis) {
        basis = new_basis;
    }

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
            if (Fraction.moreThen(system[i][column], (long) 0) && Fraction.moreThen(system[i][system[0].length - 1], (long) -1)) {
                Fraction currentMin = Fraction.divisionFractions(system[i][system[0].length - 1], system[i][column]);
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
        int temp;
        int slave, master;
        int column = -1;

        // подбираю подходящие столбцы
        for (int i = 0; i < columns - 1; i++) {
            if (Fraction.lowerThen(system[rows - 1][i], (long) 0)) {
                column = i;
                if (chooseRow(i) != -1) {
                    // переставлю опорную и зависимую переменную
                    master = findVar(chooseRow(i) + 1);
                    slave = findVar(-(i + 1));
                    temp = masterSlave[master];
                    masterSlave[master] = masterSlave[slave];
                    masterSlave[slave] = temp;
                    return new int[]{chooseRow(i), i};
                }
            }
        }
        return new int[]{-1, column};
    }


    /**
     * Просто выбираю опорный элемент без перестановок переменных
     *
     * @return выбранный опорный элемент
     * @throws InvalidTypeException
     */
    public int[] idlePickupElement() throws InvalidTypeException {
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
     * Проверяю что выбранный элемент может быть опорным
     *
     * @return true или false
     * @throws InvalidTypeException
     */
    public boolean checkPickedUpElement(int row, int col) throws InvalidTypeException {
        int rows = system.length;

        // если столбец отрицательный
        if (Fraction.lowerThen(system[rows - 1][col], (long) 0)) {
            // для него существует положительный элемент
            if (chooseRow(col) != -1) {
                // и значение совпадает полученного мной и пользовательского совпадает
                Fraction choosed = Fraction.divisionFractions(system[chooseRow(col)][system[chooseRow(col)].length - 1], system[chooseRow(col)][col]);
                Fraction user = Fraction.divisionFractions(system[row][system[row].length - 1], system[row][col]);
                if (Fraction.equal(choosed, user))
                    return true;
            }
        }
        return false;
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
            // i-элемент не равен нулю значит переменная x(i+1) независимая
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
                        basis[buf] = Fraction.toFraction((long) 1);
                        basis[i] = Fraction.toFraction((long) 0);
                        int temp;
                        /*temp = masterSlave[i];
                        masterSlave[i] = masterSlave[buf];
                        masterSlave[buf] = temp;*/
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
    protected void updateFunction() throws InvalidTypeException {
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
                                        Fraction.multiplyFractions(system[masterSlave[master] - 1][-masterSlave[slave] + system.length - 1], Fraction.toFraction((long) -1))
                                )
                        );
                    }
                }
                // складываю константы
                function[0] = Fraction.summFractions(
                        function[0],
                        Fraction.multiplyFractions(coef, system[masterSlave[master] - 1][system[0].length - 1])
                );
            }
        }
    }

    /**
     * Конвертирую решенную методом гаусса систему и обновленную функцию в симплекс таблицу
     */
    protected void gausToSimplex() throws InvalidTypeException {
        Fraction[][] simplexTable = new Fraction[system.length + 1][system[0].length - system.length];
        for (int i = 0; i < system.length + 1; i++) {
            for (int j = 0; j < system[0].length - system.length; j++) {
                simplexTable[i][j] = Fraction.toFraction((long) 0);
            }
        }


        // отрезаю от гаусса основные переменные
        for (int i = 0; i < system.length; i++) {
            for (int j = system.length; j < system[0].length; j++) {
                simplexTable[i][j - system.length] = system[i][j];
            }
        }
        // вставляю обновленную функцию
        int counter = 0;
        for (int i = 1; i < function.length; i++) {
            if (!Fraction.equal(function[i], (long) 0)) {
                if (masterSlave[i - 1] < 0) {
                    simplexTable[system.length][counter] = function[i];
                    counter++;
                } else {
                    System.out.println("Возникла ошибка. Основная переменная попала на место зависимой");
                }
            }
        }
        simplexTable[system.length][system[0].length - system.length - 1] = Fraction.multiplyFractions(function[0], Fraction.toFraction((long) -1));
        system = simplexTable;
    }

    /**
     * Выполняю шаг симплекс метода.
     *
     * @return строку и столбец выбранного опорного элемента
     * @throws InvalidTypeException
     */
    public int[] makeStep() throws InvalidTypeException {
        int[] element = pickupElement();
        if (element[0] != -1) {
            calculateNewSystem(element[0], element[1]);
            previous_steps.add(this);
        }
        // если подобранный системой следующий опорный элемент не задан значит нашли решение
        int[] nextElem = idlePickupElement();
        if (nextElem[0] == -1 && nextElem[1] == -1) {
            status = "solved";
        } else if (nextElem[0] == -1 && nextElem[1] != -1) {
            status = "solved";
        }
        return element;
    }


    /**
     * Нахожу номер переменной по её значению в masterSlave
     *
     * @param value - значение. Если нужно найти первую основную переменную нужно передать 1, если первую зависимую переменную, то нужно передать -1 и т.д.
     * @return номер переменной, -1 если не нашел
     */
    public int findVar(int value) {
        for (int i = 0; i < masterSlave.length; i++) {
            if (masterSlave[i] == value) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Вывод решения симплекс метода
     */
    public String printSolution() throws InvalidTypeException {
        StringBuilder res = new StringBuilder();
        int[] nextElem = idlePickupElement();
        if (nextElem[0] == -1 && nextElem[1] == -1) {
            res.append("Система решена\n");
            for (int i = 0; i < system.length - 1; i++) {
                res.append("x").append(findVar(i + 1) + 1).append(" = ").append(system[i][system[i].length - 1]).append("\n");
                System.out.println("x" + (findVar(i + 1) + 1) + " = " + system[i][system[i].length - 1]);
            }
            for (int i = 0; i < system[0].length - 1; i++) {
                res.append("x").append(findVar(-(i + 1)) + 1).append(" = ").append(0).append("\n");
                System.out.println("x" + (findVar(-(i + 1)) + 1) + " = " + 0);
            }
            res.append("\nf = ").append(Fraction.multiplyFractions(system[system.length - 1][system[system.length - 1].length - 1], Fraction.toFraction((long) -1))).append("\n");
            System.out.println("f = " + Fraction.multiplyFractions(system[system.length - 1][system[system.length - 1].length - 1], Fraction.toFraction((long) -1)));
        } else if (nextElem[0] == -1 && nextElem[1] != -1) {
            res.append("Система не имеет решений\n");
        }
        return res.toString();
    }

    /**
     * Сразу решаю таблицу
     *
     * @throws InvalidTypeException
     */
    protected int[] quickSolve() throws InvalidTypeException {
        int[] element = pickupElement();
        while (element[0] != -1) {
            calculateNewSystem(element[0], element[1]);
            element = pickupElement();
        }
        if (element[0] == -1 && element[1] == -1) {
            System.out.println("Система решена");
            printSolution();
        } else if (element[0] == -1 && element[1] != -1) {
            System.out.println("Система не имеет решения");
        }
        status = "solved";
        return element;
    }

    public void initiate() throws InvalidTypeException {
        //сохраняю информацию об основных и зависимых переменных
        masterSlaveInfo();
        // переставляю столбцы системы согласно полученному базису
        gausTableBasisSwap();
        // решаю систему методом гаусса
        system = MathMiddleware.gaus(system);
        // произвожу замены в  функции, отталкиваясь от решения метода гаусса
        updateFunction();
        // приводу матрицу гаусса к стартовой симплекс таблице
        gausToSimplex();
        status = "initiated";
    }

    public void solution() throws InvalidTypeException {
        initiate();
        quickSolve();
    }
}
