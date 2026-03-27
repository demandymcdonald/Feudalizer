package com.display.windows.cards.view.title;

import com.simulation.title.land.resources.HabitableLand;
import com.simulation.title.land.resources.Resource;
import com.simulation.title.Title;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import static com.display.windows.UIComponents.buildStatTile;

public abstract class HabitableLandViewCard<R extends HabitableLand<R>> extends TitleViewCard<R> {

    @Override
    protected Node buildBody(R title) {
        VBox body = new VBox(24);
        body.setPadding(new Insets(24));

        body.getChildren().addAll(
                buildHolderSection(title),
                buildStatsGrid(title),
                buildResourcesSection(title),
                buildSubHoldingsSection(title)
        );

        return body;
    }


    private Node buildStatsGrid(R title) {
        GridPane grid = new GridPane();
        grid.setHgap(16);
        grid.setVgap(16);

        // Two columns, equal width
        ColumnConstraints col = new ColumnConstraints();
        col.setPercentWidth(50);
        grid.getColumnConstraints().addAll(col, col);

        grid.add(buildStatTile("Population",
                String.format("%,d", title.getPopulation())), 0, 0);
        grid.add(buildStatTile("Sub-Holdings",
                String.valueOf(title.getChildren().size())), 1, 0);

        return grid;
    }


    private Node buildResourcesSection(R title) {
        VBox section = new VBox(12);

        Label sectionTitle = new Label("Resources");
        sectionTitle.getStyleClass().add("section-title");

        FlowPane resources = new FlowPane();
        resources.setHgap(8);
        resources.setVgap(8);

        // getResources() already aggregates up from children
        for (Resource r : title.getResources()) {
            Label tag = new Label(r.type().name() + " (" + r.abundance() + ")");
            tag.getStyleClass().add("resource-tag");
            resources.getChildren().add(tag);

        }

        section.getChildren().addAll(sectionTitle, resources);
        return section;
    }

    private Node buildSubHoldingsSection(R title) {
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

            // Clicking a sub-holding fires the title callback
            row.setOnMouseClicked(e -> fireOnTitleSelected(child));
            row.getStyleClass().add("holding-row");
            holdingName.getStyleClass().add("holding-name");
            section.getChildren().add(row);
        }

        return section;
    }
}