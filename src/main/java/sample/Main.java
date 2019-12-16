package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URI;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Objects;

public class Main extends Application {
    private static Stage staticPrimaryStage;
    private static Parent staticRoot;

    public static Stage getPrimaryStage(){
        return staticPrimaryStage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        staticPrimaryStage = primaryStage;
        URL url = Paths.get("./src/main/java/sample/view/view.fxml").toUri().toURL();
        Parent root = FXMLLoader.load(url);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 1200, 700));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
