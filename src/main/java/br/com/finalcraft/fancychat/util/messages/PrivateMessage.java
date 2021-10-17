package br.com.finalcraft.fancychat.util.messages;

import br.com.finalcraft.evernifecore.fancytext.FancyText;
import br.com.finalcraft.fancychat.config.fancychat.TellTag;
import br.com.finalcraft.fancychat.config.lang.FancyChatLang;
import br.com.finalcraft.fancychat.util.FancyTextUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;

public class PrivateMessage {

    public static Map<String,String> tellHistory = new HashMap<String, String>();

    public static void sendTell(Player sender, Player receiver, String msg){
        if (receiver == null
                || (receiver instanceof Player && (!((Player)receiver).isOnline()
                || (sender instanceof Player && !((Player)sender).canSee((Player)receiver))))
        ){
            sender.sendMessage(FancyChatLang.getLang("listener.invalidplayer"));
            return;
        }
        synchronized (tellHistory){
            tellHistory.put(sender.getName(), receiver.getName());
            tellHistory.put(receiver.getName(), sender.getName());
        }

        FancyText fancyTextSender   = FancyTextUtil.parsePlaceholdersAndClone(TellTag.TELL_TAG.getFancyTextSender(), sender);
        FancyText fancyTextReceiver = FancyTextUtil.parsePlaceholdersAndClone(TellTag.TELL_TAG.getFancyTextReceiver(), sender);

        fancyTextSender.setText(fancyTextSender.getText().replace("{sender}",sender.getName()).replace("{receiver}",receiver.getName()));
        fancyTextReceiver.setText(fancyTextReceiver.getText().replace("{sender}",sender.getName()).replace("{receiver}",receiver.getName()));

        if (sender.hasPermission("fancychat.color")){
            msg = ChatColor.translateAlternateColorCodes('&', msg);
        }

        FancyText fancyTextMessage = new FancyText(msg);

        List<FancyText> senderFancyText = new ArrayList(Arrays.asList(fancyTextSender,fancyTextMessage));

        FancyText.sendTo(sender, senderFancyText);
        FancyText.sendTo(receiver,Arrays.asList(fancyTextReceiver,fancyTextMessage));


        SpyMessage.spyOnThis(senderFancyText,new ArrayList(Arrays.asList(sender,receiver)));
    }


    public static String getLastTarget(String source){
        synchronized (tellHistory){
            return tellHistory.getOrDefault(source,null);
        }
    }
}
