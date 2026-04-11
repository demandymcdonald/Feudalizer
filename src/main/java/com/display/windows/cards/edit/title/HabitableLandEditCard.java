package com.display.windows.cards.edit.title;


import com.objects.title.land.habitable.HabitableLand;
import com.objects.title.land.resources.Resource;
import com.objects.title.land.resources.ResourceType;
import com.objects.title.land.resources.Resources;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.HashSet;
import java.util.Set;

public abstract class HabitableLandEditCard<R extends HabitableLand<R>> extends AbstractLandDivisionEditCard<R> {

    protected final TextField populationField = new TextField();
    private final VBox resourceRows = new VBox(8);

    protected HabitableLandEditCard(boolean editMode, String entityName) {
        super(editMode, entityName);
    }

    @Override
    protected Node buildDivisionExtraFields() {
        VBox extra = new VBox(16);
        extra.setPadding(new Insets(0));

        // Population
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(12);

        populationField.setPromptText("0");
        grid.add(new Label("Population:"), 0, 0);
        grid.add(populationField, 1, 0);
        extra.getChildren().add(grid);

        // Resources section
        Label resourcesTitle = new Label("Resources");
        resourcesTitle.getStyleClass().add("section-title");

        Button addResourceBtn = new Button("+ Add Resource");
        addResourceBtn.setOnAction(e -> addResourceRow(null));

        extra.getChildren().addAll(resourcesTitle, resourceRows, addResourceBtn);

        Node subExtra = buildHabitableExtraFields();
        if (subExtra != null) {
            extra.getChildren().add(subExtra);
        }

        return extra;
    }

    private void addResourceRow(Resource existing) {
        HBox row = new HBox(8);
        row.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        ComboBox<ResourceType> typeCombo = new ComboBox<>();
        typeCombo.getItems().addAll(Resources.getAll());
        typeCombo.setPromptText("Select type");

        Spinner<Integer> abundanceSpinner = new Spinner<>(0, 10, 5);
        abundanceSpinner.setEditable(true);
        abundanceSpinner.setPrefWidth(80);

        Button removeBtn = new Button("Remove");
        removeBtn.setOnAction(e -> resourceRows.getChildren().remove(row));

        if (existing != null) {
            typeCombo.setValue(existing.type());
            abundanceSpinner.getValueFactory().setValue(existing.abundance());
        }

        row.getChildren().addAll(typeCombo, abundanceSpinner, removeBtn);
        resourceRows.getChildren().add(row);
    }

    protected Set<Resource> buildResources() {
        Set<Resource> result = new HashSet<>();
        for (Node node : resourceRows.getChildren()) {
            if (node instanceof HBox row) {
                ComboBox<ResourceType> typeCombo = (ComboBox<ResourceType>) row.getChildren().get(0);
                Spinner<Integer> spinner = (Spinner<Integer>) row.getChildren().get(1);

                if (typeCombo.getValue() != null) {
                    result.add(new Resource(typeCombo.getValue(), spinner.getValue()));
                }
            }
        }
        return result;
    }

    protected Node buildHabitableExtraFields() { return null; }

    @Override
    protected void populateDivisionExtraFields(R title) {
        populationField.setText(String.valueOf(title.getPopulation()));

        // Only populate direct resources, not aggregated
        resourceRows.getChildren().clear();
        for (Resource r : title.getDirectResources()) {
            addResourceRow(r);
        }

        populateHabitableExtraFields(title);
    }

    protected void populateHabitableExtraFields(R title) {}
}