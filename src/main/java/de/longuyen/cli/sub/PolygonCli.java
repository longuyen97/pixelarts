package de.longuyen.cli.sub;

import de.longuyen.core.Transformer;
import de.longuyen.core.polygon.Polygon;
import lombok.SneakyThrows;
import picocli.CommandLine;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

@CommandLine.Command(name = "polygon", description = "Used to segment images with Polygon algorithm")
public class PolygonCli implements Runnable{
    @CommandLine.Option(names = {"--file", "-f"}, description = "Target image file for segmentation", required = true)
    private String file;

    @CommandLine.Option(names = {"--centers", "-c"}, description = "How many centers should there be")
    private int centers = 50;

    @CommandLine.Option(names = {"--iterations", "-i"}, description = "How many iterations should there be")
    private int iterations = 10;


    @SneakyThrows
    @Override
    public void run() {
        Transformer imageTransformer = new Polygon(new Polygon.Parameters(centers, iterations));
        BufferedImage bufferedImage = ImageIO.read(new File(file));
        BufferedImage output = imageTransformer.convert(bufferedImage);
        ImageIO.write(output, "png", new File("output.png"));
    }
}
