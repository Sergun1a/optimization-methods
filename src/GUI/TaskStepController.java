package GUI;

import com.sun.jdi.InvalidTypeException;
import helpers.Fraction;
import helpers.Holder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import sample.ApplicationMenu;
import simplex_method.ArtificialBasic;
import simplex_method.SimplexMethod;

import java.io.IOException;

import static GUI.TaskStartController.fieldValueType;


public class TaskStepController {
    @FXML
    private GridPane gridPane;

    @FXML
    private Button next_step;

    @FXML
    private Button step_back;

    @FXML
    private TextField basis_row;

    @FXML
    private TextField basis_col;

    @FXML
    private void initialize() throws InvalidTypeException {
        // делаю подписи
        for (int j = ApplicationMenu.functionInputRow; j < Holder.sys_number + ApplicationMenu.functionInputRow - 1; j++) {
            gridPane.add(new Text("x" + (((SimplexMethod) Holder.taskClass).findVar(j - ApplicationMenu.functionInputRow + 1) + 1)), 0, j);
        }
        for (int i = 0; i < Holder.var_number - 1; i++) {
            gridPane.add(new Text("x" + (((SimplexMethod) Holder.taskClass).findVar(-i - 1) + 1)), i + 1, ApplicationMenu.functionInputRow - 1);
        }
        gridPane.add(new Text("C"), Holder.var_number, ApplicationMenu.functionInputRow - 1);

        // делаю текстовые поля для системы
        for (int j = ApplicationMenu.functionInputRow; j < Holder.sys_number + ApplicationMenu.functionInputRow; j++) {
            for (int i = 0; i < Holder.var_number; i++) {
                TextField textField = new TextField(cellValue(j - ApplicationMenu.functionInputRow, i));
                textField.setEditable(false);
                gridPane.add(textField, i + 1, j);
            }
        }

        // устанавливаю информацию об опорном элементе
        basis_row.setText(String.valueOf(((SimplexMethod) Holder.taskClass).idlePickupElement()[0] + 1));
        basis_col.setText(String.valueOf(((SimplexMethod) Holder.taskClass).idlePickupElement()[1] + 1));
    }


    /**
     * Высчитываю значение клетки системы
     *
     * @param j - номер уравнения/неравенства
     * @param i - номер переменной
     * @return значение клетки
     */
    private String cellValue(int j, int i) {
        if (Holder.taskClass != null) {
            return ((SimplexMethod) Holder.taskClass).getSystem()[j][i].toString();
        }
        return "";
    }

    /**
     * Запускаю окно
     *
     * @param actionEvent
     */
    public void runMethod(ActionEvent actionEvent) {
        next_step.setOnAction((ActionEvent event) -> {
            if (Holder.current_task.equals("Симплекс метод")) {
                SimplexMethod task = (SimplexMethod) Holder.taskClass;
                if (validate()) {
                    try {
                        if (task.checkPickedUpElement(Integer.parseInt(basis_row.getText()) - 1, Integer.parseInt(basis_col.getText()) - 1)) {
                            task.setUserSupportingElem(Integer.parseInt(basis_col.getText()) - 1, Integer.parseInt(basis_row.getText()) - 1);
                            task.makeStep();
                            Holder.updateTask(task);
                            ApplicationMenu.showScene(Holder.primaryStage, Holder.taskStepFile(), Holder.current_task, Holder.screenWidth, Holder.screenHeight);
                        } else {
                            ApplicationMenu.showAlert("error", "Ошибка", "Некорретные данные в системе",
                                    "Выбранный элемент не может быть опорным");
                        }
                    } catch (InvalidTypeException | IOException e) {
                        e.printStackTrace();
                        ApplicationMenu.showAlert("error", "Ошибка", "Некорретные данные в системе",
                                "Некорректный данные для выбора опорного элемента");
                    }
                }
            }
            if (Holder.current_task.equals("Искусственный базис")) {
                ArtificialBasic task = (ArtificialBasic) Holder.taskClass;
            }

        });
    }


    private boolean validate() {
        int col = -1;
        int row = -1;
        try {
            row = Integer.parseInt(basis_row.getText());
            col = Integer.parseInt(basis_col.getText());
        } catch (NumberFormatException ex) {
            ApplicationMenu.showAlert("error", "Ошибка", "Некорретные данные в системе",
                    "Проверьте правильность строки и столбца опорного элемента. " +
                            "Они могут быть только целым числом");
            return false;
        }

        if (col <= 0) {
            ApplicationMenu.showAlert("error", "Ошибка", "Некорретные данные в системе",
                    "Проверьте правильность столбца опорного элемента. " +
                            "Он должен быть больше нуля");
            return false;
        }
        if (row <= 0) {
            ApplicationMenu.showAlert("error", "Ошибка", "Некорретные данные в системе",
                    "Проверьте правильность строки опорного элемента. " +
                            "Она должна быть больше нуля");
            return false;
        }
        if (col <= 0) {
            ApplicationMenu.showAlert("error", "Ошибка", "Некорретные данные в системе",
                    "Проверьте правильность столбца опорного элемента. " +
                            "Он должен быть больше нуля");
            return false;
        }
        if (row > ((SimplexMethod) Holder.taskClass).getSystem().length) {
            ApplicationMenu.showAlert("error", "Ошибка", "Некорретные данные в системе",
                    "Проверьте правильность строки опорного элемента. " +
                            "Она не должна быть больше кол-ва строк в текущей системе");
            return false;
        }
        if (col > ((SimplexMethod) Holder.taskClass).getSystem()[0].length) {
            ApplicationMenu.showAlert("error", "Ошибка", "Некорретные данные в системе",
                    "Проверьте правильность столбца опорного элемента. " +
                            "Она не должен быть больше кол-ва столбцов в текущей системе");
            return false;
        }

        return true;
    }
}
