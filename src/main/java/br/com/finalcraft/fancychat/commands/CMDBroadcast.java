package br.com.finalcraft.fancychat.commands;


import br.com.finalcraft.evernifecore.argumento.MultiArgumentos;
import br.com.finalcraft.evernifecore.commands.finalcmd.annotations.FinalCMD;
import br.com.finalcraft.evernifecore.commands.finalcmd.help.HelpLine;
import br.com.finalcraft.evernifecore.fancytext.FancyText;
import br.com.finalcraft.fancychat.PermissionNodes;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CMDBroadcast {

    @FinalCMD(
            aliases = {"fcbroadcast","broadcast"},
            usage = "<msg>",
            permission = PermissionNodes.COMMAND_BROADCAST
    )
    public void broadcast(CommandSender sender, MultiArgumentos argumentos, HelpLine helpLine) {

        if (argumentos.emptyArgs(0)) {
            helpLine.sendTo(sender);
            return;
        }

        FancyText.of(
                ChatColor.translateAlternateColorCodes('&',argumentos.joinStringArgs())
        ).broadcast();
    }
}
