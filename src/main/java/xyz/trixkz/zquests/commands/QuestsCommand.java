package xyz.trixkz.zquests.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.trixkz.zquests.ZQuests;
import xyz.trixkz.zquests.utils.InventoryBuilder;

public class QuestsCommand implements CommandExecutor {

    private ZQuests main;

    public QuestsCommand(ZQuests main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("zquests.gui")) {
                InventoryBuilder invBuilder = new InventoryBuilder(main);
                invBuilder.openQuestsLobbyGUI(p);
            } else {
                p.sendMessage(ZQuests.msg(main.getConfig().getString("messages.permission-message")));
            }
        } else {
            if (args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                InventoryBuilder invBuilder = new InventoryBuilder(main);
                invBuilder.openQuestsLobbyGUI(target);
            }
        }
        return true;
    }
}
