package com.display;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MapDisplay {
    private StackPane mapDisplay = new StackPane();
    protected static final double screenWidth = 1920;
    protected static final double screenHeight = 1080;
    private Scene mapDisplayScene = new Scene(mapDisplay, screenWidth, screenHeight);
    public MapDisplay(Stage stage) {
        stage.setScene(mapDisplayScene);
        stage.show();
    }

}
