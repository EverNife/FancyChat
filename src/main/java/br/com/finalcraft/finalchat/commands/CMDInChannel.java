package br.com.finalcraft.finalchat.commands;


import br.com.finalcraft.evernifecore.argumento.MultiArgumentos;
import br.com.finalcraft.evernifecore.commands.finalcmd.ICustomFinalCMD;
import br.com.finalcraft.evernifecore.commands.finalcmd.annotations.FinalCMD;
import br.com.finalcraft.evernifecore.commands.finalcmd.executor.CustomizeContext;
import br.com.finalcraft.evernifecore.util.FCScheduller;
import br.com.finalcraft.finalchat.config.fancychat.FancyChannel;
import br.com.finalcraft.finalchat.util.ChannelManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class CMDInChannel implements ICustomFinalCMD {

    private final FancyChannel fancyChannel;

    public CMDInChannel(FancyChannel fancyChannel) {
        this.fancyChannel = fancyChannel;
    }

    @Override
    public void customize(@NotNull CustomizeContext context) {
        context.getFinalCMDData()
                .permission(fancyChannel.getPermission())
                .labels(
                        new String[]{fancyChannel.getName(), fancyChannel.getAlias()}
                );
    }

    @FinalCMD(
            aliases = "",
            usage = "[msg]"
    )
    public void inChannel(Player player, MultiArgumentos argumentos){

        if (argumentos.get(0).isEmpty()){
            ChannelManager.setPlayerLockChannel(player, fancyChannel);
            player.sendMessage("§6§l ▶ §aCanal §e[" + fancyChannel.getName() + "]§a definido como padrão!");
            return;
        }

        ChannelManager.setTempChannel(player,fancyChannel);
        Set<Player> onlinePlayer = new HashSet(Bukkit.getOnlinePlayers());
        AsyncPlayerChatEvent event = new AsyncPlayerChatEvent(true, player, argumentos.joinStringArgs(), onlinePlayer);

        FCScheduller.runAssync(() -> Bukkit.getServer().getPluginManager().callEvent(event));
    }
}
