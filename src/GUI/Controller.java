package GUI;


import helpers.FileWorker;
import helpers.Holder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import sample.ApplicationMenu;
import sample.Validator;

import java.io.IOException;

public class Controller {
    @FXML
    private Button simplex_start;

    @FXML
    private Button basis_start;

    @FXML
    private Button graph_start;


    private String attributeToString(String field, String value) {
        if (Validator.validate(FileWorker.getAttributesValidators().
                get(field), value)) {
            return field.trim() + " => " + value + "\n";
        }
        return "";
    }

    /**
     * Запускаю окно с интерфейсом симплекс метода
     *
     * @param actionEvent
     */
    public void runMethod(ActionEvent actionEvent) {
        simplex_start.setOnAction((ActionEvent event) -> {
            // вывожу окно с выбором количества переменных и количества уравнений в системе
            try {
                ApplicationMenu.showScene(Holder.primaryStage, "select.fxml", "Выбор", 300, 300);
                Holder.current_task = "Симплекс метод";
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        basis_start.setOnAction((ActionEvent event) -> {
            // вывожу окно с выбором количества переменных и количества уравнений в системе
            try {
                ApplicationMenu.showScene(Holder.primaryStage, "select.fxml", "Выбор", 300, 300);
                Holder.current_task = "Искусственный базис";
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
