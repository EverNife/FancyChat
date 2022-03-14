package br.com.finalcraft.finalchat.commands.argparser;

import br.com.finalcraft.evernifecore.argumento.Argumento;
import br.com.finalcraft.evernifecore.commands.finalcmd.argument.ArgInfo;
import br.com.finalcraft.evernifecore.commands.finalcmd.argument.ArgParser;
import br.com.finalcraft.evernifecore.commands.finalcmd.argument.exception.ArgParseException;
import br.com.finalcraft.evernifecore.locale.FCLocale;
import br.com.finalcraft.evernifecore.locale.LocaleMessage;
import br.com.finalcraft.evernifecore.locale.LocaleType;
import br.com.finalcraft.finalchat.config.fancychat.FancyChannel;
import br.com.finalcraft.finalchat.config.fancychat.FancyChannelController;
import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArgParserFancyChannel extends ArgParser<FancyChannel> {

    public ArgParserFancyChannel(ArgInfo argInfo) {
        super(argInfo);
    }

    @FCLocale(lang = LocaleType.PT_BR, text = "§4§l ▶ §cMinichest não encontrada! Use §e/%label% list")
    @FCLocale(lang = LocaleType.EN_US, text = "§4§l ▶ §cMinichest not found! Use §e/%label% list")
    private static LocaleMessage MINICHEST_NOT_FOUND;

    @Override
    public FancyChannel parserArgument(@NotNull CommandSender sender, @NotNull Argumento argumento) throws ArgParseException {

        for (FancyChannel fancyChannel : FancyChannelController.getAllChannels()) {
            if (argumento.equalsIgnoreCase(fancyChannel.getName(), fancyChannel.getAlias())){
                return fancyChannel;
            }
        }

        return null;
    }

    @Override
    public @NotNull List<String> tabComplete(Context context) {

        List<String> matched = new ArrayList<>();

        for (FancyChannel fancyChannel : FancyChannelController.getAllChannels()) {
            if ( (fancyChannel.getPermission().isEmpty() || context.getPlayer().hasPermission(fancyChannel.getPermission()))
                    && StringUtil.startsWithIgnoreCase(fancyChannel.getName(), context.getLastWord())){
                matched.add(fancyChannel.getName());
            }
        }

        Collections.sort(matched, String.CASE_INSENSITIVE_ORDER);
        return matched;
    }
}
