package com.base;

import com.Global;
import com.base.reference.DMEReference;
import com.base.timeline.state.TimelineState;
import com.base.timeline.change.ChangeSupplier;
import com.base.timeline.change.TimelineChange;
import com.google.gson.JsonObject;
import com.utilities.SuperclassRegistry;
import com.utilities.ThreadManager;

import java.time.LocalDate;
import java.util.ArrayList;
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
    public <R extends DateMutableEntity<R>> R loadEntity(DMEReference<R> dme, JsonObject object) {
        if (!accepts(dme)){
            //This checks if this is the correct manager. It SHOULD always be by this phase.
            throw new IllegalArgumentException("Cannot load object of type " + dme.getType() + " into " + this.getClass());
        }
        DMEReference<? extends T> td = (DMEReference<? extends T>) dme;
        return  (R) super.loadObject(td.getType(), td.getID(), object);
    }
    public <R extends DateMutableEntity<R>> R updateOrLoadEntity(DMEReference<R> dme, JsonObject object) {
        if (!accepts(dme)){
            //This checks if this is the correct manager. It SHOULD always be by this phase.
            throw new IllegalArgumentException("Cannot load object of type " + dme.getType() + " into " + this.getClass());
        }
        DMEReference<? extends T> td = (DMEReference<? extends T>) dme;
        return  (R) super.updateOrLoad(td.getType(), td.getID(), object);

    }
    public <R extends DateMutableEntity<R>> R getEntity(DMEReference<R>  entity) {
        if (!accepts(entity)){
            //This checks if this is the correct manager. It SHOULD always be by this phase.
            throw new IllegalArgumentException("Cannot load object of type " + entity.getType() + " into " + this.getClass());
        }
        DMEReference<? extends T> td = (DMEReference<? extends T>) entity;
        T r = get(td.getType(),entity.getID());
        if (r == null && !ThreadManager.isMainThread()){
            JsonObject o = Global.getSandboxHandler().requestData(entity).join();
            return loadEntity(entity,o);
        }
        return (R) r;
    }


    public abstract Class<T> instanceClass();
    public abstract TimelineChange<T> getBirthChange(DMEReference<T> dme, LocalDate date);
    public abstract TimelineChange<T> getDeathChange(DMEReference<T> dme, LocalDate date);
    public final TimelineState<T> buildBirth(DMEReference<T> dme, LocalDate date, List<ChangeSupplier<T,?>> defaults){
        List<TimelineChange<? super T>> changes = buildChangeList(getBirthChange(dme,date),date,dme,defaults);
        return new TimelineState<T>(dme.get().getTimeline(), date, date,true, changes);
    };
    public final TimelineState<T> buildDeath(DMEReference<T> dme, LocalDate date, List<ChangeSupplier<T,?>> defaults){
        List<TimelineChange<? super T>> changes = buildChangeList(getDeathChange(dme,date),date,dme,defaults);
        return new TimelineState<T>(dme.get().getTimeline(),date, date,true, changes);
    };
    private static <T extends DateMutableEntity<T>> List<TimelineChange<? super T>> buildChangeList(
            TimelineChange<T> first, LocalDate date, DMEReference<T> ref, List<ChangeSupplier<T,?>> defaults){
        List<TimelineChange<? super T>> Changes = new ArrayList<>();
        Changes.add(first);
        for (ChangeSupplier<T,?> c : defaults) {
            Changes.add(c.supply(date,ref));
        }
        return Changes;
    }
}
