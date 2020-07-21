package de.longuyen.core.impl;

import de.longuyen.core.Convolution;

public class SobelConvolution implements Convolution {
    private static final int [][] KERNEL = new int[][]{
        new int[]{1, 2, 3},
        new int[]{1, 2, 3},
        new int[]{1, 2, 3}
    };

    @Override
    public int[][] getKernel() {
        return KERNEL;
    }

    @Override
    public int getSlide() {
        return 1;
    }
}
