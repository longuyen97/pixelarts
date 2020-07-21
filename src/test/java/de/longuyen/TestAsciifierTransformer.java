package de.longuyen;

import de.longuyen.core.impl.Asciifier;
import de.longuyen.core.Transformer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class TestAsciifierTransformer {
    @Test
    public void testBasicSunFlower() throws IOException {
        Transformer transformer = new Asciifier(2);
        InputStream targetTestImage = TestAsciifierTransformer.class.getResourceAsStream("/sunflower.jpg");
        BufferedImage bufferedImage = ImageIO.read(targetTestImage);
        Assertions.assertNotNull(targetTestImage);
        String result = transformer.convert(bufferedImage);
        Assertions.assertNotNull(result);
        System.out.println(result);
    }

    @Test
    public void testWhiteImage() throws IOException {
        BufferedImage bufferedImage = new BufferedImage(100, 100, ColorSpace.TYPE_RGB);
        Graphics2D graphics = bufferedImage.createGraphics();

        graphics.setColor (new Color(255, 255, 255));
        graphics.fillRect ( 0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpeg", os);
        for(int i = 1; i < 100; i++) {
            InputStream is = new ByteArrayInputStream(os.toByteArray());
            BufferedImage inputImage = ImageIO.read(is);
            Transformer transformer = new Asciifier(i);
            String result = transformer.convert(inputImage);
            Assertions.assertNotNull(result);
        }
    }

    @Test
    public void testBlackImage() throws IOException {
        BufferedImage bufferedImage = new BufferedImage(100, 100, ColorSpace.TYPE_RGB);
        Graphics2D graphics = bufferedImage.createGraphics();

        graphics.setColor (new Color(0, 0, 0));
        graphics.fillRect ( 0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpeg", os);
        for(int i = 1; i < 100; i++) {
            InputStream is = new ByteArrayInputStream(os.toByteArray());
            BufferedImage inputImage = ImageIO.read(is);
            Transformer transformer = new Asciifier(i);
            String result = transformer.convert(inputImage);
            Assertions.assertNotNull(result);
        }
    }
}
