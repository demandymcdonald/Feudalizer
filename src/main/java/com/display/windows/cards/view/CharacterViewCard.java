package com.display.windows.cards.view;

import com.display.windows.UIComponents;
import com.objects.character.sentient.HumanCharacter;
import com.objects.family.FamilyManager;
import com.objects.title.Title;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.display.windows.UIComponents.buildPersonTile;

public class CharacterViewCard extends BaseViewCard<HumanCharacter> {
    // Label fields
    private Label portrait;
    private final Label nameLabel = new Label();
    private final Label houseLabel = new Label();
    private final Label genderLabel = new Label();
    private final Label bornLabel = new Label();
    public CharacterViewCard() {
        super();
        init();
    }

    public void populate(HumanCharacter character) {
        updateHeader(character);
        scrollPane.setContent(buildBody(character));
    }

    private void updateHeader(HumanCharacter character) {
        nameLabel.setText(character.getGivenName());
        String initials = String.valueOf(character.getGivenName().charAt(0))
                + String.valueOf(character.getSurname().charAt(0));
        portrait.setText(initials);
        houseLabel.setText(character.getSurname());
    }
    @Override
    protected Node buildHeader() {
        VBox header = new VBox(8);
        header.setPadding(new Insets(24));
        header.getStyleClass().add("character-header");
        portrait = new Label(/* initials, set on populate */);
        portrait.getStyleClass().add("portrait-large");
        portrait.setPrefSize(120, 120);
        portrait.setMinSize(120, 120);
        portrait.setMaxSize(120, 120);
        portrait.setAlignment(Pos.CENTER);
        nameLabel.getStyleClass().add("card-title");
        houseLabel.getStyleClass().add("card-subtitle");

        header.getChildren().addAll(portrait,nameLabel, houseLabel);
        return header;
    }

    @Override
    protected Node buildBody(HumanCharacter character) {
        VBox body = new VBox(24);
        body.setPadding(new Insets(24));

        body.getChildren().addAll(
                buildStatsGrid(character),
                buildTitlesSection(character),
                buildSpouseSection(character),
                buildChildrenSection(character),
                buildParentsSection(character),      // ADD THIS
                buildSiblingsSection(character),
                buildLiegeSection(character),
                buildVassalsSection(character));
            return body;
    }
    @Override
    protected Node buildFooter() {
        HBox footer = new HBox(12);
        footer.setPadding(new Insets(20, 24, 20, 24));
        footer.getStyleClass().add("card-footer");
        Button editBtn = new Button("Edit");
        Button viewHoldingsBtn = new Button("View Holdings");
        viewHoldingsBtn.getStyleClass().add("button-primary");

        HBox.setHgrow(editBtn, Priority.ALWAYS);
        HBox.setHgrow(viewHoldingsBtn, Priority.ALWAYS);
        editBtn.setMaxWidth(Double.MAX_VALUE);
        viewHoldingsBtn.setMaxWidth(Double.MAX_VALUE);

        footer.getChildren().addAll(editBtn, viewHoldingsBtn);
        return footer;
    }


    private Node buildLiegeSection(HumanCharacter character) {
        VBox section = new VBox(8);

        Label sectionTitle = new Label("Liege");
        sectionTitle.getStyleClass().add("section-title");

        Optional<HumanCharacter> liege = character.getLiege();

        if (liege.isEmpty()) {
            return emptySection(section,sectionTitle);
        }
        Node row = UIComponents.buildPersonRow(
                liege.get(),
                liege.get().getSurname(),
                e -> fireOnCharacterSelected(liege.get())
        );
        row.getStyleClass().add("liege-row");
        section.getChildren().addAll(row,sectionTitle, buildPersonTile(liege.get(),e -> fireOnCharacterSelected(liege.get())));
        return section;
    }
    private Node buildStatsGrid(HumanCharacter character) {
        GridPane grid = new GridPane();
        grid.setHgap(16);
        grid.setVgap(16);

        ColumnConstraints col = new ColumnConstraints();
        col.setPercentWidth(50);
        grid.getColumnConstraints().addAll(col, col);

        grid.add(UIComponents.buildStatTile("Gender",
                character.getGender().toString()), 0, 0);
        grid.add(UIComponents.buildStatTile("Born",
                character.getCreated().toString()), 1, 0);

        return grid;
    }

    private Node buildTitlesSection(HumanCharacter character) {
        VBox section = new VBox(12);

        Label sectionTitle = new Label("Titles");
        sectionTitle.getStyleClass().add("section-title");

        if (character.getTitles().isEmpty()) {
            return emptySection(section,sectionTitle);
        }

        for (Title<?> title : character.getTitles()) {
            HBox row = new HBox(12);
            row.setPadding(new Insets(12, 16, 12, 16));
            row.getStyleClass().add("holding-row");

            Label titleName = new Label(title.getTitleName().parse());
            titleName.getStyleClass().add("holding-name");

            Label titleType = new Label(title.getClass().getSimpleName());
            titleType.getStyleClass().add("text-secondary");

            VBox info = new VBox(4, titleName, titleType);
            row.getChildren().add(info);

            row.setOnMouseClicked(e -> fireOnTitleSelected(title));

            section.getChildren().add(row);
        }

        section.getChildren().addFirst(sectionTitle);
        return section;
    }

