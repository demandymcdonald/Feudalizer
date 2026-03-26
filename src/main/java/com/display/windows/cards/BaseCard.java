package com.display.windows.cards;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public abstract class BaseCard<T> {
    protected final VBox root = new VBox();
    protected final ScrollPane scrollPane = new ScrollPane();

    protected abstract Node buildHeader();
    protected abstract Node buildFooter();
    protected abstract Node buildBody(T data);
    private Node buildScrollableBody() {
        scrollPane.getStyleClass().add("card-scroll");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setFitToWidth(true);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        return scrollPane;
    }

    protected void init() {
        root.getChildren().addAll(
                buildHeader(),
                buildScrollableBody(),
                buildFooter()
        );
    }

    public Node getNode() {
        return root;
    }
}
