package com.objects.family;

import com.base.datemutable.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.datemutable.timeline.change.ChangeSupplier;
import com.base.datemutable.timeline.change.TimelineChange;
import com.base.datemutable.timeline.change.multi.wrapper.TLMap;
import com.google.gson.JsonObject;
import com.objects.CauseOfEnd;
import com.objects.character.sentient.SentientCharacter;
import com.utilities.caching.CachingSupplier;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jgrapht.Graph;
import org.jgrapht.graph.DirectedPseudograph;

import java.time.LocalDate;
import java.util.*;

import static com.objects.family.FamilyRelationship.*;

/**
 * Represents a FamilyGroups entity which includes information about spouses, children, and
 * associated houses. The `FamilyGroups` class also provides utility functions to manage
 * relationships and retrieve information about the family members.
 */
public class Family extends DateMutableEntity<Family> {
    TLMap<DMEReference<? extends SentientCharacter<?>>,FamilyRelationship> relationships;
    CachingSupplier<Graph<DMEReference<? extends SentientCharacter<?>>,FamilyEdge>> familyTree = new CachingSupplier<>(this::buildFamilyTree);
    public Family(UUID id, LocalDate created, @Nullable LocalDate ended, List<ChangeSupplier<Family, ?>> initialState) {
        super(id, created, ended, initialState);
    }

    public Family(LocalDate created, LocalDate ended, List<ChangeSupplier<Family, ?>> initialState) {
        super(created, ended, initialState);
    }

    public Family(DMEReference<Family> dme) {
        super(dme);
    }
    public FamilyRelationship getRelationship(DMEReference<? extends SentientCharacter<?>> member) {
        return relationships.get(member);
    }
    public DMEReference<? extends SentientCharacter<?>> getHead() {
        return relationships.getWhere((k,v) -> v.equals(FamilyRelationship.HEAD_OF_FAMILY)).keySet().iterator().next();
    }
    public DMEReference<? extends SentientCharacter<?>> getSpouse() {
        return relationships.getWhere((k,v) -> v.getType().equals(FamilyRelationship.MemberType.PARTNER)).keySet().iterator().next();
    }
    public Set<DMEReference<? extends SentientCharacter<?>>> getBioChildren() {
        return new HashSet<>(relationships.getWhere((k,v) -> v.getType().equals(FamilyRelationship.MemberType.OFFSPRING_BIOLOGIC)).keySet());
    }
    public Set<DMEReference<? extends SentientCharacter<?>>> getAdoptiveChildren() {
        return new HashSet<>(relationships.getWhere((k,v) -> v.getType().equals(FamilyRelationship.MemberType.OFFSPRING_ADOPTED)).keySet());
    }
    public Set<DMEReference<? extends SentientCharacter<?>>> getChildren() {
        Set<DMEReference<? extends SentientCharacter<?>>> children = new HashSet<>();
        children.addAll(getAdoptiveChildren());
        children.addAll(getBioChildren());
        return children;
    }


    public TLMap<DMEReference<? extends SentientCharacter<?>>, FamilyRelationship> getRelationships() {
        return relationships;
    }
    public void internalSetRelationship(TLMap<DMEReference<? extends SentientCharacter<?>>, FamilyRelationship> relationships) {
        this.relationships = relationships;
    }


    @Override
    public void onLink() {
        for (DMEReference<? extends SentientCharacter<?>> character : relationships.getKeys()){
            character.get().forceLink();
            character.get().linkFamily(this.getReference(),relationships.get(character));
        }
    }
    @Override
    public void doDateChange() {

    }

    @Override
    public TimelineChange<Family> getBirthChange(DMEReference<Family> dme, LocalDate date) {
        return null;
    }

    @Override
    public TimelineChange<Family> getDeathChange(DMEReference<Family> dme, LocalDate date, CauseOfEnd<? super Family> cOd) {
        return null;
    }

    @Override
    public CauseOfEnd<? super Family> defaultDeathCause() {
        return null;
    }

    @Override
    public void additionalSave(JsonObject data) {

    }

    @Override
    public void additionalLoad(JsonObject data) {

    }
    public Graph<DMEReference<? extends SentientCharacter<?>>,FamilyEdge> buildFamilyTree(){
        TreeContainer container = new TreeContainer();
        doBuild(this,container);
        return container.graph;
    }
    protected static void doBuild(Family f, TreeContainer tree){
        tree.addVisited(f);
        handleCoreFamily(f, tree.graph);
        handleExtendedFamily(f, tree);
    }

    public static Optional<FamilyRelationship> getRelationship(
            DMEReference<? extends SentientCharacter<?>> characterA, DMEReference<? extends SentientCharacter<?>> characterB) {

    }


