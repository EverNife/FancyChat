package br.com.finalcraft.finalchat.commands;

import br.com.finalcraft.evernifecore.argumento.MultiArgumentos;
import br.com.finalcraft.evernifecore.commands.finalcmd.annotations.Arg;
import br.com.finalcraft.evernifecore.commands.finalcmd.annotations.FinalCMD;
import br.com.finalcraft.evernifecore.util.FCMessageUtil;
import br.com.finalcraft.finalchat.FinalChat;
import br.com.finalcraft.finalchat.PermissionNodes;
import br.com.finalcraft.finalchat.config.ConfigManager;
import br.com.finalcraft.finalchat.util.messages.SpyMessage;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@FinalCMD(
        aliases = {"fancychat","fchat", "chat"},
        helpHeader = "§6(  §a§lFancyChat§e  §6)§m"
)
public class CoreCommand {

    @FinalCMD.SubCMD(
            subcmd = "spy",
            permission = PermissionNodes.COMMAND_SPY
    )
    public void spy(Player player, String label, MultiArgumentos argumentos, @Arg(name = "[ON|OFF]") String operation, @Arg(name = "[color]") String color){

        if (operation == null){
            player.sendMessage("§6§l ▶ §aChatSpy Enabled: " + SpyMessage.isSpying(player));
            return;
        }

        if (color != null){
            color = "§7";
        }

        color = ChatColor.translateAlternateColorCodes('&',color);

        switch (argumentos.get(1).toLowerCase()){
            case "on":
                player.sendMessage("§6§l ▶ §aChatSpy Ativado!");
                SpyMessage.changeSpyState(player,color,true);
                return;
            case "off":
                player.sendMessage("§6§l ▶ §eChatSpy Desativado!");
                SpyMessage.changeSpyState(player,color,false);
                return;
        }
    }


    @FinalCMD.SubCMD(
            subcmd = "reload",
            permission = PermissionNodes.COMMAND_RELOAD
    )
    public void reload(CommandSender sender){
        ConfigManager.initialize(FinalChat.instance);
        CommandRegisterer.registerCommands(FinalChat.instance, false);
        FCMessageUtil.pluginHasBeenReloaded(sender, FinalChat.instance.getName());
    }
}
