package de.longuyen.core.ascii;

import de.longuyen.core.Transformer;
import lombok.SneakyThrows;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Asciifying image. Image with RGB will be transformed into the ASCII equivalent.
 *
 * The scanning process will occurs in window. A window size of 1 will produce an image with same size
 * of the input. A window size of 2 will reduce the input by factor 2.
 */
public class Asciifier implements Transformer {
    /**
     * Mapping pixel's gray scale from 0 to 255 into equivalent alphabet.
     */
    public static final char[] PIXEL_MAPPING = "$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/|()1{}[]?-_+~i!lI;:,^`. ".toCharArray();
    private final Parameters parameters;
    private final float windowArea;

    /**
     * Parameter class for asciifying.
     */
    public static class Parameters {
        public final int interpolation;
        public final int windowSize;
        public Parameters(final int interpolation, final int windowSize) {
            this.interpolation = interpolation;
            this.windowSize = windowSize;
        }
    }

    /**
     * Constructor
     * @param parameters General parameters
     * @throws RuntimeException if the parameter's window size is less than 0
     */
    public Asciifier(final Parameters parameters) {
        if (parameters.windowSize <= 0) {
            throw new RuntimeException("Please choose a greater window size than 0");
        }
        this.parameters = parameters;
        this.windowArea = this.parameters.windowSize * this.parameters.windowSize;
    }

    /**
     * Calculate the average gray scale of the the window. Start from (x, y) to (x + windowSize, y + windowSize).
     *
     * Map the gray scale value to the equivalent character.
     */
    private char index(final BufferedImage bufferedImage, final int y, final int x) {
        float averageGrayScale = 0.f;
        for (int yi = y; yi < y + this.parameters.windowSize; yi++) {
            for (int xi = x; xi < x + this.parameters.windowSize; xi++) {

                // Extracting the color.
                // 32 Bit integer will contains ARGB images.
                // Ignore the alpha value
                int color = bufferedImage.getRGB(x, y);
                int blue = color & 0xff;
                int green = (color & 0xff00) >> 8;
                int red = (color & 0xff0000) >> 16;
                averageGrayScale += (blue + green + red) / 3.f;
            }
        }
        averageGrayScale /= this.windowArea;

        // Map to the equivalent character's index.
        return PIXEL_MAPPING[(int) ((averageGrayScale / 256.f) * PIXEL_MAPPING.length)];
    }

    /**
     * Convert an image into the ascii equivalent.
     * The result of this method is a 2D string. Each lines of the string is separated by a new line \n
     *
     * @param bufferedImage targeted image
     * @return 2D String
     */
    public String toAscii(final BufferedImage bufferedImage){
        StringBuilder result = new StringBuilder();
        for (int y = 0; (y + this.parameters.windowSize) < bufferedImage.getHeight(); y += this.parameters.windowSize) {
            for (int x = 0; (x + this.parameters.windowSize) < bufferedImage.getWidth(); x += this.parameters.windowSize) {
                result.append(index(bufferedImage, y, x));
            }
            result.append("\n");
        }
        return result.toString();
    }

    @SneakyThrows
    public BufferedImage convert(final BufferedImage bufferedImage) {
        String result = toAscii(bufferedImage);
        String[] lines = result.split("\n");
        if (lines.length > 0) {
            // Calculate the width and height of the final image, considering the interpolation factor
            final int width = bufferedImage.getWidth() * this.parameters.interpolation;
            final int height = bufferedImage.getHeight() * this.parameters.interpolation;
            BufferedImage returnValue = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            // Make the final image default white
            Graphics2D graphics = returnValue.createGraphics();
            graphics.setColor(new Color(255, 255, 255));
            graphics.fillRect(0, 0, width, bufferedImage.getHeight() * height);

            // Make the text's color of the final image default black
            graphics.setColor(new Color(0, 0, 0));
            graphics.setFont(new Font("Calibri", Font.BOLD, this.parameters.windowSize * this.parameters.interpolation));

            // Putting each character of the 2D image into the corresponding pixel
            for (int y = 0; y < lines.length; y++) {
                String line = lines[y];
                char[] pixels = line.toCharArray();
                for (int x = 0; x < pixels.length; x++) {
                    final int xi = x * this.parameters.windowSize * this.parameters.interpolation;
                    final int yi = y * this.parameters.windowSize * this.parameters.interpolation;
                    graphics.drawString(String.valueOf(pixels[x]), xi, yi);
                }
            }
            return returnValue;
        } else {
            return new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        }
    }
}
