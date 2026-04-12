package com.base.timeline.change.multi;

import com.Global.*;
import com.utilities.id.Identifiable;

public class ListPos implements Identifiable<Integer> {
    public final int position;

    public ListPos(int position) {
        this.position = position;
    }

    @Override
    public Integer getID() {
        return position;
    }
}
