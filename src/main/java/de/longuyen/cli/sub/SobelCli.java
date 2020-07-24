package de.longuyen.cli.sub;

import de.longuyen.cli.Cli;
import de.longuyen.core.Transformer;
import de.longuyen.core.convolution.Sobel;
import picocli.CommandLine;

@CommandLine.Command(name = "sobel", description = "Used to segment images with Polygon algorithm")
public class SobelCli extends Cli {
    @CommandLine.Option(names = {"--times", "-t"}, description = "How many times should be image be convoled")
    private int times = 1;

    @Override
    protected Transformer transformer() {
        return new Sobel(new Sobel.Parameters(times));
    }
}
