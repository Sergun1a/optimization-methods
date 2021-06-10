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
import javafx.scene.control.Tooltip;
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
            if (Holder.taskClass != null) {
                if (type.equals("f")) {
                    int lenght = ((SimplexMethod) Holder.taskClass).getFunction().length - 1;
                    if (i == lenght) {
                        return ((SimplexMethod) Holder.taskClass).getFunction()[0].toString();
                    }
                    return ((SimplexMethod) Holder.taskClass).getFunction()[i + 1].toString();
                }
                if (type.equals("b") && (Holder.current_task.equals("Симплекс метод") || Holder.current_task.equals("Графический метод"))) {
                    return ((SimplexMethod) Holder.taskClass).getBasis()[i].toString();
                }
            } else
                // проставляю базис
                if (type.equals("b") && (Holder.current_task.equals("Симплекс метод") || Holder.current_task.equals("Графический метод"))) {
                    if (i < Holder.sys_number) {
                        return "1";
                    }
                    return "0";
                }
            return null;
        } else {
            return Holder.fileData.get(type + i);
        }
    }

    private String cellValue(String type, int i, int j) {
        if (Holder.fileData == null) {
            if (Holder.taskClass != null) {
                if (type.equals("s")) {
                    return ((SimplexMethod) Holder.taskClass).getSystem()[i - 1][j].toString();
                }
            }
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
            TextField textField = new TextField(cellValue("f", i));
            textField.setTooltip(new Tooltip("Введите целое число, десятичную дробь или обыкновенную дробь вида \"(целое число)/(целое число)\""));
            gridPane.add(textField, i, ApplicationMenu.functionInputRow);
        }
        // константа для функции и системы
        gridPane.add(new Text("C"), Holder.var_number - 1, ApplicationMenu.functionLabelRow);
        gridPane.add(new Text("C"), Holder.var_number - 1, ApplicationMenu.systemLabelRow);
        TextField textField = new TextField(cellValue("f", Holder.var_number - 1));
        textField.setTooltip(new Tooltip("Введите целое число, десятичную дробь или обыкновенную дробь вида \"(целое число)/(целое число)\""));
        gridPane.add(textField, Holder.var_number - 1, ApplicationMenu.functionInputRow);

        // делаю текстовые поля для системы
        for (int j = ApplicationMenu.systemInputRow; j < Holder.sys_number + ApplicationMenu.systemInputRow; j++) {
            for (int i = 0; i < Holder.var_number; i++) {
                TextField textField1 = new TextField(cellValue("s", j - ApplicationMenu.systemInputRow + 1, i));
                textField1.setTooltip(new Tooltip("Введите целое число, десятичную дробь или обыкновенную дробь вида \"(целое число)/(целое число)\""));
                gridPane.add(textField1, i, j);
            }
        }

        // Если решаю симплекс метод нужно задать базис
        if (Holder.current_task.equals("Симплекс метод") || Holder.current_task.equals("Графический метод")) {
            gridPane.add(new Text("Базис"), 0, 22);
            // делаю текстовые поля для базиса и подписи
            for (int i = 0; i < Holder.var_number - 1; i++) {
                gridPane.add(new Text("x" + (i + 1)), i, ApplicationMenu.basisLabelRow);
                TextField textField1 = new TextField(cellValue("b", i));
                textField1.setTooltip(new Tooltip("Введите целое число, десятичную дробь или обыкновенную дробь вида \"(целое число)/(целое число)\""));
                gridPane.add(textField1, i, ApplicationMenu.basisInputRow);
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
                    try {
                        serializeToSimplex();
                        SimplexMethod simplex = (SimplexMethod) Holder.taskClass;
                        simplex.solution();
                        ApplicationMenu.showScene(Holder.primaryStage, Holder.solutionFile(), Holder.current_task, 500, 500);
                    } catch (InvalidTypeException | IOException e) {
                        e.printStackTrace();
                    }
                }
                if (Holder.current_task.equals("Искусственный базис")) {
                    try {
                        serializeToAB();
                        ArtificialBasic ab = (ArtificialBasic) Holder.taskClass;
                        ab.solution();
                        ApplicationMenu.showScene(Holder.primaryStage, Holder.solutionFile(), Holder.current_task, 500, 500);
                    } catch (InvalidTypeException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        step_solve.setOnAction((ActionEvent event) -> {
            if (validate()) {
                if (Holder.current_task.equals("Симплекс метод")) {
                    try {
                        serializeToSimplex();
                        SimplexMethod simplex = (SimplexMethod) Holder.taskClass;
                        simplex.initiate();
                        Holder.updateTask(simplex);
                        ApplicationMenu.showScene(Holder.primaryStage, Holder.taskStepFile(), Holder.current_task, Holder.screenWidth, Holder.screenHeight);
                    } catch (IOException | InvalidTypeException e) {
                        e.printStackTrace();
                    }
                }
                if (Holder.current_task.equals("Искусственный базис")) {
                    try {
                        serializeToAB();
                        ArtificialBasic ab = (ArtificialBasic) Holder.taskClass;
                        ab.initiate();
                        Holder.updateTask(ab);
                        ApplicationMenu.showScene(Holder.primaryStage, Holder.taskStepFile(), Holder.current_task, Holder.screenWidth, Holder.screenHeight);
                    } catch (InvalidTypeException | IOException e) {
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
        ApplicationMenu.showScene(Holder.primaryStage, Holder.startedTaskFile(), Holder.current_task, Holder.screenWidth, Holder.screenHeight);
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
        if (Holder.current_task.equals("Симплекс метод") || Holder.current_task.equals("Графический метод")) {
            // проверяю данные для базиса
            int counter = 0;
            for (int i = 0; i < Holder.var_number - 1; i++) {
                try {
                    Fraction basis_elem = Fraction.toFraction(fieldValueType(((TextField) ApplicationMenu.getNodeFromGridPane(gridPane, i, ApplicationMenu.basisInputRow)).getText()));
                    if (!(Fraction.equal(basis_elem, (long) 0) || Fraction.equal(basis_elem, (long) 1))) {
                        ApplicationMenu.showAlert("error", "Ошибка", "Некорретные данные в базисе",
                                "Проверьте правильность коэффициентов базиса. " +
                                        "Базис может быть или нулём или единицей");
                        return false;
                    }
                    if (Fraction.equal(basis_elem, (long) 1)) {
                        counter++;
                    }
                } catch (InvalidTypeException ex) {
                    ApplicationMenu.showAlert("error", "Ошибка", "Некорретные данные в базисе",
                            "Проверьте правильность коэффициентов базиса. " +
                                    "Коэффициент может быть: целым числом, десятичной дробью (через точку), " +
                                    "обыкновенной дробью вида 'целое число/целое число' ");
                    return false;
                }
            }
            if (counter != Holder.sys_number) {
                ApplicationMenu.showAlert("error", "Ошибка", "Некорретные данные в базисе",
                        "Кол-во базисных единиц должно быть равно кол-ву строк в системе");
                return false;
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
    public static Object fieldValueType(String value) {
        if (value == null) {
            return null;
        }
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

    private void serializeBasicData() throws InvalidTypeException {
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
    private void serializeToSimplex() throws InvalidTypeException {
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
            Holder.task_solution_steps.add(
                    new SimplexMethod(
                            "min",
                            SimplexMethod.cloneFractionArray(function),
                            SimplexMethod.cloneFractionArray(system),
                            SimplexMethod.cloneFractionArray(basis))
            );
        } catch (InvalidTypeException ex) {
            ApplicationMenu.showAlert("error", "Не могу создать симплекс метод", "Ошибка симплекса", "Не могу создать класс симплекс метода");
        }
    }

    /**
     * Приобразую введенные данные пользователем в стартовый метод искусственного базиса
     */
    private void serializeToAB() throws InvalidTypeException {
        serializeBasicData();
        try {
            ArtificialBasic ab = new ArtificialBasic(
                    "min",
                    function,
                    system
            );
            Holder.taskClass = ab;
            Holder.task_solution_steps.add(new ArtificialBasic("min", SimplexMethod.cloneFractionArray(function), SimplexMethod.cloneFractionArray(system)));
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
