package GUI;

import com.sun.jdi.InvalidTypeException;
import helpers.FileWorker;
import helpers.Fraction;
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
import simplex_method.SimplexMethod;

import java.io.IOException;
import java.util.HashMap;

/**
 * Окно с вводом стартовой задачи
 */
public class TaskStartController {
    private Fraction[] function;
    private Fraction[][] system;

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


    private String cellValue(String type, int i) {
        if (Holder.fileData == null) {
            return null;
        } else {
            return Holder.fileData.get(type + i);
        }
    }

    private String cellValue(String type, int i, int j) {
        if (Holder.fileData == null) {
            return null;
        } else {
            return Holder.fileData.get(type + i + "_" + j);
        }
    }

    @FXML
    private void initialize() {
        // делаю текстовые поля для функции и подписи
        for (int i = 0; i < Holder.var_number - 1; i++) {
            gridPane.add(new Text("x" + (i + 1)), i, ApplicationMenu.functionLabelRow);
            gridPane.add(new Text("x" + (i + 1)), i, ApplicationMenu.systemLabelRow);
            gridPane.add(new TextField(cellValue("f", i)), i, ApplicationMenu.functionInputRow);
        }
        // константа для функции и системы
        gridPane.add(new Text("C"), Holder.var_number - 1, ApplicationMenu.functionLabelRow);
        gridPane.add(new Text("C"), Holder.var_number - 1, ApplicationMenu.systemLabelRow);
        gridPane.add(new TextField(cellValue("f", Holder.var_number - 1)), Holder.var_number - 1, ApplicationMenu.functionInputRow);

        // делаю текстовые поля для системы
        for (int j = ApplicationMenu.systemInputRow; j < Holder.sys_number + ApplicationMenu.systemInputRow; j++) {
            for (int i = 0; i < Holder.var_number; i++) {
                gridPane.add(new TextField(cellValue("s", j - ApplicationMenu.systemInputRow + 1, i)), i, j);
            }
        }

        // Если решаю симплекс метод нужно задать базис
        if (Holder.current_task.equals("Симплекс метод")) {
            gridPane.add(new Text("Базис"), 0, 22);
            // делаю текстовые поля для базиса и подписи
            for (int i = 0; i < Holder.var_number - 1; i++) {
                gridPane.add(new Text("x" + (i + 1)), i, ApplicationMenu.basisLabelRow);
                gridPane.add(new TextField(cellValue("b", i)), i, ApplicationMenu.basisInputRow);
            }
        }
        Holder.fileData = null;
    }

