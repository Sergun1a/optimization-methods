package sample;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileWorker {

    /**
     * Возвращаю массив полей и их аттрибутов.
     *
     * @return Map<String, String>
     */
    public static Map<String, String> getAttributesValidators() {
        HashMap<String, String> attr = new HashMap<String, String>();

        attr.put("testField", "String");

        return attr;
    }


    private static void saveTextToFile(String content, File file) {
        try {
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println(content);
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static File showOpeningDialog(String title, String extensionLong, String extensionShort) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter(extensionLong + " (*." + extensionShort + ")", "*." + extensionShort);
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(new Stage());
        return file;
    }

    public static void saveFile(String content) {
        File file = showOpeningDialog("Сохранить", "Custom text file", "txt");
        if (file != null) {
            saveTextToFile(content, file);
        }
    }


    public static HashMap<String, Object> openFile() throws IOException {
        HashMap<String, Object> fileData = new HashMap<String, Object>();
        File file = showOpeningDialog("Открыть", "Custom text file", "txt");
        if (file != null) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line = reader.readLine();
                String[] splittedLine = line.split(" => ", 2);
                while (line != null) {
                    splittedLine = line.split(" => ", 2);
                    if (splittedLine.length == 2) {
                        if (Validator.validate(getAttributesValidators().
                                get(splittedLine[0].trim()), splittedLine[1])) {
                            fileData.put(splittedLine[0].trim(), splittedLine[1]);
                        }
                    }
                    line = reader.readLine();
                }
                reader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return fileData;
    }
}
