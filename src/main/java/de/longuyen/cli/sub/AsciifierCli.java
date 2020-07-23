package de.longuyen.cli.sub;

import de.longuyen.cli.Cli;
import de.longuyen.core.Transformer;
import de.longuyen.core.ascii.Asciifier;
import picocli.CommandLine;

@CommandLine.Command(name = "asciify", description = "Used to asciify images")
public class AsciifierCli extends Cli {

    @CommandLine.Option(names = {"--interpolation", "-i"}, description = "How much should the image be interpolated")
    private int interpolation = 3;

    @CommandLine.Option(names = {"--window", "-w"}, description = "How big should the window's size be")
    private int windowSize = 3;

    @Override
    protected Transformer transformer() {
        return new Asciifier(new Asciifier.Parameters(interpolation, windowSize));
    }
}
