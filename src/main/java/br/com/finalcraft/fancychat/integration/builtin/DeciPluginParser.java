package br.com.finalcraft.fancychat.integration.builtin;

import br.com.finalcraft.deciplugin.config.playerdata.DBPlayerData;
import br.com.finalcraft.evernifecore.config.playerdata.PlayerController;
import br.com.finalcraft.evernifecore.cooldown.FCTimeFrame;
import br.com.finalcraft.fancychat.integration.ThirdPartTagsParser;
import org.bukkit.entity.Player;


public class DeciPluginParser extends ThirdPartTagsParser {

    public static void initialize(){
        addThirdPartTagsParser(new DeciPluginParser());
    }

    @Override
    public String parseTags(String theMessage, Player sender, Player receiver) {
        if (sender != null){
            DBPlayerData playerData = DBPlayerData.getOrCreateDBPlayerData(PlayerController.getPlayerData(sender));
            if (playerData.hasPlayedOnBeta()) {
                theMessage = theMessage
                        .replace("{dbeta-tag}"          , "§b§l(§3Beta§b§l)")
                        .replace("{dbeta-points}"       , String.valueOf(playerData.getTotalPoints()))
                        .replace("{dbeta-kzombies}"     , String.valueOf(playerData.getZombieKills()))
                        .replace("{dbeta-zplayers}"     , String.valueOf(playerData.getPKills()))
                        .replace("{dbeta-ontime}"       , String.valueOf(new FCTimeFrame(playerData.getOntimeMillis()).getFormatedDiscursive()))
                ;
                return theMessage;
            }
        }
        theMessage = theMessage
                .replace("{dbeta-tag}", "");
        return theMessage;
    }
}
