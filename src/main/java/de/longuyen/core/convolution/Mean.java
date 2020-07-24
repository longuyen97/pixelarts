package de.longuyen.core.convolution;

import java.awt.image.Kernel;

public class Mean extends Convolution  {
    @Override
    public Kernel kernel() {
        return new Kernel(3, 3, new float[]{
                1.f/9.f,  1.f/9.f,  1.f/9.f,  1.f/9.f,  1.f/9.f,  1.f/9.f,  1.f/9.f,  1.f/9.f,  1.f/9.f
        });
    }
}
