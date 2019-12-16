package sample.view;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import sample.Main;
import sample.presenter.Presenter;

import java.io.File;

public class Activity {
    @FXML
    private TextArea taMain;
    @FXML
    private MenuItem miOpen;
    @FXML
    private StackPane spMain;

    private Stage primaryStage;

    private Presenter presenter;

    public Activity() {
    }

    public void initialize(){
        presenter = new Presenter(taMain, spMain);

        primaryStage = Main.getPrimaryStage();
        primaryStage.heightProperty().addListener(e->{
            taMain.setPrefHeight(primaryStage.getHeight());
        });
        primaryStage.widthProperty().addListener(e->{
            taMain.setPrefWidth(primaryStage.getWidth());
        });
        miOpen.setOnAction(e-> presenter.onDirChoose(e));
    }

    public StackPane getSpMain() {
        return spMain;
    }
}
