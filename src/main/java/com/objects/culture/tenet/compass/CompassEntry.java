package com.objects.culture.tenet.compass;

import com.google.gson.JsonObject;
import com.utilities.number.BoundedInteger;
import com.utilities.serialization.JsonSerializable;

public class CompassEntry implements JsonSerializable {
    private static final int COMPASS_MAX = 512;
    // Which rights are the natural base for all rights: individual rights or collective rights. Low values are Collectivists: advocating for rights for groups, not individuals. High values are Individualists: advocating for individual rights that should not be trampled by the needs of the collective.
    // Examples of Low values: Communists, Nazis
    // Examples of High values: Anarchists
    private final BoundedInteger individualCollectiveAxis = BoundedInteger.of(-COMPASS_MAX,COMPASS_MAX);
    // Who matters morally and politically. Low values are Universalist: Advocating for everyone to join the ideology. High values are Particularists: Advocating for their group alone.
    // Examples of Low values: Communists, NeoLiberals,
    // Examples of High values: Nazis, Fascists
    private final BoundedInteger universalParticularAxis = BoundedInteger.of(-COMPASS_MAX,COMPASS_MAX);
    // How trusting is a person in large entities that are opaque in concept and operation. Low values are Low Trust, meaning they do not trust entities they do not control. High values are High Trust, meaning they implicitly trust entities they do not understand
    // Examples of Low values: Populists
    // Examples of High values: Technocrats, Traditional Authoritarians
    private final BoundedInteger trustInOpacityAxis = BoundedInteger.of(-COMPASS_MAX,COMPASS_MAX);

    public CompassEntry(int individualCollectiveAxis, int universalParticularAxis, int trustInOpacityAxis) {
        this.individualCollectiveAxis.set(individualCollectiveAxis);
        this.universalParticularAxis.set(universalParticularAxis);
        this.trustInOpacityAxis.set(trustInOpacityAxis);
    }
    public CompassEntry() {}
    public int calculateExtremismPercent(){
        return (int) (calculateExtremism() * 100);
    }
    public double calculateExtremism(){
        double axisA = Math.pow(Math.abs(individualCollectiveAxis.get().doubleValue()) / COMPASS_MAX,3);
        double axisB = Math.pow(Math.abs(universalParticularAxis.get().doubleValue()) / COMPASS_MAX,3);
        double axisC = Math.pow(Math.abs(trustInOpacityAxis.get().doubleValue()) / COMPASS_MAX,3);
        double total = (axisA + axisB + axisC)/2;
        return Math.min(1,total);
    }

    @Override
    public JsonObject toJson() {
        JsonObject o = new JsonObject();
        o.addProperty("axisA", individualCollectiveAxis.get());
        o.addProperty("axisB", universalParticularAxis.get());
        o.addProperty("axisC", trustInOpacityAxis.get());
        return o;
    }

    @Override
    public void fromJson(JsonObject json) {
        individualCollectiveAxis.set(json.get("axisA").getAsInt());
        universalParticularAxis.set(json.get("axisB").getAsInt());
        trustInOpacityAxis.set(json.get("axisC").getAsInt());
    }
}
