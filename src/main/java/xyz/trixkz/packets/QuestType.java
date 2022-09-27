package xyz.trixkz.packets;

import java.io.Serializable;

public enum QuestType implements Serializable {

    DAMAGE_DEALT(null),
    WALKING(DAMAGE_DEALT),
    PLAYTIME(WALKING),
    KILLS(PLAYTIME);

    public static QuestType getBaseQuest() {
        return QuestType.KILLS;
    }

    private QuestType next;

    private QuestType(QuestType next) {
        this.next = next;
    }

    public boolean hasNextQuest() {
        return next != null;
    }

    public QuestType getNextQuest() {
        return next;
    }
}
