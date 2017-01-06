package ch.fhnw.cuie;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by cansik on 06.01.17.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent rootPanel = new DemoBox();

        Scene scene = new Scene(rootPanel);

        primaryStage.setTitle("Relation Number Field Demo");
        primaryStage.setScene(scene);
        primaryStage.setWidth(400);
        primaryStage.setHeight(250);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
