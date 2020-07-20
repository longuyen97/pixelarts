package de.longuyen.core.impl;

import de.longuyen.core.Transformer;
import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;

public class Asciifier implements Transformer {
    public static final String PIXEL_MAPPING = "$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/|()1{}[]?-_+~i!lI;:,^`. ";
    private final int windowSize;

    /**
     * Constructor
     *
     * @param windowSize sliding window size. If windowSize is 1, then the output's size will be same as the input's size.
     *                   If thw windowSize is greater than 1, average gray scale of the window will be computed
     */
    public Asciifier(final int windowSize) {
        if (windowSize == 0) {
            throw new RuntimeException("Please choose a greater window size than 0");
        }
        this.windowSize = windowSize;
    }

    /**
     * Calculate the average gray scale of the the window. Start from (x, y) to (x + windowSize, y + windowSize)
     */
    private char index(final BufferedImage bufferedImage, final int y, final int x) {
        float averageGrayScale = 0.f;
        for (int yi = y; yi < y + this.windowSize; yi++) {
            for (int xi = x; xi < x + this.windowSize; xi++) {

                int color = bufferedImage.getRGB(x, y);
                int blue = color & 0xff;
                int green = (color & 0xff00) >> 8;
                int red = (color & 0xff0000) >> 16;
                averageGrayScale += (blue + green + red) / 3.f;
            }
        }
        averageGrayScale /= this.windowSize * this.windowSize;
        int index = (int) ((averageGrayScale / 256.f) * PIXEL_MAPPING.length());
        return PIXEL_MAPPING.charAt(index);
    }

    @SneakyThrows
    public String convert(final InputStream file) {
        StringBuilder result = new StringBuilder();
        BufferedImage bufferedImage = ImageIO.read(file);
        for (int y = 0; (y + this.windowSize) < bufferedImage.getHeight(); y += this.windowSize) {
            for (int x = 0; (x + this.windowSize) < bufferedImage.getWidth(); x += this.windowSize) {
                result.append(index(bufferedImage, y, x));
                result.append(" ");
            }
            result.append("\n");
        }
        return result.toString();
    }
}
