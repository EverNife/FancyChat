package br.com.finalcraft.finalchat.commands;


import br.com.finalcraft.evernifecore.util.FCBukkitUtil;
import br.com.finalcraft.finalchat.FinalChat;
import br.com.finalcraft.finalchat.config.fancychat.FancyChannel;
import br.com.finalcraft.finalchat.config.fancychat.FancyChannelController;
import br.com.finalcraft.finalchat.util.ChannelManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CMDInChannel implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        inChannel(label,sender,args);
        return true;
    }

    // -----------------------------------------------------------------------------------------------------------------------------//
    // Command InChannel
    // -----------------------------------------------------------------------------------------------------------------------------//
    public static boolean inChannel(String label, CommandSender sender, String[] args){

        if (FCBukkitUtil.isNotPlayer(sender)){
            return true;
        }

        Player player = (Player) sender;

        List<FancyChannel> allFancyChannels = new ArrayList<FancyChannel>(FancyChannelController.mapOfFancyChannels.values());
        List<String> allAliases = new ArrayList<String>();
        allFancyChannels.forEach(fancyChannel -> {
            allAliases.add(fancyChannel.getAlias().toLowerCase());
        });

        FancyChannel fancyChannel = null;
        int amoutOfChannels = allFancyChannels.size();
        for (int i = 0; i < amoutOfChannels; i++){
            if (allAliases.get(i).equals(label)){
                fancyChannel = allFancyChannels.get(i);
                break;
            }
        }

        if (!fancyChannel.getPermission().isEmpty()){
            if (!FCBukkitUtil.hasThePermission(player,fancyChannel.getPermission())){
                return true;
            }
        }

        String msg = String.join(" ", args).trim();

        if (msg.isEmpty()){
            ChannelManager.setPlayerLockChannel(player,fancyChannel);
            sender.sendMessage("§6§l ▶ §aCanal §e[" + fancyChannel.getName() + "]§a definido como padrão!");
            return true;
        }

        ChannelManager.setTempChannel(player,fancyChannel);
        Set<Player> onlinePlayer = new HashSet(Bukkit.getOnlinePlayers());
        AsyncPlayerChatEvent event = new AsyncPlayerChatEvent(true, player, msg, onlinePlayer);
        new BukkitRunnable(){
            @Override
            public void run() {
                Bukkit.getServer().getPluginManager().callEvent(event);
            }
        }.runTaskAsynchronously(FinalChat.instance);
        return true;
    }
}
