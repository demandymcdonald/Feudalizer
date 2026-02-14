package com;


import com.display.MapDisplay;
import javafx.application.Application;
import javafx.stage.Stage;

public class FeudalizerApp extends Application {
    MapDisplay mapDisplay;
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Feudalizer a0.01");
        mapDisplay = new MapDisplay(primaryStage);
    }
    public static void main(String[] args) {
        launch(args);
    }
}
