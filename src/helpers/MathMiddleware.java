package helpers;

/**
 * Вспомогательный класс, реализуюший некоторые математические методы
 */
final public class MathMiddleware {

    static final int R = 11;
    static final int C = 11;

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
        Fraction[][] new_mat = new Fraction[mat.length - 1][mat[0].length];
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < col; j++) {
                new_mat[i][j] = mat[i][j];
            }
            for (int j = col + 1; j < mat[0].length; j++) {
                new_mat[j - 1][i] = mat[j][i];
            }
        }
        return new_mat;
    }

    /**
     * Определяю ранг переданной матрицы
     *
     * @param mat - матрица
     * @return ранг переданной матрицы
     */
    public long rang(long[][] mat) {
        int rank = C;

        for (int row = 0; row < rank; row++) {

            // Before we visit current row
            // 'row', we make sure that
            // mat[row][0],....mat[row][row-1]
            // are 0.

            // Diagonal element is not zero
            if (mat[row][row] != 0) {
                for (int col = 0; col < R; col++) {
                    if (col != row) {
                        // This makes all entries
                        // of current column
                        // as 0 except entry
                        // 'mat[row][row]'
                        double mult =
                                (double) mat[col][row] /
                                        mat[row][row];

                        for (int i = 0; i < rank; i++)

                            mat[col][i] -= mult
                                    * mat[row][i];
                    }
                }
            }

            // Diagonal element is already zero.
            // Two cases arise:
            // 1) If there is a row below it
            // with non-zero entry, then swap
            // this row with that row and process
            // that row
            // 2) If all elements in current
            // column below mat[r][row] are 0,
            // then remvoe this column by
            // swapping it with last column and
            // reducing number of columns by 1.
            else {
                boolean reduce = true;

                // Find the non-zero element
                // in current column
                for (int i = row + 1; i < R; i++) {
                    // Swap the row with non-zero
                    // element with this row.
                    if (mat[i][row] != 0) {
                        swap(mat, row, i, rank);
                        reduce = false;
                        break;
                    }
                }

                // If we did not find any row with
                // non-zero element in current
                // columnm, then all values in
                // this column are 0.
                if (reduce) {
                    // Reduce number of columns
                    rank--;

                    // Copy the last column here
                    for (int i = 0; i < R; i++)
                        mat[i][row] = mat[i][rank];
                }

                // Process this row again
                row--;
            }
        }

        return rank;
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
    public static Fraction[][] gaus(Fraction[][] a) {
        Fraction x[] = new Fraction[a.length];
        for (int i = 0; i < x.length; i++) {
            x[i] = a[i][a[i].length - 1];
        }
        Fraction m;
        // прямой ход гаусса
        for (int k = 1; k < a.length; k++) {
            for (int j = k; j < a.length; j++) {
                m = Fraction.divisionFractions(a[j][k - 1], a[k - 1][k - 1]);
                for (int i = 0; i < a[j].length; i++) {
                    a[j][i] = Fraction.subtractionFractions(a[j][i], Fraction.multiplyFractions(m, a[k - 1][i]));
                }
                x[j] = Fraction.subtractionFractions(x[j], Fraction.multiplyFractions(m, x[k - 1]));
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
