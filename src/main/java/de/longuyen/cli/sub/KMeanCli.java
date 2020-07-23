package de.longuyen.cli.sub;

import de.longuyen.core.Transformer;
import de.longuyen.core.kmean.KMean;
import lombok.SneakyThrows;
import picocli.CommandLine;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

@CommandLine.Command(name = "kmean", description = "Used to segment images with K-Means algorithm")
public class KMeanCli implements Runnable{
    @CommandLine.Option(names = {"--file", "-f"}, description = "Target image file for segmentation", required = true)
    private String file;

    @CommandLine.Option(names = {"--centers", "-c"}, description = "How many centers should there be")
    private int centers = 100;

    @CommandLine.Option(names = {"--iterations", "-i"}, description = "How many iterations should there be")
    private int iterations = 100;


    @SneakyThrows
    @Override
    public void run() {
        Transformer imageTransformer = new KMean(new KMean.Parameters(centers, iterations));
        BufferedImage bufferedImage = ImageIO.read(new File(file));
        BufferedImage output = imageTransformer.convert(bufferedImage);
        ImageIO.write(output, "png", new File("output.png"));
    }
}
