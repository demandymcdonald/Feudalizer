package com.objects.culture.tenet.mutable.tenets.general;

import com.objects.culture.object.compass.PoliticalCompass;
import com.objects.culture.tenet.interest.InterestGroup;

public class CompassGenerators {

    public static PoliticalCompass disenfranchise(int severity, InterestGroup... group) {
        double multiplier = Math.min(severity, 5);
        int total = 1;
        for (InterestGroup ig : group) {
            total++;
            multiplier += switch(ig.getDimension()){
                case Sex_At_Birth,Race_Ethnicity -> 1;
                case Gender_Identity,Class_Caste,Religion -> .9;
                case Sexual_Orientation,Culture -> .8;
                case Political_Ideology -> .6;
                case Disability -> .4;
                case Education -> .2;
                case Lifestyle -> .05;
            };
        }
        double mult = multiplier / total;
        return new PoliticalCompass(0,(int) Math.round(20*mult),0,(int) Math.round(50*mult),(int) Math.round(-100 * mult));
    }
}
