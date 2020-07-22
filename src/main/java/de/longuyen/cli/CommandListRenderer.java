package de.longuyen.cli;

import picocli.CommandLine;

public class CommandListRenderer implements CommandLine.IHelpSectionRenderer {
    @Override
    public String render(CommandLine.Help help) {
        CommandLine.Model.CommandSpec spec = help.commandSpec();
        if (spec.subcommands().isEmpty()) { return ""; }

        // prepare layout: two columns
        // the left column overflows, the right column wraps if text is too long
        CommandLine.Help.TextTable textTable = CommandLine.Help.TextTable.forColumns(help.ansi(),
                new CommandLine.Help.Column(15, 2, CommandLine.Help.Column.Overflow.SPAN),
                new CommandLine.Help.Column(spec.usageMessage().width() - 15, 2, CommandLine.Help.Column.Overflow.WRAP));

        for (CommandLine subcommand : spec.subcommands().values()) {
            addHierarchy(subcommand, textTable, "");
        }
        return textTable.toString();
    }

    private void addHierarchy(CommandLine cmd, CommandLine.Help.TextTable textTable, String indent) {
        // create comma-separated list of command name and aliases
        String names = cmd.getCommandSpec().names().toString();
        names = names.substring(1, names.length() - 1); // remove leading '[' and trailing ']'

        // command description is taken from header or description
        String description = description(cmd.getCommandSpec().usageMessage());

        // add a line for this command to the layout
        textTable.addRowValues(indent + names, description);

        // add its subcommands (if any)
        for (CommandLine sub : cmd.getSubcommands().values()) {
            addHierarchy(sub, textTable, indent + "  ");
        }
    }

    private String description(CommandLine.Model.UsageMessageSpec usageMessage) {
        if (usageMessage.header().length > 0) {
            return usageMessage.header()[0];
        }
        if (usageMessage.description().length > 0) {
            return usageMessage.description()[0];
        }
        return "";
    }
}
