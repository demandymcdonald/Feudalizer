package com.display.windows.cards.view.title;


import com.Feudalizer;
import com.display.windows.cards.view.BaseViewCard;
import com.display.windows.UIComponents;
import com.objects.character.human.HumanCharacter;
import com.objects.title.Title;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public abstract class TitleViewCard<T extends Title<T>> extends BaseViewCard<T> {
    private final Label titleNameLabel = new Label();
    private final Label subtitleLabel = new Label();

    protected TitleViewCard() {
        super();
        init();
    }

    // Subclasses implement this to provide their specific body content
    protected abstract Node buildBody(T title);

    // Called by InfoPanel when a title is selected
    public void populate(T title) {
        // Rebuild body with new data
        // Header and footer stay the same structure, just update their labels
        updateHeader(title);
        updateBody(title);
    }
    private void updateHeader(T title) {
        Feudalizer.LOGGER.info("Updating header for title: " + title.getTitleName().parse());
        titleNameLabel.setText(title.getTitleName().parse());
        subtitleLabel.setText(
                title.getParent()
                        .map(p -> p.getTitleName().parse())
                        .orElse("No Parent")
        );
    }

    private void updateBody(T title) {
        scrollPane.setContent(buildBody(title));
    }
    @Override
    protected Node buildHeader() {
        VBox header = new VBox(8);
        header.setPadding(new Insets(24));
        header.getStyleClass().add("card-header");

        titleNameLabel.getStyleClass().add("card-title");
        subtitleLabel.getStyleClass().add("card-subtitle");

        header.getChildren().addAll(titleNameLabel, subtitleLabel);
        return header;
    }

    @Override
    protected Node buildFooter() {
        HBox footer = new HBox(12);
        footer.setPadding(new Insets(20, 24, 20, 24));
        footer.getStyleClass().add("card-footer");

        Button editBtn = new Button("Edit");
        Button assignBtn = new Button("Assign Lord");

        HBox.setHgrow(editBtn, Priority.ALWAYS);
        HBox.setHgrow(assignBtn, Priority.ALWAYS);
        editBtn.setMaxWidth(Double.MAX_VALUE);
        assignBtn.setMaxWidth(Double.MAX_VALUE);

        footer.getChildren().addAll(editBtn, assignBtn);
        return footer;
    }
    protected Node buildHolderSection(T title) {
        VBox section = new VBox(8);

        Label holderLabel = new Label("Current Holder");
        holderLabel.getStyleClass().add("section-label");

        if (title.getHolder().isEmpty()) {
            Label vacant = new Label("Vacant");
            vacant.getStyleClass().add("text-secondary");
            section.getChildren().addAll(holderLabel, vacant);
            return section;
        }

        HumanCharacter holder = title.getHolder().get();

        HBox holderRow = new HBox(12);
        holderRow.setAlignment(Pos.CENTER_LEFT);
        holderRow.setPadding(new Insets(12));
        holderRow.getStyleClass().add("holder-section");
//        String initials = String.valueOf(holder.getGivenName().charAt(0))
//                + String.valueOf(holder.getHouse().getName().charAt(0));
//        Label portrait = new Label(initials);
//        portrait.getStyleClass().add("portrait-medium");
//        portrait.setPrefSize(48, 48);
        final Label portrait = UIComponents.buildPortraitLabel(holder,48,"portrait-medium");
        final Label name = new Label(holder.getGivenName());
        name.getStyleClass().add("holder-name");
        Label house = new Label(holder.getSurname());
        house.getStyleClass().add("text-secondary");
        VBox info = new VBox(4, name, house);

        holderRow.getChildren().addAll(portrait, info);
        holderRow.setOnMouseClicked(e -> fireOnCharacterSelected(holder));

        section.getChildren().addAll(holderLabel, holderRow);
        return section;
    }
}
