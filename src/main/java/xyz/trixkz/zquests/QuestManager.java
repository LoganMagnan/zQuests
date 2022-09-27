package xyz.trixkz.zquests;

import xyz.trixkz.packets.QuestLevel;
import xyz.trixkz.packets.QuestType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QuestManager {

    private ZQuests main;

    Map<QuestType, List<QuestLevel>> questLevels;

    public QuestManager(ZQuests main) {
        this.main = main;
    }

    public ZQuests getMain() {
        return main;
    }

    public void setQuestLevels(Map<QuestType, List<QuestLevel>> questLevels) {
        if (this.questLevels != null) {
            return;
        }

        this.questLevels = questLevels;
    }

    public List<QuestLevel> getQuestLevels() {
        QuestType questType = QuestType.getBaseQuest();

        List<QuestLevel> newList = new ArrayList<>(questLevels.get(questType));

        do {
            questType = questType.getNextQuest();
            newList.addAll(questLevels.get(questType));
        } while (questType.hasNextQuest());

        return newList;
    }
}
