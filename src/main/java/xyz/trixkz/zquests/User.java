package xyz.trixkz.zquests;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import xyz.trixkz.packets.QuestLevel;
import xyz.trixkz.packets.QuestPacket;
import xyz.trixkz.packets.QuestType;

public class User {

    private UserManager manager;

    private Player player;

    private QuestType questType;
    private QuestLevel questLevel;
    private int progress;
    private boolean completedLevel;

    private double cumulativeProgress;

    private BukkitTask playtime;

    public User(UserManager manager, Player player) {
        this.manager = manager;
        this.player = player;
    }

    public QuestType getQuestType() {
        return questType;
    }

    public void setQuestType(QuestType questType) {
        this.questType = questType;

        if (questType == QuestType.PLAYTIME) {
            playtime = Bukkit.getScheduler().runTaskTimer(manager.getZQuests(), () -> {
                manager.checkQuest(QuestType.PLAYTIME, 1, this);
            }, 1200, 1200);
        } else if (playtime != null) {
            playtime.cancel();

            playtime = null;
        }
    }

    public QuestLevel getQuestLevel() {
        return questLevel;
    }

    public void setQuestLevel(QuestLevel questLevel) {
        this.questLevel = questLevel;
    }

    public int getProgress() {
        return progress;
    }

    public int getTotalProgress() {
        return progress + (int) cumulativeProgress;
    }

    public void setProgress(int progress) {
        this.progress = progress;

        saveProgress();
    }

    public void addProgress(int progress) {
        this.progress += progress;

        saveProgress();
    }

    public void saveProgress() {
        manager.getZQuests().getUtils().send(player, new QuestPacket(questType, questLevel, getProgress(), player.getUniqueId()));
    }

    public void setCumulativeProgress(double cumulativeProgress) {
        this.cumulativeProgress = cumulativeProgress;
    }

    public double getCumulativeProgress() {
        return cumulativeProgress;
    }

    public void addCumulativeProgress(double cumulativeProgress) {
        this.cumulativeProgress += cumulativeProgress;
    }

    public boolean hasCompletedLevel() {
        return this.completedLevel;
    }

    public void setCompletedLevel(boolean completedLevel) {
        this.completedLevel = completedLevel;

        if (completedLevel && playtime != null) {
            playtime = null;
        }
    }

    public Player getPlayer() {
        return player;
    }

    public boolean checkQuestProgress() {
        return questLevel.isCompleted(progress);
    }

    public boolean isLevelCompleted(QuestLevel questLevel) {
        if (this.questType == questLevel.getQuestType()) {
            return getQuestLevel().getIdentifier() > questLevel.getIdentifier();
        }

        QuestType currentQuestType = this.questLevel.getQuestType();

        do {
            if (currentQuestType == null) {
                break;
            }
            currentQuestType = currentQuestType.getNextQuest();
            if (currentQuestType == questLevel.getQuestType()) {
                return false;
            }
        } while (questType.hasNextQuest());

        return true;
    }
}
