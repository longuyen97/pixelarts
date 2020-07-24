package de.longuyen.cli;

import de.longuyen.cli.sub.AsciifierCli;
import de.longuyen.cli.sub.KMeanCli;
import de.longuyen.cli.sub.PolygonCli;
import de.longuyen.cli.sub.VoronoiCli;
import de.longuyen.core.kmean.KMean;
import picocli.CommandLine;

/**
 * Main Command of the CLI. Does not provide any functionality but only help documentation.
 * Each algorithm will be executed with a sub-command
 */
@CommandLine.Command(name = "arts", subcommands = {AsciifierCli.class, VoronoiCli.class, PolygonCli.class, KMeanCli.class})
public class MainCommand implements Runnable {
    @Override
    public void run() {
        CommandLine cmd = new CommandLine(this);
        cmd.getHelpSectionMap().put(CommandLine.Model.UsageMessageSpec.SECTION_KEY_COMMAND_LIST, new CommandListRenderer());
        cmd.usage(System.out);
    }
}
