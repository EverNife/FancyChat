package br.com.finalcraft.finalchat.commands;

import br.com.finalcraft.evernifecore.commands.finalcmd.FinalCMDManager;
import br.com.finalcraft.evernifecore.commands.finalcmd.argument.ArgParserManager;
import br.com.finalcraft.finalchat.commands.argparser.ArgParserFancyChannel;
import br.com.finalcraft.finalchat.config.fancychat.FancyChannel;
import br.com.finalcraft.finalchat.config.fancychat.FancyChannelController;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class CommandRegisterer {

    private static final List<String> registeredCommands = new ArrayList<>();

    public static void registerCommands(JavaPlugin pluginInstance, boolean firstLoad) {

        if (firstLoad){
            //Add Parsers
            ArgParserManager.addGlobalParser(FancyChannel.class, ArgParserFancyChannel.class);

            //Add Commands

            FinalCMDManager.registerCommand(pluginInstance,CoreCommand.class);
            pluginInstance.getCommand("tell").setExecutor(new CMDTell());
            FinalCMDManager.registerCommand(pluginInstance, CMDChannelLock.class);
            pluginInstance.getCommand("muteall").setExecutor(new CMDMuteAll());
            FinalCMDManager.registerCommand(pluginInstance, CMDBroadcast.class);
            FinalCMDManager.registerCommand(pluginInstance, CMDFancyMessage.class);
        }

        for (String registeredCommand : registeredCommands) {
            FinalCMDManager.unregisterCommand(registeredCommand, pluginInstance);
        }

        for (FancyChannel fancyChannel : FancyChannelController.getAllChannels()) {
            registeredCommands.add(fancyChannel.getName()); //Add for removal in case os reload
            FinalCMDManager.registerCommand(pluginInstance, new CMDInChannel(fancyChannel));
        }
    }

}
