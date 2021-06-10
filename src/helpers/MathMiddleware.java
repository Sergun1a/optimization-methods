package helpers;

import com.sun.jdi.InvalidTypeException;

/**
 * Вспомогательный класс, реализуюший некоторые математические методы
 */
final public class MathMiddleware {
    // function for exchanging two rows
    // of a matrix
    static void swap(long[][] mat,
                     int row1, int row2, int col) {
        for (int i = 0; i < col; i++) {
            long temp = mat[row1][i];
            mat[row1][i] = mat[row2][i];
            mat[row2][i] = temp;
        }
    }

    /**
     * Меняю местами столбцы матрицы
     *
     * @param mat  - матрица
     * @param col1 - номер первого столбца для замены
     * @param col2 - номер второго столбца для замены
     * @return Fraction[][] массив с поменяными строками
     */
    public static Fraction[][] swapCol(Fraction[][] mat, int col1, int col2) {
        for (int i = 0; i < mat.length; i++) {
            Fraction temp = mat[i][col1];
            mat[i][col1] = mat[i][col2];
            mat[i][col2] = temp;
        }
        return mat;
    }

    /**
     * Удаляю столбец в переданной матрице
     *
     * @param mat - переданная матрица
     * @param col - номер столбца
     * @return - матрица с вырезанным столбцом
     */
    public static Fraction[][] deleteCol(Fraction[][] mat, int col) {
        Fraction[][] new_mat = new Fraction[mat.length][mat[0].length - 1];
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < col; j++) {
                new_mat[i][j] = mat[i][j];
            }
            // если столбец не последний
            if (mat[0].length - 1 != col) {
                for (int j = col + 1; j < mat[0].length; j++) {
                    new_mat[i][j - 1] = mat[i][j];
                }
            }
        }
        return new_mat;
    }

    /**
     * Удаляю строку в переданной матрице
     *
     * @param mat - переданная матрица
     * @param row - номер строки
     * @return - матрица с вырезанной строкой
     */
    public static Fraction[][] deleteRow(Fraction[][] mat, int row) {
        Fraction[][] new_mat = new Fraction[mat.length - 1][mat[0].length];
        for (int j = 0; j < row; j++) {
            for (int i = 0; i < mat[j].length; i++)
                new_mat[j][i] = mat[j][i];
        }
        // если строка не последняя
        if (mat.length - 1 != row) {
            for (int j = row + 1; j < mat[0].length; j++) {
                for (int i = 0; i < mat[j].length; i++)
                    new_mat[j - 1][i] = mat[j][i];
            }
        }
        return new_mat;
    }
    /**
     * Нахождение НОК двух чисел
     *
     * @param a - первое число
     * @param b - второе число
     * @return НОК
     */
    public static long nok(long a, long b) {
        while (b != 0) {
            long tmp = a % b;
            a = b;
            b = tmp;
        }
        if (a == 0) {
            return (long) 1;
        }
        return a;
    }

    /**
     * Нахождение НОД двух чисел
     *
     * @param a
     * @param b
     * @return НОД
     */
    public static long nod(long a, long b) {
        long res = a / nok(a, b) * b;
        if (res == 0) {
            return (long) 1;
        }
        return res;
    }


    /**
     * Решаю переданную систему методом Гаусса
     *
     * @param a - матрица которую нужно решить методом гаусса
     * @return Матрицу решенную методом Гаусса
     */
    public static Fraction[][] gaus(Fraction[][] a) throws InvalidTypeException {
        Fraction m;
        // прямой ход гаусса
        for (int k = 1; k < a.length; k++) {
            for (int j = k; j < a.length; j++) {
                m = Fraction.divisionFractions(a[j][k - 1], a[k - 1][k - 1]);
                for (int i = 0; i < a[j].length; i++) {
                    a[j][i] = Fraction.subtractionFractions(a[j][i], Fraction.multiplyFractions(m, a[k - 1][i]));
                }
            }
        }

        // привожу главную диагональ к единицам
        for (int j = 0; j < a.length; j++) {
            Fraction primaryDiagonalElement = a[j][j];
            for (int i = 0; i < a[j].length; i++) {
                a[j][i] = Fraction.multiplyFractions(a[j][i], Fraction.reverseFraction(primaryDiagonalElement));
            }
        }

        // обратный ход гаусса
        for (int i = 1; i < a.length; i++) {
            for (int j = 0; j < i; j++) {
                Fraction coef = Fraction.divisionFractions(a[j][i], a[i][i]);
                for (int rowEl = i; rowEl < a[0].length; rowEl++) {
                    a[j][rowEl] = Fraction.subtractionFractions(
                            a[j][rowEl],
                            Fraction.multiplyFractions(a[i][rowEl], coef)
                    );
                }
            }
        }

        return a;
    }
}
