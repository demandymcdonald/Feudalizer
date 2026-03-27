package com;


import com.display.MainWindow;
import com.display.geography.GeographyLoader;
import com.sql.SQLManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Feudalizer extends Application {
    MainWindow mainWindow;
    static GeographyLoader geographyLoader;
    public static final Logger LOGGER = LoggerFactory.getLogger(Feudalizer.class);
    public static Thread MAIN_THREAD;
    @Override
    public void start(Stage primaryStage) throws Exception {
    //try {
        LOGGER.info("Starting application");
        primaryStage.setTitle("Feudalizer a0.3");
        MAIN_THREAD = Thread.currentThread();
        geographyLoader = new GeographyLoader();
        geographyLoader.init();
        TypedSerialized.init();
        mainWindow = new MainWindow(primaryStage);
        primaryStage.setOnCloseRequest(event -> {
            SQLManager.saveAll();
            SQLManager.close();
            Platform.exit();
            System.exit(0);
        });
    //} catch (Exception e) {
    //    LOGGER.error("Error starting application", e);
    //    e.printStackTrace(); // forces it to stdout
    //    throw e;
    //}
    }
    public static void main(String[] args) {
        SQLManager.init();
        SQLManager.loadAll();
        Testcase.init();
        LOGGER.info("Starting application 2");

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
