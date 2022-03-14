package br.com.finalcraft.finalchat.commands;

import br.com.finalcraft.evernifecore.fancytext.FancyText;
import br.com.finalcraft.evernifecore.util.FCBukkitUtil;
import br.com.finalcraft.finalchat.FinalChat;
import br.com.finalcraft.finalchat.PermissionNodes;
import br.com.finalcraft.finalchat.config.ConfigManager;
import br.com.finalcraft.finalchat.util.messages.SpyMessage;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CoreCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //  Passando os argumentos para um ArrayList
        List<String> argumentos = FCBukkitUtil.parseBukkitArgsToList(args,4);

        switch (argumentos.get(0).toLowerCase()){
            case "":
            case "?":
            case "help":
                return help(label,sender,argumentos);
            case "spy":
                return spy(label,sender,argumentos);
            case "reload":
                return reload(label,sender,argumentos);

        }

        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cErro de parametros, por favor use /" + label + " help"));
        return true;
    }

    // -----------------------------------------------------------------------------------------------------------------------------//
    // Command Help
    // -----------------------------------------------------------------------------------------------------------------------------//
    public static boolean help(String label, CommandSender sender, List<String> argumentos){
        sender.sendMessage("§6§m--------------------§6(  §a§lFancyChat§e  §6)§m--------------------");
        if (sender instanceof Player){
            Player player = (Player) sender;
            FancyText.of("§3§l ▶ §a/" + label + " ignore <Player>")
                    .setHoverText("§bIgnora as mensagens de um jogador específico!")
                    .setSuggestCommandAction("/" + label + " ignore")
                    .send(player);

            FancyText.of("§3§l ▶ §a/" + label + " ignoredList")
                    .setHoverText("§bMostra todos os jogadores que você está ignorando!")
                    .setRunCommandAction("/" + label + " ignoreList")
                    .send(player);

            if (player.hasPermission(PermissionNodes.COMMAND_SPY)) {
                FancyText.of("§3§l ▶ §e/" + label + " spy [colorCode]")
                        .setHoverText("§bMostra todas as conversas do servidor. \n   Você pode usar a cor que quiser.")
                        .setRunCommandAction("/" + label + " spy")
                        .send(player);
            }
            if (player.hasPermission(PermissionNodes.COMMAND_RELOAD)){
                FancyText.of("§3§l ▶ §a/" + label + " reload")
                        .setHoverText("§bRecarrega todas as configurações do Plugin!")
                        .setRunCommandAction("/" + label + " reload")
                        .send(player);
            }
            sender.sendMessage("");
            sender.sendMessage("§3§oPasse o mouse em cima dos comandos para ver a descrição!");
        }else {
            sender.sendMessage("Esse comando só pode ser utilizado dentro do jogo!");
        }
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6&m-----------------------------------------------------"));
        return true;
    }


    // -----------------------------------------------------------------------------------------------------------------------------//
    // Command Spy
    // -----------------------------------------------------------------------------------------------------------------------------//
    public static boolean version(String label, CommandSender sender, List<String> argumentos){

        if ( !FCBukkitUtil.hasThePermission(sender,PermissionNodes.COMMAND_SPY)){
            return true;
        }

        sender.sendMessage("§aFancyChat foi recarregado!!!");
        return true;
    }


    // -----------------------------------------------------------------------------------------------------------------------------//
    // Command Spy
    // -----------------------------------------------------------------------------------------------------------------------------//
    public static boolean spy(String label, CommandSender sender, List<String> argumentos){

        if (FCBukkitUtil.isNotPlayer(sender)){
            return true;
        }

        if ( !FCBukkitUtil.hasThePermission(sender,PermissionNodes.COMMAND_SPY)){
            return true;
        }

        Player player = (Player) sender;

        switch (argumentos.get(1).toLowerCase()){
            case "":
                sender.sendMessage("§6§l ▶ §e/" + label + " spy [ON|OFF|setColor]");
                return true;
            case "off":
                sender.sendMessage("§6§l ▶ §eChatSpy Desativado!");
                SpyMessage.changeSpyState(player,"§7",false);
                return true;
            case "on":
                sender.sendMessage("§6§l ▶ §aChatSpy Ativado!");
                SpyMessage.changeSpyState(player,"§7",true);
                return true;
            case "setcolor":
                if (!SpyMessage.isSpying(player)){
                    sender.sendMessage("§6§l ▶ §aSeu ChatSpy está desativado...");
                    return true;
                }
                String color = argumentos.get(2).isEmpty() ? "§7" : ChatColor.translateAlternateColorCodes('&',argumentos.get(2));
                SpyMessage.changeSpyState(player, color,true);
                sender.sendMessage("§6§l ▶ §aCor do ChatSpy alterado para: " + color + argumentos.get(2));
                return true;
        }
        sender.sendMessage("§6§l ▶ §aParâmetro inválido...");
        return true;
    }


    // -----------------------------------------------------------------------------------------------------------------------------//
    // Command Reload
    // -----------------------------------------------------------------------------------------------------------------------------//
    public static boolean reload(String label, CommandSender sender, List<String> argumentos){

        if (!FCBukkitUtil.hasThePermission(sender,PermissionNodes.COMMAND_RELOAD)){
            return true;
        }

        ConfigManager.initialize(FinalChat.instance);

        sender.sendMessage("§aFancyChat foi recarregado!!!");
        return true;
    }
}
