package xyz.trixkz.packets;

import java.util.UUID;

public class PacketResetLevels extends Packet {

    private String levelName;

    public PacketResetLevels(String levelName, UUID uuid) {
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
