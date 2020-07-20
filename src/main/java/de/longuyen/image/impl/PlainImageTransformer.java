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
    public BufferedImage convert(final InputStream inputStream) {
        Transformer transformer = new Asciifier(windowSize);
        String result = transformer.convert(inputStream);
        String[] lines = result.split("\n");
        if(lines.length > 0) {
            BufferedImage image = new BufferedImage(lines[0].length(), lines.length, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = image.createGraphics();
            g2d.setPaint(Color.red);
            g2d.setFont(new Font("Serif", Font.BOLD, 1));
            String s = "Hello, world!";
            FontMetrics fm = g2d.getFontMetrics();
            int x = image.getWidth() - fm.stringWidth(s) - 5;
            int y = fm.getHeight();
            g2d.drawString(s, x, y);
            g2d.dispose();
            return image;
        }else {
            return new BufferedImage(0, 0, BufferedImage.TYPE_INT_RGB);
        }
    }
}