    private Node buildSpouseSection(HumanCharacter character) {
        VBox section = new VBox(12);

        Label sectionTitle = new Label("Spouse");
        sectionTitle.getStyleClass().add("section-title");

        List<HumanCharacter> spouses = FamilyManager.getAllSpouses(character, true);

        if (spouses.isEmpty()) {
            return emptySection(section,sectionTitle);
        }

        // Tile grid for spouses
        FlowPane grid = new FlowPane();
        grid.setHgap(12);
        grid.setVgap(12);

        for (HumanCharacter spouse : spouses) {
            // Grey out deceased spouses
            Node tile = UIComponents.buildPersonTile(
                    spouse,
                    e -> fireOnCharacterSelected(spouse)
            );
            if (!spouse.isAlive()) {
                tile.setOpacity(0.5);
            }
            grid.getChildren().add(tile);
        }

        section.getChildren().addAll(sectionTitle, grid);
        return section;
    }
    private Node buildParentsSection(HumanCharacter character) {
        VBox section = new VBox(12);

        Label sectionTitle = new Label("Parents");
        sectionTitle.getStyleClass().add("section-title");

        Optional<HumanCharacter[]> parents = FamilyManager.getParents(character);

        if (parents.isEmpty()) {
            Label none = new Label("Unknown");
            none.getStyleClass().add("text-secondary");
            section.getChildren().addAll(sectionTitle, none);
            return section;
        }

        FlowPane grid = new FlowPane();
        grid.setHgap(12);
        grid.setVgap(12);

        for (HumanCharacter parent : parents.get()) {
            Node tile = UIComponents.buildPersonTile(
                    parent,
                    e -> fireOnCharacterSelected(parent)
            );
            if (!parent.isAlive()) {
                tile.setOpacity(0.5);
            }
            grid.getChildren().add(tile);
        }

        section.getChildren().addAll(sectionTitle, grid);
        return section;
    }
    private static Node emptySection(VBox section, Label sectionTitle){
        Label none = new Label("None");
        none.getStyleClass().add("text-secondary");
        section.getChildren().addAll(sectionTitle, none);
        return section;
    }
    private Node buildSiblingsSection(HumanCharacter character) {
        VBox section = new VBox(12);

        Label sectionTitle = new Label("Siblings");
        sectionTitle.getStyleClass().add("section-title");

        Optional<HumanCharacter[]> siblings = FamilyManager.getSiblings(character);

        if (siblings.isEmpty()) {
            return emptySection(section,sectionTitle);
        }

        // Filter out self from siblings array
        List<HumanCharacter> siblingsFiltered = Arrays.stream(siblings.get()).toList();

        if (siblingsFiltered.isEmpty()) {
           return emptySection(section,sectionTitle);
        }

        FlowPane grid = new FlowPane();
        grid.setHgap(12);
        grid.setVgap(12);

        for (HumanCharacter sibling : siblingsFiltered) {
            Node tile = UIComponents.buildPersonTile(
                    sibling,
                    e -> fireOnCharacterSelected(sibling)
            );
            if (!sibling.isAlive()) {
                tile.setOpacity(0.5);
            }
            grid.getChildren().add(tile);
        }

        section.getChildren().addAll(sectionTitle, grid);
        return section;
    }
    private Node buildChildrenSection(HumanCharacter character) {
        VBox section = new VBox(12);

        Label sectionTitle = new Label("Children");
        sectionTitle.getStyleClass().add("section-title");

        // Gather all children across all families
        List<HumanCharacter> children = FamilyManager.getNuclear(character).stream()
                .flatMap(f -> f.getChildrenOrdered().stream())
                .distinct()
                .collect(Collectors.toList());

        if (children.isEmpty()) {
            return emptySection(section,sectionTitle);
        }

        FlowPane grid = new FlowPane();
        grid.setHgap(12);
        grid.setVgap(12);

        for (HumanCharacter child : children) {
            Node tile = UIComponents.buildPersonTile(
                    child,
                    e -> fireOnCharacterSelected(child)
            );
            if (!child.isAlive()) {
                tile.setOpacity(0.5);
            }
            grid.getChildren().add(tile);
        }

        section.getChildren().addAll(sectionTitle, grid);
        return section;
    }

    private Node buildVassalsSection(HumanCharacter character) {
        VBox section = new VBox(12);

        // TODO: Add "Show All Vassals" button that opens a separate window
        // showing indirect vassals as well as direct ones
        Label sectionTitle = new Label("Vassals");
        sectionTitle.getStyleClass().add("section-title");

        List<HumanCharacter> vassals = character.getTitles().stream()
                .flatMap(t -> t.getChildren().stream())
                .map(Title::getHolder)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .distinct()
                .collect(Collectors.toList());

        if (vassals.isEmpty()) {
            return emptySection(section,sectionTitle);
        }

        FlowPane grid = new FlowPane();
        grid.setHgap(12);
        grid.setVgap(12);

        for (HumanCharacter vassal : vassals) {
            grid.getChildren().add(UIComponents.buildPersonTile(
                    vassal,
                    e -> fireOnCharacterSelected(vassal)
            ));
        }

        section.getChildren().addAll(sectionTitle, grid);
        return section;
    }
}
