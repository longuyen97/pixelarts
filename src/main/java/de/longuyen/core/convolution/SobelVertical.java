package de.longuyen.core.convolution;

import java.awt.image.Kernel;

public class SobelVertical extends Convolution {
    @Override
    public Kernel kernel() {
        return new Kernel(3, 3, new float[]{
                -1, 0, 1, -2, 0, 2, -1, 0, 1
        });
    }
}
