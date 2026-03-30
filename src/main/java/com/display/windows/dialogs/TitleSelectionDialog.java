package com.display.windows.dialogs;

import com.display.geography.GeographyManager;
import com.display.geography.GeometryType;
import com.objects.title.Title;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public abstract class TitleSelectionDialog<T extends Title<T>> extends Dialog<T> {

    protected final ListView<String> selectedCountiesView = new ListView<>();
    protected final List<Pair<GeometryType, String>> selectedCounties = new ArrayList<>();

    private Runnable onEnterSelectionMode;
    private Runnable onExitSelectionMode;

    protected TitleSelectionDialog(String title, String header) {
        setTitle(title);
        setHeaderText(header);
        // Remove buildDialogContent() call from here
    }

    protected void init() {
        DialogPane dialogPane = getDialogPane();
        dialogPane.getStyleClass().add("character-dialog");

        ButtonType createButton = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        dialogPane.getButtonTypes().addAll(createButton, ButtonType.CANCEL);

        dialogPane.setContent(buildDialogContent());

        Button createBtn = (Button) dialogPane.lookupButton(createButton);
        createBtn.setDisable(true);
        setupValidation(createBtn);

        setResultConverter(dialogButton -> {
            if (dialogButton == createButton) {
                exitSelectionMode();
                return createTitle();
            }
            exitSelectionMode();
            return null;
        });
    }

    protected VBox buildSelectionSection() {
        VBox section = new VBox(10);

        Label selectionLabel = new Label("Selected Counties:");
        selectionLabel.getStyleClass().add("section-title");

        selectedCountiesView.setPrefHeight(150);
        selectedCountiesView.setPlaceholder(new Label("No counties selected"));

        Button selectButton = new Button("Select Counties on Map");
        selectButton.setOnAction(e -> enterSelectionMode());

        Button clearButton = new Button("Clear Selection");
        clearButton.setOnAction(e -> clearSelection());

        section.getChildren().addAll(selectionLabel, selectedCountiesView, selectButton, clearButton);
        return section;
    }

    public void setOnEnterSelectionMode(Runnable callback) {
        this.onEnterSelectionMode = callback;
    }

    public void setOnExitSelectionMode(Runnable callback) {
        this.onExitSelectionMode = callback;
    }

    private void enterSelectionMode() {
        if (onEnterSelectionMode != null) {
            onEnterSelectionMode.run();
        }
    }

    private void exitSelectionMode() {
        if (onExitSelectionMode != null) {
            onExitSelectionMode.run();
        }
    }

    public void addSelectedCounty(GeometryType type, String id) {
        Pair<GeometryType, String> county = new Pair<>(type, id);
        if (!selectedCounties.contains(county)) {
            selectedCounties.add(county);

            String name = GeographyManager.getFeature(type, id).getAttribute("NAME").toString();
            selectedCountiesView.getItems().add(name + " (" + id + ")");
        }
    }

    protected void clearSelection() {
        selectedCounties.clear();
        selectedCountiesView.getItems().clear();
    }

    // Abstract methods subclasses must implement
    protected abstract VBox buildDialogContent();
    protected abstract void setupValidation(Button createButton);
    protected abstract T createTitle();
}