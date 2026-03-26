package com.display.windows.cards.edit.title;

import com.base.DMRegistry;
import com.display.windows.cards.edit.BaseEditCard;
import com.simulation.title.Title;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public abstract class TitleEditCard<T extends Title<T>> extends BaseEditCard<T> {

    protected final TextField nameField = new TextField();
    protected final ComboBox<Title<?>> parentCombo = new ComboBox<>();
    protected final TextField dateCreatedField = new TextField();

    private final Label titleLabel = new Label();

    protected static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MMM dd, yyyy");

    protected TitleEditCard(boolean editMode, String entityName) {
        super(editMode);
        titleLabel.setText(isEditMode() ? "Edit " + entityName : "Create " + entityName);
    }

    @Override
    protected Node buildHeader() {
        VBox header = new VBox(8);
        header.getStyleClass().add("card-header");

        titleLabel.getStyleClass().add("card-title");
        header.getChildren().add(titleLabel);
        return header;
    }

    @Override
    protected Node buildBody(T data) {
        return buildForm();
    }

    private Node buildForm() {
        VBox body = new VBox(16);
        body.setPadding(new Insets(24));

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(12);

        int row = 0;

        grid.add(new Label("Name:"), 0, row);
        grid.add(nameField, 1, row++);

        // Populate parent combo with all titles
        parentCombo.getItems().addAll(DMRegistry.getTitleManager().getAll());
        parentCombo.setPromptText("Select parent title (optional)");
        grid.add(new Label("Parent Title:"), 0, row);
        grid.add(parentCombo, 1, row++);

        dateCreatedField.setPromptText("MMM dd, yyyy");
        grid.add(new Label("Date Established:"), 0, row);
        grid.add(dateCreatedField, 1, row++);

        body.getChildren().add(grid);

        // Subclasses append their own fields
        Node extraFields = buildExtraFields();
        if (extraFields != null) {
            body.getChildren().add(extraFields);
        }

        return body;
    }

    // Subclasses override to add their own fields below the shared ones
    protected Node buildExtraFields() {
        return null;
    }

    @Override
    public void populate(T title) {
        nameField.setText(title.getTitleName().parse());

        title.getParent().ifPresent(parentCombo::setValue);

        if (title.getCreated() != null) {
            dateCreatedField.setText(title.getCreated().format(DATE_FORMAT));
        }

        populateExtraFields(title);
    }

    // Subclasses override to populate their own fields
    protected void populateExtraFields(T title) {}

    @Override
    protected T buildEntity() {
        if (isEditMode()) {
            return updateEntity();
        } else {
            return createEntity();
        }
    }

    // Subclasses implement these instead of buildEntity() directly
    protected abstract T createEntity();
    protected abstract T updateEntity();

    protected LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) return null;
        try {
            return LocalDate.parse(dateStr.trim(), DATE_FORMAT);
        } catch (DateTimeParseException e) {
            // TODO: surface inline error
            return null;
        }
    }
}