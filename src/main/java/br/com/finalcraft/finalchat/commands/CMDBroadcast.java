package br.com.finalcraft.finalchat.commands;


import br.com.finalcraft.evernifecore.argumento.MultiArgumentos;
import br.com.finalcraft.evernifecore.commands.finalcmd.annotations.Arg;
import br.com.finalcraft.evernifecore.commands.finalcmd.annotations.FinalCMD;
import br.com.finalcraft.evernifecore.fancytext.FancyText;
import br.com.finalcraft.evernifecore.util.FCColorUtil;
import br.com.finalcraft.finalchat.PermissionNodes;
import org.bukkit.command.CommandSender;

public class CMDBroadcast {

    @FinalCMD(
            aliases = {"fbroadcast","broadcast"},
            permission = PermissionNodes.COMMAND_BROADCAST
    )
    public void broadcast(CommandSender sender, @Arg(name = "<msg>") String message, MultiArgumentos argumentos) {
        message = argumentos.joinStringArgs(1);

        FancyText.of(
                FCColorUtil.colorfy(message)
        ).broadcast();
    }
}
