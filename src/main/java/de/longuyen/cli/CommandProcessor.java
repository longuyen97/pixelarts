package de.longuyen.cli;

import de.longuyen.core.Parameters;
import de.longuyen.image.ImageTransformer;
import de.longuyen.image.impl.PlainImageTransformer;
import lombok.SneakyThrows;
import picocli.CommandLine;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

@CommandLine.Command(header = "Asciify",  description = "Used to asciify images")
public class CommandProcessor implements Runnable {
    @CommandLine.Option(names = {"--file", "-f"}, description = "Target image file for asciifying", required = true)
    private String file;

    @CommandLine.Option(names = {"--interpolation", "-i"}, description = "How much should the image be interpolated")
    private int interpolation = 3;

    @CommandLine.Option(names = {"--window", "-w"}, description = "How big should the window's size be")
    private int windowSize = 3;

    @SneakyThrows
    @Override
    public void run() {
        ImageTransformer imageTransformer = new PlainImageTransformer(new Parameters(interpolation, windowSize));
        BufferedImage bufferedImage = ImageIO.read(new File(file));
        BufferedImage output = imageTransformer.convert(bufferedImage);
        ImageIO.write(output, "png", new File("output.png"));
    }
}
