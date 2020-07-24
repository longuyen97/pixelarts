package de.longuyen.core.util;

import de.longuyen.core.statistics.InterQuartile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TestInterquartileRange {
    @Test
    void testInterquartileRange(){
        BufferedImage bufferedImage = new BufferedImage(2, 5, BufferedImage.TYPE_INT_RGB);
        bufferedImage.setRGB(0, 0, new Color(0, 0, 0).getRGB());

        bufferedImage.setRGB(1, 0, new Color(1, 1, 1).getRGB());

        bufferedImage.setRGB(0, 1, new Color(2, 2, 2).getRGB());
        bufferedImage.setRGB(1, 1, new Color(2, 2, 2).getRGB());

        bufferedImage.setRGB(0, 2, new Color(3, 3, 3).getRGB());
        bufferedImage.setRGB(1, 2, new Color(3, 3, 3).getRGB());

        bufferedImage.setRGB(0, 3, new Color(3, 3, 3).getRGB());
        bufferedImage.setRGB(1, 3, new Color(4, 4, 4).getRGB());

        bufferedImage.setRGB(0, 4, new Color(4, 4, 4).getRGB());
        bufferedImage.setRGB(1, 4, new Color(4, 4, 4).getRGB());

        InterQuartile interQuartile = new InterQuartile(bufferedImage);
        Assertions.assertTrue(interQuartile.getQ25() < interQuartile.getQ50() && interQuartile.getQ50() < interQuartile.getQ75());
    }
}
