package simplex_method;

import com.sun.jdi.InvalidTypeException;
import helpers.Fraction;
import helpers.MathMiddleware;

public class GraphicalMethod extends ArtificialBasic {
    Fraction[][] artificialBasis;
    private int originalSize;

    public GraphicalMethod(String u_type, Fraction[] u_function, Fraction[][] u_system) throws InvalidTypeException {
        super(u_type, u_function, u_system);
        originalSize = system.length;
    }

    /**
     * Получаю уравнения прямых из решенной методом Гаусса системы и обновлённой функции
     *
     * @return - массив с уравнениями прямых
     */

    private Fraction[][] getLines() throws InvalidTypeException {
        return system;
    }


    /**
     * Привожу систему неравенств (<=) к системе равенств
     */

    private void toEquality() throws InvalidTypeException {
        Fraction[][] newSystem = new Fraction[system.length][system[0].length + system.length];
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
        system = newSystem;
        function = newFunction;
    }

    /**
     * Удаляю временные переменные необходимые для решения симплекс метода
     */
    private void deleteTemporalVariables() {

    }


    @Override
    public void solution() throws InvalidTypeException {
        // привожу систему к системе равенств
        toEquality();
        // устанавливаю стартовую информацию о зависимых и основных переменных
        setABMasterSlave();
        // привожу систему к стартовой таблице искуственного базиса
        toArtificialBasisTable();
        // считаю таблицу искусственного базиса
        int[] element = new int[]{0, 0};
        while (!emptyABLastRow() && element[0] != -1) {
            element = makeStep();
            if (element[0] != -1) {
                system = MathMiddleware.deleteCol(system, element[1]);
                deleteVariable(-(element[1] + 1));
            }
        }
        if ((element[0] == -1 && element[1] != -1) || !emptyABLastRow()) {
            System.out.println("Искусственный базис не имеет решения");
            return;
        }
        // нужно оставить только оригинальные переменные
        deleteTemporalVariables();
        // обновляю функцию согласно решению AB
        updateFunction();
        // получаю уравнения прямых для отрисовки
        Fraction[][] lines = getLines();
        // привожу матрицу искусственного базиса к стартовой таблице симплекс метода
        fromArtificalToSimplex();
        // решаю симплекс таблицу
        quickSolve();
    }
}