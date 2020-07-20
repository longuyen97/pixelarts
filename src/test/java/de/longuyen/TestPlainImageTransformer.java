package de.longuyen;

import de.longuyen.image.ImageTransformer;
import de.longuyen.image.impl.PlainImageTransformer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class TestPlainImageTransformer {
    @Test
    void testSunFlowerImage() throws IOException {
        ImageTransformer transformer = new PlainImageTransformer(2);
        InputStream targetTestImage = TestAsciifierTransformer.class.getResourceAsStream("/sunflower.jpg");
        Assertions.assertNotNull(targetTestImage);
        BufferedImage result = transformer.convert(targetTestImage);
        Assertions.assertNotNull(result);
        ImageIO.write(result, "png", new File("target/test.png"));
    }
}
