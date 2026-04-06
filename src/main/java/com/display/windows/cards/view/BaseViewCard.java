package com.display.windows.cards.view;

import com.display.windows.cards.BaseCard;
import com.objects.character.human.HumanCharacter;
import com.objects.title.Title;

import java.util.function.Consumer;

public abstract class BaseViewCard<T> extends BaseCard<T> {
    private Consumer<Title<?>> onTitleSelected;
    private Consumer<HumanCharacter> onCharacterSelected;

    protected BaseViewCard() {
    }

    public void setOnTitleSelected(Consumer<Title<?>> callback) {
        this.onTitleSelected = callback;
    }

    public void setOnCharacterSelected(Consumer<HumanCharacter> callback) {
        this.onCharacterSelected = callback;
    }

    protected void fireOnTitleSelected(Title<?> title) {
        if (onTitleSelected != null) onTitleSelected.accept(title);
    }

    protected void fireOnCharacterSelected(HumanCharacter character) {
        if (onCharacterSelected != null) onCharacterSelected.accept(character);
    }

}