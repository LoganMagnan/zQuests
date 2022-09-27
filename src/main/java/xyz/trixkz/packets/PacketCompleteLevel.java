package xyz.trixkz.packets;

import java.util.UUID;

public class PacketCompleteLevel extends Packet {

    private QuestLevel questLevel;

    public PacketCompleteLevel(QuestLevel questLevel, UUID uuid) {
        super(uuid);

        this.questLevel = questLevel;
    }

    public QuestLevel getQuestLevel() {
        return questLevel;
    }
}