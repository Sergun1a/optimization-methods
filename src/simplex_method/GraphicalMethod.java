package simplex_method;

import com.sun.jdi.InvalidTypeException;
import helpers.Fraction;
import helpers.MathMiddleware;

public class GraphicalMethod extends SimplexMethod {
    Fraction[][] gausSolution;

    public GraphicalMethod(String u_type, Fraction[] u_function, Fraction[][] u_system, Fraction[] u_basis) throws InvalidTypeException {
        super(u_type, u_function, u_system, new Fraction[u_system[0].length + u_system.length]);
        generateBasis();
    }

    /**
     * Генерирую базис для решения симплекс методом
     */
    private void generateBasis() throws InvalidTypeException {
        basis = new Fraction[system[0].length - 1];
        // забиваю базис нулями
        for (int i = 0; i < system[0].length - 1; i++) {
            basis[i] = Fraction.toFraction((long) 0);
        }
        // указываю основные переменные
        int counter = 0;
        for (int i = 0; i < system[0].length - 1; i++) {
            if (counter < system.length && !Fraction.equal(system[counter][i], 0)) {
                basis[i] = Fraction.toFraction((long) 1);
                counter++;
            }
        }
    }

    /**
     * Получаю уравнения прямых из решенной методом Гаусса системы и обновлённой функции
     *
     * @return - массив с уравнениями прямых
     */
    private Fraction[][] getLines() throws InvalidTypeException {
        return system;
    }

    @Override
    public void solution() throws InvalidTypeException {
        //сохраняю информацию об основных и зависимых переменных
        masterSlaveInfo();
        // переставляю столбцы системы согласно полученному базису
        gausTableBasisSwap();
        // решаю систему методом гаусса
        system = MathMiddleware.gaus(system);
        // произвожу замены в  функции, отталкиваясь от решения метода гаусса
        updateFunction();
        // получаю уравнения прямых для отрисовки
        Fraction[][] lines = getLines();
        // приводу матрицу гаусса к стартовой симплекс таблице
        gausToSimplex();
        // нахожу опорные элементы и считаю новые симплекс таблицы
        quickSolve();
    }
}
