package com.objects.culture.tenet.tenets;

import com.objects.culture.object.compass.PoliticalCompass;
import com.objects.culture.tenet.group.groups.GovernmentGroups;
import com.objects.culture.tenet.group.TenetGroup;
import com.objects.culture.tenet.types.TenetPillar;

import static com.objects.culture.tenet.group.groups.GovernmentGroups.*;

public class GovernmentTenet {
    public static abstract class GovernmentSystem extends TenetPillar<GovernmentSystem>{
        public GovernmentSystem(PoliticalCompass ce, String id, String name, String description) {
            super(GovernmentGroups.GOVERNMENT_SYSTEM, ce, id, name, description);
        }
    }
    public static abstract class ClassRights extends TenetPillar<ClassRights>{
        public enum ClassType {
            Elite(ELITE_CLASS),
            Middle_Class(MIDDLE_CLASS),
            Soldier(SOLDIER_CLASS),
            Working_Class(WORKING_CLASS),
            Disenfranchised(DISENFRANCHISED),
            Slave(SLAVE),
            Outsider(OUTSIDER);
            private final TenetGroup group;
            ClassType(TenetGroup group){
                this.group = group;
            }
            public TenetGroup getGroup(){
                return group;
            }
        }
        public ClassRights(ClassType group, GovernmentSystem gs, String id, String name, String description) {
            super(group.getGroup(), gs.getCompassEntry(), id, name, description);
            gs.add(this);
        }
        public ClassRights(ClassType group, PoliticalCompass ce, String id, String name, String description) {
            super(group.getGroup(), ce, id, name, description);
        }

        
    }








}
