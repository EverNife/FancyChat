package br.com.finalcraft.finalchat.util;

import br.com.finalcraft.finalchat.config.fancychat.FancyChannel;
import br.com.finalcraft.finalchat.config.fancychat.FancyChannelController;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ChannelManager {

    public static Map<Player, FancyChannel> playerLockFancyChannelMap = new HashMap<Player, FancyChannel>();
    public static Map<Player, FancyChannel> playerTempChannel = new HashMap<Player, FancyChannel>();

    public static void setTempChannel(Player player,FancyChannel channel){
        playerTempChannel.put(player,channel);
    }

    public static FancyChannel getTempChannel(Player player){
        return playerTempChannel.remove(player);
    }

    public static FancyChannel getPriorityChannel(Player player){
        FancyChannel fancyChannel = ChannelManager.getTempChannel(player);
        if (fancyChannel == null){
            fancyChannel = ChannelManager.getPlayerLockChannel(player);
        }
        return fancyChannel;
    }

    public static void refresh(){
        playerLockFancyChannelMap.clear();
        for (Player player : Bukkit.getOnlinePlayers()){
            for (FancyChannel fancyChannel : FancyChannelController.getAllChannels()){
                if (PermUtil.hasChannelPermission(player,fancyChannel)){
                    fancyChannel.addMember(player);
                }
            }
        }
    }

    public static void playerJoined(Player player){
        for (FancyChannel fancyChannel : FancyChannelController.getAllChannels()){
            if (PermUtil.hasChannelPermission(player,fancyChannel)){
                fancyChannel.addMember(player);
            }
        }
        FancyChannel playerLockedChannel = playerLockFancyChannelMap.getOrDefault(player, FancyChannelController.GLOBAL_CHANNEL);
        playerLockFancyChannelMap.put(player,playerLockedChannel);
    }

    public static void playerLeaved(Player player){
        for (FancyChannel fancyChannel : FancyChannelController.getAllChannels()){
            fancyChannel.removeMember(player);
        }
    }

    public static void setPlayerLockChannel(Player player, FancyChannel fancyChannel){
        playerLockFancyChannelMap.put(player,fancyChannel);
    }

    public static FancyChannel getPlayerLockChannel(Player player){
        FancyChannel fancyChannel = playerLockFancyChannelMap.get(player);
        return fancyChannel != null ? fancyChannel : FancyChannelController.DEFAULT_CHANNEL;
    }
}
