package com.objects.culture.object.compass;

import com.objects.culture.tenet.TenetManager;
import com.utilities.Displayable;

public class Ideology implements Displayable {
    //Static object, mainly used for display, but also in factory methods.
        private final String id;
        private final String displayName;
        private final String description;
        private final PoliticalCompass compass;
        //private final List<Tenet> linkedTenets = new ArrayList<>(); //may not use
        //Descriptions should be 100-200 characters max.
        //Ids should be all lowercase, using _ instead of spaces.
        //Display names should be structured as proper titles.
        public Ideology(String id, String displayName, String description, PoliticalCompass compass){
            this.id = id;
            this.displayName = displayName;
            this.description = description;
            this.compass = compass;
            TenetManager.registerIdeology(this);
        }
    @Override
    public String getID() {
        return id;
    }

    @Override
    public String displayName() {
        return displayName;
    }

    @Override
    public String description() {
        return description;
    }

    public PoliticalCompass getCompass(){
            return compass.clone();
    }

}
