package com.display.windows;

import com.simulation.people.BookCharacter;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.function.Consumer;


public class UIComponents {

    public static Node buildStatTile(String label, String value) {
        VBox tile = new VBox(6);
        tile.setPadding(new Insets(14));
        tile.getStyleClass().add("stat-tile");

        Label labelNode = new Label(label.toUpperCase());
        labelNode.getStyleClass().add("stat-label");

        Label valueNode = new Label(value);
        valueNode.getStyleClass().add("stat-value");

        tile.getChildren().addAll(labelNode, valueNode);
        return tile;
    }
    public static Node buildCharacterChip(Consumer<BookCharacter> onCharacterSelected, BookCharacter bookCharacter) {
        HBox chip = new HBox(10);
        chip.getStyleClass().add("character-chip");
        chip.setAlignment(Pos.CENTER_LEFT);
        chip.setPadding(new Insets(8, 14, 8, 14));

        String initials = String.valueOf(bookCharacter.getGivenName().charAt(0))
                + String.valueOf(bookCharacter.getSurname().charAt(0));

        Label portrait = new Label(initials);
        portrait.setPrefSize(32, 32);
        portrait.getStyleClass().add("chip-portrait");
        Label name = new Label(bookCharacter.getGivenName());
        name.getStyleClass().add("chip-name");
        Label house = new Label(bookCharacter.getSurname());
        house.getStyleClass().add("chip-house");
        VBox info = new VBox(2, name, house);

        chip.getChildren().addAll(portrait, info);
        chip.setOnMouseClicked(e -> {
            if (onCharacterSelected != null) {
                onCharacterSelected.accept(bookCharacter);
            }
        });
        return chip;
    }
    public static Node buildPersonTile(BookCharacter character, EventHandler<MouseEvent> onClick) {
        VBox tile = new VBox(8);
        tile.setPadding(new Insets(14));
        tile.setAlignment(Pos.CENTER);
        tile.getStyleClass().add("person-tile");

        String initials = String.valueOf(character.getGivenName().charAt(0))
                + String.valueOf(character.getSurname().charAt(0));

        Label portrait = buildPortraitLabel(character, 48, "portrait-medium");

        Label name = new Label(character.getGivenName());
        name.getStyleClass().add("person-name");

        Label house = new Label(character.getSurname());
        house.getStyleClass().add("text-secondary");

        tile.getChildren().addAll(portrait, name, house);

        if (onClick != null) {
            tile.setOnMouseClicked(onClick);
        }

        return tile;
    }

    public static Node buildPortrait(BookCharacter character, int size) {
        String initials = String.valueOf(character.getGivenName().charAt(0))
                + String.valueOf(character.getSurname().charAt(0));

        Label portrait = new Label(initials);
        portrait.getStyleClass().add("portrait");
        portrait.setPrefSize(size, size);
        return portrait;
    }

    public static Node buildPersonRow(BookCharacter character, String sublabel, EventHandler<MouseEvent> onClick) {
        HBox row = new HBox(12);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(12, 16, 12, 16));
        row.getStyleClass().add("holder-row");

        Node portrait = buildPortrait(character, 40);

        Label name = new Label(character.getGivenName());
        name.getStyleClass().add("holder-name");

        Label sub = new Label(sublabel);
        sub.getStyleClass().add("text-secondary");

        VBox info = new VBox(4, name, sub);
        row.getChildren().addAll(portrait, info);

        if (onClick != null) {
            row.setOnMouseClicked(onClick);
        }

        return row;
    }
    public static Label buildPortraitLabel(BookCharacter character, int size, String styleClass) {
        String initials = String.valueOf(character.getGivenName().charAt(0))
                + String.valueOf(character.getSurname().charAt(0));

        Label portrait = new Label(initials);
        portrait.getStyleClass().add(styleClass);
        portrait.setPrefSize(size, size);
        portrait.setMinSize(size, size);
        portrait.setMaxSize(size, size);
        portrait.setAlignment(Pos.CENTER);
        return portrait;
    }
    // etc. as we find more shared components
}
