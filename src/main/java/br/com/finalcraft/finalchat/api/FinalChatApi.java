package br.com.finalcraft.finalchat.api;

import br.com.finalcraft.finalchat.config.fancychat.FancyChannel;
import br.com.finalcraft.finalchat.config.fancychat.FancyChannelController;
import br.com.finalcraft.finalchat.util.ChannelManager;
import br.com.finalcraft.finalchat.util.MuteUtil;
import org.bukkit.entity.Player;

import java.util.Collection;

public class FinalChatApi {

    public static boolean isMainChannel(FancyChannel fancyChannel) {return FancyChannelController.GLOBAL_CHANNEL == fancyChannel;}

    public static FancyChannel getChannel(String name){
        return FancyChannelController.getFancyChannel(name);
    }

    public static Collection<FancyChannel> getAllChannels(){
        return FancyChannelController.getAllChannels();
    }

    public static FancyChannel getPlayerChannel(Player player){
        return ChannelManager.getPlayerLockChannel(player);
    }

    public static void mutePlayer(String playerName, long millis){
        MuteUtil.mutePlayer(playerName,millis);
    }

    public static void sendMessage(String message, FancyChannel fancyChannel){
        for (Player player : fancyChannel.getPlayersOnThisChannel()){
            player.sendMessage(message);
        }
    }


}
