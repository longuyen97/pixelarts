package de.longuyen.core.kmean;


import de.longuyen.core.polygon.Polygon;
import de.longuyen.core.utils.ReturnFunction;
import de.longuyen.core.utils.Vector3D;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.*;

/**
 * K-Mean algorithms with K centers for segmenting the RGB color space
 */
public class KMean extends Polygon {
    public KMean(Parameters parameters) {
        super(parameters);
    }

    @Override
    public Map<List<Point>, Color> generatePoints(BufferedImage bufferedImage) {
        // Generate random centers. Each center represents a colors
        List<Vector3D> centers = new ArrayList<>();
        Random random = new Random();
        for(int i = 0; i < KMean.this.parameters.centers; i++) {
            centers.add(new Vector3D(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
        }

        /*
         * Closure
         * Calculate which pixels belong to which center
         */
        ReturnFunction<Map<Vector3D, List<Point>>> calculateIdentity = () -> {
            Map<Vector3D, List<Point>> ret = new HashMap<>();
            for(int y = 0; y < bufferedImage.getHeight(); y++){
                for(int x = 0; x < bufferedImage.getWidth(); x++){
                    // Calculating the color of the current pixel
                    Point currentPixel = new Point(x, y);
                    int color = bufferedImage.getRGB(x, y);
                    int blue = color & 0xff;
                    int green = (color & 0xff00) >> 8;
                    int red = (color & 0xff0000) >> 16;
                    Vector3D currentPixelColor = new Vector3D(red, green, blue);

                    // Find the nearest center in the color space to the current pixel
                    Vector3D nearestCenter = centers.get(0);
                    double nearestDistance = centers.get(0).distance(currentPixelColor);
                    for (Vector3D center : centers) {
                        double currentDistance = center.distance(currentPixelColor);
                        if (currentDistance < nearestDistance) {
                            nearestDistance = currentDistance;
                            nearestCenter = center;
                        }
                    }

                    // Adding the current pixel to the nearest center's neighbors
                    if(!ret.containsKey(nearestCenter)){
                        ret.put(nearestCenter, new ArrayList<>());
                    }
                    ret.get(nearestCenter).add(currentPixel);
                }
            }
            return ret;
        };

        // Start the K-Mean algorithm
        for(int i = 0; i < KMean.this.parameters.iterations; i++){
            // Calculate identites for every pixel.

            Map<Vector3D, List<Point>> identities = calculateIdentity.apply();
            // Update the centers with average color of its every neighbor
            for(Map.Entry<Vector3D, List<Point>> entry : identities.entrySet()){
                DoubleSummaryStatistics xs = new DoubleSummaryStatistics();
                DoubleSummaryStatistics ys = new DoubleSummaryStatistics();
                DoubleSummaryStatistics zs = new DoubleSummaryStatistics();
                for(Point point : entry.getValue()){
                    int color = bufferedImage.getRGB(point.x, point.y);
                    int blue = color & 0xff;
                    int green = (color & 0xff00) >> 8;
                    int red = (color & 0xff0000) >> 16;
                    xs.accept(red);
                    ys.accept(green);
                    zs.accept(blue);
                }
                entry.getKey().x  = xs.getAverage();
                entry.getKey().y = ys.getAverage();
                entry.getKey().z = zs.getAverage();
            }
        }

        // Calculate the color of each point's cluster
        Map<Vector3D, List<Point>> identities = calculateIdentity.apply();
        Map<List<Point>, Color> returnValue = new HashMap<>();
        for(Map.Entry<Vector3D, List<Point>> entry : identities.entrySet()){
            returnValue.put(entry.getValue(), new Color((int)entry.getKey().x, (int)entry.getKey().y, (int)entry.getKey().z));
        }
        return returnValue;
    }
}
