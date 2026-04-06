package com.objects.title.succession;

import com.base.reference.DMEReference;
import com.objects.character.HumanCharacter;

import java.util.*;

public record SuccessionChecksum(int entries, long checksum) {

    public static SuccessionChecksum of(DMEReference<HumanCharacter>... container) {
        List<UUID> ids = new ArrayList<>();
        for (DMEReference<HumanCharacter> ref : container) {
            ids.add(ref.getID());
        }
        return of(ids.toArray(UUID[]::new));
    }
    public static SuccessionChecksum of(Collection<HumanCharacter> container) {
        List<UUID> ids = new ArrayList<>();
        for (HumanCharacter ref : container) {
            ids.add(ref.getId());
        }
        return of(ids.toArray(UUID[]::new));
    }
    public static SuccessionChecksum of(List<HumanCharacter> characters) {
        List<UUID> uuids = characters.stream().map(HumanCharacter::getId).toList();
        return of(uuids.toArray(UUID[]::new));
    }
    public static SuccessionChecksum of(UUID... characters) {
        List<UUID> uuids = Arrays.stream(characters).sorted().toList();
        long high = 0, low = 0;
        for (UUID uuid : uuids) {
            high ^= uuid.getMostSignificantBits();
            low ^= uuid.getLeastSignificantBits();
        }
        return new SuccessionChecksum(uuids.size(), (high << 32) | low);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SuccessionChecksum cs) {
            return cs.entries() == entries() && cs.checksum() == checksum();
        }
        return false;
    }
}
