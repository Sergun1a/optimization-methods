package helpers;

import javafx.stage.Stage;

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
}
