package de.longuyen.cli.sub;

import de.longuyen.core.Transformer;
import de.longuyen.core.voronoi.AdvancedVoronoi;
import picocli.CommandLine;

@CommandLine.Command(name = "advanced-voronoi", description = "Advanced voronoi used to triangulate images")
public class AdvancedVoronoiCli extends VoronoiCli {
    @CommandLine.Option(names = {"--range", "-r"}, description = "Which range should be used")
    private double range;


    @CommandLine.Option(names = {"--times", "-t"}, description = "How many times should be image be convoled")
    private int times = 1;

    @Override
    protected Transformer transformer() {
        return new AdvancedVoronoi(new AdvancedVoronoi.Parameters(this.points, this.range, this.times));
    }
}
