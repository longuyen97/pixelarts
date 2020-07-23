package de.longuyen.core.polygon;

import de.longuyen.core.Transformer;
import de.longuyen.core.ascii.TestAsciifierTransformer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class TestPolygon {
    @Test
    void testPolygon() throws IOException {
        Transformer transformer = new Polygon(new Polygon.Parameters(50, 100));
        InputStream targetTestImage = TestAsciifierTransformer.class.getResourceAsStream("/sunflower.jpg");
        BufferedImage bi = ImageIO.read(targetTestImage);
        Assertions.assertNotNull(bi);
        BufferedImage result = transformer.convert(bi);
        Assertions.assertNotNull(result);
        ImageIO.write(result, "png", new File("target/test.png"));
    }
}
