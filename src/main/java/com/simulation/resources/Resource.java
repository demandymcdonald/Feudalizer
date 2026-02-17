package com.simulation.resources;

public record Resource(ResourceType type, Integer abundance) {
    public Resource(ResourceType type, Integer abundance) {
        this.type = type;
        this.abundance = Math.clamp(abundance,0,10);
    }



    public Resource(String type, Integer abundance) {
        this(Resources.get(type),Math.clamp(abundance,0,10));
    }
    @Override
    public boolean equals(Object obj) {
        return  obj instanceof Resource r && r.type.equals(type);
    }
}
