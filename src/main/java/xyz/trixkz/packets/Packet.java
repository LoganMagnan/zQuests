package xyz.trixkz.packets;

import java.io.Serializable;
import java.util.UUID;

public abstract class Packet implements Serializable {

    private UUID uuid;

    public Packet(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUUID() {
        return uuid;
    }
}
