package de.longuyen;

import java.io.File;

public class AsciifierTransformer {
    public static void main(String[] args) {
        Transformer transformer = new Asciifier(10);
        String result = transformer.convert(System.class.getResourceAsStream("/sunflower.jpg"));
    }
}
