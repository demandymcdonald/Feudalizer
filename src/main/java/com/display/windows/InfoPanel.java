package com.display.windows;

import com.base.DMRegistry;
import com.display.geography.GeometryType;
import com.display.windows.cards.view.CharacterViewCard;
import com.display.windows.cards.view.title.*;
import com.simulation.title.land.*;
import com.simulation.people.BookCharacter;
import com.simulation.title.Title;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.util.Pair;

import java.util.List;
import java.util.function.Consumer;

public class InfoPanel {
    private final StackPane root = new StackPane();
    private final CharacterViewCard characterCard = new CharacterViewCard();

    // Title cards
    private final CountyViewCard countyCard = new CountyViewCard();
    private final ProvinceViewCard provinceCard = new ProvinceViewCard();
    private final TownViewCard townCard = new TownViewCard();
    private final ManorViewCard manorCard = new ManorViewCard();
    private final SEZViewCard sezCard = new SEZViewCard();

    // Callbacks to be set by MainWindow
    private Consumer<Title<?>> onTitleSelected;
    private Consumer<BookCharacter> onCharacterSelected;

    public InfoPanel() {
        root.setPrefWidth(440);
        root.getStyleClass().add("info-panel");
        // Add all cards to the stack
        root.getChildren().addAll(
                countyCard.getNode(),
                provinceCard.getNode(),
                townCard.getNode(),
                manorCard.getNode(),
                sezCard.getNode(),
                characterCard.getNode()
        );

        // Wire callbacks through to all cards
        wireCallbacks();

        // Start with everything hidden
        hideAll();
    }

    public void showTitle(Title<?> title) {
        hideAll();
        TitleViewCard<?> card = getCardFor(title);
        populateAndShow(card, title);
    }

    public void showCharacter(BookCharacter character) {
        hideAll();
        characterCard.populate(character);
        characterCard.getNode().setVisible(true);
    }

    // Unchecked cast is safe here because getCardFor guarantees the match
    @SuppressWarnings("unchecked")
    private <T extends Title<T>> void populateAndShow(TitleViewCard<T> card, Title<?> title) {
        card.populate((T) title);
        card.getNode().setVisible(true);
    }

    private TitleViewCard<?> getCardFor(Title<?> title) {
        return switch (title) {
            case County c      -> countyCard;
            case Province p    -> provinceCard;
            case Town t        -> townCard;
            case Manor m       -> manorCard;
            case SpecialEconomicZone s -> sezCard;
            default -> throw new IllegalArgumentException(
                    "No card registered for title type: " + title.getClass().getSimpleName()
            );
        };
    }
    public void openTitle(Pair<GeometryType,String> county){
        Title<?> title = DMRegistry.getTitleManager().getOrCreateTitle(county.getKey(),county.getValue());
        showTitle(title);
    }
    private void wireCallbacks() {
        List<TitleViewCard<?>> allTitleCards = List.of(
                countyCard, provinceCard, townCard, manorCard, sezCard
        );

        for (TitleViewCard<?> card : allTitleCards) {
            card.setOnTitleSelected(t -> {
                if (onTitleSelected != null) onTitleSelected.accept(t);
                showTitle(t);
            });
            card.setOnCharacterSelected(c -> {
                if (onCharacterSelected != null) onCharacterSelected.accept(c);
                showCharacter(c);
            });
        }

        characterCard.setOnTitleSelected(t -> {
            if (onTitleSelected != null) onTitleSelected.accept(t);
            showTitle(t);
        });
        characterCard.setOnCharacterSelected(c -> {
            if (onCharacterSelected != null) onCharacterSelected.accept(c);
            showCharacter(c);
        });
    }

    private void hideAll() {
        root.getChildren().forEach(node -> node.setVisible(false));
    }

    // Called by MainWindow to set external callbacks if needed
    public void setOnTitleSelected(Consumer<Title<?>> callback) {
        this.onTitleSelected = callback;
    }

    public void setOnCharacterSelected(Consumer<BookCharacter> callback) {
        this.onCharacterSelected = callback;
    }

    public Node getNode() {
        return root;
    }
}
