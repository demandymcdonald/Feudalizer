package com;


import com.display.MapDisplay;
import com.display.geography.GeographyLoader;
import javafx.application.Application;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FeudalizerApp extends Application {
    MapDisplay mapDisplay;
    GeographyLoader geographyLoader;
    public static final Logger LOGGER = LoggerFactory.getLogger(GlobalData.class);
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Feudalizer a0.01");
        geographyLoader = new GeographyLoader();
        geographyLoader.init();
        mapDisplay = new MapDisplay(primaryStage);
    }
    public static void main(String[] args) {
        launch(args);
    }
}
