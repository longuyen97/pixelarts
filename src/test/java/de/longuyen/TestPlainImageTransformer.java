package de.longuyen;

import de.longuyen.core.Parameters;
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
        ImageTransformer transformer = new PlainImageTransformer(new Parameters(2, 4));
        InputStream targetTestImage = TestAsciifierTransformer.class.getResourceAsStream("/sunflower.jpg");
        BufferedImage bi = ImageIO.read(targetTestImage);
        Assertions.assertNotNull(bi);
        BufferedImage result = transformer.convert(bi);
        Assertions.assertNotNull(result);
        ImageIO.write(result, "png", new File("target/test.png"));
    }
}
