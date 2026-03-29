//package com.simulation.title;
//
//import com.base.subject.StateReference;
//import com.google.gson.JsonObject;
//import com.simulation.people.BookCharacter;
//
//import java.util.Date;
//import java.util.List;
//import java.util.UUID;
//
//public class Claim extends Title<Claim> {
//    Title<?> parent;
//    int position;
//    boolean isSubinfeudationGrant = false;
//    public Claim(UUID id, Date created, Date ended, Title<?> parent, int position, boolean isSubinfeudationGrant) {
//        super(id, created, ended);
//    }
//
//    public Claim(UUID id, Date created,  Title<?> parent, int position) {
//        this(id, created, null, parent, position, false);
//    }
//
//    public Claim(JsonObject payload) {
//        super(payload);
//    }
//
//    @Override
//    public void relink(TitleContainer state) {
//
//    }
//
//    @Override
//    protected JsonObject serializeData(TitleContainer data) {
//        return null;
//    }
//
//    @Override
//    protected TitleContainer buildState(JsonObject o) {
//        return null;
//    }
//
//    protected record ClaimContainer(UUID title, Integer position){
//
//    }
//    @Override
//    public boolean canInherit(BookCharacter person) {
//        boolean base = parent.canInherit(person);
//        if (!base) {
//            for (BookCharacter p : parent.getSuccession().getChain()){
//                if (parent.canInherit(p)){
//                    return false;
//                }
//            }
//            isSubinfeudationGrant = true;
//            return true;
//        }
//        return true;
//    }
//
//    @Override
//    public boolean isInheritable() {
//        return false;
//    }
//
//    @Override
//    public boolean isSubPropagating() {
//        return false;
//    }
//
//    @Override
//    public List<BookCharacter> getAllClaimants() {
//        return parent.getAllClaimants();
//    }
//
//    @Override
//    protected JsonObject updateState(JsonObject j) {
//        return null;
//    }
//
//    @Override
//    protected void onRelink() {
//
//    }
//
//    @Override
//    protected void onNewStateLoad(JsonObject passthrough) {
//
//    }
//
//    @Override
//    public StateReference getTitleName() {
//        return null;
//    }
//
//}
