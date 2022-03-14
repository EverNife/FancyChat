package br.com.finalcraft.finalchat.commands;


import br.com.finalcraft.evernifecore.argumento.MultiArgumentos;
import br.com.finalcraft.evernifecore.commands.finalcmd.annotations.Arg;
import br.com.finalcraft.evernifecore.commands.finalcmd.annotations.FinalCMD;
import br.com.finalcraft.evernifecore.fancytext.FancyText;
import br.com.finalcraft.finalchat.PermissionNodes;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDFancyMessage {

    @FinalCMD(
            aliases = {"fancymessage","fmessage","fmsg","umsg","pbroadcast"},
            permission = PermissionNodes.COMMAND_FANCYMESSAGE
    )
    public void fancyMessage(CommandSender sender, MultiArgumentos argumentos, @Arg(name = "<Player>") Player target, @Arg(name = "<msg>") String message) {
        FancyText.of(
                ChatColor.translateAlternateColorCodes('&',argumentos.joinStringArgs(1))
        ).send(target);
    }
}
