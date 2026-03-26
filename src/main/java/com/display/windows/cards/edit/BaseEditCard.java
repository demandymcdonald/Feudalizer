package com.display.windows.cards.edit;

import com.display.windows.cards.BaseCard;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.util.function.Consumer;

public abstract class BaseEditCard<T> extends BaseCard<T> {
    private Consumer<T> onConfirm;
    private Runnable onCancel;
    private final boolean editMode;

    protected BaseEditCard(boolean editMode) {
        this.editMode = editMode;
    }

    @Override
    protected Node buildFooter() {
        HBox footer = new HBox(12);
        footer.getStyleClass().add("card-footer");

        Button confirmBtn = new Button(editMode ? "Save" : "Create");
        Button cancelBtn = new Button("Cancel");
        confirmBtn.getStyleClass().add("button-primary");

        HBox.setHgrow(confirmBtn, Priority.ALWAYS);
        HBox.setHgrow(cancelBtn, Priority.ALWAYS);
        confirmBtn.setMaxWidth(Double.MAX_VALUE);
        cancelBtn.setMaxWidth(Double.MAX_VALUE);

        confirmBtn.setOnAction(e -> fireOnConfirm());
        cancelBtn.setOnAction(e -> fireOnCancel());

        footer.getChildren().addAll(confirmBtn, cancelBtn);
        return footer;
    }

    protected boolean isEditMode() { return editMode; }

    public void setOnConfirm(Consumer<T> callback) { this.onConfirm = callback; }
    public void setOnCancel(Runnable callback) { this.onCancel = callback; }

    protected void fireOnConfirm() { if (onConfirm != null) onConfirm.accept(buildEntity()); }
    protected void fireOnCancel() { if (onCancel != null) onCancel.run(); }

    protected abstract T buildEntity();
    public abstract void populate(T entity);
}