package br.com.finalcraft.finalchat.commands;


import br.com.finalcraft.evernifecore.commands.finalcmd.annotations.Arg;
import br.com.finalcraft.evernifecore.commands.finalcmd.annotations.FinalCMD;
import br.com.finalcraft.finalchat.PermissionNodes;
import br.com.finalcraft.finalchat.util.MuteUtil;
import org.bukkit.command.CommandSender;

public class CMDMuteAll {

    @FinalCMD(
            aliases = "muteall",
            permission = PermissionNodes.COMMAND_MUTE
    )
    public void mute(CommandSender sender, @Arg(name = "<On|Off>") Boolean mute){
        if (MuteUtil.toggleGlobalMute(mute)){
            sender.sendMessage("§a§lGlobalMute foi ativado com sucesso!");
        }else {
            sender.sendMessage("§e§lGlobalMute foi desativado com sucesso!");
        }
    }

}
