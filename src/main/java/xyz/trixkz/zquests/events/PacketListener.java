package xyz.trixkz.zquests.events;

import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import xyz.trixkz.packets.InitialPacket;
import xyz.trixkz.packets.QuestPacket;
import xyz.trixkz.zquests.User;
import xyz.trixkz.zquests.ZQuests;
import xyz.trixkz.packets.PacketPlayOutCompleteLevel;

import java.io.*;

public class PacketListener implements PluginMessageListener {

    private ZQuests main;

    public PacketListener(ZQuests main) {
        this.main = main;
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        ByteArrayInputStream bis = new ByteArrayInputStream(message);
        ObjectInput in = null;

        try {
            in = new ObjectInputStream(bis);

            if (in.readUTF().equals("packets")) {
                processPacket(in.readObject());
            }
        } catch (StreamCorruptedException streamException) {
            return;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void processPacket(Object object) {
        if (object instanceof String) {
            main.getServer().dispatchCommand(main.getServer().getConsoleSender(), (String) object);
        } else if (object instanceof PacketPlayOutCompleteLevel) {
            PacketPlayOutCompleteLevel packetPlayOutCompleteLevel = (PacketPlayOutCompleteLevel) object;

            main.getUtils().completeLevel(packetPlayOutCompleteLevel);
        } else if (object instanceof QuestPacket) {
            QuestPacket questPacket = (QuestPacket) object;

            User user = main.getUserManager().getUser(questPacket.getUUID());

            user.setQuestType(questPacket.getQuestType());
            user.setQuestLevel(questPacket.getQuestLevel());
            user.setProgress(questPacket.getProgress());
            user.setCompletedLevel(false);
        } else if (object instanceof InitialPacket) {
            InitialPacket initialPacket = (InitialPacket) object;

            main.getQuestManager().setQuestLevels(initialPacket.getQuestLevels());
        }
    }
}
