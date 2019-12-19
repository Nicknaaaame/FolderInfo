package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import sample.view.Activity;

import java.net.URI;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Main extends Application {
    private static Stage staticPrimaryStage;
    private static FXMLLoader loader;

    public static Stage getPrimaryStage(){
        return staticPrimaryStage;
    }

    public static FXMLLoader getLoader(){
        return loader;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        staticPrimaryStage = primaryStage;
        URL url = Paths.get("./src/main/java/sample/view/view.fxml").toUri().toURL();
        loader = new FXMLLoader(url);
        Parent root = loader.load();
        //lox
        new Activity();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 1200, 700));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
