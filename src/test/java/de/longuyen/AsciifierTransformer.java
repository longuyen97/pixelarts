package de.longuyen;

import de.longuyen.core.impl.Asciifier;
import de.longuyen.core.Transformer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

public class AsciifierTransformer {
    @Test
    public void testBasicSunFlower() {
        Transformer transformer = new Asciifier(2);
        InputStream targetTestImage = AsciifierTransformer.class.getResourceAsStream("/sunflower.jpg");
        Assertions.assertNotNull(targetTestImage);
        String result = transformer.convert(targetTestImage);
        Assertions.assertNotNull(result);
    }
}
