package helpers;

import javafx.stage.Stage;
import simplex_method.ArtificialBasic;
import simplex_method.SimplexMethod;

import java.util.HashMap;

/**
 * Класс для хранения переменных, которые должны быть доступны в нескольких местах
 */
public class Holder {

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


    public static String startedTaskFile() {
        /*if (current_task.equals("Симплекс метод")) {
            return "simplex_start.fxml";
        }
        if (current_task.equals("Искусственный базис")) {
            return "basis_start.fxml";
        }
        if (current_task.equals("Графический метод")) {
            return "graph_start.fxml";
        }*/
        return "task_start.fxml";
    }

    public static String[] fileArgumentsForTask() {
        if (taskClass instanceof SimplexMethod) {
            return SimplexMethod.fileArguments();
        }
        if (taskClass instanceof ArtificialBasic) {
            return ArtificialBasic.fileArguments();
        }
        return new String[]{};
    }
}
