package de.longuyen.core.voronoi;

import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TestSimpleVoronoi {
    @Test
    void testSimpleImage() throws IOException {
        BufferedImage bufferedImage = new BufferedImage(1000, 1000, ColorSpace.TYPE_RGB);
        Graphics2D graphics = bufferedImage.createGraphics();

        graphics.setColor (new Color(0, 0, 0));
        graphics.fillRect ( 0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());

        BufferedImage trianglatedImage = new SimpleVoronoi(new SimpleVoronoi.Parameters(100)).convert(bufferedImage);
        ImageIO.write(trianglatedImage, "png", new File("target/test.png"));

    }
}
