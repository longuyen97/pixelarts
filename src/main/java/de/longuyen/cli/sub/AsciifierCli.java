package de.longuyen.cli.sub;

import de.longuyen.core.Transformer;
import de.longuyen.core.ascii.Asciifier;
import lombok.SneakyThrows;
import picocli.CommandLine;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

@CommandLine.Command(name = "asciify", description = "Used to asciify images")
public class AsciifierCli implements Runnable{
    @CommandLine.Option(names = {"--file", "-f"}, description = "Target image file for asciifying", required = true)
    private String file;

    @CommandLine.Option(names = {"--interpolation", "-i"}, description = "How much should the image be interpolated")
    private int interpolation = 3;

    @CommandLine.Option(names = {"--window", "-w"}, description = "How big should the window's size be")
    private int windowSize = 3;

    @SneakyThrows
    @Override
    public void run() {
        Transformer imageTransformer = new Asciifier(new Asciifier.Parameters(interpolation, windowSize));
        BufferedImage bufferedImage = ImageIO.read(new File(file));
        BufferedImage output = imageTransformer.convert(bufferedImage);
        ImageIO.write(output, "png", new File("output.png"));
    }
}
