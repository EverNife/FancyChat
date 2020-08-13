package br.com.finalcraft.fancychat.commands;


import br.com.finalcraft.evernifecore.argumento.MultiArgumentos;
import br.com.finalcraft.fancychat.FCBukkitUtil;
import br.com.finalcraft.fancychat.PermissionNodes;
import br.com.finalcraft.fancychat.util.MuteUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CMDMuteAll implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        mute(label,sender,args);
        return true;
    }

    // -----------------------------------------------------------------------------------------------------------------------------//
    // Command Mute
    // -----------------------------------------------------------------------------------------------------------------------------//
    public void mute(String label, CommandSender sender, String[] args){

        if (!FCBukkitUtil.hasThePermission(sender, PermissionNodes.commandMute)){
            return;
        }

        MultiArgumentos argumentos = new MultiArgumentos(args);

        if (argumentos.get(0).isEmpty()){
            sender.sendMessage("§6§l ▶ §a/" + label + " <on|off>");
            return;
        }

        Boolean bolean = argumentos.get(0).getBoolean();

        if (bolean == null){
            sender.sendMessage("§cErro de parametros!");
            sender.sendMessage("§6§l ▶ §a/" + label + " <on|off>");
            return;
        }
        if (MuteUtil.toggleGlobalMute(bolean)){
            sender.sendMessage("§a§lGlobalMute foi ativado com sucesso!");
        }else {
            sender.sendMessage("§e§lGlobalMute foi desativado com sucesso!");
        }
        return;
    }

    // I took this from here "https://github.com/DevLeoko/AdvancedBan/blob/f3c1c3b3fee957032ecd14399be83d03cfa1a906/src/main/java/me/leoko/advancedban/manager/TimeManager.java"
    public static long toMilliSec(String s) {
        // This is not my regex :P | From: http://stackoverflow.com/a/8270824
        String[] sl = s.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
        long i = Long.parseLong(sl[0]);
        if (sl.length == 1){
            return i;
        }
        switch (sl[1]) {
            case "s":
                return i * 1000;
            case "m":
                return i * 1000 * 60;
            case "h":
                return i * 1000 * 60 * 60;
            case "d":
                return i * 1000 * 60 * 60 * 24;
            case "w":
                return i * 1000 * 60 * 60 * 24 * 7;
            case "mo":
                return i * 1000 * 60 * 60 * 24 * 30;
            default:
                return -1;
        }
    }
}
