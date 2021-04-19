package simplex_method;

import helpers.Fraction;

/**
 * Класс хранящий условия симплекс метода и методы его решающие
 */
public class SimplexMethod {
    public static final String MAX = "max";
    public static final String MIN = "min";

    /**
     * Кол-во переменный функции
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



}
