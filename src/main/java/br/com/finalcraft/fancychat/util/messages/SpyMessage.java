package br.com.finalcraft.fancychat.util.messages;

import br.com.finalcraft.evernifecore.fancytext.FancyText;
import br.com.finalcraft.fancychat.FancyChat;
import br.com.finalcraft.fancychat.util.FancyTextUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;

public class SpyMessage {

    private static Map<Player,String> spyingPlayers = new HashMap<>(); //Player - Color

    /**
     *
     * @return <tt>true</tt> if this player has benn added to the SpyList
     * @return <tt>false</tt> if this player has benn removed from the SpyList
     */
    public static void changeSpyState(Player player, String color, boolean state){
        spyingPlayers.remove(player);
        if (state == true){
            spyingPlayers.put(player, color);
        }
    }

    public static boolean isSpying(Player player){
        return spyingPlayers.containsKey(player);
    }

    public static void spyOnThis(List<FancyText> msg, List<Player> allPlayerWhoHeard){
        if (spyingPlayers.size() == 0){
            return;
        }

        HashSet<UUID> allPlayerWhoHeardUUIDs = new HashSet();

        StringBuilder allPlayerWhoHeardString = new StringBuilder();
        for (Player player : allPlayerWhoHeard){
            allPlayerWhoHeardString.append("\n  - " + player.getName());
            allPlayerWhoHeardUUIDs.add(player.getUniqueId());
        }

        msg.forEach(fancyText -> {
            fancyText.setText("§7" + ChatColor.stripColor(fancyText.getText()));
            fancyText.setHoverText("Jogadores que escutaram essa mensagem: \n " + allPlayerWhoHeardString);
        });


        String previousColor = "§7";
        for (FancyText fancyText : msg) {
            fancyText.setText(previousColor + ChatColor.stripColor(fancyText.getText()));
        }

        FancyChat.chatLog(FancyTextUtil.textOnly(msg));
        for (Map.Entry<Player, String> entry : spyingPlayers.entrySet()) {
            if (entry.getKey().isOnline()){
                if (!allPlayerWhoHeardUUIDs.contains(entry.getKey().getUniqueId())){
                    if (!previousColor.equalsIgnoreCase(entry.getValue())){
                        previousColor = entry.getValue();
                        for (FancyText fancyText : msg) {
                            fancyText.setText(previousColor + ChatColor.stripColor(fancyText.getText()));
                        }
                    }
                    FancyText.sendTo(entry.getKey(),msg);
                }
            }
        }
    }
}
