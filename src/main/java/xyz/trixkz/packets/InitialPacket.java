package xyz.trixkz.packets;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class InitialPacket extends Packet {

    Map<QuestType, List<QuestLevel>> questLevels;

    public InitialPacket(Map<QuestType, List<QuestLevel>> questLevels, UUID uuid) {
        super(uuid);
        this.questLevels = questLevels;
    }

    public Map<QuestType, List<QuestLevel>> getQuestLevels() {
        return questLevels;
    }
}
