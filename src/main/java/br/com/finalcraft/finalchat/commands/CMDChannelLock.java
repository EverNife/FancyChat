package br.com.finalcraft.finalchat.commands;


import br.com.finalcraft.evernifecore.commands.finalcmd.annotations.Arg;
import br.com.finalcraft.evernifecore.commands.finalcmd.annotations.FinalCMD;
import br.com.finalcraft.evernifecore.util.FCBukkitUtil;
import br.com.finalcraft.finalchat.config.fancychat.FancyChannel;
import br.com.finalcraft.finalchat.util.ChannelManager;
import org.bukkit.entity.Player;

public class CMDChannelLock {

    @FinalCMD(
            aliases = {"channellock", "ch", "channel", "lock"}
    )
    public void lockChannel(Player player, @Arg(name = "<Channel>") FancyChannel fancyChannel){

        if (!fancyChannel.getPermission().isEmpty() &&
                !FCBukkitUtil.hasThePermission(player,fancyChannel.getPermission())){
            return;
        }

        ChannelManager.setPlayerLockChannel(player,fancyChannel);

        player.sendMessage("§6§l ▶ §aCanal §e[" + fancyChannel.getName() + "]§a definido como padrão!");
    }
}
