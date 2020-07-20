package de.longuyen.core.impl;

import de.longuyen.core.Transformer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Asciifier implements Transformer {
    public static final String PIXEL_MAPPING = "$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/|()1{}[]?-_+~i!lI;:,^`. ";
    private final int windowSize;

    public Asciifier(final int windowSize) {
        this.windowSize = windowSize;
    }

    private char index(final BufferedImage bufferedImage, final int y, final int x){
        return 'a';
    }

    public String convert(final InputStream file) {
        StringBuilder result = new StringBuilder();
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            for (int y = 0; y < bufferedImage.getWidth(); y += this.windowSize) {
                for (int x = 0; x < bufferedImage.getHeight(); x += this.windowSize) {
                    result.append(index(bufferedImage, y, x));
                }
                result.append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result.toString();
    }
}