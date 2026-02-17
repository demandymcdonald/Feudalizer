package com.simulation.succession;

import com.base.AbstractMutableManager;
import com.google.gson.JsonObject;

import java.util.UUID;

public class TitleManager extends AbstractMutableManager<Title.TitleContainer,Title<?>> {
    @Override
    public Title<?> deserializer(UUID id, JsonObject json) {
        return TitleFactory.create(json);
    }
}
