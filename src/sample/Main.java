package sample;

import com.sun.jdi.InvalidTypeException;
import helpers.Fraction;
import helpers.MathMiddleware;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Arrays;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        ApplicationMenu menu = new ApplicationMenu(primaryStage, "sample.fxml", "Методы оптимизации", 500, 400);
        menu.show();

        try {

            System.out.println(Fraction.toFraction("123/3"));
            /*
            System.out.println(Fraction.toFracture((long) 46));
            System.out.println(Fraction.toFracture(31.782));


            Fraction first = new Fraction(1, 2);
            Fraction second = new Fraction(4, 39);

            System.out.println(Fraction.summFractions(first, second).toString());
            System.out.println(Fraction.subtractionFractions(second, first).toString());
            System.out.println(Fraction.multiplyFractions(first, second).toString());
            System.out.println(Fraction.divisionFractions(first, second).toString());
            */
            // гаус тест
            Fraction[][] a = {
                    {Fraction.toFraction((long) 3), Fraction.toFraction((long) -7), Fraction.toFraction((long) 2), Fraction.toFraction((long) -1), Fraction.toFraction((long) 8)},
                    {Fraction.toFraction((long) 1), Fraction.toFraction((long) 2), Fraction.toFraction((long) 3), Fraction.toFraction((long) 4), Fraction.toFraction((long) 1)},
                    {Fraction.toFraction((long) 0), Fraction.toFraction((long) 3), Fraction.toFraction((long) 0), Fraction.toFraction((long) 6), Fraction.toFraction((long) 1)}
            };
            System.out.println(Arrays.deepToString(MathMiddleware.gaus(a)));


        } catch (InvalidTypeException ex) {
            System.out.println(ex.getMessage());
        }


    }


    public static void main(String[] args) {
        launch(args);
    }
}
