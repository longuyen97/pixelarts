package de.longuyen.core.convolution;

import de.longuyen.core.Transformer;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Sobel implements Transformer {
    private final Parameters parameters;

    public Sobel(Parameters parameters) {
        this.parameters = parameters;
    }

    public static class Parameters {
        private final int times;
        public Parameters(int times) {
            this.times = times;
        }
    }

    public BufferedImage convolve(BufferedImage bufferedImage){
        SobelHorizontal sobelHorizontal = new SobelHorizontal();
        SobelVertical sobelVertical = new SobelVertical();
        BufferedImage horizontal = sobelHorizontal.convert(bufferedImage);
        BufferedImage vertical = sobelVertical.convert(bufferedImage);
        assert horizontal.getWidth() == vertical.getWidth() && horizontal.getHeight() == vertical.getHeight();
        BufferedImage returnValue = new BufferedImage(horizontal.getWidth(), horizontal.getHeight(), horizontal.getType());
        for(int y = 0; y < returnValue.getHeight(); y++){
            for(int x = 0; x < returnValue.getWidth(); x++){
                Color horizontalColor = new Color(horizontal.getRGB(x, y));
                Color verticalColor = new Color(vertical.getRGB(x, y));
                double horizonGrayScale = (horizontalColor.getBlue() + horizontalColor.getGreen() + horizontalColor.getRed()) / 3.;
                double verticalGrayScale = (verticalColor.getBlue() + verticalColor.getGreen() + verticalColor.getRed()) / 3.;
                if(horizonGrayScale > verticalGrayScale){
                    returnValue.setRGB(x, y, horizontalColor.getRGB());
                }else{
                    returnValue.setRGB(x, y, verticalColor.getRGB());
                }
            }
        }
        return returnValue;
    }

    @Override
    public BufferedImage convert(BufferedImage bufferedImage) {
        for(int i = 0; i < this.parameters.times; i++){
            bufferedImage = convolve(bufferedImage);
        }
        return bufferedImage;
    }
}
