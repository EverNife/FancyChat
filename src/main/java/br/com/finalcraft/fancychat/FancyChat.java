package br.com.finalcraft.fancychat;

import br.com.finalcraft.evernifecore.version.MCVersion;
import br.com.finalcraft.fancychat.commands.CommandRegisterer;
import br.com.finalcraft.fancychat.config.ConfigManager;
import br.com.finalcraft.fancychat.integration.builtin.DefaultParser;
import br.com.finalcraft.fancychat.integration.builtin.FactionsParser;
import br.com.finalcraft.fancychat.listener.FancyChatListener;
import br.com.finalcraft.fancychat.placeholders.PlaceHolderIntegration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class FancyChat extends JavaPlugin{

    public static FancyChat instance;

    public static void info(String msg){
        instance.getLogger().info("[Info] " + msg.replace("&","§"));
    }
    public static void chatLog(String msg){
        instance.getLogger().info("[ChatLog] " + (MCVersion.isLegacy() ? ChatColor.stripColor(msg) : msg));
    }
    public static void debug(String msg){
        instance.getLogger().info("[Debug] " + msg.replace("&","§"));
    }

    private FancyChatListener fancyChatListener = new FancyChatListener();

    @Override
    public void onEnable() {
        instance = this;

        info("&aIniciando o Plugin...");

        info("&aCarregando configuracoes...");
        ConfigManager.initialize(this);

        info("&aRegistrando comandos...");
        CommandRegisterer.registerCommands(this);

        info("&aRegistrando Listeners");
        this.getServer().getPluginManager().registerEvents(fancyChatListener, this);

        //Iniciando PlaceHolderAPI Integration
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")){
            info("&aIntegration to PlaceHolderAPI");
            PlaceHolderIntegration.initialize();
        }

        new BukkitRunnable(){
            @Override
            public void run() {
                if (Bukkit.getPluginManager().isPluginEnabled("Factions")){
                    info("&aIntegration to Factions");
                    FactionsParser.initialize();
                }
            }
        }.runTaskLater(instance,1);

        DefaultParser.initialize();
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(fancyChatListener);
    }


}
