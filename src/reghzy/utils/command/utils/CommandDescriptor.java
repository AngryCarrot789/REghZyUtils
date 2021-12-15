package reghzy.utils.command.utils;

import org.bukkit.ChatColor;

public class CommandDescriptor {
    private final String prefix;
    private final String mainCommand;
    private String[] subMainCommands;
    private final String commandName;
    private final String args;
    private final String[] descriptionLines;
    private final boolean isMainCommand;
    //private int topLength;

    public CommandDescriptor(String prefix, String mainCommand, String commandName, String args, String... descriptionLines) {
        this.prefix = prefix;
        this.mainCommand = mainCommand;
        this.commandName = commandName;
        this.args = args;
        this.descriptionLines = descriptionLines;
        this.isMainCommand = ((commandName == null || commandName.isEmpty()) && (mainCommand != null && (!mainCommand.isEmpty())));
        //this.topLength = 0;
        //this.topLength += (pluginPrefix == null ? 0 : pluginPrefix.length());
        //this.topLength += (mainCommand == null ? 0 : mainCommand.length());
        //this.topLength += (commandName == null ? 0 : commandName.length());
        //this.topLength += (args == null ? 0 : args.length());
    }

    public String getFormatCommandAndParamsOnly() {
        StringBuilder string = new StringBuilder();
        string.append(Colours.prefix).append('/').append(prefix).append(' ');
        if (mainCommand != null && mainCommand.length() > 0) {
            string.append(Colours.mainCommand).append(formatSubCommands()).append(' ');
        }
        if (commandName != null && commandName.length() > 0) {
            string.append(Colours.actualCommand).append(commandName).append(' ');
        }

        if (isMainCommand) {
            string.append(Colours.mainCommandIndicator).append("(This command has sub-commands)");
        }
        else {
            if (args != null && args.length() > 0) {
                string.append(Colours.args).append(getArgs());
            }
        }

        return string.toString();
    }

    public String getFormatCommandOnly() {
        StringBuilder string = new StringBuilder();
        string.append(Colours.prefix).append('/').append(prefix).append(' ');
        if (mainCommand != null && mainCommand.length() > 0) {
            string.append(Colours.mainCommand).append(formatSubCommands());
        }
        if (commandName != null && commandName.length() > 0) {
            string.append(' ').append(Colours.actualCommand).append(commandName);
        }
        return string.toString();
    }

    public String getFormatCommandAll() {
        StringBuilder string = new StringBuilder();
        string.append(Colours.prefix).append('/').append(prefix).append(' ');
        if (mainCommand != null && mainCommand.length() > 0) {
            string.append(Colours.mainCommand).append(formatSubCommands()).append(' ');
        }
        if (commandName != null && commandName.length() > 0) {
            string.append(Colours.actualCommand).append(commandName).append(' ');
        }

        if (isMainCommand) {
            string.append(Colours.mainCommandIndicator).append("(This command has sub-commands)\n");
        }
        else {
            if (args != null && args.length() > 0) {
                string.append(Colours.args).append(getArgs());
            }
            string.append('\n');
        }

        String[] descriptionLines = getDescriptionLines();
        for (int i = 0, len = descriptionLines.length, lenIndex = len - 1; i < len; i++) {
            string.append(Colours.description).append(descriptionLines[i]);
            if (i < lenIndex) {
                string.append('\n');
            }
        }
        return string.toString();
    }

    public void writeFormatAll(CommandLogger logger) {
        StringBuilder firstLine = new StringBuilder();
        firstLine.append(Colours.prefix).append('/').append(prefix).append(' ');
        if (mainCommand != null && mainCommand.length() > 0) {
            firstLine.append(Colours.mainCommand).append(formatSubCommands()).append(' ');
        }
        if (commandName != null && commandName.length() > 0) {
            firstLine.append(Colours.actualCommand).append(commandName).append(' ');
        }

        if (isMainCommand) {
            firstLine.append(Colours.mainCommandIndicator).append("(This command has sub-commands)");
        }
        else if (args != null && args.length() > 0) {
            firstLine.append(Colours.args).append(getArgs());
        }

        logger.logNoColour(firstLine.toString());

        for (String descriptionLine : getDescriptionLines()) {
            logger.logNoColour(Colours.description + descriptionLine);
        }
    }

    public String formatSubCommands() {
        if (this.subMainCommands == null) {
            return this.mainCommand;
        }
        else {
            StringBuilder string = new StringBuilder(64);
            for (String subCommand : this.subMainCommands) {
                string.append(subCommand).append(' ');
            }
            return string.append(this.mainCommand).toString();
        }
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getCommandName() {
        return this.commandName;
    }

    public String getMainCommand() {
        return this.mainCommand;
    }

    public String getAnyCommandName() {
        if (this.isMainCommand) {
            return this.mainCommand;
        }
        else {
            return this.commandName;
        }
    }

    public String getArgs() {
        //if (this.topLength > 64) {
        //    return "\n Args: " + this.args;
        //}
        return this.args;
    }

    public String[] getDescriptionLines() {
        return this.descriptionLines;
    }

    public boolean isMainCommand() {
        return this.isMainCommand;
    }

    /**
     * Add these before the main subcommand (e.g. if the full command is 'register state',
     * the first sub command is 'register', so add 'register' in the parameters)
     */
    public void setSubMainCommands(String... subCommands) {
        this.subMainCommands = subCommands;
        //if (subCommands == null) {
        //    return;
        //}
        // this.topLength = 0;
        // for(String sub : subCommands) {
        //     if (sub != null) {
        //         this.topLength += sub.length();
        //     }
        // }
    }

    public enum Colours {
        prefix(ChatColor.GREEN),
        mainCommand(ChatColor.AQUA),
        mainCommandIndicator(ChatColor.YELLOW),
        actualCommand(ChatColor.DARK_AQUA),
        args(ChatColor.LIGHT_PURPLE),
        description(ChatColor.DARK_GREEN);

        private final ChatColor chatColour;

        Colours(ChatColor chatColour) {
            this.chatColour = chatColour;
        }

        public ChatColor getChatColour() {
            return chatColour;
        }

        @Override
        public String toString() {
            return this.chatColour.toString();
        }
    }
}
