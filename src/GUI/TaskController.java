package GUI;

import helpers.Holder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 * Окно с вводом стартовой задачи
 */
public class TaskController {
    @FXML
    private GridPane gridPane;


    @FXML
    private void initialize() {
        // делаю текстовые поля для функции и подписи
        for (int i = 0; i < Holder.var_number - 1; i++) {
            gridPane.add(new Text("x" + (i + 1)), i, 1);
            gridPane.add(new Text("x" + (i + 1)), i, 4);
            gridPane.add(new TextField(), i, 2);
        }
        // константа для функции и системы
        gridPane.add(new Text("C"), Holder.var_number - 1, 1);
        gridPane.add(new Text("C"), Holder.var_number - 1, 4);
        gridPane.add(new TextField(), Holder.var_number - 1, 2);

        // делаю текстовые поля для системы
        for (int j = 5; j < Holder.sys_number + 5; j++) {
            for (int i = 0; i < Holder.var_number; i++) {
                gridPane.add(new TextField(), i, j);
            }
        }

        // Если решаю симплекс метод нужно задать базис
        if (Holder.current_task.equals("Симплекс метод")) {

        }
    }

    /**
     * Запускаю окно
     *
     * @param actionEvent
     */
    public void runMethod(ActionEvent actionEvent) {
        /*select_end.setOnAction((ActionEvent event) -> {
            if (var_number.getValue() != null && sys_number.getValue() != null) {
                try {
                    Stage taskStartStage = ApplicationMenu.showNewStage(Holder.startedTaskFile(), Holder.current_task, 500, 500);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                error_field.setText("Не выбраны значения");
            }
        });*/
    }
}
