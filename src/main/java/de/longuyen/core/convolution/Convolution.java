package de.longuyen.core.convolution;

import de.longuyen.core.Transformer;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public abstract class Convolution implements Transformer {
    public abstract Kernel kernel();

    @Override
    public BufferedImage convert(BufferedImage bufferedImage) {
        ConvolveOp convolveOp = new ConvolveOp(kernel(), ConvolveOp.EDGE_ZERO_FILL, null);
        BufferedImage returnValue = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), bufferedImage.getType());
        convolveOp.filter(bufferedImage, returnValue);
        return returnValue;
    }
}
