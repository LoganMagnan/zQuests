package xyz.trixkz.zquests;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.trixkz.packets.PacketCompleteLevel;
import xyz.trixkz.packets.QuestLevel;
import xyz.trixkz.packets.QuestType;
import java.util.HashMap;
import java.util.UUID;

public class UserManager implements Listener {

    private ZQuests main;

    private HashMap<UUID, User> users = new HashMap<UUID, User>();

    public UserManager(ZQuests main) {
        this.main = main;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        users.put(player.getUniqueId(), new User(this, player));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        users.remove(player.getUniqueId());
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        if (e.getEntity().getKiller() == null) return;
        Player p = e.getEntity().getKiller();
        User user = main.getUserManager().getUser(p.getUniqueId());
        if (user.getQuestType() != QuestType.KILLS) {
            return;
        }
        checkQuest(QuestType.KILLS, 1, user);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (e.getFrom().getBlock().equals(e.getTo().getBlock())) {
            return;
        }
        Player p = e.getPlayer();
        User user = main.getUserManager().getUser(p.getUniqueId());
        if (user.getQuestType() != QuestType.WALKING) {
            return;
        }
        user.addCumulativeProgress(e.getTo().distance(e.getFrom()));
        if (user.getCumulativeProgress() >= 30) {
            checkQuest(QuestType.WALKING, (int) user.getCumulativeProgress(), user);
            user.setCumulativeProgress(user.getCumulativeProgress() - (int) user.getCumulativeProgress());
        }
    }


    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        Player p = (Player) e.getDamager();
        User user = main.getUserManager().getUser(p.getUniqueId());
        if (user.getQuestType() != QuestType.DAMAGE_DEALT) {
            return;
        }
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
            checkQuest(QuestType.DAMAGE_DEALT, (int) e.getFinalDamage(), user);
        }
    }

    public void checkQuest(QuestType type, int progress, User user) {
        if (user.hasCompletedLevel()) {
            return;
        }
        if (user.getQuestType() == type) {
            user.addProgress(progress);
            QuestLevel level = user.getQuestLevel();
            if (user.checkQuestProgress()) {
                user.setCompletedLevel(true);
                main.getUtils().completeLevel(new PacketCompleteLevel(level, user.getPlayer().getUniqueId()));
            }
        }
    }

    public User getUser(UUID uuid) {
        return users.get(uuid);
    }

    public ZQuests getZQuests() {
        return main;
    }
}
