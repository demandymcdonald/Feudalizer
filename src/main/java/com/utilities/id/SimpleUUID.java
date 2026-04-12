package com.utilities.id;

import java.util.UUID;

public class SimpleUUID implements UUIDIdentifiable{
    UUID uuid;
    public SimpleUUID(UUID uuid) {
        this.uuid = uuid;
    }
    @Override
    public UUID getID() {
        return uuid;
    }
}
