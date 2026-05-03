package com.base.datemutable.utilities;

import com.Global;
import com.Global.*;

import java.time.LocalDate;
import java.util.UUID;

public abstract class TLSynced implements TimelineSynced {
    private final UUID id;
    public TLSynced(UUID id){
        this.id = id;
        registerListener();
    }


}
