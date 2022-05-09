package br.com.finalcraft.finalchat.util.messages;

import br.com.finalcraft.evernifecore.fancytext.FancyFormatter;
import br.com.finalcraft.evernifecore.fancytext.FancyText;
import br.com.finalcraft.finalchat.config.fancychat.TellTag;
import br.com.finalcraft.finalchat.config.lang.FinalChatLang;
import br.com.finalcraft.finalchat.util.FancyTextUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PrivateMessage {

    public static Map<String,String> tellHistory = new HashMap<String, String>();

    public static void sendTell(Player sender, Player target, String msg){
        if (target == null
                || !target.isOnline()
                || !sender.canSee(target)
        ){
            sender.sendMessage(FinalChatLang.getLang("listener.invalidplayer"));
            return;
        }

        synchronized (tellHistory){
            tellHistory.put(sender.getName(), target.getName());
            tellHistory.put(target.getName(), sender.getName());
        }

        //Append message prefix
        FancyFormatter textToSender = FancyFormatter.of().append(FancyTextUtil.parsePlaceholdersAndClone(TellTag.TELL_TAG.getFancyTextSender(), sender));
        FancyFormatter textToTarget = FancyFormatter.of().append(FancyTextUtil.parsePlaceholdersAndClone(TellTag.TELL_TAG.getFancyTextSender(), sender));

        //Replace static variables
        textToSender.replace("{sender}",sender.getName()).replace("{receiver}",target.getName());
        textToTarget.replace("{sender}",sender.getName()).replace("{receiver}",target.getName());

        //Apply to the final message color
        if (sender.hasPermission("fancychat.color")){
            msg = ChatColor.translateAlternateColorCodes('&', msg);
        }

        //Append message body
        FancyText messageBody = new FancyText(msg);
        textToSender.append(messageBody);
        textToTarget.append(messageBody);

        //Send the message
        textToSender.send(sender);
        textToTarget.send(target);

        //Spy on it
        SpyMessage.spyOnThis(textToTarget.getFancyTextList(), Arrays.asList(sender,target));
    }


    public static String getLastTarget(String source){
        synchronized (tellHistory){
            return tellHistory.getOrDefault(source,null);
        }
    }
}
