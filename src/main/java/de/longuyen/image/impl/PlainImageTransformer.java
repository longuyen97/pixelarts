package de.longuyen.image.impl;

import de.longuyen.core.FinalTransformer;
import de.longuyen.core.Parameters;
import de.longuyen.core.impl.Asciifier;
import de.longuyen.image.ImageTransformer;
import lombok.SneakyThrows;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PlainImageTransformer implements ImageTransformer {
    private final Parameters parameters;

    public PlainImageTransformer(final Parameters parameters) {
        this.parameters = parameters;
    }

    @SneakyThrows
    @Override
    public BufferedImage convert(final BufferedImage bufferedImage) {
        FinalTransformer transformer = new Asciifier(this.parameters);
        String result = transformer.convert(bufferedImage);
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