    /**
     * Запускаю окно
     *
     * @param actionEvent
     */
    public void runMethod(ActionEvent actionEvent) {
        fileOpen.setOnAction((ActionEvent event) -> {
            try {
                HashMap<String, String> data = FileWorker.openFile();
                Holder.fileData = data;
                fillFieldsByFileData(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        fileSave.setOnAction((ActionEvent event) -> {
            String content = "";
            content += FileWorker.attributeToString("type", Holder.current_task);
            content += FileWorker.attributeToString("var_number", Integer.toString(Holder.var_number));
            content += FileWorker.attributeToString("sys_number", Integer.toString(Holder.sys_number));
            for (int i = 0; i < Holder.var_number; i++) {
                content += FileWorker.attributeToString("f" + i, ((TextField) ApplicationMenu.getNodeFromGridPane(gridPane, i, ApplicationMenu.functionInputRow)).getText());
            }
            for (int j = ApplicationMenu.systemInputRow; j < Holder.sys_number + ApplicationMenu.systemInputRow; j++) {
                for (int i = 0; i < Holder.var_number; i++) {
                    content += FileWorker.attributeToString("s" + (j - ApplicationMenu.systemInputRow + 1) + "_" + i, ((TextField) ApplicationMenu.getNodeFromGridPane(gridPane, i, j)).getText());
                }
            }

            if (Holder.current_task.equals("Симплекс метод")) {
                for (int i = 0; i < Holder.var_number - 1; i++) {
                    content += FileWorker.attributeToString("b" + i, ((TextField) ApplicationMenu.getNodeFromGridPane(gridPane, i, ApplicationMenu.basisInputRow)).getText());
                }
            }
            FileWorker.saveFile(content);
        });

        quick_solve.setOnAction((ActionEvent event) -> {
            // если все введенные пользователем данные корректны, создаю класс задачи
            if (validate()) {
                if (Holder.current_task.equals("Симплекс метод")) {
                    serializeToSimplex();
                    SimplexMethod simplex = (SimplexMethod) Holder.taskClass;
                    try {
                        simplex.solution();
                        ApplicationMenu.showScene(Holder.primaryStage, Holder.solutionFile(), Holder.current_task, 500, 500);
                    } catch (InvalidTypeException | IOException e) {
                        e.printStackTrace();
                    }
                }
                if (Holder.current_task.equals("Искусственный базис")) {
                    serializeToAB();
                    ArtificialBasic ab = (ArtificialBasic) Holder.taskClass;
                    try {
                        ab.solution();
                    } catch (InvalidTypeException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        step_solve.setOnAction((ActionEvent event) -> {
            if (validate()) {
                if (Holder.current_task.equals("Симплекс метод")) {
                    SimplexMethod simplex = (SimplexMethod) Holder.taskClass;
                    try {
                        simplex.initiate();
                        simplex.makeStep();
                        Holder.taskClass = simplex;
                    } catch (InvalidTypeException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * Заполняю поля данными из файла
     */
    private void fillFieldsByFileData(HashMap<String, String> data) throws IOException {
        if (!data.get("type").equals(Holder.current_task)) {
            ApplicationMenu.showAlert("warning", "Типы задач не совпадают", "",
                    "Тип решаемой вами задачи и задачи в файле не совпадают. Вероятна потеря информации или необходимость ввести недостающие данные");
        }
        int var_number = 0;
        int sys_number = 0;
        try {
            var_number = Integer.parseInt(data.get("var_number"));
            sys_number = Integer.parseInt(data.get("sys_number"));
        } catch (NumberFormatException ex) {
            ApplicationMenu.showAlert("error", "Неверный формат файла", "",
                    "Некорректно указаны параметры: `sys_number` или(и) `var_number`");
            return;
        }
        Holder.var_number = var_number;
        Holder.sys_number = sys_number;
        ApplicationMenu.showScene(Holder.primaryStage, Holder.startedTaskFile(), Holder.current_task, 800, 650);
    }

    /**
     * Проверяю введенные данные на корректность
     *
     * @return true или false
     */
    private boolean validate() {
        // проверяю данные для функции
        for (int i = 0; i < Holder.var_number; i++) {
            try {
                Fraction.toFraction(fieldValueType(((TextField) ApplicationMenu.getNodeFromGridPane(gridPane, i, ApplicationMenu.functionInputRow)).getText()));
            } catch (InvalidTypeException ex) {
                ApplicationMenu.showAlert("error", "Ошибка", "Некорретные данные в функции",
                        "Проверьте правильность коэффициентов функции. " +
                                "Коэффициент может быть: целым числом, десятичной дробью (через точку), " +
                                "обыкновенной дробью вида 'целое число/целое число' ");
                return false;
            }
        }
        for (int j = ApplicationMenu.systemInputRow; j < Holder.sys_number + ApplicationMenu.systemInputRow; j++) {
            for (int i = 0; i < Holder.var_number; i++) {
                try {
                    Fraction.toFraction(fieldValueType(((TextField) ApplicationMenu.getNodeFromGridPane(gridPane, i, j)).getText()));
                } catch (InvalidTypeException ex) {
                    ApplicationMenu.showAlert("error", "Ошибка", "Некорретные данные в системе",
                            "Проверьте правильность коэффициентов системы. " +
                                    "Коэффициент может быть: целым числом, десятичной дробью (через точку), " +
                                    "обыкновенной дробью вида 'целое число/целое число' ");
                    return false;
                }
            }
        }
        if (Holder.current_task.equals("Симплекс метод")) {
            // проверяю данные для базиса
            for (int i = 0; i < Holder.var_number - 1; i++) {
                try {
                    Fraction.toFraction(fieldValueType(((TextField) ApplicationMenu.getNodeFromGridPane(gridPane, i, ApplicationMenu.basisInputRow)).getText()));
                } catch (InvalidTypeException ex) {
                    ApplicationMenu.showAlert("error", "Ошибка", "Некорретные данные в базисе",
                            "Проверьте правильность коэффициентов базиса. " +
                                    "Коэффициент может быть: целым числом, десятичной дробью (через точку), " +
                                    "обыкновенной дробью вида 'целое число/целое число' ");
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Конвертирую строку к числу нужного типа
     *
     * @param value
     * @return Object
     */
    private Object fieldValueType(String value) {
        // дробь
        if (value.contains("/")) {
            return value;
        }
        // целое число
        try {
            Long.parseLong(value);
            return Long.parseLong(value);
        } catch (NumberFormatException ignored) {

        }
        // десятичная дробь
        try {
            Double.parseDouble(value);
            return Double.parseDouble(value);
        } catch (NumberFormatException ignored) {

        }
        return null;
    }

    private void serializeBasicData() {
        function = new Fraction[Holder.var_number];
        // задаю данные для функции
        for (int i = 0; i < Holder.var_number - 1; i++) {
            try {
                function[i + 1] = Fraction.toFraction(fieldValueType(((TextField) ApplicationMenu.getNodeFromGridPane(gridPane, i, ApplicationMenu.functionInputRow)).getText()));
            } catch (InvalidTypeException ex) {
                function[i + 1] = new Fraction((long) 0, (long) 1);
            }
        }
        // задаю константу функции
        try {
            function[0] = Fraction.toFraction(fieldValueType(((TextField) ApplicationMenu.getNodeFromGridPane(gridPane, Holder.var_number - 1, ApplicationMenu.functionInputRow)).getText()));
        } catch (InvalidTypeException ex) {
            function[0] = new Fraction((long) 0, (long) 1);
        }

        system = new Fraction[Holder.sys_number][Holder.var_number];
        // задаю систему
        for (int j = ApplicationMenu.systemInputRow; j < Holder.sys_number + ApplicationMenu.systemInputRow; j++) {
            for (int i = 0; i < Holder.var_number; i++) {
                try {
                    system[j - ApplicationMenu.systemInputRow][i] = Fraction.toFraction(fieldValueType(((TextField) ApplicationMenu.getNodeFromGridPane(gridPane, i, j)).getText()));
                } catch (InvalidTypeException ex) {
                    system[j - ApplicationMenu.systemInputRow][i] = new Fraction((long) 0, (long) 1);
                }
            }
        }
    }

    /**
     * Приобразую введенные данные пользователем в стартовый симплекс метод
     */
    private void serializeToSimplex() {
        serializeBasicData();
        Fraction[] basis = new Fraction[Holder.var_number - 1];
        // задаю базис
        for (int i = 0; i < Holder.var_number - 1; i++) {
            try {
                basis[i] = Fraction.toFraction(fieldValueType(((TextField) ApplicationMenu.getNodeFromGridPane(gridPane, i, ApplicationMenu.basisInputRow)).getText()));
            } catch (InvalidTypeException ex) {
                basis[i] = new Fraction((long) 0, (long) 1);
            }
        }
        try {
            SimplexMethod simplex = new SimplexMethod(
                    "min",
                    function,
                    system,
                    basis
            );
            Holder.taskClass = simplex;
        } catch (InvalidTypeException ex) {
            ApplicationMenu.showAlert("error", "Не могу создать симплекс метод", "Ошибка симплекса", "Не могу создать класс симплекс метода");
        }
    }

    /**
     * Приобразую введенные данные пользователем в стартовый метод искусственного базиса
     */
    private void serializeToAB() {
        serializeBasicData();
        try {
            ArtificialBasic ab = new ArtificialBasic(
                    "min",
                    function,
                    system
            );
            Holder.taskClass = ab;
        } catch (InvalidTypeException ex) {
            ApplicationMenu.showAlert("error", "Не могу создать метод искусственного базиса", "Ошибка искусственного базиса", "Не могу создать класс искусственного базиса");
        }
    }

    /**
     * Приобразую введенные данные пользователем в стартовый графический метод
     *
     * @return - класс графического метода с пользовательскими данными
     */
    /*private GraphicalMethod serializeToGraph() {

    }*/
}
