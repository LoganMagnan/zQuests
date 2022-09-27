package xyz.trixkz.zquests;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.trixkz.zquests.commands.QuestsCommand;
import xyz.trixkz.zquests.events.*;
import xyz.trixkz.zquests.utils.Config;
import xyz.trixkz.zquests.utils.Utils;

public class ZQuests extends JavaPlugin {

    public static FileConfiguration config;
    // public static Config levelsConfig;
    public static Config data;
    private Utils utils;
    private UserManager userManager;
    private QuestManager questManager;

    @Override
    public void onEnable() {
        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(this, "my:packets");
        Bukkit.getMessenger().registerIncomingPluginChannel(this, "my:packets", new PacketListener(this));

        userManager = new UserManager(this);
        questManager = new QuestManager(this);

        utils = new Utils(this);

        initConfig();
        initCommands();

        initEvents();
    }

    private void initConfig() {
        log("&aInitiating configs");
        saveDefaultConfig();
        config = getConfig();
        log("&aInitiated config.yml");
        // levelsConfig = new Config("items.yml");
        // log("&aInitiated items.yml");
//        data = new Config("data/playerdata.yml");
//        log("&aInitiated playerdata.yml");
        log("&aAll configs initiated");
    }

    private void initCommands() {
        log("Initiating commands");
        getCommand("quests").setExecutor(new QuestsCommand(this));
        log("Finished loading commands");
    }

    private void initEvents() {
        log("Initiating events");
        getServer().getPluginManager().registerEvents(new GUIEvents(this), this);
        getServer().getPluginManager().registerEvents(userManager, this);
        log("Finished register events");
    }

    public static String msg(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void log(String message) {
        getPlugin(ZQuests.class).getServer().getConsoleSender().sendMessage(msg(message));
    }

    public Utils getUtils() {
        return utils;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public QuestManager getQuestManager() {
        return questManager;
    }
}
