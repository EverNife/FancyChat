package br.com.finalcraft.finalchat.commands;

import br.com.finalcraft.evernifecore.commands.finalcmd.FinalCMDManager;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandRegisterer {


    public static CMDInChannel cmdInChannel = new CMDInChannel();

    public static void registerCommands(JavaPlugin pluginInstance) {
        pluginInstance.getCommand("chat").setExecutor(new CoreCommand());
        pluginInstance.getCommand("tell").setExecutor(new CMDTell());
        pluginInstance.getCommand("channellock").setExecutor(new CMDChannelLock());
        pluginInstance.getCommand("muteall").setExecutor(new CMDMuteAll());
        FinalCMDManager.registerCommand(pluginInstance, CMDBroadcast.class);
        FinalCMDManager.registerCommand(pluginInstance, CMDFancyMessage.class);
    }

}
