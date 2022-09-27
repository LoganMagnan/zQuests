package xyz.trixkz.packets;

import java.util.UUID;

public class QuestPacket extends Packet {

    private QuestType questType;
    private QuestLevel questLevel;
    private int progress;

    public QuestPacket(QuestType questType, QuestLevel questLevel, int progress, UUID uuid) {
        super(uuid);

        this.questType = questType;
        this.questLevel = questLevel;
        this.progress = progress;
    }

    public QuestType getQuestType() {
        return questType;
    }

    public QuestLevel getQuestLevel() {
        return questLevel;
    }

    public int getProgress() {
        return progress;
    }
}
