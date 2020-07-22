package de.longuyen.core.ascii;

import de.longuyen.core.Transformer;
import lombok.Data;
import lombok.SneakyThrows;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Asciifier implements Transformer {
    public static final char[] PIXEL_MAPPING = "$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/|()1{}[]?-_+~i!lI;:,^`. ".toCharArray();
    private final Parameters parameters;
    private final float windowArea;

    @Data
    public static class Parameters {
        private int interpolation;
        private int windowSize;
        public Parameters(final int interpolation, final int windowSize) {
            this.interpolation = interpolation;
            this.windowSize = windowSize;
        }
    }

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

    public String toAscii(final BufferedImage bufferedImage){
        StringBuilder result = new StringBuilder();
        for (int y = 0; (y + this.parameters.getWindowSize()) < bufferedImage.getHeight(); y += this.parameters.getWindowSize()) {
            for (int x = 0; (x + this.parameters.getWindowSize()) < bufferedImage.getWidth(); x += this.parameters.getWindowSize()) {
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
            BufferedImage image = new BufferedImage(bufferedImage.getWidth() * this.parameters.getInterpolation(), bufferedImage.getHeight() * this.parameters.getInterpolation(), BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = image.createGraphics();
            graphics.setColor(new Color(255, 255, 255));
            graphics.fillRect(0, 0, bufferedImage.getWidth() * this.parameters.getInterpolation(), bufferedImage.getHeight() * this.parameters.getInterpolation());
            graphics.setColor(new Color(0, 0, 0));
            graphics.setFont(new Font("Calibri", Font.BOLD, this.parameters.getWindowSize() * this.parameters.getInterpolation()));
            for (int y = 0; y < lines.length; y++) {
                String line = lines[y];
                char[] pixels = line.toCharArray();
                for (int x = 0; x < pixels.length; x++) {
                    graphics.drawString(String.valueOf(pixels[x]), x * this.parameters.getWindowSize() * this.parameters.getInterpolation(), y * this.parameters.getWindowSize() * this.parameters.getInterpolation());
                }
            }
            return image;
        } else {
            return new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        }
    }
}
