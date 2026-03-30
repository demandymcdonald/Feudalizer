package com.base;

import com.base.reference.DMEReference;
import com.base.timeline.TimelineState;
import com.base.timeline.change.TimelineChange;
import com.google.gson.JsonObject;
import com.utilities.SuperclassRegistry;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public abstract class AbstractMutableManager<M extends AbstractMutableManager<M,T,BA>,T extends DateMutableEntity<T>,BA>
        extends SuperclassRegistry<M,T,UUID,BA> {
    protected AbstractMutableManager(String uniqueKey) {
        super(uniqueKey);
        DMRegistry.registerManager(this);
    }
    public boolean accepts(DMEReference<?> dme){
        return accepts(dme.getClass());
    }
    public boolean accepts(Class<?> clazz){
        return instanceClass().isAssignableFrom(clazz);
    }
    public void onLink(){
        doIterate(DateMutableEntity::onLink);
    }
    public void onDateChange(){
        doIterate(DateMutableEntity::onLink);
    }
    public <R extends DateMutableEntity<R>> R loadObject(DMEReference<R> dme, JsonObject object) {
        if (!accepts(dme)){
            //This checks if this is the correct manager. It SHOULD always be by this phase.
            throw new IllegalArgumentException("Cannot load object of type " + dme.getType() + " into " + this.getClass());
        }
        DMEReference<? extends T> td = (DMEReference<? extends T>) dme;
        return  (R) super.loadObject(td.getType(), td.getID(), object);
    }

    public abstract Class<T> instanceClass();
    public abstract TimelineChange<T> getBirthChange(DMEReference<T> dme, LocalDate date);
    public abstract TimelineChange<T> getDeathChange(DMEReference<T> dme, LocalDate date);
    public final TimelineState<T> buildBirth(DMEReference<T> dme, LocalDate date, List<TimelineChange<? super T>> defaults){
        defaults.addFirst(getBirthChange(dme,date));
        return new TimelineState<T>(dme, date, date,true, defaults);
    };
    public final TimelineState<T> buildDeath(DMEReference<T> dme, LocalDate date, List<TimelineChange<? super T>> defaults){
        defaults.addFirst(getDeathChange(dme,date));
        return new TimelineState<T>(dme,date, date,true, defaults);
    };
}
