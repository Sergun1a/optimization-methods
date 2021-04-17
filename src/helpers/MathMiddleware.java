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
                     int row1, int row2, int col)
    {
        for (int i = 0; i < col; i++)
        {
            long temp = mat[row1][i];
            mat[row1][i] = mat[row2][i];
            mat[row2][i] = temp;
        }
    }

    /**
     * Решаю переданную матрицу методом Гаусса
     *
     * @param rawMatrix - матрицу которую нужно решить
     * @return - решенная методом Гаусса матрица
     */
    public long[][] gaysMethod(long[][] rawMatrix) {
        long[][] solvedMatrix = rawMatrix;
        return solvedMatrix;
    }

    /**
     * Определяю ранг переданной матрицы
     *
     * @param mat - матрица
     * @return ранг переданной матрицы
     */
    public long rang(long[][] mat) {
        int rank = C;

        for (int row = 0; row < rank; row++)
        {

            // Before we visit current row
            // 'row', we make sure that
            // mat[row][0],....mat[row][row-1]
            // are 0.

            // Diagonal element is not zero
            if (mat[row][row] != 0)
            {
                for (int col = 0; col < R; col++)
                {
                    if (col != row)
                    {
                        // This makes all entries
                        // of current column
                        // as 0 except entry
                        // 'mat[row][row]'
                        double mult =
                                (double)mat[col][row] /
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
            else
            {
                boolean reduce = true;

                // Find the non-zero element
                // in current column
                for (int i = row + 1; i < R; i++)
                {
                    // Swap the row with non-zero
                    // element with this row.
                    if (mat[i][row] != 0)
                    {
                        swap(mat, row, i, rank);
                        reduce = false;
                        break ;
                    }
                }

                // If we did not find any row with
                // non-zero element in current
                // columnm, then all values in
                // this column are 0.
                if (reduce)
                {
                    // Reduce number of columns
                    rank--;

                    // Copy the last column here
                    for (int i = 0; i < R; i ++)
                        mat[i][row] = mat[i][rank];
                }

                // Process this row again
                row--;
            }
        }

        return rank;
    }

}
