package GUI;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.ApplicationMenu;
import sample.FileWorker;
import sample.Validator;

import java.io.IOException;

public class Controller {
    @FXML
    private Button simplex_start;

    @FXML
    private Button basis_start;

    @FXML
    private Button graph_start;

    @FXML
    private Button select_end;

    @FXML
    private Text error_field;


    public void initialize() {

    }


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
                Stage simplexStage = ApplicationMenu.showNewStage("select.fxml", "Выбор", 800, 600);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
