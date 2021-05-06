package sample;

import com.sun.jdi.InvalidTypeException;
import helpers.Fraction;
import helpers.MathMiddleware;
import javafx.application.Application;
import javafx.stage.Stage;
import simplex_method.ArtificialBasic;
import simplex_method.SimplexMethod;

import java.util.Arrays;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        //ApplicationMenu menu = new ApplicationMenu(primaryStage, "sample.fxml", "Методы оптимизации", 500, 400);
        //menu.show();

        try {

            //System.out.println(Fraction.lowerThen(Fraction.toFraction("123/3"), 0));
            // гаус тест
            /*Fraction[][] a = {
                    {Fraction.toFraction((long) 3), Fraction.toFraction((long) -7), Fraction.toFraction((long) 2), Fraction.toFraction((long) -1), Fraction.toFraction((long) 8)},
                    {Fraction.toFraction((long) 1), Fraction.toFraction((long) 2), Fraction.toFraction((long) 3), Fraction.toFraction((long) 4), Fraction.toFraction((long) 1)},
                    {Fraction.toFraction((long) 0), Fraction.toFraction((long) 3), Fraction.toFraction((long) 0), Fraction.toFraction((long) 6), Fraction.toFraction((long) 1)}
            };
            System.out.println(Arrays.deepToString(MathMiddleware.gaus(a)));*/

            // симплекс тест
            /*SimplexMethod simplex = new SimplexMethod(
                    "min",
                    new Fraction[]{Fraction.toFraction((long) 0), Fraction.toFraction((long) -1), Fraction.toFraction((long) 2), Fraction.toFraction((long) -1)},
                    new Fraction[][]{
                            {Fraction.toFraction((long) 1), Fraction.toFraction((long) 4), Fraction.toFraction((long) 1), Fraction.toFraction((long) 5)},
                            {Fraction.toFraction((long) 1), Fraction.toFraction((long) -2), Fraction.toFraction((long) -1), Fraction.toFraction((long) -1)},
                    },
                    new Fraction[]{Fraction.toFraction((long) 1), Fraction.toFraction((long) 1), Fraction.toFraction((long) 0)}
            );*/
            /*SimplexMethod simplex = new SimplexMethod(
                    "min",
                    new Fraction[]{Fraction.toFraction((long) 0), Fraction.toFraction((long) -6), Fraction.toFraction((long) -1), Fraction.toFraction((long) -4), Fraction.toFraction((long) -5)},
                    new Fraction[][]{
                            {Fraction.toFraction((long) 3), Fraction.toFraction((long) 1), Fraction.toFraction((long) -1), Fraction.toFraction((long) 1), Fraction.toFraction((long) 4)},
                            {Fraction.toFraction((long) 5), Fraction.toFraction((long) 1), Fraction.toFraction((long) 1), Fraction.toFraction((long) -1), Fraction.toFraction((long) 4)},
                    },
                    new Fraction[]{Fraction.toFraction((long) 1), Fraction.toFraction((long) 0), Fraction.toFraction((long) 0), Fraction.toFraction((long) 1)}
            );*/
            /*SimplexMethod simplex = new SimplexMethod(
                    "min",
                    new Fraction[]{Fraction.toFraction((long) 0), Fraction.toFraction((long) -1), Fraction.toFraction((long) -2), Fraction.toFraction((long) -3), Fraction.toFraction((long) 1)},
                    new Fraction[][]{
                            {Fraction.toFraction((long) 1), Fraction.toFraction((long) -3), Fraction.toFraction((long) -1), Fraction.toFraction((long) -2), Fraction.toFraction((long) -4)},
                            {Fraction.toFraction((long) 1), Fraction.toFraction((long) -1), Fraction.toFraction((long) 1), Fraction.toFraction((long) 0), Fraction.toFraction((long) 0)},
                    },
                    new Fraction[]{Fraction.toFraction((long) 0), Fraction.toFraction((long) 1), Fraction.toFraction((long) 1), Fraction.toFraction((long) 0)}
            );*/
            /*SimplexMethod simplex = new SimplexMethod(
                    "min",
                    new Fraction[]{Fraction.toFraction((long) 0), Fraction.toFraction((long) -1), Fraction.toFraction((long) 3), Fraction.toFraction((long) 5), Fraction.toFraction((long) 1)},
                    new Fraction[][]{
                            {Fraction.toFraction((long) 1), Fraction.toFraction((long) 4), Fraction.toFraction((long) 4), Fraction.toFraction((long) 1), Fraction.toFraction((long) 5)},
                            {Fraction.toFraction((long) 1), Fraction.toFraction((long) 7), Fraction.toFraction((long) 8), Fraction.toFraction((long) 2), Fraction.toFraction((long) 9)},
                    },
                    new Fraction[]{Fraction.toFraction((long) 1), Fraction.toFraction((long) 0), Fraction.toFraction((long) 1), Fraction.toFraction((long) 0)}
            );*/
            //simplex.solution();

            // искусственный базис тест
            /*ArtificialBasic abasis = new ArtificialBasic(
                    "min",
                    new Fraction[]{Fraction.toFraction((long) 0), Fraction.toFraction((long) 3), Fraction.toFraction((long) 8), Fraction.toFraction((long) 5), Fraction.toFraction((long) 4), Fraction.toFraction((long) 0)},
                    new Fraction[][]{
                            {Fraction.toFraction((long) 2), Fraction.toFraction((long) -1), Fraction.toFraction((long) 3), Fraction.toFraction((long) -5), Fraction.toFraction((long) -6), Fraction.toFraction((long) 8)},
                            {Fraction.toFraction((long) -1), Fraction.toFraction((long) 1), Fraction.toFraction((long) -6), Fraction.toFraction((long) 4), Fraction.toFraction((long) 0), Fraction.toFraction((long) -2)},
                            {Fraction.toFraction((long) 0), Fraction.toFraction((long) 3), Fraction.toFraction((long) 2), Fraction.toFraction((long) -3), Fraction.toFraction((long) -5), Fraction.toFraction((long) 0)}
                    }

            );*/
            /*ArtificialBasic abasis = new ArtificialBasic(
                    "min",
                    new Fraction[]{Fraction.toFraction((long) 0), Fraction.toFraction((long) -1), Fraction.toFraction((long) 5), Fraction.toFraction((long) 1), Fraction.toFraction((long) -4)},
                    new Fraction[][]{
                            {Fraction.toFraction((long) 1), Fraction.toFraction((long) 3), Fraction.toFraction((long) 3), Fraction.toFraction((long) 1), Fraction.toFraction((long) 3)},
                            {Fraction.toFraction((long) 2), Fraction.toFraction((long) 0), Fraction.toFraction((long) 3), Fraction.toFraction((long) -1), Fraction.toFraction((long) 4)},
                    }

            );*/
            /*ArtificialBasic abasis = new ArtificialBasic(
                    "min",
                    new Fraction[]{Fraction.toFraction((long) 0), Fraction.toFraction((long) -1), Fraction.toFraction((long) -4), Fraction.toFraction((long) -1)},
                    new Fraction[][]{
                            {Fraction.toFraction((long) 1), Fraction.toFraction((long) -1), Fraction.toFraction((long) 1), Fraction.toFraction((long) 3)},
                            {Fraction.toFraction((long) 2), Fraction.toFraction((long) -5), Fraction.toFraction((long) -1), Fraction.toFraction((long) 0)},
                    }

            );*/
            ArtificialBasic abasis = new ArtificialBasic(
                    "min",
                    new Fraction[]{Fraction.toFraction((long) 0), Fraction.toFraction((long) -1), Fraction.toFraction((long) 5), Fraction.toFraction((long) 1), Fraction.toFraction((long) -1)},
                    new Fraction[][]{
                            {Fraction.toFraction((long) 1), Fraction.toFraction((long) 3), Fraction.toFraction((long) 3), Fraction.toFraction((long) 1), Fraction.toFraction((long) 3)},
                            {Fraction.toFraction((long) 2), Fraction.toFraction((long) 0), Fraction.toFraction((long) 3), Fraction.toFraction((long) -1), Fraction.toFraction((long) 4)},
                    }

            );
            abasis.solution();
        } catch (InvalidTypeException ex) {
            System.out.println(ex.getMessage());
        }


    }


    public static void main(String[] args) {
        launch(args);
    }
}
