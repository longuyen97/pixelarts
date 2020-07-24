package de.longuyen.core.voronoi;

import de.longuyen.core.Transformer;
import de.longuyen.core.convolution.Sobel;
import de.longuyen.core.utils.Vector2D;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class AdvancedVoronoi extends SimpleVoronoi {
    public AdvancedVoronoi(Parameters parameters) {
        super(parameters);
    }

    @Override
    public List<Vector2D> generatePoints(BufferedImage bufferedImage) {
        List<Vector2D> returnValue = new ArrayList<>();
        Transformer convolution = new Sobel();
        BufferedImage edgeEnhancedImage = convolution.convert(bufferedImage);

        return returnValue;
    }
}
