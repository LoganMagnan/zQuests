package xyz.trixkz.zquests.utils;

import com.google.common.base.Charsets;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginAwareness;
import xyz.trixkz.zquests.ZQuests;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class Config {

    private final Plugin plugin = ZQuests.getPlugin(ZQuests.class);
    public File configFile;
    public FileConfiguration config;
    private final String configurationName;

    public Config(String configName) {
        configurationName = configName;
        createConfigFile();
        saveConfig();
    }
    public FileConfiguration getConfig() {
        return config;
    }

    private void createConfigFile() {
        configFile = new File(plugin.getDataFolder(), configurationName);
        if (!configFile.exists()) {
            log("&aFailed to find '{NAME}'. Creating file &7(All settings will be set to default)".replace("{NAME}", configurationName));
            configFile.getParentFile().mkdirs();
            plugin.saveResource(configurationName, false);
        }

        config = new YamlConfiguration();
        try {
            config.load(configFile);
        }catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
    public void saveConfig() {
        try {
            getConfig().save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reload() {
        final InputStream defConfigStream = plugin.getResource("config.yml");
        if (defConfigStream == null) {
            return;
        }

        final YamlConfiguration defConfig;
        if (plugin.getDescription().getAwareness().contains(PluginAwareness.Flags.UTF8) || FileConfiguration.UTF8_OVERRIDE) {
            defConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream, Charsets.UTF_8));
        } else {
            final byte[] contents;
            defConfig = new YamlConfiguration();
            try {
                contents = ByteStreams.toByteArray(defConfigStream);
            } catch (final IOException e) {
                log("Unexpected error");
                return;
            }

            final String text = new String(contents, Charset.defaultCharset());
            if (!text.equals(new String(contents, Charsets.UTF_8))) {
                log("Default system encoding may have misread config.yml from plugin jar");
            }

            try {
                defConfig.loadFromString(text);
            } catch (final InvalidConfigurationException e) {
                log("Cannot load configuration from jar");
            }
        }

        config.setDefaults(defConfig);
    }

    // Util methods
    private String msg(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
    private void log(String message) {
        Bukkit.getServer().getConsoleSender().sendMessage(msg(message));
    }
}
