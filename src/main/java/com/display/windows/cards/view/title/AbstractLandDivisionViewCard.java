package com.display.windows.cards.view.title;

import com.objects.title.land.AbstractLandDivision;
import com.objects.title.Title;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public abstract class AbstractLandDivisionViewCard<T extends AbstractLandDivision<T>> extends TitleViewCard<T> {

    @Override
    protected Node buildBody(T title) {
        VBox body = new VBox(24);
        body.setPadding(new Insets(24));

        body.getChildren().addAll(
                buildHolderSection(title),
                buildSubHoldingsSection(title)
        );

        return body;
    }


    private Node buildSubHoldingsSection(T title) {
        VBox section = new VBox(12);

        Label sectionTitle = new Label("Sub-Holdings");
        sectionTitle.getStyleClass().add("section-title");

        for (Title<?> child : title.getChildren()) {
            HBox row = new HBox(12);
            row.setPadding(new Insets(12, 16, 12, 16));
            row.getStyleClass().add("holding-row");

            Label holdingName = new Label(child.getTitleName().parse());
            holdingName.getStyleClass().add("holding-name");

            Label holdingType = new Label(child.getClass().getSimpleName());
            holdingType.getStyleClass().add("text-secondary");

            VBox info = new VBox(4, holdingName, holdingType);
            row.getChildren().add(info);

            row.setOnMouseClicked(e -> fireOnTitleSelected(child));
            section.getChildren().add(row);
        }

        return section;
    }
}