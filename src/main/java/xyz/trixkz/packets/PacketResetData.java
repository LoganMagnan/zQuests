package xyz.trixkz.packets;

import java.util.UUID;

public class PacketResetData extends Packet {

    private String levelName;

    public PacketResetData(String levelName, UUID uuid) {
        super(uuid);

        this.levelName = levelName;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }
}
