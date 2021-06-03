package simplex_method;

import com.sun.jdi.InvalidTypeException;
import helpers.Fraction;
import helpers.MathMiddleware;

/**
 * Класс хранящий условия симплекс метода с искусственным базисом и методы его решающие
 */
public class ArtificialBasic extends SimplexMethod {
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
        };
    }

    public ArtificialBasic(String u_type, Fraction[] u_function, Fraction[][] u_system) throws InvalidTypeException {
        super(u_type, u_function, u_system, new Fraction[u_system[0].length + u_system.length]);
    }

    /**
     * Вычисляю значения элемента последней строки для стартового симплекс метода
     *
     * @return
     */
    protected Fraction abLastRowEl(int col) throws InvalidTypeException {
        Fraction value = new Fraction((long) 0, (long) 1);
        for (int i = 0; i < system.length; i++) {
            value = Fraction.summFractions(value, system[i][col]);
        }
        return Fraction.multiplyFractions(value, Fraction.toFraction((long) -1));
    }

    /**
     * Перевожу переданную систему в стартовую таблицу искусственного базиса
     */
    protected void toArtificialBasisTable() throws InvalidTypeException {
        Fraction[][] artificialBasis = new Fraction[system.length + 1][system[0].length];
        // копирую все строчки из системы
        for (int i = 0; i < system.length; i++) {
            for (int j = 0; j < system[i].length; j++) {
                artificialBasis[i][j] = system[i][j];
            }
        }
        // расчитываю последнюю строчку системы
        for (int j = 0; j < system[0].length; j++) {
            artificialBasis[artificialBasis.length - 1][j] = abLastRowEl(j);
        }
        system = artificialBasis;
    }

    protected void calculateBasis() {

    }

    /**
     * Привожу матрицу искусственного базиса к стартовой таблице симплекс метода
     */
    protected void fromArtificalToSimplex() throws InvalidTypeException {
        for (int i = 0; i < system[system.length - 1].length; i++) {
            int var = findVar(-(i + 1));
            system[system.length - 1][i] = function[var + 1];
        }
        system[system.length - 1][system[system.length - 1].length - 1] = Fraction.multiplyFractions(function[0], Fraction.toFraction((long) -1));
    }

    /**
     * Устанавливаю стартовую информацию о зависимых и основных переменных (для метода искусственного базиса)
     */
    protected void setABMasterSlave() throws InvalidTypeException {
        masterSlave = new int[system[0].length + system.length - 1];
        // переменные объявленные в условии будут зависимыми
        for (int i = 0; i < system[0].length - 1; i++) {
            masterSlave[i] = -(i + 1);
        }
        // созданные для решения будут основными
        for (int i = 0; i < system.length; i++) {
            masterSlave[i + system[i].length - 1] = i + 1;
        }
    }

    /**
     * Проверяю, что последняя строка искусственного базиса = 0
     *
     * @return - true или false
     */
    protected boolean emptyABLastRow() throws InvalidTypeException {
        for (int i = 0; i < system[system.length - 1].length; i++) {
            if (!Fraction.equal(system[system.length - 1][i], Fraction.toFraction((long) 0))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Удаляю переменную из masterSlave, по его значению в masterSlave
     */
    protected void deleteVariable(int elVal) {
        int[] newMasterSlave = new int[masterSlave.length];
        for (int i = 0; i < masterSlave.length; i++) {
            if (masterSlave[i] != elVal) {
                if (masterSlave[i] < elVal) {
                    newMasterSlave[i] = masterSlave[i] + 1;
                } else {
                    newMasterSlave[i] = masterSlave[i];
                }
            } else {
                masterSlave[i] = 0;
            }
        }
        masterSlave = newMasterSlave;
    }

    @Override
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
                                        Fraction.multiplyFractions(system[masterSlave[master] - 1][-masterSlave[slave] - 1], Fraction.toFraction((long) -1))
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

    @Override
    public void initiate() throws InvalidTypeException {
        // устанавливаю стартовую информацию о зависимых и основных переменных
        setABMasterSlave();
        // привожу систему к стартовой таблице искуственного базиса
        toArtificialBasisTable();
    }

    @Override
    public int[] makeStep() throws InvalidTypeException {
        int[] element = super.makeStep();
        if (element[0] != -1) {
            system = MathMiddleware.deleteCol(system, element[1]);
            deleteVariable(-(element[1] + 1));
        }
        return element;
    }

    @Override
    public void solution() throws InvalidTypeException {
        initiate();
        // считаю таблицу искусственного базиса
        int[] element = new int[]{0, 0};
        while (!emptyABLastRow() && element[0] != -1) {
            element = makeStep();
        }
        if ((element[0] == -1 && element[1] != -1) || !emptyABLastRow()) {
            System.out.println("Искусственный базис не имеет решения");
            return;
        }
        // обновляю функцию согласно решению AB
        updateFunction();
        // привожу матрицу искусственного базиса к стартовой таблице симплекс метода
        fromArtificalToSimplex();
        // решаю симплекс таблицу
        quickSolve();
    }

}
