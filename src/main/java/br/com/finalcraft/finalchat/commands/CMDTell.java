package br.com.finalcraft.finalchat.commands;


import br.com.finalcraft.evernifecore.argumento.MultiArgumentos;
import br.com.finalcraft.evernifecore.commands.finalcmd.annotations.Arg;
import br.com.finalcraft.evernifecore.commands.finalcmd.annotations.FinalCMD;
import br.com.finalcraft.evernifecore.util.FCMessageUtil;
import br.com.finalcraft.finalchat.util.MuteUtil;
import br.com.finalcraft.finalchat.util.messages.PrivateMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CMDTell {

    @FinalCMD(
            aliases = {"ftell","tell","whispper","t","w","msg","private"}
    )
    public void tell(Player player, MultiArgumentos argumentos, @Arg(name = "<Player>") Player target, @Arg(name = "<msg>") String message){

        if (MuteUtil.isMuted(player)){
            player.sendMessage("§cVocê está mutado!");
            player.sendMessage(MuteUtil.getMuteMessage(player));
            return;
        }

        message = argumentos.joinStringArgs(1);

        PrivateMessage.sendTell(player, target, message);
    }

    @FinalCMD(
            aliases = {"reply","responder","r"}
    )
    public void reply(Player player, MultiArgumentos argumentos, @Arg(name = "<msg>") String message){

        if (MuteUtil.isMuted(player)){
            player.sendMessage("§cVocê está mutado!");
            player.sendMessage(MuteUtil.getMuteMessage(player));
            return;
        }

        String targetName = PrivateMessage.getLastTarget(player.getName());

        if (targetName == null){
            player.sendMessage("§6§l ▶ §cVocê não recebeu nenhuma mensagem privada recentemente...");
            return;
        }

        final Player target = targetName != null ? Bukkit.getPlayer(targetName) : null;

        if (target == null || !target.isOnline()){
            FCMessageUtil.playerNotOnline(player, targetName);
            return;
        }

        message = argumentos.joinStringArgs(0);

        PrivateMessage.sendTell(player, target, message);
    }
}
