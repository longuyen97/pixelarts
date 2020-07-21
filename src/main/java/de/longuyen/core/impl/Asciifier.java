package de.longuyen.core.impl;

import de.longuyen.core.FinalTransformer;
import de.longuyen.core.Parameters;
import lombok.SneakyThrows;

import java.awt.image.BufferedImage;

public class Asciifier implements FinalTransformer {
    public static final char[] PIXEL_MAPPING = "$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/|()1{}[]?-_+~i!lI;:,^`. ".toCharArray();
    private final Parameters parameters;
    private final float windowArea;

    /**
     * Constructor
     * @param parameters General parameters
     */
    public Asciifier(final Parameters parameters) {
        if (parameters.getWindowSize() == 0) {
            throw new RuntimeException("Please choose a greater window size than 0");
        }
        this.parameters = parameters;
        this.windowArea = this.parameters.getWindowSize() * this.parameters.getWindowSize();
    }

    /**
     * Calculate the average gray scale of the the window. Start from (x, y) to (x + windowSize, y + windowSize)
     */
    private char index(final BufferedImage bufferedImage, final int y, final int x) {
        float averageGrayScale = 0.f;
        for (int yi = y; yi < y + this.parameters.getWindowSize(); yi++) {
            for (int xi = x; xi < x + this.parameters.getWindowSize(); xi++) {
                int color = bufferedImage.getRGB(x, y);
                int blue = color & 0xff;
                int green = (color & 0xff00) >> 8;
                int red = (color & 0xff0000) >> 16;
                averageGrayScale += (blue + green + red) / 3.f;
            }
        }
        averageGrayScale /= this.windowArea;
        int index = (int) ((averageGrayScale / 256.f) * PIXEL_MAPPING.length);
        return PIXEL_MAPPING[index];
    }

    @SneakyThrows
    public String convert(final BufferedImage bufferedImage) {
        StringBuilder result = new StringBuilder();
        for (int y = 0; (y + this.parameters.getWindowSize()) < bufferedImage.getHeight(); y += this.parameters.getWindowSize()) {
            for (int x = 0; (x + this.parameters.getWindowSize()) < bufferedImage.getWidth(); x += this.parameters.getWindowSize()) {
                result.append(index(bufferedImage, y, x));
            }
            result.append("\n");
        }
        return result.toString();
    }
}
