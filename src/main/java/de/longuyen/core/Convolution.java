package de.longuyen.core;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public interface Convolution {
    int [][] getKernel();

    int getSlide();

    default BufferedImage convert(final BufferedImage bufferedImage){
        ColorModel colorModel = bufferedImage.getColorModel();
        boolean isAlphaPremultiplied = colorModel.isAlphaPremultiplied();
        WritableRaster raster = bufferedImage.copyData(null);
        BufferedImage returnValue = new BufferedImage(colorModel, raster, isAlphaPremultiplied, null);
        for(int y = 0; y < returnValue.getHeight(); y += getSlide()){
            for(int x = 0; x < returnValue.getWidth(); x += getSlide()){
                // TODO
            }
        }
        return returnValue;
    }
}
