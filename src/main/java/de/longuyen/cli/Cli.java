package de.longuyen.cli;

import de.longuyen.core.Transformer;
import lombok.SneakyThrows;
import picocli.CommandLine;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public abstract class Cli implements Runnable {
    @CommandLine.Parameters(description = "Target image file for triangulating")
    protected String file;

    protected abstract Transformer transformer();

    @SneakyThrows
    @Override
    public void run() {
        String [] parts = file.split("\\.");
        BufferedImage bufferedImage = ImageIO.read(new File(file));
        BufferedImage output = transformer().convert(bufferedImage);
        ImageIO.write(output, parts[1], new File(String.format("%s-output.%s", parts[0], parts[1])));
    }
}
