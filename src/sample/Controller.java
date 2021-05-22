package sample;


import helpers.FileWorker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.HashMap;

public class Controller {
    @FXML
    private MenuItem fileOpen;

    @FXML
    private MenuItem fileSave;

    @FXML
    private TextField testField;

    public void initialize() {
        /*fileOpen.setOnAction((ActionEvent event) -> {
            try {
                HashMap<String, Object> data = FileWorker.openFile();
                *//*TODO Дальше нужно присвоить полям новые значения, которые получили из файла*//*
                if (data.get("testField") != null) {
                    testField.setText(data.get("testField").toString());
                    ApplicationMenu.showAlert("Info", "Файл успешно открыт",null, "Файл успешно открыт");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        fileSave.setOnAction((ActionEvent event) -> {
            *//*TODO Здесь нужно получить значения полей которые будут записаны в файл и передать в функцию*//*
            String content = "";
            content += attributeToString("testField", testField.getText());
            FileWorker.saveFile(content);
        });*/
    }


    private String attributeToString(String field, String value) {
        if (Validator.validate(FileWorker.getAttributesValidators().
                get(field), value)) {
            return field.trim() + " => " + value.trim() + "\n";
        }
        return "";
    }
}
