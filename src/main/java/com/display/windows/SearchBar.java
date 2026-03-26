package com.display.windows;

import com.base.DMRegistry;
import com.simulation.people.BookCharacter;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.function.Consumer;

public class SearchBar {
    private static final int MIN_CHARS = 3;
    private static final int MAX_RESULTS = 20;
    private static final int DEBOUNCE_MS = 300;
    private Consumer<BookCharacter> onCharacterSelected; // ADD THIS

    // ADD THIS METHOD
    public void setOnCharacterSelected(Consumer<BookCharacter> callback) {
        this.onCharacterSelected = callback;
    }
    private final HBox root = new HBox(16);

    public SearchBar() {
        root.setAlignment(Pos.CENTER_LEFT);
        root.setPadding(new Insets(12, 24, 12, 24));
        root.setPrefHeight(80);

        HBox chipsContainer = new HBox(12);
        chipsContainer.setAlignment(Pos.CENTER_LEFT);

        ScrollPane scrollPane = new ScrollPane(chipsContainer);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setFitToHeight(true);
        HBox.setHgrow(scrollPane, Priority.ALWAYS);

        TextField searchField = new TextField();
        searchField.setPromptText("Search characters by name or house...");
        searchField.setPrefWidth(280);

        // Debounce timer - resets on every keystroke
        PauseTransition debounce = new PauseTransition(Duration.millis(DEBOUNCE_MS));
        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            debounce.setOnFinished(e -> populateChips(chipsContainer, newVal));
            debounce.playFromStart();
        });
        root.getStyleClass().add("search-hotbar");

        // search field
        searchField.getStyleClass().add("search-field");

        // scroll pane
        scrollPane.getStyleClass().add("chips-scroll");
        root.getChildren().addAll(searchField, scrollPane);
    }

    private void populateChips(HBox container, String filter) {
        container.getChildren().clear();

        // Option 3: don't search until MIN_CHARS typed
        if (filter.length() < MIN_CHARS) return;

        // Option 3 + 4: filter AND cap results
        DMRegistry.getCharacterManager().getWhere(c ->
                        c.isAlive() &&
                                (c.getGivenName().toLowerCase().contains(filter.toLowerCase())
                                        || c.getSurname().toLowerCase().contains(filter.toLowerCase()))
                )
                .stream()
                .limit(MAX_RESULTS)
                .forEach(c -> container.getChildren().add(UIComponents.buildCharacterChip(onCharacterSelected,c)));
    }



    public Node getNode() {
        return root;
    }
}
