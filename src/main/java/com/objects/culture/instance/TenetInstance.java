package com.objects.culture.instance;

import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.change.ChangeID;
import com.base.timeline.change.map.TimelineMapChange;
import com.base.timeline.variable.TimelineEasingVariable;
import com.google.gson.JsonObject;
import com.objects.culture.tenet.TenetReference;
import com.objects.culture.tenet.types.Tenet;
import com.utilities.number.BoundedDouble;
import org.apache.commons.lang3.tuple.Pair;

import static com.objects.culture.tenet.Acceptance.MAX_VALUE;

public abstract class TenetInstance<TI extends TenetInstance<TI,C,T>,C extends TimelineMapChange<?,Tenet, TI,T>,T extends DateMutableEntity<T>> extends TimelineEasingVariable<TI, C, T> {
//    private static final double b = .23; //apathy peak as percent from start
//    private static final double c = 0.00022; //apathy decay
//    private static final double z = .125; // zealotry peak as percent from end. Should hit right as the they pass the Fanatic mark
//    private static final double f = .69; // zealotry drop-off  target
//    private static final double g = 2.2; //zealotry drop-off steepness
//    private static final double k = .05; //kernal floor
//    private static final int pf = 2; //crushing power for normalization
    private final BoundedDouble opinion = new BoundedDouble(-MAX_VALUE, MAX_VALUE);
    private TenetReference tenetReference;
    public TenetInstance() {
    }
    public TenetInstance(TenetReference tr, DMEReference<T> owner, EasingType easeType, ChangeID thisChange, double currentValue) {
        super(owner, thisChange, easeType);
        this.opinion.set(currentValue);
        this.tenetReference = tr;
    }
    @Override
    public double getCurrent() {
        return opinion.get();
    }
    public void amentCurrent(double amount){
        opinion.add(amount);
        setChanged();
    }
    public void setCurrent(double amount){
        opinion.set(amount);
        setChanged();
    }
    public Tenet getTenet() {
        return tenetReference.get();
    }

    private void setChanged() {
        DMEReference<T> owner = getOwner();
        C c = getC(owner, getThisChange());
        c.addChange(Pair.of(getTenet(), (TI) this));
    }

//    @Override
//    protected TenetInstance<?,?,?> findE(DMEReference<Culture> ref, ChangeID current) {
//        CultureMapChanges.TenetMapChange c = getC(ref, getThisChange());
//        return c.getFromFuture(current, this);
//    }
    @Override
    public void additionalSave(JsonObject data) {
        data.addProperty("opinion", opinion.get());
        data.add("tenet", tenetReference.serialize());
    }

    @Override
    public void additionalLoad(JsonObject data) {
        opinion.set(data.get("opinion").getAsDouble());
        this.tenetReference = TenetReference.deserialize(data.get("tenet").getAsJsonObject());
    }
}
