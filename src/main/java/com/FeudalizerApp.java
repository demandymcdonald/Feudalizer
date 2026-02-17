package com;


import com.display.MapDisplay;
import com.display.geography.GeographyLoader;
import com.sql.SQLManager;
import javafx.application.Application;
import javafx.application.Platform;
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
        SQLManager.init();
        SQLManager.loadAll();
        geographyLoader = new GeographyLoader();
        geographyLoader.init();
        mapDisplay = new MapDisplay(primaryStage);
        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
    }
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() {
        SQLManager.saveAll();
        SQLManager.close();
        Platform.exit();
        System.exit(0);
    }

}
