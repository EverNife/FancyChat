package br.com.finalcraft.fancychat.config;

import br.com.finalcraft.fancychat.commands.alias.AliaseRegister;
import br.com.finalcraft.fancychat.config.fancychat.FancyChannel;
import br.com.finalcraft.fancychat.config.fancychat.FancyTag;
import br.com.finalcraft.fancychat.config.fancychat.TellTag;
import br.com.finalcraft.fancychat.integration.FactionsIntegration;
import br.com.finalcraft.fancychat.util.ChannelManager;
import br.com.finalcraft.fancychat.util.MuteUtil;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class ConfigManager {

    public static JavaPlugin instance;

    public static Config mainConfig;
    public static Config dataStore;

    public static Config getMainConfig(){
        return mainConfig;
    }
    public static Config getDataStore(){
        return dataStore;
    }

    public static void initialize(JavaPlugin aInstace){
        instance = aInstace;

        mainConfig  = new Config(instance,"config.yml"      ,false);
        dataStore   = new Config(instance,"DataStore.yml"      ,false);

        setDefaultValues();

        FancyTag.initialize();                      //Ler Tags
        TellTag.initialize();                       //Ler TellTag
        FancyChannel.initialize();                  //Ler Canais
        ChannelManager.refresh();                   //Carregar ChannelManager
        MuteUtil.initialize();                      //Carregar mutes e tempmutes
        AliaseRegister.registerChannelAliases();    //Registrar os atalhos para os canais, /g /l etc

        FactionsIntegration.initialize();           //Ativa integração com Factions
    }

    private static final int CURRENT_VERSION = 2;
    private static void setDefaultValues(){
        if (ConfigManager.getMainConfig().getInt("ConfigVersion",0) != CURRENT_VERSION){
            ConfigManager.getMainConfig().setDefaultValue("ConfigVersion",CURRENT_VERSION);

            ConfigManager.getMainConfig().setDefaultValue("Settings.Integrations.Factions",true);

            ConfigManager.getMainConfig().setDefaultValue("TellTag.sender-format"," {sender} &b-> &r{receiver}&r > &f");
            ConfigManager.getMainConfig().setDefaultValue("TellTag.receiver-format"," {sender} &c-> &r{receiver}&r > &f");
            ConfigManager.getMainConfig().setDefaultValue("TellTag.hover-messages", Arrays.asList("&3Isso é uma mensagem privada!","This is a private message"));

            ConfigManager.getMainConfig().setDefaultValue("TagFormats.ch-global.format","&7[&aG&7]");
            ConfigManager.getMainConfig().setDefaultValue("TagFormats.ch-global.run-command","/ch g");
            ConfigManager.getMainConfig().setDefaultValue("TagFormats.ch-global.hover-messages",Arrays.asList("&3Channel: &a&oGlobal","&bClick here to lock conversation in this channel"));

            ConfigManager.getMainConfig().setDefaultValue("TagFormats.ch-local.format","&7[&eL&7]");
            ConfigManager.getMainConfig().setDefaultValue("TagFormats.ch-local.run-command","/ch l");
            ConfigManager.getMainConfig().setDefaultValue("TagFormats.ch-local.hover-messages",Arrays.asList("&3Channel: &a&oLocal","&bClick here to lock conversation in this channel"));

            ConfigManager.getMainConfig().setDefaultValue("TagFormats.nickname.format","{player}");
            ConfigManager.getMainConfig().setDefaultValue("TagFormats.nickname.global-premes"," &r > {msg}");
            ConfigManager.getMainConfig().setDefaultValue("TagFormats.nickname.local-premes"," &r > &6{msg}");

            ConfigManager.getMainConfig().setDefaultValue("ChannelFormats.Global.alias","g");
            ConfigManager.getMainConfig().setDefaultValue("ChannelFormats.Global.distance",-1);
            ConfigManager.getMainConfig().setDefaultValue("ChannelFormats.Global.tag-builder","ch-global,nickname,global-premes");
            ConfigManager.getMainConfig().setDefaultValue("ChannelFormats.Global.permission","");

            ConfigManager.getMainConfig().setDefaultValue("ChannelFormats.Local.alias","g");
            ConfigManager.getMainConfig().setDefaultValue("ChannelFormats.Local.distance",150);
            ConfigManager.getMainConfig().setDefaultValue("ChannelFormats.Local.tag-builder","ch-local,nickname,local-premes");
            ConfigManager.getMainConfig().setDefaultValue("ChannelFormats.Local.permission","");

            ConfigManager.getMainConfig().save();
        }
    }

}
