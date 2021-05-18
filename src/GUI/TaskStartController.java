package GUI;

import helpers.Holder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import sample.ApplicationMenu;
import simplex_method.ArtificialBasic;
import simplex_method.GraphicalMethod;
import simplex_method.SimplexMethod;

/**
 * Окно с вводом стартовой задачи
 */
public class TaskStartController {
    @FXML
    private GridPane gridPane;

    @FXML
    private Button quick_solve;

    @FXML
    private Button step_solve;

    @FXML
    private MenuItem fileSave;

    @FXML
    private MenuItem fileOpen;

    @FXML
    private void initialize() {
        // делаю текстовые поля для функции и подписи
        for (int i = 0; i < Holder.var_number - 1; i++) {
            gridPane.add(new Text("x" + (i + 1)), i, ApplicationMenu.functionLabelRow);
            gridPane.add(new Text("x" + (i + 1)), i, ApplicationMenu.systemLabelRow);
            gridPane.add(new TextField(), i, ApplicationMenu.functionInputRow);
        }
        // константа для функции и системы
        gridPane.add(new Text("C"), Holder.var_number - 1, ApplicationMenu.functionLabelRow);
        gridPane.add(new Text("C"), Holder.var_number - 1, ApplicationMenu.systemLabelRow);
        gridPane.add(new TextField(), Holder.var_number - 1, ApplicationMenu.functionInputRow);

        // делаю текстовые поля для системы
        for (int j = ApplicationMenu.systemInputRow; j < Holder.sys_number + ApplicationMenu.systemInputRow; j++) {
            for (int i = 0; i < Holder.var_number; i++) {
                gridPane.add(new TextField(), i, j);
            }
        }

        // Если решаю симплекс метод нужно задать базис
        if (Holder.current_task.equals("Симплекс метод")) {
            // делаю текстовые поля для базиса и подписи
            for (int i = 0; i < Holder.var_number - 1; i++) {
                gridPane.add(new Text("x" + (i + 1)), i, ApplicationMenu.basisLabelRow);
                gridPane.add(new TextField(), i, ApplicationMenu.basisInputRow);
            }
            // константа для базиса
            gridPane.add(new Text("C"), Holder.var_number - 1, ApplicationMenu.basisLabelRow);
            gridPane.add(new TextField(), Holder.var_number - 1, ApplicationMenu.basisInputRow);
        }
    }

    /**
     * Запускаю окно
     *
     * @param actionEvent
     */
    public void runMethod(ActionEvent actionEvent) {
        quick_solve.setOnAction((ActionEvent event) -> {

        });
    }

    /**
     * Проверяю введенные данные на корректность
     *
     * @return true или false
     */
    private boolean validate() {
        return true;
    }

    /**
     * Приобразую введенные данные пользователем в стартовый симплекс метод
     * @return - класс симплекс метода с пользовательскими данными
     */
    private SimplexMethod serializeToSimplex() {

    }

    /**
     * Приобразую введенные данные пользователем в стартовый метод искусственного базиса
     * @return - класс метода искусственного базиса с пользовательскими данными
     */
    private ArtificialBasic serializeToAB() {

    }

    /**
     * Приобразую введенные данные пользователем в стартовый графический метод
     * @return - класс графического метода с пользовательскими данными
     */
    private GraphicalMethod serializeToGraph() {

    }
}
