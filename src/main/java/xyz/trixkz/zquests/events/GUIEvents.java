package xyz.trixkz.zquests.events;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import xyz.trixkz.zquests.ZQuests;
import xyz.trixkz.zquests.utils.InventoryBuilder;
import xyz.trixkz.zquests.utils.Utils;

import java.util.Arrays;
import java.util.List;

public class GUIEvents implements Listener {

    private ZQuests main;

    public GUIEvents(ZQuests main) {
        this.main = main;
    }

    @EventHandler
    public void clickEvent(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getClickedInventory() == null) {
            return;
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ZQuests.msg(main.getConfig().getString("gui.item-names.proceed-to-levels")))) {
            if(e.getCurrentItem() != null) {
                e.setCancelled(true);
                if(e.getCurrentItem().getType() == Material.INK_SACK) {
                    // Open gui
                    InventoryBuilder invBuilder = new InventoryBuilder(main);
                    invBuilder.openQuestsGUI(p);
                }
            }
        } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ZQuests.msg(main.getConfig().getString("gui.gui-names.levels-menu")))) {
            e.setCancelled(true);
        }
    }
}
