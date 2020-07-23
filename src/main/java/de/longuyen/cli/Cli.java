package de.longuyen.cli;

import de.longuyen.core.Transformer;
import lombok.SneakyThrows;
import picocli.CommandLine;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public abstract class Cli implements Runnable {
    @CommandLine.Option(names = {"--file", "-f"}, description = "Target image file for triangulating", required = true)
    protected String file;

    protected abstract Transformer transformer();

    @SneakyThrows
    @Override
    public void run() {
        BufferedImage bufferedImage = ImageIO.read(new File(file));
        BufferedImage output = transformer().convert(bufferedImage);
        ImageIO.write(output, "png", new File("output.png"));
    }
}
