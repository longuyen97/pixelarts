package de.longuyen.core.voronoi;

import de.longuyen.core.Transformer;
import de.longuyen.core.convolution.Sobel;
import de.longuyen.core.statistics.InterQuartile;
import de.longuyen.core.utils.Vector2D;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AdvancedVoronoi extends SimpleVoronoi {
    private final Parameters parameters;
    public AdvancedVoronoi(Parameters parameters) {
        super(parameters);
        this.parameters = parameters;
    }

    public static class Parameters extends SimpleVoronoi.Parameters {
        public final double range;
        public final int times;
        public Parameters(int points, double range, int times) {
            super(points);
            this.range = range;
            this.times = times;
        }
    }

    @Override
    public List<Vector2D> generatePoints(BufferedImage bufferedImage) {
        Transformer convolution = new Sobel(new Sobel.Parameters(this.parameters.times));
        BufferedImage edgeEnhancedImage = convolution.convert(bufferedImage);
        InterQuartile interQuartile = new InterQuartile(edgeEnhancedImage);
        int Q95 = interQuartile.getPercentile(this.parameters.range);
        List<Vector2D> candidates = new ArrayList<>();
        for(int y = 0; y < edgeEnhancedImage.getHeight(); y++){
            for(int x = 0; x < edgeEnhancedImage.getWidth(); x++){
                Vector2D point2D = new Vector2D(x, y);
                Color currentColor = new Color(edgeEnhancedImage.getRGB(x, y));
                if((currentColor.getRed() + currentColor.getGreen() + currentColor.getBlue()) / 3 >= Q95){
                    candidates.add(point2D);
                }
            }
        }

        Random random = new Random();
        List<Vector2D> returnValue = new ArrayList<>();
        for(int i = 0; i < this.parameters.points && candidates.size() > 0; i++){
            int randomIndex = random.nextInt(candidates.size());
            returnValue.add(candidates.get(randomIndex));
            candidates.remove(randomIndex);
        }
        return returnValue;
    }
}
