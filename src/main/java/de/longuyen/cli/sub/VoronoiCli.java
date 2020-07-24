package de.longuyen.cli.sub;

import de.longuyen.cli.Cli;
import de.longuyen.core.Transformer;
import de.longuyen.core.voronoi.SimpleVoronoi;
import picocli.CommandLine;

@CommandLine.Command(name = "voronoi", description = "Used to triangulate images")
public class VoronoiCli extends Cli {
    @CommandLine.Option(names = {"--points", "-p"}, description = "How big should the window's size be", required = true)
    protected int points;

    @Override
    protected Transformer transformer() {
        return new SimpleVoronoi(new SimpleVoronoi.Parameters(points));
    }
}
