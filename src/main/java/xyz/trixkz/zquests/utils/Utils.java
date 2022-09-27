package xyz.trixkz.zquests.utils;

import org.bukkit.Sound;
import org.bukkit.entity.Player;import xyz.trixkz.zquests.ZQuests;
import xyz.trixkz.packets.Packet;
import xyz.trixkz.packets.PacketCompleteLevel;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Utils {

    private ZQuests pl;

    public Utils(ZQuests pl) {
        this.pl = pl;
    }

    public void completeLevel(PacketCompleteLevel packetCompleteLevel) {
        pl.getServer().getPlayer(packetCompleteLevel.getUUID()).playSound(pl.getServer().getPlayer(packetCompleteLevel.getUUID()).getLocation(), Sound.LEVEL_UP, 10.0f, 1.0f);

        send(pl.getServer().getPlayer(packetCompleteLevel.getUUID()), packetCompleteLevel);
    }

    public void send(Player p, Packet packet) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        ObjectOutputStream out;

        byte[] data;

        try {
            out = new ObjectOutputStream(b);
            out.writeUTF("packets");
            out.writeObject(packet);
            out.flush();

            data = b.toByteArray();
        } catch (IOException exception) {
            exception.printStackTrace();
            return;
        } finally {
            try {
                b.close();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        p.sendPluginMessage(ZQuests.getPlugin(ZQuests.class), "my:packets", data);
    }
}
