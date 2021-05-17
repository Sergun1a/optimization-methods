package GUI;

import helpers.Holder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

/**
 * Окно с вводом стартовой задачи
 */
public class TaskController {
    @FXML
    private GridPane gridPane;


    @FXML
    private void initialize() {
        // делаю текстовые поля для функции
        for (int i = 0; i < Holder.var_number; i++) {
            
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
