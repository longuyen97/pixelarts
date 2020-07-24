package de.longuyen.core.convolution;

import de.longuyen.core.Transformer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class TestSobel {
    @Test
    void testSobel() throws IOException {
        Transformer transformer = new Sobel(new Sobel.Parameters(1));
        InputStream targetTestImage = TestSobel.class.getResourceAsStream("/sunflower.jpg");
        BufferedImage bi = ImageIO.read(targetTestImage);
        Assertions.assertNotNull(bi);
        BufferedImage result = transformer.convert(bi);
        Assertions.assertNotNull(result);
        ImageIO.write(result, "png", new File("target/test.png"));
    }
}
