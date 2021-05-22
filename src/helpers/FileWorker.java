package helpers;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.Validator;

import java.io.*;
import java.util.Arrays;
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

    private static File showSavingDialog(String title, String extensionLong, String extensionShort) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter(extensionLong + " (*." + extensionShort + ")", "*." + extensionShort);
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(new Stage());
        return file;
    }

    public static void saveFile(String content) {
        File file = showSavingDialog("Сохранить", "optimization method task file", "om");
        if (file != null) {
            saveTextToFile(content, file);
        }
    }


    public static HashMap<String, String> openFile() throws IOException {
        HashMap<String, String> fileData = new HashMap<String, String>();
        File file = showOpeningDialog("Открыть", "optimization method task file", "om");
        if (file != null) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line = reader.readLine();
                String[] splittedLine = line.split(" => ", 2);
                while (line != null) {
                    splittedLine = line.split(" => ", 2);
                    if (splittedLine.length == 2 && Arrays.asList(Holder.fileArgumentsForTask()).contains(splittedLine[0].trim())) {
                        fileData.put(splittedLine[0].trim(), splittedLine[1].toString().trim());
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

    public static String attributeToString(String field, String value) {
        if (Validator.validate(FileWorker.getAttributesValidators().
                get(field), value)) {
            return field.trim() + " => " + value.trim() + "\n";
        }
        return "";
    }
}
