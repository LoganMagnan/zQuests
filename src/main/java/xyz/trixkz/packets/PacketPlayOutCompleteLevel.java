package xyz.trixkz.packets;

import java.util.UUID;

public class PacketPlayOutCompleteLevel extends PacketCompleteLevel {

    public PacketPlayOutCompleteLevel(QuestLevel questLevel, UUID uuid) {
        super(questLevel, uuid);
    }
}
