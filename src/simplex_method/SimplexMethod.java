package simplex_method;

import com.sun.jdi.InvalidTypeException;
import helpers.Fraction;

import java.util.LinkedList;

/**
 * Класс хранящий условия симплекс метода и методы его решающие
 */
public class SimplexMethod {
    SimplexMethod(String u_type, Fraction[] u_function, Fraction[][] u_system) {
        type = u_type;
        function = u_function;
        system = u_system;
        copy_system = u_system;
    }

    /**
     * Переменная для хранения предыдущих шагов решения
     */
    private LinkedList<SimplexMethod> previous_steps;

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
            if (Fraction.moreThen(system[i][column], 0)) {
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
     * @return - массив int, где первый элемент строка выбранного элемента, а второй элемент столбцец выбранного элемента. Если элемент не выбран возвращаю массив со значениями [-1, -1]
     * @throws InvalidTypeException
     */
    private int[] pickupElement() throws InvalidTypeException {
        int row = -1;
        int column = -1;

        int rows = system.length;
        int columns = system[0].length;

        // подбираю подходящие столбцы
        for (int i = 0; i < columns - 1; i++) {
            if (Fraction.lowerThen(system[rows - 1][i], 0)) {
                if (chooseRow(i) != -1) {
                    return new int[]{chooseRow(i), i};
                }
            }
        }
        return new int[]{-1, -1};
    }


}
