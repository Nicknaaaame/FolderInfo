package sample.view;

import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import sample.Main;
import sample.presenter.Presenter;

public class Activity {
    private TextArea taMain;

    private Stage primaryStage;

    private Presenter presenter;

    public Activity() {
        presenter = new Presenter();
        taMain = (TextArea) Main.getLoader().getNamespace().get("taMain");
        MenuItem miOpen = (MenuItem) Main.getLoader().getNamespace().get("miOpen");

        primaryStage = Main.getPrimaryStage();
        primaryStage.heightProperty().addListener(e->{
            taMain.setPrefHeight(primaryStage.getHeight());
        });
        primaryStage.widthProperty().addListener(e->{
            taMain.setPrefWidth(primaryStage.getWidth());
        });
        miOpen.setOnAction(e-> presenter.onDirChoose(e));
    }
}