    private static void handleExtendedFamily(Family f, TreeContainer tree){
        for(DMEReference<? extends SentientCharacter<?>> member : f.relationships.getKeys()){
            for(Map.Entry<DMEReference<Family>, FamilyRelationship> family : member.get().getFamilies().entrySet()){
                Family memberFamily = family.getKey().get();
                if(tree.visited.contains(memberFamily)) continue;
                doBuild(memberFamily, tree);
            }
        }
    }
    private static void handleCoreFamily(Family f, Graph<DMEReference<? extends SentientCharacter<?>>, FamilyEdge> tree) {
        for(DMEReference<? extends SentientCharacter<?>> member : f.relationships.getKeys()){
            FamilyRelationship relationship = f.relationships.get(member);
            tree.addVertex(member);
            switch(relationship.getType()){
                case PARTNER -> {
                    handleSpouse(f, tree, member, relationship);
                }
                case OFFSPRING_BIOLOGIC -> {
                    DMEReference<? extends SentientCharacter<?>> head = f.getHead();
                    DMEReference<? extends SentientCharacter<?>> spouse = f.getSpouse();
                    Set<DMEReference<? extends SentientCharacter<?>>> children = f.getBioChildren();
                    handleParents(tree, member, head, spouse, FamilyRelationship.CHILD_BORN, FamilyRelationship.PARENT);
                    for(DMEReference<? extends SentientCharacter<?>> child : children){
                        if(child == member) continue;
                        if (tree.containsEdge(member,child)){
                            FamilyRelationship e = tree.getEdge(member,child).getRelationship();
                            if (e.equals(SIBLING)) continue;
                        }
                        tree.addVertex(child);
                        tree.addEdge(child,member,new FamilyEdge(SIBLING,member));
                        tree.addEdge(member,child,new FamilyEdge(SIBLING,child));
                    }
                }
                case OFFSPRING_ADOPTED -> {
                    DMEReference<? extends SentientCharacter<?>> head = f.getHead();
                    DMEReference<? extends SentientCharacter<?>> spouse = f.getSpouse();
                    Set<DMEReference<? extends SentientCharacter<?>>> children = f.getChildren();
                    handleParents(tree, member, head, spouse, CHILD_ADOPTED, PARENT_ADOPTIVE);
                    for(DMEReference<? extends SentientCharacter<?>> child : children){
                        if(child == member) continue;
                        if (tree.containsEdge(member,child)){
                            FamilyRelationship e = tree.getEdge(member,child).getRelationship();
                            if (e.equals(SIBLING_ADOPTIVE) || e.equals(SIBLING)) continue;
                        }
                        tree.addVertex(child);
                        tree.addEdge(child,member,new FamilyEdge(SIBLING_ADOPTIVE,member));
                        tree.addEdge(member,child,new FamilyEdge(SIBLING_ADOPTIVE,child));
                    }
                }
            }
        }
    }
    private static void handleSpouse(Family f, Graph<DMEReference<? extends SentientCharacter<?>>, FamilyEdge> tree, DMEReference<? extends SentientCharacter<?>> member, FamilyRelationship relationship) {
        DMEReference<? extends SentientCharacter<?>> head = f.getHead();
        FamilyRelationship rt = null;
        switch(relationship){
            case LOVER, LOVER_MALE, LOVER_FEMALE -> {
                rt = LOVER;
                break;
            }
            case SPOUSE, HUSBAND, WIFE -> {
                rt = SPOUSE;
                break;
            }
            case EX_LOVER, EX_LOVER_MALE, EX_LOVER_FEMALE -> {
                rt = EX_LOVER;

            }
            case EX_SPOUSE, EX_SPOUSE_MALE, EX_SPOUSE_FEMALE -> {
                rt = EX_SPOUSE;
            }
            case CONCUBINE -> {
                rt = CONCUBINE;
            }
            case EX_CONCUBINE -> {
                rt = EX_CONCUBINE;
            }
        }
        tree.addVertex(head);
        tree.addEdge(member,head,new FamilyEdge(rt,head));
        tree.addEdge(head, member,new FamilyEdge(rt, member));
    }
    private static void handleParents(Graph<DMEReference<? extends SentientCharacter<?>>, FamilyEdge> tree, DMEReference<? extends SentientCharacter<?>> member, DMEReference<? extends SentientCharacter<?>> head, DMEReference<? extends SentientCharacter<?>> spouse, FamilyRelationship familyRelationship, FamilyRelationship familyRelationship2) {
        tree.addVertex(head);
        tree.addVertex(spouse);
        tree.addEdge(head,member,new FamilyEdge(familyRelationship,member));
        tree.addEdge(spouse,member,new FamilyEdge(familyRelationship,member));
        tree.addEdge(member,head,new FamilyEdge(familyRelationship2,head));
        tree.addEdge(member,spouse,new FamilyEdge(familyRelationship2,spouse));
    }
    protected record TreeContainer(Set<Family> visited, Graph<DMEReference<? extends SentientCharacter<?>>,FamilyEdge> graph){
        TreeContainer(){
            this(new HashSet<>(),new DirectedPseudograph<>(FamilyEdge.class));
        }
        public void addVisited(Family f){
            visited.add(f);
        }
    }

    //######

























}
