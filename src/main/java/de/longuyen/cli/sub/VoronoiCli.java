package de.longuyen.cli.sub;

import de.longuyen.core.Transformer;
import de.longuyen.core.voronoi.SimpleVoronoi;
import lombok.SneakyThrows;
import picocli.CommandLine;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

@CommandLine.Command(name = "voronoi", description = "Used to triangulate images")
public class VoronoiCli implements Runnable {
    @CommandLine.Option(names = {"--file", "-f"}, description = "Target image file for triangulating", required = true)
    private String file;

    @CommandLine.Option(names = {"--points", "-p"}, description = "How big should the window's size be")
    private Integer points;

    @SneakyThrows
    @Override
    public void run() {
        BufferedImage bufferedImage = ImageIO.read(new File(file));
        if(points == null){
            points = (bufferedImage.getWidth() * bufferedImage.getHeight()) / (bufferedImage.getHeight() + bufferedImage.getWidth());
        }
        Transformer imageTransformer = new SimpleVoronoi(new SimpleVoronoi.Parameters(points));
        BufferedImage output = imageTransformer.convert(bufferedImage);
        ImageIO.write(output, "png", new File("output.png"));
    }
}
