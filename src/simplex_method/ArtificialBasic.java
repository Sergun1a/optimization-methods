package simplex_method;

import com.sun.jdi.InvalidTypeException;
import helpers.Fraction;
import helpers.MathMiddleware;

/**
 * Класс хранящий условия симплекс метода с искусственным базисом и методы его решающие
 */
public class ArtificialBasic extends SimplexMethod {
    public ArtificialBasic(String u_type, Fraction[] u_function, Fraction[][] u_system) {
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
    protected void fromArtificalToSimplex() {

    }

    /**
     * Устанавливаю стартовую информацию о зависимых и основных переменных (для метода искусственного базиса)
     */
    protected void setABMasterSlave() throws InvalidTypeException {
        // переменные объявленные в условии будут зависимыми
        for (int i = 0; i < system[i].length; i++) {
            masterSlave[i] = -(i + 1);
        }
        // созданные для решения будут основными
        for (int i = 0; i < system.length; i++) {
            masterSlave[i + system[i].length] = i + 1;
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
        int[] newMasterSlave = new int[masterSlave.length - 1];
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
    public void solution() throws InvalidTypeException {
        // устанавливаю стартовую информацию о зависимых и основных переменных
        setABMasterSlave();
        // привожу систему к стартовой таблице искуственного базиса
        toArtificialBasisTable();
        // считаю таблицу искусственного базиса
        int[] element = pickupElement();
        while (!emptyABLastRow() && element[0] != -1) {
            element = makeStep();
            if (element[0] != -1) {
                MathMiddleware.deleteCol(system, element[0]);
                deleteVariable(-(element[1] + 1));
            }
        }
        if (element[0] == -1 && element[1] == -1) {
            System.out.println("Искусственный базис решен");
            printSolution();
        } else if (element[0] == -1 && element[1] != -1) {
            System.out.println("Система не имеет решения");
            return;
        }
        // привожу матрицу искусственного базиса к стартовой таблице симплекс метода
        fromArtificalToSimplex();
    }

}
