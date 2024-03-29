package GUI;

import helpers.FileWorker;
import helpers.Holder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import sample.ApplicationMenu;

import java.io.IOException;
import java.util.HashMap;

import static GUI.TaskStartController.fillFieldsByFileData;

/**
 * Контроллер для выбора количества переменных и количества уравнений в системе
 */
public class SelectController {
    @FXML
    private ComboBox var_number;

    @FXML
    private ComboBox sys_number;

    @FXML
    private Button select_end;

    @FXML
    private Text error_field;

    @FXML
    private Button file_open;

    @FXML
    private void initialize() {
        setSelectComboBoxes();
    }

    /**
     * Запускаю окно
     *
     * @param actionEvent
     */
    public void runMethod(ActionEvent actionEvent) {
        select_end.setOnAction((ActionEvent event) -> {
            if (var_number.getValue() != null && sys_number.getValue() != null) {
                try {
                    Holder.var_number = ((int) var_number.getValue()) + 1;
                    Holder.sys_number = (int) sys_number.getValue();
                    if (Holder.sys_number > Holder.var_number) {
                        error_field.setText("Количества строк в системе не может превышать кол-ва переменных");
                    } else {
                        ApplicationMenu.showScene(Holder.primaryStage, Holder.startedTaskFile(), Holder.current_task, Holder.screenWidth, Holder.screenHeight);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    error_field.setText("Некорректные значения");
                }
            } else {
                error_field.setText("Не выбраны значения");
            }
        });

        file_open.setOnAction((ActionEvent event) -> {
            try {
                HashMap<String, String> data = FileWorker.openFile();
                Holder.fileData = data;
                fillFieldsByFileData(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Заполняю комбобоксы с вариантами количества переменных в функции и количества уравнений в системе
     */
    public void setSelectComboBoxes() {
        ObservableList<Integer> observableList = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
        var_number.setItems(observableList);
        sys_number.setItems(observableList);
    }
}
