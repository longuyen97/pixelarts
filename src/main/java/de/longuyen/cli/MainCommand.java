package de.longuyen.cli;

import de.longuyen.cli.sub.AsciifierCli;
import de.longuyen.cli.sub.PolygonCli;
import de.longuyen.cli.sub.VoronoiCli;
import picocli.CommandLine;

@CommandLine.Command(name = "arts", subcommands = {AsciifierCli.class, VoronoiCli.class, PolygonCli.class})
public class MainCommand implements Runnable {
    @Override
    public void run() {
        CommandLine cmd = new CommandLine(this);
        cmd.getHelpSectionMap().put(CommandLine.Model.UsageMessageSpec.SECTION_KEY_COMMAND_LIST, new CommandListRenderer());
        cmd.usage(System.out);
    }
}
