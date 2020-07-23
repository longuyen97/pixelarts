package de.longuyen.cli.sub;

import de.longuyen.cli.Cli;
import de.longuyen.core.Transformer;
import de.longuyen.core.kmean.KMean;
import picocli.CommandLine;

@CommandLine.Command(name = "polygon", description = "Used to segment images with KMean algorithm")
public class KMeanCli extends Cli {
    @CommandLine.Option(names = {"--centers", "-c"}, description = "How many centers should there be")
    private int centers = 50;

    @CommandLine.Option(names = {"--iterations", "-i"}, description = "How many iterations should there be")
    private int iterations = 10;

    @Override
    protected Transformer transformer() {
        return new KMean(new KMean.Parameters(centers, iterations));
    }
}