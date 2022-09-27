package xyz.trixkz.packets;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class QuestLevel implements Serializable {

    private QuestType questType;
    private int identifier;
    private String label;

    private String title;
    private String rewardDescription;
    private List<String> rewardCommands = new ArrayList<>();
    private int trackingAmount;

    public QuestLevel(QuestType questType, int identifier, String label, String title, String rewardDescription, List<String> rewardCommands, int trackingAmount) {
        this.questType = questType;
        this.identifier = identifier;
        this.label = label;
        this.title = title;
        this.rewardDescription = rewardDescription;
        this.rewardCommands = rewardCommands;
        this.trackingAmount = trackingAmount;
    }

    public QuestType getQuestType() {
        return questType;
    }

    public int getIdentifier() {
        return identifier;
    }

    public String getTitle() {
        return title;
    }

    public String getRewardDescription() {
        return rewardDescription;
    }

    public List<String> getRewardCommands() {
        return rewardCommands;
    }

    public int getTrackingAmount() {
        return trackingAmount;
    }

    public boolean isCompleted(int progress) {
        return progress >= trackingAmount;
    }

    @Override
    public int hashCode() {
        int code = identifier * 12;
        return label.hashCode() + code + questType.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj.hashCode() == this.hashCode();
    }
}