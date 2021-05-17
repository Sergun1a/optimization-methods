package GUI;


import helpers.Holder;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.ApplicationMenu;
import sample.FileWorker;
import sample.Validator;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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
                Stage simplexStage = ApplicationMenu.showNewStage("select.fxml", "Выбор", 300, 300);
                Holder.selectStage = simplexStage;
                Holder.current_task = "Симплекс метод";
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
