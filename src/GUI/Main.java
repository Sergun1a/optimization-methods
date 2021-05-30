package GUI;

import com.sun.jdi.InvalidTypeException;
import helpers.Fraction;
import javafx.application.Application;
import javafx.stage.Stage;
import sample.ApplicationMenu;
import simplex_method.ArtificialBasic;
import simplex_method.GraphicalMethod;
import simplex_method.SimplexMethod;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        ApplicationMenu menu = new ApplicationMenu(primaryStage, "main.fxml", "Методы оптимизации", 500, 400);
        menu.show();

        /*try {*/

        //System.out.println(Fraction.lowerThen(Fraction.toFraction("123/3"), 0));
        // гаус тест
            /*Fraction[][] a = {
                    {Fraction.toFraction((long) 3), Fraction.toFraction((long) -7), Fraction.toFraction((long) 2), Fraction.toFraction((long) -1), Fraction.toFraction((long) 8)},
                    {Fraction.toFraction((long) 1), Fraction.toFraction((long) 2), Fraction.toFraction((long) 3), Fraction.toFraction((long) 4), Fraction.toFraction((long) 1)},
                    {Fraction.toFraction((long) 0), Fraction.toFraction((long) 3), Fraction.toFraction((long) 0), Fraction.toFraction((long) 6), Fraction.toFraction((long) 1)}
            };
            System.out.println(Arrays.deepToString(MathMiddleware.gaus(a)));*/


        // граф. метод тест
            /*GraphicalMethod graph = new GraphicalMethod(
                    "min",
                    new Fraction[]{Fraction.toFraction((long) 0), Fraction.toFraction((long) -1), Fraction.toFraction((long) -1)},
                    new Fraction[][]{
                            {Fraction.toFraction((long) 2), Fraction.toFraction((long) 3), Fraction.toFraction((long) 36)},
                            {Fraction.toFraction((long) 1), Fraction.toFraction((long) 0), Fraction.toFraction((long) 13)},
                    },
                    new Fraction[]{Fraction.toFraction((long) -1), Fraction.toFraction((long) -1)}
            )*/
        ;
            /*GraphicalMethod graph = new GraphicalMethod(
                    "min",
                    new Fraction[]{Fraction.toFraction((long) 0), Fraction.toFraction((long) -9), Fraction.toFraction((long) -11)},
                    new Fraction[][]{
                            {Fraction.toFraction((long) 4), Fraction.toFraction((long) 3), Fraction.toFraction((long) 10)},
                            {Fraction.toFraction((long) 1), Fraction.toFraction((long) 2), Fraction.toFraction((long) 8)},
                            {Fraction.toFraction((long) 1), Fraction.toFraction((long) 0), Fraction.toFraction((long) 5)},
                    },
                    new Fraction[]{Fraction.toFraction((long) 1), Fraction.toFraction((long) 1)}
            );*/
            /*GraphicalMethod graph = new GraphicalMethod(
                    "min",
                    new Fraction[]{Fraction.toFraction((long) 0), Fraction.toFraction((long) -4), Fraction.toFraction((long) -4)},
                    new Fraction[][]{
                            {Fraction.toFraction((long) 4), Fraction.toFraction((long) 1), Fraction.toFraction((long) 44)},
                            {Fraction.toFraction((long) 1), Fraction.toFraction((long) 0), Fraction.toFraction((long) 22)},
                            {Fraction.toFraction((long) 0), Fraction.toFraction((long) 1), Fraction.toFraction((long) 18)},
                    }
            );*/
            /*GraphicalMethod graph = new GraphicalMethod(
                    "min",
                    new Fraction[]{Fraction.toFraction((long) 0), Fraction.toFraction((long) 1), Fraction.toFraction((long) 2), Fraction.toFraction((long) 1)},
                    new Fraction[][]{
                            {Fraction.toFraction("1/3"), Fraction.toFraction("1/3"), Fraction.toFraction("2/3"), Fraction.toFraction((long) 1)},
                            {Fraction.toFraction((long) 2), Fraction.toFraction((long) 1), Fraction.toFraction((long) 0), Fraction.toFraction((long) 1)},
                            {Fraction.toFraction("1/2"), Fraction.toFraction("3/4"), Fraction.toFraction((long) 0), Fraction.toFraction((long) 1)},
                    }
            );*/
            /*GraphicalMethod graph = new GraphicalMethod(
                    "min",
                    new Fraction[]{Fraction.toFraction((long) 0), Fraction.toFraction((long) -1), Fraction.toFraction((long) 1), Fraction.toFraction((long) -1), Fraction.toFraction((long) 1)},
                    new Fraction[][]{
                            {Fraction.toFraction((long) 1), Fraction.toFraction((long) 0), Fraction.toFraction((long) 2), Fraction.toFraction((long) 1), Fraction.toFraction((long) 8)},
                            {Fraction.toFraction((long) 1), Fraction.toFraction((long) 1), Fraction.toFraction((long) 0), Fraction.toFraction((long) -1), Fraction.toFraction((long) 4)},
                            {Fraction.toFraction((long) -1), Fraction.toFraction((long) 2), Fraction.toFraction((long) 1), Fraction.toFraction((long) 3), Fraction.toFraction((long) 6)},
                    }
            );*/
            /*GraphicalMethod graph = new GraphicalMethod(
                    "min",
                    new Fraction[]{Fraction.toFraction((long) 0), Fraction.toFraction((long) -4), Fraction.toFraction((long) -3)},
                    new Fraction[][]{
                            {Fraction.toFraction((long) 4), Fraction.toFraction((long) 1), Fraction.toFraction((long) 10)},
                            {Fraction.toFraction((long) 2), Fraction.toFraction((long) 3), Fraction.toFraction((long) 8)},
                    }
            );*/
            /*GraphicalMethod graph = new GraphicalMethod(
                    "min",
                    new Fraction[]{Fraction.toFraction((long) 0), Fraction.toFraction((long) -3), Fraction.toFraction((long) -2)},
                    new Fraction[][]{
                            {Fraction.toFraction((long) 1), Fraction.toFraction((long) 2), Fraction.toFraction((long) 7)},
                            {Fraction.toFraction((long) 2), Fraction.toFraction((long) 1), Fraction.toFraction((long) 8)},
                            {Fraction.toFraction((long) 0), Fraction.toFraction((long) 1), Fraction.toFraction((long) 3)},

                    }
            );*/
            /*GraphicalMethod graph = new GraphicalMethod(
                    "min",
                    new Fraction[]{Fraction.toFraction((long) 0), Fraction.toFraction((long) 1), Fraction.toFraction((long) 1)},
                    new Fraction[][]{
                            {Fraction.toFraction((long) 1), Fraction.toFraction((long) 0), Fraction.toFraction((long) 1)},
                            {Fraction.toFraction((long) -1), Fraction.toFraction((long) 0), Fraction.toFraction((long) 0)},
                            {Fraction.toFraction((long) 0), Fraction.toFraction((long) 1), Fraction.toFraction((long) 2)},
                            {Fraction.toFraction((long) 0), Fraction.toFraction((long) -1), Fraction.toFraction((long) 0)},
                            {Fraction.toFraction((long) 1), Fraction.toFraction((long) 1), Fraction.toFraction((long) 3)},
                            {Fraction.toFraction((long) -1), Fraction.toFraction((long) -1), Fraction.toFraction((long) 0)},
                            {Fraction.toFraction((long) 1), Fraction.toFraction((long) -1), Fraction.toFraction((long) 0)},
                            {Fraction.toFraction((long) -1), Fraction.toFraction((long) 1), Fraction.toFraction((long) 1)},
                    }
            );*/
        //graph.solution();

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
            /*ArtificialBasic abasis = new ArtificialBasic(
                    "min",
                    new Fraction[]{Fraction.toFraction((long) 0), Fraction.toFraction((long) -1), Fraction.toFraction((long) 5), Fraction.toFraction((long) 1), Fraction.toFraction((long) -1)},
                    new Fraction[][]{
                            {Fraction.toFraction((long) 1), Fraction.toFraction((long) 3), Fraction.toFraction((long) 3), Fraction.toFraction((long) 1), Fraction.toFraction((long) 3)},
                            {Fraction.toFraction((long) 2), Fraction.toFraction((long) 0), Fraction.toFraction((long) 3), Fraction.toFraction((long) -1), Fraction.toFraction((long) 4)},
                    }

            );*/
        GraphicalMethod abasis = new GraphicalMethod(
                "min",
                new Fraction[]{Fraction.toFraction((long) 0), Fraction.toFraction((long) -2), Fraction.toFraction((long) -1), Fraction.toFraction((long) -3), Fraction.toFraction((long) -1)},
                new Fraction[][]{
                        {Fraction.toFraction((long) 1), Fraction.toFraction((long) 2), Fraction.toFraction((long) 5), Fraction.toFraction((long) -1), Fraction.toFraction((long) 4)},
                        {Fraction.toFraction((long) 1), Fraction.toFraction((long) -1), Fraction.toFraction((long) -1), Fraction.toFraction((long) 2), Fraction.toFraction((long) 1)},
                },
                new Fraction[]{Fraction.toFraction((long) 1), Fraction.toFraction((long) 1), Fraction.toFraction((long) 0), Fraction.toFraction((long) 0)}
        );
        abasis.solution();
        /*} catch (InvalidTypeException ex) {
            System.out.println(ex.getMessage());
        }*/
    }


    public static void main(String[] args) {
        launch(args);
    }
}
