package de.longuyen;

import de.longuyen.core.Asciifier;
import de.longuyen.core.Transformer;

import java.io.InputStream;

public class AsciifierTransformer {
    public static void main(String[] args) {
        Transformer transformer = new Asciifier(10);
        InputStream targetTestImage = AsciifierTransformer.class.getResourceAsStream("/sunflower.jpg");
        String result = transformer.convert(targetTestImage);
        System.out.println(result);
    }
}
