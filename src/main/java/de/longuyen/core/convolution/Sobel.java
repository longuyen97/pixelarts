package de.longuyen.core.convolution;

import de.longuyen.core.Transformer;

import java.awt.image.BufferedImage;

public class Sobel implements Transformer {
    public final int [][] SOBEL = new int[][]{
            new int[]{-1, 0, 1},
            new int[]{-2, 0, 2},
            new int[]{-1, 0, 1}
    };

    @Override
    public BufferedImage convert(BufferedImage bufferedImage) {
        return null;
    }
}
