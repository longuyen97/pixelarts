package de.longuyen.image.impl;

import de.longuyen.core.Transformer;
import de.longuyen.core.impl.Asciifier;
import de.longuyen.image.ImageTransformer;
import lombok.SneakyThrows;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;

public class PlainImageTransformer implements ImageTransformer {
    private int windowSize;

    public PlainImageTransformer(final int windowSize) {
        this.windowSize = windowSize;
    }

    @SneakyThrows
    @Override
    public BufferedImage convert(final BufferedImage bufferedImage) {
        Transformer transformer = new Asciifier(windowSize);
        String result = transformer.convert(bufferedImage);
        String[] lines = result.split("\n");
        if(lines.length > 0) {
            BufferedImage image = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = image.createGraphics();
            graphics.setColor (new Color(255, 255, 255));
            graphics.fillRect ( 0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
            graphics.setColor (new Color(0, 0, 0));
            graphics.setFont(new Font("Calibri", Font.BOLD, this.windowSize));
            for (int y = 0; y < lines.length; y++) {
                String line = lines[y];
                char [] pixels = line.toCharArray();
                for (int x = 0; x < pixels.length; x++) {
                    graphics.drawString(String.valueOf(pixels[x]), x * this.windowSize, y * this.windowSize);
                }
            }
            return image;
        }else {
            return new BufferedImage(bufferedImage.getWidth(),  bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        }
    }
}
