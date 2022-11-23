package br.com.finalcraft.finalchat.commands;


import br.com.finalcraft.evernifecore.argumento.MultiArgumentos;
import br.com.finalcraft.evernifecore.commands.finalcmd.annotations.Arg;
import br.com.finalcraft.evernifecore.commands.finalcmd.annotations.FinalCMD;
import br.com.finalcraft.evernifecore.fancytext.FancyText;
import br.com.finalcraft.evernifecore.util.FCColorUtil;
import br.com.finalcraft.finalchat.PermissionNodes;
import org.bukkit.entity.Player;

public class CMDFancyMessage {

    @FinalCMD(
            aliases = {"fancymessage","fmessage","fmsg","umsg","pbroadcast"},
            permission = PermissionNodes.COMMAND_FANCYMESSAGE
    )
    public void fancyMessage(MultiArgumentos argumentos, @Arg(name = "<Player>") Player target, @Arg(name = "<msg>") String message) {
        message = argumentos.joinStringArgs(1);

        FancyText.of(
                FCColorUtil.colorfy(message)
        ).send(target);
    }
}
