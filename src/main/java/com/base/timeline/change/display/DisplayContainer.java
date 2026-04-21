package com.base.timeline.change.display;

import com.Global;
import com.base.DateMutableEntity;
import com.base.reference.DMEReference;
import com.base.timeline.Timeline;

public class DisplayContainer<T extends DateMutableEntity<T> & ITLDisplayable<T>> {
        private final DMEReference<T> reference;
        private String name;
        private String description;
        private String displayId;
        public DisplayContainer(DMEReference<T> reference, String displayId, String name, String description) {
            this.reference = reference;
            this.displayId = displayId == null || displayId.isEmpty() ? "loading_id" : displayId;
            this.name = name == null || name.isEmpty() ? "loading_name" : name;
            this.description = description == null || description.isEmpty() ? "loading_desc" : description;
        }
        public DisplayContainer(DMEReference<T> reference) {
            this(reference, null, null, null);
        }
        public String getDisplayId() {
            return displayId;
        }
        public String getName() {
            return name;
        }
        public String getDescription() {
            return description;
        }

        public void setName(String name) {
            this.name = name;
        }
        private Timeline<T> getTL(){
            return reference.get().getTimeline();
        }
        public void setDescription(String description) {
            getTL().addChange(new DisplayChanges.Description<>(reference, Global.getDate(),description));
        }
        public void setDisplayId(String displayId) {
            getTL().addChange(new DisplayChanges.ID<>(reference, Global.getDate(),displayId));
        }
        public void internalName(String name) {
            getTL().addChange(new DisplayChanges.Name<>(reference, Global.getDate(),name));
        }
        public void internalID(String id) {
            this.displayId = id;
        }
        public void internalDescription(String description) {
            this.description = description;
        }
        public DMEReference<T> getReference() {
            return reference;
        }
}
