package com.display.windows.cards.edit.title;

import com.base.DMRegistry;
import com.objects.character.HumanCharacter;
import com.objects.title.land.AbstractLandDivision;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;

public abstract class AbstractLandDivisionEditCard<T extends AbstractLandDivision<T>> extends TitleEditCard<T> {

    protected final ComboBox<HumanCharacter> holderCombo = new ComboBox<>();

    protected AbstractLandDivisionEditCard(boolean editMode, String entityName) {
        super(editMode, entityName);
    }

    @Override
    protected Node buildExtraFields() {
        VBox extra = new VBox(16);
        extra.setPadding(new Insets(0));

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(12);

        holderCombo.getItems().addAll(DMRegistry.getCharacterManager().getAll());
        holderCombo.setPromptText("Select holder (optional)");
        grid.add(new Label("Holder:"), 0, 0);
        grid.add(holderCombo, 1, 0);

        extra.getChildren().add(grid);

        Node subExtra = buildDivisionExtraFields();
        if (subExtra != null) {
            extra.getChildren().add(subExtra);
        }

        return extra;
    }

    protected Node buildDivisionExtraFields() { return null; }

    @Override
    protected void populateExtraFields(T title) {
        title.getHolder().ifPresent(holderCombo::setValue);
        populateDivisionExtraFields(title);
    }

    protected void populateDivisionExtraFields(T title) {}
}