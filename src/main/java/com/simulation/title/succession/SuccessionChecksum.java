package com.simulation.title.succession;

import com.base.reference.DMEReference;
import com.simulation.people.BookCharacter;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;
import java.util.zip.Checksum;

public record SuccessionChecksum(int entries, long checksum) {

    public static SuccessionChecksum of(DMEReference<BookCharacter>... container) {
        List<UUID> ids = new ArrayList<>();
        for (DMEReference<BookCharacter> ref : container) {
            ids.add(ref.getUuid());
        }
        return of(ids.toArray(UUID[]::new));
    }
    public static SuccessionChecksum of(Collection<BookCharacter> container) {
        List<UUID> ids = new ArrayList<>();
        for (BookCharacter ref : container) {
            ids.add(ref.getId());
        }
        return of(ids.toArray(UUID[]::new));
    }
    public static SuccessionChecksum of(List<BookCharacter> characters) {
        List<UUID> uuids = characters.stream().map(BookCharacter::getId).toList();
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
