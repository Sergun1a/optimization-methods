package helpers;

import javafx.stage.Stage;
import simplex_method.ArtificialBasic;
import simplex_method.SimplexMethod;

import java.awt.*;
import java.util.HashMap;

/**
 * Класс для хранения переменных, которые должны быть доступны в нескольких местах
 */
public class Holder {
    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    /**
     * Размеры экрана пользователя
     */
    public static int screenWidth = (int) screenSize.getWidth();
    public static int screenHeight = (int) screenSize.getHeight();
    /**
     * Основная сцена
     */
    public static Stage primaryStage;

    /**
     * Текущая решаемая задача
     */
    public static String current_task;

    /**
     * Кол-во уравнений системы
     */
    public static int sys_number;
    /**
     * Кол-во переменных
     */
    public static int var_number;

    /**
     * Сцена селекта
     */
    public static Stage selectStage;

    /**
     * Сцена текущей решаемой задачи
     */
    public static Stage taskStage;

    /**
     * Класс для текущей решаемой задачи
     */
    public static Object taskClass;

    /**
     * Данные из открытого файла
     */
    public static HashMap<String, String> fileData;

    /**
     * Fxml файл для вывода решения
     */
    public static String solutionFile() {
        if (current_task.equals("Симплекс метод")) {
            return "solution_output.fxml";
        }
        if (current_task.equals("Искусственный базис")) {
            return "solution_output.fxml";
        }
        return "";
    }

    public static String startedTaskFile() {
        return "task_start.fxml";
    }

    public static String taskStepFile() {
        return "task_step.fxml";
    }

    public static void updateTask(Object task) {
        taskClass = task;
        var_number = ((SimplexMethod) task).getSystem()[0].length;
        sys_number = ((SimplexMethod) task).getSystem().length;
    }

    public static String[] fileArgumentsForTask() {
        if (taskClass instanceof ArtificialBasic) {
            return ArtificialBasic.fileArguments();
        }
        if (taskClass instanceof SimplexMethod) {
            return SimplexMethod.fileArguments();
        }
        return new String[]{};
    }
}
