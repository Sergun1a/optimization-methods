package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;

public class ApplicationMenu {
    private Stage stage;
    private Scene currentScene;
    private Menu currentMenu;
    private BorderPane currentRoot;
    private Parent currentSceneBuilderRoot;

    /**
     * Создают новое окно из fxml файла
     *
     * @param filename - имя fxml файла из папки /GUI/fxml/
     * @param title    - заголовок окна
     * @param width    - ширина окна
     * @param height   - высота окна
     * @return Созданную сцену
     * @throws IOException
     */
    public static Stage showNewStage(String filename, String title, Integer width, Integer height) throws IOException {
        Stage newStage = new Stage();
        Parent sceneFromFile = FXMLLoader.load(ApplicationMenu.class.getResource("../GUI/fxml/" + filename));
        newStage.setTitle(title);
        Scene newScene = new Scene(sceneFromFile, width, height);
        newStage.setScene(newScene);
        newStage.show();
        return newStage;
    }

    /**
     * Применяю переданную переданную сцену
     *
     * @param myScene  - сцена
     * @param newStage
     */
    public static void applySceneToStage(Scene myScene, Stage newStage) {
        newStage.setScene(myScene);
        newStage.show();
    }

    public ApplicationMenu(Stage primaryStage, String filename, String title, Integer width, Integer height) throws IOException {
        stage = primaryStage;
        currentSceneBuilderRoot = FXMLLoader.load(getClass().getResource("../GUI/fxml/" + filename));
        stage.setTitle(title);
        currentScene = new Scene(currentSceneBuilderRoot, width, height);
        stage.setScene(currentScene);
    }

    ApplicationMenu(Stage primaryStage, String title, Integer width, Integer height) {
        stage = primaryStage;
        currentRoot = new BorderPane();
        stage.setTitle(title);
        currentScene = new Scene((BorderPane) currentRoot, width, height);
        stage.setScene(currentScene);
    }

    public static void showAlert(String type, String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        switch (type.toLowerCase(Locale.ROOT)) {
            case "info":
                alert = new Alert(Alert.AlertType.INFORMATION);
                break;
            case "warning":
                alert = new Alert(Alert.AlertType.WARNING);
                break;
            case "error":
                alert = new Alert(Alert.AlertType.ERROR);
                break;
        }
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public void show() {
        stage.show();
    }

    public Stage getStage() {
        return stage;
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    public BorderPane getCurrentRoot() {
        return currentRoot;
    }

    public Parent getCurrentSceneBuilderRoot() {
        return currentSceneBuilderRoot;
    }

    public Menu getCurrentMenu() {
        return currentMenu;
    }

    public Menu createMenu(String name) {
        return new Menu(name);
    }


    public MenuItem createMenuItem(Menu menu, String itemName) {
        MenuItem menuItem = new MenuItem(itemName);
        menu.getItems().add(menuItem);
        currentMenu = menu;
        return menuItem;
    }

    public MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();
        currentRoot.setTop(menuBar);
        return menuBar;
    }

    public MenuBar addMenuToMenuBar(MenuBar menuBar, Menu menu) {
        menuBar.getMenus().add(menu);
        return menuBar;
    }
}
