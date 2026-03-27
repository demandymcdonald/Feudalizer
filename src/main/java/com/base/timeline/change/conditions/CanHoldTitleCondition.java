package com.base.timeline.change.conditions;

import com.base.flags.Errors;
import com.base.flags.StateError;
import com.base.reference.DMEReference;
import com.simulation.factions.FactionManager;
import com.simulation.people.BookCharacter;
import com.simulation.title.Title;
import org.apache.commons.lang3.function.TriFunction;

import java.util.List;

import static com.base.flags.Errors.aboveMaxTitle;

public class CanHoldTitleCondition<T extends Title<T>, U extends Title<U>> extends DMECondition<CanHoldTitleCondition<T,U>,T,U,BookCharacter>{


    protected CanHoldTitleCondition(String id, TriFunction<T, U, BookCharacter, DMEResult<T, U, BookCharacter>> function) {
        super(id, function);
    }


    public static <T extends Title<T>, U extends Title<U>> CanHoldTitleCondition<T, U> build(String id, TriFunction<T, U, BookCharacter, DMEResult<T, U, BookCharacter>> func) {
        return new CanHoldTitleCondition<>(id, func);
    }

    /**
     * Creates a CanHoldTitleCondition that evaluates whether the number of holdings for a specific title type
     * exceeds the maximum allowed for that type. The condition checks the number of titles held of a
     * certain type by a given BookCharacter and compares it to the maximum allowed for that type.
     *
     * If the number of holdings exceeds the limit, the condition fails and constructs
     * a descriptive error message. Otherwise, it succeeds and indicates that the holdings are
     * within the allowed limit.
     *
     * @param <T> the specific type of the first title involved in the condition
     * @param <U> the specific type of the second title involved in the condition (unused)
     * @return a CanHoldTitleCondition that verifies if the number of titles held by a BookCharacter of
     *         a certain type does not exceed the maximum allowable limit.
     */
    public static <T extends Title<T>, U extends Title<U>> CanHoldTitleCondition<T,U> AT_MAX(){
        return build("tc_domainLimit", new TriFunction<T, U, BookCharacter, DMEResult<T,U, BookCharacter>>() {
            @Override
            public DMEResult<T,U,BookCharacter> apply(T t, U u, BookCharacter bookCharacter) {
                List<T> matchingHoldings = bookCharacter.getTitlesOfType(t.getClass());
                final String text;
                if (matchingHoldings.size() >= t.maxOfType()){
                    text = matchingHoldings.size() + " is above the Holding Limit of " + t.maxOfType() + " for type " + t.getClass().getName();
                    return new DMEResult<>(false, DMEReference.of((Title<T>) t), DMEReference.of((Title<U>) u), DMEReference.of(bookCharacter), text, () -> aboveMaxTitle(text));
                }
                text = matchingHoldings.size() + " is below the Holding Limit of " + t.maxOfType() + " for type " + t.getClass().getName();
                return new DMEResult<>(true, DMEReference.of((Title<T>) t), DMEReference.of((Title<U>) u), DMEReference.of(bookCharacter), text, Errors::EMPTY);
            }
        });
    }
    /**
     * Defines a CanHoldTitleCondition that evaluates whether a BookCharacter is in the same faction
     * as a particular title. The condition checks if the factions of the character and the title match.
     *
     * @param <T> the type of the first title involved in the condition
     * @param <U> the type of the second title involved in the condition (unused)
     * @return a CanHoldTitleCondition that fails if the BookCharacter does not belong to the same faction
     *         as the given title, and succeeds otherwise
     */
    public static <T extends Title<T>, U extends Title<U>> CanHoldTitleCondition<T,U> WRONG_TOP_FACTION(){
        return build("tc_wrongTopFaction", new TriFunction<T, U, BookCharacter, DMEResult<T,U, BookCharacter>>() {
            @Override
            public DMEResult<T,U,BookCharacter> apply(T t, U u, BookCharacter bookCharacter) {
                String text;
                //TODO actually match once factions as a concept are implemented for DMEs...
                if (FactionManager.matchingFaction(bookCharacter,t)){
                    text = bookCharacter.toString() +" is in the same faction as " + t.toString();
                    return new DMEResult<>(true, DMEReference.of((Title<T>) t), DMEReference.of((Title<U>) u), DMEReference.of(bookCharacter), text, Errors::EMPTY);
                } else {
                    text = bookCharacter.toString() + " is not in the same faction as " + t.toString();
                    final StateError se = Errors.wrongFaction(bookCharacter,t,text);
                    return new DMEResult<>(false, DMEReference.of((Title<T>) t), DMEReference.of((Title<U>) u), DMEReference.of(bookCharacter), text, () -> {
                        return se;
                    });
                }
            }
        });
    }

    public static <T extends Title<T>,U extends Title<U>> List<CanHoldTitleCondition<T,U>> BaseConditions(){
        return List.of(WRONG_TOP_FACTION(),AT_MAX());
    }

    @Override
    public String getText() {
        return "";

    }
}
