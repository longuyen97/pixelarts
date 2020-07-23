package de.longuyen.core.voronoi;

import de.longuyen.core.Transformer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.List;
import java.util.*;

public class SimpleVoronoi implements Transformer {
    private final Parameters parameters;

    public SimpleVoronoi(Parameters parameters) {
        this.parameters = parameters;
    }

    public static class Parameters {
        public final int points;

        public Parameters(int points) {
            this.points = points;
        }
    }

    public List<Vector2D> generatePoints(final BufferedImage bufferedImage){
        Random random = new Random();
        Set<Vector2D> points = new HashSet<>();
        for(int i = 0; i < parameters.points; i++){
            int x = random.nextInt(bufferedImage.getWidth() - 1);
            int y = random.nextInt(bufferedImage.getHeight() - 1);
            points.add(new Vector2D(x, y));
        }
        points.add(new Vector2D(0, 0));
        points.add(new Vector2D(0, bufferedImage.getHeight() - 1));
        points.add(new Vector2D(bufferedImage.getWidth() - 1, 0));
        points.add(new Vector2D(bufferedImage.getWidth() - 1, bufferedImage.getHeight() - 1));
        return new ArrayList<>(points);
    }

    @Override
    public BufferedImage convert(BufferedImage bufferedImage) {
        List<Triangle2D> triangles = new VoronoiTriangulator().triangulate(generatePoints(bufferedImage));

        Map<Triangle2D, List<DoubleSummaryStatistics>> trianglesPixelsMap = new HashMap<>();
        for(int y = 0; y < bufferedImage.getHeight(); y++){
            for(int x = 0; x < bufferedImage.getWidth(); x++){
                Vector2D currentPixel = new Vector2D(x, y);
                for(Triangle2D triangle2D : triangles){
                    if(triangle2D.contains(currentPixel)) {
                        int color = bufferedImage.getRGB(x, y);
                        int blue = color & 0xff;
                        int green = (color & 0xff00) >> 8;
                        int red = (color & 0xff0000) >> 16;
                        List<DoubleSummaryStatistics> colorStatistics = Arrays.asList(new DoubleSummaryStatistics(), new DoubleSummaryStatistics(), new DoubleSummaryStatistics());
                        colorStatistics.get(0).accept(blue);
                        colorStatistics.get(1).accept(green);
                        colorStatistics.get(2).accept(red);
                        trianglesPixelsMap.put(triangle2D, colorStatistics);
                        break;
                    }
                }
            }
        }

        ColorModel cm = bufferedImage.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bufferedImage.copyData(null);
        BufferedImage returnValue = new BufferedImage(cm, raster, isAlphaPremultiplied, null);
        Map<Triangle2D, Color> trianglesColors = new HashMap<>();
        for (Map.Entry<Triangle2D, List<DoubleSummaryStatistics>> entry : trianglesPixelsMap.entrySet()) {
            DoubleSummaryStatistics sumBlue = entry.getValue().get(0);
            DoubleSummaryStatistics sumGreen = entry.getValue().get(1);
            DoubleSummaryStatistics sumRed = entry.getValue().get(2);
            trianglesColors.put(entry.getKey(), new Color((int)sumRed.getAverage(), (int)sumGreen.getAverage(), (int)sumBlue.getAverage()));
        }

        TriangleCollection triangleCollection = new TriangleCollection(triangles);
        for(int y = 0; y < returnValue.getHeight(); y++) {
            for (int x = 0; x < returnValue.getWidth(); x++) {
                boolean dangling = true;
                Vector2D currentPixel = new Vector2D(x, y);
                for (Map.Entry<Triangle2D, Color> entry : trianglesColors.entrySet()) {
                    if (entry.getKey().contains(currentPixel)) {
                        returnValue.setRGB(x, y, entry.getValue().getRGB());
                        dangling = false;
                        break;
                    }
                }
                if (dangling){
                    Triangle2D nearestTriangle = triangleCollection.findNearestTriangle(currentPixel);
                    returnValue.setRGB(x, y, trianglesColors.getOrDefault(nearestTriangle, new Color(255, 255, 255)).getRGB());
                }
            }
        }
        return returnValue;
    }
}
