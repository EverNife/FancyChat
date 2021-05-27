package br.com.finalcraft.fancychat.integration.builtin;

import br.com.finalcraft.betterclans.api.BetterClansAPI;
import br.com.finalcraft.betterclans.config.data.ClanData;
import br.com.finalcraft.betterclans.config.playerdata.BCPlayerData;
import br.com.finalcraft.evernifecore.cooldown.FCTimeFrame;
import br.com.finalcraft.fancychat.integration.ThirdPartTagsParser;
import org.bukkit.entity.Player;

public class BetterClansParser extends ThirdPartTagsParser {

    public static void initialize(){
        addThirdPartTagsParser(new BetterClansParser());
    }

    @Override
    public String parseTags(String theMessage, Player sender, Player receiver) {
        if (sender != null){
            BCPlayerData playerData = BetterClansAPI.getBCPlayerData(sender);
            if (playerData.isInClan()) {
                ClanData clanData = playerData.getClanData();
                theMessage = theMessage
                        .replace("{clan-name}"      , clanData.getName())
                        .replace("{clan-tag}"       , clanData.getTag())
                        .replace("{clan-kills}"     , String.valueOf(clanData.getTotalKills()))
                        .replace("{clan-members}"   , String.valueOf(clanData.getMembers().size()))
                        .replace("{clan-foundation}", FCTimeFrame.getFormatedNoHours(clanData.getFoundationTime()))
                     //   .replace("{faction-deaths}"     , String.valueOf(clanData.getDeaths()))
                    //    .replace("{faction-lifetime}"   , String.valueOf(TimeUnit.MILLISECONDS.toDays(clanData.getAge())))
                   //     .replace("{faction-motd}"       , (clanData.hasMotd() ? clanData.getMotd() : "" ))
                   //     .replace("{faction-description}", (clanData.hasDescription() ? clanData.getDescription() : "" ))
                ;
                //.replace("{faction-owner}", fac.getLeader().getNameAndFactionName());  //Should be only "getName()" but seems to be private!
                return theMessage;
            }
        }
        theMessage = theMessage
                .replace("{clan-name}", "")
                .replace("{clan-tag}", "");
        return theMessage;
    }
}
