package br.com.finalcraft.finalchat.util.messages;

import br.com.finalcraft.evernifecore.fancytext.FancyText;
import br.com.finalcraft.evernifecore.util.FCBukkitUtil;
import br.com.finalcraft.finalchat.FinalChat;
import br.com.finalcraft.finalchat.PermissionNodes;
import br.com.finalcraft.finalchat.api.FinalChatSendChannelMessageEvent;
import br.com.finalcraft.finalchat.config.fancychat.FancyChannel;
import br.com.finalcraft.finalchat.config.fancychat.FancyTag;
import br.com.finalcraft.finalchat.placeholders.PlaceHolderIntegration;
import br.com.finalcraft.finalchat.util.FancyTextUtil;
import br.com.finalcraft.finalchat.util.IgnoreUtil;
import br.com.finalcraft.finalchat.util.MuteUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class PublicMessage {

    public static void sendPublicMessage(Player player, FancyChannel channel, String msg){

        if (MuteUtil.isMuted(player)){
            player.sendMessage("§6§l ▶ Você está mutado!");
            player.sendMessage(MuteUtil.getMuteMessage(player));
            return;
        }

        FinalChatSendChannelMessageEvent sendMessageEvent = new FinalChatSendChannelMessageEvent(player, channel, msg);
        Bukkit.getServer().getPluginManager().callEvent(sendMessageEvent);
        if (sendMessageEvent.isCancelled()){
            return;
        }

        if (player.hasPermission(PermissionNodes.CHAT_COLOR)){
            msg = ChatColor.translateAlternateColorCodes('&', msg);
        }

        int idOfMSGText = 0;
        int contador = 0;
        List<FancyText> textChatList = new ArrayList<FancyText>();
        for (FancyTag fancyTag : channel.getTagsFromThisBuilder()){
            if (!fancyTag.getPermission().isEmpty() && !player.hasPermission(fancyTag.getPermission())){
                continue;
            }
            if (!fancyTag.getPlaceholderCondition().isEmpty()){
                String[] placeholderToCondition = fancyTag.getPlaceholderCondition().split("|");
                if (placeholderToCondition.length != 2){
                    FinalChat.info("Error parsing placeholderToCondition [" + fancyTag.getPlaceholderCondition() + "] from tag [" + fancyTag.getName() + "] and channel [" + channel.getName() + "]" );
                    continue;
                }
                if (!PlaceHolderIntegration.parsePlaceholder(placeholderToCondition[0],player).equalsIgnoreCase(PlaceHolderIntegration.parsePlaceholder(placeholderToCondition[1],player))){
                    continue;
                }

            }
            FancyText fancyText = FancyTextUtil.parsePlaceholdersAndClone(fancyTag.getFancyText(),player);
            fancyText.setText(fancyText.getText().replace("{player}",player.getName()));
            String replacedText = fancyText.getText().replace("{msg}",msg);
            if (!replacedText.equals(fancyText.getText())){
                fancyText.setText(replacedText);
                idOfMSGText = contador;
            }
            contador++;
            textChatList.add(fancyText);
        }

        final int finalIdOfMSGText = idOfMSGText;


        //Better do the rest sync, read        http://bit.ly/1oSiM6C
        new BukkitRunnable(){
            @Override
            public void run() {
                if (channel.getDistance() <= -1){
                    for (Player onlinePlayerToSendMessage : channel.getPlayersOnThisChannel()) {
                        if (!IgnoreUtil.isIgnoring(onlinePlayerToSendMessage, player)){

                            //Entregando mensagem ao jogador!
                            doTheDeploy(textChatList,player,onlinePlayerToSendMessage,finalIdOfMSGText);

                        }
                    }
                }else {
                    List<Player> playerThatHeardedThis = new ArrayList<Player>();

                    for (Player onlinePlayerToSendMessage : channel.getPlayersOnThisChannel()) {
                        if (calcDistance(player,onlinePlayerToSendMessage) <=  channel.getDistance()){
                            playerThatHeardedThis.add(onlinePlayerToSendMessage);
                            if (!IgnoreUtil.isIgnoring(onlinePlayerToSendMessage, player)){

                                //Entregando mensage ao jogador!
                                doTheDeploy(textChatList,player,onlinePlayerToSendMessage,finalIdOfMSGText);

                            }
                        }
                    }

                    if (playerThatHeardedThis.size() <= 1 && channel.getDistance() > -1){
                        player.sendMessage("§6§l ▶ §cNão tem ninguem perto de você para receber essa mensagem...");
                    }

                    if (channel.getDistance() > -1){
                        SpyMessage.spyOnThis(textChatList, playerThatHeardedThis);
                    }
                }
                FinalChat.chatLog(FancyTextUtil.textOnly(textChatList));
            }
        }.runTask(FinalChat.instance);
    }

    public static void doTheDeploy(final List<FancyText> textChatList, Player player, Player onlinePlayerToSendMessage, int finalIdOfMSGText){
        FancyText fancyTextContainingMessage = textChatList.get(finalIdOfMSGText);

        String[] array = fancyTextContainingMessage.getText().split("(?i)" + onlinePlayerToSendMessage.getName()); // splits case insensitive
        if (array.length > 1){
            List<FancyText> textToSend = new ArrayList<FancyText>();
            textToSend.addAll(textChatList.subList(0,finalIdOfMSGText));

            FancyText fancyTextPart1 = fancyTextContainingMessage.clone();
            fancyTextPart1.setText(array[0]);
            fancyTextPart1.setRecentChanged();

            FancyText fancyTextPart2 = new FancyText("§6 @" + onlinePlayerToSendMessage.getName() + ChatColor.getLastColors(array[0])).setHoverText("§aO jogador " + player.getName() + " marcou você!");

            FancyText fancyTextPart3 = fancyTextContainingMessage.clone();
            fancyTextPart3.setText(array[1]);
            fancyTextPart3.setRecentChanged();

            textToSend.add(fancyTextPart1);
            textToSend.add(fancyTextPart2);
            textToSend.add(fancyTextPart3);

            textToSend.addAll(textChatList.subList(finalIdOfMSGText + 1,textChatList.size()));

            FCBukkitUtil.playSound(onlinePlayerToSendMessage.getName(),"entity.experience_orb.pickup");
            FancyText.sendTo(onlinePlayerToSendMessage,textToSend);
        }else {
            FancyText.sendTo(onlinePlayerToSendMessage,textChatList);
        }
    }

    public static double calcDistance(Player player, Player otherPlayer){
        Location playerLocation = player.getLocation();
        Location otherPlayerLocation = otherPlayer.getLocation();
        if (playerLocation.getWorld() == otherPlayerLocation.getWorld()){
            return playerLocation.distance(otherPlayerLocation);
        }
        return Double.MAX_VALUE;
    }
}
