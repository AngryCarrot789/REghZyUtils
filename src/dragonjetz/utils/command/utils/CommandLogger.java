package dragonjetz.utils.command.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import dragonjetz.mfunclagfind.utils.MathsHelper;
import dragonjetz.mfunclagfind.utils.text.Format;

public final class CommandLogger {
    private final String prefix;
    private CommandSender sender;

    public CommandLogger(String formattedPrefix) {
        this.prefix = formattedPrefix;
    }

    public void logNoColour(String text) {
        if (sender == null) {
            logConsoleNoColour(text);
        }
        else {
            sender.sendMessage(text);
        }
    }

    public void logGold(String text) {
        logNoColour(ChatColor.GOLD + text);
    }

    public void logRed(String text) {
        logNoColour(ChatColor.RED + text);
    }

    public void logGreen(String text) {
        logNoColour(ChatColor.GREEN + text);
    }

    public void logCyan(String text) {
        logNoColour(ChatColor.DARK_AQUA + text);
    }

    public void logPrefix(String text) {
        logNoColour(new StringBuilder().append(this.prefix).append(' ').append(ChatColor.GOLD).append(text).toString());
    }

    public void logFormat(String text, Object... args) {
        logPrefixTranslate(Format.format(text, args));
    }

    public void logFormatConsole(String text, Object... args) {
        logConsolePrefixTranslate(Format.format(text, args));
    }

    public void logTranslate(String text) {
        logNoColour(Format.translateColourCode('&', text));
    }

    public void logPrefixTranslate(String text) {
        logPrefix(Format.translateColourCode('&', text));
    }

    public boolean nullCheckPrefix(Object value, String isNullMessage) {
        if (value == null) {
            logPrefix(isNullMessage);
            return true;
        }
        return false;
    }

    public void logTeleport(String playerName, String worldName, double x, double y, double z) {
        logTranslate("&6Teleported &e" + playerName +
                     " &6to &8" + worldName +
                     " &6at &c" + MathsHelper.round(x, 2) +
                     ", &a" + MathsHelper.round(y, 2) +
                     ", &b" + MathsHelper.round(z, 2));
    }

    public void logConsolePrefix(String text) {
        logConsoleNoColour(this.prefix + ' ' + ChatColor.GOLD + text);
    }

    public void logConsolePrefixTranslate(String text) {
        logConsoleNoColour(this.prefix + ' ' + ChatColor.GOLD + Format.translateColours(text));
    }

    public static void logConsoleNoColour(String text) {
        Bukkit.getConsoleSender().sendMessage(text);
    }

    public void setSender(CommandSender sender) {
        this.sender = sender;
    }

    public CommandSender getSender() {
        return this.sender;
    }

    public String getPrefix() {
        return this.prefix;
    }
}
