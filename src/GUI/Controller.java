package GUI;


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

    @FXML
    private Button select_end;

    @FXML
    private Text error_field;

    @FXML
    private ComboBox<Integer> var_number;

    @FXML
    private ComboBox<Integer> sys_number;


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
                setSelectComboBoxes();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Заполняю комбобоксы с вариантами количества переменных в функции и  количестве уравнений в системе
     */
    public void setSelectComboBoxes() {
        ObservableList<Integer> observableList = FXCollections.observableArrayList(1, 2, 3);
        var_number.setItems(observableList);
        /*for (int i = 1; i < 17; i++) {
            var_number.setValue(i);
            sys_number.setValue(i);
        }*/
    }
}
