package com.display.windows.cards.view;

import com.display.windows.cards.BaseCard;
import com.simulation.people.BookCharacter;
import com.simulation.title.Title;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.function.Consumer;

public abstract class BaseViewCard<T> extends BaseCard<T> {
    private Consumer<Title<?>> onTitleSelected;
    private Consumer<BookCharacter> onCharacterSelected;

    protected BaseViewCard() {
    }

    public void setOnTitleSelected(Consumer<Title<?>> callback) {
        this.onTitleSelected = callback;
    }

    public void setOnCharacterSelected(Consumer<BookCharacter> callback) {
        this.onCharacterSelected = callback;
    }

    protected void fireOnTitleSelected(Title<?> title) {
        if (onTitleSelected != null) onTitleSelected.accept(title);
    }

    protected void fireOnCharacterSelected(BookCharacter character) {
        if (onCharacterSelected != null) onCharacterSelected.accept(character);
    }

}