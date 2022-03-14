package br.com.finalcraft.finalchat.util;

import br.com.finalcraft.finalchat.config.fancychat.FancyChannel;
import org.bukkit.entity.Player;

public class PermUtil {

    public static boolean hasChannelPermission(Player player, FancyChannel fancyChannel){
        if (fancyChannel.getPermission().isEmpty()){
            return true;
        }
        return player.hasPermission(fancyChannel.getPermission());
    }

}
