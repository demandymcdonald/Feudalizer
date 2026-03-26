package com.display;

import com.display.geography.GeometryType;
import com.display.utils.FontManager;
import com.display.windows.InfoPanel;
import com.display.windows.MapDisplay;
import com.display.windows.SearchBar;
import com.display.windows.dialogs.ProvinceCreationDialog;
import com.display.windows.menu.AppMenuBar;
import com.display.windows.menu.CharacterCreationDialog;
import com.simulation.land.Province;
import com.simulation.people.BookCharacter;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.Optional;

import static com.GlobalVars.SCREEN_HEIGHT;
import static com.GlobalVars.SCREEN_WIDTH;

public class MainWindow {
    private final BorderPane root = new BorderPane();
    private final SearchBar searchBar = new SearchBar();
    private final MapDisplay mapDisplay = new MapDisplay();
    private final InfoPanel infoPanel = new InfoPanel();
    private final AppMenuBar menuBar = new AppMenuBar();
    public MainWindow(Stage stage) {
        menuBar.setOnCreateTitle(this::openCreateTitleForm);
        menuBar.setOnCreateCharacter(this::openCreateCharacterForm);
        menuBar.setOnCreateHouse(this::openCreateHouseForm);
        VBox topContainer = new VBox();
        topContainer.getChildren().addAll(menuBar.getNode(), searchBar.getNode());

        // Build the three regions
        root.setTop(topContainer);
        root.setCenter(mapDisplay.getMapPane());
        root.setRight(infoPanel.getNode());
        mapDisplay.setOnCountySelected(infoPanel::openTitle);
        searchBar.setOnCharacterSelected(infoPanel::showCharacter);
        Scene scene = new Scene(root);
        scene.getStylesheets().addAll(
                getClass().getResource("/styles/shared.css").toExternalForm(),
                getClass().getResource("/styles/cards.css").toExternalForm(),
                getClass().getResource("/styles/hotbar.css").toExternalForm()
        );
        FontManager.CINZEL_REGULAR.getName();
        stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
        stage.setScene(scene);
        stage.setMaximized(true);
        mapDisplay.requestRender();
        stage.show();
    }
    private void openCreateTitleForm(String titleType) {
        if (type.equals("Province")) {
            ProvinceCreationPanel panel = new ProvinceCreationPanel();
            panel.setOnConfirm(province -> infoPanel.hideCreationPanel());
            panel.setOnCancel(() -> infoPanel.hideCreationPanel());
            infoPanel.showCreationPanel(panel.getNode());
            mapDisplay.enterMultiSelectMode(panel::addSelectedCounty);
        } else {
            System.out.println("Opening form to create: " + titleType);
        }
    }

    private void openCreateCharacterForm() {
        System.out.println("Opening character creation form");
        CharacterCreationDialog dialog = new CharacterCreationDialog();
        Optional<BookCharacter> result = dialog.showAndWait();

        result.ifPresent(character -> {
            System.out.println("Created character: " + character.getGivenName());
            // Character is already registered with DMRegistry in its constructor
            // Optionally refresh the search bar to show the new character
        });
    }

    private void openCreateHouseForm() {
        System.out.println("Opening house creation form");
        // TODO: Implement
    }

}
