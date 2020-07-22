package de.longuyen.cli;

import de.longuyen.cli.sub.AsciifierCli;
import de.longuyen.cli.sub.SimpleVoronoiCli;
import de.longuyen.core.ascii.Asciifier;
import picocli.CommandLine;

@CommandLine.Command(name = "arts", subcommands = {AsciifierCli.class, SimpleVoronoiCli.class})
public class CommandProcessor implements Runnable {
    @Override
    public void run() {

    }
}
