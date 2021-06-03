package GUI;

import com.sun.jdi.InvalidTypeException;
import helpers.Holder;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import simplex_method.ArtificialBasic;
import simplex_method.SimplexMethod;

public class SolutionController {
    @FXML
    private GridPane gridPane;

    @FXML
    private void initialize() throws InvalidTypeException {
        if (Holder.current_task.equals("Симплекс метод")) {
            SimplexMethod task = (SimplexMethod) (Holder.taskClass);
            gridPane.add(new Text(task.printSolution()), 0, 1);
        }
        if (Holder.current_task.equals("Искусственный базис")) {
            ArtificialBasic task = (ArtificialBasic) (Holder.taskClass);
            gridPane.add(new Text((task.printSolution())), 0, 1);
        }
    }
}
