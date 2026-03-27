package com.base.timeline;

import com.base.DMRegistry;
import com.base.DateMutableEntity;
import com.base.timeline.change.TimelineChange;
import com.google.gson.JsonObject;
import javafx.util.Pair;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public record TimelineChangeState<T extends DateMutableEntity<T,?>> (LocalDate start, Optional<LocalDate> end, TimelineChange<T> change){

}
