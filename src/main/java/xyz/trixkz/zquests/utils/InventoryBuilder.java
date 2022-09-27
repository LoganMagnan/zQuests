package xyz.trixkz.zquests.utils;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.trixkz.packets.QuestLevel;
import xyz.trixkz.zquests.User;
import xyz.trixkz.zquests.ZQuests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class InventoryBuilder {

    private ZQuests pl;

    public InventoryBuilder(ZQuests pl) {
        this.pl = pl;
    }

    public void openQuestsLobbyGUI(Player p) {
        // Create inv
        Inventory gui = Bukkit.createInventory(null, 9, ZQuests.msg(pl.getConfig().getString("gui.gui-names.main-menu")));

        // Lores
        List<String> lore = new ArrayList<>();
        lore.add(ZQuests.msg("&7Click here to see all of the challenges"));

        // Itemstacks
        ItemStack dye = new ItemStack(Material.INK_SACK, 1, DyeColor.PURPLE.getWoolData());
        ItemMeta meta = dye.getItemMeta();
        meta.setLore(lore);
        meta.setDisplayName(ZQuests.msg(pl.getConfig().getString("gui.item-names.proceed-to-levels")));
        dye.setItemMeta(meta);

        // Set items
        gui.setItem(4, dye);

        // Open GUI
        p.openInventory(gui);
    }
    public void openQuestsGUI(Player p) {
        // Create gui
        Inventory gui = Bukkit.createInventory(null, 54, ZQuests.msg(pl.getConfig().getString("gui.gui-names.levels-menu")));

        List<Integer> openSlots = calculateDynamicSlots(pl.getQuestManager().getQuestLevels().size());
        Iterator<Integer> slotsIterator = openSlots.iterator();

        // Add items
        for (QuestLevel questLevel : pl.getQuestManager().getQuestLevels()) {
            if (!slotsIterator.hasNext()) {
                break;
            }

            User user = pl.getUserManager().getUser(p.getUniqueId());

            ItemStack item;
            List<String> itemLore = new ArrayList<>();
            ItemMeta itemMeta;

            itemLore.add(questLevel.getRewardDescription());

            if (user.isLevelCompleted(questLevel)) {
                item = new ItemStack(Material.INK_SACK, 1, DyeColor.PURPLE.getWoolData());
                itemMeta = item.getItemMeta();
                itemMeta.setDisplayName(questLevel.getTitle());
                itemLore.add(ZQuests.msg("&aCompleted"));
            } else if (questLevel.equals(user.getQuestLevel())) {
                item = new ItemStack(Material.INK_SACK,1, DyeColor.LIME.getWoolData());
                itemMeta = item.getItemMeta();
                itemMeta.setDisplayName(questLevel.getTitle());
                itemLore.add(ZQuests.msg("&dIn progress.."));
                itemLore.add(ZQuests.msg("&bProgress: &f" + user.getTotalProgress()));
            } else {
                item = new ItemStack(Material.INK_SACK, 1, DyeColor.SILVER.getWoolData());
                itemMeta = item.getItemMeta();
                itemMeta.setDisplayName(questLevel.getTitle());
                itemLore.add(ZQuests.msg("&7To be completed"));
            }

            itemMeta.setLore(itemLore);
            item.setItemMeta(itemMeta);

            int slot = slotsIterator.next();

            gui.setItem(slot, item);
        }

        p.openInventory(gui);
    }

    private int indexSlots = 54;

    private List<Integer> dynamicSlots = new ArrayList<>();

    /**
     * Calculate the next set of dynamic slots based on the current index.
     * @param maxSlots The total amount of slots allowed in the dynamic page.
     * @param indexSlots The amount of slots allowed in this specific page index.
     * @return The list of dynamic slots in the current page index.
     */
    private static final List<Integer> genericRow = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
    public List<Integer> calculateDynamicSlots(int maxSlots) {
        dynamicSlots.clear();
        int rows = 4;
        int dynamicRemainder = (maxSlots);

        int indexTotal = 0;
        for (int currentRow = 0; currentRow < rows; currentRow++) {
            for (int slot: indexTotal + 7 > dynamicRemainder ? getRowLayout(dynamicRemainder - indexTotal)
                    : indexTotal + 7 > indexSlots ? getRowLayout(indexSlots - indexTotal) : genericRow) {
                indexTotal++;
                dynamicSlots.add(slot + (9 * (currentRow+1)));
            }
            if (indexTotal >= dynamicRemainder || indexTotal == indexSlots) {
                break;
            }
        }
        return dynamicSlots;
    }

    /**
     *
     * @param remainder The number of slots in the row.
     * @return The centered layout for the items in the row.
     */
    public static List<Integer> getRowLayout(int remainder) {
        switch (remainder) {
            case 1:
                return Arrays.asList(4);
            case 2:
                return Arrays.asList(3, 5);
            case 3:
                return Arrays.asList(3, 4, 5);
            case 4:
                return Arrays.asList(2, 3, 5, 6);
            case 5:
                return Arrays.asList(2, 3, 4, 5, 6);
            case 6:
                return Arrays.asList(1, 2, 3, 5, 6, 7);
            default:
                return Arrays.asList();
        }
    }
}
