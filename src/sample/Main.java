package sample;

import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        ApplicationMenu menu = new ApplicationMenu(primaryStage, "sample.fxml", "Hello world", 500, 400);
        menu.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
